package com.mygdx.game;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
	public FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;
	public BitmapFont font;

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
		this.levelNum = 1;
		this.currentScore = 0;
		try {
			this.highScore = this.getHighScore();
		} catch (IOException e) {
			System.out.println("Unable to attain highscore...");
			e.printStackTrace();
		}
		batch = new SpriteBatch();
		this.fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/impact.ttf"));
		this.fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		this.fontParameter.size = 100;
		this.fontParameter.borderWidth = 5;
		this.fontParameter.borderColor = Color.BLACK;
		this.fontParameter.color = Color.RED;
		this.font = fontGenerator.generateFont(this.fontParameter);
		this.setScreen(new MenuScreen(this));
	}

	// Render Method, renders the game to the screen using LibGDX methods
	@Override
	public void render() {
		super.render();
	}

	public int getHighScore() throws IOException {
		File gameData = new File(this.filePath);
		if (gameData.exists()) {
			Scanner myReader = new Scanner(gameData);
			int data = myReader.nextInt();
			myReader.close();
			return data;
		} else {
			FileWriter myWriter = new FileWriter(this.filePath);
			myWriter.write("0");
			myWriter.close();
			return 0;
		}
	}

	public void writeHighScore() throws IOException {
		FileWriter myWriter = new FileWriter(this.filePath);
		myWriter.write(String.valueOf(this.highScore));
		myWriter.close();
	}
}
