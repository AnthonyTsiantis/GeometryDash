package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

import java.io.IOException;

public class GameOver extends ScreenAdapter {
    private Boot game;
    Texture gameOverBackground;
    Texture backButtonActive;
    Texture backButtonInactive;
    private static final int BACK_BUTTON_WIDTH = 150;
    private static final int BACK_BUTTON_HEIGHT = 75;


    public GameOver(Boot game) throws IOException {
        this.game = game;
        gameOverBackground = new Texture("FlashScreens/GameOver.png");
        backButtonActive = new Texture("Menu/Back_Button_On.png");
        backButtonInactive = new Texture("Menu/Back_Button_Off.png");
        if(game.currentScore > game.highScore) {
            game.highScore = game.currentScore;
            game.writeHighScore();
        }
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        // Clear the screen
        Gdx.gl.glClearColor(0f, 0.0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Create batch and draw to it
        game.batch.begin();
        game.batch.draw(gameOverBackground, 0, 0, game.widthScreen, game.heightScreen);

        int backButtonVerticalOffset = 25;
        int backButtonHorizontalOffset = 25;

        int verticalButtonHitboxOffset = game.heightScreen - 25 - BACK_BUTTON_HEIGHT;

        // Get the mouse X and Y coordinates
        int mouseX = Gdx.input.getX();
        int mouseY = Gdx.input.getY();

        if ((mouseX > backButtonHorizontalOffset) && (mouseX < backButtonHorizontalOffset + BACK_BUTTON_WIDTH) && (mouseY > verticalButtonHitboxOffset) && (mouseY < verticalButtonHitboxOffset + BACK_BUTTON_HEIGHT)) {
            game.batch.draw(backButtonActive, backButtonHorizontalOffset, backButtonVerticalOffset, BACK_BUTTON_WIDTH, BACK_BUTTON_HEIGHT);
            if (Gdx.input.isTouched()) {
                game.audio.stopMusic(game.currentScreen);
                game.currentScreen = "Menu Screen";
                game.audio.playMusic(game.currentScreen);
                game.setScreen(new MenuScreen(game));
            }
        } else {
            game.batch.draw(backButtonInactive, backButtonHorizontalOffset, backButtonVerticalOffset, BACK_BUTTON_WIDTH, BACK_BUTTON_HEIGHT);
        }

        game.font.draw(game.batch, String.valueOf(game.levelNum), 925, 425);
        game.font.draw(game.batch, String.valueOf(game.currentScore), 925, 325);
        game.font.draw(game.batch, String.valueOf(game.highScore), 925, 225);
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
