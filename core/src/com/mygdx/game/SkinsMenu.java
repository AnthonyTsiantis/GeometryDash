package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

public class SkinsMenu implements Screen {
    Boot game;

    private Texture skinsMenuBackground;
    private Texture backButtonActive, backButtonInactive;
    private Texture purchase6Off, purchase6On;
    private Texture purchase9Off, purchase9On;
    private Texture selectOff, selectOn;
    private Texture selected;

    private static final int BACK_BUTTON_WIDTH = 150;
    private static final int BACK_BUTTON_HEIGHT = 75;
    private static final int SELECTED_BUTTON_WIDTH = 150;
    private static final int SELECTED_BUTTON_HEIGHT = 65;

    private int x;
    private int y;


    public SkinsMenu(Boot game) {
        this.game = game;
        this.skinsMenuBackground = new Texture("Menu/Skins Menu/skins background.png");
        this.purchase6Off = new Texture("Menu/Skins Menu/Purchase 6 Off.png");
        this.purchase6On = new Texture("Menu/Skins Menu/Purchase 6 On.png");
        this.purchase9Off = new Texture("Menu/Skins Menu/Purchase 9 Off.png");
        this.purchase9On = new Texture("Menu/Skins Menu/Purchase 9 On.png");
        this.selectOff = new Texture("Menu/Skins Menu/Select Off.png");
        this.selectOn = new Texture("Menu/Skins Menu/Select On.png");
        this.selected = new Texture("Menu/Skins Menu/Selected On.png");
        this.backButtonActive = new Texture("Menu/Back_Button_On.png");
        this.backButtonInactive = new Texture("Menu/Back_Button_Off.png");
        this.x = 200;
        this.y = 120;
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
        game.batch.draw(skinsMenuBackground, 0, 0, game.widthScreen, game.heightScreen);
        this.drawButtons();
        game.batch.end();
    }

