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
    private Sprite backgroundSprite;


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

    // Set font parameters portraying to this class
    private void setFont() {
        this.fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        this.fontParameter.size = 25;
        this.fontParameter.borderWidth = 5;
        this.fontParameter.borderColor = Color.BLACK;
        this.fontParameter.color = Color.WHITE;
        this.game.font = this.game.fontGenerator.generateFont(this.fontParameter);
    }

    // Update method updates the game every frame 60 frames per second or (1/60 in hertz)
    private void update() throws IOException {
        world.step(1 / 60f, 6, 2);
        // Update camera
        cameraUpdate();
        batch.setProjectionMatrix(camera.combined);
        orthogonalTiledMapRenderer.setView(camera);
        player.update(); // Update player

        // If escape is pressed exit to main menu
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

        // For the first 100 frames, display the level flash screen
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
        // On the 100th frame, update the screen name
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

        // On every other frame afterwards, set the player skin position and render the level
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
        this.checkGame(); // Also, check if the player has completed the level
    }

    // Create level method is used to reinitialize class variables for each level.
    public void createLevel() {
        this.game.reset();
        this.batch = new SpriteBatch();
        if (this.game.levelNum == 1) {
            this.world = new World(new Vector2(725f, -65f), false); // y value is gravity
            this.game.currentScreen = "Level 1";
            this.backgroundSprite = new Sprite(new Texture("Levels/Level 1/level1 bg.png"));
        } else if (this.game.levelNum == 2) {
            this.world = new World(new Vector2(875f, -65f), false); // y value is gravity
            this.game.currentScreen = "Level 2";
            this.backgroundSprite = new Sprite(new Texture("Levels/Level 2/level2 bg.png"));
        } else if (this.game.levelNum == 3) {
            this.world = new World(new Vector2(1000f, -90f), false); // y value is gravity
            this.game.currentScreen = "Level 3";
            this.backgroundSprite = new Sprite(new Texture("Levels/Level 3/level3 bg.png"));
        } else if (this.game.levelNum == 4) {
            this.world = new World(new Vector2(1150f, -100f), false); // y value is gravity
            this.game.currentScreen = "Level 4";
            this.backgroundSprite = new Sprite(new Texture("Levels/Level 4/level4 bg.png"));
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

    // Level foundation method is used to create the foundation for rendering a level
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

        // draw background
        batch.begin();
        batch.draw(this.backgroundSprite, this.player.xPos - (int) (game.widthScreen / 2), this.player.yPos - (int) (game.heightScreen / 2), game.widthScreen, game.heightScreen);
        batch.end();

        // render map
        orthogonalTiledMapRenderer.render();
    }

    // Render level 1 method is used to render level 1
    public void renderLevel1() {
        // Call level foundation methods
        this.levelFoundation();
        this.checkObjectsLevel1();

        // begin batch
        batch.begin();
        batch.draw(this.game.playerSkin, this.game.playerSkin.getX(), this.game.playerSkin.getY()); // Dray player skin
        batch.draw(this.victoryFlag, 15260f, 350f, 128, 128); // Draw flag

        // Determine if coins should be displayed
        if (this.showCoin1) {
            batch.draw(this.coin, 4290f, 590f);
        }

        if (this.showCoin2) {
            batch.draw(this.coin, 8960f, 580f);
        }

        if (this.showCoin3) {
            batch.draw(this.coin, 13375f, 840f);
        }

        this.game.font.draw(batch, "Current Score: " + this.game.currentScore, this.player.xPos - 635, this.player.yPos + 350); // Draw current score
        this.game.font.draw(batch, "Coins Collected: " + this.collectedCoins + "/3", this.player.xPos - 635, this.player.yPos + 325); // Draw coins collected

        batch.end(); // End game batch

        // Update Game Score
        game.currentScore = ((int) this.player.xPos) / 10;
    }

    // Render level 2 method is used to render level 2
    private void renderLevel2() {
        // Call level foundation methods
        this.levelFoundation();
        this.checkObjectsLevel2();
        // begin batch
        this.batch.begin();
        this.batch.draw(this.game.playerSkin, this.game.playerSkin.getX(), this.game.playerSkin.getY()); // Dray player skin
        batch.draw(this.victoryFlag, 15260f, 350f, 128, 128);  // Draw flag

        // Determine if coins should be displayed
        if (this.showCoin1) {
            batch.draw(this.coin, 1344f, 450f);
        }
        if (this.showCoin2) {
            batch.draw(this.coin, 5850f, 900f);
        }
        if (this.showCoin3) {
            batch.draw(this.coin, 11080f, 960f);
        }

        this.game.font.draw(batch, "Current Score: " + this.game.currentScore, this.player.xPos - 635, this.player.yPos + 350); // Draw current score
        this.game.font.draw(batch, "Coins Collected: " + this.collectedCoins + "/3", this.player.xPos - 635, this.player.yPos + 325); // Draw coins collected
        this.batch.end(); // end batch

        // Update Game Score
        game.currentScore = ((int) this.player.xPos) / 10;
    }

    // Render level 3 method is used to render level 3
    private void renderLevel3() {
        // Call level foundation methods
        this.levelFoundation();
        this.checkObjectsLevel3();

        // begin batch
        batch.begin();
        batch.draw(this.game.playerSkin, this.game.playerSkin.getX(), this.game.playerSkin.getY()); // Draw player skin
        batch.draw(this.victoryFlag, 15260f, 350f, 128, 128); // Draw game flag

        // Determine if coins should be displayed
        if (this.showCoin1) {
            batch.draw(this.coin, 3145, 700);
        }

        if (this.showCoin2) {
            batch.draw(this.coin, 10280, 500);
        }

        if (this.showCoin3) {
            batch.draw(this.coin, 12620, 650);
        }

        this.game.font.draw(batch, "Current Score: " + this.game.currentScore, this.player.xPos - 635, this.player.yPos + 350); // Draw current score
        this.game.font.draw(batch, "Coins Collected: " + this.collectedCoins + "/3", this.player.xPos - 635, this.player.yPos + 325); // Draw coins collected

        batch.end(); // end batch

        // Update Game Score
        game.currentScore = ((int) this.player.xPos) / 10;
    }

    // Render level 4 method is used to render level 4
    private void renderLevel4() {
        // Call level foundation methods
        this.levelFoundation();
        this.checkObjectsLevel4();

        // begin batch
        batch.begin();
        batch.draw(this.game.playerSkin, this.game.playerSkin.getX(), this.game.playerSkin.getY());
        batch.draw(this.victoryFlag, 15260f, 350f, 128, 128);

        // Determine if coins should be displayed
        if (this.showCoin1) {
            batch.draw(this.coin, 5000, 770);
        }

        if (this.showCoin2) {
            batch.draw(this.coin, 9920, 460);
        }

        if (this.showCoin3) {
            batch.draw(this.coin, 12290, 575);
        }

        this.game.font.draw(batch, "Current Score: " + this.game.currentScore, this.player.xPos - 635, this.player.yPos + 350); // Draw current score
        this.game.font.draw(batch, "Coins Collected: " + this.collectedCoins + "/3", this.player.xPos - 635, this.player.yPos + 325); // Draw coins collected

        batch.end(); // End batch

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

    // Check objects and update boolean for level 1
    public void checkObjectsLevel1() {
        // Coin #1
        if (this.player.xPos > 4290 && this.player.xPos < 4334 && this.player.yPos < 740 && this.player.yPos > 590 && this.showCoin1) {
            this.showCoin1 = false;
            this.collectedCoins++;
        } else if (this.player.xPos < 9024 && this.player.xPos > 8960 && this.player.yPos < 700 && this.player.yPos > 560 && this.showCoin2) { // Coin #2
            this.showCoin2 = false;
            this.collectedCoins++;
        } else if (this.player.xPos > 13375 && this.player.xPos < 13440 && this.player.yPos < 950 && this.player.yPos > 840 && this.showCoin3) { // Coin #3
            this.showCoin3 = false;
            this.collectedCoins++;
        }
    }

    // Check objects and update boolean for level 2
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

    // Check objects and update boolean for level 3
    public void checkObjectsLevel3() {
        // Coin #1
        if (this.player.xPos > 3145 && this.player.xPos < 3250 && this.player.yPos < 800 && this.player.yPos > 695 && this.showCoin1) {
            this.showCoin1 = false;
            this.collectedCoins++;
        } else if (this.player.xPos < 10400 && this.player.xPos > 10280 && this.player.yPos < 600 && this.player.yPos > 500 && this.showCoin2) { // Coin #2
            this.showCoin2 = false;
            this.collectedCoins++;
        } else if (this.player.xPos > 12620 && this.player.xPos < 12700 && this.player.yPos < 780 && this.player.yPos > 650 && this.showCoin3) { // Coin #3
            this.showCoin3 = false;
            this.collectedCoins++;
        }
    }

    // Check objects and update boolean for level 4
    public void checkObjectsLevel4() {
        // Coin #1
        if (this.player.xPos > 5000 && this.player.xPos < 5100 && this.player.yPos < 880 && this.player.yPos > 770 && this.showCoin1) {
            this.showCoin1 = false;
            this.collectedCoins++;
        } else if (this.player.xPos > 9920 && this.player.xPos < 10000 && this.player.yPos < 550 && this.player.yPos > 455 && this.showCoin2) { // Coin #2
            this.showCoin2 = false;
            this.collectedCoins++;
        } else if (this.player.xPos > 12290 && this.player.xPos < 12400 && this.player.yPos < 700 && this.player.yPos > 570 && this.showCoin3) { // Coin #3
            this.showCoin3 = false;
            this.collectedCoins++;
        }
    }

    // Check if player has completed the level
    public void checkGame() {
        if (this.player.xPos > 15260) { // Flag Position
            this.game.audio.stopMusic(game.currentScreen);
            this.game.currentScreen = "Victory";
            this.completedLevel = new CompletedLevel(this.game, this);
            this.game.setScreen(this.completedLevel);
        }
    }
}
