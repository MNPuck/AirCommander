package com.nowhereinc.AirCommander.game;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.math.Vector3;
import com.nowhereinc.AirCommander.game.objects.Player;
import com.nowhereinc.AirCommander.screens.DirectedGame;
import com.nowhereinc.AirCommander.util.Xbox360Pad;
import com.badlogic.gdx.controllers.PovDirection;


public class WorldController extends InputAdapter {

	private static final String TAG = WorldController.class.getName();
	
	private DirectedGame game;
	public Level level;
	
	private Controller player1Controller;
	
	private static boolean startPressed;
	private static boolean selectPressed;
	public static boolean escPressed;
	public static boolean gameOver;
	
	private float tsXAxis;
	private float tsYAxis;

	public WorldController (DirectedGame game) {
		this.game = game;
		init();
	}

	private void init () {
		
		Gdx.input.setInputProcessor(this);
		Gdx.input.setCatchBackKey(true);
		
		switch (Gdx.app.getType()) {
		
			case Desktop: 
				player1Controller = Controllers.getControllers().get(0);
				break;
				
			case Android:
				tsXAxis = 0;
				break;
				
			default:
				break;
		
		}
		
		startPressed = false;
		selectPressed = false;
		gameOver = false;
		escPressed = false;
		
		initLevel();
		
	}
	
	public static boolean isGameOver () {
		return gameOver;
	}
	
	public static boolean isEscPressed () {
		return escPressed;
	}
	
	public static boolean isStartPressed() {
		return startPressed;
	}
	
	public static boolean isSelectPressed() {
		return selectPressed;
	}
	
	public void update (float deltaTime, WorldRenderer worldRenderer) {
		
		//handleDebugInput(deltaTime);
		
		if (!gameOver) {
		
			switch (Gdx.app.getType()) {
		
				case Desktop: 
					readController();
					break;
			
				case Android:
					readScreenInput(worldRenderer);
					break;
			
				default:
					break;
	
			}
			
			level.update(deltaTime);
			level.deleteFlaggedItems();
		
			if (level.lives == 0) {
			
				gameOver = true;
			
			}
		
		}
		

		switch (Gdx.app.getType()) {
		
		case Desktop: 
			checkBackKeyJoystick();
			checkEscKey();
			break;
		
		case Android:
			break;
		
		default:
			break;
				
		}
	
	}
	
	private void readController() {
		
		// player move 
		float leftXAxis = player1Controller.getAxis(Xbox360Pad.AXIS_LEFT_X);
		float leftYAxis = player1Controller.getAxis(Xbox360Pad.AXIS_LEFT_Y);
		
		// player button checks
		boolean aButton = player1Controller.getButton(Xbox360Pad.BUTTON_A);
		boolean bButton = player1Controller.getButton(Xbox360Pad.BUTTON_B);
		boolean xButton = player1Controller.getButton(Xbox360Pad.BUTTON_X);
		boolean yButton = player1Controller.getButton(Xbox360Pad.BUTTON_Y);

		level.player.inputController(leftXAxis, leftYAxis, aButton, bButton,
				                     xButton, yButton);

		
		//pause check
		startPressed = player1Controller.getButton(Xbox360Pad.BUTTON_START);
		
	}
	
	private void readScreenInput(WorldRenderer worldRenderer) {
	
		// see if touch is in green sprite
		
		if (Gdx.input.isTouched()) {
			
			tsXAxis = Gdx.input.getX();
			tsYAxis = Gdx.input.getY();
			
			//set up inputs to pass to convert
			Vector3 tsInputs;
			tsInputs = new Vector3(tsXAxis, tsYAxis, 0f);
			
		    //set up output from convert
			Vector3 tsOutput;
		
			//call convert
			tsOutput = worldRenderer.cameraUnproject(tsInputs);
		
			//call player module
			float wuXAxis = tsOutput.x;
			Player.inputTouchScreen(wuXAxis);
				
		}
		
		if (!Gdx.input.isTouched()) {
				
			Player.inputTouchScreen(0);
			
		}
				
	}
	
	private void testCollisions() {

		// Test collisions

	}
	
	private void checkEscKey() {
		
		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE))
				escPressed = true;

	}
	
	private void checkBackKeyJoystick() {
		
		//select check
		selectPressed =  player1Controller.getButton(Xbox360Pad.BUTTON_BACK);
		
	}
	
	private void initLevel() {
	
		level = new Level();

	}
	
	private void handleDebugInput (float deltaTime) {
		if (Gdx.app.getType() != ApplicationType.Desktop) return;

		// Camera Controls (move)
		float camMoveSpeed = 5 * deltaTime;
		float camMoveSpeedAccelerationFactor = 5;
		if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) camMoveSpeed *= camMoveSpeedAccelerationFactor;
		if (Gdx.input.isKeyPressed(Keys.LEFT)) moveCamera(-camMoveSpeed, 0);
		if (Gdx.input.isKeyPressed(Keys.RIGHT)) moveCamera(camMoveSpeed, 0);
		if (Gdx.input.isKeyPressed(Keys.UP)) moveCamera(0, camMoveSpeed);
		if (Gdx.input.isKeyPressed(Keys.DOWN)) moveCamera(0, -camMoveSpeed);

		// Camera Controls (zoom)
		float camZoomSpeed = 1 * deltaTime;
		float camZoomSpeedAccelerationFactor = 5;
		if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) camZoomSpeed *= camZoomSpeedAccelerationFactor;
	
	}

	private void moveCamera (float x, float y) {
	
	}
	
	@Override
	public boolean keyDown (int keycode) {
		// Reset game world
		if (keycode == Keys.BACK) {
			selectPressed = true;
		}

		return false;
	}

	@Override
	public boolean keyUp (int keycode) {
		// Reset game world
		if (keycode == Keys.R) {
			init();
			Gdx.app.debug(TAG, "Game world resetted");
		}
		// Toggle camera follow
		else if (keycode == Keys.ENTER) {
		
		}
		return false;
	}
	
}