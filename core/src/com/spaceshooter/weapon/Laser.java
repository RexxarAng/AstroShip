package com.spaceshooter.weapon;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Laser extends Projectile {

    public Laser(float xCentre, float yBottom, float width, float height, float movementSpeed, int damage, int player, TextureRegion textureRegion) {
        super(xCentre, yBottom, width, height, movementSpeed, damage, player, textureRegion);
    }

    @Override
    public void update(float deltaTime) {
        // Enemy's projectile travel downwards
        if (player == 0) {
            this.translate(0, -this.movementSpeed*deltaTime);
        } else {
            this.translate(0, this.movementSpeed*deltaTime);
        }
    }

}
