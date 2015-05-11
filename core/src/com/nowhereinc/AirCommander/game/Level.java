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
	private Vector2 playerPos;
	
	// game over boolean
	public boolean isGameOver;
	
	// time since last bullet
	private float timeSinceLastBullet;
	
	
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
		playerPos = new Vector2(0,0);
		
		// level game over init
		isGameOver = false;
		
		// init time since last bullet to 0
		timeSinceLastBullet = 0;
		
		newLevel();
		
	}
	
	public void update (float deltaTime, Vector2 cameraPosition) {
		
		// step world
		
		world.step(deltaTime, 8, 3);
		
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
			
		playerPos = player.returnPlayerPosition();
		
		// planes update
		
		for (Plane plane : planes) {
			
			plane.update(deltaTime, cameraPosition);
			
		}
		
		// bullets update
		
		for (Bullet bullet : bullets) {
			
			bullet.update(world, deltaTime, cameraPosition);
			
		}
		
		// if time since last bullet > constant, spawn a bullet
		
		timeSinceLastBullet += deltaTime;
		
		if (timeSinceLastBullet > Constants.BULLET_SPAWN_TIME) {
			
			switch (Gdx.app.getType()) {
			
				case Desktop: 
				
					if (player.returnAButton()) {
					
						bullet = null;
						bullet = new Bullet(world, playerPos);
						bullets.add((Bullet)bullet);
					
						timeSinceLastBullet = 0;
					
					}
				
					break;
				
				case Android:
				
					bullet = null;
					bullet = new Bullet(world, playerPos);
					bullets.add((Bullet)bullet);
				
					timeSinceLastBullet = 0;
				
					break;
				
				default:
					break;
		
			}
			
		}
			
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
			
			addPlanes();
			newGame = false;
			
		}
		
		else {
			
			addPlanes();
			resetPlayer();
			
		}
			
	}
	
	private void addPlanes() {
		
		// loop thru object layer and look for planes
		
		for (MapObject obj : Assets.instance.mapObjectLayer.getObjects()) {
			
			if (obj instanceof RectangleMapObject) {
			
				Rectangle rect = ((RectangleMapObject) obj).getRectangle();
				
				Vector2 conPos = new Vector2(rect.x, rect.y);
				
				conPos = ConvertPosition(conPos);
				
				if ("plane1".equals(obj.getProperties().get("type", String.class)) ) {
			
					addPlane("plane1", conPos.x, conPos.y);
				}
				
				if ("plane2".equals(obj.getProperties().get("type", String.class)) ) {
					
					addPlane("plane2", conPos.x, conPos.y);
				}
				
				if ("plane3".equals(obj.getProperties().get("type", String.class)) ) {
					
					addPlane("plane3", conPos.x, conPos.y);
				}
				
				if ("plane4".equals(obj.getProperties().get("type", String.class)) ) {
					
					addPlane("plane4", conPos.x, conPos.y);
				}
				
				if ("plane5".equals(obj.getProperties().get("type", String.class)) ) {
					
					addPlane("plane5", conPos.x, conPos.y);
				}
				
				if ("plane6".equals(obj.getProperties().get("type", String.class)) ) {
					
					addPlane("plane6", conPos.x, conPos.y);
				}
				
				if ("plane7".equals(obj.getProperties().get("type", String.class)) ) {
					
					addPlane("plane7", conPos.x, conPos.y);
				}
				
				if ("plane8".equals(obj.getProperties().get("type", String.class)) ) {
					
					addPlane("plane8", conPos.x, conPos.y);
				}
				
				if ("plane9".equals(obj.getProperties().get("type", String.class)) ) {
					
					addPlane("plane9", conPos.x, conPos.y);
				}
				
				if ("plane10".equals(obj.getProperties().get("type", String.class)) ) {
					
					addPlane("plane10", conPos.x, conPos.y);
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
	
	private void addPlane(String planeNumber, float posX, float posY) {
		
		plane = null;
		plane = new Plane(world, planeNumber, posX, posY);
		planes.add((Plane)plane);
		
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
