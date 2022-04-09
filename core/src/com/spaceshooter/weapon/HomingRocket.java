package com.spaceshooter.weapon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.spaceshooter.configuration.AudioConfiguration;
import com.spaceshooter.configuration.GameConfiguration;
import com.spaceshooter.configuration.TextureConfiguration;
import com.spaceshooter.ship.EnemyShip;
import com.spaceshooter.ship.PlayerShip;
import com.spaceshooter.ship.Ship;

import java.util.ArrayList;
import java.util.List;

public class HomingRocket extends Projectile {

    private Ship target;
    private final Vector2 currentDirectionVector;
    private final float homingRotationSpeed;
    private final float trackingDuration;
    private float trackingTime;
    private final float accelerationSpeed;

    public HomingRocket(float xCentre, float yBottom, float width, float height, float movementSpeed, int damage, int player, TextureRegion textureRegion) {
        super(xCentre, yBottom, width, height, movementSpeed, damage, player, textureRegion);

        int yDirection = 1;
        if (!isPlayerProjectile()) yDirection = -1;
        currentDirectionVector = new Vector2(0, yDirection);
        accelerationSpeed = GameConfiguration.getInstance().DEFAULT_HOMING_ACCELERATION_SPEED;
        homingRotationSpeed = GameConfiguration.getInstance().DEFAULT_HOMING_ROTATION_SPEED;
        trackingDuration = GameConfiguration.getInstance().DEFAULT_HOMING_TRACKING_DURATION;
    }

    public void updateTarget(ArrayList<PlayerShip> playerShips, ArrayList<EnemyShip> enemyShips) {
        if (target != null && !target.isDestroyed()) {
            return;
        }

        target = null;

        List<Ship> targetShips;
        if (isPlayerProjectile()) targetShips = (ArrayList<Ship>)((ArrayList<?>) enemyShips);
        else targetShips = (ArrayList<Ship>)((ArrayList<?>) playerShips);

        if (targetShips != null && !targetShips.isEmpty()) {
            target = targetShips.get(GameConfiguration.random.nextInt(targetShips.size()));
        }
    }

    @Override
    public void draw(Batch batch) {
        int rotateClockwise = 1;
        if (isPlayerProjectile())
            rotateClockwise = -1;

        if (isHoming()) {
            TextureRegion trackingIndicationTexture;
            if (player == GameConfiguration.getInstance().PLAYER_1)
                trackingIndicationTexture = TextureConfiguration.getInstance().TEXTURE_PROJECTILE_ROCKET_FIRE_FRIENDLY_BLUE;
            else if (player == GameConfiguration.getInstance().PLAYER_2)
                trackingIndicationTexture = TextureConfiguration.getInstance().TEXTURE_PROJECTILE_ROCKET_FIRE_FRIENDLY_GREEN;
            else
                trackingIndicationTexture = TextureConfiguration.getInstance().TEXTURE_PROJECTILE_ROCKET_FIRE_NOT_FRIENDLY;
            batch.draw(trackingIndicationTexture,
                    hitBox.x,
                    hitBox.y,
                    hitBox.width/2,
                    hitBox.height/2,
                    hitBox.width, hitBox.height,
                    1,
                    1,
                    currentDirectionVector.angleDeg()+rotateClockwise*90);

        }

        batch.draw(this.getTextureRegion(),
                hitBox.x,
                hitBox.y,
                hitBox.width/2,
                hitBox.height/2,
                hitBox.width, hitBox.height,
                1,
                1,
                currentDirectionVector.angleDeg()+rotateClockwise*90);
    }

    @Override
    public void update(float deltaTime) {
        trackingTime += deltaTime;
        if (isHoming()) {
            // Accelerate when homing at target
            movementSpeed += accelerationSpeed * deltaTime;

            // Homes toward the target
            Vector2 targetVector = new Vector2(target.getHitBox().x, target.getHitBox().y);
            targetVector.sub(new Vector2(this.hitBox.x, this.hitBox.y));
            targetVector.nor();

            float curDegree = currentDirectionVector.angleDeg();
            float desDegree = targetVector.angleDeg();

            // Get the rotation
            float deltaDegree = desDegree - curDegree;

            // Get the closest rotation clockwise or anticlockwise in + or - respectively
            int rotateClockwise = 1;
            if (deltaDegree < 0) rotateClockwise = -1;
            if (Math.abs(deltaDegree) > 180) rotateClockwise *= -1;

            // Calculate the angle based on the rotation speed
            float angleToRotate = Math.min(Math.abs(deltaDegree), homingRotationSpeed) * rotateClockwise;
            currentDirectionVector.rotateDeg(angleToRotate);
        }
        this.translate(
                currentDirectionVector.x * this.movementSpeed * deltaTime,
                currentDirectionVector.y * this.movementSpeed * deltaTime);
    }

    private boolean isHoming() {
        return target != null && trackingTime < trackingDuration;
    }

    public int getTarget() {
        if (target instanceof EnemyShip) {
           return 0;
        }
        PlayerShip playerShip = (PlayerShip)target;
        return ((PlayerShip) target).getPlayer().getId();
    }

}
