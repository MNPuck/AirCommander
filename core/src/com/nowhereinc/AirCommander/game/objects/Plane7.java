package com.nowhereinc.AirCommander.game.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.nowhereinc.AirCommander.game.Assets;

public class Plane7 extends Planes {
	
	public Plane7(World world, float posX, float posY) {
		
		super(world, "plane7", posX, posY);
		init();
		
	}
	
	private void init() {
		
		body.setUserData(Assets.instance.plane7.plane7);
		
	}
	
	public void update(float deltaTime, Vector2 cameraPosition) {
		
		if (body.getFixtureList().first().getUserData() == "delete") {
			
			setDeleteFlag();
			
		}
		
		// this.body.setLinearVelocity(0, deltaTime * Constants.SCROLL_SPEED * Constants.OBJECT_SCROLL_ADJUSTMENT);
	
	}

}
