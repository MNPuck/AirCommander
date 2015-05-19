package com.nowhereinc.AirCommander.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.nowhereinc.AirCommander.game.Assets;
import com.nowhereinc.AirCommander.game.WorldController;
import com.nowhereinc.AirCommander.game.WorldRenderer;
import com.nowhereinc.AirCommander.util.Constants;

public class Bullet {
	
	private static final String TAG = WorldRenderer.class.getName();
	
	private TextureRegion Bullet;
	
	// Body
	public Body body;
	
	// bullet body
	public BodyDef bodyDefBullet;
	
	// bullet fixture
	public FixtureDef fixtureDefBullet;
	
	// box size
	private float boxXSize;
	private float boxYSize;
;
	
	public Bullet (World world, Vector2 planePosition, 
			       float bulletSpeed, int bulletColor, int bulletDirection) {
		init(world, planePosition, bulletSpeed, bulletColor, bulletDirection);
	}
	
	private void init(World world, Vector2 planePosition, float bulletSpeed, int bulletColor, int bulletDirection) {
		
		// set texture
		
		if (bulletColor == 1) {
		
			Bullet = Assets.instance.bullet1.bullet1;
		
		}
		
		if (bulletColor == 2) {
			
			Bullet = Assets.instance.bullet2.bullet2;
		
		}
		
		// set box size here to use in position set
		boxXSize = .20f;
		boxYSize = .20f;
		
		// create Vector2 to adjust postion to the bullets center
		Vector2 adjustedPosition = planePosition;
		
		adjustedPosition.x -= boxXSize * .5f;
		adjustedPosition.y -= boxYSize * .5f;
		
		// create body def for bullet
		bodyDefBullet = new BodyDef();
		bodyDefBullet.position.set(adjustedPosition);
		bodyDefBullet.type = BodyDef.BodyType.DynamicBody;
		body = world.createBody(bodyDefBullet);
		
		// set user data
		body.setUserData(Bullet);
		
		// set to bullet property to true
		body.setBullet(true);
	
		CircleShape shape = new CircleShape();
	
		shape.setRadius(.10f);
	
		fixtureDefBullet = new FixtureDef();
		fixtureDefBullet.shape = shape;
		fixtureDefBullet.restitution = 1;
		
		if (bulletColor == 1) {
			
			body.createFixture(fixtureDefBullet).setUserData("bullet1");
		
		}
		
		if (bulletColor == 2) {
			
			body.createFixture(fixtureDefBullet).setUserData("bullet2");
		
		}
		
		shape.dispose();
		
		switch (bulletDirection) {
		
			case Constants.N:
				body.applyLinearImpulse(0, bulletSpeed, planePosition.x, planePosition.y, true);
				break;
			
			case Constants.NE:
				break;
			
			case Constants.NW:
				break;
			
			case Constants.E:
				body.applyLinearImpulse(bulletSpeed * .5f, - bulletSpeed, planePosition.x, planePosition.y, true);
				break;
			
			case Constants.W:
				body.applyLinearImpulse(- bulletSpeed * .5f, - bulletSpeed, planePosition.x, planePosition.y, true);
				break;
			
			case Constants.S:
				body.applyLinearImpulse(0, - bulletSpeed, planePosition.x, planePosition.y, true);
				break;
			
			case Constants.SW:
				body.applyLinearImpulse(- bulletSpeed * .25f, - bulletSpeed, planePosition.x, planePosition.y, true);
				break;
			
			case Constants.SE:
				body.applyLinearImpulse(bulletSpeed * .25f, - bulletSpeed, planePosition.x, planePosition.y, true);
				break;
			
			case Constants.NIL:
				break;
				
		}
	
	}
	
	public void update (World world, float deltaTime, Vector2 cameraPosition) {
		
		Vector2 pos = this.body.getPosition();

		if (pos.y >= (cameraPosition.y + (Constants.GAMEBOARD_HEIGHT * .5f))) {
			
			setDeleteFlag();
			
		}
		
		if (pos.y <= (cameraPosition.y - (Constants.GAMEBOARD_HEIGHT * .5f))) {
			
			setDeleteFlag();
			
		}
		
		if (pos.x >= Constants.GAMEBOARD_WIDTH * .5f) {
			
			setDeleteFlag();
			
		}
		
		if (pos.x <= - Constants.GAMEBOARD_WIDTH * .5f) {
			
			setDeleteFlag();
			
		}
		
		
		if (body.getFixtureList().first().getUserData() == "delete") {
			
			setDeleteFlag();
			
		}
		
	}
	
	public void setDeleteFlag() {
		
		// delete body
		
		body.setUserData("delete");
		
	}
	
	public boolean deleteBullet(World world) {
		
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
		
		if (body.getUserData() != "delete") {
		
			Vector2 position = this.body.getPosition();
			float rotation = (MathUtils.radiansToDegrees * this.body.getAngle());
		
			Bullet = (TextureRegion)this.body.getUserData();
			batch.draw(Bullet, position.x, position.y, 0, 0, boxXSize, boxYSize, 1, 1, rotation);
		
		}
	
	}

}
