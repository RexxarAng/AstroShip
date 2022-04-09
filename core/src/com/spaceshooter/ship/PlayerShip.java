package com.spaceshooter.ship;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.spaceshooter.Player;
import com.spaceshooter.configuration.GameConfiguration;
import com.spaceshooter.configuration.TextureConfiguration;
import com.spaceshooter.weapon.Blaster;
import com.spaceshooter.weapon.Projectile;
import com.spaceshooter.weapon.Railgun;
import com.spaceshooter.weapon.Weapon;

import java.util.ArrayList;


public class PlayerShip extends Ship {

    private final Player player;

    private final Weapon railGun;

    public PlayerShip(float xCentre,
                      float yCentre,
                      float width,
                      float height,
                      Player player,
                      float movementSpeed,
                      int shield,
                      TextureRegion shipTextureRegion) {
        super(xCentre, yCentre, width, height, movementSpeed, shipTextureRegion);
        this.setWeapon(new Blaster());
        this.railGun = new Railgun();
        this.shield = new Shield(this.hitBox, TextureConfiguration.getInstance().TEXTURE_PLAYER_SHIELD, shield, GameConfiguration.getInstance().DEFAULT_PLAYER_SHIELD_REGEN_SPEED);
        this.player = player;
        this.isFaceUpward = true;
    }

    @Override
    public void fireProjectiles(ArrayList<Projectile> projectileList) {
        this.getWeapon().fire(this.getHitBox(), this.getPlayer().getId(), projectileList);
    }

    @Override
    public void draw(Batch batch) {
        super.draw(batch);
        drawAmmoIndicator(batch);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        this.railGun.update(deltaTime);
    }

    public void fireSecondaryProjectiles(ArrayList<Projectile> projectileList) {
        railGun.fire(this.getHitBox(), this.getPlayer().getId(), projectileList);
    }

    public int getScore() {
        return player.getScore();
    }

    public void increaseScore(int score) {
        this.player.increaseScore(score);
    }

    public Player getPlayer() {
        return player;
    }

    public Weapon getRailGun() {
        return railGun;
    }

    private void drawAmmoLeft(Batch batch) {
        final float spacing = 0.1f;
        final int ammoSize = 1;
        final double yellowPerAmmo = 5.0;

        final int totalAmmo = ((Railgun)this.railGun).getAmmo();
        final int totalIndicator = (int) Math.ceil(totalAmmo / yellowPerAmmo);
        final int totalYellow = (int) (totalAmmo / yellowPerAmmo);

        final float totalWidth = totalIndicator * (ammoSize + spacing) - spacing;

        float centerX = this.getHitBox().x + this.getHitBox().width/2f;
        float bottomY = this.getHitBox().y - this.getHitBox().height - 2f -2f;

        float startX = centerX - totalWidth/2;

        for (int i=0; i < totalIndicator; i++) {
            TextureRegion texture;
            if (i < totalYellow) texture = TextureConfiguration.getInstance().TEXTURE_PLAYER_AMMO;
            else texture = TextureConfiguration.getInstance().TEXTURE_PLAYER_AMMO_HALF;

            batch.draw(
                    texture,
                    startX + i * (ammoSize + spacing),
                    bottomY,
                    ammoSize,
                    ammoSize);
        }
    }


    private void drawAmmoIndicator(Batch batch) {
        drawAmmoLeft(batch);
        batch.draw(this.getTextureRegion(), this.getHitBox().x, this.getHitBox().y, this.getHitBox().width, this.getHitBox().height);
        if (this.shield.getCurrentCapacity() > 0) {
            batch.draw(this.shield.getTextureRegion(),
                    this.getHitBox().x,
                    this.getHitBox().y+this.getHitBox().height*0.2f,
                    this.getHitBox().width,
                    this.getHitBox().height);
        }
    }
}
