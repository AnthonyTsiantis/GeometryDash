package Objects.Player;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;

// Create basic game physics
public abstract class GameEntity {
    // Intilize variables
    protected float x, y, velocityX, velocityY, speed;
    protected float width, height;
    protected Body body;

    // Constructor to populate variables
    public GameEntity(float width, float height, Body body) {
        this.x = body.getPosition().x;
        this.y = body.getPosition().y;
        this.width = width;
        this.height = height;
        this.body = body;
        this.velocityX = 0;
        this.velocityY = 0;
        this.speed = 0;
    }

    // Update method
    public abstract void update();

    // Render method
    public void render(OrthographicCamera camera, SpriteBatch batch) {

    }

    // Body getter
    public Body getBody() {
        return body;
    }
}
