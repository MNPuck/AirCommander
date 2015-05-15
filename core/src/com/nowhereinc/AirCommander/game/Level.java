package com.nowhereinc.AirCommander.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.nowhereinc.AirCommander.game.objects.Bullet;
import com.nowhereinc.AirCommander.game.objects.Plane;
import com.nowhereinc.AirCommander.game.objects.Player;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.nowhereinc.AirCommander.util.Constants;
import com.nowhereinc.AirCommander.game.Assets;

public class Level {

	public static final String TAG = Level.class.getName();
	
	// World
	public World world;
	
	// bullet single
	public Bullet bullet;
	
	// bullets array
	public Array<Bullet> bullets;
	
	// Player
	public Player player;
	
	// plane single
	public Plane plane;
	
	// plane array
	public Array<Plane> planes;
	
	// level objects
	public LevelBuilder levels;
	
	// variable to store level number
	public int levelNumber;
	
	// variable to store score
	public int score;
	
	// variable to store lives
	public int lives;
	
	// variable to see if this is new game
	private boolean newGame; 
	
	// player position
	private Vector2 playerPosition;
	
	// plane position
	private Vector2 planePosition;
	
	// game over boolean
	public boolean isGameOver;
	
	// time since last bullet
	private float timeSinceLastBulletPlayer;
	
	// time since last bullet
	private float timeSinceLastBulletComputer;
	
	
	public Level () {

		init();
	}
		
	private void init() {
		
		// level number
		levelNumber = 0;
		
		// world
		world = new World(new Vector2(0f, 0f), true);
		
		// set world to contact listener
		world.setContactListener(new AirCommanderContactListener());
		
		// player
		player = new Player(world);
		
		// planes
		planes = new Array<Plane>();
		
		// bullets
		bullets = new Array<Bullet>();
		
		// set to new game
		newGame = true;
		
		// set score to 0
		score = 0;
		
		// set live to constant
		lives = Constants.LIVES_START;
	
		// init player pos 
		playerPosition = new Vector2(0,0);
		
		// init plane pos 
		planePosition = new Vector2(0,0);
		
		// level game over init
		isGameOver = false;
		
		// init time since last bullet to 0
		timeSinceLastBulletPlayer = 0;
		
		// init time since last bullet to 0
		timeSinceLastBulletComputer = 0;
		
		newLevel();
		
	}
	
	public void update (float deltaTime, Vector2 cameraPosition) {
		
		// step world
		
		world.step(deltaTime, 8, 3);
		
		// add planes that are on screen
		
		addPlanes(cameraPosition);
		
		
		// player update
		
		switch (Gdx.app.getType()) {
		
			case Desktop: 
				player.updateDesktop(deltaTime, cameraPosition);
				break;
			
			case Android:
				 player.updateAndroid(deltaTime, cameraPosition);
				 break;
			
			default:
				 break;
	
		}	
		
		// get player position
			
		playerPosition = player.returnPlayerPosition();
		
		// planes update
		
		for (Plane plane : planes) {
			
			plane.update(deltaTime, cameraPosition, playerPosition);
			
		}
		
		// bullets update
		
		for (Bullet bullet : bullets) {
			
			bullet.update(world, deltaTime, cameraPosition);
			
		}
		
		// player bullets spawn
		
		// if time since last bullet > constant, spawn a bullet
		
		timeSinceLastBulletPlayer += deltaTime;
		
		if (timeSinceLastBulletPlayer > Constants.BULLET_SPAWN_TIME_PLAYER) {
			
			switch (Gdx.app.getType()) {
			
				case Desktop: 
				
					if (player.returnAButton()) {
					
						bullet = null;
						bullet = new Bullet(world, playerPosition, Constants.MAX_PLAYER_BULLET_VELOCITY, 1, Constants.N);
						bullets.add((Bullet)bullet);
					
						timeSinceLastBulletPlayer = 0;
					
					}
				
					break;
				
				case Android:
				
					bullet = null;
					bullet = new Bullet(world, playerPosition, Constants.MAX_PLAYER_BULLET_VELOCITY, 1, Constants.N);
					bullets.add((Bullet)bullet);
				
					timeSinceLastBulletPlayer = 0;
				
					break;
				
				default:
					break;
		
			}
			
		}
		
		// computer bullets spawn
		
		timeSinceLastBulletComputer += deltaTime;
		
		if (timeSinceLastBulletComputer > Constants.BULLET_SPAWN_TIME_COMPUTER) {
			
			// loop thru active planes and get their coordinates and init shot
			
			for (Plane plane : planes) {
				
				if (plane.body.isActive() &&
					plane.bodyDefPlane.angle == 180f) {
				
					planePosition = plane.returnPlanePosition();
				
					bullet = null;
					bullet = new Bullet(world, planePosition, Constants.MAX_COMPUTER_BULLET_VELOCITY, 2, Constants.S);
					bullets.add((Bullet)bullet);
				
				}
				
				if (plane.body.isActive() &&
						plane.bodyDefPlane.angle == 0f) {
					
						planePosition = plane.returnPlanePosition();
					
						bullet = null;
						bullet = new Bullet(world, planePosition, Constants.MAX_COMPUTER_BULLET_VELOCITY, 2, Constants.N);
						bullets.add((Bullet)bullet);
					
				}
							
			}
			
			timeSinceLastBulletComputer = 0;
			
		}
		
	}	
	
