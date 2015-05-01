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
import com.badlogic.gdx.utils.Disposable;
import com.nowhereinc.AirCommander.util.Constants;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class Assets implements Disposable, AssetErrorListener {

	public static final String TAG = Assets.class.getName();

	public static final Assets instance = new Assets();

	private AssetManager assetManager;
	
	public AssetFonts fonts;
	public AssetBullet bullet;
	public AssetPlayer player;	
	
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
	
	public class AssetBullet {
		public final AtlasRegion bullet;

		public AssetBullet (TextureAtlas atlas) {
			bullet = atlas.findRegion("bullet_orange0000");
			
			if (bullet == null) {
				
				Gdx.app.debug(TAG,"Bullet is null");
			}

		}
		
	}
	
	public class AssetPlayer {
		public final AtlasRegion player;

		public AssetPlayer (TextureAtlas atlas) {
			player = atlas.findRegion("Aircraft");
			
			if (player == null) {
				
				Gdx.app.debug(TAG,"Player is null");
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

		TextureAtlas atlas = assetManager.get(Constants.TEXTURE_ATLAS_OBJECTS);

		// enable texture filtering for pixel smoothing
		for (Texture t : atlas.getTextures()) {
			t.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		}

		// create game resource objects
		fonts = new AssetFonts();
		bullet = new AssetBullet(atlas);
		player = new AssetPlayer(atlas);
		
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
