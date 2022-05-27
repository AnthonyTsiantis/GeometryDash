package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

import java.io.IOException;

public class MenuScreen implements Screen {
    // Initialize Class Variables
    Boot game;

    Texture playButtonActive;
    Texture playButtonInactive;
    Texture exitButtonActive;
    Texture exitButtonInactive;
    Texture menuBackground;
    Texture helpButtonActive;
    Texture helpButtonInactive;
    Texture skinsButtonActive;
    Texture skinsButtonInactive;

    private static final int BUTTON_WIDTH = 300;
    private static final int BUTTON_HEIGHT = 150;
    private static final int SIDE_BUTTON_WIDTH = 200;
    private static final int SIDE_BUTTON_HEIGHT = 100;
    private static int playButtonY, exitButtonY, midWidth, midHeight, midButtonWidth, midButtonHeight;

    // Constructor class populates most class variables
    public MenuScreen(Boot game) {
        this.game = game;
        playButtonActive =  new Texture("Menu/Play_Button_On.png");
        exitButtonActive = new Texture("Menu/Quit_Button_On.png");
        playButtonInactive = new Texture("Menu/Play_Button_Off.png");
        exitButtonInactive = new Texture("Menu/Quit_Button_Off.png");
        menuBackground = new Texture("Menu/Title_Background.png");
        helpButtonActive = new Texture("Menu/Help_Button_On.png");
        helpButtonInactive = new Texture("Menu/Help_Button_Off.png");
        skinsButtonActive = new Texture("Menu/Skins_Button_On.png");
        skinsButtonInactive = new Texture("Menu/Skins_Button_Off.png");
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


        // Vertical offset for side buttons
        int ySideButtonsOffset = midHeight - 115;
        int verticalSideButtonBound = ySideButtonsOffset + 135;

        // Horizontal offset for side button
        int xSkinButtonOffset = midWidth - 400;
        int xHelpButtonOffset = midWidth + 200;

        // Get the mouse X and Y coordinates
        int mouseX = Gdx.input.getX();
        int mouseY = Gdx.input.getY();

        // If mouse is within the play button display Yellow Button
        if ((mouseX > xButtonOffset) && (mouseX < (xButtonOffset + BUTTON_WIDTH)) && (mouseY > yPlayButtonOffset) && (mouseY < (yPlayButtonOffset + BUTTON_HEIGHT))) {
            game.batch.draw(playButtonActive, xButtonOffset, playButtonY, BUTTON_WIDTH, BUTTON_HEIGHT);
            // If the button is clicked, start the game
            if (Gdx.input.isTouched()) {
                game.audio.stopMusic(game.currentScreen);
                game.currentScreen = "Level 1";
                game.audio.playMusic(game.currentScreen);
                game.setScreen(new GameScreen(game, game.camera));
            }

        // If outside button display grey button
        } else {
            game.batch.draw(playButtonInactive, xButtonOffset, playButtonY, BUTTON_WIDTH, BUTTON_HEIGHT);
        }

        // If mouse is within exit button, display yellow button
        if ((mouseX < xButtonOffset + BUTTON_WIDTH) && (mouseX > xButtonOffset) && (mouseY > yExitButtonOffset) && (mouseY < (BUTTON_HEIGHT + yExitButtonOffset))) {
            game.batch.draw(exitButtonActive, xButtonOffset, exitButtonY, BUTTON_WIDTH, BUTTON_HEIGHT);
            // If button is clicked, end the application
            if (Gdx.input.isTouched()) {
                try {
                    game.writeHighScore();
                } catch (IOException e) {
                    System.out.println("Trouble writing highscore...");
                    e.printStackTrace();
                }
                game.dispose();
                Gdx.app.exit();
            }

        // If mouse is outside exit button, display grey button
        } else {
            game.batch.draw(exitButtonInactive, xButtonOffset, exitButtonY, BUTTON_WIDTH, BUTTON_HEIGHT);
        }

        // If mouse is within help button, display yellow button
        if((mouseX > xHelpButtonOffset) && (mouseX < xHelpButtonOffset + SIDE_BUTTON_WIDTH) && (mouseY > verticalSideButtonBound) && (mouseY < verticalSideButtonBound + SIDE_BUTTON_HEIGHT)) {
            game.batch.draw(helpButtonActive, xHelpButtonOffset, ySideButtonsOffset, SIDE_BUTTON_WIDTH, SIDE_BUTTON_HEIGHT);
            // Change menu to Help menu once clicked
            if (Gdx.input.isTouched()) {
                this.dispose();
                game.setScreen(new HelpMenu(game));
            }

        // If mouse is outside, display grey
        } else {
            game.batch.draw(helpButtonInactive, xHelpButtonOffset, ySideButtonsOffset, SIDE_BUTTON_WIDTH, SIDE_BUTTON_HEIGHT);
        }

        // If mouse is within skins button, display yellow
        if ((mouseX > xSkinButtonOffset) && (mouseX < xSkinButtonOffset + SIDE_BUTTON_WIDTH) && (mouseY > verticalSideButtonBound) && (mouseY < verticalSideButtonBound + SIDE_BUTTON_HEIGHT)) {
            game.batch.draw(skinsButtonActive, xSkinButtonOffset, ySideButtonsOffset, SIDE_BUTTON_WIDTH, SIDE_BUTTON_HEIGHT);
            //Display Skins menu
            if (Gdx.input.isTouched()) {
                this.dispose();
                game.setScreen(new SkinsMenu(game));
            }

        // If mouse is outside, display grey
        } else {
            game.batch.draw(skinsButtonInactive, xSkinButtonOffset, ySideButtonsOffset, SIDE_BUTTON_WIDTH, SIDE_BUTTON_HEIGHT);
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
