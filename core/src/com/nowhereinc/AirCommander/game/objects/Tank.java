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

public class Tank {
	
	private static final String TAG = WorldRenderer.class.getName();
	
	private TextureRegion Tank;
	
	// Body
	public Body body;
	
	// tanks body
	public BodyDef bodyDefTank;
	
	// tank fixture
	public FixtureDef fixtureDefTank;
	
	// box size
	private float boxXSize;
	private float boxYSize;
	
	// tank type int
	private int tankType;
	
	// tank Origin
	private String saveTankOrigin;
	
	// tank Hitpoints
	private int hitPoints;
	
	// tank Value, which is counter from level that matches tank to turret
	private int tankValue;
	
	
	public Tank (World world, Vector2 cameraPosition, String tankNumber, String tankOrigin, int tankCounter) {
		
		saveTankOrigin = tankOrigin;
		Vector2 pos = initOrigin(world, cameraPosition, tankOrigin);
		init(world, tankNumber, pos, tankCounter);
	}
	
	private Vector2 initOrigin(World world, Vector2 cameraPosition, String tankOrigin) {
		
		Vector2 position = new Vector2(0,0);
		
		if (tankOrigin.equals("NE")) {
			
			position.x = Constants.GAMEBOARD_WIDTH * .5f;
			position.y = cameraPosition.y + Constants.GAMEBOARD_HEIGHT * .5f;
			
		}
		
		if (tankOrigin.equals("NW")) {
			
			position.x = - (Constants.GAMEBOARD_WIDTH * .5f);
			position.y = cameraPosition.y + Constants.GAMEBOARD_HEIGHT * .5f;
			
		}
		
		if (tankOrigin.equals("N")) {
			
			position.x = 0f;
			position.y = (cameraPosition.y + Constants.GAMEBOARD_HEIGHT * .5f) - 2f;
			
		}
		
		if (tankOrigin.equals("NE2")) {
			
			position.x = Constants.GAMEBOARD_WIDTH * .25f;
			position.y = cameraPosition.y + Constants.GAMEBOARD_HEIGHT * .5f;
			
		}
		
		if (tankOrigin.equals("NW2")) {
			
			position.x = - (Constants.GAMEBOARD_WIDTH * .25f);
			position.y = cameraPosition.y + Constants.GAMEBOARD_HEIGHT * .5f;
			
		}
		
		return position;
		
	}
	
	private void init(World world, String tankNumber, Vector2 pos, int tankCounter) {
		
		// create body def for player
		bodyDefTank = new BodyDef();
		
		bodyDefTank.angle = 180f;
		bodyDefTank.position.set(pos.x, pos.y);
		bodyDefTank.type = BodyDef.BodyType.DynamicBody;
		body = world.createBody(bodyDefTank);
		
		switch (tankNumber) {
		
			case "tank1":
				body.setUserData(Assets.instance.tank1.tank1);
				tankType = 1;
				hitPoints = 3;
				break;
			
			case "tank2":
				body.setUserData(Assets.instance.tank2.tank2);
				tankType = 2;
				hitPoints = 3;
				break;
	
		}
		
		PolygonShape shape = new PolygonShape();
			
		boxXSize = Constants.TANKXSIZE;
		boxYSize = Constants.TANKYSIZE;
		
		shape.setAsBox(boxXSize, boxYSize);
		
		fixtureDefTank = new FixtureDef();
		fixtureDefTank.shape = shape;
		fixtureDefTank.restitution = 0;
		body.createFixture(fixtureDefTank).setUserData(tankNumber);
		
		// set to active
		body.setActive(true);
		
		shape.dispose();
		
		tankValue = tankCounter;
		
	}
	

	
	public int returnTankType() {
		
		return tankType;
		
	}
	
	// return computer x bottom and y center to fire bullet from
	
	public Vector2 returnTankPosition() {
		
		Vector2 tankPos;
		
		tankPos = new Vector2(0,0);
		
		tankPos.x = this.body.getPosition().x;
		tankPos.y = this.body.getPosition().y - boxYSize * 1.25f;
			
		return tankPos;
			
	}
	
	public int returnTankValue() {
		
		return tankValue;
		
	}
	
	public void update(float deltaTime, Vector2 cameraPosition, Vector2 playerPosition) {
		
		Vector2 vel = this.body.getLinearVelocity();
		Vector2 pos = this.body.getPosition();
		
		if (body.getFixtureList().first().getUserData() == "hit") {
			
			hitPoints--;
			body.getFixtureList().first().setUserData("tank" + tankType);
			
			if (hitPoints <= 0) {
				
				body.getFixtureList().first().setUserData("delete");
			}
			
		}
		
		if (body.getFixtureList().first().getUserData() == "delete") {
			
			setDeleteFlag();
			
		}
		
		if (body.isActive()) {
		
			switch (tankType) {
		
				case 1:
					moveTank1(cameraPosition, vel, pos);
					break;
			
				case 2:
					moveTank2(cameraPosition, vel, pos);
					break;
	
			}
			
		}
	
	}
	
