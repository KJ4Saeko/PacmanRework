package model;

/* Classe représentant une case vide => pacGomme détruite */
import com.badlogic.gdx.math.Vector2;

public class Vide extends GameElement {
	private int gomme;
	private boolean _intersection;
	
	public Vide(Vector2 pos, World world, boolean intersection) {
		super(pos, world);
		gomme = 1;
		this._intersection = intersection;
	}
	
	public void setGomme() {
		this.gomme = 1;
	}
	
	public void setSuperGomme() {
		this.gomme = 2;
	}
	
	public boolean estUneSuperGomme() {
		return gomme == 2;
	}
	
	public void supprimerGomme() {
		this.gomme = 0;
	}
	
	public boolean clearGomme() {
		return gomme == 0;
	}
	
	public boolean estUneGomme() {
		return gomme == 1;
	}
	
	public boolean estIntersection() {
		return this._intersection;
	}

}
