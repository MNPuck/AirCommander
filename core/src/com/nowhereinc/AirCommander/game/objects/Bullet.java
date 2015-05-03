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
	
	public Bullet (World world, Vector2 playerPos) {
		init(world, playerPos);
	}
	
	private void init(World world, Vector2 PlayerPos) {
		
		// set texture
		Bullet = Assets.instance.bullet.bullet;
		
		// create body def for bullet
		bodyDefBullet = new BodyDef();
		bodyDefBullet.position.set(PlayerPos);
		bodyDefBullet.type = BodyDef.BodyType.DynamicBody;
		body = world.createBody(bodyDefBullet);
		
		// set user data
		body.setUserData(Bullet);
		
		// apply movement
		body.applyLinearImpulse(0, Constants.MAX_BULLET_VELOCITY, PlayerPos.x, PlayerPos.y, true);
	
		CircleShape shape = new CircleShape();
	
		shape.setRadius(.10f);
		
		boxXSize = .20f;
		boxYSize = .20f;
	
		fixtureDefBullet = new FixtureDef();
		fixtureDefBullet.shape = shape;
		fixtureDefBullet.restitution = 1;
		body.createFixture(fixtureDefBullet).setUserData("bullet");
		
		shape.dispose();
	
	}
	
	public void update (World world, float deltaTime) {
		
		Vector2 pos = this.body.getPosition();

		if (pos.y > Constants.GAMEBOARD_HEIGHT * .5) {
			
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
