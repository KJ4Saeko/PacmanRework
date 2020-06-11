package model;

/* Classe repr�sentant une trappe, elle est repr�sent�e par 2 �tats : ouvert ou ferm�e */
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
