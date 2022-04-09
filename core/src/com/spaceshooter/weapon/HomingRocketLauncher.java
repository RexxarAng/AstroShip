package com.spaceshooter.weapon;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.spaceshooter.configuration.GameConfiguration;
import com.spaceshooter.configuration.TextureConfiguration;

import java.util.ArrayList;

public class HomingRocketLauncher extends Weapon {

    public HomingRocketLauncher() {
        this.setDamage(2);
        this.setTimeBetweenShots(0.75f);
        this.projectileMovementSpeed = GameConfiguration.getInstance().DEFAULT_HOMING_MOVEMENT_SPEED;
    }

    @Override
    public void fire(Rectangle shipHitBox, int player, ArrayList<Projectile> projectileList) {
        if (this.canFire()) {
            TextureRegion laserTexture;
            if (player == GameConfiguration.getInstance().PLAYER_1)
                laserTexture = TextureConfiguration.getInstance().TEXTURE_PROJECTILE_ROCKET_FRIENDLY_BLUE;
            else if (player == GameConfiguration.getInstance().PLAYER_2)
                laserTexture = TextureConfiguration.getInstance().TEXTURE_PROJECTILE_ROCKET_FRIENDLY_GREEN;
            else
                laserTexture = TextureConfiguration.getInstance().TEXTURE_PROJECTILE_ROCKET_NOT_FRIENDLY;

            Projectile projectile = new HomingRocket(shipHitBox.x + shipHitBox.width / 2,
                    shipHitBox.y + shipHitBox.height * 0.52f,
                    GameConfiguration.getInstance().DEFAULT_ROCKET_WIDTH,
                    shipHitBox.height,
                    this.projectileMovementSpeed,
                    this.damage,
                    player,
                    laserTexture);
            this.setTimeSinceLastShot(0);
            projectileList.add(projectile);
        }
    }
}
