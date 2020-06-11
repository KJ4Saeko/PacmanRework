package model;

import java.util.concurrent.TimeUnit;

/* Classe représentant Pacman, constituée d'une vie ainsi que d'une position */
import com.badlogic.gdx.math.Vector2;

public class Pacman extends GameElementDynamique {
	private boolean _enVie;
	private int direction;
	private int _nbVies;
	private float _velocite;
	private int _fantomeMange;
	private boolean _immunite;
	private int _tempsImmunite;
	
	public Pacman(Vector2 pos, World world) {
		super(pos, world);
		_enVie = true;
		_nbVies = 3;
		_velocite =  0.1f;
		_fantomeMange = 0;
		_immunite = false;
	}

	public void update(float delta) {
		mange();
		intersection();
		degats();
		prendreArmure();
	}

	public boolean getImmunite() {
		return this._immunite;
	}
	
	public void setImmunite(boolean i) {
		this._immunite = i;
	}
	
	public void setTempsImmunite() {
		_tempsImmunite++;
	}
	
	public int getTempsImmunite() {
		return _tempsImmunite;
	}
	
	public void resetTempsImmunite() {
		this._tempsImmunite = 0;
	}
	
	public void mange() {
		Lab lab = this.getWorld().getLab();
		int cX = Math.round(this.getPosition().x);
		int cY = Math.round(this.getPosition().y);
		
		int realY = 27-cX; 
		int realX = cY;
		GameElement g = lab.getGE(realX, realY);
		
		if(g instanceof Vide) {
			Vide p = (Vide)g;
			if(p.estUneGomme()) {
				this.getWorld().decrementeGomme();
			}
			else if(p.estUneSuperGomme()) {
				this.getWorld().getFantomeBleu().setFuite(true);
				
				this.getWorld().getFantomeJaune().setFuite(true);
				this.getWorld().getFantomeRouge().setFuite(true);
				this.getWorld().getFantomeRose().setFuite(true);
				this.getWorld().decrementeGomme();
			}
			p.supprimerGomme();
		}
		
		Vector2 fantomeRougePos = this.getWorld().getFantomeRouge().getPosition();
		Vector2 fantomeRosePos = this.getWorld().getFantomeRose().getPosition();
		Vector2 fantomeBleuPos = this.getWorld().getFantomeBleu().getPosition();
		Vector2 fantomeJaunePos = this.getWorld().getFantomeJaune().getPosition();
		
		if(fantomeJaunePos.x == this.getPosition().x  && fantomeJaunePos.y == this.getPosition().y) {
			if(this.getWorld().getFantomeJaune().enVie()) {
				if(this.getWorld().getFantomeJaune().getFuite()) {
					_fantomeMange++;
					this.getWorld().getFantomeJaune().setFuite(false);
					this.getWorld().getFantomeJaune().setVie(false);
					this.getWorld().getFantomeJaune().setPosition(new Vector2(12,16));
				}
				
			}
		}
		
		if(fantomeRosePos.x == this.getPosition().x  && fantomeRosePos.y == this.getPosition().y) {
			if(this.getWorld().getFantomeRose().enVie()) {
				if(this.getWorld().getFantomeRose().getFuite()) {
					_fantomeMange++;
					this.getWorld().getFantomeRose().setFuite(false);
					this.getWorld().getFantomeRose().setVie(false);
					this.getWorld().getFantomeRose().setPosition(new Vector2(14,16));
				}
			}
		}
		
		if(fantomeRougePos.x == this.getPosition().x  && fantomeRougePos.y == this.getPosition().y) {
			if(this.getWorld().getFantomeRouge().enVie()) {
				if(this.getWorld().getFantomeRouge().getFuite()) {
					_fantomeMange++;
					this.getWorld().getFantomeRouge().setFuite(false);
					this.getWorld().getFantomeRouge().setVie(false);
					this.getWorld().getFantomeRouge().setPosition(new Vector2(13,16));
				}
			}
		}
		
		if(fantomeBleuPos.x == this.getPosition().x  && fantomeBleuPos.y == this.getPosition().y) {
			if(this.getWorld().getFantomeBleu().enVie()) {
				if(this.getWorld().getFantomeBleu().getFuite()) {
					_fantomeMange++;
					this.getWorld().getFantomeBleu().setFuite(false);
					this.getWorld().getFantomeBleu().setVie(false);
					this.getWorld().getFantomeBleu().setPosition(new Vector2(15,16));
				}
			}
		}
	}
	
	public void degats() {
		Lab lab = this.getWorld().getLab();
		int cX = Math.round(this.getPosition().x);
		int cY = Math.round(this.getPosition().y);
		
		int realY = 27-cX; 
		int realX = cY;
		GameElement g = lab.getGE(realX, realY);
		
		if(g instanceof Trappe) {
			Trappe p = (Trappe)g;
			if(p.getOuvert()) {
				this.prendreDegats();
				p.ferme();
			}
		}
	}
	
	public void prendreArmure() {
		Lab lab = this.getWorld().getLab();
		int cX = Math.round(this.getPosition().x);
		int cY = Math.round(this.getPosition().y);
		
		int realY = 27-cX; 
		int realX = cY;
		GameElement g = lab.getGE(realX, realY);
		
		if(g instanceof Armure) {
			Armure a = (Armure)g;
			lab.setGe(realX, realY, new Vide(a.getPosition(), this.getWorld(), false));
			this._immunite = true;
		}
	}
	
	public void intersection() {
		Lab lab = this.getWorld().getLab();
		int cX = Math.round(this.getPosition().x);
		int cY = Math.round(this.getPosition().y);
		
		if(this.getPosition().x == cX && this.getPosition().y == cY) {
			int realY = 27-cX; 
			int realX = cY;
			GameElement g = lab.getGE(realX, realY);
			
			if(g instanceof Vide) {
				Vide p = (Vide)g;
				if(p.estIntersection()) {
					this.setDirection(0);
				}
			}
		}
	}
	
	public void setVelocite(float v) {
		this._velocite = v;
	}
	
	public float getVelocite() {
		return this._velocite;
	}
	
	public void mort() {
		this._enVie = false;
	}
	
	public void prendreDegats() {
		this._nbVies--;
	}
	
	public void ajouterVie() {
		this._nbVies++;
	}
	
	public boolean enVie() {
		return this._enVie;
	}
	
	public int getVies() {
		return this._nbVies;
	}
	
	public void setVies(int v) {
		this._nbVies = v;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}
	
}
