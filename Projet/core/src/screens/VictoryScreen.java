package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;


import packageGame.PacmanGame;

public class VictoryScreen implements Screen {
	private Stage stage;
	private Table table;
	private Skin skin;
	Label label;
	Label label2;
	
	public VictoryScreen(PacmanGame game) {
		stage = new Stage();
		table = new Table();
		createBasicSkin();
		LabelStyle labelStyle = new LabelStyle(new BitmapFont(), Color.WHITE);
		label = new Label("Score: 0", labelStyle);
		label.setFontScale(2);
		LabelStyle labelStyle2 = new LabelStyle(new BitmapFont(), Color.RED);
		label2 = new Label("VICTOIRE", labelStyle2);
		label2.setFontScale(2);
		
		table.add(label2).row();
		table.add(label).row();
		
		table.add( new Label(" ", labelStyle )).row();
		table.add( new Label(" ", labelStyle )).row();
		
		table.setFillParent(true);
		stage.addActor(table);
		Gdx.input.setInputProcessor(stage);	
	}
	
	public void setScore(int score) {
		label.setText("Score: " + score);
	}

	@Override
	public void show() {
	}
	
	private void createBasicSkin(){
		  //Create a font
		  BitmapFont font = new BitmapFont();
		  font.getData().setScale(1);
		  skin = new Skin();
		  skin.add("default", font);
		 
		  //Create a texture
		  Pixmap pixmap = new Pixmap((int)Gdx.graphics.getWidth()/4,(int)Gdx.graphics.getHeight()/10, Pixmap.Format.RGB888);
		  pixmap.setColor(Color.WHITE);
		  skin.add("background",new Texture(pixmap));
	}
		 

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act();
		stage.draw();	
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

}