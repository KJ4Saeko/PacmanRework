package view;

import com.badlogic.gdx.graphics.Texture;

import model.FantomeRouge;

public class TextureFantomeRouge implements iTexturable{

	private Texture _textureBase, _textureFuite, _fantomeMort;
	private FantomeRouge _fantomeRouge;
	private double _seuil;
	private double _delta;
	
	
	public TextureFantomeRouge(FantomeRouge fr, double seuil) {
		this._fantomeRouge = fr;
		this._seuil = seuil;
		_textureBase = new Texture("ghost1.png");
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
		if(_fantomeRouge.getFuite()) {
			return _textureFuite;
		}
		else if(!_fantomeRouge.enVie()) {
			return _fantomeMort;
		}
		else {
			return _textureBase;
		}
	}
}
