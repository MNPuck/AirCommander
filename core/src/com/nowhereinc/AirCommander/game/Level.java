package com.nowhereinc.AirCommander.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.math.MathUtils;
import com.nowhereinc.AirCommander.util.AudioManager;
import com.nowhereinc.AirCommander.game.objects.Bullet;
import com.nowhereinc.AirCommander.game.objects.Player;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.nowhereinc.AirCommander.util.Constants;
import java.util.Random;

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
	
	// level objects
	public LevelBuilder levels;
	
	// variable to store level number
	public static int levelNumber;
	
	// variable to store score
	public static int score;
	
	// variable to store lives
	public static int lives;
	
	// variable to see if this is new game
	private boolean newGame; 
	
	// player position
	private Vector2 playerPos;
	
	
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
		
		// bullets
		bullets = new Array<Bullet>();
		
		// add bullets to list
		bullet = null;
		bullet = new Bullet(world);
		bullets.add((Bullet)bullet);
		bulletsCounter++;
		
		// set to new game
		newGame = true;
		
		// set score to 0
		score = 0;
		
		// set live to constant
		lives = Constants.LIVES_START;
	
		// init player pos 
		playerPos = new Vector2(0,0);
		
		newLevel();
		
	}
	
	public void update (float deltaTime) {
		
		// step world
		
		world.step(deltaTime, 8, 3);
		
		// player update
		
		switch (Gdx.app.getType()) {
		
			case Desktop: 
				player.updateDesktop(deltaTime);
				break;
			
			case Android:
				 player.updateAndroid(deltaTime);
				 break;
			
			default:
				 break;
	
		}	
		
		// get player position
			
		playerPos = player.returnPlayerPosition();
		
		// init bullets if enough time has passed
		
		// bullets update
		
		for (Bullet bullet : bullets) {
			
			bullet.update(world, deltaTime);
			
		}
		
	}	
	
	public void deleteFlaggedItems() {
		
		bullet.deleteBullet(world);
		player.deletePlayer(world);
		
	}

	private void newLevel() {
		
		// inc level number 
		levelNumber++;
		
		// if level greater then max level set game over
		
		if (levelNumber > Constants.NUMBEROFLEVELS)
			WorldController.gameOver = true;
		
		// create levels

		/*
		 
		levels = null;
		levels = new LevelBuilder(("levels/level-") + (levelNumber) + (".png"));
		
		loadLevel();
		
		*/
		
	}
	
	private void resetPlayer() {
		
		player.setDeleteFlag();
		
	}

	public void render (SpriteBatch batch, float deltaTime) {
							
		// draw player
		player.render(batch);
		
		// draw bullets
		for (Bullet bullet : bullets) 
			bullet.render(batch);
		
	}

}
