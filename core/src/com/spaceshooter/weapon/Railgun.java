package com.spaceshooter.weapon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.spaceshooter.configuration.AudioConfiguration;
import com.spaceshooter.configuration.GameConfiguration;

import java.util.ArrayList;
import java.util.List;

public class Railgun extends Weapon {

    private int ammo;

    public Railgun() {
        ammo = 25;
        this.setDamage(1);
        this.setTimeBetweenShots(0.1f);
        this.projectileMovementSpeed = GameConfiguration.getInstance().DEFAULT_RAILGUN_MOVEMENT_SPEED;
    }

    @Override
    public void fire(Rectangle shipHitBox, int player, ArrayList<Projectile> projectileList) {
        if (this.canFire() && ammo > 0) {
            // Get direction by aiming at homing rocket
            Projectile targetProjectile = calculateTargetRocket(player, projectileList);
            if (targetProjectile == null) return;

            Vector2 targetDirectionVector = calculateDirectionVector(
                    shipHitBox.getCenter(new Vector2()),
                    targetProjectile.getHitBox().getCenter(new Vector2()));

            Projectile projectile = new TungstenSlug(
                    shipHitBox.getCenter(new Vector2()),
                    targetDirectionVector,
                    projectileMovementSpeed,
                    damage,
                    player);
            this.setTimeSinceLastShot(0);
            projectileList.add(projectile);
            ammo--;

            AudioConfiguration.getInstance().AUDIO_RAILGUN.play(0.5f);
        }
    }

    private Projectile calculateTargetRocket(int player, List<Projectile> projectileList) {
        // Return a rocket homing towards self
        for (Projectile projectile: projectileList) {
            if (
                    projectile instanceof HomingRocket &&
                    (player > 0 && !projectile.isFriendly() || player <= 0 && projectile.isFriendly())) {

                if (((HomingRocket)(projectile)).getTarget() == player)
                    return projectile;
            }
        }

        // If no rockets homing towards self, return other rocket
        for (Projectile projectile: projectileList) {
            if (
                    projectile instanceof HomingRocket &&
                            (player > 0 && !projectile.isFriendly() || player <= 0 && projectile.isFriendly())) {

                return projectile;
            }
        }

        // If no homing rockets, return null
        return null;
    }

    private Vector2 calculateDirectionVector(Vector2 currentPosition, Vector2 targetPosition) {
        Vector2 targetVector = new Vector2(targetPosition.x, targetPosition.y);
        targetVector.sub(new Vector2(currentPosition.x, currentPosition.y));
        targetVector.nor();
        return targetVector;
    }

    public int getAmmo() {
        return ammo;
    }

    public void addAmmo(int ammo) {
        this.ammo += ammo;
    }
}
