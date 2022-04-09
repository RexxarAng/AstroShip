package com.spaceshooter.pickup;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.spaceshooter.Player;
import com.spaceshooter.common.Drawable;
import com.spaceshooter.configuration.GameConfiguration;
import com.spaceshooter.configuration.TextureConfiguration;
import com.spaceshooter.effect.TextEffect;
import com.spaceshooter.objects.GameObject;
import com.spaceshooter.ship.PlayerShip;

public abstract class Pickup extends GameObject implements Drawable {

    protected Player player;
    protected String textForDisplayUponPickup;
    protected boolean isNegativeEffect;

    protected Pickup(Rectangle hitBox, TextureRegion textureRegion, Player player) {
        super(hitBox, textureRegion);
        this.player = player;
    }

    @Override
    public void draw(Batch batch) {
        TextureRegion background;
        if (player.getId() == GameConfiguration.getInstance().PLAYER_1)
            background = TextureConfiguration.getInstance().TEXTURE_POWERUP_PLAYER1_BACKGROUND;
        else
            background  = TextureConfiguration.getInstance().TEXTURE_POWERUP_PLAYER2_BACKGROUND;
        batch.draw(
                background,
                hitBox.x, hitBox.y,
                hitBox.width, hitBox.height);

        batch.draw(textureRegion, hitBox.x, hitBox.y, hitBox.width, hitBox.height);
    }

    @Override
    public void update(float deltaTime) {
        this.translate(0, -5f * deltaTime);
    }

    public abstract boolean consumeByPlayer(PlayerShip playerShip);

    public boolean hasTextForDisplayUponPickup() {
        return this.textForDisplayUponPickup != null && !this.textForDisplayUponPickup.isEmpty();
    }

    public String getTextForDisplayUponPickup() {
        return this.textForDisplayUponPickup;
    }

    public boolean isNegativeEffect() {
        return this.isNegativeEffect;
    }

    protected void setTextForDisplayUponPickup(String value) {
        this.textForDisplayUponPickup = value;
    }

    protected void setIsNegativeEffect(boolean isNegativeEffect) {
        this.isNegativeEffect = isNegativeEffect;
    }

    protected void translate(float xChange, float yChange) {
        hitBox.setPosition(hitBox.x+xChange, hitBox.y+yChange);
    }
}
