package view;

import com.badlogic.gdx.graphics.Texture;

import model.Feu;

public class TextureFeu implements iTexturable {

    private Texture _textureBase1, _textureBase2, _textureBase3;
    
    private double _seuil;
    private double _deltaT;
    private int _direction;
    private Feu _feu;

    public TextureFeu (Feu feu, double seuil) {
    	this._seuil = seuil;
    	this._deltaT = 0.0;
    	this._feu = feu;
        _textureBase1 = new Texture("fire1.png");
        _textureBase2 = new Texture("fire2.png");
        _textureBase3 = new Texture("fire3.png");
    }
    

    public void render( double delta ) {
    	_deltaT += delta;
    	if ( _deltaT > _seuil ) {
    		_deltaT = 0.0;
    	}
    }
    
    public Texture getTexture() {
    	if ( _deltaT < (_seuil / 2.0)) {
            return _textureBase1;
        } 
        else if(_deltaT < (_seuil / 2.0) ) {
            return _textureBase2;
        }
        else{
            return _textureBase3;
        }
    }
}
