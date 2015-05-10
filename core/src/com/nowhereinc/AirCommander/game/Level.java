package com.nowhereinc.AirCommander.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.nowhereinc.AirCommander.game.objects.Bullet;
import com.nowhereinc.AirCommander.game.objects.Plane1;
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
	
	// bullets counter
	public int bulletsCounter;
	
	// Player
	public Player player;
	
	// plane 1 single
	public Plane1 plane1;
	
	// plane1 array
	public Array<Plane1> plane1s;
	
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
		
		// plane1s
		plane1s = new Array<Plane1>();
		
		// bullets
		bullets = new Array<Bullet>();
		
		bulletsCounter = 0;
		
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
		
		// plane1s update
		
		for (Plane1 plane1 : plane1s) {
			
			plane1.update(deltaTime, cameraPosition);
			
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
		
		for (Plane1 plane1 : plane1s) {
			
			if (plane1.deletePlane(world)) {
				
				plane1s.removeValue(plane1, true);
				
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
				
				float conPosX = ConvertPosition(rect.x);
				float conPosY = ConvertPosition(rect.y);
				
				if ("Plane1".equals(obj.getProperties().get("type", String.class)) ) {
			
					addPlane1(conPosX, conPosY);
				}
			
			}
				
		}

	}
	
	private float ConvertPosition(float pos) {
		
		return pos / Constants.TILE_SIZE;
		
	}
	
	private void addPlane1(float posX, float posY) {

		
		// create a single plane1
		
		plane1 = null;
		plane1 = new Plane1(world, posX, posY);
		plane1s.add((Plane1)plane1);
		
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
		
		// draw plane1
		for (Plane1 plane1 : plane1s)
			plane1.render(batch);
		
		// draw bullets
		for (Bullet bullet : bullets) 
			bullet.render(batch);
		
	}

}
