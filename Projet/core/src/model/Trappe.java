package model;

/* Classe repr�sentant une trappe, elle est repr�sent�e par 2 �tats : ouvert ou ferm�e */
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
