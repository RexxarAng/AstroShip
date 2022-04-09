package com.spaceshooter.weapon;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.spaceshooter.configuration.GameConfiguration;
import com.spaceshooter.configuration.TextureConfiguration;

import java.util.ArrayList;

public class Blaster extends Weapon {

    public Blaster() {
        this.setDamage(2);
        this.setTimeBetweenShots(0.25f);
        this.projectileMovementSpeed = 100.0F;
    }

    @Override
    public void fire(Rectangle shipHitBox, int player, ArrayList<Projectile> projectileList) {
        if (this.canFire()) {
            TextureRegion laserTexture;
            if (player == GameConfiguration.getInstance().PLAYER_1)
                laserTexture = TextureConfiguration.getInstance().TEXTURE_PLAYER1_LASER;
            else if (player == GameConfiguration.getInstance().PLAYER_2)
                laserTexture = TextureConfiguration.getInstance().TEXTURE_PLAYER2_LASER;
            else
                laserTexture = TextureConfiguration.getInstance().TEXTURE_PROJECTILE_LASER_NOT_FRIENDLY;

//            Projectile[] projectiles = new Laser[2];
//            projectiles[0] = new Laser(shipHitBox.x + shipHitBox.width * 0.07f,
//                    shipHitBox.y + shipHitBox.height * 0.52f,
//                    0.4f,
//                    4f,
//                    this.projectileMovementSpeed,
//                    this.damage,
//                    player,
//                    laserTexture);
//            projectiles[1] = new Laser(shipHitBox.x + shipHitBox.width * 0.93f,
//                    shipHitBox.y + shipHitBox.height * 0.52f,
//                    0.4f,
//                    4f,
//                    this.projectileMovementSpeed,
//                    this.damage,
//                    player,
//                    laserTexture);
            Projectile projectile = new Laser(shipHitBox.x + shipHitBox.width / 2,
                    shipHitBox.y + shipHitBox.height * 0.52f,
                    GameConfiguration.getInstance().DEFAULT_LASER_WIDTH,
                    shipHitBox.height,
                    this.projectileMovementSpeed,
                    this.damage,
                    player,
                    laserTexture);
            this.setTimeSinceLastShot(0);
            projectileList.add(projectile);

//            for (Projectile projectile: projectiles)
//                projectileList.add(projectile);
        }
    }
}
