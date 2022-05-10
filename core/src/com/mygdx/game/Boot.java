package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

public class Boot extends Game {
	public static Boot INSTANCE;
	public int widthScreen, heightScreen;
	private OrthographicCamera camera;
	public SpriteBatch batch;

	public Boot() {
		INSTANCE = this;
	}

	@Override
	public void create () {
		this.widthScreen = Gdx.graphics.getWidth();
		this.heightScreen = Gdx.graphics.getHeight();
		this.camera = new OrthographicCamera();
		this.camera.setToOrtho(false,  widthScreen, heightScreen);
		batch = new SpriteBatch();
		// change this to "(new GameScreen (camera))" to bypass menu
		this.setScreen(new MenuScreen(this));

	}

	@Override
	public void render () {
		super.render();
	}

}
