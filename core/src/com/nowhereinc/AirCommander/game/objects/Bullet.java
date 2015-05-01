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
	
	// first time switch
	private boolean firstTime;
	
	// off edge switch
	private boolean offEdge;
	
	// is bullet active
	private boolean isActive;
	
	public Bullet (World world) {
		init(world);
	}
	
	private void init(World world) {
		
		// set texture
		Bullet = Assets.instance.bullet.bullet;
		
		// create body def for bullet
		bodyDefBullet = new BodyDef();
		bodyDefBullet.position.set(0,0);
		bodyDefBullet.type = BodyDef.BodyType.DynamicBody;
		body = world.createBody(bodyDefBullet);
		
		// set user data
		body.setUserData(Bullet);
	
		CircleShape shape = new CircleShape();
	
		shape.setRadius(.10f);
		
		boxXSize = .20f;
		boxYSize = .20f;
	
		fixtureDefBullet = new FixtureDef();
		fixtureDefBullet.shape = shape;
		fixtureDefBullet.restitution = 1;
		body.createFixture(fixtureDefBullet).setUserData("bullet");
		
		firstTime = true;
		offEdge = false;
		
		// set to isActive to false
		isActive = false;
		
		shape.dispose();
	
	}
	
	public void initShot (World world, float deltaTime, Vector2 playerPos) {
		
		
	}
	
	public void update (World world, float deltaTime) {
		
		Vector2 vel = this.body.getLinearVelocity();
		Vector2 pos = this.body.getPosition();
		
		if (firstTime) {
		
		
		}

		
		if (!offEdge) {
					
			moveBullet(vel, pos);
					
		}
		
		if (pos.y < -4.5f) {
			
			offEdge = true;
			
		}
		
	}
	
	private void moveBullet (Vector2 vel, Vector2 pos) {
		
		float maxBulletVelocity = 0f;
		
		boolean moveSet = false;
		
		maxBulletVelocity = Constants.MAX_BULLET_VELOCITY;
		
		// if moving right and less then max velocity continue in that direction
		
		if (vel.x > 0 &&
			vel.x < maxBulletVelocity &&
			!moveSet) {
			
			// move right
			this.body.applyLinearImpulse(.80f, 0, pos.x, pos.y, true);
			
		}
	
		// if moving left and less then max velocity continue in that direction
	
		if (vel.x < 0 &&
			vel.x > - maxBulletVelocity &&
			!moveSet) {
		
			// move left
			this.body.applyLinearImpulse(-.80f, 0, pos.x, pos.y, true);
		
		}
		
		// if moving up continue in that direction
		
		if (vel.y > 0 &&
			vel.y < maxBulletVelocity &&
			!moveSet) {
			
			// move up
			this.body.applyLinearImpulse(0, .80f, pos.x, pos.y, true);
			
		}
	
		// if moving down continue in that direction
	
		if (vel.y < 0 &&
			vel.y > - maxBulletVelocity &&
			!moveSet) {
		
			// move down
			this.body.applyLinearImpulse(0, -.80f, pos.x, pos.y, true);
		
		}
		
	}
	
	public void setDeleteFlag() {
		
		// delete body
		
		body.setUserData("delete");
		
	}
	
	public void deleteBullet(World world) {
		
		if(body.getUserData() == "delete") {
		
			// delete body
		
			body.setActive(false);
			body.setUserData(null);
			body = null;
		
		}
		
	}
	
	public boolean returnOffEdge() {
		
		return offEdge;
		
	}
	
	public void render (SpriteBatch batch) {
		
		if (body.getUserData() != "delete" &&
			isActive) {
		
			Vector2 position = this.body.getPosition();
			float rotation = (MathUtils.radiansToDegrees * this.body.getAngle());
		
			Bullet = (TextureRegion)this.body.getUserData();
			batch.draw(Bullet, position.x, position.y, 0, 0, boxXSize, boxYSize, 1, 1, rotation);
		
		}
	
	}

}
