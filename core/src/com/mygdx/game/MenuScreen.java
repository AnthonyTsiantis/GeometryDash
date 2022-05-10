package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

public class MenuScreen implements Screen {

    Boot game;
    Game camera;

    Texture playButtonActive;
    Texture playButtonInactive;
    Texture exitButtonActive;
    Texture exitButtonInactive;
    Texture menuBackground;

    private static final int PLAY_BUTTON_WIDTH = 300;
    private static final int PLAY_BUTTON_HEIGHT = 150;
    private static final int EXIT_BUTTON_WIDTH = 300;
    private static final int EXIT_BUTTON_HEIGHT = 150;
    private static final int PLAY_BUTTON_Y = 300;
    private static final int EXIT_BUTTON_Y = 100;


    public MenuScreen(Boot game) {
        this.game = game;
        playButtonActive =  new Texture("LEVELS0.png");
        exitButtonActive = new Texture("QUIT0.png");
        playButtonInactive = new Texture("levels1.png");
        exitButtonInactive = new Texture("quit1.png");
        menuBackground = new Texture("SMCSJUMPER!0.png");
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor((float) 0.3, (float) 0.0, (float) 0.9, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();

        game.batch.draw(menuBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


        int x = game.widthScreen / 2 - PLAY_BUTTON_WIDTH / 2;
        if (Gdx.input.getX() < x + PLAY_BUTTON_WIDTH && Gdx.input.getX() > x && Gdx.input.getY() < PLAY_BUTTON_Y + PLAY_BUTTON_HEIGHT && Gdx.input.getY() > PLAY_BUTTON_Y){
            game.batch.draw(playButtonActive, game.widthScreen / 2 - PLAY_BUTTON_WIDTH / 2, PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
            if (Gdx.input.isTouched()) {
                this.dispose();
                // change this to "(new GameScreen(new OrthographicCamera())" to make it actually run, i need to fix
                // the "new orthographiccamera" part to something like game so it actually runs upon clicking play
                game.setScreen(new GameScreen(new OrthographicCamera()));
            }

        } else {
            game.batch.draw(playButtonInactive, x, PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
        }

        x = game.widthScreen / 2 - EXIT_BUTTON_WIDTH / 2;
        if (Gdx.input.getX() < x + EXIT_BUTTON_WIDTH && Gdx.input.getX() > x && Gdx.input.getY() < EXIT_BUTTON_Y + EXIT_BUTTON_HEIGHT && Gdx.input.getY() > EXIT_BUTTON_Y) {
            game.batch.draw(exitButtonActive, game.widthScreen / 2 - EXIT_BUTTON_WIDTH / 2, EXIT_BUTTON_Y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
            if (Gdx.input.isTouched()) {
                Gdx.app.exit();
                System.out.println("Thanks for closing the application!");
            }
        } else {
            game.batch.draw(exitButtonInactive, x, EXIT_BUTTON_Y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
        }

        x = game.widthScreen / 2 - PLAY_BUTTON_WIDTH / 2;
        if (Gdx.input.getX() < x + PLAY_BUTTON_WIDTH && Gdx.input.getX() > x && Gdx.input.getY() < PLAY_BUTTON_Y + PLAY_BUTTON_HEIGHT && Gdx.input.getY() > PLAY_BUTTON_Y) {
            game.batch.draw(playButtonActive, game.widthScreen / 2 - PLAY_BUTTON_WIDTH / 2, PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
            if (Gdx.input.isTouched()) {
                this.dispose();
            }
        }

        game.batch.end();

    }

    @Override
    public void resize(int width, int height) {
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
