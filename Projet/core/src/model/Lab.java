package model;

/* Classe représentant le labyrinthe ainsi que les positions de ces objets */
import java.util.Iterator;
import java.io.IOException;
import java.io.InputStream;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;

public class Lab implements Iterable<GameElement>{
	private int _height;
	private int _width;
	private World _world;
	private int _nbGommes;

	private GameElement[][] laby;

	
	public Lab(World world) {
		this._world = world;
		_nbGommes = 0;
		this.init();
	}
	
	//Fonction qui permet de construire le labyrinthe
	private void init() {
		setHeight(31);
		setWidth(28);
		this.laby =  new GameElement[this._height][this._width];
		
		FileHandle handle = Gdx.files.internal("labyrinthe.txt");
		InputStream stream = handle.read();
		
		for ( int i = 0; i < this.getHeight(); i++) {
			for( int j = 0; j < this.getWidth(); j++) {
				try {
					char c = (char)stream.read();
					while (!Character.isDigit(c)) {
						c = (char) stream.read();
					}
					if (c == '0') {
						GameElement element = MazeCOR.getCOR().build(this._world, 0, i, j);
						this.laby[30-i][27-j] = element;
					}
					if (c == '1') {
						GameElement element = MazeCOR.getCOR().build(this._world, 1, i, j);
						this.laby[30-i][27-j] = element;
						_nbGommes++;
					}
					if (c == '2') {
						GameElement element = MazeCOR.getCOR().build(this._world, 2, i, j);
						this.laby[30-i][27-j] = element;
						_nbGommes++;
					}
					if(c == '3') {
						GameElement element = MazeCOR.getCOR().build(this._world, 3, i, j);
						this.laby[30-i][27-j] = element;
					}
					if(c == '5') {
						GameElement element = MazeCOR.getCOR().build(this._world, 5, i, j);
						this.laby[30-i][27-j] = element;
						_nbGommes++;
					}
					if (c == '6') {
						GameElement element = MazeCOR.getCOR().build(this._world, 6, i, j);
						this.laby[30-i][27-j] = element;
					}
					if (c == '7') {
						GameElement element = MazeCOR.getCOR().build(this._world, 7, i, j);
						this.laby[30-i][27-j] = element;
					}
					if (c == '8') {
						GameElement element = MazeCOR.getCOR().build(this._world, 8, i, j);
						this.laby[30-i][27-j] = element;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public int getHeight() {
		return _height;
	}

	public int getWidth() {
		return _width;
	}
	
	public void setHeight(int h) {
		this._height = h;
	}
	
	public void setWidth(int w) {
		this._width = w;
	}

	public GameElement getGE(int x, int y) {
		return this.laby[x][y];
	}
	
	public void setGe(int x, int y, GameElement g) {
		this.laby[x][y] = g;
	}
	

	public static class MazeCOR {
		private static MazeCOR _instance = null;

		private MazeCOR() { } 

		public static MazeCOR getCOR() {
			if ( _instance == null ) {
				_instance = new MazeCOR();
			}
			return _instance;
		}
		
		public GameElement build(World world, int elementType, int x, int y ) {
			int realX = y;
			int realY = 31 - x -1;
			switch( elementType ) {
				case 0: return new Block(new Vector2(realX, realY), world);
				case 1: return new Vide(new Vector2(realX, realY), world, false);
				case 2: return new Vide(new Vector2(realX, realY), world, true);
				case 3: return new Vide(new Vector2(realX, realY), world, true);
				case 4: {
					Vide v = new Vide(new Vector2(realX, realY), world, false);
					v.clearGomme();
					return v;
				}
				case 5 : 
					Vide v = new Vide(new Vector2(realX, realY), world, false); 
					v.setSuperGomme();
					return v;
				case 6 : return new Trappe(new Vector2(realX, realY), world);
				case 7 : return new Armure(new Vector2(realX, realY), world);
				case 8 : 
					return new Canon(new Vector2(realX, realY), world);
				default: return new Vide(new Vector2(realX, realY), world, true); 
			}
		}
	}
	
	@Override
	public Iterator<GameElement> iterator() {
		return new LabIterator(this);
	}
	

	//Methodes permettant la gestion des gommes dans le labyrinthe
		public void decrementeGomme() {
			this._nbGommes--;
		}

		public void setNbGomme(int nbGomme) {
			this._nbGommes = nbGomme;
		}
		
		public int getNbGomme() {
			return _nbGommes;
		}
}
