package com.nowhereinc.AirCommander.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.nowhereinc.AirCommander.game.Assets;
import com.nowhereinc.AirCommander.game.WorldRenderer;
import com.nowhereinc.AirCommander.util.Constants;

public class Plane {
	
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
	
	public Plane (World world, String planeNumber, float posX, float posY) {
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
		
		switch (planeNumber) {
		
			case "plane1":
				body.setUserData(Assets.instance.plane1.plane1);
				break;
				
			case "plane2":
				body.setUserData(Assets.instance.plane2.plane2);
				break;
				
			case "plane3":
				body.setUserData(Assets.instance.plane3.plane3);
				break;
				
			case "plane4":
				body.setUserData(Assets.instance.plane4.plane4);
				break;
				
			case "plane5":
				body.setUserData(Assets.instance.plane5.plane5);
				break;
				
			case "plane6":
				body.setUserData(Assets.instance.plane6.plane6);
				break;
				
			case "plane7":
				body.setUserData(Assets.instance.plane7.plane7);
				break;
				
			case "plane8":
				body.setUserData(Assets.instance.plane8.plane8);
				break;
				
			case "plane9":
				body.setUserData(Assets.instance.plane9.plane9);
				break;
				
			case "plane10":
				body.setUserData(Assets.instance.plane10.plane10);
				break;
		
		}
		
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
	
	public void update(float deltaTime, Vector2 cameraPosition) {
		
		if (body.getFixtureList().first().getUserData() == "delete") {
			
			setDeleteFlag();
			
		}
		
		if (body.getPosition().y < (cameraPosition.y + (Constants.GAMEBOARD_WIDTH / 2))) {
			
			body.setActive(true);
		}
		
		if (body.isActive()) {
		
			this.body.setLinearVelocity(0, deltaTime * Constants.SCROLL_SPEED * Constants.OBJECT_SCROLL_ADJUSTMENT);
			
		}
	
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
