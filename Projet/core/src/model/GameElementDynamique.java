package model;

/* Classe pour les éléments dynamique du jeu (fantomes, pacman etc ..) */
import com.badlogic.gdx.math.Vector2;

public abstract class GameElementDynamique extends GameElement {
	
	public GameElementDynamique(Vector2 pos, World world) {
		super(pos, world);
	}
}