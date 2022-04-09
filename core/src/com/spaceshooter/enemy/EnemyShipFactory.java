package com.spaceshooter.enemy;
import com.spaceshooter.Score;
import com.spaceshooter.configuration.GameConfiguration;
import com.spaceshooter.ship.EnemyShip;
import com.spaceshooter.weapon.HomingRocketLauncher;
import com.spaceshooter.weapon.Weapon;
import com.spaceshooter.common.AbstractFactory;
import com.spaceshooter.configuration.TextureConfiguration;

public class EnemyShipFactory implements AbstractFactory<EnemyShip> {

    public static final String LASER_ENEMY = "LASER_ENEMY";
    public static final String ROCKET_ENEMY = "ROCKET_ENEMY";

    @Override
    public EnemyShip create(String enemyShipType) {
        return create(enemyShipType, 1);
    }

    public EnemyShip create(String enemyShipType, int level) {

        int movementSpeed = GameConfiguration.getInstance().DEFAULT_MAX_ENEMY_MOVEMENT_SPEED;
        int shieldHitPoint = GameConfiguration.getInstance().DEFAULT_ENEMY_SHIELD;
        int scorePoints = GameConfiguration.getInstance().DEFAULT_SCORE_POINTS;

        Score score = new Score(scorePoints);
        movementSpeed += movementSpeed * (level * 0.1);
        shieldHitPoint += level - 1;
        score.setScore(scorePoints * level * 2);

        // Get random ship color
        int index = GameConfiguration.random.nextInt(TextureConfiguration.getInstance().TEXTURE_ENEMY_SHIP_TYPE1_LIST.size());
        EnemyShip enemyShip = new EnemyShip(
                level,
                0 , 0,
                GameConfiguration.getInstance().CURRENT_ENEMY_SHIP_WIDTH,
                GameConfiguration.getInstance().CURRENT_ENEMY_SHIP_HEIGHT,
                movementSpeed,
                shieldHitPoint,
                score,
                TextureConfiguration.getInstance().TEXTURE_ENEMY_SHIP_TYPE1_LIST.get(index));
        enemyShip.setDirectionChangeFrequency(GameConfiguration.getInstance().DEFAULT_ENEMY_DIRECTION_CHANGE_FREQUENCY);

        if (LASER_ENEMY.equalsIgnoreCase(enemyShipType)) {
            changeToLaserWeapon(enemyShip);
            return enemyShip;
        }
        else if (ROCKET_ENEMY.equals(enemyShipType)) {
            enemyShip.setTextureRegion(TextureConfiguration.getInstance().TEXTURE_ENEMY_SHIP_TYPE2_LIST.get(index));
            changeToRocketWeapon(enemyShip);
            return enemyShip;
        }

        return null;
    }

    private void changeToLaserWeapon(EnemyShip enemyShip) {
        Weapon weapon = enemyShip.getWeapon();
        weapon.setDamage(GameConfiguration.getInstance().DEFAULT_ENEMY_DAMAGE);
        weapon.setTimeBetweenShots(GameConfiguration.random.nextFloat() * GameConfiguration.getInstance().DEFAULT_MAX_ENEMY_TIME_BETWEEN_SHOTS + 0.5f);
        weapon.setProjectileMovementSpeed(GameConfiguration.getInstance().DEFAULT_ENEMY_PROJECTILE_SPEED);
    }

    public void changeToRocketWeapon(EnemyShip enemyShip) {
        enemyShip.setWeapon(new HomingRocketLauncher());
        Weapon weapon = enemyShip.getWeapon();
        weapon.setDamage(GameConfiguration.getInstance().DEFAULT_ENEMY_DAMAGE);
        weapon.setTimeBetweenShots(GameConfiguration.random.nextFloat() * GameConfiguration.getInstance().DEFAULT_MAX_ENEMY_TIME_BETWEEN_SHOTS + 0.5f);
        weapon.setProjectileMovementSpeed(GameConfiguration.getInstance().DEFAULT_ENEMY_PROJECTILE_SPEED);
    }

}
