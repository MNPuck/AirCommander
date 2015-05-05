package com.nowhereinc.AirCommander.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.utils.Disposable;
import com.nowhereinc.AirCommander.util.Constants;
import com.nowhereinc.AirCommander.util.GamePreferences;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;


public class WorldRenderer implements Disposable {

	private static final String TAG = WorldRenderer.class.getName();

	private OrthographicCamera camera;
	private OrthographicCamera cameraGUI;
	private SpriteBatch batch;
	private TiledMapRenderer renderer;
	private WorldController worldController;
	
	private Box2DDebugRenderer debugRenderer;
	// private Matrix4 debugMatrix;

	public WorldRenderer (WorldController worldController) {
		this.worldController = worldController;
		init();
	}

	private void init () {
		batch = new SpriteBatch();
		camera = new OrthographicCamera(Constants.GAMEBOARD_WIDTH, Constants.GAMEBOARD_HEIGHT);
		camera.position.set(0, 0, 0);
		camera.update();
		cameraGUI = new OrthographicCamera(Constants.VIEWPORT_GUI_WIDTH, Constants.VIEWPORT_GUI_HEIGHT);
		cameraGUI.position.set(0, 0, 0);
		cameraGUI.setToOrtho(true); // flip y-axis
		cameraGUI.update();
		renderer = new OrthogonalTiledMapRenderer(Assets.instance.map, 1f / 32f);
		
		//debugMatrix = new Matrix4(camera.combined);
		//debugMatrix.scale(Constants.BOX_TO_WORLD, Constants.BOX_TO_WORLD, 1f);
		
		debugRenderer = new Box2DDebugRenderer();
	}

	public void render (float deltaTime) {
		
		renderMap(renderer);
		
		if (!worldController.gameOver)	
			renderWorld(batch, deltaTime);
		
		renderGui(batch);
	}
	
	private void renderMap(TiledMapRenderer renderer) {
		
		float width = (float) (Constants.GAMEBOARD_WIDTH * .5);
		float height = (float) (Constants.GAMEBOARD_HEIGHT * .5);
		
		camera.translate(width, height);
		camera.update();
		renderer.setView(camera);
		renderer.render();
		
	}

	private void renderWorld (SpriteBatch batch, float deltaTime) {
		
		worldController.cameraHelper.applyTo(camera);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		//debugRenderer.render(worldController.level.world, camera.combined);
		worldController.level.render(batch, deltaTime);
		batch.end();
	}
	
	private void renderGui (SpriteBatch batch) {
		batch.setProjectionMatrix(cameraGUI.combined);
		batch.begin();

		// draw score in upper left
		renderGuiScore(batch);
		
		// draw high Score next to score
		//renderGuiHighScore(batch);
		
		// draw multiplier in upper middle
		//renderGuiMultiplier(batch);
		
		// draw lives in upper right
		renderGuiLives(batch);
		
		// draw bombs to the left of lives
		// renderGuiBombs(batch);
		
		// draw FPS text bottom right
		renderGuiFpsCounter(batch);
		
		// draw Level in bottom left
		renderGuiLevel(batch);
		
		// draw game over text
		renderGuiGameOverMessage(batch);

		batch.end();
		
	}
	
	private void renderGuiScore (SpriteBatch batch) {
		float x = 20;
		float y = 15;
		
		BitmapFont fpsFont = Assets.instance.fonts.defaultBig;
		
		fpsFont.setColor(0, 1, 0, 1); // green
		fpsFont.draw(batch, "Score " + worldController.level.returnScore() , x, y);
		fpsFont.setColor(1, 1, 1, 1); // white
	
	}
	
	private void renderGuiHighScore (SpriteBatch batch) {
		
	}
	
	private void renderGuiMultiplier (SpriteBatch batch) {

	}
	
	private void renderGuiLives (SpriteBatch batch) {
		float x = cameraGUI.viewportWidth - 155;
		float y = 15;
		
		BitmapFont fpsFont = Assets.instance.fonts.defaultBig;
		
		fpsFont.setColor(0, 1, 0, 1);
		fpsFont.draw(batch, "Lives: " + worldController.level.returnLives(), x , y);		
		fpsFont.setColor(1, 1, 1, 1); // white
		
	}
	
	private void renderGuiFpsCounter (SpriteBatch batch) {
		float x = cameraGUI.viewportWidth - 155;
		float y = cameraGUI.viewportHeight - 55;
		
		int fps = Gdx.graphics.getFramesPerSecond();
		
		BitmapFont fpsFont = Assets.instance.fonts.defaultBig;
		
		if (fps >= 45) {
			
			fpsFont.setColor(0, 1, 0, 1);
			
		} else if (fps >= 30) {
			
			fpsFont.setColor(1, 1, 0, 1);
			
		} else {
			
			fpsFont.setColor(1, 0, 0, 1);
			
		}
		
		fpsFont.draw(batch, "FPS: " + fps , x, y);
		fpsFont.setColor(1, 1, 1, 1); // white

	}
	
	private void renderGuiLevel (SpriteBatch batch) {
		
		float x = 55;
		float y = cameraGUI.viewportHeight - 55;
		
		BitmapFont fpsFont = Assets.instance.fonts.defaultBig;
		
		fpsFont.setColor(1, 1, 1, 1); // white
		fpsFont.draw(batch, "Level: " + worldController.level.returnLevelNumber() , x, y);
		fpsFont.setColor(1, 1, 1, 1); // white

	}
	
	private void renderGuiGameOverMessage (SpriteBatch batch) {
		float x = cameraGUI.viewportWidth * .5f;
		float y = cameraGUI.viewportHeight * .5f;
		if (worldController.gameOver) {
			BitmapFont fontGameOver = Assets.instance.fonts.defaultBig;
			fontGameOver.setColor(0, 1, 0, 1);
			fontGameOver.drawMultiLine(batch, "GAME OVER", x, y, 1, BitmapFont.HAlignment.CENTER);
			fontGameOver.setColor(1, 1, 1, 1);
		}

	}

	public void resize (int width, int height) {
		camera.viewportWidth = (Constants.VIEWPORT_HEIGHT / height) * width;
		camera.update();
		cameraGUI.viewportHeight = Constants.VIEWPORT_GUI_HEIGHT;
		cameraGUI.viewportWidth = (Constants.VIEWPORT_GUI_HEIGHT / (float)height) * (float)width;
		cameraGUI.position.set(cameraGUI.viewportWidth * .5f,
							   cameraGUI.viewportHeight * .5f,
							   0);
		cameraGUI.update();
	}
	
	public Vector3 cameraUnproject(Vector3 tsInputs) {
		
		return camera.unproject(tsInputs);
		
	}

	@Override
	public void dispose () {
		batch.dispose();
		
	}

}
