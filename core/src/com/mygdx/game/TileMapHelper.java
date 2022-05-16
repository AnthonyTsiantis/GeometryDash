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
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;

import static com.mygdx.game.GameScreen.PPM;

// This class is utilized to incorporate the game map
public class TileMapHelper {
    // Instantiate class variables
    private TiledMap tiledMap;
    private GameScreen gameScreen;

    // Constructor initializes game screen
    public TileMapHelper(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    // Setup map method used to create tiled map
    public OrthogonalTiledMapRenderer setupMap() {
        tiledMap = new TmxMapLoader().load("Maps/Level 1/Level1.tmx");
        parseMapObjects(tiledMap.getLayers().get("Objects").getObjects());
        return new OrthogonalTiledMapRenderer(tiledMap);
    }

    // This method is used to parse object from the tiled map and set player object
    private void parseMapObjects(MapObjects mapObjects) {
        for (MapObject mapObject : mapObjects) {
            if (mapObject instanceof PolygonMapObject) {
                createStaticBody((PolygonMapObject) mapObject);
            }

            if (mapObject instanceof RectangleMapObject) {
                Rectangle rectangle = ((RectangleMapObject) mapObject).getRectangle();
                String rectangleName = mapObject.getName();

                if (rectangleName.equals("player")) {
                    Body body = BodyHelperService.createBody(
                            rectangle.getX() + rectangle.getWidth() / 2,
                            rectangle.getY() + rectangle.getHeight() / 2,
                            rectangle.getWidth(),
                            rectangle.getHeight(),
                            false,
                            gameScreen.getWorld()
                    );
                    gameScreen.setPlayer(new Player(rectangle.getWidth(), rectangle.getHeight(), body));
                }
            }
        }
    }

    // Create a body for each object in map
    private void createStaticBody(PolygonMapObject polygonMapObject) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        Body body = gameScreen.getWorld().createBody(bodyDef);
        Shape shape = createPolygonShape(polygonMapObject);
        body.createFixture(shape, 1000);
        shape.dispose();
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