    private int drawButtons() {
        // Get the mouse X and Y coordinates
        int mouseX = Gdx.input.getX();
        int mouseY = Gdx.input.getY();
        // Draw Selected Button
        if (this.game.playerSkinID == 1) {
            this.x = 200;
        } else if (this.game.playerSkinID == 2) {
            this.x = 560;
        } else if (this.game.playerSkinID == 3) {
            this.x = 930;
        }
        this.game.batch.draw(this.selected, this.x, this.y, SELECTED_BUTTON_WIDTH, SELECTED_BUTTON_HEIGHT);

        if (this.game.purchased6 && this.game.purchased9) {
            if (this.game.playerSkinID == 1) {
                this.x += 360;
                if (checkCollision(mouseX, mouseY)) {
                    this.game.batch.draw(this.selectOn, this.x, this.y, SELECTED_BUTTON_WIDTH, SELECTED_BUTTON_HEIGHT);
                    if(Gdx.input.isTouched()) {
                        this.game.playerSkinID = 2;
                        this.game.setSkin(this.game.playerSkinID);
                    }
                } else {
                    this.game.batch.draw(this.selectOff, this.x, this.y, SELECTED_BUTTON_WIDTH, SELECTED_BUTTON_HEIGHT);
                }

                this.x += 370;
                if (checkCollision(mouseX, mouseY)) {
                    this.game.batch.draw(this.selectOn, this.x, this.y, SELECTED_BUTTON_WIDTH, SELECTED_BUTTON_HEIGHT);
                    if(Gdx.input.isTouched()) {
                        this.game.playerSkinID = 3;
                        this.game.setSkin(this.game.playerSkinID);
                    }
                } else {
                    this.game.batch.draw(this.selectOff, this.x, this.y, SELECTED_BUTTON_WIDTH, SELECTED_BUTTON_HEIGHT);
                }
            } else if (this.game.playerSkinID == 2) {
                this.x -= 360;
                if (checkCollision(mouseX, mouseY)) {
                    this.game.batch.draw(this.selectOn, this.x, this.y, SELECTED_BUTTON_WIDTH, SELECTED_BUTTON_HEIGHT);
                    if(Gdx.input.isTouched()) {
                        this.game.playerSkinID = 1;
                        this.game.setSkin(this.game.playerSkinID);
                    }
                } else {
                    this.game.batch.draw(this.selectOff, this.x, this.y, SELECTED_BUTTON_WIDTH, SELECTED_BUTTON_HEIGHT);
                }

                this.x += 730;
                if (checkCollision(mouseX, mouseY)) {
                    this.game.batch.draw(this.selectOn, this.x, this.y, SELECTED_BUTTON_WIDTH, SELECTED_BUTTON_HEIGHT);
                    if (Gdx.input.isTouched()) {
                        this.game.playerSkinID = 3;
                        this.game.setSkin(this.game.playerSkinID);
                    }
                } else {
                    this.game.batch.draw(this.selectOff, this.x, this.y, SELECTED_BUTTON_WIDTH, SELECTED_BUTTON_HEIGHT);
                }

            } else if (this.game.playerSkinID == 3) {
                this.x -= 370;
                if (checkCollision(mouseX, mouseY)) {
                    this.game.batch.draw(this.selectOn, this.x, this.y, SELECTED_BUTTON_WIDTH, SELECTED_BUTTON_HEIGHT);
                    if(Gdx.input.isTouched()) {
                        this.game.playerSkinID = 2;
                        this.game.setSkin(this.game.playerSkinID);
                    }
                } else {
                    this.game.batch.draw(this.selectOff, this.x, this.y, SELECTED_BUTTON_WIDTH, SELECTED_BUTTON_HEIGHT);
                }

                this.x -= 360;
                if (checkCollision(mouseX, mouseY)) {
                    this.game.batch.draw(this.selectOn, this.x, this.y, SELECTED_BUTTON_WIDTH, SELECTED_BUTTON_HEIGHT);
                    if (Gdx.input.isTouched()) {
                        this.game.playerSkinID = 1;
                        this.game.setSkin(this.game.playerSkinID);
                    }
                } else {
                    this.game.batch.draw(this.selectOff, this.x, this.y, SELECTED_BUTTON_WIDTH, SELECTED_BUTTON_HEIGHT);
                }
            }
        } else if (this.game.purchased6) {
            if (this.game.playerSkinID == 1) {
                this.x += 360;
                if (checkCollision(mouseX, mouseY)) {
                    this.game.batch.draw(this.selectOn, this.x, this.y, SELECTED_BUTTON_WIDTH, SELECTED_BUTTON_HEIGHT);
                    if(Gdx.input.isTouched()) {
                        this.game.playerSkinID = 2;
                        this.game.setSkin(this.game.playerSkinID);
                    }
                } else {
                    this.game.batch.draw(this.selectOff, this.x, this.y, SELECTED_BUTTON_WIDTH, SELECTED_BUTTON_HEIGHT);
                }

                this.x += 370;
                if (checkCollision(mouseX, mouseY)) {
                    this.game.batch.draw(this.purchase9On, this.x, this.y, SELECTED_BUTTON_WIDTH, SELECTED_BUTTON_HEIGHT);
                    if(Gdx.input.isTouched()) {
                        // TODO MAKE SURE USER CAN AFFORD THIS AND MINUS COINS
                        this.game.purchased9 = true;
                        this.game.playerSkinID = 3;
                        this.game.setSkin(this.game.playerSkinID);
                    }
                } else {
                    this.game.batch.draw(this.purchase9Off, this.x, this.y, SELECTED_BUTTON_WIDTH, SELECTED_BUTTON_HEIGHT);
                }
            } else if (this.game.playerSkinID == 2) {
                this.x -= 360;
                if (checkCollision(mouseX, mouseY)) {
                    this.game.batch.draw(this.selectOn, this.x, this.y, SELECTED_BUTTON_WIDTH, SELECTED_BUTTON_HEIGHT);
                    if(Gdx.input.isTouched()) {
                        this.game.playerSkinID = 1;
                        this.game.setSkin(this.game.playerSkinID);
                    }
                } else {
                    this.game.batch.draw(this.selectOff, this.x, this.y, SELECTED_BUTTON_WIDTH, SELECTED_BUTTON_HEIGHT);
                }

                this.x += 730;
                if (checkCollision(mouseX, mouseY)) {
                    this.game.batch.draw(this.purchase9On, this.x, this.y, SELECTED_BUTTON_WIDTH, SELECTED_BUTTON_HEIGHT);
                    if (Gdx.input.isTouched()) {
                        // TODO MAKE SURE USER CAN AFFORD THIS AND MINUS COINS
                        this.game.purchased9 = true;
                        this.game.playerSkinID = 3;
                        this.game.setSkin(this.game.playerSkinID);
                    }
                } else {
                    this.game.batch.draw(this.purchase9Off, this.x, this.y, SELECTED_BUTTON_WIDTH, SELECTED_BUTTON_HEIGHT);
                }
            }

        } else if (this.game.purchased9) {
            if (this.game.playerSkinID == 1) {
                this.x += 360;
                if (checkCollision(mouseX, mouseY)) {
                    this.game.batch.draw(this.purchase6On, this.x, this.y, SELECTED_BUTTON_WIDTH, SELECTED_BUTTON_HEIGHT);
                    if(Gdx.input.isTouched()) {
                        // TODO MAKE SURE USER CAN AFFORD THIS AND MINUS COINS
                        this.game.purchased6 = true;
                        this.game.playerSkinID = 2;
                        this.game.setSkin(this.game.playerSkinID);
                    }
                } else {
                    this.game.batch.draw(this.purchase6Off, this.x, this.y, SELECTED_BUTTON_WIDTH, SELECTED_BUTTON_HEIGHT);
                }

                this.x += 370;
                if (checkCollision(mouseX, mouseY)) {
                    this.game.batch.draw(this.selectOn, this.x, this.y, SELECTED_BUTTON_WIDTH, SELECTED_BUTTON_HEIGHT);
                    if(Gdx.input.isTouched()) {
                        this.game.playerSkinID = 3;
                        this.game.setSkin(this.game.playerSkinID);
                    }
                } else {
                    this.game.batch.draw(this.selectOff, this.x, this.y, SELECTED_BUTTON_WIDTH, SELECTED_BUTTON_HEIGHT);
                }
            } else if (this.game.playerSkinID == 3) {
                this.x -= 370;
                if (checkCollision(mouseX, mouseY)) {
                    this.game.batch.draw(this.purchase6On, this.x, this.y, SELECTED_BUTTON_WIDTH, SELECTED_BUTTON_HEIGHT);
                    if(Gdx.input.isTouched()) {
                        // TODO MAKE SURE USER CAN AFFORD THIS AND MINUS COINS
                        this.game.purchased6 = true;
                        this.game.playerSkinID = 2;
                        this.game.setSkin(this.game.playerSkinID);
                    }
                } else {
                    this.game.batch.draw(this.purchase6Off, this.x, this.y, SELECTED_BUTTON_WIDTH, SELECTED_BUTTON_HEIGHT);
                }

                this.x -= 360;
                if (checkCollision(mouseX, mouseY)) {
                    this.game.batch.draw(this.purchase9On, this.x, this.y, SELECTED_BUTTON_WIDTH, SELECTED_BUTTON_HEIGHT);
                    if (Gdx.input.isTouched()) {
                        // TODO MAKE SURE USER CAN AFFORD THIS AND MINUS COINS
                        this.game.purchased9 = true;
                        this.game.playerSkinID = 3;
                        this.game.setSkin(this.game.playerSkinID);
                    }
                } else {
                    this.game.batch.draw(this.purchase9Off, this.x, this.y, SELECTED_BUTTON_WIDTH, SELECTED_BUTTON_HEIGHT);
                }
            }
        } else {
            this.x += 360;
            if (checkCollision(mouseX, mouseY)) {
                this.game.batch.draw(this.purchase6On, this.x, this.y, SELECTED_BUTTON_WIDTH, SELECTED_BUTTON_HEIGHT);
                if(Gdx.input.isTouched()) {
                    // TODO MAKE SURE USER CAN AFFORD THIS AND MINUS COINS
                    this.game.purchased6 = true;
                    this.game.playerSkinID = 2;
                    this.game.setSkin(this.game.playerSkinID);
                }
            } else {
                this.game.batch.draw(this.purchase6Off, this.x, this.y, SELECTED_BUTTON_WIDTH, SELECTED_BUTTON_HEIGHT);
            }

            this.x += 370;
            if (checkCollision(mouseX, mouseY)) {
                this.game.batch.draw(this.purchase9On, this.x, this.y, SELECTED_BUTTON_WIDTH, SELECTED_BUTTON_HEIGHT);
                if(Gdx.input.isTouched()) {
                    this.game.playerSkinID = 3;
                    this.game.setSkin(this.game.playerSkinID);
                }
            } else {
                this.game.batch.draw(this.purchase9Off, this.x, this.y, SELECTED_BUTTON_WIDTH, SELECTED_BUTTON_HEIGHT);
            }
        }


        // Draw Back Button
        int backButtonVerticalOffset = 25;
        int backButtonHorizontalOffset = 25;
        int verticalButtonHitboxOffset = game.heightScreen - 25 - BACK_BUTTON_HEIGHT;
        if ((mouseX > backButtonHorizontalOffset) && (mouseX < backButtonHorizontalOffset + BACK_BUTTON_WIDTH) && (mouseY > verticalButtonHitboxOffset) && (mouseY < verticalButtonHitboxOffset + BACK_BUTTON_HEIGHT)) {
            game.batch.draw(backButtonActive, backButtonHorizontalOffset, backButtonVerticalOffset, BACK_BUTTON_WIDTH, BACK_BUTTON_HEIGHT);
            if (Gdx.input.isTouched()) {
                this.dispose();
                game.setScreen(new MenuScreen(game));
            }
        } else {
            game.batch.draw(backButtonInactive, backButtonHorizontalOffset, backButtonVerticalOffset, BACK_BUTTON_WIDTH, BACK_BUTTON_HEIGHT);
        }

        return 0;
    }

    private boolean checkCollision(int mouseX, int mouseY) {
        int verticalOffset = this.game.heightScreen - this.y;
        if ((mouseX > this.x) && (mouseX < this.x + SELECTED_BUTTON_WIDTH) && (mouseY < verticalOffset) && (mouseY > verticalOffset - SELECTED_BUTTON_HEIGHT)) {
            return true;
        }
        return false;
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
