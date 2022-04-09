package com.spaceshooter.weapon;

import com.badlogic.gdx.math.Vector2;
import com.spaceshooter.configuration.TextureConfiguration;

public class TungstenSlug extends Projectile {

    private Vector2 currentDirectionVector;

    public TungstenSlug(Vector2 position, Vector2 directionVector, float movementSpeed, int damage, int player) {
        super(
                position.x, position.y,
                4, 4,
                movementSpeed,
                damage,
                player,
                TextureConfiguration.getInstance().TEXTURE_PROJECTILE_TUNGSTEN_SLUG);
        this.currentDirectionVector = directionVector;
    }

    @Override
    public void update(float deltaTime) {
        this.translate(
                currentDirectionVector.x * this.movementSpeed * deltaTime,
                currentDirectionVector.y * this.movementSpeed * deltaTime);
    }

}
