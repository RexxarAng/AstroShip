package com.spaceshooter.screens;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.spaceshooter.*;
import com.spaceshooter.common.State;
import com.spaceshooter.configuration.GameConfiguration;
import com.spaceshooter.configuration.TextureConfiguration;
import com.spaceshooter.effect.ExplosionEffect;
import com.spaceshooter.effect.GameEffect;
import com.spaceshooter.pickup.Pickup;
import com.spaceshooter.ship.EnemyShip;
import com.spaceshooter.ship.PlayerShip;
import com.spaceshooter.weapon.Projectile;
import com.spaceshooter.SpaceShooterGame;
import com.spaceshooter.ImageButton;
import java.util.*;
import java.util.ArrayList;

//this class renders all the assets needed for in-game
public class GameScreen implements Screen {
    // Screen
    private final Camera camera;
    private final Viewport viewport;

    //state of game (can be run/pause)
    private State state;

    //Game
    private final SpaceShooterGame game;

    // Background
    private final Background background;

    // Graphics
    private final SpriteBatch batch;
    private final ArrayList<ImageButton> imageButtonList;

    // Game Objects
    private List<PlayerShip> playerShipList;
    private List<EnemyShip> enemyShipList;
    private List<Projectile> projectileList;
    private List<Pickup> powerUpList;
    private List<GameEffect> gameEffectList;

    //private BitmapFont font;
    private BitmapFont titleFont;
    private BitmapFont indicatorFont;

    // Game-mode
    private final boolean isSinglePlayer;

    private final GameController gameController;

