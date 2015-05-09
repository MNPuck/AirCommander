package com.nowhereinc.AirCommander.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.nowhereinc.AirCommander.game.Level;
import com.nowhereinc.AirCommander.util.Constants;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class LevelBuilder {
	
	public static final String TAG = Level.class.getName();
	
	public enum BLOCK_TYPE {
		
		EMPTY(0, 0, 0), // black
		PLANE1(255, 0, 0), //red
		PLANE2(255, 106, 0), // orange
		PLANE3(255, 216, 0), // yellow
		PLANE4(182, 255, 0), // light green
		PLANE5(76, 255, 0), // green
		PLANE6(0, 255, 255), // light blue
		PLANE7(0, 38, 255), // blue
		PLANE8(178, 0, 255), // purple
		PLANE9(255, 0, 220), // violet
		PLANE10(255, 0, 110); // pink
		

		private int color;

		private BLOCK_TYPE (int r, int g, int b) {
			color = r << 24 | g << 16 | b << 8 | 0xff;
		}

		public boolean sameColor (int color) {
			return this.color == color;
		}

		public int getColor () {
			return color;
		}
		
	} 
	
	// plane position coordinates
	public float[] planeCoordX;
	public float[] planeCoordY;
	
	// plane type 
	public int[] planeType;
	
	// plane Indexes
	public int planeIndex;

	
	public LevelBuilder (String filename) {
	
		init();
		load(filename);
		
	}
	
	private void init() {
		
		planeCoordX = new float[150];
		planeCoordY = new float[150];
		planeType = new int[150];
		
		planeIndex = 0;
		
	}
	
	public void load(String filename) {
		
		// load image file that represents the level data
		Pixmap pixmap = new Pixmap(Gdx.files.internal(filename));
		
		// scan pixels from top-left to bottom-right
		
		int lastPixel = -1;
		
		for (int pixelY = 0; pixelY < pixmap.getHeight(); pixelY++) {
			for (int pixelX = 0; pixelX < pixmap.getWidth(); pixelX++) {
						
				// get color of current pixel as 32-bit RGBA value
				int currentPixel = pixmap.getPixel(pixelX, pixelY);
				
				// find matching color value to identify block type at (x,y)
				// point and create the corresponding game object if there is
				// a match

				// empty space
				if (BLOCK_TYPE.EMPTY.sameColor(currentPixel)) {
				
				}
				
				// block red, plane 1
				else if (BLOCK_TYPE.PLANE1.sameColor(currentPixel)) {
					
					calculateAdjustedPixels(pixelX, pixelY);					
					planeType[planeIndex] = 1;
					
					planeIndex++;
					
				}
				
				// block orange, plane 2
				else if (BLOCK_TYPE.PLANE2.sameColor(currentPixel)) {
					
					calculateAdjustedPixels(pixelX, pixelY);					
					planeType[planeIndex] = 1;
					
					planeIndex++;
					
				}
				
				// block yellow, plane 3
				else if (BLOCK_TYPE.PLANE3.sameColor(currentPixel)) {
					
					calculateAdjustedPixels(pixelX, pixelY);					
					planeType[planeIndex] = 1;
					
					planeIndex++;
					
				}	
				
				// block light green, plane 4
				else if (BLOCK_TYPE.PLANE4.sameColor(currentPixel)) {
					
					calculateAdjustedPixels(pixelX, pixelY);					
					planeType[planeIndex] = 1;
					
					planeIndex++;
					
				}
				
				// block green, plane 5
				else if (BLOCK_TYPE.PLANE5.sameColor(currentPixel)) {
					
					calculateAdjustedPixels(pixelX, pixelY);					
					planeType[planeIndex] = 1;
					
					planeIndex++;
					
				}	
				
				// block light blue, plane 6
				else if (BLOCK_TYPE.PLANE6.sameColor(currentPixel)) {
					
					calculateAdjustedPixels(pixelX, pixelY);					
					planeType[planeIndex] = 1;
					
					planeIndex++;
					
				}	
				
				// block blue, plane 7
				else if (BLOCK_TYPE.PLANE7.sameColor(currentPixel)) {
					
					calculateAdjustedPixels(pixelX, pixelY);					
					planeType[planeIndex] = 1;
					
					planeIndex++;
					
				}	
				
				// block purple, plane 8
				else if (BLOCK_TYPE.PLANE8.sameColor(currentPixel)) {
					
					calculateAdjustedPixels(pixelX, pixelY);					
					planeType[planeIndex] = 1;
					
					planeIndex++;
					
				}	
				
				// block violet, plane 9
				else if (BLOCK_TYPE.PLANE9.sameColor(currentPixel)) {
					
					calculateAdjustedPixels(pixelX, pixelY);					
					planeType[planeIndex] = 1;
					
					planeIndex++;
					
				}	
				
				// block pink, plane 10
				else if (BLOCK_TYPE.PLANE10.sameColor(currentPixel)) {
					
					calculateAdjustedPixels(pixelX, pixelY);					
					planeType[planeIndex] = 1;
					
					planeIndex++;
					
				}	
				
				// unknown object/pixel color
				else {
					// red color channel
					int r = 0xff & (currentPixel >>> 24);
					// green color channel
					int g = 0xff & (currentPixel >>> 16);
					// blue color channel
					int b = 0xff & (currentPixel >>> 8);
					// alpha channel
					int a = 0xff & currentPixel;
					Gdx.app.error(TAG, "Unknown object at x<" + pixelX + "> y<" + pixelY + ">: r<" + r + "> g<" + g + "> b<" + b
									    + "> a<" + a + ">");
				}
				
				lastPixel = currentPixel;
					
			}
			
		}
		
		pixmap.dispose();
				
	}
	
	public void calculateAdjustedPixels(int pixelX, int pixelY) {
		
		// calculate ration between gameboard and level builder input
		
		float gameboardToLevelWidth = Constants.GAMEBOARD_WIDTH / Constants.LEVEL_INPUT_WIDTH;
		float gameboardToLevelHeight = Constants.GAMEBOARD_HEIGHT / Constants.LEVEL_INPUT_HEIGHT;
		
		// change the pixel X to negative if it's under half of level width
		
		float adjustedGameBoardWidth = Constants.GAMEBOARD_WIDTH / 2;
		float adjustedPixelX = pixelX * gameboardToLevelWidth;
		
		adjustedPixelX = adjustedPixelX - adjustedGameBoardWidth;
		
		// change the pixel Y to negative if it's under half of gameworld width
		
		float adjustedGameBoardHeight = Constants.GAMEBOARD_HEIGHT / 2;
		float adjustedPixelY = pixelY * gameboardToLevelHeight;
		
		adjustedPixelY = adjustedGameBoardHeight - adjustedPixelY;
		
		float adjustedPositionX = 0f;
		float adjustedPositionY = 0f;
		
		if (adjustedPixelX < 0)
			adjustedPositionX = adjustedPixelX; //- (Constants.BLOCKXSIZE);
		else
			adjustedPositionX = adjustedPixelX;
	
		
		if (adjustedPixelY < 0)
			adjustedPositionY = adjustedPixelY; // - (Constants.BLOCKYSIZE);
		else
			adjustedPositionY = adjustedPixelY;
		
		planeCoordX[planeIndex] = adjustedPositionX;
		planeCoordY[planeIndex] = adjustedPositionY;
		
	}

}
