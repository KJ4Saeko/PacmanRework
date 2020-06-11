package model;

import com.badlogic.gdx.math.Vector2;

/* Classe repr�sentant un des fantomes du jeu */

public class FantomeBleu extends GameElementDynamique{
	
	private boolean _enVie;
	private int direction;
	private float _velocite;
	private boolean _fuir;
	private int _tempsFuite;
	private int _tempsMort;

	public FantomeBleu(Vector2 pos, World world) {
		super(pos, world);
		_enVie = true;
		_velocite = 0.1f;
		_fuir = false;
	}

	public void update(float delta) {
		intersection();
	}
	
	public void intersection() {
		Lab lab = this.getWorld().getLab();
		int cX = Math.round(this.getPosition().x);
		int cY = Math.round(this.getPosition().y);
		
		if(this.getPosition().x == cX && this.getPosition().y == cY) {
			int realY = 27-cX; 
			int realX = cY;
			GameElement g = lab.getGE(realX, realY);
			
			if(g instanceof Vide) {
				Vide p = (Vide)g;
				if(p.estIntersection()) {
					this.setDirection(0);
				}
			}
		}
	}
	
	public long getTempsMort() {
		return _tempsMort;
	}
	
	
	public void setTempsMort() {
		this._tempsMort++;
	}
	
	public void resetTempsMort() {
		this._tempsMort = 0;
	}
	
	public boolean getFuite() {
		return this._fuir;
	}
	
	public void setVie(boolean vie) {
		this._enVie = vie;
	}
	
	public long getTempsFuite() {
		return _tempsFuite;
	}
	
	
	public void setTempsFuite() {
		this._tempsFuite++;
	}
	
	public void resetTempsFuite() {
		this._tempsFuite = 0;
	}
	
	
	public void setFuite(boolean fuir) {
		this._fuir = fuir;
	}
	
	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}
	
	public boolean enVie() {
		return this._enVie;
	}
	
	public void setTempsFuite(int t) {
		this._tempsFuite = t;
	}
	
	public void mort() {
		this._enVie = false;
	}
	
	public void setVelocite(float v) {
		this._velocite = v;
	}
	
	public float getVelocite() {
		return this._velocite;
	}
	
}
