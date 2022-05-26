package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

public class HelpMenu implements Screen {
    Boot game;

    Texture helpMenuBackground;
    Texture backButtonActive;
    Texture backButtonInactive;

    private static final int BACK_BUTTON_WIDTH = 150;
    private static final int BACK_BUTTON_HEIGHT = 75;

    public HelpMenu(Boot game) {
        this.game = game;
        helpMenuBackground = new Texture("Menu/Help_Menu_Background.png");
        backButtonActive = new Texture("Menu/Back_Button_On.png");
        backButtonInactive = new Texture("Menu/Back_Button_Off.png");
    }

    @Override
    public void show() {

    }

    public void render(float delta) {
        // Clear the screen
        Gdx.gl.glClearColor(0.3f, 0.0f, 0.9f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Create batch and draw to it
        game.batch.begin();
        game.batch.draw(helpMenuBackground, 0, 0, game.widthScreen, game.heightScreen);

        int backButtonVerticalOffset = 25;
        int backButtonHorizontalOffset = 25;

        int verticalButtonHitboxOffset = game.heightScreen - 25 - BACK_BUTTON_HEIGHT;

        // Get the mouse X and Y coordinates
        int mouseX = Gdx.input.getX();
        int mouseY = Gdx.input.getY();

        if ((mouseX > backButtonHorizontalOffset) && (mouseX < backButtonHorizontalOffset + BACK_BUTTON_WIDTH) && (mouseY > verticalButtonHitboxOffset) && (mouseY < verticalButtonHitboxOffset + BACK_BUTTON_HEIGHT)) {
            game.batch.draw(backButtonActive, backButtonHorizontalOffset, backButtonVerticalOffset, BACK_BUTTON_WIDTH, BACK_BUTTON_HEIGHT);
            if (Gdx.input.isTouched()) {
                this.dispose();
                game.setScreen(new MenuScreen(game));
            }
        } else {
            game.batch.draw(backButtonInactive, backButtonHorizontalOffset, backButtonVerticalOffset, BACK_BUTTON_WIDTH, BACK_BUTTON_HEIGHT);
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
