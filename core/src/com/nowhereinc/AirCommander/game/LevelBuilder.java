package com.nowhereinc.AirCommander.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.nowhereinc.AirCommander.game.Level;
import com.nowhereinc.AirCommander.game.objects.Block;
import com.nowhereinc.AirCommander.util.Constants;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class LevelBuilder {
	
	public static final String TAG = Level.class.getName();
	
	public enum BLOCK_TYPE {
		
		EMPTY(0, 0, 0), // black
		BLOCK0(255, 255, 255), //white
		BLOCK1(76, 255, 0), // green
		BLOCK2(0, 33, 255), // blue
		BLOCK3(255, 0, 21), // red
		BLOCK4(255, 239, 17), // yellow
		BLOCK5(250, 0, 255); // purple
		

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
	
	// block position coordinates
	public float[] blockCoordX;
	public float[] blockCoordY;
	
	// block color 
	public int[] blockColor;
	
	// block Indexes
	public int blockIndex;

	
	public LevelBuilder (String filename) {
	
		init();
		load(filename);
		
	}
	
	private void init() {
		
		blockCoordX = new float[150];
		blockCoordY = new float[150];
		blockColor = new int[150];
		
		blockIndex = 0;
		
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
				
				// block white
				else if (BLOCK_TYPE.BLOCK0.sameColor(currentPixel)) {
					
					calculateAdjustedPixels(pixelX, pixelY);					
					blockColor[blockIndex] = 0;
					
					blockIndex++;
					
				}
						
				// block green
				else if (BLOCK_TYPE.BLOCK1.sameColor(currentPixel)) {
					
					calculateAdjustedPixels(pixelX, pixelY);					
					blockColor[blockIndex] = 1;
					
					blockIndex++;
					
				}
				
				// block blue
				else if (BLOCK_TYPE.BLOCK2.sameColor(currentPixel)) {
					
					calculateAdjustedPixels(pixelX, pixelY);
					blockColor[blockIndex] = 2;
					
					blockIndex++;
								
				}
				
				// block red
				else if (BLOCK_TYPE.BLOCK3.sameColor(currentPixel)) {
					
					calculateAdjustedPixels(pixelX, pixelY);
					blockColor[blockIndex] = 3;
					
					blockIndex++;
								
				}
				
				// block yellow
				else if (BLOCK_TYPE.BLOCK4.sameColor(currentPixel)) {
					
					calculateAdjustedPixels(pixelX, pixelY);
					blockColor[blockIndex] = 4;
					
					blockIndex++;
								
				}
				
				// block purple
				else if (BLOCK_TYPE.BLOCK5.sameColor(currentPixel)) {
					
					calculateAdjustedPixels(pixelX, pixelY);
					blockColor[blockIndex] = 5;
					
					blockIndex++;

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
		
		blockCoordX[blockIndex] = adjustedPositionX;
		blockCoordY[blockIndex] = adjustedPositionY;
		
	}

}
