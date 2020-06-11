package view;

import com.badlogic.gdx.graphics.Texture;

import model.FantomeBleu;

public class TextureFantomeBleu implements iTexturable{

	private Texture _textureBase, _textureFuite, _fantomeMort;
	private FantomeBleu _fantomeb;
	private double _seuil;
	private double _delta;
	
	
	public TextureFantomeBleu(FantomeBleu fb, double seuil) {
		this._fantomeb = fb;
		this._seuil = seuil;
		_textureBase = new Texture("ghost3.png");
		_textureFuite = new Texture("ghostEscaping.png");
		_fantomeMort = new Texture("ghostDead.png");
	}
	
    public void render( double delta ) {
    	_delta += delta;
    	if ( _delta > _seuil ) {
    		_delta = 0.0;
    	}
    }
	
	@Override
	public Texture getTexture() {
		if(_fantomeb.getFuite()) {
			return _textureFuite;
		}
		else if(!_fantomeb.enVie()) {
			return _fantomeMort;
		}
		else {
			return _textureBase;
		}

	}

}
