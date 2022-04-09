package com.spaceshooter.screens;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.spaceshooter.Background;
import com.spaceshooter.ImageButton;
import com.spaceshooter.Player;
import com.spaceshooter.SpaceShooterGame;
//packages for text input
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.spaceshooter.configuration.GameConfiguration;
import com.spaceshooter.configuration.TextureConfiguration;

import java.util.ArrayList;
import java.util.List;

public class GameLobbyScreen implements Screen{

    private final ArrayList<ImageButton> imageButtonList;

    //setting up vars for User input field
    final private Stage stage;
    private final TextField player1TextField;
    private TextField player2TextField;
    private final SpaceShooterGame game;
    // Background
    private final Background background;
    // Graphics
    private final SpriteBatch batch;
    //check if single player is true
    private final boolean isSinglePlayer;

    GameLobbyScreen(SpaceShooterGame game, boolean isSinglePlayer){
        this.game = game;
        this.isSinglePlayer = isSinglePlayer;
        imageButtonList = new ArrayList<>();
        batch = new SpriteBatch();
        background = new Background(SpaceShooterGame.WIDTH, SpaceShooterGame.HEIGHT);

        //initiating things for the text input
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        Skin skin = new Skin(Gdx.files.internal("ui-skin.json"));

        //creating nameField object, and setting parameters
        player1TextField = new TextField("Player 1", skin);
        player1TextField.setPosition((SpaceShooterGame.WIDTH/2f)-SpaceShooterGame.WIDTH/3f/2, SpaceShooterGame.HEIGHT/2f-SpaceShooterGame.HEIGHT/8f);
        player1TextField.setSize(SpaceShooterGame.WIDTH/3f, SpaceShooterGame.HEIGHT/12f);
        player1TextField.setMaxLength(9);
        //if the mode picked is no single player, adjust player name fields accordingly
        if (!isSinglePlayer){
            player1TextField.setPosition((SpaceShooterGame.WIDTH/2f)-SpaceShooterGame.WIDTH/3f/2, SpaceShooterGame.HEIGHT/2f-SpaceShooterGame.HEIGHT/14f);
            player2TextField = new TextField("Player 2", skin);
            player2TextField.setPosition((SpaceShooterGame.WIDTH/2f)-SpaceShooterGame.WIDTH/3f/2, SpaceShooterGame.HEIGHT/2f-SpaceShooterGame.HEIGHT/6f);
            player2TextField.setSize(SpaceShooterGame.WIDTH/3f, SpaceShooterGame.HEIGHT/12f);
            player2TextField.setMaxLength(9);
            stage.addActor(player2TextField);
        }
        stage.addActor(player1TextField);

        createButtons();
    }

    private void createButtons() {
        //this method generates the parameter and assets for the buttons
        //vars or values of the position of all the buttons
        int buttonHeight = SpaceShooterGame.HEIGHT/6;
        float backButtonHeight = (float) buttonHeight*2/3;
        int beginButtonY = SpaceShooterGame.HEIGHT/6;
        int backButtonY = 0;
        //width of the exit and coop buttons
        int beginButtonWidth = SpaceShooterGame.WIDTH/4;
        final int backButtonWidth = SpaceShooterGame.WIDTH/6;
        int x = SpaceShooterGame.WIDTH / 2 - beginButtonWidth / 2;
        imageButtonList.add(
                new ImageButton("Begin",
                new Rectangle(x, beginButtonY, beginButtonWidth, buttonHeight),
                new TextureRegion(new Texture("Begin_White.png")),
                new TextureRegion(new Texture("Begin_Yellow.png"))));
        x = SpaceShooterGame.WIDTH - backButtonWidth - backButtonWidth/6;
        imageButtonList.add(
                new ImageButton("Back",
                new Rectangle(x, backButtonY , backButtonWidth, backButtonHeight),
                new TextureRegion(new Texture("Back_White.png")),
                new TextureRegion(new Texture("Back_Yellow.png"))));
    }


