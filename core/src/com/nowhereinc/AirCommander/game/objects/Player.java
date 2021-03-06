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

public class Player {
	
	private static final String TAG = WorldRenderer.class.getName();
	
	private TextureRegion Player;
	
	private float leftXAxis;
	private float leftYAxis;
	private boolean aButton;
	private boolean bButton;
	private boolean xButton;
	private boolean yButton;

	private float tsXAxis;
	private float tsYAxis;
	
	// Body
	public Body body;
	
	// player body
	public BodyDef bodyDefPlayer;
	
	// player fixture
	public FixtureDef fixtureDefPlayer;
	
	// box size
	private float boxXSize;
	private float boxYSize;
	
	// edge booleans
	private boolean onRightEdge = false;
	private boolean onLeftEdge = false;
	private boolean onTopEdge = false;
	private boolean onBottomEdge = false;
	
	// spawn time counter
	private float spawnTime;
	
	// spawn time counter
	private float spawnFlashTime;
	
	// spawn hit boolean to determine which image is showing
	private boolean isSpawnHit;
	
	// spawn boolean
	private boolean isSpawning;

	
	public Player (World world, Vector2 cameraPosition, boolean spawn) {
		init(world, cameraPosition, spawn);
	}
	
	private void init(World world, Vector2 cameraPosition, boolean spawn) {
	
		// create body def for player
		bodyDefPlayer = new BodyDef();
		bodyDefPlayer.position.set(0f, cameraPosition.y - 7f);
		bodyDefPlayer.type = BodyDef.BodyType.DynamicBody;
		body = world.createBody(bodyDefPlayer);
		
		body.setUserData(Assets.instance.plane10.plane10);
		
		PolygonShape shape = new PolygonShape();
		
		boxXSize = Constants.PLAYERXSIZE;
		boxYSize = Constants.PLAYERYSIZE;
		
		shape.setAsBox(boxXSize, boxYSize);
		
		fixtureDefPlayer = new FixtureDef();
		fixtureDefPlayer.shape = shape;
		fixtureDefPlayer.restitution = 0;
		
		if (spawn) {
			
			isSpawning = true;
			spawnTime = 0f;
			spawnFlashTime = 0f;
			isSpawnHit = true;
			body.createFixture(fixtureDefPlayer).setUserData("spawn");
			body.setUserData(Assets.instance.plane10Hit.plane10Hit);
			
		}
		
		else {
			
			isSpawning = false;
			body.createFixture(fixtureDefPlayer).setUserData("player");
			body.setUserData(Assets.instance.plane10.plane10);
			
		}
		
		
		shape.dispose();
		
		onRightEdge = false;
		onLeftEdge = false;
		
	}
	
	public void inputController (float leftXAxisIn, float leftYAxisIn, boolean aButtonIn,
			                            boolean bButtonIn, boolean xButtonIn,
			                            boolean yButtonIn) {
		
		leftXAxis = leftXAxisIn;
		leftYAxis = leftYAxisIn;
		aButton = aButtonIn;
		bButton = bButtonIn;
		xButton = xButtonIn;
		yButton = yButtonIn;
	
	}
	
	public void inputTouchScreen (float tsXAxisIn, float tsYAxisIn) {
		
		tsXAxis = tsXAxisIn;
		tsYAxis = tsYAxisIn;
	
	}
	
	// return player center
	
	public Vector2 returnPlayerCenter() {
		
		return this.body.getPosition();
			
	}
	
	// return player x top and y center to fire bullet from
	
	public Vector2 returnPlayerPosition() {
		
		Vector2 playerTopMiddle;
		
		playerTopMiddle = new Vector2(0,0);
		
		playerTopMiddle.x = this.body.getPosition().x;
		playerTopMiddle.y = this.body.getPosition().y + boxYSize * 2;
		
		return playerTopMiddle;
			
	}
	
