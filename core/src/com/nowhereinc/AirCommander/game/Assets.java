package com.nowhereinc.AirCommander.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.nowhereinc.AirCommander.util.Constants;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class Assets implements Disposable, AssetErrorListener {

	public static final String TAG = Assets.class.getName();

	public static final Assets instance = new Assets();

	private AssetManager assetManager;
	
	public TiledMap map;
	public MapObjects mapObjects;
	public MapLayer mapObjectLayer;
	
	public AssetFonts fonts;
	
	public AssetBullet1 bullet1;
	public AssetBullet2 bullet2;
	
	
	public AssetPlane1 plane1;	
	public AssetPlane2 plane2;	
	public AssetPlane3 plane3;	
	public AssetPlane4 plane4;	
	public AssetPlane5 plane5;	
	public AssetPlane6 plane6;
	public AssetPlane7 plane7;
	public AssetPlane8 plane8;
	public AssetPlane9 plane9;
	public AssetPlane10 plane10;
	public AssetPlane10Hit plane10Hit;	
	
	public AssetTank1 tank1;
	public AssetTank2 tank2;
	
	public AssetTurret1 turret1;
	public AssetTurret2 turret2;
	
	public AssetTruck1 truck1;
	public AssetTruck2 truck2;
	public AssetTruck3 truck3;
	
	public AssetSounds sounds;
	

	// singleton: prevent instantiation from other classes
	private Assets () {
	}
	
	public class AssetFonts {
		public final BitmapFont defaultSmall;
		public final BitmapFont defaultNormal;
		public final BitmapFont defaultBig;

	public AssetFonts () {

			// create three fonts using Libgdx's 15px bitmap font
			defaultSmall = new BitmapFont(Gdx.files.internal("images/arial-15.fnt"), true);
			defaultNormal = new BitmapFont(Gdx.files.internal("images/arial-15.fnt"), true);
			defaultBig = new BitmapFont(Gdx.files.internal("images/arial-15.fnt"), true);
			// set font sizes
			defaultSmall.setScale(0.75f);
			defaultNormal.setScale(1.0f);
			defaultBig.setScale(2.0f);
			// enable linear texture filtering for smooth fonts
			defaultSmall.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
			defaultNormal.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
			defaultBig.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		}
	}
	
	public class AssetBullet1 {
		public final AtlasRegion bullet1;

		public AssetBullet1 (TextureAtlas atlas) {
			bullet1 = atlas.findRegion("bullet_orange0000");
			
			if (bullet1 == null) {
				
				Gdx.app.debug(TAG,"Bullet1 is null");
			}

		}
		
	}
	
	public class AssetBullet2 {
		public final AtlasRegion bullet2;

		public AssetBullet2 (TextureAtlas atlas) {
			bullet2 = atlas.findRegion("bullet_blue0000");
			
			if (bullet2 == null) {
				
				Gdx.app.debug(TAG,"Bullet2 is null");
			}

		}
		
	}
	
	public class AssetPlane1 {
		public final AtlasRegion plane1;

		public AssetPlane1 (TextureAtlas atlas) {
			plane1 = atlas.findRegion("Aircraft_01_normal");
			
			if (plane1 == null) {
				
				Gdx.app.debug(TAG,"Plane1 is null");
			}		
			
		}
		
	}
	
	public class AssetPlane2 {
		public final AtlasRegion plane2;

		public AssetPlane2 (TextureAtlas atlas) {
			plane2 = atlas.findRegion("Aircraft_02_normal");
			
			if (plane2 == null) {
				
				Gdx.app.debug(TAG,"Plane2 is null");
			}		
			
		}
		
	}
	
	public class AssetPlane3 {
		public final AtlasRegion plane3;

		public AssetPlane3 (TextureAtlas atlas) {
			plane3 = atlas.findRegion("Aircraft_03_normal");
			
			if (plane3 == null) {
				
				Gdx.app.debug(TAG,"Plane3 is null");
			}		
			
		}
		
	}
	
	public class AssetPlane4 {
		public final AtlasRegion plane4;

		public AssetPlane4 (TextureAtlas atlas) {
			plane4 = atlas.findRegion("Aircraft_04_normal");
			
			if (plane4 == null) {
				
				Gdx.app.debug(TAG,"Plane4 is null");
			}		
			
		}
		
	}
	
	public class AssetPlane5 {
		public final AtlasRegion plane5;

		public AssetPlane5 (TextureAtlas atlas) {
			plane5 = atlas.findRegion("Aircraft_05_normal");
			
			if (plane5 == null) {
				
				Gdx.app.debug(TAG,"Plane5 is null");
			}		
			
		}
		
	}
	
	public class AssetPlane6 {
		public final AtlasRegion plane6;

		public AssetPlane6 (TextureAtlas atlas) {
			plane6 = atlas.findRegion("Aircraft_06_normal");
			
			if (plane6 == null) {
				
				Gdx.app.debug(TAG,"Plane6 is null");
			}		
			
		}
		
	}
	
	public class AssetPlane7 {
		public final AtlasRegion plane7;

		public AssetPlane7 (TextureAtlas atlas) {
			plane7 = atlas.findRegion("Aircraft_07_normal");
			
			if (plane7 == null) {
				
				Gdx.app.debug(TAG,"Plane7 is null");
			}		
			
		}
		
	}
	
	public class AssetPlane8 {
		public final AtlasRegion plane8;

		public AssetPlane8 (TextureAtlas atlas) {
			plane8 = atlas.findRegion("Aircraft_08_normal");
			
			if (plane8 == null) {
				
				Gdx.app.debug(TAG,"Plane8 is null");
			}		
			
		}
		
	}
	
	public class AssetPlane9 {
		public final AtlasRegion plane9;

		public AssetPlane9 (TextureAtlas atlas) {
			plane9 = atlas.findRegion("Aircraft_09_normal");
			
			if (plane9 == null) {
				
				Gdx.app.debug(TAG,"Plane9 is null");
			}		
			
		}
		
	}
	
	public class AssetPlane10 {
		public final AtlasRegion plane10;

		public AssetPlane10 (TextureAtlas atlas) {
			plane10 = atlas.findRegion("Aircraft_10_normal");
			
			if (plane10 == null) {
				
				Gdx.app.debug(TAG,"Plane10 is null");
			}		
			
		}
		
	}
	
	public class AssetPlane10Hit {
		public final AtlasRegion plane10Hit;

		public AssetPlane10Hit (TextureAtlas atlas) {
			plane10Hit = atlas.findRegion("Aircraft_10_hit");
			
			if (plane10Hit == null) {
				
				Gdx.app.debug(TAG,"Plane10Hit is null");
			}		
			
		}
		
	}
	
	public class AssetTank1 {
		public final AtlasRegion tank1;

		public AssetTank1 (TextureAtlas atlas) {
			tank1 = atlas.findRegion("tank1_body");
			
			if (tank1 == null) {
				
				Gdx.app.debug(TAG,"Tank1 is null");
			}		
			
		}
		
	}
	
	public class AssetTank2 {
		public final AtlasRegion tank2;

		public AssetTank2 (TextureAtlas atlas) {
			tank2 = atlas.findRegion("tank2_body");
			
			if (tank2 == null) {
				
				Gdx.app.debug(TAG,"Tank2 is null");
			}		
			
		}
		
	}
	
	public class AssetTurret1 {
		public final AtlasRegion turret1;

		public AssetTurret1 (TextureAtlas atlas) {
			turret1 = atlas.findRegion("tank1_gun");
			
			if (turret1 == null) {
				
				Gdx.app.debug(TAG,"Turret1 is null");
			}		
			
		}
		
	}
	
	public class AssetTurret2 {
		public final AtlasRegion turret2;

		public AssetTurret2 (TextureAtlas atlas) {
			turret2 = atlas.findRegion("tank2_gun");
			
			if (turret2 == null) {
				
				Gdx.app.debug(TAG,"Turret2 is null");
			}		
			
		}
		
	}
	
	public class AssetTruck1 {
		public final AtlasRegion truck1;

		public AssetTruck1 (TextureAtlas atlas) {
			truck1 = atlas.findRegion("truck1_body");
			
			if (truck1 == null) {
				
				Gdx.app.debug(TAG,"Truck1 is null");
			}		
			
		}
		
	}
	
	public class AssetTruck2 {
		public final AtlasRegion truck2;

		public AssetTruck2 (TextureAtlas atlas) {
			truck2 = atlas.findRegion("truck2_body");
			
			if (truck2 == null) {
				
				Gdx.app.debug(TAG,"Truck2 is null");
			}		
			
		}
		
	}
	
	public class AssetTruck3 {
		public final AtlasRegion truck3;

		public AssetTruck3 (TextureAtlas atlas) {
			truck3 = atlas.findRegion("truck3_body");
			
			if (truck3 == null) {
				
				Gdx.app.debug(TAG,"Truck3 is null");
			}		
			
		}
		
	}
	
	public class AssetSounds {
		
		public final Sound shipShot;
		public final Sound shipExplosion;
		public final Sound gemPickup;
		public final Sound enemyExplosion;
		public final Sound enemySpawn;
		public final Sound bombExplosion;
		
		public AssetSounds (AssetManager am) {
			
			shipShot = am.get("sounds/ship_shot.wav", Sound.class);
			shipExplosion = am.get("sounds/ship_explosion.wav", Sound.class);
			gemPickup = am.get("sounds/gem_pickup.wav", Sound.class);
			enemyExplosion = am.get("sounds/enemy_explosion.wav", Sound.class);
			enemySpawn = am.get("sounds/enemy_spawn.wav", Sound.class);
			bombExplosion = am.get("sounds/bomb_explosion.wav", Sound.class);
			
		}
		
	}

	public void init (AssetManager assetManager) {
		this.assetManager = assetManager;
		// set asset manager error handler
		assetManager.setErrorListener(this);
		
		// load tiled map
		assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
		assetManager.load("maps/AirCommander1.tmx", TiledMap.class);
		
		// load texture atlas
		assetManager.load(Constants.TEXTURE_ATLAS_OBJECTS, TextureAtlas.class);
		
		// load sounds
		assetManager.load("sounds/ship_shot.wav", Sound.class);
		assetManager.load("sounds/ship_explosion.wav", Sound.class);
		assetManager.load("sounds/gem_pickup.wav", Sound.class);
		assetManager.load("sounds/enemy_explosion.wav", Sound.class);
		assetManager.load("sounds/enemy_spawn.wav", Sound.class);
		assetManager.load("sounds/bomb_explosion.wav", Sound.class);
		
		// start loading assets and wait until finished
		assetManager.finishLoading();

		Gdx.app.debug(TAG, "# of assets loaded: " + assetManager.getAssetNames().size);
		for (String a : assetManager.getAssetNames()) {
			Gdx.app.debug(TAG, "asset: " + a);
		}
		
		map = assetManager.get("maps/AirCommander1.tmx");
		mapObjectLayer = map.getLayers().get("Object Layer 1");
		
		mapObjects = mapObjectLayer.getObjects();
		
		TextureAtlas atlas = assetManager.get(Constants.TEXTURE_ATLAS_OBJECTS);

		// enable texture filtering for pixel smoothing
		for (Texture t : atlas.getTextures()) {
			t.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		}

		// create game resource objects
		fonts = new AssetFonts();
		
		bullet1 = new AssetBullet1(atlas);
		bullet2 = new AssetBullet2(atlas);
		
		plane1 = new AssetPlane1(atlas);
		plane2 = new AssetPlane2(atlas);
		plane3 = new AssetPlane3(atlas);
		plane4 = new AssetPlane4(atlas);
		plane5 = new AssetPlane5(atlas);
		plane6 = new AssetPlane6(atlas);
		plane7 = new AssetPlane7(atlas);
		plane8 = new AssetPlane8(atlas);
		plane9 = new AssetPlane9(atlas);
		plane10 = new AssetPlane10(atlas);
		plane10Hit = new AssetPlane10Hit(atlas);
		
		tank1 = new AssetTank1(atlas);
		tank2 = new AssetTank2(atlas);
		
		turret1 = new AssetTurret1(atlas);
		turret2 = new AssetTurret2(atlas);
		
		truck1 = new AssetTruck1(atlas);
		truck2 = new AssetTruck2(atlas);
		truck3 = new AssetTruck3(atlas);
	
		sounds = new AssetSounds(assetManager);
		
	}

	@Override
	public void dispose () {
		assetManager.dispose();
		fonts.defaultSmall.dispose();
		fonts.defaultNormal.dispose();
		fonts.defaultBig.dispose();
	}
	
	@Override
	public void error(@SuppressWarnings("rawtypes") AssetDescriptor asset, Throwable throwable) {
	   Gdx.app.error(getClass().getSimpleName(), "Couldn't load asset '" + asset + "'", throwable);
   }

}
