package model;

/* Classe repr�sentant un �l�ment du jeu, constitu� d'une position et d'une taille au pr�alable d�finie */

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import view.TextureFactory;

public abstract class GameElement {
	public static final float _TAILLE_ELEMENT = 1f;
	private Vector2 _position;
	private World _world;
	
	public GameElement( Vector2 pos, World world ) {
		_position = pos;
		this._world = world;
	}

	public Vector2 getPosition() {
		return _position;
	}
	
	public void setPosition(Vector2 newPos) {
		this._position = newPos;
	}
	
	public float getWidth() {
		return _TAILLE_ELEMENT;
	}
	
	public float getHeight() {
		return _TAILLE_ELEMENT;
	}
	
	public Texture getTexture() {
		return TextureFactory.getInstance(_world).getTexture(this.getClass());
	}

	public World getWorld() {
		return _world;
	}
	
	public Rectangle getBounds() {
		return new Rectangle(_position.x - _TAILLE_ELEMENT / 2, _position.y - _TAILLE_ELEMENT / 2, _TAILLE_ELEMENT, _TAILLE_ELEMENT);
	}
}