	private void addPlanes(Vector2 cameraPosition) {
		
		// loop thru object layer and look for planes that are on screen
		
		for (MapObject obj : Assets.instance.mapObjectLayer.getObjects()) {
			
			if (obj instanceof RectangleMapObject) {
			
				Rectangle rect = ((RectangleMapObject) obj).getRectangle();
				
				Vector2 conPos = new Vector2(rect.x, rect.y);
				
				conPos = ConvertPosition(conPos);
				
				if (conPos.y < cameraPosition.y + (Constants.GAMEBOARD_HEIGHT * .5f)) {
			
					if ("plane1".equals(obj.getName()) ) {
			
						addPlane(cameraPosition, "plane1", obj.getProperties().get("type", String.class));
						obj.setName("done");
					}
				
					if ("plane2".equals(obj.getName()) ) {
					
						addPlane(cameraPosition, "plane2", obj.getProperties().get("type", String.class));
						obj.setName("done");
					}
				
					if ("plane3".equals(obj.getName()) ) {
					
						addPlane(cameraPosition, "plane3", obj.getProperties().get("type", String.class));
						obj.setName("done");
					}
				
					if ("plane4".equals(obj.getName()) ) {
					
						addPlane(cameraPosition, "plane4", obj.getProperties().get("type", String.class));
						obj.setName("done");
					}
				
					if ("plane5".equals(obj.getName()) ) {
					
						addPlane(cameraPosition, "plane5", obj.getProperties().get("type", String.class));
						obj.setName("done");
					}
				
					if ("plane6".equals(obj.getName()) ) {
					
						addPlane(cameraPosition, "plane6", obj.getProperties().get("type", String.class));
						obj.setName("done");
					}
				
					if ("plane7".equals(obj.getName()) ) {
					
						addPlane(cameraPosition, "plane7", obj.getProperties().get("type", String.class));
						obj.setName("done");
					}
				
					if ("plane8".equals(obj.getProperties().get("name", String.class)) ) {
					
						addPlane(cameraPosition, "plane8", obj.getProperties().get("type", String.class));
						obj.setName("done");
					}
				
					if ("plane9".equals(obj.getProperties().get("name", String.class)) ) {
					
						addPlane(cameraPosition, "plane9", obj.getProperties().get("type", String.class));
						obj.setName("done");
					}
				
					if ("plane10".equals(obj.getProperties().get("name", String.class)) ) {
					
						addPlane(cameraPosition, "plane10", obj.getProperties().get("type", String.class));
						obj.setName("done");
					}
				
				}
			
			}
				
		}

	}
	
	private Vector2 ConvertPosition(Vector2 pos) {
		
		Vector2 tilePos = new Vector2(0,0);
		
		tilePos.x = pos.x / Constants.TILE_SIZE;
		tilePos.y = pos.y / Constants.TILE_SIZE;
	
		float adjustedGameBoardWidth = Constants.GAMEBOARD_WIDTH / 2;
		tilePos.x -= adjustedGameBoardWidth;
		
		float adjustedGameBoardHeight = Constants.GAMEBOARD_HEIGHT / 2;
		tilePos.y -= adjustedGameBoardHeight;
		
		return tilePos;
		
	}
	
	private void addPlane(Vector2 cameraPosition, String planeNumber, String planeOrigin) {
		
		plane = null;
		plane = new Plane(world, cameraPosition, planeNumber, planeOrigin);
		planes.add((Plane)plane);
		
	}
	
	public void deleteFlaggedItems() {
		
		for (Bullet bullet : bullets) {
			
			if (bullet.deleteBullet(world)) {
				
				bullets.removeValue(bullet, true);
				
			}
			
		}
		
		player.deletePlayer(world);
		
		for (Plane plane : planes) {
			
			if (plane.deletePlane(world)) {

				planes.removeValue(plane, true);
				
			}
			
		}
		
	}

	private void newLevel() {
		
		// inc level number 
		levelNumber++;
		
		// if level greater then max level set game over
		
		if (levelNumber > Constants.NUMBEROFLEVELS) {
			
			isGameOver = true;
			
		}
		
		else {
		
			loadLevel();
		
		}
		
	}
	
	private void loadLevel() {
		
		if (newGame) {
			
			// addPlanes();
			newGame = false;
			
		}
		
		else {
			
			// addPlanes();
			resetPlayer();
			
		}
			
	}
	
	private void resetPlayer() {
		
		player.setDeleteFlag();
		
	}
	
	public int returnLevelNumber() {
		
		return levelNumber;
		
	}
	
	public int returnLives() {
		
		return lives;
		
	}
	
	public int returnScore() {
		
		return score;
		
	}
	
	public boolean returnIsGameOver() {
		
		return isGameOver;
	}

	public void render (SpriteBatch batch, float deltaTime) {
							
		// draw player
		player.render(batch);
		
		// draw planes
		for (Plane plane : planes)
			plane.render(batch);
		
		// draw bullets
		for (Bullet bullet : bullets) 
			bullet.render(batch);
		
	}

}
