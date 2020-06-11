package model;

/* Classe représentant un bloc, un bloc contient uniquement une position */

import com.badlogic.gdx.math.Vector2;

public class Block extends GameElement {
	public Block(Vector2 pos, World world) {
		super(pos, world);
	}
}
