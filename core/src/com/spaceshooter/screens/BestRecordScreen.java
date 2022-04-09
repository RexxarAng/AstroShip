package com.spaceshooter.screens;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Align;
import com.spaceshooter.Background;
import com.spaceshooter.ImageButton;
import com.spaceshooter.SpaceShooterGame;
import com.spaceshooter.configuration.GameConfiguration;
import com.spaceshooter.preferences.NamePrefs;
import com.spaceshooter.preferences.TimePrefs;
import com.spaceshooter.preferences.ScorePrefs;
import java.util.ArrayList;

public class BestRecordScreen implements Screen{
    //facilitates loading and rendering of leaderboard
    private final NamePrefs namePrefs = new NamePrefs(GameConfiguration.getInstance().DEFAULT_NO_OF_RECORDS);
    private final TimePrefs timePrefs = new TimePrefs(GameConfiguration.getInstance().DEFAULT_NO_OF_RECORDS);
    private final ScorePrefs scorePrefs = new ScorePrefs(GameConfiguration.getInstance().DEFAULT_NO_OF_RECORDS);
    private final ArrayList<ImageButton> imageButtonList;

    // Heads-up Display
    private final BitmapFont font;

    private final SpaceShooterGame game;

    // Background
    private final Background background;
    // Graphics
    private final SpriteBatch batch;
    // Texture of all buttons on screen

    private final Texture hallTitle;

    public BestRecordScreen(SpaceShooterGame game){
        this.game = game;
        batch = new SpriteBatch();
        background = new Background(SpaceShooterGame.WIDTH, SpaceShooterGame.HEIGHT);
        imageButtonList = new ArrayList<>();
        createButtons();
        //setting the texture of the buttons
        hallTitle = new Texture("Hall_Yellow.png");

        // Create a BitmapFont from font file
        font = new BitmapFont(Gdx.files.internal("PressStart2PReg.fnt"));
    }

    private void createButtons() {
        //calculate all the button's height
        int buttonHeight = SpaceShooterGame.HEIGHT/8;
        //vars or values of the position of all the buttons
        int backButtonY = 0;
        int clearY = 0;
        //width of the buttons
        int backButtonWidth = SpaceShooterGame.WIDTH/6;
        int clearWidth = SpaceShooterGame.WIDTH/6;
        int x = SpaceShooterGame.WIDTH - backButtonWidth - backButtonWidth/6;
        imageButtonList.add(
                new ImageButton("Back",
                        new Rectangle(x, backButtonY, backButtonWidth, buttonHeight),
                        new TextureRegion(new Texture("Back_White.png")),
                        new TextureRegion(new Texture("Back_Yellow.png"))));
        x = SpaceShooterGame.WIDTH/16-2;
        imageButtonList.add(
                new ImageButton("Clear",
                        new Rectangle(x, clearY , clearWidth, buttonHeight*2/3f-4),
                        new TextureRegion(new Texture("Clear.png")),
                        new TextureRegion(new Texture("ClearYellow.png"))));
    }

    @SuppressWarnings("DefaultLocale")
    private void updateAndRenderHUD() {
        // Calculate hud margins, etc.
        float hudVerticalMargin = SpaceShooterGame.WIDTH / 8f;
        float hudLeftX = SpaceShooterGame.WIDTH / 8f;
        float hudRightX = SpaceShooterGame.WIDTH * 2f / 3 - hudLeftX;
        float hudCentreX = SpaceShooterGame.WIDTH / 3f;
        float hudRow1Y = SpaceShooterGame.HEIGHT - hudVerticalMargin;
        float hudSectionWidth = SpaceShooterGame.WIDTH / 3f;

        float hudRowCurrY;
        // Render top row labels
        font.draw(batch, "Rank", hudLeftX, hudRow1Y, hudSectionWidth, Align.left, false);
        font.draw(batch, "Time", hudCentreX, hudRow1Y, hudSectionWidth, Align.center, false);
        font.draw(batch, "Score", hudRightX, hudRow1Y, hudSectionWidth, Align.right, false);

        //updates high scores
        ArrayList<String> names = namePrefs.loadPrefs();
        ArrayList<Float> times = timePrefs.loadPrefs();
        ArrayList<Integer> scores = scorePrefs.loadPrefs();

        for (int i = 0; i < namePrefs.getNoOfRecords(); i++){
            hudRowCurrY = hudRow1Y - SpaceShooterGame.HEIGHT/8f*(i+1)/2f;
            font.draw(batch, String.format("%d %s", i+1, names.get(i)), hudLeftX, hudRowCurrY, hudSectionWidth, Align.left, false);
            font.draw(batch, String.format("%.2f", times.get(i)), hudCentreX, hudRowCurrY, hudSectionWidth, Align.center, false);
            font.draw(batch, String.format("%06d", scores.get(i)), hudRightX, hudRowCurrY, hudSectionWidth, Align.right, false);
        }
    }

    @Override
    public void render(float delta) {
        //initializing the game
        batch.begin();
        //initializing the background session for the screen
        background.renderBackground(batch, delta);
        //calculate all the button's height
        int buttonHeight = SpaceShooterGame.HEIGHT/8;
        int hallTitleY = SpaceShooterGame.HEIGHT - buttonHeight*3;
        //width of the exit and coop buttons
        int hallTitleWidth = SpaceShooterGame.WIDTH*2/3;
        int x = SpaceShooterGame.WIDTH / 2 - hallTitleWidth / 2;//this x store the x cord of the Single Player button
        //mouse exit from button, "Single Player button", set to inactive texture
        batch.draw(hallTitle, x, hallTitleY, hallTitleWidth, buttonHeight*4);

        for (ImageButton button: imageButtonList) {
            button.onHover(Gdx.input.getX(), Gdx.input.getY());
            button.draw(batch);
            if (button.isActive()) {
                if (Gdx.input.isTouched()) {
                    switch (button.getName()) {
                        case "Back":
                            this.dispose();
                            game.setScreen(new MainMenuScreen(this.game));
                            break;
                        case "Clear":
                            namePrefs.clearPrefs();
                            timePrefs.clearPrefs();
                            scorePrefs.clearPrefs();
                            break;
                    }
                }
            }
        }

        // HUD rendering
        updateAndRenderHUD();
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
