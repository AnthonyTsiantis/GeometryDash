package Objects.Player;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;

public abstract class GameEntity {
    protected float x, y, velocityX, velocityY, speed;
    protected float width, height;
    protected Body body;

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

    public abstract void update();

    public void render(OrthographicCamera camera, SpriteBatch batch) {


    }


    public Body getBody() {
        return body;
    }
}
