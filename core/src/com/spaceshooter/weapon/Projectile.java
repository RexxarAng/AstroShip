package com.spaceshooter.weapon;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.spaceshooter.objects.GameObject;
import com.spaceshooter.objects.IsDestroyable;

public abstract class Projectile extends GameObject implements IsDestroyable {
    protected float movementSpeed;
    protected int player; // Player 0 is enemy
    protected int damage;
    protected boolean isDestroyed;

    protected Projectile(float xCentre,
                      float yBottom,
                      float width,
                      float height,
                      float movementSpeed,
                      int damage,
                      int player,
                      TextureRegion textureRegion) {
        super(new Rectangle(xCentre - width / 2, yBottom, width, height), textureRegion);
        this.movementSpeed = movementSpeed;
        this.damage = damage;
        this.player = player;
    }

    @Override
    public void draw(Batch batch) {
        batch.draw(this.getTextureRegion(), hitBox.x, hitBox.y, hitBox.width, hitBox.height);
    }

    public void translate(float xChange, float yChange) {
        hitBox.setPosition(hitBox.x+xChange, hitBox.y+yChange);
    }

    public int getPlayer() {
        return player;
    }

    public boolean isPlayerProjectile() { return player != 0; }

    public boolean isFriendly() {
        return this.player > 0;
    }

    public int getDamage() {
        return damage;
    }

    @Override
    public void setDestroyed(boolean destroyed) {
        isDestroyed = destroyed;
    }

    @Override
    public boolean isDestroyed() {
        return isDestroyed;
    }
}
