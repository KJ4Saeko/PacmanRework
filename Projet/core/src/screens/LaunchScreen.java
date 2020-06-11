package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class LaunchScreen implements Screen, ApplicationListener{
	
	private Stage _stage;
	private Label outputLabel;
	private SpriteBatch _batch;
	
	public LaunchScreen() {
		BitmapFont font = new BitmapFont();
		font.setColor(Color.WHITE);
		_stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(_stage);
		
		int _rowHeight = Gdx.graphics.getHeight()/12;
		int _colWidth = Gdx.graphics.getWidth()/12;
		Skin _skin = new Skin(Gdx.files.internal("data/comic-ui.json"));
		
		Label _titre = new Label("PacMan", _skin, "title");
		_titre.setSize(Gdx.graphics.getWidth(), _rowHeight*2);
		_titre.setPosition(0, Gdx.graphics.getHeight() - _rowHeight*2);
		_titre.setAlignment(Align.center);
		
		_stage.addActor(_titre);
		
		//Création du bouton
		ImageButton _bouton1 = new ImageButton(_skin);
		_bouton1.setSize(_colWidth*4, _rowHeight*2);
        _bouton1.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("play_button_inactive.png"))));
        _bouton1.getStyle().imageDown = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("play_button_active.png"))));
        _bouton1.setPosition(Gdx.graphics.getWidth()/3,250);
        
        


        
        _stage.addActor(_bouton1);
        
        outputLabel = new Label("Press a Button",_skin,"title");
		outputLabel.setSize(Gdx.graphics.getWidth(), _rowHeight);
		outputLabel.setPosition(0, _rowHeight);
		outputLabel.setAlignment(Align.center);
		_stage.addActor(outputLabel);
	}
	
	public void mouseClicked(MouseEvent e) {
		if(e.getButton()==MouseEvent.BUTTON1) {
			Point position = e.getPoint();
			System.out.println(position);
		}
	}
	
	@Override
	public void create() {
	}

	@Override
	public void show() {
	}

	public void getMouse() {
		
	}
	
	@Override
	public void render(float delta) {
		 Gdx.gl.glClearColor(1, 1, 1, 1);
		 Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		 _stage.act();		  
		 _stage.draw();
		
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void hide() {	
	}

	@Override
	public void dispose() {
	}

	@Override
	public void render() {
	}
}