	private void moveTank1(Vector2 cameraPosition, Vector2 vel, Vector2 pos) {
		
		if (saveTankOrigin.equals("NE")) {
			
			moveTank1NE(cameraPosition, vel, pos);
			
		}
		
		if (saveTankOrigin.equals("NW")) {
			
			moveTank1NW(cameraPosition, vel, pos);
			
		}
		
	}
	
	private void moveTank1NE(Vector2 cameraPosition, Vector2 vel, Vector2 pos) {
		
		// if tank below bottom of screen set for deletion
		
		if (pos.y < cameraPosition.y + (-Constants.GAMEBOARD_HEIGHT * .5f)) {
			
			setDeleteFlag();
			vel.y = 0;
			
		}
		
		else {
		
			if (pos.y < cameraPosition.y) {
		
				
				vel.x += Constants.COMPUTER_SIDE_VELOCITY_INC;
						
			}
			
			if (pos.y > cameraPosition.y) {
				
				vel.x -= Constants.COMPUTER_SIDE_VELOCITY_INC;
				
			}
			
		}
		
		
		// apply movement
		
		this.body.setLinearVelocity(vel.x, vel.y);
		
	}
	
	private void moveTank1NW(Vector2 cameraPosition, Vector2 vel, Vector2 pos) {
		
		// if tank below bottom of screen set for deletion
		
		if (pos.y < cameraPosition.y + (-Constants.GAMEBOARD_HEIGHT * .5f)) {
			
			setDeleteFlag();
			vel.y = 0;
			
		}
		
		else {
			
			if (pos.y < cameraPosition.y) {
		
				
				vel.x -= Constants.COMPUTER_SIDE_VELOCITY_INC;
						
			}
			
			if (pos.y > cameraPosition.y) {
				
				vel.x += Constants.COMPUTER_SIDE_VELOCITY_INC;
				
			}
			
		}

		
		// apply movement
		
		this.body.setLinearVelocity(vel.x, vel.y);
		
	}
	
	private void moveTank2(Vector2 cameraPosition, Vector2 vel, Vector2 pos) {
		
		if (saveTankOrigin.equals("NE")) {
			
			moveTank2NE(cameraPosition, vel, pos);
			
		}
		
		if (saveTankOrigin.equals("NW")) {
			
			moveTank2NW(cameraPosition, vel, pos);
			
		}
		
	}
	
	private void moveTank2NE(Vector2 cameraPosition, Vector2 vel, Vector2 pos) {
		
		// if tank below bottom of screen set for deletion
		
		if (pos.y < cameraPosition.y + (-Constants.GAMEBOARD_HEIGHT * .5f)) {
			
			setDeleteFlag();
			vel.y = 0;
			
		}
		
		else {
			
			vel.x -= Constants.COMPUTER_SIDE_VELOCITY_INC * .5f;
			
		}
		
		// apply movement
		
		this.body.setLinearVelocity(vel.x, vel.y);
		
	}
	
	private void moveTank2NW(Vector2 cameraPosition, Vector2 vel, Vector2 pos) {
		
		// if tank below bottom of screen set for deletion
		
		if (pos.y < cameraPosition.y + (-Constants.GAMEBOARD_HEIGHT * .5f)) {
			
			setDeleteFlag();
			vel.y = 0;
			
		}
		
		else {
	
			
			vel.x += Constants.COMPUTER_SIDE_VELOCITY_INC * .5f;
			
		}
		
		// apply movement
		
		this.body.setLinearVelocity(vel.x, vel.y);
		
	}
	
	public void setDeleteFlag() {
		
		body.setUserData("delete");

	}
	
	public int returnTankScore() {
		
		if (body.getUserData().equals("delete")) {
		
			switch (tankType) {
		
				case 1:
					return 30;
			
				case 2:
					return 30;
			
				default:
					return 0;
					
			}
	
		}
		
		return 0;
		
	}
	
	public boolean deleteTank(World world) {
		
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
			
			float rotation = this.body.getAngle();
			
			position.x = position.x + boxXSize;
			position.y = position.y + boxYSize;

			Tank = (TextureRegion)this.body.getUserData();
			batch.draw(Tank, position.x, position.y, 0, 0, boxXSize * 2, boxYSize * 2, 1, 1, rotation);
				
		}		
		
	}
	
}
