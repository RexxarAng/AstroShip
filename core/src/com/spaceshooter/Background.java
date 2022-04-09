package com.spaceshooter;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.spaceshooter.configuration.TextureConfiguration;

public class Background {
    // Graphics
    private final TextureRegion[] backgrounds;

    // Dimensions
    private final int width;
    private final int height;

    // Timing
    private final float[] backgroundOffsets;
    private final float backgroundMaxScrollingSpeed;

    public Background(int width, int height) {
        //Set up background
        this.width = width;
        this.height = height;

        //Initialize texture regions
        backgroundOffsets = new float[4];
        backgrounds = new TextureRegion[4];
        backgrounds[0] = TextureConfiguration.getInstance().TEXTURE_BACKGROUND_STARSCAPE0;
        backgrounds[1] = TextureConfiguration.getInstance().TEXTURE_BACKGROUND_STARSCAPE1;
        backgrounds[2] = TextureConfiguration.getInstance().TEXTURE_BACKGROUND_STARSCAPE2;
        backgrounds[3] = TextureConfiguration.getInstance().TEXTURE_BACKGROUND_STARSCAPE3;

        backgroundMaxScrollingSpeed = (float)(height / 4);
    }

    public void renderBackground(Batch batch) {
        for (int layer = 0; layer < backgroundOffsets.length; layer++) {
            if (backgroundOffsets[layer] > height) {
                backgroundOffsets[layer] = 0;
            }
            batch.draw(backgrounds[layer], 0, -backgroundOffsets[layer], width, height);
            batch.draw(backgrounds[layer], 0, -backgroundOffsets[layer] + height, width, height);
        }
    }

    public void renderBackground(Batch batch, float deltaTime) {
        backgroundOffsets[0] += deltaTime * backgroundMaxScrollingSpeed / 8;
        backgroundOffsets[1] += deltaTime * backgroundMaxScrollingSpeed / 4;
        backgroundOffsets[2] += deltaTime * backgroundMaxScrollingSpeed / 2;
        backgroundOffsets[3] += deltaTime * backgroundMaxScrollingSpeed;
        for (int layer = 0; layer < backgroundOffsets.length; layer++) {
            if (backgroundOffsets[layer] > height) {
                backgroundOffsets[layer] = 0;
            }
            batch.draw(backgrounds[layer], 0, -backgroundOffsets[layer], width, height);
            batch.draw(backgrounds[layer], 0, -backgroundOffsets[layer] + height, width, height);

        }
    }

}
