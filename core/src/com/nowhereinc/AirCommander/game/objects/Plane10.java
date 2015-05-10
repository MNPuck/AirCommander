package com.nowhereinc.AirCommander.game.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.nowhereinc.AirCommander.game.Assets;
import com.nowhereinc.AirCommander.util.Constants;

public class Plane10 extends Planes {
	
	public Plane10(World world, float posX, float posY) {
		
		super(world, "plane10", posX, posY);
		init();
		
	}
	
	private void init() {
		
		body.setUserData(Assets.instance.plane10.plane10);
		
	}
	
	public void update(float deltaTime, Vector2 cameraPosition) {
		
		if (body.getFixtureList().first().getUserData() == "delete") {
			
			setDeleteFlag();
			
		}
		
		// this.body.setLinearVelocity(0, deltaTime * Constants.SCROLL_SPEED * Constants.OBJECT_SCROLL_ADJUSTMENT);
	
	}

}