package view;

import com.badlogic.gdx.graphics.Texture;

public class TextureSuperPacGomme implements iTexturable {
    private double _seuil;
    private double _deltat;
    private Texture _super1, _super2;

    public TextureSuperPacGomme(double seuil) {
    	this._seuil = seuil;
    	this._deltat = 0.0;
    	this._super1 = new Texture("superpellet.png");
    	this._super2 = new Texture("superpellet-2.png");
    }

	public void render( double delta ) {
        _deltat += delta;
        if ( _deltat > _seuil ) {
            _deltat = 0.0;
        }
    }
	
	public Texture getTexture() {
        if ( _deltat < (_seuil / 2.0) ) {
            return _super1;
        } else {
            return _super2;
        }
    }
}