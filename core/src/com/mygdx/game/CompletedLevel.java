package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class CompletedLevel implements Screen {
    private Texture backButtonInactive, backButtonActive;
    private Boot game;
    private GameScreen gameScreen;
    public FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;
    private Texture victoryScreen, nextLevelOn, nextLevelOff;

    private final int BUTTON_WIDTH = 150;
    private final int BUTTON_HEIGHT = 75;
    private final int BUTTON_Y = 15;
    private final int BACK_BUTTON_X = 15;
    private int NEXT_BUTTON_X;

    public CompletedLevel(Boot game, GameScreen gameScreen) {
        this.game = game;
        this.gameScreen = gameScreen;
        this.game.wealth += this.gameScreen.collectedCoins;
        this.gameScreen.collectedCoins = 0;
        this.victoryScreen = new Texture("FlashScreens/Victory/Victory Screen.png");
        this.nextLevelOff = new Texture("FlashScreens/Victory/Next Level Off.png");
        this.nextLevelOn = new Texture("FlashScreens/Victory/Next Level On.png");
        this.backButtonActive = new Texture("Menu/Back_Button_On.png");
        this.backButtonInactive = new Texture("Menu/Back_Button_Off.png");
        this.setFont();
        this.NEXT_BUTTON_X = this.game.widthScreen - this.BUTTON_WIDTH - 15;
        // TODO PLAY VICTORY AUDIO
    }

    private void setFont() {
        this.fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        this.fontParameter.size = 85;
        this.fontParameter.borderWidth = 5;
        this.fontParameter.borderColor = Color.BLACK;
        this.fontParameter.color = Color.GREEN;
        this.game.font = this.game.fontGenerator.generateFont(this.fontParameter);
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
        this.game.batch.begin();
        this.game.batch.draw(this.victoryScreen, 0, 0, game.widthScreen, game.heightScreen);
        this.game.font.draw(this.game.batch, String.valueOf(game.levelNum), 935, 420);
        this.game.font.draw(this.game.batch, String.valueOf(game.currentScore), 935, 320);
        this.game.font.draw(this.game.batch, this.gameScreen.collectedCoins + "/3", 935, 220);
        this.checkCollision();
        this.game.batch.end();
    }

    public void checkCollision() {
        int mouseX = Gdx.input.getX();
        int mouseY = Gdx.input.getY();

        if (mouseX > this.NEXT_BUTTON_X && mouseX < this.NEXT_BUTTON_X + this.BUTTON_WIDTH && mouseY < this.game.heightScreen - this.BUTTON_Y && mouseY > this.game.heightScreen - (this.BUTTON_Y + this.BUTTON_HEIGHT)) {
            this.game.batch.draw(this.nextLevelOn, this.NEXT_BUTTON_X, this.BUTTON_Y, this.BUTTON_WIDTH, this.BUTTON_HEIGHT);
            if (Gdx.input.isTouched()) {
                // TODO Generate next level
            }
        } else {
            this.game.batch.draw(this.nextLevelOff, this.NEXT_BUTTON_X, this.BUTTON_Y, this.BUTTON_WIDTH, this.BUTTON_HEIGHT);
        }

        if (mouseX > this.BACK_BUTTON_X && mouseX < this.BACK_BUTTON_X + this.BUTTON_WIDTH && mouseY < this.game.heightScreen - this.BUTTON_Y && mouseY > this.game.heightScreen - (this.BUTTON_Y + this.BUTTON_HEIGHT)) {
            this.game.batch.draw(this.backButtonActive, this.BACK_BUTTON_X, this.BUTTON_Y, this.BUTTON_WIDTH, this.BUTTON_HEIGHT);
            if (Gdx.input.isTouched()) {
                game.audio.stopMusic(game.currentScreen);
                game.currentScreen = "Menu Screen";
                game.audio.playMusic(game.currentScreen);
                game.setScreen(new MenuScreen(game));
            }
        } else {
            this.game.batch.draw(this.backButtonInactive, this.BACK_BUTTON_X, this.BUTTON_Y, this.BUTTON_WIDTH, this.BUTTON_HEIGHT);
        }



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
