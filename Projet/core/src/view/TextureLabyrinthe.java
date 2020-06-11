package view;

import com.badlogic.gdx.graphics.Texture;

public class TextureLabyrinthe implements iTexturable {
	private Texture _texture;

    public TextureLabyrinthe (Texture texture) {
        _texture = texture;
    }

    public Texture getTexture () {
        return _texture;
    }
}
