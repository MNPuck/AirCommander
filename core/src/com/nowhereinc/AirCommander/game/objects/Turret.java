package com.nowhereinc.AirCommander.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.nowhereinc.AirCommander.game.Assets;
import com.nowhereinc.AirCommander.game.WorldRenderer;
import com.nowhereinc.AirCommander.util.Constants;

public class Turret {
	
	private static final String TAG = WorldRenderer.class.getName();
	
	private TextureRegion Turret;
	
	// Body
	public Body body;
	
	// turrets body
	public BodyDef bodyDefTurret;
	
	// turret fixture
	public FixtureDef fixtureDefTurret;
	
	// box size
	private float boxXSize;
	private float boxYSize;
	
	// turret type int
	private int turretType;
	
	// turret Origin
	private String saveTurretOrigin;
	
	// turret Value, which is counter from level that matches tank to turret
	private int turretValue;

	
	public Turret (World world, Vector2 cameraPosition, String turretNumber, String turretOrigin, int turretCounter) {
		
		Vector2 pos = initOrigin(world, cameraPosition, turretOrigin);
		init(world, turretNumber, pos, turretCounter);
	}
	
	private Vector2 initOrigin(World world, Vector2 cameraPosition, String turretOrigin) {
		
		Vector2 position = new Vector2(0,0);
		
		if (turretOrigin.equals("NE")) {
			
			position.x = Constants.GAMEBOARD_WIDTH * .5f;
			position.y = cameraPosition.y + Constants.GAMEBOARD_HEIGHT * .5f;
			
		}
		
		if (turretOrigin.equals("NW")) {
			
			position.x = - (Constants.GAMEBOARD_WIDTH * .5f);
			position.y = cameraPosition.y + Constants.GAMEBOARD_HEIGHT * .5f;
			
		}
		
		if (turretOrigin.equals("N")) {
			
			position.x = 0f;
			position.y = (cameraPosition.y + Constants.GAMEBOARD_HEIGHT * .5f) - 2f;
			
		}
		
		if (turretOrigin.equals("NE2")) {
			
			position.x = Constants.GAMEBOARD_WIDTH * .25f;
			position.y = cameraPosition.y + Constants.GAMEBOARD_HEIGHT * .5f;
			
		}
		
		if (turretOrigin.equals("NW2")) {
			
			position.x = - (Constants.GAMEBOARD_WIDTH * .25f);
			position.y = cameraPosition.y + Constants.GAMEBOARD_HEIGHT * .5f;
			
		}
		
		return position;
		
	}
	
	private void init(World world, String turretNumber, Vector2 pos, int turretCounter) {
		
		// create body def for player
		bodyDefTurret = new BodyDef();
		
		bodyDefTurret.angle = 180f;
		bodyDefTurret.position.set(pos.x, pos.y);
		bodyDefTurret.type = BodyDef.BodyType.DynamicBody;
		body = world.createBody(bodyDefTurret);
		
		switch (turretNumber) {
		
			case "turret1":
				body.setUserData(Assets.instance.turret1.turret1);
				turretType = 1;
				break;
			
			case "turret2":
				body.setUserData(Assets.instance.turret2.turret2);
				turretType = 2;
				break;
	
		}
		
		PolygonShape shape = new PolygonShape();
			
		boxXSize = Constants.TURRETXSIZE;
		boxYSize = Constants.TURRETYSIZE;
		
		shape.setAsBox(boxXSize, boxYSize);
		
		fixtureDefTurret = new FixtureDef();
		fixtureDefTurret.shape = shape;
		fixtureDefTurret.restitution = 0;
		body.createFixture(fixtureDefTurret).setUserData(turretNumber);
		
		// set to active
		body.setActive(true);
		
		shape.dispose();
		
		turretValue = turretCounter;
		
	}
	
	public int returnTurretType() {
		
		return turretType;
		
	}
	
	public int returnTurretValue() {
		
		return turretValue;
		
	}
	
	// return computer x bottom and y center to fire bullet from
	
	public Vector2 returnTurretPosition() {
		
		Vector2 turretPos;
		
		turretPos = new Vector2(0,0);
		
		turretPos.x = this.body.getPosition().x;
		turretPos.y = this.body.getPosition().y - boxYSize * 1.25f;
			
		return turretPos;
			
	}
	
	public void update(float deltaTime, Vector2 tankVelocity) {
		
		Vector2 vel = this.body.getLinearVelocity();
		Vector2 pos = this.body.getPosition();
		
		if (body.isActive()) {
		
			moveTurret(vel, pos, tankVelocity);
			
		}
	
	}
	
	private void moveTurret(Vector2 vel, Vector2 pos, Vector2 tankVelocity) {
		
		vel = tankVelocity;
		
		this.body.setLinearVelocity(vel.x, vel.y);
		
	}
	
	public void flashTurret(World world) {
		
		switch (turretType) {
		
			case 1:
				body.setUserData(Assets.instance.turret1Hit.turret1Hit);
				break;

			case 2:
				body.setUserData(Assets.instance.turret2Hit.turret2Hit);
				break;
		
		}
		
	}
	
	public void normalTurret(World world) {
		
		switch (turretType) {
		
			case 1:
				body.setUserData(Assets.instance.turret1.turret1);
				break;

			case 2:
				body.setUserData(Assets.instance.turret2.turret2);
				break;
		
		}
		
	}
	
	public void deleteTurret(World world) {
	
		// delete body
		
		body.setActive(false);
		body.setUserData(null);
		body = null;
		
	}

	public void render (SpriteBatch batch) {
			
		if  (body.isActive() != false) {
					
			Vector2 position = this.body.getPosition();
			
			float rotation = this.body.getAngle();
			
			position.x = position.x + boxXSize;
			position.y = position.y + boxYSize;

			Turret = (TextureRegion)this.body.getUserData();
			batch.draw(Turret, position.x, position.y, 0, 0, boxXSize * 2, boxYSize * 2, 1, 1, rotation);
				
		}		
		
	}
	
}