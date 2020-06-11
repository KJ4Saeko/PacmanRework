package model;

/* Classe représentant une trappe, elle est représentée par 2 états : ouvert ou fermée */
import com.badlogic.gdx.math.Vector2;

public class Trappe extends GameElement {
	private boolean ouvert;
	
	public Trappe(Vector2 pos, World world) {
		super(pos, world);
	}
	
	public void ouvre() {
		this.ouvert = true;
	}
	
	public void ferme() {
		this.ouvert = false;
	}
	
	public boolean getOuvert() {
		return this.ouvert;
	}
}
