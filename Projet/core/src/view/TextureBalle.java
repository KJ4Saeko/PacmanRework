package view;

import com.badlogic.gdx.graphics.Texture;

import model.Balle;

public class TextureBalle implements iTexturable {

    private Texture _textureBase1, _textureBase2, _textureBase3;
    
    private double _seuil;
    private double _deltaT;
    private int _direction;
    private Balle _balle;

    public TextureBalle (Balle balle, double seuil) {
    	this._seuil = seuil;
    	this._deltaT = 0.0;
    	this._balle = balle;
        _textureBase1 = new Texture("balle.png");
    }
    

    public void render( double delta ) {
    	_deltaT += delta;
    	if ( _deltaT > _seuil ) {
    		_deltaT = 0.0;
    	}
    }
    
    public Texture getTexture() {
    	return _textureBase1;
    }
}
