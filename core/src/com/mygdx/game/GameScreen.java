package com.mygdx.game;

import Objects.Player.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;

import java.io.IOException;

// Game screen class is used to create a game screen
public class GameScreen extends ScreenAdapter {
    // Initialize the game variables
    private OrthographicCamera camera;
    public SpriteBatch batch;
    private World world;
    public static final float PPM = 32.0f;
    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    private TileMapHelper tileMapHelper;
    private ContactListener contactListener;

    // Game objects
    private Player player;
    public Sprite victoryFlag, coin;
    private Boot game;

    public boolean showCoin1, showCoin2, showCoin3;
    public int collectedCoins;
    public FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;
    private int counter;
    private Texture level1Flash, level2Flash, level3Flash, level4Flash;
    private CompletedLevel completedLevel;


    // GameScreen constructor creates a new game with an OrthographicCamera as a parameter
    public GameScreen(Boot game, OrthographicCamera camera) {
        this.game = game;
        this.camera = camera;
        this.batch = new SpriteBatch();
        this.victoryFlag = new Sprite(new Texture("Levels/Other/Victory Flag.png"));
        this.coin = new Sprite(new Texture("Levels/Other/coin.png"));
        this.level1Flash = new Texture("Levels/Level 1/Level 1 Flashscreen.png");
        this.level2Flash = new Texture("Levels/Level 2/Level 2 Flash.png");
        this.level3Flash = new Texture("Levels/Level 3/Level 3 Flash.png");
        this.level4Flash = new Texture("Levels/Level 4/Level 4 Flash.png");
        this.createLevel();
    }

    private void setFont() {
        this.fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        this.fontParameter.size = 25;
        this.fontParameter.borderWidth = 5;
        this.fontParameter.borderColor = Color.BLACK;
        this.fontParameter.color = Color.WHITE;
        this.game.font = this.game.fontGenerator.generateFont(this.fontParameter);
    }