	public void updateAndroid (float deltaTime, Vector2 cameraPosition, boolean isScrolling) {
		
		Vector2 vel = this.body.getLinearVelocity();
		Vector2 pos = this.body.getPosition();
		
		if (body.getFixtureList().first().getUserData() == "delete") {
			
			setDeleteFlag();
		
		}
		
		if (isSpawning) {
			
			spawnTime += deltaTime;
			
			if (spawnTime > Constants.PLAYER_SPAWN_TIME) {
				
				isSpawning = false;
				body.getFixtureList().first().setUserData("player");
				body.setUserData(Assets.instance.plane10.plane10);
				
			}
			
			spawnFlashTime += deltaTime;
			
			if (spawnFlashTime > Constants.PLAYER_FLASH_TIME) {
				
				if (isSpawnHit) {
					
					isSpawnHit = false;
					body.setUserData(Assets.instance.plane10.plane10);
					
				}
				
				else {
					
					isSpawnHit = true;
					body.setUserData(Assets.instance.plane10Hit.plane10Hit);
					
				}
				
				spawnFlashTime = 0f;
				
			}
			
		}
		
		Vector2 playerCenter;
		
		playerCenter = new Vector2(0,0);
		
		playerCenter.x = this.body.getPosition().x + boxXSize;
		playerCenter.y = this.body.getPosition().y + boxYSize;
		
		float maxPlayerVelocity = 0f;
		 
		maxPlayerVelocity = Constants.MAX_PLAYER_VELOCITY_A;
		
		// stop if right edge is reached
		
		if (pos.x >= (Constants.GAMEBOARD_WIDTH / 2) - boxXSize &&
			!onRightEdge) {
			
			vel.x = 0;
			onRightEdge = true;
			
		}
		
		// stop if left edge is reached
		
		if (pos.x <= (- Constants.GAMEBOARD_WIDTH / 2) + boxXSize &&
			!onLeftEdge) {
			
			vel.x = 0;
			onLeftEdge = true;
			
		}
		
		// stop if bottom edge is reached
		
		if (pos.y <= - ((Constants.GAMEBOARD_HEIGHT / 2) - cameraPosition.y) + boxYSize &&
			!onBottomEdge) {
			
			vel.y = deltaTime * Constants.SCROLL_SPEED * Constants.OBJECT_SCROLL_ADJUSTMENT;
			onBottomEdge = true;
			
		}
		
		// stop if top edge is reached
		
		if (pos.y >= (cameraPosition.y + (Constants.GAMEBOARD_HEIGHT / 2)) - boxYSize &&
			!onTopEdge) {
			
			vel.y = deltaTime * Constants.SCROLL_SPEED * Constants.OBJECT_SCROLL_ADJUSTMENT;
			onTopEdge = true;
			
		}
		
		// move right
		
		if (tsXAxis > playerCenter.x &&
			tsXAxis != -99 &&
			vel.x < maxPlayerVelocity &&
			pos.x < (Constants.GAMEBOARD_WIDTH / 2) - (boxXSize * 2) &&
			!onRightEdge) {
				
				vel.x += Constants.PLAYER_VELOCITY_INC_A;
				onLeftEdge = false;
		
		}
		
		// move left
		
		if (tsXAxis < playerCenter.x &&
			tsXAxis != -99 &&
			vel.x > - maxPlayerVelocity &&
			pos.x > (- Constants.GAMEBOARD_WIDTH / 2) &&
			!onLeftEdge) {
			
			vel.x += -Constants.PLAYER_VELOCITY_INC_A;
			onRightEdge = false;
			
		}
		
		// move up
		
		if (tsYAxis > playerCenter.y + boxYSize * 2 &&
			tsYAxis != -99 &&
			vel.y < maxPlayerVelocity &&
			pos.y < (cameraPosition.y + (Constants.GAMEBOARD_HEIGHT / 2)) - (boxYSize * 2) &&
			!onTopEdge) {
			
				vel.y += Constants.PLAYER_VELOCITY_INC_A;
				onBottomEdge = false;
		}
		
		// move down
		
		if (tsYAxis < playerCenter.y - boxYSize * 2 &&
			tsYAxis != -99 &&
			vel.y > - maxPlayerVelocity &&
			pos.y > - ((Constants.GAMEBOARD_HEIGHT / 2) - cameraPosition.y) &&
			!onBottomEdge) {
			
				vel.y += -Constants.PLAYER_VELOCITY_INC_A;
				onTopEdge = false;
		}
		
		// no move so stop movement
		if (tsXAxis == -99 &&
			tsYAxis == -99) {
			
			vel.x = 0;
			
			if (isScrolling) {
			
				vel.y =  deltaTime * Constants.SCROLL_SPEED * Constants.OBJECT_SCROLL_ADJUSTMENT;
				
			}
			
			else {
				
				vel.y = 0f;
				
			}
		
		}
		
		this.body.setLinearVelocity(vel.x, vel.y);
		
	}
	
