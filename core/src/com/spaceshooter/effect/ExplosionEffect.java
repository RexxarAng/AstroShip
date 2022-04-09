package com.spaceshooter.effect;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.spaceshooter.configuration.AudioConfiguration;

public class ExplosionEffect extends GameEffect {

    private final Animation<TextureRegion> explosionAnimation;

    public ExplosionEffect(Texture texture, Rectangle hitBox, float totalAnimationTime) {
        super(hitBox, totalAnimationTime);

        //split texture
        TextureRegion[][] textureRegion2D = TextureRegion.split(texture, 64, 64);

        //Convert to 1D array
        TextureRegion[] textureRegion1D = new TextureRegion[16];
        int index = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                textureRegion1D[index] = textureRegion2D[i][j];
                index++;
            }
        }

        explosionAnimation = new Animation<>(totalAnimationTime/16, textureRegion1D);

        AudioConfiguration.getInstance().AUDIO_EXPLOSION.play();
    }

    @Override
    public void draw(Batch batch) {
        batch.draw(explosionAnimation.getKeyFrame(this.tick), hitBox.x, hitBox.y, hitBox.width, hitBox.height);
    }

    @Override
    public boolean isFinished() {
        return explosionAnimation.isAnimationFinished(this.tick);
    }


}
