package com.nowhereinc.AirCommander;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.nowhereinc.AirCommander.screens.DirectedGame;
import com.nowhereinc.AirCommander.screens.GameScreen;
import com.nowhereinc.AirCommander.game.Assets;

public class AirCommanderMain extends DirectedGame {

	@Override
	public void create () {
		
		// Set Libgdx log level to DEBUG
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		// Load assets
		Assets.instance.init(new AssetManager());
		
		// Initialize controller and renderer
		
		setScreen(new GameScreen(this));
		
	   // Assets.instance.dispose();
		
	}

}
