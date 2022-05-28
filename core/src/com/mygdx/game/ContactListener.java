package com.mygdx.game;
import Objects.Player.Player;
import com.badlogic.gdx.physics.box2d.*;
import java.io.IOException;

import static com.mygdx.game.GameScreen.PPM;

public class ContactListener implements com.badlogic.gdx.physics.box2d.ContactListener {
    Boot game;
    GameScreen screen;
    private final float BOX_HEIGHT = 15.005f;

    public ContactListener(Boot game, GameScreen screen) {
        this.game = game;
        this.screen = screen;
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture a = contact.getFixtureA(); // Object Body
        Fixture b = contact.getFixtureB(); // Player Body

        short id;
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

            // Box
            if (id == 2) {
                float playerYPos = a.getBody().getPosition().y * PPM;
                float objectYPos = (b.getBody().getPosition().y + BOX_HEIGHT) * PPM;

                if (playerYPos < objectYPos) {
                    this.endGame();
                }

            } else if (id == 4 || id == 16) {
                this.endGame();
            }
        }
    }

    private void endGame() {
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
