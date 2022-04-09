package com.spaceshooter.effect;

import com.badlogic.gdx.math.Rectangle;
import com.spaceshooter.common.Drawable;

public abstract class GameEffect implements Drawable {

    protected final Rectangle hitBox;
    protected float tick;
    protected float timeOnScreen;

    protected GameEffect(Rectangle hitBox, float timeOnScreen) {
        this.hitBox = hitBox;
        this.timeOnScreen = timeOnScreen;

        tick = 0;
    }

    public void update(float deltaTime) {
        tick += deltaTime;
    }

    public boolean isFinished() {
        return this.tick >= this.timeOnScreen;
    }


}
