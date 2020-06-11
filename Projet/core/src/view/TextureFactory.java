package view;

import java.util.HashMap;

import com.badlogic.gdx.graphics.Texture;

import model.Block;
import model.FantomeJaune;
import model.FantomeRose;
import model.FantomeRouge;
import model.FantomeBleu;
import model.PacGomme;
import model.Pacman;
import model.SuperPacGomme;
import model.Armure;
import model.Balle;
import model.Vide;
import model.Trappe;
import model.World;
import model.Canon;
import model.Feu;

public class TextureFactory {
	private static TextureFactory _instance = null;
	private HashMap<Class<?>, iTexturable> _textures;
	
	private TextureFactory(World world) {
		_textures = new HashMap<Class<?>, iTexturable>();
		_textures.put(Pacman.class, new TexturePacman(world.getPacman(), 0.5));
		_textures.put(Canon.class, new TextureCanon());
		_textures.put(FantomeJaune.class, new TextureFantomeJaune(world.getFantomeJaune(), 0.5));
		_textures.put(FantomeRouge.class, new TextureFantomeRouge(world.getFantomeRouge(), 0.5));
		_textures.put(FantomeRose.class, new TextureFantomeRose(world.getFantomeRose(), 0.5));
		_textures.put(FantomeBleu.class, new TextureFantomeBleu(world.getFantomeBleu(), 0.5));
		_textures.put(Feu.class, new TextureFeu(world.getFeu(), 0.5));
		_textures.put(Balle.class, new TextureBalle(world.getBalle(), 0.5));
		_textures.put(Block.class, new TextureLabyrinthe(new Texture("bloc.png")));
		_textures.put(Vide.class, new TextureLabyrinthe(new Texture("dark.png")));
		_textures.put(PacGomme.class, new TextureLabyrinthe(new Texture("pellet.png")));
		_textures.put(Trappe.class, new TextureTrappe(8.0));
		_textures.put(Armure.class, new TextureArmure(1.0));
		_textures.put(SuperPacGomme.class, new TextureSuperPacGomme(2.4));
		
    }
	
	public static TextureFactory getInstance(World world) {
		if ( _instance == null ) {
			_instance = new TextureFactory(world);
		}
		return _instance;
	}
	
	public Texture getTexture(Class<?> c) {
		return _textures.get(c).getTexture();
	}
	
	public iTexturable getTexturable(Class<?> c) {
		return _textures.get(c);
	}
	
	public Texture getTexturePacman() {
		return _textures.get(Pacman.class).getTexture();
	}
	
	public Texture getTextureBloc() {
		return _textures.get(Block.class).getTexture();
	}

	public static void reset(World world) {
		_instance = null;
    }
}
