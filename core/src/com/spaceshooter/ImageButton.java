package com.spaceshooter;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.spaceshooter.common.Drawable;
import com.spaceshooter.configuration.AudioConfiguration;
import com.spaceshooter.configuration.GameConfiguration;


public class ImageButton implements Drawable {
    private final String name;
    private final Rectangle hitBox;
    private final TextureRegion inactiveTextureRegion;
    private final TextureRegion activeTextureRegion;
    private boolean isActive;
    private boolean hasPlayForActive;

    public ImageButton(String name, Rectangle hitBox, TextureRegion inactiveTextureRegion, TextureRegion activeTextureRegion) {
        this.hitBox = hitBox;
        this.inactiveTextureRegion = inactiveTextureRegion;
        this.name = name;
        this.activeTextureRegion = activeTextureRegion;
    }

    @Override
    public void draw(Batch batch) {
        if (isActive)
            batch.draw(activeTextureRegion, this.hitBox.x, this.hitBox.y, this.hitBox.width, this.hitBox.height);
        else
            batch.draw(inactiveTextureRegion, this.hitBox.x, this.hitBox.y, this.hitBox.width, this.hitBox.height);

        playSound();
        if (!isActive) hasPlayForActive = false; // restart for next first active
    }

    public void onHover(int x, float y) {
        // Gdx input returns coordinate from top of screen
        y = SpaceShooterGame.HEIGHT - y;
        isActive = this.hitBox.contains(x, y);
    }
    //temp method specifically for game-screen pause screen
    public boolean onHoverPause(float mouseX, float mouseY){
        //Cords and values of the button on the pause screen is computed differently
        // due to its calculation using world size instead of game size.
        mouseY = SpaceShooterGame.HEIGHT - mouseY;
        //debuggers to check items
        //System.out.println("Mouse X: " + mouseX + " Mouse Y: " + mouseY);
        //System.out.println("Rec X:" + this.hitBox.x + " Rec Y: " + this.hitBox.y);
        //System.out.println("Rec width: " + this.hitBox.width + " Rec height: " + this.hitBox.height);
        //checking if x mouse in x cord
        if ((mouseX >= this.hitBox.x*7)&&(mouseX <= this.hitBox.x*7 + this.hitBox.width*7)){
            //System.out.println("mouse X is inside");
            if ((mouseY >= this.hitBox.y)&&(mouseY <= this.hitBox.y + this.hitBox.height*6)){
                //System.out.println("Mouse is inside button");
                isActive = true;
                return true;
            }
        }
        isActive = false;
        return false;
    }

    public String getName() {
        return name;
    }

    public boolean isActive() {
        return isActive;
    }

    private void playSound() {
        if (!isActive || hasPlayForActive) return;
        AudioConfiguration.getInstance().AUDIO_MOUSE_OVER.play(.3f);
        hasPlayForActive = true;
    }
}
