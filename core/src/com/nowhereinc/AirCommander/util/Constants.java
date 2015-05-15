package com.nowhereinc.AirCommander.util;

public class Constants {

	// Visible game world is 9 meters wide
	public static final float VIEWPORT_WIDTH = 9.0f;

	// Visible game world is 16 meters tall
	public static final float VIEWPORT_HEIGHT = 16.0f;
	
	// GameWorld Border
	
	public static final float GAMEBOARD_WIDTH = 12.0f;
	public static final float GAMEBOARD_HEIGHT = 16.0f;
	
	// Level Size Input Size
	public static final float LEVEL_INPUT_WIDTH = 12.0f;
	public static final float LEVEL_INPUT_HEIGHT = 64.0f;

	// GUI Width
	public static final float VIEWPORT_GUI_WIDTH = 1920.0f;

	// GUI Height
	public static final float VIEWPORT_GUI_HEIGHT = 1080.0f;

	// Location of description file for texture atlas
	public static final String TEXTURE_ATLAS_OBJECTS = 
	"images/AirCommander.pack.atlas";
	
	// Number of Levels
	public static final int NUMBEROFLEVELS = 1;
	
	// Amount of extra lives at level start
	public static final int LIVES_START = 9;
	
	// Amount of bombs at level start
	public static final int BOMBS_START = 3;

	// Delay after game over
	public static final float TIME_DELAY_GAME_OVER = 3;

	
	//used for left joystick adjustments
	
	public static final float LEFTJOYADJUSTMENT = 0.2f;
	
	//used for right joystick adjustments
	
	public static final float RIGHTJOYADJUSTMENT = 0.2f;
	
	//used for controller right trigger adjustment
	
	public static final float RIGHTTRIGGERADJUSTMENT = -0.5f;
	
	//used for controller left trigger adjustment
	
	public static final float LEFTTRIGGERADJUSTMENT = 0.5f;
	
	// directions used for movement
	
	public static final int NUM_DIRS = 9;
	public static final int N = 0;  // north, etc going clockwise
	public static final int NE = 1;
	public static final int E = 2;
	public static final int SE = 3;
	public static final int S = 4;
	public static final int SW = 5;
	public static final int W = 6;
	public static final int NW = 7;
	public static final int NIL = 8;
	
	// Size of player
	public static final float PLAYERXSIZE = .5f;
	public static final float PLAYERYSIZE = .5f;
	
	// Size of planes
	public static final float PLANEXSIZE = .5f;
	public static final float PLANEYSIZE = .5f;
	
	// Game preferences file
	public static final String PREFERENCES = "pong.prefs";
	
	// World to Box
	public static final float WORLD_TO_BOX = .0083f;
	public static final float BOX_TO_WORLD = 120f;
	
	// Max Player Velocity Android
	public static final float MAX_PLAYER_VELOCITY_A = 8.0f;
		
	// Max Bullet Velocity Player 
	public static final float MAX_PLAYER_BULLET_VELOCITY = 12.0f;
	
	// Max Bullet Velocity Computer 
	public static final float MAX_COMPUTER_BULLET_VELOCITY = 8.0f;
		
	// Player Velocity Increment Android
	public static final float PLAYER_VELOCITY_INC_A = 4.0f;
		
	// Max Player Velocity Desktop
	public static final float MAX_PLAYER_VELOCITY_D = 8.0f;
		
	// Player Velocity Increment Desktop
	public static final float PLAYER_VELOCITY_INC_D = .80f;
	
	// Max Computer Velocity
	public static final float MAX_COMPUTER_VELOCITY = 5.0f;
	
	// Computer Velocity Inc
	public static final float COMPUTER_VELOCITY_INC = 1.0f;
	
	// Computer Side Velocity Inc
	public static final float COMPUTER_SIDE_VELOCITY_INC = .10f;
	
	// Max Computer Side Velocity Up
	public static final float MAX_COMPUTER_SIDE_VELOCITY_UP = 1.25f;
	
	// Max Computer Side Velocity Down
	public static final float MAX_COMPUTER_SIDE_VELOCITY_DOWN = 3.0f;
		
	// Max Number of Bullets
	public static final int MAX_BULLETS = 20;
	
	// Interval to fire bullets
	public static final float BULLET_SPAWN_TIME_PLAYER = .33f;
	
	// Interval to fire bullets
	public static final float BULLET_SPAWN_TIME_COMPUTER = 1f;
	
	// Object Scroll adjustment
	public static final int OBJECT_SCROLL_ADJUSTMENT = 60;
	
	// Scroll speed
	public static final float SCROLL_SPEED = 2.0f;
	
	// Tile Size
	public static final int TILE_SIZE = 32;
		
}
