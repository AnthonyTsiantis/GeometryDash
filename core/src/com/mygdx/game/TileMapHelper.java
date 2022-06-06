package com.mygdx.game;

import Objects.Player.Player;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import static com.mygdx.game.GameScreen.PPM;

// This class is utilized to incorporate the game map
public class TileMapHelper {
    // Instantiate class variables
    private TiledMap tiledMap;
    private GameScreen gameScreen;

    // Constants for collision detection (Must be in powers of 2)
    private final short BIT_PLAYER = 1;
    private final short BIT_BOX = 2;
    private final short BIT_TRIANGLE = 4;
    private final short BIT_GROUND = 8;
    private final short BIT_SUPPORT = 16;
    private Boot game;


    // Constructor initializes game screen
    public TileMapHelper(GameScreen gameScreen, Boot game) {
        this.gameScreen = gameScreen;
        this.game = game;
    }

    // Setup map method used to create tiled map
    public OrthogonalTiledMapRenderer setupMap(int level) {
        if (level == 1) {
            tiledMap = new TmxMapLoader().load("Levels/Level 1/Level1.tmx");
        } else if (level == 2) {
            tiledMap = new TmxMapLoader().load("Levels/Level 2/Level 2.tmx");
        } else if (level == 3) {
            tiledMap = new TmxMapLoader().load("Levels/Level 3/level3.tmx");
        } else if (level == 4) {
            tiledMap = new TmxMapLoader().load("Levels/Level 4/level4.tmx");
        }
        parseMapObjects(tiledMap.getLayers().get("Objects").getObjects());
        return new OrthogonalTiledMapRenderer(tiledMap);
    }

    // This method is used to parse object from the tiled map and set player object
    private void parseMapObjects(MapObjects mapObjects) {
        // Parse all objects
        for (MapObject mapObject : mapObjects) {
            // If the object is a polygon map object create a static body
            if (mapObject instanceof PolygonMapObject) {
                createStaticBody((PolygonMapObject) mapObject);
            }

            // If it is a rectangle map object, create a rectangle object
            if (mapObject instanceof RectangleMapObject) {
                Rectangle rectangle = ((RectangleMapObject) mapObject).getRectangle();
                String rectangleName = mapObject.getName();

                // If the rectangle object has the player name then create new player object
                if (rectangleName.equals("player")) {
                    Body body = BodyHelperService.createBody(
                            rectangle.getX() + rectangle.getWidth() / 2,
                            rectangle.getY() + rectangle.getHeight() / 2,
                            rectangle.getWidth(),
                            rectangle.getHeight(),
                            false,
                            gameScreen.getWorld()
                    );
                    gameScreen.setPlayer(new Player(rectangle.getWidth(), rectangle.getHeight(), body, this.game));
                }
            }
        }
    }

    // Create a body for each object in map
    private void createStaticBody(PolygonMapObject polygonMapObject) {
        // Create new body def
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        Body body = gameScreen.getWorld().createBody(bodyDef);
        Shape shape = createPolygonShape(polygonMapObject);

        // Create fixture def and get body name
        String name = polygonMapObject.getName();
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1000f;

        // As long as the name is not null adjust category bits and mask bits for contact detection
        if (name != null) {
            switch (name) {
                case "triangle":
                    fixtureDef.filter.categoryBits = BIT_TRIANGLE; // It is a triangle
                    fixtureDef.filter.maskBits = BIT_PLAYER; // It can collide with player
                    break;
                case "box":
                    fixtureDef.filter.categoryBits = BIT_BOX; // It is a box
                    fixtureDef.filter.maskBits = BIT_PLAYER; // It can collide with player
                    break;
                case "ground":
                    fixtureDef.filter.categoryBits = BIT_GROUND; // It is the ground
                    fixtureDef.filter.maskBits = BIT_PLAYER; // It can collide with player
                    break;
                case "support":
                    fixtureDef.filter.categoryBits = BIT_SUPPORT; // It is the supporting structure
                    fixtureDef.filter.maskBits = BIT_PLAYER; // It can collide with player
                    break;
            }
        }
        body.createFixture(fixtureDef); // Create fixture
        shape.dispose(); // Dispose
    }

    // Create player object with polygon shape and return shape
    private Shape createPolygonShape(PolygonMapObject polygonMapObject) {
        // Get player vertices and transform
        float[] vertices = polygonMapObject.getPolygon().getTransformedVertices();
        int len = vertices.length / 2;
        Vector2[] worldVertices = new Vector2[len];
        for (int i = 0; i < len; i++) {
            Vector2 current = new Vector2(vertices[i * 2] / PPM, vertices[i * 2 + 1] / PPM);
            worldVertices[i] = current;
        }

        // Apply transformed vertices to shape and return
        PolygonShape shape = new PolygonShape();
        shape.set(worldVertices);
        return shape;
    }

}
