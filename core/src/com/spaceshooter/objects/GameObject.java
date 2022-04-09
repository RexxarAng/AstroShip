package com.spaceshooter.objects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.spaceshooter.common.Drawable;

public abstract class GameObject implements Drawable {

    //Position & Dimensions
    protected Rectangle hitBox;

    protected TextureRegion textureRegion;

    public GameObject(Rectangle hitBox,
                      TextureRegion textureRegion) {
        this.hitBox = hitBox;
        this.textureRegion = textureRegion;
    }

    public abstract void update(float deltaTime);

    public Rectangle getHitBox() {
        return this.hitBox;
    }

    public TextureRegion getTextureRegion() {
        return this.textureRegion;
    }

    public void setTextureRegion(TextureRegion textureRegion) {
        this.textureRegion = textureRegion;
    }


}
