package model;

import com.badlogic.gdx.math.Vector2;

public class Balle extends GameElementDynamique{

	private boolean _ecrase;
	private int direction;
	private float _velocite;
	
	public Balle(Vector2 pos, World world) {
		super(pos, world);
		this._velocite = 0.1f;
	}

	public void setVelocite(float v) {
		this._velocite = v;
	}
	
	public float getVelocite() {
		return this._velocite;
	}
	
	public void ecraser() {
		this._ecrase = false;
	}
	
	public boolean getEtatBalle() {
		return _ecrase;
	}
	
	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}
}
