package com.spaceshooter.effect;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Align;

public class TextEffect extends GameEffect {

    protected String text;
    protected BitmapFont textFont;

    public TextEffect(Rectangle hitBox, float timeOnScreen, String text, float fontSize, boolean isNegative) {
        super(hitBox, timeOnScreen);

        this.text = text;
        this.textFont = new BitmapFont();
        this.textFont.setUseIntegerPositions(false);
        this.textFont.getData().setScale(fontSize);
        if (!isNegative)
            this.textFont.setColor(Color.valueOf("#33ff33"));
        else
            this.textFont.setColor(Color.valueOf("#FF0000"));

    }

    @Override
    public void draw(Batch batch) {
        this.textFont.draw(batch,
                this.text,
                this.hitBox.x, this.hitBox.y + (int) (textFont.getCapHeight()),
                this.hitBox.width,
                Align.center,
                false);
    }


}
