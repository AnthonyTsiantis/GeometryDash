package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.*;
import java.io.IOException;

import static com.mygdx.game.GameScreen.PPM;

// Contact listener class used to determine player collisions
public class ContactListener implements com.badlogic.gdx.physics.box2d.ContactListener {
    // Initialize class variables
    public Boot game;
    public GameScreen screen;
    private final float BOX_HEIGHT = 15.005f;

    // Constructor class populates variables
    public ContactListener(Boot game, GameScreen screen) {
        this.game = game;
        this.screen = screen;
    }

    // Begin contact method is called when a collision is detected between two bodies
    @Override
    public void beginContact(Contact contact) {
        // Get the fixtures of body a and body b
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();

        // Create id variable used to keep track of object (triangle, box, ground, support)
        short id;

        // Determine which box is the player and which is the object
        if (a != null && b != null ) {
            Filter aData = a.getFilterData();
            Filter bData = b.getFilterData();
            if (aData.categoryBits == 1) {
                id = bData.categoryBits;
            } else if (bData.categoryBits == 1) {
                id = aData.categoryBits;
            } else {
                id = 0;
            }

            // Box Collision
            if (id == 2) {
                // Get the position of player and object
                float tempA = a.getBody().getPosition().y;
                float tempB = b.getBody().getPosition().y;
                float playerYPos;
                float objectYPos;
                if (tempA < tempB) {
                    playerYPos = tempB * PPM;
                    objectYPos = (tempA + BOX_HEIGHT) * PPM;
                } else {
                    playerYPos = tempA * PPM;
                    objectYPos = (tempB + BOX_HEIGHT) * PPM;
                }

                // Determine if player box collision is valid
                if (playerYPos < objectYPos) {
                    this.endGame();
                }

            // Triangle and support collision
            } else if (id == 4 || id == 16) {
                this.endGame();
            }
        }
    }

    // End game method calls the end game screen
    private void endGame() {
        this.game.wealth += this.screen.collectedCoins;
        game.audio.stopMusic(game.currentScreen);
        game.currentScreen = "Game Over";
        game.audio.playMusic(game.currentScreen);
        try {
            game.setScreen(new GameOver(game));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
