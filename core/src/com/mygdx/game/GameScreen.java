package com.mygdx.game;

import Objects.Player.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
    private Box2DDebugRenderer box2DDebugRenderer;
    public static final float PPM = 32.0f;
    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    private TileMapHelper tileMapHelper;
    private ContactListener contactListener;

    // Game objects
    private Player player;
    public Sprite playerSkin;
    private Boot game;


    // GameScreen constructor creates a new game with an OrthographicCamera as a parameter
    public GameScreen(Boot game, OrthographicCamera camera) {
        this.game = game;
        this.camera = camera;
        this.batch = new SpriteBatch();
        this.playerSkin = new Sprite(new Texture("Skins/Character1.png"));
        this.playerSkin.setPosition(64f, 64f);
        this.world = new World(new Vector2(500f, -65f), false); // y value is gravity
        this.contactListener = new ContactListener(this.game);
        this.world.setContactListener(contactListener);
        this.box2DDebugRenderer = new Box2DDebugRenderer();
        this.tileMapHelper = new TileMapHelper(this);
        this.orthogonalTiledMapRenderer = tileMapHelper.setupMap();
        game.currentScore = 0;
    }

    // Update method updates the game every frame (1/60)
    private void update() throws IOException {
        world.step(1/60f, 6, 2);

        cameraUpdate();

        batch.setProjectionMatrix(camera.combined);
        orthogonalTiledMapRenderer.setView(camera);
        player.update();

        //TODO
        // IF ESCAPE IS PRESSED AND NOT IN MAIN MENU DISPLAY PAUSE MENU, ELSE QUIT
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
    }

    public void gameOver() throws IOException {
        // TODO
        // Dispose of all game attributes
        // Change screen
        game.setScreen(new GameOver(game));
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
        // Call update method
        try {
            this.update();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Clear screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // render map
        orthogonalTiledMapRenderer.render();

        // Set skin position the same as player position
        this.playerSkin.setPosition((this.player.xPos) - 32, (this.player.yPos) - 32);

        // set camera
        batch.setProjectionMatrix(camera.combined);
        // begin batch
        batch.begin();
        batch.draw(this.playerSkin, this.playerSkin.getX(), this.playerSkin.getY());
        batch.end();
        // Render World

        // TODO Comment this out to get rid of map boundaries
        box2DDebugRenderer.render(world, camera.combined.scl(PPM));

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
}
