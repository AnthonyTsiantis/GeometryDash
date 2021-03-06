package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.*;

import static com.mygdx.game.GameScreen.PPM;

// Body Service helper is used to create body physics in game

public class BodyHelperService {
    // create body method returns a body with the following defined aspects
    public static Body createBody(float x, float y, float width, float height, boolean isStatic, World world) {
        BodyDef bodyDef  = new BodyDef();
        bodyDef.type = isStatic ?  BodyDef.BodyType.StaticBody : BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x / PPM, y / PPM);
        bodyDef.fixedRotation = true;
        Body body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2 / PPM, height / 2 / PPM);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.friction = 0;
        body.createFixture(fixtureDef);
        shape.dispose();
        return body;
    }
}
