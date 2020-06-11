package view;

import com.badlogic.gdx.graphics.Texture;

import model.Canon;

public class TextureCanon implements iTexturable {
    private int _etat;
    private Texture _t1;

    public TextureCanon() {
    	this._t1 = new Texture("tower.png");
    }
	
	public Texture getTexture() {
       return _t1;
    }

	public int getEtat() {
		return this._etat;
	}
	
}
