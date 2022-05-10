package Objects.Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import static com.mygdx.game.GameScreen.PPM;

public class Player extends GameEntity {
    private int counter = 0;

    public Player(float width, float height, Body body) {
        super(width, height, body);
        this.speed = 4f;
    }

    @Override
    public void update() {
        x = body.getPosition().x * PPM;
        y = body.getPosition().y * PPM;
        checkUserInput();
    }

    public void render(OrthographicCamera camera, SpriteBatch batch) {

        super.render(camera, batch);
    }

    private void checkUserInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && counter == 0) {
            float force = body.getMass() * 25;
            body.applyLinearImpulse(new Vector2(0, force),body.getPosition(), true);
            counter++;
        }

        if(body.getLinearVelocity().y == 0) {
            counter = 0;
        }

        body.setLinearVelocity(0, body.getLinearVelocity().y < 25 ? body.getLinearVelocity().y : 25);

    }
}
