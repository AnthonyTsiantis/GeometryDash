package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.io.IOException;

// Game Over class is used to display game over screen
public class GameOver extends ScreenAdapter {
    // Initialize class variables
    private Boot game;
    private Texture gameOverBackground;
    private Texture backButtonActive;
    private Texture backButtonInactive;
    private static final int BACK_BUTTON_WIDTH = 150;
    private static final int BACK_BUTTON_HEIGHT = 75;
    public FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;


    // Constructor Class used to instantiate screen and update variables
    public GameOver(Boot game) throws IOException {
        this.game = game;
        gameOverBackground = new Texture("FlashScreens/GameOver.png");
        backButtonActive = new Texture("Menu/Back_Button_On.png");
        backButtonInactive = new Texture("Menu/Back_Button_Off.png");
        if(this.game.currentScore > this.game.highScore) {
            this.game.highScore = this.game.currentScore;
        }
        this.setFont();
    }

    // Set font method updates the screen font style
    private void setFont() {
        this.fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        this.fontParameter.size = 100;
        this.fontParameter.borderWidth = 5;
        this.fontParameter.borderColor = Color.BLACK;
        this.fontParameter.color = Color.RED;
        this.game.font = this.game.fontGenerator.generateFont(this.fontParameter);
    }


    @Override
    public void show() {

    }

    // Render method displays the screen
    @Override
    public void render(float delta) {
        // Clear the screen
        Gdx.gl.glClearColor(0f, 0.0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Create batch and draw to it
        game.batch.begin();
        game.batch.draw(gameOverBackground, 0, 0, game.widthScreen, game.heightScreen); // Draw background

        // Create variable offsets
        int backButtonVerticalOffset = 25;
        int backButtonHorizontalOffset = 25;
        int verticalButtonHitboxOffset = game.heightScreen - 25 - BACK_BUTTON_HEIGHT;

        // Get the mouse X and Y coordinates
        int mouseX = Gdx.input.getX();
        int mouseY = Gdx.input.getY();

        // Back button collision
        if ((mouseX > backButtonHorizontalOffset) && (mouseX < backButtonHorizontalOffset + BACK_BUTTON_WIDTH) && (mouseY > verticalButtonHitboxOffset) && (mouseY < verticalButtonHitboxOffset + BACK_BUTTON_HEIGHT)) {
            // if mouse is within button, display yellow
            game.batch.draw(backButtonActive, backButtonHorizontalOffset, backButtonVerticalOffset, BACK_BUTTON_WIDTH, BACK_BUTTON_HEIGHT);
            // If the button is clicked, set menu screen
            if (Gdx.input.isTouched()) {
                game.audio.stopMusic(game.currentScreen);
                game.currentScreen = "Menu Screen";
                game.audio.playMusic(game.currentScreen);
                game.setScreen(new MenuScreen(game));
            }
        } else {
            // If not within back button, draw grey button
            game.batch.draw(backButtonInactive, backButtonHorizontalOffset, backButtonVerticalOffset, BACK_BUTTON_WIDTH, BACK_BUTTON_HEIGHT);
        }

        game.font.draw(game.batch, String.valueOf(game.levelNum), 925, 425); // Draw level number
        game.font.draw(game.batch, String.valueOf(game.currentScore), 925, 325); // Draw current score
        game.font.draw(game.batch, String.valueOf(game.highScore + 1), 925, 225); // Draw highscore
        game.batch.end(); // End batch
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
