package com.mygdx.game;
import com.badlogic.gdx.physics.box2d.*;
import java.io.IOException;
import java.util.Objects;

import static com.mygdx.game.GameScreen.PPM;

public class ContactListener implements com.badlogic.gdx.physics.box2d.ContactListener {
    Boot game;

    public ContactListener(Boot game) {
        this.game = game;
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

            if (id == 2) {
                float playerYPos = b.getBody().getPosition().y * PPM;
                float objectYPos = (a.getBody().getPosition().y + 5.014999f) * PPM;
                if (playerYPos < (objectYPos + 63)) {
                    this.endGame();
                }
            } else if (id == 4) {
                this.endGame();
            }
        }
    }

    private void endGame() {
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
