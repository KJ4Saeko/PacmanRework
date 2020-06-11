package view;

import com.badlogic.gdx.graphics.Texture;

public class TextureTrappe implements iTexturable {
    private double _seuil;
    private double _deltaT;
    private int _etat;
    private Texture _t1, _t2, _t3, _t4, _t5, _t6;

    public TextureTrappe(double seuil) {
    	this._seuil = seuil;
    	this._deltaT = 0.0;
    	this._t1 = new Texture("trap.png");
    	this._t2 = new Texture("trap2.png");
    	this._t3 = new Texture("trap3.png");
    	this._t4 = new Texture("trap4.png");
    	this._t5 = new Texture("trap5.png");
    	this._t6 = new Texture("trap6.png");
    	
    }

	public void render( double delta ) {
        _deltaT += delta;
        if ( _deltaT > _seuil ) {
            _deltaT = 0.0;
        }
    }
	
	public Texture getTexture() {
        if ( _deltaT < (_seuil / 6.0)) {
        	this._etat = 1;
            return _t1;
        } 
        else if(_deltaT < (_seuil / 6.0) * 2) {
        	this._etat = 2;
            return _t2;
        }
        else if(_deltaT < (_seuil / 6.0) * 3) {
        	this._etat = 3;
            return _t3;
        }
        else if(_deltaT < (_seuil / 6.0) * 4) {
        	this._etat = 4;
            return _t4;
        }
        else if(_deltaT < (_seuil / 6.0) * 5) {
        	this._etat = 5;
            return _t5;
        }
        else {
        	this._etat = 6;
            return _t6;
        }
    }

	public int getEtat() {
		return this._etat;
	}
	
}