    // Update method updates the game every frame (1/60)
    private void update() throws IOException {
        world.step(1 / 60f, 6, 2);

        cameraUpdate();

        batch.setProjectionMatrix(camera.combined);
        orthogonalTiledMapRenderer.setView(camera);
        player.update();

        //TODO
        // IF ESCAPE IS PRESSED AND NOT IN MAIN MENU DISPLAY PAUSE MENU, ELSE QUIT
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            this.game.audio.stopMusic(game.currentScreen);
            this.game.currentScreen = "Menu Screen";
            this.game.audio.playMusic(game.currentScreen);
            this.game.setScreen(new MenuScreen(game));
        }
    }

    // Camera Update method updates camera position based on game data
    private void cameraUpdate() {
        Vector3 position = camera.position;
        position.x = Math.round(player.getBody().getPosition().x * PPM * 10) / 10f;
        position.y = Math.round(player.getBody().getPosition().y * PPM * 10) / 10f;
        camera.position.set(position);
        camera.update();
    }

    // Render method, renders game
    @Override
    public void render(float delta) {
        // Clear screen
        Gdx.gl.glClearColor(0.25f, 0.25f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (this.counter < 100) {
            this.batch.begin();
            if (game.levelNum == 1) {
                this.batch.draw(this.level1Flash, 0, 0, 1280, 720);
            } else if (game.levelNum == 2) {
                this.batch.draw(this.level2Flash, 0, 0, 1280, 720);
            } else if (game.levelNum == 3) {
                this.batch.draw(this.level3Flash, 0, 0, 1280, 720);
            } else if (game.levelNum == 4) {
                this.batch.draw(this.level4Flash, 0, 0, 1280, 720);
            }
            this.batch.end();
            this.counter++;
        } else if (counter == 100) {
            if (this.game.levelNum == 1) {
                this.game.currentScreen = "Level 1";
            } else if (this.game.levelNum == 2) {
                this.game.currentScreen = "Level 2";
            } else if (this.game.levelNum == 3) {
                this.game.currentScreen = "Level 3";
            } else if (this.game.levelNum == 4) {
                this.game.currentScreen = "Level 4";
            }
            this.game.audio.playMusic(this.game.currentScreen);
            this.counter++;
        } else {
            // Set skin position the same as player position
            this.game.playerSkin.setPosition((this.player.xPos) - 32, (this.player.yPos) - 32);
            if (game.levelNum == 1) {
                this.renderLevel1();
            } else if (game.levelNum == 2) {
                this.renderLevel2();
            } else if (game.levelNum == 3) {
                this.renderLevel3();
            } else if (game.levelNum == 4) {
                this.renderLevel4();
            }
        }
        this.checkGame();
    }

    public void createLevel() {
        this.game.reset();
        this.batch = new SpriteBatch();
        if (this.game.levelNum == 1) {
            this.world = new World(new Vector2(725f, -65f), false); // y value is gravity
            this.game.currentScreen = "Level 1";
        } else if (this.game.levelNum == 2) {
            this.world = new World(new Vector2(875f, -65f), false); // y value is gravity
            this.game.currentScreen = "Level 2";
        } else if (this.game.levelNum == 3) {
            this.world = new World(new Vector2(1000f, -65f), false); // y value is gravity
            this.game.currentScreen = "Level 3";
        } else if (this.game.levelNum == 4) {
            this.world = new World(new Vector2(1100f, -125f), false); // y value is gravity
            this.game.currentScreen = "Level 4";
        }

        this.contactListener = new ContactListener(this.game, this);
        this.world.setContactListener(this.contactListener);
        this.tileMapHelper = new TileMapHelper(this, this.game);
        this.orthogonalTiledMapRenderer = tileMapHelper.setupMap(game.levelNum);
        this.showCoin1 = true;
        this.showCoin2 = true;
        this.showCoin3 = true;
        this.collectedCoins = 0;
        this.counter = 0;
        this.setFont();
    }


    public void levelFoundation() {
        // Clear screen
        Gdx.gl.glClearColor(0.25f, 0.25f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Call update method
        try {
            this.update();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // render map
        orthogonalTiledMapRenderer.render();
    }


    public void renderLevel1() {
        this.levelFoundation();
        this.checkObjectsLevel1();

        // begin batch
        batch.begin();
        batch.draw(this.game.playerSkin, this.game.playerSkin.getX(), this.game.playerSkin.getY() + 5);
        batch.draw(this.victoryFlag, 15260f, 350f, 128, 128);

        if (this.showCoin1) {
            batch.draw(this.coin, 4290f, 590f);
        }

        if (this.showCoin2) {
            batch.draw(this.coin, 8960f, 580f);
        }

        if (this.showCoin3) {
            batch.draw(this.coin, 13375f, 840f);
        }

        this.game.font.draw(batch, "Current Score: " + this.game.currentScore, this.player.xPos - 635, this.player.yPos + 350);
        this.game.font.draw(batch, "Coins Collected: " + this.collectedCoins + "/3", this.player.xPos - 635, this.player.yPos + 325);

        batch.end();
        // Update Game Score
        game.currentScore = ((int) this.player.xPos) / 10;
    }

    private void renderLevel2() {
        this.levelFoundation();
        this.checkObjectsLevel2();
        this.batch.begin();
        this.batch.draw(this.game.playerSkin, this.game.playerSkin.getX(), this.game.playerSkin.getY());
        System.out.println(this.game.playerSkin.getX() + " " + this.game.playerSkin.getY());
        batch.draw(this.victoryFlag, 15260f, 350f, 128, 128);

        if (this.showCoin1) {
            batch.draw(this.coin, 1344f, 450f);
        }
        if (this.showCoin2) {
            batch.draw(this.coin, 5850f, 900f);
        }
        if (this.showCoin3) {
            batch.draw(this.coin, 11080f, 960f);
        }
        this.game.font.draw(batch, "Current Score: " + this.game.currentScore, this.player.xPos - 635, this.player.yPos + 350);
        this.game.font.draw(batch, "Coins Collected: " + this.collectedCoins + "/3", this.player.xPos - 635, this.player.yPos + 325);
        this.batch.end();
        // Update Game Score
        game.currentScore = ((int) this.player.xPos) / 10;
    }

    private void renderLevel3() {

    }

    private void renderLevel4() {
        this.levelFoundation();
        this.checkObjectsLevel4();

        // begin batch
        batch.begin();
        batch.draw(this.game.playerSkin, this.game.playerSkin.getX(), this.game.playerSkin.getY());
        batch.draw(this.victoryFlag, 15260f, 350f, 128, 128);

        if (this.showCoin1) {
            //batch.draw(this.coin, 4290f, 590f);
        }

        if (this.showCoin2) {
            //batch.draw(this.coin, 8960f, 580f);
        }

        if (this.showCoin3) {
            //batch.draw(this.coin, 13375f, 840f);
        }

        this.game.font.draw(batch, "Current Score: " + this.game.currentScore, this.player.xPos - 635, this.player.yPos + 350);
        this.game.font.draw(batch, "Coins Collected: " + this.collectedCoins + "/3", this.player.xPos - 635, this.player.yPos + 325);

        batch.end();
        // Update Game Score
        game.currentScore = ((int) this.player.xPos) / 10;
    }

    // getWorld method returns this class' world
    public World getWorld() {
        return world;
    }

    // Set player is a setter for player object
    public void setPlayer(Player player) {
        this.player = player;
    }

    public void checkObjectsLevel1() {
        // Coin #1
        if (this.player.xPos > 4290 && this.player.xPos < 4334 && this.player.yPos < 740 && this.player.yPos > 590 && this.showCoin1) {
            this.showCoin1 = false;
            this.collectedCoins++;
        } else if (this.player.xPos < 9024 && this.player.xPos > 8960 && this.player.yPos < 650 && this.player.yPos > 560 && this.showCoin2) { // Coin #2
            this.showCoin2 = false;
            this.collectedCoins++;
        } else if (this.player.xPos > 13375 && this.player.xPos < 13440 && this.player.yPos < 950 && this.player.yPos > 840 && this.showCoin3) { // Coin #3
            this.showCoin3 = false;
            this.collectedCoins++;
        }
    }

    public void checkObjectsLevel2() {
        // Coin #1
        if (this.player.xPos > 1344 && this.player.xPos < 1408 && this.player.yPos < 520 && this.player.yPos > 450 && this.showCoin1) {
            this.showCoin1 = false;
            this.collectedCoins++;
        } else if (this.player.xPos < 5950 && this.player.xPos > 5850 && this.player.yPos < 1100 && this.player.yPos > 900 && this.showCoin2) { // Coin #2
            this.showCoin2 = false;
            this.collectedCoins++;
        } else if (this.player.xPos > 11080 && this.player.xPos < 11144 && this.player.yPos < 1100 && this.player.yPos > 960 && this.showCoin3) { // Coin #3
            this.showCoin3 = false;
            this.collectedCoins++;
        }
    }

    public void checkObjectsLevel3() {

    }

    public void checkObjectsLevel4() {

    }

    public void checkGame() {
        if (this.player.xPos > 15260) { // Flag
            this.game.audio.stopMusic(game.currentScreen);
            this.completedLevel = new CompletedLevel(this.game, this);
            this.game.setScreen(this.completedLevel);
        }
    }
}