    public GameScreen(SpaceShooterGame game, List<Player> playerList) {
        this.game = game;
        isSinglePlayer = playerList.size() == 1;
        camera = new OrthographicCamera();
        viewport = new StretchViewport(GameConfiguration.getInstance().WORLD_WIDTH, GameConfiguration.getInstance().WORLD_HEIGHT, camera);

        //Set up background
        background = new Background(GameConfiguration.getInstance().WORLD_WIDTH, GameConfiguration.getInstance().WORLD_HEIGHT);

        //Set up game objects
        gameController = new GameController(isSinglePlayer, playerList);
        enemyShipList = gameController.getEnemyShipList();
        playerShipList = gameController.getPlayerShipList();
        projectileList = gameController.getProjectileList();
        gameEffectList = gameController.getGameEffectList();
        powerUpList = gameController.getPowerUpList();

        batch = new SpriteBatch();
        imageButtonList = new ArrayList<>();
        createButtons();

        prepareFonts();

        state = State.RUN;
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float deltaTime) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);      //clears the buffer
        //Escape pausing game
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            if (this.state == State.PAUSE) {
                this.resumeGame();
            } else {
                this.pauseGame();
            }
        }

        // Checking current state
        switch (state) {
            case RUN: //if the state
                this.runGame(deltaTime);
                break;
            case PAUSE:
                this.renderPauseScreen();
                if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
                    this.dispose();
                    this.game.setScreen(new MainMenuScreen(this.game));
                } else if (Gdx.input.isKeyPressed(Input.Keys.R)) {
                    this.gameController.restartGame();
                    restart();
                    this.state = State.RUN;
                }
                break;
            default:
                break;
        }
    }

    public void restart() {
        enemyShipList = gameController.getEnemyShipList();
        playerShipList = gameController.getPlayerShipList();
        projectileList = gameController.getProjectileList();
        gameEffectList = gameController.getGameEffectList();
        powerUpList = gameController.getPowerUpList();
    }

    private void prepareFonts() {
        // Create a BitmapFont from font file
        // Heads-up Display
        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("EdgeOfTheGalaxyRegular-OVEa6.otf"));
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        indicatorFont = new BitmapFont();
        indicatorFont.getData().setScale(0.2f);
        indicatorFont.setUseIntegerPositions(false);

        fontParameter.size = 72;
        fontParameter.borderWidth = 3.6f;
        fontParameter.color = new Color(1, 1, 1, 0.3f);
        fontParameter.borderColor = new Color(0, 0, 0, 0.3f);
        titleFont = fontGenerator.generateFont(fontParameter);
        titleFont.setUseIntegerPositions(false);
        // Scale font to fit world
        titleFont.getData().setScale(0.08f);
    }

    private void drawAllObjects() {
        // Player ship
        for (PlayerShip playerShip : playerShipList) {
            indicatorFont.draw(batch, "P" + playerShip.getPlayer().getId(), playerShip.getHitBox().x + playerShip.getHitBox().width / 2, playerShip.getHitBox().y - (int) (indicatorFont.getCapHeight()), playerShip.getHitBox().width / 4, Align.center, false);
            playerShip.draw(batch);
        }

        // Enemy ships
        for (EnemyShip enemyShip : enemyShipList) {
            enemyShip.draw(batch);
        }

        // lasers
        renderProjectiles();

        // Explosions
        renderExplosions();

        // PowerUps
        renderPowerUps();

        // HUD rendering
        updateAndRenderHUD();
    }

    private void runGame(float deltaTime) {
        if (gameController.isGameOver()) {
            this.dispose();
            game.setScreen(new GameOverScreen(this.game, isSinglePlayer, gameController.getTime(), gameController.getPlayers()));
        }
        batch.begin();

        // Scrolling background
        background.renderBackground(batch, deltaTime);

        this.drawAllObjects();

        // Game Logic
        gameController.tick(deltaTime);
        batch.end();
    }

    private void createButtons() {
        //function generates and links all required assets and cords
        //calculate all the button's height
        int exitHeight = GameConfiguration.getInstance().WORLD_HEIGHT/10;
        int restartHeight = GameConfiguration.getInstance().WORLD_HEIGHT/8;
        //width of the buttons
        int exitButtonWidth = GameConfiguration.getInstance().WORLD_WIDTH/6;
        int restartWidth = GameConfiguration.getInstance().WORLD_WIDTH/8;
        //vars or values of the position of all the buttons
        int exitButtonY = 0;
        int exitButtonX = GameConfiguration.getInstance().WORLD_WIDTH - exitButtonWidth;
        int restartY = 0;
        int restartX = restartWidth / 3;
        imageButtonList.add(
            new ImageButton("Exit",
            new Rectangle(exitButtonX, exitButtonY, exitButtonWidth, exitHeight),
            new TextureRegion(new Texture("Exit_White.png")),
            new TextureRegion(new Texture("Exit_Yellow.png"))));

        imageButtonList.add(
            new ImageButton("Restart",
            new Rectangle(restartX, restartY , restartWidth, restartHeight),
            new TextureRegion(new Texture("Retry_White.png")),
            new TextureRegion(new Texture("Retry_Yellow.png"))));
    }

    private void renderPauseScreen() {
        //initializing the game
        batch.begin();
        //initializing the background session for the screen
        background.renderBackground(batch);
        for (ImageButton button: imageButtonList) {
            //button.onHover(Gdx.input.getX(), Gdx.input.getY()); //function called to check mouse hover area
            float mouseX = Gdx.input.getX();
            float mouseY = Gdx.input.getY();
            button.draw(batch);
            if ((button.onHoverPause(mouseX, mouseY))) {
                if (Gdx.input.isTouched()) {
                    switch (button.getName()) {
                        case "Exit":
                            this.dispose();
                            game.setScreen(new MainMenuScreen(game));
                            break;
                        case "Restart":
                            gameController.restartGame();
                            restart();
                            this.state = State.RUN;
                            break;
                    }
                }
            }
        }
        //render pause title
        //calculate values for X and Y cords for the Pause word
        float pauseX = GameConfiguration.getInstance().WORLD_WIDTH/2f - ((GameConfiguration.getInstance().WORLD_WIDTH / 3f)/2);
        float pauseY = GameConfiguration.getInstance().WORLD_HEIGHT/2f;
        this.drawAllObjects();
        titleFont.draw(batch,
                "Paused",
                pauseX,
                pauseY,
                GameConfiguration.getInstance().WORLD_WIDTH / 3f,
                Align.center,
                false);

        updateAndRenderHUD();
        batch.end();
    }

    private void updateAndRenderHUD() {
        // Calculate hud margins, etc.
        float hudVerticalMargin = titleFont.getCapHeight() / 2;
        float hudLeftX = titleFont.getCapHeight() / 2;
        float hudRightX = GameConfiguration.getInstance().WORLD_WIDTH * 2 / 3f - hudLeftX;
        float hudCentreX = GameConfiguration.getInstance().WORLD_WIDTH / 3f;
        float hudRow1Y = GameConfiguration.getInstance().WORLD_HEIGHT - hudVerticalMargin;
        float hudSectionWidth = GameConfiguration.getInstance().WORLD_WIDTH / 3f;

        // Render top row labels
        titleFont.draw(batch, "Player", hudLeftX, hudRow1Y, hudSectionWidth, Align.left, false);
        titleFont.draw(batch, "Score", hudCentreX, hudRow1Y, hudSectionWidth, Align.center, false);
        titleFont.draw(batch, "Lives", hudRightX, hudRow1Y, hudSectionWidth, Align.right, false);
        float hudRowCurrY = 0;
        int i = 1;
        // Render second row values
        for (PlayerShip playerShip : playerShipList) {
            hudRowCurrY = hudRow1Y - ((hudVerticalMargin + titleFont.getCapHeight())*i);
            titleFont.draw(batch, playerShip.getPlayer().getName(), hudLeftX, hudRowCurrY, hudSectionWidth, Align.left, false);
            titleFont.draw(batch, String.format(Locale.getDefault(), "%06d", playerShip.getScore()), hudCentreX, hudRowCurrY, hudSectionWidth, Align.center, false);
            i += 1;
        }

        if (playerShipList.size() > 1)
            hudRowCurrY += titleFont.getCapHeight();
        titleFont.draw(batch, String.format(Locale.getDefault(), "%01d", gameController.getLives()), hudRightX, hudRowCurrY, hudSectionWidth, Align.right, false);


    }

    private void renderExplosions() {
        ListIterator<GameEffect> gameEffectIterator = gameEffectList.listIterator();
        while (gameEffectIterator.hasNext()) {
            GameEffect gameEffect = gameEffectIterator.next();
            if (gameEffect.isFinished()) {
                gameEffectIterator.remove();
            } else {
                gameEffect.draw(batch);
            }
        }
    }

    private void renderProjectiles() {
        for (Projectile projectile: projectileList) {
            projectile.draw(batch);
        }
    }

    private void renderPowerUps() {
        for (Pickup powerUp : powerUpList) {
            powerUp.draw(batch);
        }
    }

    public void pauseGame() {
        this.state = State.PAUSE;
    }

    public void resumeGame() {
        this.state = State.RUN;
    }


    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        batch.setProjectionMatrix(camera.combined);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
    }
}
