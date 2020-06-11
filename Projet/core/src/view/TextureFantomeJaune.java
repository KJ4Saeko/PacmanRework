package view;

import com.badlogic.gdx.graphics.Texture;

import model.FantomeJaune;

public class TextureFantomeJaune implements iTexturable{

	private Texture _textureBase, _textureFuite, _fantomeMort;
	private FantomeJaune _fantomej;
	private double _seuil;
	private double _delta;
	
	
	public TextureFantomeJaune(FantomeJaune fj, double seuil) {
		this._fantomej = fj;
		this._seuil = seuil;
		_textureBase = new Texture("ghost4.png");
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
		if(_fantomej.getFuite()) {
			return _textureFuite;
		}
		else if(!_fantomej.enVie()) {
			return _fantomeMort;
		}
		else {
			return _textureBase;
		}
		
	}

}
