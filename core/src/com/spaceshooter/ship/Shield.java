package com.spaceshooter.ship;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.spaceshooter.objects.GameObject;

public class Shield extends GameObject {


    private int maxCapacity;
    private int currentCapacity;
    private boolean isRegenerative;
    private float regenShieldSpeed;
    private float timeSinceLastRegen;

    public Shield(Rectangle hitBox, TextureRegion textureRegion, int shieldPoints) {
        super(hitBox, textureRegion);
        this.isRegenerative = false;
        this.maxCapacity = shieldPoints;
        this.currentCapacity = shieldPoints;
        this.textureRegion = textureRegion;
    }

    public Shield(Rectangle hitBox, TextureRegion textureRegion, int shieldPoints, float regenShieldSpeed) {
        super(hitBox, textureRegion);
        this.isRegenerative = true;
        this.regenShieldSpeed = regenShieldSpeed;
        this.maxCapacity = shieldPoints;
        this.currentCapacity = shieldPoints;
        this.textureRegion = textureRegion;
    }

    @Override
    public void draw(Batch batch) {
    }

    public void update(float deltaTIme){
        // maybe regen shield
        if (!isRegenerative)
            return;
        if (currentCapacity < maxCapacity)
            timeSinceLastRegen += deltaTIme;
        if (timeSinceLastRegen > regenShieldSpeed) {
            currentCapacity += 1;
            timeSinceLastRegen = 0;
        }
    }

    public int getCurrentCapacity() {
        return currentCapacity;
    }

    public void setCurrentCapacity(int currentCapacity) {
        this.currentCapacity = currentCapacity;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public boolean isAtMaxCapacity() {
        return this.currentCapacity == this.maxCapacity;
    }

    public void increaseShield(int value) {
        this.currentCapacity = Math.min(this.maxCapacity, this.currentCapacity + value);
    }

    public int decreaseShield(int damage) {
        this.currentCapacity -= damage;
        this.timeSinceLastRegen = 0;
        return this.currentCapacity;
    }

    public void increaseShieldCapacity(int shieldPoints) {
        maxCapacity += shieldPoints;
    }
}