    @Override
    public void render(float delta) {
        //width of the exit and coop buttons
        int buttonWidth = SpaceShooterGame.WIDTH/2;
        int buttonHeight = SpaceShooterGame.HEIGHT/8;
        int coopButtonWidth = SpaceShooterGame.WIDTH/4;
        int enterNameButtonWidth = SpaceShooterGame.WIDTH/2;
        int titleY = buttonHeight*6;
        int enterNameButtonY = titleY - buttonHeight*5/2;

        //setting up vars for game control pics
        int controlWidth = SpaceShooterGame.WIDTH/6;
        int controlHeight = SpaceShooterGame.HEIGHT/2;
        int controlY = SpaceShooterGame.HEIGHT*2/8;
        int controlX = SpaceShooterGame.WIDTH/32;
        Texture enterNameTexture = new Texture("Enter_Your_Name.png");

        //initializing the game
        batch.begin();
        //initializing the background session for the screen
        background.renderBackground(batch, delta);


        int x = SpaceShooterGame.WIDTH / 2 - buttonWidth / 2;//this x store the x coordinate of the Single Player title
        if (isSinglePlayer) {
            // If single player display single player title
            batch.draw(TextureConfiguration.getInstance().TEXTURE_SINGLE_PLAYER_TITLE, x, titleY, buttonWidth, buttonHeight);
        }
        else{
            x = SpaceShooterGame.WIDTH / 2 - coopButtonWidth / 2; //set x of coop button
            // Display co-op title
            batch.draw(TextureConfiguration.getInstance().TEXTURE_COOP_TITLE, x, titleY, coopButtonWidth, buttonHeight);
        }

        //render Lobby Greeting placeholder
        x = SpaceShooterGame.WIDTH / 2 - enterNameButtonWidth / 2;
        batch.draw(enterNameTexture, x, enterNameButtonY, enterNameButtonWidth, buttonHeight*3);

        //render player control instructions
        batch.draw(TextureConfiguration.getInstance().TEXTURE_PLAYER1_CONTROL, controlX, controlY, controlWidth, controlHeight);
        if (!isSinglePlayer){
            batch.draw(TextureConfiguration.getInstance().TEXTURE_PLAYER2_CONTROL, SpaceShooterGame.WIDTH-controlWidth-20, controlY, controlWidth, controlHeight);
        }

        for (ImageButton button: imageButtonList) {
            button.onHover(Gdx.input.getX(), Gdx.input.getY());
            button.draw(batch);
            if (button.isActive()) {
                if (Gdx.input.isTouched()) {
                    switch (button.getName()) {
                        case "Begin":
                            List<Player> playerList = new ArrayList<>();
                            playerList.add(new Player(
                                    GameConfiguration.getInstance().PLAYER_1_UP_KEY,
                                    GameConfiguration.getInstance().PLAYER_1_LEFT_KEY,
                                    GameConfiguration.getInstance().PLAYER_1_DOWN_KEY,
                                    GameConfiguration.getInstance().PLAYER_1_RIGHT_KEY,
                                    GameConfiguration.getInstance().PLAYER_1_SECONDARY_KEY,
                                    player1TextField.getText(),
                                    GameConfiguration.getInstance().PLAYER_1
                            ));
                            if (isSinglePlayer){
                                System.out.println("Player 1 Name: " + player1TextField.getText()); //retrieve input from Player 1
                            }
                            else{
                                playerList.add(new Player(
                                        GameConfiguration.getInstance().PLAYER_2_UP_KEY,
                                        GameConfiguration.getInstance().PLAYER_2_LEFT_KEY,
                                        GameConfiguration.getInstance().PLAYER_2_DOWN_KEY,
                                        GameConfiguration.getInstance().PLAYER_2_RIGHT_KEY,
                                        GameConfiguration.getInstance().PLAYER_2_SECONDARY_KEY,
                                        player2TextField.getText(),
                                        GameConfiguration.getInstance().PLAYER_2
                                ));
                                System.out.println("Player 1 Name: " + player1TextField.getText()); //retrieve input from Player 1
                                System.out.println("Player 2 Name: " + player2TextField.getText()); //retrieve input from Player 2
                            }
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

        //rendering name field
        stage.act(delta);
        stage.draw();
        batch.end();
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
