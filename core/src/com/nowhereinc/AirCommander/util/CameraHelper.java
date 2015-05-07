package com.nowhereinc.AirCommander.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.nowhereinc.AirCommander.game.objects.Player;

public class CameraHelper {

	private static final String TAG = CameraHelper.class.getName();

	private final float MAX_ZOOM_IN = 0.25f;
	private final float MAX_ZOOM_OUT = 10.0f;
	private final float FOLLOW_SPEED = 4.0f;

	private Vector2 position;
	private float zoom;
	private Body target;

	public CameraHelper () {
		position = new Vector2();
		zoom = 1.0f;
	}

	public void update (float deltaTime, Vector2 cameraPosition, Vector2 playerPosition) {
		if (!hasTarget()) return;
		
		// change to only follow on the x Axis
		Vector2 scrollPosition = target.getPosition();
		scrollPosition.y = cameraPosition.y;
		
		
		if (playerPosition.x > (Constants.GAMEBOARD_WIDTH * .5f - Constants.VIEWPORT_WIDTH * .5f)) {
			
			scrollPosition.x = (Constants.GAMEBOARD_WIDTH * .5f - Constants.VIEWPORT_WIDTH * .5f);
			
		}
		
		if (playerPosition.x < (Constants.VIEWPORT_WIDTH * .5f - Constants.GAMEBOARD_WIDTH * .5f)) {
			
			scrollPosition.x = (Constants.VIEWPORT_WIDTH * .5f - Constants.GAMEBOARD_WIDTH * .5f);
			
		}
		
		
		position.lerp(scrollPosition, FOLLOW_SPEED * deltaTime);
		
	}

	public void setPosition (float x, float y) {
		this.position.set(x, y);
	}

	public Vector2 getPosition () {
		return position;
	}

	public void addZoom (float amount) {
		setZoom(zoom + amount);
	}

	public void setZoom (float zoom) {
		this.zoom = MathUtils.clamp(zoom, MAX_ZOOM_IN, MAX_ZOOM_OUT);
	}

	public float getZoom () {
		return zoom;
	}

	public void setTarget (Body target) {
		this.target = target;
	}

	public Body getTarget () {
		return target;
	}

	public boolean hasTarget () {
		return target != null;
	}

	public boolean hasTarget (Body target) {
		return hasTarget() && this.target.equals(target);
	}

	public void applyTo (OrthographicCamera camera) {
		camera.position.x = position.x;
		camera.position.y = position.y;
		camera.zoom = zoom;
		camera.update();
	}

}
