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
	public static final float LEVEL_INPUT_WIDTH = 32.0f;
	public static final float LEVEL_INPUT_HEIGHT = 45.0f;

	// GUI Width
	public static final float VIEWPORT_GUI_WIDTH = 1920.0f;

	// GUI Height
	public static final float VIEWPORT_GUI_HEIGHT = 1080.0f;

	// Location of description file for texture atlas
	public static final String TEXTURE_ATLAS_OBJECTS = 
	"images/AirCommander.pack.atlas";
	
	// Number of Levels
	public static final int NUMBEROFLEVELS = 5;
	
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
	
	// Time to spend on level until next level is triggered
	public static final float LEVELTIMEAMOUNT = 5.0f;
	
	// Time for explosions, used to determine how long to show score
	public static final float EXPLOSIONDURATION = 0.7f;
	
	// Size of blocks
	public static final float BLOCKXSIZE = .25f;
	public static final float BLOCKYSIZE = .10f;
	
	// Size of player
	public static final float PLAYERXSIZE = .5f;
	public static final float PLAYERYSIZE = .5f;
	
	// Size of sides
	public static final float SIDESXSIZE = .02f;
	public static final float SIDESYSIZE = 8.5f;
	
	// Size of top
	public static final float TOPXSIZE = 11.0f;
	public static final float TOPYSIZE = .02f;
	
	// Game preferences file
	public static final String PREFERENCES = "pong.prefs";
	
	// World to Box
	public static final float WORLD_TO_BOX = .0083f;
	public static final float BOX_TO_WORLD = 120f;
	
	// Max Player Velocity Android
	public static final float MAX_PLAYER_VELOCITY_A = 8.0f;
		
	// Max Ball Velocity Android
	public static final float MAX_BULLET_VELOCITY = 12.0f;
		
	// Player Velocity Increment Android
	public static final float PLAYER_VELOCITY_INC_A = 4.0f;
		
	// Max Player Velocity Desktop
	public static final float MAX_PLAYER_VELOCITY_D = 8.0f;
		
	// Player Velocity Increment Desktop
	public static final float PLAYER_VELOCITY_INC_D = .80f;
		
	// Screen Side Edges Location
	public static final float SCREEN_SIDE_EDGES = 5.5f;
		
	// Max Number of Bullets
	public static final int MAX_BULLETS = 20;
	
	// Interval to fire bullets
	public static final float BULLET_SPAWN_TIME = .33f;
	
	// Object Scroll adjustment
	public static final int OBJECT_SCROLL_ADJUSTMENT = 60;
		
}
