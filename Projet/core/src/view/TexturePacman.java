package view;

import com.badlogic.gdx.graphics.Texture;

import model.Pacman;

public class TexturePacman implements iTexturable {

    private Texture _textureBase, _textureUp1, _textureUp2,
    							  _textureDown1, _textureDown2,
    							  _textureLeft1, _textureLeft2, 
    							  _textureRight1, _textureRight2;
    private double _seuil;
    private double _deltaT;
    private int _direction;
    private Pacman _pacman;

    public TexturePacman (Pacman pacman, double seuil) {
    	this._seuil = seuil;
    	this._deltaT = 0.0;
    	this._pacman = pacman;
        _textureBase = new Texture("pacman-3.png");
        _textureUp1 = new Texture("pacmanUp.png");
        _textureUp2 = new Texture("pacmanUp-2.png");
		_textureDown1 = new Texture("pacmanDown.png");
		_textureDown2 = new Texture("pacmanDown-2.png");
		_textureLeft1 = new Texture("pacmanLeft.png");
		_textureLeft2 = new Texture("pacmanLeft-2.png");
		_textureRight1 = new Texture("pacmanRight.png");
		_textureRight2 = new Texture("pacmanRight-2.png");
    }
    
    public void render( double delta ) {
    	_deltaT += delta;
    	if ( _deltaT > _seuil ) {
    		_deltaT = 0.0;
    	}
    }

    public void setDirection(int d) {
    	this._direction = d;
    }
    
    public int getDirection() {
    	return _direction;
    }
    
    public Texture getTexture() {
    	if (this._pacman.getDirection() == 1) {
    		if(_deltaT < (_seuil /2.0)) {
    			return _textureLeft1;
    		}
    	 	else {
    	 		return _textureLeft2;
    	 	}
    	}
    	else if (this._pacman.getDirection() == 2) {
    		if(_deltaT < (_seuil / 2.0)) {
    			return _textureRight1;
    		}
    	 	else {
    	 		return _textureRight2;
    	 	}
    	}
    	else if (this._pacman.getDirection() == 3) {
    		if(_deltaT < (_seuil / 2.0)) {
    			return _textureUp1;
    		}
    	 	else {
    	 		return _textureUp2;
    	 	}
    	}
    	else if (this._pacman.getDirection() == 4) {
    		if(_deltaT < (_seuil / 2.0)) {
    			return _textureDown1;
    		}
    	 	else {
    	 		return _textureDown2;
    	 	}
    	}
    	else {
    		return _textureBase;
    	}
    }
}
