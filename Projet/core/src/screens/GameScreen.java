package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

import model.World;
import view.TextureFactory;
import controller.WorldRenderer;

public class GameScreen implements Screen, InputProcessor {
	private WorldRenderer _renderer;
	private World _world;
	private Stage infoStage;
	private Label scoreLabel;
	private Label immuniteLabel;
	private Label vieLabel;

	
	public GameScreen() {
		this._world = new World();
		this._renderer = new WorldRenderer(_world);
	}
	
	public void init() {
		this._world.init();
		TextureFactory.reset(_world);
	}

	@Override
	public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		this._renderer.render(delta);	
		scoreLabel.setText("Score: " + _world.getScore());
		immuniteLabel.setText("Immunite : " + _world.getPacman().getImmunite());
		vieLabel.setText("Vies restantes : " + _world.getPacman().getVies());
        infoStage.act();
        infoStage.draw();
	}
	
	@Override
	public void resize(int width, int height) {
		this._renderer.setPpuX(width/(float)this._world.getWidth());
		this._renderer.setPpuY((height*0.85f)/(float)this._world.getHeight());
	}

	@Override
	public void show() {
        Gdx.input.setInputProcessor(this);
		infoStage = new Stage();
		LabelStyle labelStyleScore = new LabelStyle(new BitmapFont(), Color.YELLOW);
		LabelStyle labelStyleVie = new LabelStyle(new BitmapFont(), Color.YELLOW);
		LabelStyle labelStyleImmunite = new LabelStyle(new BitmapFont(), Color.YELLOW);
		
		
		
		scoreLabel = new Label("Score : " + 0, labelStyleScore);
		scoreLabel.setPosition(Gdx.graphics.getWidth() * 0.45f, Gdx.graphics.getHeight() * 0.96f);
		scoreLabel.setSize(20,20);
		vieLabel = new Label("Vie restantes : " + 0, labelStyleVie);
		vieLabel.setPosition(Gdx.graphics.getWidth() * 0.20f, Gdx.graphics.getHeight() * 0.03f);
		immuniteLabel = new Label("Immunite : " + 0, labelStyleImmunite);
		immuniteLabel.setPosition(Gdx.graphics.getWidth() * 0.60f, Gdx.graphics.getHeight() * 0.03f);
		
		infoStage.addActor(scoreLabel);
		infoStage.addActor(vieLabel);
		infoStage.addActor(immuniteLabel);
	}
	
	@Override
	public void hide() {
		
	}
	
	@Override
	public void pause() {
		
	}
	
	@Override
	public void resume() {
		
	}
	
	@Override
	public void dispose() {
		
	}
	
	public World getWorld() {
		return _world;
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return true;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}
