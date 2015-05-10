package com.nowhereinc.AirCommander.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.nowhereinc.AirCommander.game.WorldRenderer;
import com.nowhereinc.AirCommander.util.Constants;

public class Planes {
	
	private static final String TAG = WorldRenderer.class.getName();
	
	private TextureRegion Plane;
	
	// Body
	public Body body;
	
	// planes body
	public BodyDef bodyDefPlane;
	
	// plane fixture
	public FixtureDef fixtureDefPlane;
	
	// box size
	private float boxXSize;
	private float boxYSize;
	
	public Planes (World world, String planeNumber, float posX, float posY) {
		init(world, planeNumber, posX, posY);
	}
	
	private void init(World world, String planeNumber, float posX, float posY) {
		
		// create body def for player
		bodyDefPlane = new BodyDef();
		bodyDefPlane.position.set(posX, posY);
		bodyDefPlane.type = BodyDef.BodyType.DynamicBody;
		body = world.createBody(bodyDefPlane);
		
		PolygonShape shape = new PolygonShape();
		
		boxXSize = Constants.PLANEXSIZE;
		boxYSize = Constants.PLANEYSIZE;
		
		shape.setAsBox(boxXSize, boxYSize);
		
		fixtureDefPlane = new FixtureDef();
		fixtureDefPlane.shape = shape;
		fixtureDefPlane.restitution = 0;
		body.createFixture(fixtureDefPlane).setUserData(planeNumber);
		
		// set to inactive
		body.setActive(false);
		
		
		shape.dispose();
		
	}
	
	public Vector2 returnPlanePosition() {
		
		Vector2 planeCenter;
		
		planeCenter = new Vector2(0,0);
		
		planeCenter.x = this.body.getPosition().x + boxXSize;
		planeCenter.y = this.body.getPosition().y + boxYSize;
		
		return planeCenter;
				
	}
	
	public void update() {
		
	}
	
	public void setDeleteFlag() {
		
		body.setUserData("delete");

	}
	
	public boolean deletePlane(World world) {
		
		if(body.getUserData() == "delete") {
		
			// delete body
		
			body.setActive(false);
			body.setUserData(null);
			body = null;
			
			return true;
		
		}
		
		else {
			
			return false;
			
		}
		
	}

	public void render (SpriteBatch batch) {
			
		if (body.getUserData() != "delete" &&
			body.isActive() != false) {
					
			Vector2 position = this.body.getPosition();
				
			position.x = position.x - boxXSize;
			position.y = position.y - boxYSize;

			float rotation = (MathUtils.radiansToDegrees * this.body.getAngle());
			
			Plane = (TextureRegion)this.body.getUserData();
			batch.draw(Plane, position.x, position.y, 0, 0, boxXSize * 2, boxYSize * 2, 1, 1, rotation);
				
		}		
		
	}
	
}
