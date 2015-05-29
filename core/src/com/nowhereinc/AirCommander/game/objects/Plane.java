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
	
	// plane type int
	private int planeType;
	
	// plane Origin
	private String savePlaneOrigin;
	
	// plane Hit points
	private int hitPoints;
	
	// tank flash time
	private float flashTime;
	
	public Plane (World world, Vector2 cameraPosition, String planeNumber, String planeOrigin) {
		
		savePlaneOrigin = planeOrigin;
		Vector2 pos = initOrigin(world, cameraPosition, planeOrigin);
		init(world, planeNumber, pos);
	}
	
	private Vector2 initOrigin(World world, Vector2 cameraPosition, String planeOrigin) {
		
		Vector2 position = new Vector2(0,0);
		
		if (planeOrigin.equals("NE")) {
			
			position.x = Constants.GAMEBOARD_WIDTH * .5f;
			position.y = cameraPosition.y + Constants.GAMEBOARD_HEIGHT * .5f;
			
		}
		
		if (planeOrigin.equals("NW")) {
			
			position.x = - (Constants.GAMEBOARD_WIDTH * .5f);
			position.y = cameraPosition.y + Constants.GAMEBOARD_HEIGHT * .5f;
			
		}
		
		if (planeOrigin.equals("N")) {
			
			position.x = 0f;
			position.y = (cameraPosition.y + Constants.GAMEBOARD_HEIGHT * .5f) - 2f;
			
		}
		
		if (planeOrigin.equals("NE2")) {
			
			position.x = Constants.GAMEBOARD_WIDTH * .25f;
			position.y = cameraPosition.y + Constants.GAMEBOARD_HEIGHT * .5f;
			
		}
		
		if (planeOrigin.equals("NW2")) {
			
			position.x = - (Constants.GAMEBOARD_WIDTH * .25f);
			position.y = cameraPosition.y + Constants.GAMEBOARD_HEIGHT * .5f;
			
		}
		
		return position;
		
	}
	
	private void init(World world, String planeNumber, Vector2 pos) {
		
		// create body def for player
		bodyDefPlane = new BodyDef();
		
		bodyDefPlane.angle = 180f;
		bodyDefPlane.position.set(pos.x, pos.y);
		bodyDefPlane.type = BodyDef.BodyType.DynamicBody;
		body = world.createBody(bodyDefPlane);
		
		switch (planeNumber) {
		
			case "plane1":
				body.setUserData(Assets.instance.plane1.plane1);
				planeType = 1;
				hitPoints = 1;
				break;
			
			case "plane2":
				body.setUserData(Assets.instance.plane2.plane2);
				planeType = 2;
				hitPoints = 1;
				break;
			
			case "plane3":
				body.setUserData(Assets.instance.plane3.plane3);
				planeType = 3;
				hitPoints = 1;
				break;
			
			case "plane4":
				body.setUserData(Assets.instance.plane4.plane4);
				planeType = 4;
				hitPoints = 10;
				break;
			
			case "plane5":
				body.setUserData(Assets.instance.plane5.plane5);
				planeType = 5;
				break;
			
			case "plane6":
				body.setUserData(Assets.instance.plane6.plane6);
				planeType = 6;
				break;
			
			case "plane7":
				body.setUserData(Assets.instance.plane7.plane7);
				planeType = 7;
				break;
			
			case "plane8":
				body.setUserData(Assets.instance.plane8.plane8);
				planeType = 8;
				break;
			
			case "plane9":
				body.setUserData(Assets.instance.plane9.plane9);
				planeType = 9;
				break;
			
			case "plane10":
				body.setUserData(Assets.instance.plane10.plane10);
				planeType = 10;
				break;
	
		}
		
		PolygonShape shape = new PolygonShape();
		
		if (planeType < 4) {
			
			boxXSize = Constants.PLANEXSIZE;
			boxYSize = Constants.PLANEYSIZE;
			
		}
			
		else {
			
			boxXSize = Constants.PLANEXSIZE * 2.5f;
			boxYSize = Constants.PLANEYSIZE * 2.5f;
			
		}
		
		shape.setAsBox(boxXSize, boxYSize);
		
		fixtureDefPlane = new FixtureDef();
		fixtureDefPlane.shape = shape;
		fixtureDefPlane.restitution = 0;
		body.createFixture(fixtureDefPlane).setUserData(planeNumber);
		
		// set to active
		body.setActive(true);
		
		shape.dispose();
		
	}
	

	
	public int returnPlaneType() {
		
		return planeType;
		
	}
	
	// return plane center
	
	public Vector2 returnPlaneCenter() {
		
		return this.body.getPosition();
		
	}
	
	// return computer x bottom and y center to fire bullet from
	
	public Vector2 returnPlanePosition() {
		
		Vector2 planePos;
		
		planePos = new Vector2(0,0);
		
		if (planeType < 4) {
		
			planePos.x = this.body.getPosition().x;
			planePos.y = this.body.getPosition().y - boxYSize * 2f;
			
		}
		
		else {
			
			planePos.x = this.body.getPosition().x;
			planePos.y = this.body.getPosition().y - boxYSize * 1.25f;
			
		}
		
		return planePos;
			
	}
	
	public void update(float deltaTime, Vector2 cameraPosition, Vector2 playerPosition) {
		
		Vector2 vel = this.body.getLinearVelocity();
		Vector2 pos = this.body.getPosition();
		
		if (body.getFixtureList().first().getUserData().equals("flash")) {
			
			flashTime += deltaTime;
			
			if (flashTime < Constants.HITFLASHTIME) {
			
				switch (planeType) {

					case 1:
						body.setUserData(Assets.instance.plane1Hit.plane1Hit);
						break;
				
					case 2:
						body.setUserData(Assets.instance.plane2Hit.plane2Hit);
						break;
				
					case 3:
						body.setUserData(Assets.instance.plane3Hit.plane3Hit);
						break;
				
					case 4:
						body.setUserData(Assets.instance.plane4Hit.plane4Hit);
						break;
				
					case 5:
						body.setUserData(Assets.instance.plane5Hit.plane5Hit);
						break;
				
					case 6:
						body.setUserData(Assets.instance.plane6Hit.plane6Hit);
						break;
				
					case 7:
						body.setUserData(Assets.instance.plane7Hit.plane7Hit);
						break;
				
					case 8:
						body.setUserData(Assets.instance.plane8Hit.plane8Hit);
						break;
				
					case 9:
						body.setUserData(Assets.instance.plane9Hit.plane9Hit);
						break;
				
					case 10:
						body.setUserData(Assets.instance.plane10Hit.plane10Hit);
						break;
					
				}
					
			}
			
			else {
				
				body.getFixtureList().first().setUserData("plane" + planeType);
				
				switch (planeType) {
				
					case 1:
						body.setUserData(Assets.instance.plane1.plane1);
						break;
			
					case 2:
						body.setUserData(Assets.instance.plane2.plane2);
						break;
			
					case 3:
						body.setUserData(Assets.instance.plane3.plane3);
						break;
			
					case 4:
						body.setUserData(Assets.instance.plane4.plane4);
						break;
				
					case 5:
						body.setUserData(Assets.instance.plane5.plane5);
						break;
			
					case 6:
						body.setUserData(Assets.instance.plane6.plane6);
						break;
			
					case 7:
						body.setUserData(Assets.instance.plane7.plane7);
						break;
			
					case 8:
						body.setUserData(Assets.instance.plane8.plane8);
						break;
			
					case 9:
						body.setUserData(Assets.instance.plane9.plane9);
						break;
			
					case 10:
						body.setUserData(Assets.instance.plane10.plane10);
						break;
				
				}
				
			}
	
		}
		
		if (body.getFixtureList().first().getUserData() == "hit") {
			
			hitPoints--;
			flashTime = 0f;
			body.getFixtureList().first().setUserData("flash");
			
			if (hitPoints <= 0) {
				
				body.getFixtureList().first().setUserData("delete");
			}
			
		}
		
		if (body.getFixtureList().first().getUserData() == "delete") {
			
			setDeleteFlag();
			
		}
		
		if (body.isActive()) {
		
			switch (planeType) {
		
				case 1:
					movePlane1(cameraPosition, vel, pos);
					break;
			
				case 2:
					movePlane2(cameraPosition, vel, pos);
					break;
			
				case 3:
					movePlane3(cameraPosition, vel, pos);
					break;
			
				case 4:
					movePlane4(deltaTime, vel, pos);
					break;
			
				case 5:
					movePlane5();
					break;
			
				case 6:
					movePlane6();
					break;
			
				case 7:
					movePlane7();
					break;
			
				case 8:
					movePlane8();
					break;
			
				case 9:
					movePlane9();
					break;
			
				case 10:
					movePlane10();
					break;
	
			}
			
		}
	
	}
	
	private void movePlane1(Vector2 cameraPosition, Vector2 vel, Vector2 pos) {
		
		if (savePlaneOrigin.equals("NE")) {
			
			movePlane1NE(cameraPosition, vel, pos);
			
		}
		
		if (savePlaneOrigin.equals("NW")) {
			
			movePlane1NW(cameraPosition, vel, pos);
			
		}
		
	}
	
	private void movePlane1NE(Vector2 cameraPosition, Vector2 vel, Vector2 pos) {
		
		// if plane below bottom of screen set for deletion
		
		if (pos.y < cameraPosition.y + (-Constants.GAMEBOARD_HEIGHT * .5f)) {
			
			setRemoveFlag();
			vel.y = 0;
			
		}
		
		else {
			
			if (vel.y > - Constants.MAX_COMPUTER_VELOCITY) {
				
				// move plane down
			
				vel.y -= Constants.COMPUTER_VELOCITY_INC;
		
			}
		
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
	
	private void movePlane1NW(Vector2 cameraPosition, Vector2 vel, Vector2 pos) {
		
		// if plane below bottom of screen set for deletion
		
		if (pos.y < cameraPosition.y + (-Constants.GAMEBOARD_HEIGHT * .5f)) {
			
			setRemoveFlag();
			vel.y = 0;
			
		}
		
		else {
		
			if (vel.y > - Constants.MAX_COMPUTER_VELOCITY) {
		
				// move plane down
			
				vel.y -= Constants.COMPUTER_VELOCITY_INC;
		
			}
			
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
	
	private void movePlane2(Vector2 cameraPosition, Vector2 vel, Vector2 pos) {
		
		if (savePlaneOrigin.equals("NE")) {
			
			movePlane2NE(cameraPosition, vel, pos);
			
		}
		
		if (savePlaneOrigin.equals("NW")) {
			
			movePlane2NW(cameraPosition, vel, pos);
			
		}
		
	}
	
	private void movePlane2NE(Vector2 cameraPosition, Vector2 vel, Vector2 pos) {
		
		// if plane below bottom of screen set for deletion
		
		if (pos.y < cameraPosition.y + (-Constants.GAMEBOARD_HEIGHT * .5f)) {
			
			setRemoveFlag();
			vel.y = 0;
			
		}
		
		else {
			
			if (vel.y > - Constants.MAX_COMPUTER_VELOCITY) {
				
				// move plane down
			
				vel.y -= Constants.COMPUTER_VELOCITY_INC;
		
			}
			
			vel.x -= Constants.COMPUTER_SIDE_VELOCITY_INC * .5f;
			
		}
		
		// apply movement
		
		this.body.setLinearVelocity(vel.x, vel.y);
		
	}
	
	private void movePlane2NW(Vector2 cameraPosition, Vector2 vel, Vector2 pos) {
		
		// if plane below bottom of screen set for deletion
		
		if (pos.y < cameraPosition.y + (-Constants.GAMEBOARD_HEIGHT * .5f)) {
			
			setRemoveFlag();
			vel.y = 0;
			
		}
		
		else {
			
			if (vel.y > - Constants.MAX_COMPUTER_VELOCITY) {
				
				// move plane down
			
				vel.y -= Constants.COMPUTER_VELOCITY_INC;
		
			}
			
			vel.x += Constants.COMPUTER_SIDE_VELOCITY_INC * .5f;
			
		}
		
		// apply movement
		
		this.body.setLinearVelocity(vel.x, vel.y);
		
	}

	private void movePlane3(Vector2 cameraPosition, Vector2 vel, Vector2 pos) {
		
		if (savePlaneOrigin.equals("NE2")) {
			
			movePlane3NE2(cameraPosition, vel, pos);
			
		}
		
		if (savePlaneOrigin.equals("NW2")) {
			
			movePlane3NW2(cameraPosition, vel, pos);
			
		}
		
	}
	
	private void movePlane3NE2(Vector2 cameraPosition, Vector2 vel, Vector2 pos) {
		
		// if plane below bottom of screen set for deletion
		
		if (pos.y < cameraPosition.y + (-Constants.GAMEBOARD_HEIGHT * .5f)) {
			
			setRemoveFlag();
			vel.y = 0;
			
		}
		
		else {
			
			if (vel.y > - Constants.MAX_COMPUTER_VELOCITY) {
				
				// move plane down
			
				vel.y -= Constants.COMPUTER_VELOCITY_INC;
		
			}
		
			if (pos.y < cameraPosition.y) {
		
				
				vel.x += Constants.COMPUTER_SIDE_VELOCITY_INC * .33f;
						
			}
			
			if (pos.y > cameraPosition.y) {
				
				vel.x -= Constants.COMPUTER_SIDE_VELOCITY_INC * .33f;
				
			}
			
		}
		
		
		// apply movement
		
		this.body.setLinearVelocity(vel.x, vel.y);
		
	}
	
	private void movePlane3NW2(Vector2 cameraPosition, Vector2 vel, Vector2 pos) {
		
		// if plane below bottom of screen set for deletion
		
		if (pos.y < cameraPosition.y + (-Constants.GAMEBOARD_HEIGHT * .5f)) {
			
			setRemoveFlag();
			vel.y = 0;
			
		}
		
		else {
		
			if (vel.y > - Constants.MAX_COMPUTER_VELOCITY) {
		
				// move plane down
			
				vel.y -= Constants.COMPUTER_VELOCITY_INC;
		
			}
			
			if (pos.y < cameraPosition.y) {
		
				
				vel.x -= Constants.COMPUTER_SIDE_VELOCITY_INC * .33f;
						
			}
			
			if (pos.y > cameraPosition.y) {
				
				vel.x += Constants.COMPUTER_SIDE_VELOCITY_INC * .33f;
				
			}
			
		}

		
		// apply movement
		
		this.body.setLinearVelocity(vel.x, vel.y);
		
	}
	
	private void movePlane4(float deltaTime, Vector2 vel, Vector2 pos) {
		
		// init vel y to 0
		vel.y = 0;
		
		// move right if velocity is zero
		
		if (vel.x == 0) {
			
			vel.x = Constants.COMPUTER_SIDE_VELOCITY_INC;
			
		}
		
		// continue to move right if not to far over and less then max velocity
		
		if (vel.x > 0 &&
			pos.x < Constants.GAMEBOARD_WIDTH * .25f &&
			vel.x < Constants.MAX_COMPUTER_SIDE_VELOCITY) {
			
			vel.x += Constants.COMPUTER_SIDE_VELOCITY_INC;
			
		}
		
		// move left if moving right and past the turn around point
		
		if (vel.x > 0 &&
			pos.x > Constants.GAMEBOARD_WIDTH * .25f) {
			
			vel.x = - Constants.COMPUTER_SIDE_VELOCITY_INC;
			
		}
		
		// continue to move left if not to far over and less then max velocity
		
		if (vel.x < 0 &&
			pos.x > - Constants.GAMEBOARD_WIDTH * .25f &&
			vel.x > - Constants.MAX_COMPUTER_SIDE_VELOCITY) {
			
			vel.x -= Constants.COMPUTER_SIDE_VELOCITY_INC;
			
		}
		
		// move right if moving left and past the turn around point
		
		if (vel.x < 0 &&
			pos.x < - Constants.GAMEBOARD_WIDTH * .25f) {
			
			vel.x = Constants.COMPUTER_SIDE_VELOCITY_INC;
			
		} 

		this.body.setLinearVelocity(vel.x, vel.y);
		
	}
	
	private void movePlane5() {
		
		
	}
	
	private void movePlane6() {
		
		
	}
	
	private void movePlane7() {
		
		
	}
	
	private void movePlane8() {
		
		
	}
	
	private void movePlane9() {
		
		
	}
	
	private void movePlane10() {
		
		
	}
	
	public void setDeleteFlag() {
		
		body.setUserData("delete");

	}
	
	public void setRemoveFlag() {
		
		body.setUserData("remove");
		
	}
	
	public int returnPlaneScore() {
		
		if (body.getUserData().equals("delete")) {
		
			switch (planeType) {
		
				case 1:
					return 10;
			
				case 2:
					return 10;
			
				case 3:
					return 10;
			
				case 4:
					return 100;
			
				case 5:
					return 10;
			
				case 6:
					return 10;

				case 7:
					return 10;
			
				case 8:
					return 10;
				
				case 9:
					return 10;
			
				case 10:
					return 10;
			
				default:
					return 0;
					
			}
	
		}
		
		return 0;
		
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
	
	public boolean removePlane(World world) {
		
		if(body.getUserData() == "remove") {
		
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
			body.isActive() != false &&
			body.getUserData() != "remove") {
					
			Vector2 position = this.body.getPosition();
			
			float rotation = this.body.getAngle();
			
			position.x = position.x + boxXSize;
			position.y = position.y + boxYSize;

			Plane = (TextureRegion)this.body.getUserData();
			batch.draw(Plane, position.x, position.y, 0, 0, boxXSize * 2, boxYSize * 2, 1, 1, rotation);
				
		}		
		
	}
	
}