	public void updateDesktop (float deltaTime, Vector2 cameraPosition, boolean isScrolling) {
		
		Vector2 vel = this.body.getLinearVelocity();
		Vector2 pos = this.body.getPosition();
		
		if (body.getFixtureList().first().getUserData() == "delete") {
			
			setDeleteFlag();
			
		}
		
		if (isSpawning) {
			
			spawnTime += deltaTime;
			
			if (spawnTime > Constants.PLAYER_SPAWN_TIME) {
				
				isSpawning = false;
				body.getFixtureList().first().setUserData("player");
				body.setUserData(Assets.instance.plane10.plane10);
				
			}
			
			spawnFlashTime += deltaTime;
			
			if (spawnFlashTime > Constants.PLAYER_FLASH_TIME) {
				
				if (isSpawnHit) {
					
					isSpawnHit = false;
					body.setUserData(Assets.instance.plane10.plane10);
					
				}
				
				else {
					
					isSpawnHit = true;
					body.setUserData(Assets.instance.plane10Hit.plane10Hit);
					
				}
				
				spawnFlashTime = 0f;
				
			}
			
		}
		
		float maxPlayerVelocity = 0f;
	 
		maxPlayerVelocity = Constants.MAX_PLAYER_VELOCITY_D;
	
		// stop if right edge is reached
		
		if (pos.x >= (Constants.GAMEBOARD_WIDTH / 2) - boxXSize &&
			!onRightEdge) {
			
			vel.x = 0;
			onRightEdge = true;
			
		}
		
		// stop if left edge is reached
		
		if (pos.x <= (- Constants.GAMEBOARD_WIDTH / 2) + boxXSize &&
			!onLeftEdge) {
			
			vel.x = 0;
			onLeftEdge = true;
			
		}
		
		// stop if bottom edge is reached
		
		if (pos.y <= - ((Constants.GAMEBOARD_HEIGHT / 2) - cameraPosition.y) + (boxYSize * 2) &&
			!onBottomEdge) {
			
			vel.y = deltaTime *  Constants.SCROLL_SPEED * Constants.OBJECT_SCROLL_ADJUSTMENT;
			onBottomEdge = true;
			
		}
		
		else {
			
			onBottomEdge = false;
		}
		
		// stop if top edge is reached
		
		if (pos.y >= (cameraPosition.y + (Constants.GAMEBOARD_HEIGHT / 2)) - boxYSize &&
			!onTopEdge) {
			
			vel.y = deltaTime *  Constants.SCROLL_SPEED * Constants.OBJECT_SCROLL_ADJUSTMENT;
			onTopEdge = true;
			
		}
		
		else {
			
			onTopEdge = false;
			
		}

		// move right
		
		if (leftXAxis > Constants.LEFTJOYADJUSTMENT &&
			vel.x < maxPlayerVelocity &&
			pos.x < (Constants.GAMEBOARD_WIDTH / 2) - (boxXSize * 2) &&
			!onRightEdge) {
				
			vel.x += leftXAxis;
			onLeftEdge = false;
		
		}
		
		// move left
		
		if (leftXAxis < - Constants.LEFTJOYADJUSTMENT &&
			vel.x > - maxPlayerVelocity &&
			pos.x > (- Constants.GAMEBOARD_WIDTH / 2) &&
			!onLeftEdge) {
				
			vel.x += leftXAxis;
			onRightEdge = false;

		}
		
		// move up
		
		if (leftYAxis < - Constants.LEFTJOYADJUSTMENT &&
			vel.y < maxPlayerVelocity &&
			pos.y < (cameraPosition.y + (Constants.GAMEBOARD_HEIGHT / 2)) - (boxYSize * 2) &&
			!onTopEdge) {
			
			vel.y += -leftYAxis;
			onBottomEdge = false;
			
		}
		
		// move down
		
		if (leftYAxis > Constants.LEFTJOYADJUSTMENT &&
			vel.y > - maxPlayerVelocity &&
			pos.y > - ((Constants.GAMEBOARD_HEIGHT / 2) - cameraPosition.y) &&
			!onBottomEdge) {
			
			vel.y += -leftYAxis;
			onTopEdge = false;
			
		}
		
		// no move so stop movement, else apply movement
		
		if (leftXAxis > - Constants.LEFTJOYADJUSTMENT &&
			leftXAxis < Constants.LEFTJOYADJUSTMENT &&
			leftYAxis > - Constants.LEFTJOYADJUSTMENT &&
			leftYAxis < Constants.LEFTJOYADJUSTMENT) {
			
			if (isScrolling) {
				
				vel.y = deltaTime * Constants.SCROLL_SPEED * Constants.OBJECT_SCROLL_ADJUSTMENT;
				
			}
			
			else {
				
				vel.y = 0;
				
			}
			
			this.body.setLinearVelocity(0, vel.y);
		
		}
		
		else {
			
			this.body.setLinearVelocity(vel.x, vel.y);
			
		}
		
	}
	
	public void setDeleteFlag() {
		
		body.setUserData("delete");

	}
	
	public boolean deletePlayer(World world) {
		
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
	
	public boolean returnAButton() {
		
		return aButton;
		
	}
	
	public void render (SpriteBatch batch) {
			
		if (body.getUserData() != "delete") {
					
			Vector2 position = this.body.getPosition();
				
			position.x = position.x - boxXSize;
			position.y = position.y - boxYSize;

			float rotation = (MathUtils.radiansToDegrees * this.body.getAngle());
			
			Player = (TextureRegion)this.body.getUserData();
			batch.draw(Player, position.x, position.y, 0, 0, boxXSize * 2, boxYSize * 2, 1, 1, rotation);
				
		}
		
	}

}