package model;

import com.badlogic.gdx.utils.TimeUtils;
import java.util.Iterator;
import java.util.ArrayList;
import com.badlogic.gdx.math.Vector2;

public class World implements Iterable<GameElement>{
	private Pacman _pacman;
	private FantomeJaune _fantomeJaune;
	private FantomeRouge _fantomeRouge;
	private FantomeRose _fantomeRose;
	private FantomeBleu _fantomeBleu;
	private Feu _feu;
	private Trappe _trap;
	private Lab _lab;
	private int _score;
	private long _time;
	private Canon _canon;
	private Balle _balle;
	private Balle _balle2;

	public World() {
		init();
	}
	
	public void init() {
		/*Initialisation de la position de pacman ainsi que des autres éléments */
		_time = TimeUtils.millis();
		this._pacman = new Pacman(new Vector2(19,1), this);
		this._fantomeJaune = new FantomeJaune(new Vector2(12,16), this);//12,16
		this._fantomeRouge = new FantomeRouge(new Vector2(13,16), this);
		this._fantomeRose = new FantomeRose(new Vector2(14,16), this);
		this._fantomeBleu = new FantomeBleu(new Vector2(15,16), this);
		this._feu = new Feu(new Vector2(2,1), this);
		this._balle = new Balle(new Vector2(2, 16), this);
		this._balle2 = new Balle(new Vector2(25,16), this);
		this._lab = new Lab(this);
		this._score = 0;
	}
	

	public int getHeight() {
		return _lab.getHeight();
	}
	
	public int getWidth() {
		return _lab.getWidth();
	}
	
	public Trappe getTrap() {
		return _trap;
	}
	
	public Pacman getPacman() {
		return _pacman;
	}
	
	public FantomeJaune getFantomeJaune() {
		return _fantomeJaune;
	}
	
	public FantomeRouge getFantomeRouge() {
		return _fantomeRouge;
	}
	
	public FantomeRose getFantomeRose() {
		return _fantomeRose;
	}
	
	public FantomeBleu getFantomeBleu() {
		return _fantomeBleu;
	}
	
	public Feu getFeu() {
		return _feu;
	}
	
	public Balle getBalle() {
		return _balle;
	}
	
	public Balle getBalle2() {
		return _balle2;
	}
	
	public Lab getLab() {
		return _lab;
	}
	
	public long getTime() {
		return _time;
	}
	
	public Canon getCanon() {
		return _canon;
	}
	
	//Methodes pour la gestion du score 
	public void ajouterScore() {
		//this._score += score;
		long elapsedTime = TimeUtils.timeSinceMillis(this._time);
		this.setScore((int)elapsedTime / 20);
	}
		
	public int getScore() {
		return _score;
	}
		
	public void setScore(int score) {
		this._score = score;
	}

	public void decrementeGomme() {
		_lab.decrementeGomme();
	}
		
	public int getNbGomme() {
		return _lab.getNbGomme();
	}
	
	public boolean finDeJeu() {
		if(this.getPacman().getVies() == 0 || this.getNbGomme() == 0) {
			return true;
		}
		return false;
	}
	
	@Override
	public Iterator<GameElement> iterator() {
		return new WorldIterator(this);
	}




	
}