package com.spaceshooter.weapon;

import com.badlogic.gdx.math.Rectangle;
import com.spaceshooter.configuration.GameConfiguration;

import java.util.ArrayList;

public abstract class Weapon {
    protected float timeBetweenShots;
    protected float timeSinceLastShot;
    protected int damage;
    protected float projectileMovementSpeed;

    public Weapon() {
        timeBetweenShots = GameConfiguration.getInstance().DEFAULT_TIME_BETWEEN_SHOTS;
        projectileMovementSpeed = GameConfiguration.getInstance().DEFAULT_PROJECTILE_MOVEMENT_SPEED;
        damage = 1;
    }

    public abstract void fire(Rectangle shipHitBox, int player, ArrayList<Projectile> projectileList);

    public void update(float deltaTime) {
        timeSinceLastShot += deltaTime;
    }

    public boolean canFire() {
        return (timeSinceLastShot - timeBetweenShots > 0);
    }

    public float getTimeBetweenShots() {
        return timeBetweenShots;
    }

    public void setTimeBetweenShots(float timeBetweenShots) {
        this.timeBetweenShots = timeBetweenShots;
    }

    public float getTimeSinceLastShot() {
        return timeSinceLastShot;
    }

    public void setTimeSinceLastShot(float timeSinceLastShot) {
        this.timeSinceLastShot = timeSinceLastShot;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public float getProjectileMovementSpeed() {
        return projectileMovementSpeed;
    }

    public void setProjectileMovementSpeed(float projectileMovementSpeed) {
        this.projectileMovementSpeed = projectileMovementSpeed;
    }
}
