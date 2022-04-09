package com.spaceshooter.screens;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Align;
import com.spaceshooter.*;
import com.spaceshooter.configuration.GameConfiguration;
import com.spaceshooter.preferences.NamePrefs;
import com.spaceshooter.preferences.ScorePrefs;
import com.spaceshooter.preferences.TimePrefs;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class GameOverScreen implements Screen{
    // Array List to store images assets and positional data
    private final ArrayList<ImageButton> imageButtonList;
    // Fonts
    private final BitmapFont font;
    // Main Game Obj
    private final SpaceShooterGame game;
    // Background
    private final Background background;
    // Graphics
    private final SpriteBatch batch;
    // Texture of all buttons on screen
    private final Texture gameOverTitle;
    //tracking game-mode for retry
    private final boolean isSinglePlayer;
    private final float time;
    private final List<Player> playerList;


    public GameOverScreen(SpaceShooterGame game, boolean isSinglePlayer, float time, List<Player> playerList){
        this.playerList = playerList;
        this.game = game;
        this.time = time;
        this.isSinglePlayer = isSinglePlayer;
        imageButtonList = new ArrayList<>();
        batch = new SpriteBatch();
        background = new  Background(SpaceShooterGame.WIDTH, SpaceShooterGame.HEIGHT);

        //setting the texture of the title
        gameOverTitle = new Texture("Game_Over_White.png");

        // Create a BitmapFont from font file
        font = new BitmapFont(Gdx.files.internal("PressStart2PReg.fnt"));

        //updates leaderboard
        checkAndSetHighScore();
        createButtons();
    }

    private void createButtons() {
        //calculate all the button's width and height
        int buttonWidth = SpaceShooterGame.WIDTH/4;
        int buttonHeight = SpaceShooterGame.HEIGHT/8;
        float retryButtonHeight = (float) buttonHeight*3/2;
        //y position of the buttons
        int retryButtonY = SpaceShooterGame.HEIGHT/8;
        int backButtonY = 0;
        //width of the exit and coop buttons
        int retryButtonWidth = SpaceShooterGame.WIDTH/4;
        int backButtonWidth = SpaceShooterGame.WIDTH/6;
        int x = SpaceShooterGame.WIDTH / 2 - retryButtonWidth / 2; //this x stores the x coordinate of the exit button
        imageButtonList.add(
                new ImageButton("Retry",
                        new Rectangle(x, retryButtonY, buttonWidth, retryButtonHeight),
                        new TextureRegion(new Texture("Retry_White.png")),
                        new TextureRegion(new Texture("Retry_Yellow.png"))));
        x = SpaceShooterGame.WIDTH - backButtonWidth - backButtonWidth/6; //this x stores the x coordinate of the exit button
        imageButtonList.add(
                new ImageButton("Back",
                        new Rectangle(x, backButtonY , backButtonWidth, buttonHeight),
                        new TextureRegion(new Texture("Back_White.png")),
                        new TextureRegion(new Texture("Back_Yellow.png"))));
    }

    private void updateAndRenderResults() {
        float hudLeftX = SpaceShooterGame.WIDTH/8f;
        int buttonHeight = SpaceShooterGame.HEIGHT/8;
        float hudRightX = SpaceShooterGame.WIDTH * 2 / 3f - hudLeftX;
        float hudCentreX = SpaceShooterGame.WIDTH / 3f;
        float hudRow1Y = SpaceShooterGame.HEIGHT-buttonHeight*3 + buttonHeight/2f;
        float hudRow2Y = hudRow1Y - buttonHeight;
        float hudRow3Y = hudRow2Y - buttonHeight;
        float hudSectionWidth = SpaceShooterGame.WIDTH / 3f;
        // Render top row labels
        if (this.isSinglePlayer){
            font.draw(batch, playerList.get(0).getName(), hudCentreX, hudRow1Y, hudSectionWidth, Align.center, false);
            font.draw(batch, String.format(Locale.getDefault(), "%06d", playerList.get(0).getScore()), hudCentreX, hudRow2Y, hudSectionWidth, Align.center, false);
            font.draw(batch, String.format(Locale.getDefault(), "%.2f seconds", time), hudCentreX, hudRow3Y, hudSectionWidth, Align.center, false);
        }
        else{
            font.draw(batch, playerList.get(0).getName(), hudLeftX, hudRow1Y, hudSectionWidth, Align.center, false);
            font.draw(batch, playerList.get(1).getName(), hudRightX, hudRow1Y, hudSectionWidth, Align.center, false);
            font.draw(batch, String.format(Locale.getDefault(), "%06d", playerList.get(0).getScore()), hudLeftX, hudRow2Y, hudSectionWidth, Align.center, false);
            font.draw(batch, String.format(Locale.getDefault(), "%06d", playerList.get(1).getScore()), hudRightX, hudRow2Y, hudSectionWidth, Align.center, false);
            font.draw(batch, String.format(Locale.getDefault(), "%.02f seconds", time), hudCentreX, hudRow3Y, hudSectionWidth, Align.center, false);
        }
    }

    @Override
    public void render(float delta) {
        //initializing the game
        batch.begin();
        //initializing the background session for the screen
        background.renderBackground(batch, delta);
        int buttonHeight = SpaceShooterGame.HEIGHT/8;
        int gameOverY =  SpaceShooterGame.HEIGHT-buttonHeight*3;
        int gameOverWidth = SpaceShooterGame.WIDTH / 2;
        int x = SpaceShooterGame.WIDTH / 2 - gameOverWidth / 2;
        //mouse exit from button, "Single Player button", set to inactive texture
        batch.draw(gameOverTitle, x, gameOverY, gameOverWidth, buttonHeight*4);

        for (ImageButton button: imageButtonList) {
            button.onHover(Gdx.input.getX(), Gdx.input.getY());
            button.draw(batch);
            if (button.isActive()) {
                if (Gdx.input.isTouched()) {
                    switch (button.getName()) {
                        case "Retry":
                            this.dispose();
                            game.setScreen(new GameScreen(game, playerList));
                            break;
                        case "Back":
                            this.dispose();
                            game.setScreen(new MainMenuScreen(game));
                            break;
                    }
                }
            }
        }
        // HUD rendering
        updateAndRenderResults();
        batch.end();
    }

    //facilitates updating of the leaderboard
    private void checkAndSetHighScore() { //updates preference file if new highscores are achieved (inclusive of name, time, and score)
        NamePrefs namePrefs = new NamePrefs(GameConfiguration.getInstance().DEFAULT_NO_OF_RECORDS);
        TimePrefs timePrefs = new TimePrefs(GameConfiguration.getInstance().DEFAULT_NO_OF_RECORDS);
        ScorePrefs scorePrefs = new ScorePrefs(GameConfiguration.getInstance().DEFAULT_NO_OF_RECORDS);

        ArrayList<String>  names = namePrefs.loadPrefs();
        ArrayList<Float>  times = timePrefs.loadPrefs();
        ArrayList<Integer>  scores = scorePrefs.loadPrefs();

        int current_score;

        for (Player player: playerList) {
            for (int i = 0; i < namePrefs.getNoOfRecords()-1; i++) //iterate through highscores "high_score_0" to "high_score_8", biggest to smallest
            {
                current_score = scores.get(i);
                if (player.getScore() > current_score) { //automatically places highscore info into rank and pushes the list down
                    names.add(i, player.getName());
                    names.remove(namePrefs.getNoOfRecords()); //remove the lowest score's name
                    times.add(i, time);
                    times.remove(namePrefs.getNoOfRecords()); //remove the lowest score's time
                    scores.add(i, player.getScore());
                    scores.remove(namePrefs.getNoOfRecords()); //remove the lowest score
                    break;
                }
            }
        }

        //saves and updates new highscores into preference file
        namePrefs.savePrefs(names);
        timePrefs.savePrefs(times);
        scorePrefs.savePrefs(scores);
    }

    @Override
    public void show() {}
    @Override
    public void resize(int width, int height){}
    @Override
    public void pause() {}
    @Override
    public void resume() {}
    @Override
    public void hide() {}
    @Override
    public void dispose() {}
}
