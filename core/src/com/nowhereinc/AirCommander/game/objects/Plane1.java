package com.nowhereinc.AirCommander.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.nowhereinc.AirCommander.game.Assets;
import com.nowhereinc.AirCommander.game.WorldRenderer;
import com.nowhereinc.AirCommander.util.Constants;

public class Plane1 extends Planes {
	
	public Plane1(World world) {
		
		super(world, "plane1");
		init();
		
	}
	
	private void init() {
		
		body.setUserData(Assets.instance.plane1.plane1);
		
	}
	
	public void update(float deltaTime, Vector2 cameraPosition) {
		
		this.body.setLinearVelocity(0, deltaTime * Constants.SCROLL_SPEED * Constants.OBJECT_SCROLL_ADJUSTMENT);
	
	}

}
