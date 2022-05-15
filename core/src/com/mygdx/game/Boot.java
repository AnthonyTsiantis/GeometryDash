package com.mygdx.game;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

// Main Class, necessary for Game booting

public class Boot extends Game {
	// Initialize Class variables
	public static Boot INSTANCE;
	public int widthScreen, heightScreen;
	public OrthographicCamera camera;
	public SpriteBatch batch;

	// Constructor method to create a new game instance
	public Boot() {
		INSTANCE = this;
	}

	// Create method used to populate Instance with data
	@Override
	public void create() {
		this.widthScreen = 1280;
		this.heightScreen = 720;
		this.camera = new OrthographicCamera();
		this.camera.setToOrtho(false,  widthScreen, heightScreen);
		batch = new SpriteBatch();
		this.setScreen(new MenuScreen(this));
	}

	// Render Method, renders the game to the screen using LibGDX methods
	@Override
	public void render() {
		super.render();
	}

}
