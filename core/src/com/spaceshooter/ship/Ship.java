package com.spaceshooter.ship;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.spaceshooter.configuration.TextureConfiguration;
import com.spaceshooter.objects.*;
import com.spaceshooter.weapon.Blaster;
import com.spaceshooter.weapon.Projectile;
import com.spaceshooter.weapon.Weapon;

import java.util.ArrayList;

public abstract class Ship extends GameObject {

    //Ship Characteristics
    private float movementSpeed; // world units per second
    private float attackSpeed;
    protected Weapon weapon;
    protected Shield shield;
    protected boolean isFaceUpward;

    public Ship(float xCentre,
                float yCentre,
                float width,
                float height,
                float movementSpeed,
                TextureRegion textureRegion) {
        super(new Rectangle(xCentre - width / 2, yCentre - height / 2, width, height), textureRegion);
        this.movementSpeed = movementSpeed;
        this.weapon = new Blaster();
    }

    @Override
    public void draw(Batch batch) {
        drawShieldIndicator(batch);
    }

    @Override
    public void update(float deltaTime) {
        this.weapon.update(deltaTime);
        this.shield.update(deltaTime);
    }

    public void translate(float xChange, float yChange) {
        hitBox.setPosition(hitBox.x+xChange, hitBox.y+yChange);
    }

    public abstract void fireProjectiles(ArrayList<Projectile> projectileList);

    public boolean intersects(Rectangle objectHitBox) {
        return hitBox.overlaps(objectHitBox);
    }

    public boolean hitAndCheckDestroyed(Projectile projectile) {
        return shield.decreaseShield(projectile.getDamage()) < 0;
    }

    public boolean isDestroyed() {
        return shield.getCurrentCapacity() < 0;
    }

    public float getMovementSpeed() {
        return movementSpeed;
    }

    public void setMovementSpeed(float movementSpeed) {
        this.movementSpeed = movementSpeed;
    }

    public float getAttackSpeed() {
        return attackSpeed;
    }

    public void setAttackSpeed(float attackSpeed) {
        this.attackSpeed = attackSpeed;
    }

    public Shield getShield() {
        return shield;
    }

    public void setShield(Shield shield) {
        this.shield = shield;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    protected void drawShieldIndicator(Batch batch) {
        final float spacing = 0.3f;
        final int size = 1;

        final int totalShield = this.shield.getMaxCapacity();
        final int currentShield = this.shield.getCurrentCapacity();


        final float totalWidth = totalShield * (size + spacing) - spacing;

        float centerX = this.getHitBox().x + this.getHitBox().width/2f;

        float y = this.getHitBox().y;
        if (isFaceUpward) y -= this.getHitBox().height + 2f;
        else y += this.getHitBox().height + 2f;

        float startX = centerX - totalWidth/2;

        for (int i=0; i < totalShield; i++) {
            TextureRegion texture;
            if (i < currentShield)
                texture = TextureConfiguration.getInstance().TEXTURE_PLAYER_SHIELD_INDICATOR;
            else
                texture = TextureConfiguration.getInstance().TEXTURE_PLAYER_SHIELD_EMPTY_INDICATOR;

            batch.draw(
                    texture,
                    startX + i * (size + spacing),
                    y,
                    size,
                    size);
        }
    }
}
