package com.nowhereinc.AirCommander.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.nowhereinc.AirCommander.game.objects.Bullet;
import com.nowhereinc.AirCommander.game.objects.Plane;
import com.nowhereinc.AirCommander.game.objects.Player;
import com.nowhereinc.AirCommander.game.objects.Tank;
import com.nowhereinc.AirCommander.game.objects.Turret;
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
	
	// tank single
	public Tank tank;
	
	// tank array
	public Array<Tank> tanks;
	
	// turret single
	public Turret turret;
	
	// turret array
	public Array<Turret> turrets;
	
	
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
	
	// tank position
	private Vector2 tankPosition;
	
	// turret position
	private Vector2 turretPosition;
	
	// game over boolean
	public boolean isGameOver;
	
	// time since last bullet
	private float timeSinceLastBulletPlayer;
	
	// time since last bullet
	private float timeSinceLastBulletComputer;
	
	// booelan to tell world controller if map should be scrolled
	private boolean isScrolling;
	
	// tank Counter
	private int tankCounter;
	
	// player delay time;
	private float playerDelayTime;
	
	// player delay switch
	private boolean playerDelaySwitch;
	
	// particle pool 1
	public ParticleEffectPool pool1;
	public Array<PooledEffect> activeEffects1;
	
	// particle pool 2
	public ParticleEffectPool pool2;
	public Array<PooledEffect> activeEffects2;
	
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
		player = new Player(world, new Vector2(0f,0f), false);
		
		// planes
		planes = new Array<Plane>();
		
		// tanks
		tanks = new Array<Tank>();
		
		// turrets
		turrets = new Array<Turret>();
		
		// bullets
		bullets = new Array<Bullet>();
		
		// player delay switch
		playerDelaySwitch = false;
		
		// Particle Pool 1
		
		ParticleEffect explosionEffect1 = new ParticleEffect();
		
		explosionEffect1.load(Gdx.files.internal("particles/enemyExp.pfx"),
				Gdx.files.internal("particles"));
		
		float pScale = Constants.PARTICLESCALE1;

	    float scaling = explosionEffect1.getEmitters().get(0).getScale().getHighMax();
	    explosionEffect1.getEmitters().get(0).getScale().setHigh(scaling * pScale);

	    scaling = explosionEffect1.getEmitters().get(0).getScale().getLowMax();
	    explosionEffect1.getEmitters().get(0).getScale().setLow(scaling * pScale);
	    
	    scaling = explosionEffect1.getEmitters().get(0).getVelocity().getHighMax();
	    explosionEffect1.getEmitters().get(0).getVelocity().setHigh(scaling * pScale);

	    scaling = explosionEffect1.getEmitters().get(0).getVelocity().getLowMax();
	    explosionEffect1.getEmitters().get(0).getVelocity().setLow(scaling * pScale);
	    
	    pool1 = new ParticleEffectPool(explosionEffect1, 10 , 30);
	    activeEffects1 = new Array<PooledEffect>();
	    
		// Particle Pool 2
		
		ParticleEffect explosionEffect2 = new ParticleEffect();
		
		explosionEffect2.load(Gdx.files.internal("particles/enemyExp.pfx"),
				Gdx.files.internal("particles"));
		
		pScale = Constants.PARTICLESCALE2;

	    scaling = explosionEffect2.getEmitters().get(0).getScale().getHighMax();
	    explosionEffect2.getEmitters().get(0).getScale().setHigh(scaling * pScale);

	    scaling = explosionEffect2.getEmitters().get(0).getScale().getLowMax();
	    explosionEffect2.getEmitters().get(0).getScale().setLow(scaling * pScale);
	    
	    scaling = explosionEffect2.getEmitters().get(0).getVelocity().getHighMax();
	    explosionEffect2.getEmitters().get(0).getVelocity().setHigh(scaling * pScale);

	    scaling = explosionEffect2.getEmitters().get(0).getVelocity().getLowMax();
	    explosionEffect2.getEmitters().get(0).getVelocity().setLow(scaling * pScale);
	    
	    pool2 = new ParticleEffectPool(explosionEffect2, 10 , 30);
	    activeEffects2 = new Array<PooledEffect>();
		
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
		
		// init tank pos
		tankPosition = new Vector2(0,0);
		
		// init turret pos
		turretPosition = new Vector2(0,0);
		
		// level game over init
		isGameOver = false;
		
		// init time since last bullet to 0
		timeSinceLastBulletPlayer = 0;
		
		// init time since last bullet to 0
		timeSinceLastBulletComputer = 0;
		
		// set scrolling to true
		isScrolling = true;
		
		// set tank counter to 0
		tankCounter = 0;
		
		newLevel();
		
	}
	
	public void update (float deltaTime, Vector2 cameraPosition) {
		
		// step world
		
		world.step(deltaTime, 8, 3);
		
		// add plane and tank objects that are on screen
		
		addObjects(cameraPosition);
		
		// see if player is in delay period
		
		if (playerDelaySwitch) {
			
			playerDelayTime += deltaTime;
			
			if (playerDelayTime > Constants.PLAYER_DELAY_TIME) {
				
				playerDelaySwitch = false;
				
				player = null;
				player = new Player(world, cameraPosition, true);
				
			}
		
		}
		
		if (!playerDelaySwitch) {
		
			playerUpdate(deltaTime, cameraPosition);
			
		}
		
		// planes update
		
		planesUpdate(deltaTime, cameraPosition);
		
		// tanks update
		
		tanksUpdate(deltaTime, cameraPosition);
			
		// bullets update
		
		for (Bullet bullet : bullets) {
			
			bullet.update(world, deltaTime, cameraPosition);
			
		}
		
		// player bullets spawn
		
		if (!playerDelaySwitch) {
		
			playerBulletsSpawn(deltaTime);
		
		}
		
		// computer bullets spawn
		
		computerBulletsSpawn(deltaTime);
			
	}	
	
	private void addObjects(Vector2 cameraPosition) {
		
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
						isScrolling = false;
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
				
					if ("plane8".equals(obj.getName()) ) {
						
						addPlane(cameraPosition, "plane8", obj.getProperties().get("type", String.class));
						obj.setName("done");
					}
				
					if ("plane9".equals(obj.getName()) ) {
					
						addPlane(cameraPosition, "plane9", obj.getProperties().get("type", String.class));
						obj.setName("done");
					}
				
					if ("plane10".equals(obj.getName()) ) {
					
						addPlane(cameraPosition, "plane10", obj.getProperties().get("type", String.class));
						obj.setName("done");
					}
					
					if ("tank1".equals(obj.getName()) ) {
						
						addTank(cameraPosition, "tank1", obj.getProperties().get("type", String.class));
						obj.setName("done");
					}
					
					if ("tank2".equals(obj.getName()) ) {
						
						addTank(cameraPosition, "tank2", obj.getProperties().get("type", String.class));
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
	
	private void addTank(Vector2 cameraPosition, String tankNumber, String tankOrigin) {
		
		tank = null;
		tank = new Tank(world, cameraPosition, tankNumber, tankOrigin, tankCounter);
		tanks.add((Tank)tank);
		
		addTurret(cameraPosition, tankNumber, tankOrigin, tankCounter);
		
		// add 1 to tank counter for next new tank
		
		tankCounter++;
		
		
	}
	
	private void addTurret(Vector2 cameraPosition, String tankNumber, String tankOrigin, int tankCounter) {
		
		String turretNumber = null;
		
		turret = null;
		
		if (tankNumber.equals("tank1")) {
			
			turretNumber = "turret1";
			
		}
		
		if (tankNumber.equals("tank2")) {
			
			turretNumber = "turret2";
			
		}
		
		turret = new Turret(world, cameraPosition, turretNumber, tankOrigin, tankCounter);
		turrets.add((Turret)turret);
		
	}
	
	public boolean returnScrolling() {
		
		return isScrolling;
		
	}
	
	private void playerUpdate(float deltaTime, Vector2 cameraPosition) {
		
		// player update
		
		switch (Gdx.app.getType()) {
		
			case Desktop: 
				player.updateDesktop(deltaTime, cameraPosition, isScrolling);
				break;
			
			case Android:
				 player.updateAndroid(deltaTime, cameraPosition, isScrolling);
				 break;
			
			default:
				 break;
	
		}	
		
		// get player position
			
		playerPosition = player.returnPlayerPosition();
	
	}
	
	private void planesUpdate(float deltaTime, Vector2 cameraPosition) {
		
		for (Plane plane : planes) {
			
			plane.update(deltaTime, cameraPosition, playerPosition);
			score += plane.returnPlaneScore();
			
		}
	
	}
	
	private void tanksUpdate(float deltaTime, Vector2 cameraPosition) {
		
		for (Tank tank : tanks) {
			
			tank.update(deltaTime, cameraPosition, playerPosition);
			
			Vector2 tankVelocity;
			tankVelocity = new Vector2(0,0);
			tankVelocity = tank.returnTankVelocity();
			
			score += tank.returnTankScore();
			
			for (Turret turret : turrets) {
				
				if (tank.returnTankValue() == turret.returnTurretValue()) {
				
					turret.update(deltaTime, tankVelocity);
					
				}
				
			}
			
		}
		
	}
	
	private void playerBulletsSpawn(float deltaTime) {
		
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
		
	}
	
	private void computerBulletsSpawn(float deltaTime) {
		
		timeSinceLastBulletComputer += deltaTime;
		
		if (timeSinceLastBulletComputer > Constants.BULLET_SPAWN_TIME_COMPUTER) {
			
			// loop thru active planes and get their coordinates and init shot
			
			for (Plane plane : planes) {
				
				if (plane.body.isActive() &&
					plane.returnPlaneType() < 4) {
				
					planePosition = plane.returnPlanePosition();
				
					bullet = null;
					bullet = new Bullet(world, planePosition, Constants.MAX_COMPUTER_BULLET_VELOCITY, 2, Constants.S);
					bullets.add((Bullet)bullet);
				
				}
				
				if (plane.body.isActive() &&
					plane.returnPlaneType() == 4) {
					
					planePosition = plane.returnPlanePosition();
					
					bullet = null;
					bullet = new Bullet(world, planePosition, Constants.MAX_COMPUTER_BULLET_VELOCITY, 2, Constants.S);
					bullets.add((Bullet)bullet);
					
					bullet = null;
					bullet = new Bullet(world, planePosition, Constants.MAX_COMPUTER_BULLET_VELOCITY, 2, Constants.W);
					bullets.add((Bullet)bullet);
					
					bullet = null;
					bullet = new Bullet(world, planePosition, Constants.MAX_COMPUTER_BULLET_VELOCITY, 2, Constants.E);
					bullets.add((Bullet)bullet);
					
				}
				
				
			}
			
			for (Tank tank : tanks) {
				
				boolean bulletShot = false;
				tankPosition = tank.returnTankPosition();
				
				// if player is to the right of tank fire bullet SE
				
				if (tankPosition.x < playerPosition.x - Constants.PLAYERXSIZE * .5f) {
					
					bullet = null;
					bullet = new Bullet(world, tankPosition, Constants.MAX_COMPUTER_BULLET_VELOCITY, 2, Constants.SE);
					bullets.add((Bullet)bullet);
					bulletShot = true;
					
				}
				
				// if player is to the left of tank fire bullet SW
				
				if (tankPosition.x > playerPosition.x + Constants.PLAYERXSIZE * .5f) {
					
					bullet = null;
					bullet = new Bullet(world, tankPosition, Constants.MAX_COMPUTER_BULLET_VELOCITY, 2, Constants.SW);
					bullets.add((Bullet)bullet);
					bulletShot = true;
					
				}
				
				if (!bulletShot) {
					
					bullet = null;
					bullet = new Bullet(world, tankPosition, Constants.MAX_COMPUTER_BULLET_VELOCITY, 2, Constants.S);
					bullets.add((Bullet)bullet);
					bulletShot = true;
					
				}
				
			}
			
			timeSinceLastBulletComputer = 0;
			
		}
		
	}
	
	public void deleteFlaggedItems(Vector2 cameraPosition) {
		
		for (Bullet bullet : bullets) {
			
			if (bullet.deleteBullet(world)) {
				
				bullets.removeValue(bullet, true);
				
			}
			
		}
		
		if (!playerDelaySwitch) {
		
			Vector2 playerCenter = player.returnPlayerCenter();
			deleteFlaggedPlayer(cameraPosition, playerCenter);
		
		}
		
		deleteFlaggedPlanes();
		deleteFlaggedTanks();
		
	}
	
	private void deleteFlaggedPlayer(Vector2 cameraPosition, Vector2 playerCenter) {
		
		if (player.deletePlayer(world)) {
			
			PooledEffect effect = pool2.obtain();
			
			if (effect != null) {
				activeEffects2.add(effect);
				effect.setPosition(playerCenter.x, playerCenter.y);
			}
			
			lives--;
			
			if (lives == 0) {
				
				isGameOver = true;
				
			}
			
			else {
				
				playerDelaySwitch = true;
				playerDelayTime = 0f;
				
			}
			
		}
		
	}
	
	private void deleteFlaggedPlanes() {
		
		for (Plane plane : planes) {
			
			Vector2 planeCenter = plane.returnPlaneCenter();
			
			if (plane.deletePlane(world)) {
				
				if (!isScrolling) {
					
					isScrolling = true;
					
				}
				
				if (plane.returnPlaneType() < 4) {
				
					PooledEffect effect = pool1.obtain();
				
					if (effect != null) {
						activeEffects1.add(effect);
						effect.setPosition(planeCenter.x, planeCenter.y);
					}
				
				}
				
				else {
					
					PooledEffect effect = pool2.obtain();
					
					// middle
					
					if (effect != null) {
						activeEffects2.add(effect);
						effect.setPosition(planeCenter.x, planeCenter.y);
					}
					
				}
				
				planes.removeValue(plane, true);
				
			}
			
			else {
			
				if (plane.removePlane(world)) {
				
					planes.removeValue(plane, true);
					
				}
				
			}
			
		}
		
	}
	
	private void deleteFlaggedTanks() {
		
		for (Tank tank : tanks) {
			
			int saveTankValue = tank.returnTankValue();
			
			Vector2 tankCenter = tank.returnTankCenter();
			
			if (tank.flashTank(world)) {
				
				for (Turret turret : turrets){
					
					if (saveTankValue == turret.returnTurretValue()) {
						
						turret.flashTurret(world);
				
					}
					
				}
				
			}
			
			else {
				
				for (Turret turret : turrets){
					
					if (saveTankValue == turret.returnTurretValue()) {
						
						turret.normalTurret(world);
				
					}
					
				}
				
			}
			
			if (tank.deleteTank(world)) {
				
				for (Turret turret : turrets){
					
					if (saveTankValue == turret.returnTurretValue()) {
						
						turret.deleteTurret(world);
						turrets.removeValue(turret, true);
						
					}
					
				}
				
				PooledEffect effect = pool1.obtain();
				
				if (effect != null) {
					activeEffects1.add(effect);
					effect.setPosition(tankCenter.x, tankCenter.y);
				}
				
				tanks.removeValue(tank, true);
				
			}
			
			else {
				
				if (tank.removeTank(world)) {
					
					for (Turret turret : turrets){
						
						if (saveTankValue == turret.returnTurretValue()) {
							
							turret.deleteTurret(world);
							turrets.removeValue(turret, true);
							
						}
						
					}
					
					tanks.removeValue(tank, true);
					
				}
				
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
			
			newGame = false;
			
		}
		
		else {
			
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
		
		// draw tanks
		for (Tank tank : tanks)
			tank.render(batch);
		
		// draw turret
		for (Turret turret : turrets)
			turret.render(batch);
		
		if (lives > 0 &&
			!playerDelaySwitch) {
						
			player.render(batch);
		
		}
		
		// draw planes
		for (Plane plane : planes)
			plane.render(batch);
		
		// draw pooled particle effect
		
		for (int i = 0; i < activeEffects1.size; ) {
			
			PooledEffect effect = activeEffects1.get(i);
			
			if (effect.isComplete()) {
				pool1.free(effect);
				activeEffects1.removeIndex(i);
			}
			
			else {
				effect.draw(batch, deltaTime);
				i++;
			}
			
		}
		
		for (int i = 0; i < activeEffects2.size; ) {
			
			PooledEffect effect = activeEffects2.get(i);
			
			if (effect.isComplete()) {
				pool2.free(effect);
				activeEffects2.removeIndex(i);
			}
			
			else {
				effect.draw(batch, deltaTime);
				i++;
			}
			
		}
		
		// draw bullets
		for (Bullet bullet : bullets) 
			bullet.render(batch);
		
	}

}
