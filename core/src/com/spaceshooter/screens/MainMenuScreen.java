package com.spaceshooter.screens;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.spaceshooter.Background;
import com.spaceshooter.ImageButton;
import com.spaceshooter.SpaceShooterGame;
import com.spaceshooter.configuration.AudioConfiguration;

import java.util.ArrayList;

public class MainMenuScreen implements Screen{

    //Game
    private final SpaceShooterGame game;
    // Background
    final private Background background;
    // Graphics
    final private SpriteBatch batch;
    final private ArrayList<ImageButton> imageButtonList;

    //pre=loading all the assets on the screen
    public MainMenuScreen(SpaceShooterGame game) {
        this.game = game;
        batch = new SpriteBatch();
        background = new Background(SpaceShooterGame.WIDTH, SpaceShooterGame.HEIGHT);
        imageButtonList = new ArrayList<>();
        createButtons();
    }

    private void createButtons() {
        //calculate all the button's width and height
        int buttonWidth = SpaceShooterGame.WIDTH/2;
        int buttonHeight = SpaceShooterGame.HEIGHT/8;
        int x = SpaceShooterGame.WIDTH / 2 - buttonWidth / 2;

        //vars or values of the position of all the buttons
        int exitButtonY = SpaceShooterGame.HEIGHT/8;
        int bestRecordButtonY = exitButtonY+buttonHeight+buttonHeight/2;
        int coopButtonY = bestRecordButtonY+buttonHeight+buttonHeight/2;
        int singlePlayerButtonY = coopButtonY+buttonHeight+buttonHeight/2;

        //width of the exit and coop buttons
        int exitButtonWidth = SpaceShooterGame.WIDTH/4;
        int coopButtonWidth = SpaceShooterGame.WIDTH/4;

        imageButtonList.add(
                new ImageButton("Single Player",
                new Rectangle(x, singlePlayerButtonY, buttonWidth, buttonHeight),
                new TextureRegion(new Texture("Single_Player_White.png")),
                new TextureRegion(new Texture("Single_Player_Yellow.png"))));
        x = SpaceShooterGame.WIDTH / 2 - coopButtonWidth / 2;
        imageButtonList.add(
                new ImageButton("Co-op",
                new Rectangle(x, coopButtonY , coopButtonWidth, buttonHeight),
                new TextureRegion(new Texture("Co-Op_White.png")),
                new TextureRegion(new Texture("Co-Op_Yellow.png"))));
        x = SpaceShooterGame.WIDTH / 2 - buttonWidth / 2;
        imageButtonList.add(
                new ImageButton("Best Record",
                new Rectangle(x, bestRecordButtonY, buttonWidth, buttonHeight),
                new TextureRegion(new Texture("Best_Record_White.png")),
                new TextureRegion(new Texture("Best_Record_Yellow.png"))));
        // Create exit button
        x = SpaceShooterGame.WIDTH / 2 - exitButtonWidth / 2; //this x stores the x coordinate of the exit button
        imageButtonList.add(
                new ImageButton("Exit",
                new Rectangle(x, exitButtonY, exitButtonWidth, buttonHeight),
                new TextureRegion(new Texture("Exit_White.png")),
                new TextureRegion(new Texture("Exit_Yellow.png"))));
    }

    @Override
    public void show() {
    }

    //loading of all the assets on the screen
    @Override
    public void render(float delta) {
        //initializing the game
        batch.begin();
        //initializing the background session for the screen
        background.renderBackground(batch, delta);

        if (Gdx.input.isKeyPressed(Input.Keys.M)) {
            if (AudioConfiguration.getInstance().MUSIC_BGM.isPlaying()) {
                AudioConfiguration.getInstance().MUSIC_BGM.stop();
            } else {
                AudioConfiguration.getInstance().MUSIC_BGM.setLooping(true);
                AudioConfiguration.getInstance().MUSIC_BGM.setVolume(0.1f);
                AudioConfiguration.getInstance().MUSIC_BGM.play();
            }
        }

        for (ImageButton button: imageButtonList) {
            button.onHover(Gdx.input.getX(), Gdx.input.getY());
            button.draw(batch);
            if (button.isActive()) {
                if (Gdx.input.isTouched()) {
                    switch (button.getName()) {
                        case "Single Player":
                            this.dispose();
                            game.setScreen(new GameLobbyScreen(game, true));
                            break;
                        case "Co-op":
                            this.dispose();
                            game.setScreen(new GameLobbyScreen(game, false));
                            break;
                        case "Best Record":
                            this.dispose();
                            game.setScreen(new BestRecordScreen(game));
                            break;
                        case "Exit":
                            Gdx.app.exit();
                            break;
                    }
                }
            }
        }
        batch.end();
    }


    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
    }
}
