package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

public class LevelMenu implements Screen {
    Boot game;

    private Texture levelMenuBackground;
    private Texture level1Off, level1On;
    private Texture level2Off, level2On;
    private Texture level3Off, level3On;
    private Texture level4Off, level4On;
    private Texture backButtonActive;
    private Texture backButtonInactive;

    private static final int BACK_BUTTON_WIDTH = 150;
    private static final int BACK_BUTTON_HEIGHT = 75;
    private static final int BUTTON_WIDTH = 400;
    private static final int BUTTON_HEIGHT = 200;

    public LevelMenu(Boot game) {
        this.game = game;
        this.levelMenuBackground = new Texture("Menu/Levels Menu/levels background.png");
        this.level1Off = new Texture("Menu/Levels Menu/level 1 Off.png");
        this.level1On = new Texture("Menu/Levels Menu/level 1 On.png");
        this.level2Off = new Texture("Menu/Levels Menu/level 2 Off.png");
        this.level2On = new Texture("Menu/Levels Menu/level 2 On.png");
        this.level3Off = new Texture("Menu/Levels Menu/level 3 Off.png");
        this.level3On = new Texture("Menu/Levels Menu/level 3 On.png");
        this.level4Off = new Texture("Menu/Levels Menu/level 4 Off.png");
        this.level4On = new Texture("Menu/Levels Menu/level 4 On.png");
        this.backButtonActive = new Texture("Menu/Back_Button_On.png");
        this.backButtonInactive = new Texture("Menu/Back_Button_Off.png");
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        // Clear the screen
        Gdx.gl.glClearColor(0.3f, 0.0f, 0.9f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Create batch and draw to it
        game.batch.begin();
        game.batch.draw(levelMenuBackground, 0, 0, game.widthScreen, game.heightScreen);

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

        //new GameScreen(this.game, this.game.camera)

        int verticalOff = 350;
        int horizontalOff = (this.game.widthScreen / 2) + 100;
        if ((mouseX > 150) && (mouseX < 150 + BUTTON_WIDTH) && (mouseY < game.heightScreen - verticalOff) && (mouseY > game.heightScreen - (verticalOff + BUTTON_HEIGHT))) {
            this.game.batch.draw(this.level1On, 150, verticalOff, BUTTON_WIDTH, BUTTON_HEIGHT);
            if (Gdx.input.isTouched()) {
                this.game.levelNum = 1;
                this.dispose();
                this.game.setScreen(new GameScreen(this.game, this.game.camera));
            }
        } else {
            this.game.batch.draw(this.level1Off, 150, verticalOff, BUTTON_WIDTH, BUTTON_HEIGHT);
        }

        if ((mouseX > horizontalOff) && (mouseX < horizontalOff + BUTTON_WIDTH) && (mouseY < game.heightScreen - verticalOff) && (mouseY > game.heightScreen - (verticalOff + BUTTON_HEIGHT))) {
            this.game.batch.draw(this.level2On, horizontalOff, verticalOff, BUTTON_WIDTH, BUTTON_HEIGHT);
            if (Gdx.input.isTouched()) {
                this.game.levelNum = 2;
                this.dispose();
                this.game.setScreen(new GameScreen(this.game, this.game.camera));
            }
        } else {
            this.game.batch.draw(this.level2Off, horizontalOff, verticalOff, BUTTON_WIDTH, BUTTON_HEIGHT);
        }

        if ((mouseX > 150) && (mouseX < 150 + BUTTON_WIDTH) && (mouseY < game.heightScreen - 125) && (mouseY > game.heightScreen - (125 + BUTTON_HEIGHT))) {
            this.game.batch.draw(this.level3On, 150, 125, BUTTON_WIDTH, BUTTON_HEIGHT);
            if (Gdx.input.isTouched()) {
                this.game.levelNum = 3;
                this.dispose();
                this.game.setScreen(new GameScreen(this.game, this.game.camera));
            }
        } else {
            this.game.batch.draw(this.level3Off, 150, 125, BUTTON_WIDTH, BUTTON_HEIGHT);
        }

        if ((mouseX > horizontalOff) && (mouseX < horizontalOff + BUTTON_WIDTH) && (mouseY < game.heightScreen - 125) && (mouseY > game.heightScreen - (125 + BUTTON_HEIGHT))) {
            this.game.batch.draw(this.level4On, horizontalOff, 125, BUTTON_WIDTH, BUTTON_HEIGHT);
            if (Gdx.input.isTouched()) {
                this.game.levelNum = 4;
                this.dispose();
                this.game.setScreen(new GameScreen(this.game, this.game.camera));
            }
        } else {
            this.game.batch.draw(this.level4Off, horizontalOff, 125, BUTTON_WIDTH, BUTTON_HEIGHT);
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
