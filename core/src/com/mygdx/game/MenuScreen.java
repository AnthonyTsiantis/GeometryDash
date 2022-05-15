package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

public class MenuScreen implements Screen {
    // Initilize Class Variables
    Boot game;
    Game camera;

    Texture playButtonActive;
    Texture playButtonInactive;
    Texture exitButtonActive;
    Texture exitButtonInactive;
    Texture menuBackground;

    private static final int BUTTON_WIDTH = 300;
    private static final int BUTTON_HEIGHT = 150;
    private static int playButtonY, exitButtonY, midWidth, midHeight, midButtonWidth, midButtonHeight;

    // Constructor class populates most class variables
    public MenuScreen(Boot game) {
        this.game = game;
        playButtonActive =  new Texture("Maps/Menu/Play_Button_On.png");
        exitButtonActive = new Texture("Maps/Menu/Quit_Button_On.png");
        playButtonInactive = new Texture("Maps/Menu/Play_Button_Off.png");
        exitButtonInactive = new Texture("Maps/Menu/Quit_Button_Off.png");
        menuBackground = new Texture("Maps/Menu/Title_background.png");
        midWidth = game.widthScreen / 2;
        midHeight = game.heightScreen / 2;
        playButtonY = midHeight - 50;
        exitButtonY = midHeight - 250;
        midButtonWidth = BUTTON_WIDTH / 2;
        midButtonHeight = BUTTON_HEIGHT / 2;
    }

    @Override
    public void show() {
    }

    // Render method
    @Override
    public void render(float delta) {
        // Clear the screen
        Gdx.gl.glClearColor(0.3f, 0.0f, 0.9f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Create batch and draw to it
        game.batch.begin();
        game.batch.draw(menuBackground, 0, 0, game.widthScreen, game.heightScreen);

        // Horizontal adjustment for mainButtons, left side of play button
        int xButtonOffset = midWidth - midButtonWidth;

        // Vertical offsets for each button
        int yPlayButtonOffset = playButtonY - 50;
        int yExitButtonOffset = exitButtonY + midHeight - midButtonHeight + 65;

        // Get the mouse X and Y coordinates
        int mouseX = Gdx.input.getX();
        int mouseY = Gdx.input.getY();

        // If mouse is within the play button display Yellow Button
        if ((mouseX > xButtonOffset) && (mouseX < (xButtonOffset + BUTTON_WIDTH)) && (mouseY > yPlayButtonOffset) && (mouseY < (yPlayButtonOffset + BUTTON_HEIGHT))) {
            game.batch.draw(playButtonActive, xButtonOffset, playButtonY, BUTTON_WIDTH, BUTTON_HEIGHT);
            // If the button is clicked, start the game
            if (Gdx.input.isTouched()) {
                this.dispose();
                game.setScreen(new GameScreen(game.camera));
            }
        // If outside button display grey button
        } else {
            game.batch.draw(playButtonInactive, xButtonOffset, playButtonY, BUTTON_WIDTH, BUTTON_HEIGHT);
        }

        // If mouse is within exit button, display grey button
        if ((mouseX < xButtonOffset + BUTTON_WIDTH) && (mouseX > xButtonOffset) && (mouseY > yExitButtonOffset) && (mouseY < (BUTTON_HEIGHT + yExitButtonOffset))) {
            game.batch.draw(exitButtonActive, xButtonOffset, exitButtonY, BUTTON_WIDTH, BUTTON_HEIGHT);
            // If button is clicked, end the application
            if (Gdx.input.isTouched()) {
                Gdx.app.exit();
            }

        // If mouse is outside exit button, display grey button
        } else {
            game.batch.draw(exitButtonInactive, xButtonOffset, exitButtonY, BUTTON_WIDTH, BUTTON_HEIGHT);
        }

        // End batch
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
