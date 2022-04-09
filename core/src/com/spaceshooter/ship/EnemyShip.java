package com.spaceshooter.ship;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.spaceshooter.Score;
import com.spaceshooter.configuration.GameConfiguration;
import com.spaceshooter.configuration.TextureConfiguration;
import com.spaceshooter.weapon.Projectile;

import java.util.ArrayList;

public class EnemyShip extends Ship {

    private int level;
    private Vector2 directionVector;
    private float timeSinceLastDirectionChange = 0;
    private float directionChangeFrequency = 0.75f;
    private Score score;

    public EnemyShip(int level,
                     float xCentre,
                     float yCentre,
                      float width,
                     float height,
                      float movementSpeed,
                      int shield,
                      Score score,
                      TextureRegion shipTextureRegion) {
        super(xCentre, yCentre, width, height, movementSpeed, shipTextureRegion);
        this.level = level;
        this.shield = new Shield(this.hitBox, TextureConfiguration.getInstance().TEXTURE_ENEMY_SHIELD, shield);
        this.score = score;
        directionVector = new Vector2(0, -1);
    }

    public Vector2 getDirectionVector() {
        return directionVector;
    }

    private void randomizeDirectionVector() {
        double bearing = GameConfiguration.getInstance().random.nextDouble()*6.283185; // 0 to 2*PI
        directionVector.x = (float)Math.sin(bearing);
        directionVector.y = (float)Math.cos(bearing);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        timeSinceLastDirectionChange += deltaTime;
        if (timeSinceLastDirectionChange > directionChangeFrequency) {
            randomizeDirectionVector();
            timeSinceLastDirectionChange -= directionChangeFrequency;
        }
        this.move(deltaTime);
    }

    @Override
    public void fireProjectiles(ArrayList<Projectile> projectileList) {
        this.getWeapon().fire(this.getHitBox(), 0, projectileList);
    }

    @Override
    public void draw(Batch batch) {
        super.draw(batch);
        drawRanking(batch);
        drawShip(batch);
        drawShield(batch);
    }

    private void move(float deltaTime) {
        //Determine max distance ship can move
        float leftLimit, rightLimit, upLimit, downLimit;
        leftLimit = -this.getHitBox().x;
        downLimit = (float) GameConfiguration.getInstance().WORLD_HEIGHT / 2 - this.getHitBox().y;
        rightLimit = GameConfiguration.getInstance().WORLD_WIDTH - this.getHitBox().x - this.getHitBox().width;
        upLimit = GameConfiguration.getInstance().WORLD_HEIGHT - this.getHitBox().y - this.getHitBox().height;

        float xMove = this.getDirectionVector().x * this.getMovementSpeed() * deltaTime;
        float yMove = this.getDirectionVector().y * this.getMovementSpeed() * deltaTime;

        if (xMove > 0)
            xMove = Math.min(xMove, rightLimit);
        else
            xMove = Math.max(xMove, leftLimit);

        if (yMove > 0)
            yMove = Math.min(yMove, upLimit);
        else
            yMove = Math.max(yMove, downLimit);

        this.translate(xMove, yMove);
    }

    public void setDirectionChangeFrequency(float directionChangeFrequency) {
        this.directionChangeFrequency = directionChangeFrequency;
    }

    private void drawRanking(Batch batch) {

        final float starSpacing = 0.3f;
        final int starSize = 1;

        final float totalWidth = level * (starSize + starSpacing) - starSpacing;

        float centerX = this.getHitBox().x + this.getHitBox().width/2f;
        float topY = this.getHitBox().y + this.getHitBox().height + 1.5f;

        float startX = centerX - totalWidth/2;

        for (int i=0; i < level; i++) {
            batch.draw(
                    TextureConfiguration.getInstance().TEXTURE_ENEMY_SHIP_STAR_RANKING,
                    startX + i * (starSize + starSpacing),
                    topY - starSize / 2f,
                    starSize,
                    starSize);
        }
    }

    private void drawShip(Batch batch) {
        batch.draw(
                this.getTextureRegion(),
                this.getHitBox().x,
                this.getHitBox().y,
                this.getHitBox().width,
                this.getHitBox().height);

    }

    private void drawShield(Batch batch) {
        if (this.shield.getCurrentCapacity() > 0) {
            batch.draw(
                    this.shield.getTextureRegion(),
                    this.getHitBox().x,
                    this.getHitBox().y - this.getHitBox().height * 0.2f,
                    this.getHitBox().width,
                    this.getHitBox().height);
        }
    }

    public int getScore() {
        return this.score.getPoints();
    }

}
