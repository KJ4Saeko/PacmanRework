package view;

import com.badlogic.gdx.graphics.Texture;

public class TextureArmure implements iTexturable {
    private double _seuil;
    private double _deltaT;
    private boolean _etat;
    private Texture _textureBase;

    public TextureArmure(double seuil) {
    	this._seuil = seuil;
    	this._deltaT = 0.0;
    	_textureBase = new Texture("shield.png");
    }

	public void render( double delta ) {
        _deltaT += delta;
        if ( _deltaT > _seuil ) {
            _deltaT = 0.0;
        }
    }
	
	public Texture getTexture() {
			return _textureBase;
    }

	public boolean getEtat() {
		return this._etat;
	}
	
}

