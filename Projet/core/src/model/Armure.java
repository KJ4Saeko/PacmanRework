package model;

/* Classe représentant une trappe, elle est représentée par 2 états : ouvert ou fermée */
import com.badlogic.gdx.math.Vector2;

public class Armure extends GameElement {
	private boolean _utilise;
	
	public Armure(Vector2 pos, World world) {
		super(pos, world);
	}
	
	public void ouvre() {
		this._utilise = true;
	}
	
	public void ferme() {
		this._utilise = false;
	}
	
	public boolean getEtat() {
		return this._utilise;
	}
	
	public void setEtat(boolean e) {
		this._utilise = e;
	}
}
