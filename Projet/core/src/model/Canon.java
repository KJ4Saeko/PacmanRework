package model;

/* Classe représentant une trappe, elle est représentée par 2 états : ouvert ou fermée */
import com.badlogic.gdx.math.Vector2;

public class Canon extends GameElement {
	private int _direction;
	private float _velocite;
	
	public Canon(Vector2 pos, World world) {
		super(pos, world);
		_velocite = 0.1f;
	}
	
	public int getDirection() {
		return this._direction;
	}
	
	public void setDirection(int d) {
		this._direction = d;
	}
	
	public void setVelocite(float v) {
		this._velocite = v;
	}
	
	public float getVelocite() {
		return this._velocite;
	}
	
}
