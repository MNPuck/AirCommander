package com.nowhereinc.AirCommander.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.nowhereinc.AirCommander.game.Assets;
import com.nowhereinc.AirCommander.game.WorldRenderer;
import com.nowhereinc.AirCommander.util.Constants;

public class Planes {
	
	private static final String TAG = WorldRenderer.class.getName();
	
	private TextureRegion Plane;
	
	// Body
	public Body body;
	
	// player body
	public BodyDef bodyDefPlane;
	
	// player fixture
	public FixtureDef fixtureDefPlane;
	
	// box size
	private float boxXSize;
	private float boxYSize;
	
	public Planes (World world) {
		init(world);
	}
	
	private void init(World world) {
		
		// create body def for player
		bodyDefPlane = new BodyDef();
		bodyDefPlane.type = BodyDef.BodyType.DynamicBody;
		body = world.createBody(bodyDefPlane);
		
		PolygonShape shape = new PolygonShape();
		
		boxXSize = Constants.PLANEXSIZE;
		boxYSize = Constants.PLANEYSIZE;
		
		shape.setAsBox(boxXSize, boxYSize);
		
		fixtureDefPlane = new FixtureDef();
		fixtureDefPlane.shape = shape;
		fixtureDefPlane.restitution = 0;
		
		shape.dispose();
		
	}

}
