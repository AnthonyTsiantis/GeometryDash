package Objects.Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

// PPM Constant (Pixel's per meter) is used to convert digital data to a real world scale
import static com.mygdx.game.GameScreen.PPM;

// Player class creates game player
public class Player extends GameEntity {
    // Initialize counter to 0, counter keeps track of amount of consecutive jumps
    private int counter = 0;
    public float xPos, yPos;

    // Player Constructor class creates player object
    public Player(float width, float height, Body body) {
        super(width, height, body);
        this.speed = 4f;
    }

    // Update method updates player's position and checks for input
    @Override
    public void update() {
        this.xPos = body.getPosition().x * PPM;
        this.yPos = body.getPosition().y * PPM;
        checkUserInput();
    }

    // Render method renders game
    public void render(OrthographicCamera camera, SpriteBatch batch) {
        super.render(camera, batch);
    }

    // Check input method, checks user input
    private void checkUserInput() {
        // if space is pressed and counter is zero, apply a jump force and increase counter
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && counter == 0) {
            float force = body.getMass() * 25;
            body.applyLinearImpulse(new Vector2(0, force), body.getPosition(), true);
            counter++;
        }

        // When the user lands, reset the counter
        if(body.getLinearVelocity().y == 0) {
            counter = 0;
        }

        // Set linear velocity
        body.setLinearVelocity(0, body.getLinearVelocity().y < 25 ? body.getLinearVelocity().y : 25);
    }
}
