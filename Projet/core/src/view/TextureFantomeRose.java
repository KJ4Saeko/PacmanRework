package view;

import com.badlogic.gdx.graphics.Texture;

import model.FantomeRose;

public class TextureFantomeRose implements iTexturable{

	private Texture _textureBase, _textureFuite, _fantomeMort;
	private FantomeRose _fantomer;
	private double _seuil;
	private double _delta;
	
	
	public TextureFantomeRose(FantomeRose fr, double seuil) {
		this._fantomer = fr;
		this._seuil = seuil;
		_textureBase = new Texture("ghost2.png");
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
		if(_fantomer.getFuite()) {
			return _textureFuite;
		}
		else if(!_fantomer.enVie()) {
			return _fantomeMort;
		}
		else {
			return _textureBase;
		}
	}

}
