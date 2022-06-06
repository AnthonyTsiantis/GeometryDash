package com.mygdx.game;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

// Main Class, necessary for Game booting

public class Boot extends Game {
	// Initialize Class variables
	public static Boot INSTANCE;
	public int widthScreen, heightScreen;
	public OrthographicCamera camera;
	public SpriteBatch batch;
	public int levelNum, currentScore, highScore;
	public String filePath = System.getProperty("user.dir") + "/data/gameData.txt";
	public FreeTypeFontGenerator fontGenerator;
	public BitmapFont font;
	public float gameVolume = 1.0f;
	public String currentScreen;
	public AudioManager audio;
	public Sprite playerSkin, playerSkin1, playerSkin2, playerSkin3;
	public int playerSkinID;
	public boolean purchased6, purchased9;
	public int wealth;
	public int[] data;

	// Constructor method to create a new game instance
	public Boot() {
		INSTANCE = this;
	}

	// Create method used to populate Instance with data
	@Override
	public void create() {
		// Create screen dimensions
		this.widthScreen = 1280;
		this.heightScreen = 720;
		// Create camera and set dimensions
		this.camera = new OrthographicCamera();
		this.camera.setToOrtho(false,  widthScreen, heightScreen);

		// Create level number and currents score
		this.levelNum = 1;
		this.currentScore = 0;

		// Get past game data
		this.data = new int[4];
		try {
			this.data = this.getData();
		} catch (IOException e) {
			System.out.println("Unable to attain highscore...");
			e.printStackTrace();
		}
		this.highScore = this.data[0];
		this.wealth = this.data[1];
		this.purchased6 = this.data[2] == 1;
		this.purchased9 = this.data[3] == 1;

		// Create new sprite batch
		this.batch = new SpriteBatch();
		// Create new font
		this.fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/impact.ttf"));

		// Set screen to menu screen
		this.setScreen(new MenuScreen(this));
		this.currentScreen = "Menu Screen";

		// Play screen audio
		this.audio = new AudioManager(this);
		this.audio.playMusic(this.currentScreen);

		// Load player skins and set current
		this.playerSkin1 = new Sprite(new Texture("Skins/Character 1.png"));
		this.playerSkin2 = new Sprite(new Texture("Skins/Character 2.png"));
		this.playerSkin3 = new Sprite(new Texture("Skins/Character 3.png"));
		this.playerSkinID = 1;
		this.playerSkin = this.playerSkin1;
	}

	// Reset method used after level has been completed, resets all variables necessary to start another level.
	public void reset() {
		this.camera = new OrthographicCamera();
		this.camera.setToOrtho(false,  widthScreen, heightScreen);
		this.currentScore = 0;
		this.currentScreen = "Level " + this.levelNum;
		this.audio.playMusic(this.currentScreen);
	}

	// Set skin method, sets players skin
	public void setSkin(int skinID) {
		this.playerSkinID = skinID;
		if (skinID == 1) {
			this.playerSkin = this.playerSkin1;
		} else if (skinID == 2) {
			this.playerSkin = this.playerSkin2;
		} else if (skinID == 3) {
			this.playerSkin = this.playerSkin3;
		}
	}

	// Render Method, renders the game to the screen using LibGDX methods
	@Override
	public void render() {
		super.render();
	}

	// Get data method parses text file in assets directory and gets past game data.
	//	If the data does not exist, the game will create a new directory and populate default data
	public int[] getData() throws IOException {
		File gameData = new File(this.filePath);
		if (gameData.exists()) {
			Scanner myReader = new Scanner(gameData);
			for (int i = 0; i < 4; i++) {
				String line = myReader.nextLine();
				this.data[i] = Integer.parseInt(line.replaceAll("[\\D]", ""));
			}
			myReader.close();
		} else {
			FileWriter myWriter = new FileWriter(this.filePath);
			myWriter.write("Player Highscore: " + 0 + "\n");
			myWriter.write("Coins: " + 0 + "\n");
			myWriter.write("Purchased Skin Six: " + 0 + "\n");
			myWriter.write("Purchased Skin Nine: " + 0 + "\n");
			myWriter.close();
			this.data = new int[]{0, 0, 0, 0};
		}
		return this.data;
	}

	// Store data method will overwrite historical data with most recent data
	//	This is called whenever the application is closed
	public void storeData() throws IOException {
		FileWriter myWriter = new FileWriter(this.filePath);
		myWriter.write("Player Highscore: " + this.highScore + "\n");
		myWriter.write("Coins: " + this.wealth + "\n");
		myWriter.write("Purchased Skin Six: " + (this.purchased6 ? 1 : 0) + "\n");
		myWriter.write("Purchased Skin Nine: " + (this.purchased9 ? 1 : 0) + "\n");
		myWriter.close();
	}

	// Dispose method disposes of audio and stores historical data
	//	This is also called on game quit
	public void dispose() {
		this.audio.dispose();
		try {
			this.storeData();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
