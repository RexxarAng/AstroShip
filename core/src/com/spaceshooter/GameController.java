package com.spaceshooter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.spaceshooter.configuration.AudioConfiguration;
import com.spaceshooter.configuration.GameConfiguration;
import com.spaceshooter.configuration.TextureConfiguration;
import com.spaceshooter.effect.ExplosionEffect;
import com.spaceshooter.effect.GameEffect;
import com.spaceshooter.effect.TextEffect;
import com.spaceshooter.enemy.EnemyShipSpawner;
import com.spaceshooter.pickup.Pickup;
import com.spaceshooter.pickup.RailgunAmmoPickup;
import com.spaceshooter.pickup.RocketPickup;
import com.spaceshooter.pickup.ShieldPickup;
import com.spaceshooter.ship.EnemyShip;
import com.spaceshooter.ship.PlayerShip;
import com.spaceshooter.weapon.HomingRocket;
import com.spaceshooter.weapon.Projectile;
import com.spaceshooter.weapon.TungstenSlug;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class GameController {

    // Player lives
    private int lives;
    // Time
    private float time;

    // Players
    private final ArrayList<Player> playerList;

    // Game objects arraylist reference to GameScreen lists for rendering
    private ArrayList<PlayerShip> playerShipList;
    private ArrayList<EnemyShip> enemyShipList;
    private ArrayList<Projectile> projectileList;
    private ArrayList<GameEffect> gameEffectList;
    private ArrayList<Pickup> powerUpList;

    // For spawning enemy ships
    private EnemyShipSpawner enemyShipSpawner;

    // For game mode
    private final boolean isSinglePlayer;

    // For increasing difficulty
    private float timeSinceLastDifficultyIncrease = 0f;

    public GameController(boolean isSinglePlayer, List<Player> playerList) {
        this.isSinglePlayer = isSinglePlayer;
        this.playerList = (ArrayList<Player>) playerList;
        AudioConfiguration.getInstance().AUDIO_GAME_START.play(0.2f);
        this.instantiate();
    }

    public void instantiate() {
        this.playerShipList = new ArrayList<>();
        this.enemyShipList = new ArrayList<>();
        this.projectileList = new ArrayList<>();
        this.gameEffectList = new ArrayList<>();
        this.powerUpList = new ArrayList<>();
        this.enemyShipSpawner = new EnemyShipSpawner(enemyShipList, isSinglePlayer);
        this.time = 0;
        for (Player player: playerList) {
            player.setScore(0);
        }
        if (isSinglePlayer) {
            playerShipList.add(new PlayerShip(
                    GameConfiguration.getInstance().WORLD_WIDTH/2f,
                    GameConfiguration.getInstance().WORLD_HEIGHT/4f,
                    GameConfiguration.getInstance().CURRENT_PLAYER_SHIP_WIDTH,
                    GameConfiguration.getInstance().CURRENT_PLAYER_SHIP_HEIGHT,
                    playerList.get(0),
                    GameConfiguration.getInstance().DEFAULT_PLAYER_MOVEMENT_SPEED,
                    GameConfiguration.getInstance().DEFAULT_PLAYER_SHIELD,
                    TextureConfiguration.getInstance().TEXTURE_PLAYER1));
            this.lives = GameConfiguration.getInstance().SINGLE_PLAYER_DEFAULT_LIVES;
        } else {
            playerShipList.add(new PlayerShip(
                    GameConfiguration.getInstance().WORLD_WIDTH/2f + GameConfiguration.getInstance().WORLD_WIDTH/4f,
                    GameConfiguration.getInstance().WORLD_HEIGHT/4f,
                    GameConfiguration.getInstance().CURRENT_PLAYER_SHIP_WIDTH,
                    GameConfiguration.getInstance().CURRENT_PLAYER_SHIP_HEIGHT,
                    playerList.get(0),
                    GameConfiguration.getInstance().DEFAULT_PLAYER_MOVEMENT_SPEED,
                    GameConfiguration.getInstance().DEFAULT_PLAYER_SHIELD,
                    TextureConfiguration.getInstance().TEXTURE_PLAYER1));
            playerShipList.add(new PlayerShip(
                    GameConfiguration.getInstance().WORLD_WIDTH/2f - GameConfiguration.getInstance().WORLD_WIDTH/4f,
                    GameConfiguration.getInstance().WORLD_HEIGHT/4f,
                    GameConfiguration.getInstance().CURRENT_PLAYER_SHIP_WIDTH,
                    GameConfiguration.getInstance().CURRENT_PLAYER_SHIP_HEIGHT,
                    playerList.get(1),
                    GameConfiguration.getInstance().DEFAULT_PLAYER_MOVEMENT_SPEED,
                    GameConfiguration.getInstance().DEFAULT_PLAYER_SHIELD,
                    TextureConfiguration.getInstance().TEXTURE_PLAYER2));
            this.lives = GameConfiguration.getInstance().COOP_DEFAULT_LIVES;
        }
    }

    public void restartGame() {
        this.playerShipList.clear();
        this.enemyShipList.clear();
        this.projectileList.clear();
        this.gameEffectList.clear();
        this.instantiate();
    }

    public void tick(float deltaTime) {
        // Move players
        detectInput(deltaTime);
        for (PlayerShip playerShip : playerShipList) {
            playerShip.update(deltaTime);
            playerShip.fireProjectiles(projectileList);
        }

        // Increase Difficulty
        this.increaseDifficulty(deltaTime);

        // Spawn enemy ships
        enemyShipSpawner.spawnEnemyShips(deltaTime);

        // Enemy movements
        for (EnemyShip enemyShip : enemyShipList) {
            enemyShip.update(deltaTime);
            enemyShip.fireProjectiles(projectileList);
        }

        ListIterator<GameEffect> explosionListIterator = gameEffectList.listIterator();
        while (explosionListIterator.hasNext()) {
            GameEffect explosionEffect = explosionListIterator.next();
            explosionEffect.update(deltaTime);
            if (explosionEffect.isFinished()) {
                explosionListIterator.remove();
            }
        }

        ListIterator<Projectile> projectileIterator = projectileList.listIterator();
        while(projectileIterator.hasNext()) {
            Projectile projectile = projectileIterator.next();
            if (projectile instanceof HomingRocket) {
                ((HomingRocket) projectile).updateTarget(playerShipList, enemyShipList);
            }
            projectile.update(deltaTime);
            if (projectileOutOfBound(projectile)) {
                projectileIterator.remove();
            }
        }

        for (Pickup powerUp : powerUpList) {
            powerUp.update(deltaTime);
        }

        this.time += deltaTime;
        detectCollisions();
    }

    private boolean projectileOutOfBound(Projectile projectile) {
        return projectile.getHitBox().y > GameConfiguration.getInstance().WORLD_HEIGHT ||
                projectile.getHitBox().y + projectile.getHitBox().height < 0 ||
                projectile.getHitBox().x < 0 ||
                projectile.getHitBox().x > GameConfiguration.getInstance().WORLD_WIDTH;
    }

    private void increaseDifficulty(float deltaTime) {
        timeSinceLastDifficultyIncrease += deltaTime;
        int diffSec = GameConfiguration.getInstance().DIFFICULTY_INCREASE_RATE_IN_SECONDS;
        if (timeSinceLastDifficultyIncrease > diffSec) {
            System.out.println("Current time: " + this.time);
            enemyShipSpawner.decreaseTimeBetweenSpawns();
            timeSinceLastDifficultyIncrease -= diffSec;

            this.enemyShipSpawner.updateLevel(Math.max(1, Math.min(diffSec, (int)(this.time / diffSec))));
        }
    }

    private void detectInput(float deltaTime) {
        //Keyboard input
        //Determine max distance ship can move
        //Check each key that matters and move accordingly

        float leftLimit, rightLimit, upLimit, downLimit;
        for (PlayerShip playerShip : playerShipList) {
            leftLimit = -playerShip.getHitBox().x;
            downLimit = -playerShip.getHitBox().y;
            rightLimit = GameConfiguration.getInstance().WORLD_WIDTH - playerShip.getHitBox().x - playerShip.getHitBox().width;
            upLimit = (float) GameConfiguration.getInstance().WORLD_HEIGHT / 2 - playerShip.getHitBox().y - playerShip.getHitBox().height;

            if (Gdx.input.isKeyPressed(playerShip.getPlayer().getSecondaryKey())) {
                playerShip.fireSecondaryProjectiles(projectileList);
            }

            if (Gdx.input.isKeyPressed(playerShip.getPlayer().getRightKey()) && rightLimit > 0) {
                float movement = Math.min(playerShip.getMovementSpeed() * deltaTime, rightLimit);
                playerShip.translate(movement, 0f);
                if (checkShipCollision(playerShip, playerShipList))
                    playerShip.translate(-movement, 0f);
            }

            if (Gdx.input.isKeyPressed(playerShip.getPlayer().getUpKey()) && upLimit > 0) {
                float movement = Math.min(playerShip.getMovementSpeed() * deltaTime, upLimit);
                playerShip.translate(0f, movement);
                if (checkShipCollision(playerShip, playerShipList))
                    playerShip.translate(0f, -movement);
            }

            if (Gdx.input.isKeyPressed(playerShip.getPlayer().getLeftKey()) && leftLimit < 0) {
                float movement = Math.max(-playerShip.getMovementSpeed() * deltaTime, leftLimit);
                playerShip.translate(movement, 0f);
                if (checkShipCollision(playerShip, playerShipList))
                    playerShip.translate(-movement, 0f);
            }
            if (Gdx.input.isKeyPressed(playerShip.getPlayer().getDownKey()) && downLimit < 0) {
                float movement = Math.max(-playerShip.getMovementSpeed() * deltaTime, downLimit);
                playerShip.translate(0f, movement);
                if (checkShipCollision(playerShip, playerShipList))
                    playerShip.translate(0f, -movement);
            }
        }
    }

    private boolean checkShipCollision(PlayerShip targetPlayerShip, ArrayList<PlayerShip> playerShipList) {
        for (PlayerShip playerShip: playerShipList) {
            if (playerShip != targetPlayerShip && playerShip.intersects(targetPlayerShip.getHitBox())) {
                return true;
            }
        }
        return false;
    }

    private void detectCollisions() {

        ListIterator<PlayerShip> playerShipListIterator;
        ListIterator<EnemyShip> enemyShipListIterator;

        // for each player's laser, check if it intersects with enemy ship
        ListIterator<Projectile> projectileListIterator = projectileList.listIterator();
        while(projectileListIterator.hasNext()) {
            Projectile projectile = projectileListIterator.next();
            if (projectileOutOfBound(projectile)) continue;

            if (projectile.isFriendly()) {
                enemyShipListIterator = enemyShipList.listIterator();
                while (enemyShipListIterator.hasNext()) {
                    EnemyShip enemyShip = enemyShipListIterator.next();
                    if (enemyShip.intersects(projectile.getHitBox())) {
                        //Contact with enemy ship
                        if (enemyShip.hitAndCheckDestroyed(projectile))
                        {
                            dropPowerUps(enemyShip.getHitBox());
                            enemyShipListIterator.remove();

                            addExplosionEffect(enemyShip.getHitBox(), enemyShip.getHitBox().width * 2f);

                            // Check which ship shot the laser
                            for (PlayerShip playerShip: playerShipList) {
                                if (projectile.getPlayer() == playerShip.getPlayer().getId()) {
                                    playerShip.increaseScore(enemyShip.getScore());
                                }
                            }
                        }
                        else {
                            addExplosionEffect(projectile.getHitBox(), 5f);
                        }
                        projectileListIterator.remove();
                        break;
                    }
                }
            }
            else {
                playerShipListIterator = playerShipList.listIterator();
                while(playerShipListIterator.hasNext()) {
                    PlayerShip playerShip = playerShipListIterator.next();
                    if (playerShip.intersects(projectile.getHitBox())) {
                        //Contact with enemy ship
                        if(playerShip.hitAndCheckDestroyed(projectile)) {
                            addExplosionEffect(playerShip.getHitBox(), playerShip.getHitBox().height * 2f);
                            this.lives -= 1;
                            playerShip.getShield().setCurrentCapacity(3);
                        }
                        else {
                            addExplosionEffect(projectile.getHitBox(), 5f);
                        }
                        projectileListIterator.remove();
                    }
                }
            }
        }

        // Anti-Rocket Railgun mechanics
        for (Projectile projectileA : projectileList) {
            if (projectileA instanceof HomingRocket && !projectileA.isDestroyed()) {
                for (Projectile projectileB : projectileList) {
                    if (
                            projectileB instanceof TungstenSlug && !projectileB.isDestroyed() &&
                            projectileA.getHitBox().overlaps(projectileB.getHitBox()) &&
                            projectileA.isPlayerProjectile() != projectileB.isPlayerProjectile()
                    ) {
                        addExplosionEffect(projectileA.getHitBox(), projectileA.getHitBox().height);
                        projectileA.setDestroyed(true);
                        projectileB.setDestroyed(true);
                    }
                }
            }
        }

        projectileListIterator = projectileList.listIterator();
        while(projectileListIterator.hasNext()) {
            Projectile projectile = projectileListIterator.next();
            if (projectile.isDestroyed()) projectileListIterator.remove();
        }

        ListIterator<Pickup> powerUpListIterator = powerUpList.listIterator();
        while (powerUpListIterator.hasNext()) {
            Pickup powerUp = powerUpListIterator.next();
            for (PlayerShip playerShip : playerShipList) {
                if (playerShip.intersects(powerUp.getHitBox()) && powerUp.consumeByPlayer(playerShip)) {
                    addTextEffectForPickup(powerUp);
                    addSoundEffectForPickup(powerUp);
                    powerUpListIterator.remove();
                }
            }
        }
    }

    private void dropPowerUps(Rectangle hitBox) {
        int chance = GameConfiguration.random.nextInt(10);

        if (chance >= 5) return;

        float centerX = hitBox.getX() + hitBox.getWidth()/2;
        float centerY = hitBox.getY() + hitBox.getHeight()/2;

        int index = GameConfiguration.random.nextInt(playerList.size());
        Player player = playerShipList.get(index).getPlayer();

        if (chance < 2) {
            if (chance < 1)
                powerUpList.add(new ShieldPickup(new Rectangle(centerX, centerY, 4, 4), player));
            else
                powerUpList.add(new RocketPickup(new Rectangle(centerX, centerY, 5, 5), player));
        }
        else {
            powerUpList.add(new RailgunAmmoPickup(new Rectangle(centerX, centerY, 4, 4), player));
        }


    }

    public Boolean isGameOver(){
        return this.lives < 1;
    }

    public int getLives() {
        return this.lives;
    }

    public float getTime() {
        return this.time;
    }

    public List<Player> getPlayers() {
        return playerList;
    }

    public List<PlayerShip> getPlayerShipList() {
        return playerShipList;
    }

    public List<EnemyShip> getEnemyShipList() {
        return enemyShipList;
    }

    public List<GameEffect> getGameEffectList() {
        return gameEffectList;
    }

    public List<Pickup> getPowerUpList() {
        return powerUpList;
    }

    public ArrayList<Projectile> getProjectileList() {
        return projectileList;
    }

    private void addExplosionEffect(Rectangle rect, float size) {
        Vector2 position = rect.getCenter(new Vector2());
        gameEffectList.add(new ExplosionEffect(
                TextureConfiguration.getInstance().TEXTURE_EXPLOSION,
                new Rectangle(position.x, position.y, size, size),
                0.8f));
    }

    private void addTextEffectForPickup(Pickup pickup) {
        if (!pickup.hasTextForDisplayUponPickup()) return;

        // Add text effects
        Vector2 position = pickup.getHitBox().getCenter(new Vector2());
        gameEffectList.add(new TextEffect(
                new Rectangle(
                        position.x,
                        position.y + (pickup.getHitBox().getHeight()/2),
                        pickup.getHitBox().getWidth(),
                        pickup.getHitBox().getHeight()),
                1.5f,
                pickup.getTextForDisplayUponPickup(),
                0.25f,
                pickup.isNegativeEffect()));
    }

    private void addSoundEffectForPickup(Pickup pickup) {
        if (pickup.isNegativeEffect()) AudioConfiguration.getInstance().AUDIO_PICKUP_NEGATIVE.play(0.3f);
        else AudioConfiguration.getInstance().AUDIO_PICKUP_POSITIVE.play(0.3f);
    }
}
