package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class SkinsMenu implements Screen {
    // Instantiate variables
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
    public FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;

    // Constructor Class used to populate game variables and set font
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
        this.setFont();
    }

    // alters game font appertaining to this class
    private void setFont() {
        this.fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        this.fontParameter.size = 50;
        this.fontParameter.borderWidth = 5;
        this.fontParameter.borderColor = Color.BLACK;
        this.fontParameter.color = Color.GREEN;
        this.game.font = this.game.fontGenerator.generateFont(this.fontParameter);
    }
    @Override
    public void show() {

    }

    // Render method used to render this class to the screen
    @Override
    public void render(float delta) {
        // Clear the screen
        Gdx.gl.glClearColor(0.3f, 0.0f, 0.9f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Create batch and draw to it
        game.batch.begin();
        game.batch.draw(skinsMenuBackground, 0, 0, game.widthScreen, game.heightScreen); // Draw skins background
        // Draw buttons
        this.drawButtons();

        // Create and determine the x offset for each button
        int xOffset;
        if (game.wealth < 10) {
            xOffset = 200;
        } else if (game.wealth < 100) {
            xOffset = 225;
        } else {
            xOffset = 275;
        }
        game.font.draw(game.batch, "Coins: " + game.wealth, this.game.widthScreen - xOffset, 50); // Draw the amount of coins the player has
        game.batch.end(); // End the sprite batch
    }

    // Draw buttons class is used to determine which buttons to draw, where to draw them and when
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

        // Draw the selected button
        this.game.batch.draw(this.selected, this.x, this.y, SELECTED_BUTTON_WIDTH, SELECTED_BUTTON_HEIGHT);

        // If the player has purchased both skins
        if (this.game.purchased6 && this.game.purchased9) {
            // and the player has selected skin #1
            if (this.game.playerSkinID == 1) {
                // Adjust the x offset
                this.x += 360;

                // If the mouse is hovering over the second skin
                if (checkCollision(mouseX, mouseY)) {
                    // draw the yellow select button
                    this.game.batch.draw(this.selectOn, this.x, this.y, SELECTED_BUTTON_WIDTH, SELECTED_BUTTON_HEIGHT);
                    // If the mouse is clicked, change player skin
                    if(Gdx.input.isTouched()) {
                        this.game.playerSkinID = 2;
                        this.game.setSkin(this.game.playerSkinID);
                    }
                } else {
                    // If not, draw the grey button
                    this.game.batch.draw(this.selectOff, this.x, this.y, SELECTED_BUTTON_WIDTH, SELECTED_BUTTON_HEIGHT);
                }

                // Adjust the x offset
                this.x += 370;
                // If the mouse is hovering over the third skin
                if (checkCollision(mouseX, mouseY)) {
                    // draw the yellow select button
                    this.game.batch.draw(this.selectOn, this.x, this.y, SELECTED_BUTTON_WIDTH, SELECTED_BUTTON_HEIGHT);
                    // If the mouse is clicked, change player skin
                    if(Gdx.input.isTouched()) {
                        this.game.playerSkinID = 3;
                        this.game.setSkin(this.game.playerSkinID);
                    }
                } else {
                    // If not, display the grey button
                    this.game.batch.draw(this.selectOff, this.x, this.y, SELECTED_BUTTON_WIDTH, SELECTED_BUTTON_HEIGHT);
                }
            // If the player has the second skin selected
            } else if (this.game.playerSkinID == 2) {
                // Adjust the x offset
                this.x -= 360;
                // Determine if the mouse is hovering over the first button
                if (checkCollision(mouseX, mouseY)) {
                    // Draw the yellow button
                    this.game.batch.draw(this.selectOn, this.x, this.y, SELECTED_BUTTON_WIDTH, SELECTED_BUTTON_HEIGHT);
                    // If the button is selected, change the player skin
                    if(Gdx.input.isTouched()) {
                        this.game.playerSkinID = 1;
                        this.game.setSkin(this.game.playerSkinID);
                    }
                } else {
                    // If not, display the grey button
                    this.game.batch.draw(this.selectOff, this.x, this.y, SELECTED_BUTTON_WIDTH, SELECTED_BUTTON_HEIGHT);
                }

                // Adjust the x offset
                this.x += 730;
                // If the mouse is hovering over the button
                if (checkCollision(mouseX, mouseY)) {
                    // Draw the yellow button
                    this.game.batch.draw(this.selectOn, this.x, this.y, SELECTED_BUTTON_WIDTH, SELECTED_BUTTON_HEIGHT);
                    // If the mouse is pressed change the skin
                    if (Gdx.input.isTouched()) {
                        this.game.playerSkinID = 3;
                        this.game.setSkin(this.game.playerSkinID);
                    }
                } else {
                    // If not, display the grey button
                    this.game.batch.draw(this.selectOff, this.x, this.y, SELECTED_BUTTON_WIDTH, SELECTED_BUTTON_HEIGHT);
                }
            // If the player skin is set to #3
            } else if (this.game.playerSkinID == 3) {
                // Adjust the x offset
                this.x -= 370;
                // If the mouse is hovering over the second skin button
                if (checkCollision(mouseX, mouseY)) {
                    // Draw the yellow button
                    this.game.batch.draw(this.selectOn, this.x, this.y, SELECTED_BUTTON_WIDTH, SELECTED_BUTTON_HEIGHT);
                    // If the mouse is pressed, change the skin
                    if(Gdx.input.isTouched()) {
                        this.game.playerSkinID = 2;
                        this.game.setSkin(this.game.playerSkinID);
                    }
                } else {
                    // If not, display the grey button
                    this.game.batch.draw(this.selectOff, this.x, this.y, SELECTED_BUTTON_WIDTH, SELECTED_BUTTON_HEIGHT);
                }

                // Adjust the x offset
                this.x -= 360;
                // If the mouse is hovering over the first button
                if (checkCollision(mouseX, mouseY)) {
                    // Draw the yellow button
                    this.game.batch.draw(this.selectOn, this.x, this.y, SELECTED_BUTTON_WIDTH, SELECTED_BUTTON_HEIGHT);
                    // If the mouse is pressed, change the skin
                    if (Gdx.input.isTouched()) {
                        this.game.playerSkinID = 1;
                        this.game.setSkin(this.game.playerSkinID);
                    }
                } else {
                    // If not, display the grey button
                    this.game.batch.draw(this.selectOff, this.x, this.y, SELECTED_BUTTON_WIDTH, SELECTED_BUTTON_HEIGHT);
                }
            }
        // If the player has only purchased the six coin skin
        } else if (this.game.purchased6) {
            if (this.game.playerSkinID == 1) {
                // Adjust the x offset
                this.x += 360;

                // If the mouse is hovering over the second skin
                if (checkCollision(mouseX, mouseY)) {
                    // Draw yellow select button
                    this.game.batch.draw(this.selectOn, this.x, this.y, SELECTED_BUTTON_WIDTH, SELECTED_BUTTON_HEIGHT);
                    // If the mouse is clicked, change the player skin
                    if(Gdx.input.isTouched()) {
                        this.game.playerSkinID = 2;
                        this.game.setSkin(this.game.playerSkinID);
                    }
                } else {
                    // If not, display the grey button
                    this.game.batch.draw(this.selectOff, this.x, this.y, SELECTED_BUTTON_WIDTH, SELECTED_BUTTON_HEIGHT);
                }

                // Adjust the x offset
                this.x += 370;

                // Check is mouse is hovering over the purchase button and the player can afford it
                if (checkCollision(mouseX, mouseY) && this.game.wealth >= 9) {
                    // Display the yellow button
                    this.game.batch.draw(this.purchase9On, this.x, this.y, SELECTED_BUTTON_WIDTH, SELECTED_BUTTON_HEIGHT);
                    // If the player clicks, subtract their wealth, purchase the skin and change their skin
                    if(Gdx.input.isTouched()) {
                        this.game.wealth -= 9;
                        this.game.purchased9 = true;
                        this.game.playerSkinID = 3;
                        this.game.setSkin(this.game.playerSkinID);
                    }
                } else {
                    // If not, display the grey button
                    this.game.batch.draw(this.purchase9Off, this.x, this.y, SELECTED_BUTTON_WIDTH, SELECTED_BUTTON_HEIGHT);
                }

            // If the player skin is set to 2
            } else if (this.game.playerSkinID == 2) {
                // Adjust the x offset
                this.x -= 360;
                // Check if the mouse is hovering over the first button
                if (checkCollision(mouseX, mouseY)) {
                    // Draw the yellow button
                    this.game.batch.draw(this.selectOn, this.x, this.y, SELECTED_BUTTON_WIDTH, SELECTED_BUTTON_HEIGHT);
                    // If the mouse is clicked, select that skin
                    if(Gdx.input.isTouched()) {
                        this.game.playerSkinID = 1;
                        this.game.setSkin(this.game.playerSkinID);
                    }
                } else {
                    // If not, display the grey button
                    this.game.batch.draw(this.selectOff, this.x, this.y, SELECTED_BUTTON_WIDTH, SELECTED_BUTTON_HEIGHT);
                }

                // Adjust the x offset
                this.x += 730;
                // If the mouse is hovering over the 9 coin skin and the player can afford it
                if (checkCollision(mouseX, mouseY) && this.game.wealth >= 9) {
                    // Draw the yellow purchase button
                    this.game.batch.draw(this.purchase9On, this.x, this.y, SELECTED_BUTTON_WIDTH, SELECTED_BUTTON_HEIGHT);
                    // If the user selects the button, purchase the skin
                    if (Gdx.input.isTouched()) {
                        this.game.wealth -= 9;
                        this.game.purchased9 = true;
                        this.game.playerSkinID = 3;
                        this.game.setSkin(this.game.playerSkinID);
                    }
                } else {
                    // If not, display the grey button
                    this.game.batch.draw(this.purchase9Off, this.x, this.y, SELECTED_BUTTON_WIDTH, SELECTED_BUTTON_HEIGHT);
                }
            }

        // If the player has only purchased the 9 coin skin
        } else if (this.game.purchased9) {
            // If the skin id is set to 1
            if (this.game.playerSkinID == 1) {
                // Adjust the x offset
                this.x += 360;
                // Check is the mouse is hovering over the 6 coin skin and the player can afford that skin
                if (checkCollision(mouseX, mouseY) && this.game.wealth >= 6) {
                    // Draw the yellow purchase 6 button
                    this.game.batch.draw(this.purchase6On, this.x, this.y, SELECTED_BUTTON_WIDTH, SELECTED_BUTTON_HEIGHT);
                    // If the button is selected, purchase the skin and set it
                    if(Gdx.input.isTouched()) {
                        this.game.wealth -= 6;
                        this.game.purchased6 = true;
                        this.game.playerSkinID = 2;
                        this.game.setSkin(this.game.playerSkinID);
                    }
                } else {
                    // If not, display the grey button
                    this.game.batch.draw(this.purchase6Off, this.x, this.y, SELECTED_BUTTON_WIDTH, SELECTED_BUTTON_HEIGHT);
                }

                // Adjust the x offset
                this.x += 370;
                // If the mouse is hovering over the button
                if (checkCollision(mouseX, mouseY)) {
                    // Make it yellow
                    this.game.batch.draw(this.selectOn, this.x, this.y, SELECTED_BUTTON_WIDTH, SELECTED_BUTTON_HEIGHT);
                    // If the button is selected, set the skin
                    if(Gdx.input.isTouched()) {
                        this.game.playerSkinID = 3;
                        this.game.setSkin(this.game.playerSkinID);
                    }
                } else {
                    // If not, display the grey button
                    this.game.batch.draw(this.selectOff, this.x, this.y, SELECTED_BUTTON_WIDTH, SELECTED_BUTTON_HEIGHT);
                }
            // If the player has selected the 3rd skin
            } else if (this.game.playerSkinID == 3) {
                // Adjust the x offset
                this.x -= 370;
                // Check is the mouse is hovering over the 6 coin skin and the player can afford that skin
                if (checkCollision(mouseX, mouseY) && this.game.wealth >= 6) {
                    // Draw the yellow purchase 6 button
                    this.game.batch.draw(this.purchase6On, this.x, this.y, SELECTED_BUTTON_WIDTH, SELECTED_BUTTON_HEIGHT);
                    // If the button is selected, purchase the skin and set it
                    if(Gdx.input.isTouched()) {
                        this.game.wealth -= 6;
                        this.game.purchased6 = true;
                        this.game.playerSkinID = 2;
                        this.game.setSkin(this.game.playerSkinID);
                    }
                } else {
                    // If not, display the grey button
                    this.game.batch.draw(this.purchase6Off, this.x, this.y, SELECTED_BUTTON_WIDTH, SELECTED_BUTTON_HEIGHT);
                }

                // Adjust the x offset
                this.x -= 360;
                // If the mouse is hovering over the first skin
                if (checkCollision(mouseX, mouseY)) {
                    // Draw the yellow select button
                    this.game.batch.draw(this.selectOn, this.x, this.y, SELECTED_BUTTON_WIDTH, SELECTED_BUTTON_HEIGHT);
                    // If the mouse is clicked, set the skin
                    if (Gdx.input.isTouched()) {
                        this.game.playerSkinID = 1;
                        this.game.setSkin(this.game.playerSkinID);
                    }
                } else {
                    // If not, display the grey button
                    this.game.batch.draw(this.selectOff, this.x, this.y, SELECTED_BUTTON_WIDTH, SELECTED_BUTTON_HEIGHT);
                }
            }
        } else {
            // Adjust the x offset
            this.x += 360;
            // If the mouse is hovering over the purchase 6 button and the user can afford it
            if (checkCollision(mouseX, mouseY) && this.game.wealth >= 6) {
                // Draw the yellow purchase 6 button
                this.game.batch.draw(this.purchase6On, this.x, this.y, SELECTED_BUTTON_WIDTH, SELECTED_BUTTON_HEIGHT);
                // If the button is selected, subtract coins, purchase the skin and set it
                if(Gdx.input.isTouched()) {
                    this.game.wealth -= 6;
                    this.game.purchased6 = true;
                    this.game.playerSkinID = 2;
                    this.game.setSkin(this.game.playerSkinID);
                }
            } else {
                // If not, display the grey button
                this.game.batch.draw(this.purchase6Off, this.x, this.y, SELECTED_BUTTON_WIDTH, SELECTED_BUTTON_HEIGHT);
            }

            // Adjust the x offset
            this.x += 370;
            // If the mouse is hovering over the purchase 9 button and the user can afford it
            if (checkCollision(mouseX, mouseY) && this.game.wealth >= 9) {
                // Display yellow button
                this.game.batch.draw(this.purchase9On, this.x, this.y, SELECTED_BUTTON_WIDTH, SELECTED_BUTTON_HEIGHT);
                // If the button is selected, purchase the skin and set it
                if(Gdx.input.isTouched()) {
                    this.game.wealth -= 9;
                    this.game.purchased9 = true;
                    this.game.playerSkinID = 3;
                    this.game.setSkin(this.game.playerSkinID);
                }
            } else {
                // If not, display the grey button
                this.game.batch.draw(this.purchase9Off, this.x, this.y, SELECTED_BUTTON_WIDTH, SELECTED_BUTTON_HEIGHT);
            }
        }


        // Draw Back Button
        int backButtonVerticalOffset = 25;
        int backButtonHorizontalOffset = 25;
        int verticalButtonHitboxOffset = game.heightScreen - 25 - BACK_BUTTON_HEIGHT;
        // If the mouse is hovering over the back button
        if ((mouseX > backButtonHorizontalOffset) && (mouseX < backButtonHorizontalOffset + BACK_BUTTON_WIDTH) && (mouseY > verticalButtonHitboxOffset) && (mouseY < verticalButtonHitboxOffset + BACK_BUTTON_HEIGHT)) {
            // Draw the yellow back button
            game.batch.draw(backButtonActive, backButtonHorizontalOffset, backButtonVerticalOffset, BACK_BUTTON_WIDTH, BACK_BUTTON_HEIGHT);
            // If the mouse is clicked, dispose and set new menu screen
            if (Gdx.input.isTouched()) {
                this.dispose();
                game.setScreen(new MenuScreen(game));
            }
        } else {
            // If not, display the grey button
            game.batch.draw(backButtonInactive, backButtonHorizontalOffset, backButtonVerticalOffset, BACK_BUTTON_WIDTH, BACK_BUTTON_HEIGHT);
        }
        return 0;
    }

    // Check Collision function is used to determine if the mouse is in contact with a button
    private boolean checkCollision(int mouseX, int mouseY) {
        int verticalOffset = this.game.heightScreen - this.y;
        return (mouseX > this.x) && (mouseX < this.x + SELECTED_BUTTON_WIDTH) && (mouseY < verticalOffset) && (mouseY > verticalOffset - SELECTED_BUTTON_HEIGHT);
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
