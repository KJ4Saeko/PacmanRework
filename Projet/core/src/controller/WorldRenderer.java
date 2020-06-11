package controller;

/* Classe permettant d'afficher les éléments du jeu 
 * On gérera également les déplacement de Pacaman ici */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import model.Balle;
import model.Canon;
import model.Feu;
import model.GameElement;
import model.PacGomme;
import model.Pacman;
import model.SuperPacGomme;
import model.Trappe;
import model.Vide;
import model.World;
import view.TextureBalle;
import view.TextureFactory;
import view.TexturePacman;
import view.TextureSuperPacGomme;
import view.TextureTrappe;
import view.TextureFeu;


public class WorldRenderer {
	private SpriteBatch _spriteBatch;
	private float _ppuX;
	private float _ppuY;
	private World _world;
	private long _tempsFuite;

	public WorldRenderer( World world ) {
		this._world = world;
		this._spriteBatch = new SpriteBatch();
		this._ppuX = 1;
		this._ppuY = 1;
	}
	
	
	public void setWorld( World world ) {
		this._world = world;
	}
	
	
	public void render(float delta) {
		TexturePacman texturePacman = (TexturePacman) TextureFactory.getInstance(_world).getTexturable(Pacman.class);
		texturePacman.render(delta);
		
		this._spriteBatch.begin();
		for ( GameElement element : this._world ) {
			Texture texture = null;
			if(element instanceof Vide) {
				Vide v = (Vide)element;
				if(v.estUneSuperGomme()) {
					TextureSuperPacGomme textureGomme = (TextureSuperPacGomme) TextureFactory.getInstance(_world).getTexturable(SuperPacGomme.class);
					textureGomme.render(delta);
					
					texture = TextureFactory.getInstance(_world).getTexture(SuperPacGomme.class);
				}
				else if(v.estUneGomme()) {
					texture = TextureFactory.getInstance(_world).getTexture(PacGomme.class);
				}
				else {
					texture = TextureFactory.getInstance(_world).getTexture(Vide.class);
				}
			}else {
				texture = TextureFactory.getInstance(_world).getTexture(element.getClass());
			}
			if(element instanceof Trappe) {
				TextureTrappe textureTrap = (TextureTrappe) TextureFactory.getInstance(_world).getTexturable(Trappe.class);
				Trappe t = (Trappe)element;
				if(textureTrap.getEtat() == 6) {
					t.ouvre();
				}
				else {
					t.ferme();
				}
				textureTrap.render(delta);
				texture = TextureFactory.getInstance(_world).getTexture(Trappe.class);
			}
			if(element instanceof Feu) {
				TextureFeu textureFeu = (TextureFeu) TextureFactory.getInstance(_world).getTexturable(Feu.class);
				textureFeu.render(delta);
			}
			if(element instanceof Balle) {
				TextureBalle textureBalle = (TextureBalle) TextureFactory.getInstance(_world).getTexturable(Balle.class);
				textureBalle.render(delta);
			}
			this._spriteBatch.draw(
                texture,
				element.getPosition().x * element.getWidth() * this._ppuX,
				element.getPosition().y * element.getHeight() * this._ppuY + Gdx.graphics.getHeight() * 0.1f,
				element.getWidth() * this._ppuX,
				element.getHeight() * this._ppuY
			);
		}
		this._spriteBatch.end();
		this._world.getPacman().update(delta);
		this._world.getFantomeJaune().update(delta);
		this._world.getFantomeRouge().update(delta);
		this._world.getFantomeRose().update(delta);
		this._world.getFantomeBleu().update(delta);
		deplacementPacman();	
		degats();
		tirer();
		tirCanon();
		deplacementFantomes();
		
		tempsFuite();
		resetFuite();
		
		tempsMort();
		resetTempsMort();
		
		System.out.println(_world.getPacman().getTempsImmunite());
		checkImmune();
		resetImmunite();
		
		this._world.ajouterScore();
		
	}
	
	public void deplacementFantomes() {
		if(_world.getFantomeBleu().enVie()) {
			deplacementFantomeBleu();
		}
		if(_world.getFantomeRouge().enVie()) {
			deplacementFantomeRouge();
		}
		if(_world.getFantomeRose().enVie()) {
			deplacementFantomeRose();
		}
		if(_world.getFantomeJaune().enVie()) {
			deplacementFantomeJaune();
		}
	}
	
	public void resetFuite() {
		if(_world.getFantomeJaune().getTempsFuite() > 750) {
			_world.getFantomeJaune().setFuite(false);
			_world.getFantomeJaune().resetTempsFuite();
		}
		if(_world.getFantomeRouge().getTempsFuite() > 750) {
			_world.getFantomeRouge().setFuite(false);
			_world.getFantomeRouge().resetTempsFuite();
		}
		if(_world.getFantomeBleu().getTempsFuite() > 750) {
			_world.getFantomeBleu().setFuite(false);
			_world.getFantomeBleu().resetTempsFuite();
		}
		if(_world.getFantomeRose().getTempsFuite() > 750) {
			_world.getFantomeRose().setFuite(false);
			_world.getFantomeRose().resetTempsFuite();
		}
	}
	
	public void tempsFuite() {
		if(_world.getFantomeJaune().getFuite()) {
			_world.getFantomeJaune().setTempsFuite();
		}
		if(_world.getFantomeRouge().getFuite()) {
			_world.getFantomeRouge().setTempsFuite();
		}
		if(_world.getFantomeRose().getFuite()) {
			_world.getFantomeRose().setTempsFuite();
		}
		if(_world.getFantomeBleu().getFuite()) {
			_world.getFantomeBleu().setTempsFuite();
		}
	}
	
	public void tempsMort() {
		if(!_world.getFantomeJaune().enVie()) {
			_world.getFantomeJaune().setTempsMort();
		}
		if(!_world.getFantomeBleu().enVie()) {
			_world.getFantomeBleu().setTempsMort();
		}
		if(!_world.getFantomeRouge().enVie()) {
			_world.getFantomeRouge().setTempsMort();
		}
		if(!_world.getFantomeRose().enVie()) {
			_world.getFantomeRose().setTempsMort();
		}
	}
	
	public void resetTempsMort() {
		if(_world.getFantomeJaune().getTempsMort() > 750) {
			_world.getFantomeJaune().setVie(true);
			_world.getFantomeJaune().resetTempsMort();
		}
		if(_world.getFantomeRouge().getTempsMort() > 750) {
			_world.getFantomeRouge().setVie(true);
			_world.getFantomeRouge().resetTempsMort();
		}
		if(_world.getFantomeBleu().getTempsMort() > 750) {
			_world.getFantomeBleu().setVie(true);
			_world.getFantomeBleu().resetTempsMort();
		}
		if(_world.getFantomeRose().getTempsMort() > 750) {
			_world.getFantomeRose().setVie(true);
			_world.getFantomeRose().resetTempsMort();
		}
	}
	public boolean collision(float x, float y) {
		if(_world.getLab().getGE((int)y, (int)x).getClass().getName() == "model.Block") {
			return true;
		}
		else {
			return false;
		}		
	}
	
	public void checkImmune() {
		if(_world.getPacman().getImmunite() == true) {
			_world.getPacman().setTempsImmunite();
		}
	}
	public void resetImmunite() {
		if(_world.getPacman().getTempsImmunite() > 350) {
			_world.getPacman().resetTempsImmunite();
			_world.getPacman().setImmunite(false);
		}
	}
	
	
	public void degats() {
		Vector2 pos = _world.getPacman().getPosition();
		Vector2 posf1 = _world.getFantomeBleu().getPosition();
		Vector2 posf2 = _world.getFantomeJaune().getPosition();
		Vector2 posf3 = _world.getFantomeRouge().getPosition();
		Vector2 posf4 = _world.getFantomeRose().getPosition();
		
		Vector2 posb1 = _world.getBalle().getPosition();
		Vector2 posb2 = _world.getBalle2().getPosition();
		
		if(pos.x >= posf2.x && pos.x <= posf2.x && pos.y >= posf2.y && pos.y <= posf2.y){
			if(!_world.getFantomeJaune().getFuite()) {
				if(_world.getFantomeJaune().enVie()) {
					_world.getPacman().prendreDegats();
				}
			}
		}
		
		if(pos.x >= posf1.x && pos.x <= posf1.x && pos.y >= posf1.y && pos.y <= posf1.y){
			if(!_world.getFantomeBleu().getFuite()) {
				if(_world.getFantomeBleu().enVie()) {
					_world.getPacman().prendreDegats();
				}
			}
		}
		
		if(pos.x >= posf3.x && pos.x <= posf3.x && pos.y >= posf3.y && pos.y <= posf3.y){
			if(!_world.getFantomeRouge().getFuite()) {
				if(_world.getFantomeRouge().enVie()) {
					_world.getPacman().prendreDegats();
				}
			}
		}
		
		if(pos.x >= posb1.x && pos.x <= posb1.x && pos.y >= posb1.y && pos.y <= posb1.y){
			if(_world.getPacman().getImmunite() == false) {
				_world.getPacman().setVies(-2);
			}	
		}
		
		if(pos.x >= posb2.x && pos.x <= posb2.x && pos.y >= posb2.y && pos.y <= posb2.y){
			if(_world.getPacman().getImmunite() == false) {
				_world.getPacman().setVies(-2);
			}	
		}
	}
	
	public void deplacementFantomeJaune() {
		Vector2 pos = _world.getFantomeJaune().getPosition();
		float _velociteFantome = _world.getFantomeJaune().getVelocite();
		float _distance = 1.0f - _velociteFantome;
		int random = (int) (Math.random() * 4);
		
		if(_world.getFantomeJaune().getCheminBase()) {
			Vector2 pos1 = new Vector2(12,17);
			_world.getFantomeJaune().setPosition(pos1);
			Vector2 pos2 = new Vector2(13,17);
			_world.getFantomeJaune().setPosition(pos2);
			Vector2 pos3 = new Vector2(13,18);
			_world.getFantomeJaune().setPosition(pos3);
			Vector2 pos4 = new Vector2(13,19);
			_world.getFantomeJaune().setPosition(pos4);
			Vector2 pos5 = new Vector2(12,19);
			_world.getFantomeJaune().setPosition(pos5);
			_world.getFantomeJaune().setCheminBase(false);
		}
		else {
			switch(_world.getFantomeJaune().getDirection()) {
			case 0 : 
				_world.getFantomeJaune().setDirection(random+1);
				if( !collision((Math.round((pos.x - _velociteFantome)*100)/100f), pos.y) && !collision((Math.round((pos.x - _velociteFantome)*100)/100f) , pos.y + _distance )) {
					if(_world.getFantomeJaune().getDirection() == 1) {
						Vector2 _positionLeft = new Vector2((Math.round((pos.x - _velociteFantome)*100)/100f),pos.y);
						_world.getFantomeJaune().setPosition(_positionLeft);
					}
				}
					
				
				if(!collision(pos.x + _velociteFantome + _distance, pos.y) && !collision(pos.x + _velociteFantome + _distance, pos.y + _distance)){
					if(_world.getFantomeJaune().getDirection() == 2) {
						Vector2 _positionRight = new Vector2( (Math.round((pos.x + _velociteFantome)*100)/100f),pos.y);
						_world.getFantomeJaune().setPosition(_positionRight);
					}
				}	
				
				if(!collision(pos.x, pos.y +  _velociteFantome + _distance) && !collision(pos.x + _distance , pos.y + _distance + _velociteFantome)) {
					if(_world.getFantomeJaune().getDirection() == 3) {
						 Vector2 _positionUp = new Vector2(pos.x, (Math.round((pos.y + _velociteFantome)*100)/100f));
						 _world.getFantomeJaune().setPosition(_positionUp);
					}
				}
				
				if(!collision(pos.x, (Math.round((pos.y - _velociteFantome)*100)/100f)) && !collision(pos.x + _distance , (Math.round((pos.y - _velociteFantome)*100)/100f))) {
					if(_world.getFantomeJaune().getDirection() == 4) {
						Vector2 _positionDown = new Vector2(pos.x, (Math.round((pos.y - _velociteFantome)*100)/100f));
						_world.getFantomeJaune().setPosition(_positionDown);
					}
				}	
				
				break;
			case 1 : 
				if( !collision((Math.round((pos.x - _velociteFantome)*100)/100f), pos.y) && !collision((Math.round((pos.x - _velociteFantome)*100)/100f) , pos.y + _distance )) {
						Vector2 _positionLeft = new Vector2((Math.round((pos.x - _velociteFantome)*100)/100f),pos.y);
						_world.getFantomeJaune().setPosition(_positionLeft);
				}
				break;
			case 2 : 
				if(!collision(pos.x + _velociteFantome + _distance, pos.y) && !collision(pos.x + _velociteFantome + _distance, pos.y + _distance)){
						Vector2 _positionRight = new Vector2( (Math.round((pos.x + _velociteFantome)*100)/100f),pos.y);
						_world.getFantomeJaune().setPosition(_positionRight);
				}
				break;
			case 3 : 
				if(!collision(pos.x, pos.y +  _velociteFantome + _distance) && !collision(pos.x + _distance , pos.y + _distance + _velociteFantome)) {
						 Vector2 _positionUp = new Vector2(pos.x, (Math.round((pos.y + _velociteFantome)*100)/100f));
						 _world.getFantomeJaune().setPosition(_positionUp);
				}
				break;
			case 4 : 
				if(!collision(pos.x, (Math.round((pos.y - _velociteFantome)*100)/100f)) && !collision(pos.x + _distance , (Math.round((pos.y - _velociteFantome)*100)/100f))) {
						Vector2 _positionDown = new Vector2(pos.x, (Math.round((pos.y - _velociteFantome)*100)/100f));
						_world.getFantomeJaune().setPosition(_positionDown);
				}	
				break;
		}
	}			
}

	public void deplacementFantomeRouge() {
		Vector2 pos = _world.getFantomeRouge().getPosition();
		float _velociteFantome = _world.getFantomeRouge().getVelocite();
		float _distance = 1.0f - _velociteFantome;
		Vector2 _posPacman = _world.getPacman().getPosition();
		int random = (int) (Math.random() * 4);
		float dist = Float.MAX_VALUE;
		double _sq = Math.sqrt(Math.pow((double)(pos.x - _posPacman.x),2.0) + Math.pow((double)(pos.y - _posPacman.y),2.0));
		
		if(_sq > 9) {
			_world.getFantomeRouge().setVelocite(0.05f);
			switch(_world.getFantomeRouge().getDirection()) {
			case 0 : 
				_world.getFantomeRouge().setDirection(random+1);
				if( !collision((Math.round((pos.x - _velociteFantome)*100)/100f), pos.y) && !collision((Math.round((pos.x - _velociteFantome)*100)/100f) , pos.y + _distance )) {
					if(_world.getFantomeRouge().getDirection() == 1) {
						Vector2 _positionLeft = new Vector2((Math.round((pos.x - _velociteFantome)*100)/100f),pos.y);
						_world.getFantomeRouge().setPosition(_positionLeft);
					}
				}
					
				
				if(!collision(pos.x + _velociteFantome + _distance, pos.y) && !collision(pos.x + _velociteFantome + _distance, pos.y + _distance)){
					if(_world.getFantomeRouge().getDirection() == 2) {
						Vector2 _positionRight = new Vector2( (Math.round((pos.x + _velociteFantome)*100)/100f),pos.y);
						_world.getFantomeRouge().setPosition(_positionRight);
					}
				}	
				
				if(!collision(pos.x, pos.y +  _velociteFantome + _distance) && !collision(pos.x + _distance , pos.y + _distance + _velociteFantome)) {
					if(_world.getFantomeRouge().getDirection() == 3) {
						 Vector2 _positionUp = new Vector2(pos.x, (Math.round((pos.y + _velociteFantome)*100)/100f));
						 _world.getFantomeRouge().setPosition(_positionUp);
					}
				}
				
				if(!collision(pos.x, (Math.round((pos.y - _velociteFantome)*100)/100f)) && !collision(pos.x + _distance , (Math.round((pos.y - _velociteFantome)*100)/100f))) {
					if(_world.getFantomeRouge().getDirection() == 4) {
						Vector2 _positionDown = new Vector2(pos.x, (Math.round((pos.y - _velociteFantome)*100)/100f));
						_world.getFantomeRouge().setPosition(_positionDown);
					}
				}	
				
				break;
			case 1 : 
				if( !collision((Math.round((pos.x - _velociteFantome)*100)/100f), pos.y) && !collision((Math.round((pos.x - _velociteFantome)*100)/100f) , pos.y + _distance )) {
						Vector2 _positionLeft = new Vector2((Math.round((pos.x - _velociteFantome)*100)/100f),pos.y);
						_world.getFantomeRouge().setPosition(_positionLeft);
				}
				break;
			case 2 : 
				if(!collision(pos.x + _velociteFantome + _distance, pos.y) && !collision(pos.x + _velociteFantome + _distance, pos.y + _distance)){
						Vector2 _positionRight = new Vector2( (Math.round((pos.x + _velociteFantome)*100)/100f),pos.y);
						_world.getFantomeRouge().setPosition(_positionRight);
				}
				break;
			case 3 : 
				if(!collision(pos.x, pos.y +  _velociteFantome + _distance) && !collision(pos.x + _distance , pos.y + _distance + _velociteFantome)) {
						 Vector2 _positionUp = new Vector2(pos.x, (Math.round((pos.y + _velociteFantome)*100)/100f));
						 _world.getFantomeRouge().setPosition(_positionUp);
				}
				break;
			case 4 : 
				if(!collision(pos.x, (Math.round((pos.y - _velociteFantome)*100)/100f)) && !collision(pos.x + _distance , (Math.round((pos.y - _velociteFantome)*100)/100f))) {
						Vector2 _positionDown = new Vector2(pos.x, (Math.round((pos.y - _velociteFantome)*100)/100f));
						_world.getFantomeRouge().setPosition(_positionDown);
				}	
				break;
			}
		}else {
			_world.getFantomeRouge().setVelocite(0.1f);
			switch(_world.getFantomeRouge().getDirection()) {
			case 0 : 
				_world.getFantomeRouge().setDirection(random+1);
				
				if( !collision((Math.round((pos.x - _velociteFantome)*100)/100f), pos.y) && !collision((Math.round((pos.x - _velociteFantome)*100)/100f) , pos.y + _distance )) {
					if(_world.getFantomeRouge().getDirection() == 1) {
						if(_posPacman.x < pos.x) {
							float _distanceX =  pos.x -_posPacman.x;
							if(_distanceX < dist) {
								Vector2 _positionLeft = new Vector2((Math.round((pos.x - _velociteFantome)*100)/100f),pos.y);
								_world.getFantomeRouge().setPosition(_positionLeft);
							}
						}
					}
				}
					
				
				if(!collision(pos.x + _velociteFantome + _distance, pos.y) && !collision(pos.x + _velociteFantome + _distance, pos.y + _distance)){
					if(_world.getFantomeRouge().getDirection() == 2) {
						if(_posPacman.x > pos.x) {
							float _distanceX =  pos.x -_posPacman.x;
							if(_distanceX < dist) {
								Vector2 _positionRight = new Vector2( (Math.round((pos.x + _velociteFantome)*100)/100f),pos.y);
								_world.getFantomeRouge().setPosition(_positionRight);
							}
						}
					}
				}	
				
				if(!collision(pos.x, pos.y +  _velociteFantome + _distance) && !collision(pos.x + _distance , pos.y + _distance + _velociteFantome)) {
					if(_world.getFantomeRouge().getDirection() == 3) {
						if(_posPacman.y > pos.y) {
							float _distanceX =  _posPacman.y - pos.y ;
							if(_distanceX < dist) {
								 Vector2 _positionUp = new Vector2(pos.x, (Math.round((pos.y + _velociteFantome)*100)/100f));
								 _world.getFantomeRouge().setPosition(_positionUp);
							}
						}
					}
				}
				
				if(!collision(pos.x, (Math.round((pos.y - _velociteFantome)*100)/100f)) && !collision(pos.x + _distance , (Math.round((pos.y - _velociteFantome)*100)/100f))) {
					if(_world.getFantomeRouge().getDirection() == 4) {
						if(_posPacman.y < pos.y) {
							float _distanceX =  pos.y -_posPacman.y  ;
							if(_distanceX < dist) {
								Vector2 _positionDown = new Vector2(pos.x, (Math.round((pos.y - _velociteFantome)*100)/100f));
								_world.getFantomeRouge().setPosition(_positionDown);
							}
						}
					}
				}	
				
				break;
			case 1 : 
				if( !collision((Math.round((pos.x - _velociteFantome)*100)/100f), pos.y) && !collision((Math.round((pos.x - _velociteFantome)*100)/100f) , pos.y + _distance )) {
						Vector2 _positionLeft = new Vector2((Math.round((pos.x - _velociteFantome)*100)/100f),pos.y);
						_world.getFantomeRouge().setPosition(_positionLeft);
				}
				break;
			case 2 : 
				if(!collision(pos.x + _velociteFantome + _distance, pos.y) && !collision(pos.x + _velociteFantome + _distance, pos.y + _distance)){
						Vector2 _positionRight = new Vector2( (Math.round((pos.x + _velociteFantome)*100)/100f),pos.y);
						_world.getFantomeRouge().setPosition(_positionRight);
				}
				break;
			case 3 : 
				if(!collision(pos.x, pos.y +  _velociteFantome + _distance) && !collision(pos.x + _distance , pos.y + _distance + _velociteFantome)) {
						 Vector2 _positionUp = new Vector2(pos.x, (Math.round((pos.y + _velociteFantome)*100)/100f));
						 _world.getFantomeRouge().setPosition(_positionUp);
				}
				break;
			case 4 : 
				if(!collision(pos.x, (Math.round((pos.y - _velociteFantome)*100)/100f)) && !collision(pos.x + _distance , (Math.round((pos.y - _velociteFantome)*100)/100f))) {
						Vector2 _positionDown = new Vector2(pos.x, (Math.round((pos.y - _velociteFantome)*100)/100f));
						_world.getFantomeRouge().setPosition(_positionDown);
				}	
				break;
		}
		}
			
}
	
	public void deplacementFantomeRose() {
		Vector2 pos = _world.getFantomeRose().getPosition();
		float _velociteFantome = _world.getFantomeRose().getVelocite();
		float _distance = 1.0f - _velociteFantome;
		int random = (int) (Math.random() * 4);
		
		
			switch(_world.getFantomeRose().getDirection()) {
				case 0 : 
					_world.getFantomeRose().setDirection(random+1);
					if( !collision((Math.round((pos.x - _velociteFantome)*100)/100f), pos.y) && !collision((Math.round((pos.x - _velociteFantome)*100)/100f) , pos.y + _distance )) {
						if(_world.getFantomeRose().getDirection() == 1) {
							Vector2 _positionLeft = new Vector2((Math.round((pos.x - _velociteFantome)*100)/100f),pos.y);
							_world.getFantomeRose().setPosition(_positionLeft);
						}
					}
						
					
					if(!collision(pos.x + _velociteFantome + _distance, pos.y) && !collision(pos.x + _velociteFantome + _distance, pos.y + _distance)){
						if(_world.getFantomeRose().getDirection() == 2) {
							Vector2 _positionRight = new Vector2( (Math.round((pos.x + _velociteFantome)*100)/100f),pos.y);
							_world.getFantomeRose().setPosition(_positionRight);
						}
					}	
					
					if(!collision(pos.x, pos.y +  _velociteFantome + _distance) && !collision(pos.x + _distance , pos.y + _distance + _velociteFantome)) {
						if(_world.getFantomeRose().getDirection() == 3) {
							 Vector2 _positionUp = new Vector2(pos.x, (Math.round((pos.y + _velociteFantome)*100)/100f));
							 _world.getFantomeRose().setPosition(_positionUp);
						}
					}
					
					if(!collision(pos.x, (Math.round((pos.y - _velociteFantome)*100)/100f)) && !collision(pos.x + _distance , (Math.round((pos.y - _velociteFantome)*100)/100f))) {
						if(_world.getFantomeRose().getDirection() == 4) {
							Vector2 _positionDown = new Vector2(pos.x, (Math.round((pos.y - _velociteFantome)*100)/100f));
							_world.getFantomeRose().setPosition(_positionDown);
						}
					}	
					
					break;
				case 1 : 
					if( !collision((Math.round((pos.x - _velociteFantome)*100)/100f), pos.y) && !collision((Math.round((pos.x - _velociteFantome)*100)/100f) , pos.y + _distance )) {
							Vector2 _positionLeft = new Vector2((Math.round((pos.x - _velociteFantome)*100)/100f),pos.y);
							_world.getFantomeRose().setPosition(_positionLeft);
					}
					break;
				case 2 : 
					if(!collision(pos.x + _velociteFantome + _distance, pos.y) && !collision(pos.x + _velociteFantome + _distance, pos.y + _distance)){
							Vector2 _positionRight = new Vector2( (Math.round((pos.x + _velociteFantome)*100)/100f),pos.y);
							_world.getFantomeRose().setPosition(_positionRight);
					}
					break;
				case 3 : 
					if(!collision(pos.x, pos.y +  _velociteFantome + _distance) && !collision(pos.x + _distance , pos.y + _distance + _velociteFantome)) {
							 Vector2 _positionUp = new Vector2(pos.x, (Math.round((pos.y + _velociteFantome)*100)/100f));
							 _world.getFantomeRose().setPosition(_positionUp);
					}
					break;
				case 4 : 
					if(!collision(pos.x, (Math.round((pos.y - _velociteFantome)*100)/100f)) && !collision(pos.x + _distance , (Math.round((pos.y - _velociteFantome)*100)/100f))) {
							Vector2 _positionDown = new Vector2(pos.x, (Math.round((pos.y - _velociteFantome)*100)/100f));
							_world.getFantomeRose().setPosition(_positionDown);
					}	
					break;
			}
}
	
	public void deplacementFantomeBleu() {
		Vector2 pos = _world.getFantomeBleu().getPosition();
		float _velociteFantome = _world.getFantomeBleu().getVelocite();
		float _distance = 1.0f - _velociteFantome;
		int random = (int) (Math.random() * 4);
		
		
			switch(_world.getFantomeBleu().getDirection()) {
				case 0 : 
					_world.getFantomeBleu().setDirection(random+1);
					if( !collision((Math.round((pos.x - _velociteFantome)*100)/100f), pos.y) && !collision((Math.round((pos.x - _velociteFantome)*100)/100f) , pos.y + _distance )) {
						if(_world.getFantomeBleu().getDirection() == 1) {
							Vector2 _positionLeft = new Vector2((Math.round((pos.x - _velociteFantome)*100)/100f),pos.y);
							_world.getFantomeBleu().setPosition(_positionLeft);
						}
					}
						
					
					if(!collision(pos.x + _velociteFantome + _distance, pos.y) && !collision(pos.x + _velociteFantome + _distance, pos.y + _distance)){
						if(_world.getFantomeBleu().getDirection() == 2) {
							Vector2 _positionRight = new Vector2( (Math.round((pos.x + _velociteFantome)*100)/100f),pos.y);
							_world.getFantomeBleu().setPosition(_positionRight);
						}
					}	
					
					if(!collision(pos.x, pos.y +  _velociteFantome + _distance) && !collision(pos.x + _distance , pos.y + _distance + _velociteFantome)) {
						if(_world.getFantomeBleu().getDirection() == 3) {
							 Vector2 _positionUp = new Vector2(pos.x, (Math.round((pos.y + _velociteFantome)*100)/100f));
							 _world.getFantomeBleu().setPosition(_positionUp);
						}
					}
					
					if(!collision(pos.x, (Math.round((pos.y - _velociteFantome)*100)/100f)) && !collision(pos.x + _distance , (Math.round((pos.y - _velociteFantome)*100)/100f))) {
						if(_world.getFantomeBleu().getDirection() == 4) {
							Vector2 _positionDown = new Vector2(pos.x, (Math.round((pos.y - _velociteFantome)*100)/100f));
							_world.getFantomeBleu().setPosition(_positionDown);
						}
					}	
					
					break;
				case 1 : 
					if( !collision((Math.round((pos.x - _velociteFantome)*100)/100f), pos.y) && !collision((Math.round((pos.x - _velociteFantome)*100)/100f) , pos.y + _distance )) {
							Vector2 _positionLeft = new Vector2((Math.round((pos.x - _velociteFantome)*100)/100f),pos.y);
							_world.getFantomeBleu().setPosition(_positionLeft);
					}
					break;
				case 2 : 
					if(!collision(pos.x + _velociteFantome + _distance, pos.y) && !collision(pos.x + _velociteFantome + _distance, pos.y + _distance)){
							Vector2 _positionRight = new Vector2( (Math.round((pos.x + _velociteFantome)*100)/100f),pos.y);
							_world.getFantomeBleu().setPosition(_positionRight);
					}
					break;
				case 3 : 
					if(!collision(pos.x, pos.y +  _velociteFantome + _distance) && !collision(pos.x + _distance , pos.y + _distance + _velociteFantome)) {
							 Vector2 _positionUp = new Vector2(pos.x, (Math.round((pos.y + _velociteFantome)*100)/100f));
							 _world.getFantomeBleu().setPosition(_positionUp);
					}
					break;
				case 4 : 
					if(!collision(pos.x, (Math.round((pos.y - _velociteFantome)*100)/100f)) && !collision(pos.x + _distance , (Math.round((pos.y - _velociteFantome)*100)/100f))) {
							Vector2 _positionDown = new Vector2(pos.x, (Math.round((pos.y - _velociteFantome)*100)/100f));
							_world.getFantomeBleu().setPosition(_positionDown);
					}	
					break;
			}
}

	public void deplacementPacman() {
		float _velocitePacman = _world.getPacman().getVelocite();
		float _distance = 1.0f - _velocitePacman;		
		Vector2 pos = _world.getPacman().getPosition();
		
		switch(_world.getPacman().getDirection()) {
		  case 1:
			  Vector2 _positionLeft = new Vector2( (Math.round((pos.x - _velocitePacman)*100)/100f),pos.y);
				  if( !collision((Math.round((pos.x - _velocitePacman)*100)/100f), pos.y) && !collision((Math.round((pos.x - _velocitePacman)*100)/100f) , pos.y + _distance )) {
					  _world.getPacman().setPosition(_positionLeft);
				  }
			  break;
		  case 2:
			  Vector2 _positionRight = new Vector2( (Math.round((pos.x + _velocitePacman)*100)/100f),pos.y);
				  if(!collision(pos.x + _velocitePacman + _distance, pos.y) && !collision(pos.x + _velocitePacman + _distance, pos.y + _distance)){
						 _world.getPacman().setPosition(_positionRight); 
				  }
			  break;
		  case 3:
			  Vector2 _positionUp = new Vector2(pos.x, (Math.round((pos.y + _velocitePacman)*100)/100f));
				  if(!collision(pos.x, pos.y +  _velocitePacman + _distance) && !collision(pos.x + _distance , pos.y + _distance + _velocitePacman)) {
					  _world.getPacman().setPosition(_positionUp); 
				  }
			  break;
		  case 4:
			  Vector2 _positionDown = new Vector2(pos.x, (Math.round((pos.y - _velocitePacman)*100)/100f));
			  	if(!collision(pos.x, (Math.round((pos.y - _velocitePacman)*100)/100f)) && !collision(pos.x + _distance , (Math.round((pos.y - _velocitePacman)*100)/100f))) {
					  _world.getPacman().setPosition(_positionDown); 
				  }
			  	break;
		  default:
		}
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)){
			Vector2 _positionLeft = new Vector2((Math.round((pos.x - _velocitePacman)*100)/100f),pos.y);
				if(!collision((Math.round((pos.x - _velocitePacman)*100)/100f), pos.y) && !collision((Math.round((pos.x - _velocitePacman)*100)/100f), pos.y + _distance)) {
					_world.getPacman().setPosition(_positionLeft);
					_world.getPacman().setDirection(1);
				}
		}
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)){
			  Vector2 _positionRight = new Vector2( (Math.round((pos.x + _velocitePacman)*100)/100f),pos.y);
			if(!collision(pos.x +  _velocitePacman + _distance, pos.y) && !collision(pos.x +_velocitePacman + _distance, pos.y + _distance)){
    				_world.getPacman().setPosition(_positionRight);
    				_world.getPacman().setDirection(2);
            }
        }
		
        if(Gdx.input.isKeyJustPressed(Input.Keys.UP)){
			  Vector2 _positionUp = new Vector2(pos.x, (Math.round((pos.y + _velocitePacman)*100)/100f));
     	   if(!collision(pos.x, pos.y + _velocitePacman + _distance) && !collision(pos.x + _distance , pos.y + _distance + _velocitePacman)) {
     			   _world.getPacman().setPosition(_positionUp);
     			   _world.getPacman().setDirection(3);
     		   }
        }
        
        if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)){
        	Vector2 _positionDown = new Vector2(pos.x, (Math.round((pos.y - _velocitePacman)*100)/100f));
       	  	if(!collision(pos.x, (Math.round((pos.y - _velocitePacman)*100)/100f)) && !collision(pos.x + _distance , (Math.round((pos.y - _velocitePacman)*100)/100f))) {
    				_world.getPacman().setPosition(_positionDown);
    				_world.getPacman().setDirection(4);
        		}
        }
		

	}

	public void tirer() {
		Vector2 pos = _world.getFeu().getPosition();
		float _velociteFeu = _world.getFeu().getVelocite();
		float _distance = 1.0f - _velociteFeu;
		
		switch(_world.getFeu().getDirection()) {
			case 1 : 
				if(!collision((Math.round((pos.x - _velociteFeu)*100)/100f), pos.y) && !collision((Math.round((pos.x - _velociteFeu)*100)/100f) , pos.y + _distance )) {
					 Vector2 _positionLeft = new Vector2( (Math.round((pos.x - _velociteFeu)*100)/100f),pos.y);
					 _world.getFeu().setPosition(_positionLeft);
				
					 if(pos.x == _world.getFantomeBleu().getPosition().x || pos.y == _world.getFantomeBleu().getPosition().y) {
						 _world.getFantomeBleu().setVie(false);
						_world.getFantomeBleu().setPosition(new Vector2(15,16));
					}
					if(pos.x == _world.getFantomeRose().getPosition().x || 	pos.y == _world.getFantomeRose().getPosition().y) {
						_world.getFantomeRose().setVie(false);
						_world.getFantomeRose().setPosition(new Vector2(14,16));
					}
					if(pos.x == _world.getFantomeJaune().getPosition().x ||	pos.y == _world.getFantomeJaune().getPosition().y) {
						_world.getFantomeJaune().setVie(false);
						_world.getFantomeJaune().setPosition(new Vector2(13,16));
					}
					if(pos.x == _world.getFantomeRouge().getPosition().x ||	pos.y == _world.getFantomeRouge().getPosition().y){
						_world.getFantomeRouge().setVie(false);
						_world.getFantomeRouge().setPosition(new Vector2(12,16));
					}
				}else {
					_world.getFeu().setPosition(new Vector2(-1,-1));
					_world.getFeu().setDirection(0);
				}
				break;
			case 2 : 
				if(!collision(pos.x + _velociteFeu + _distance, pos.y) && !collision(pos.x + _velociteFeu + _distance, pos.y + _distance)){
					  Vector2 _positionRight = new Vector2( (Math.round((pos.x + _velociteFeu)*100)/100f),pos.y);
					_world.getFeu().setPosition(_positionRight);
					if(pos.x == _world.getFantomeBleu().getPosition().x || pos.y == _world.getFantomeBleu().getPosition().y) {	
						_world.getFantomeBleu().setVie(false);
						_world.getFantomeBleu().setPosition(new Vector2(15,16));
					}
					if(pos.x == _world.getFantomeRose().getPosition().x || pos.y == _world.getFantomeRose().getPosition().y) {
						_world.getFantomeRose().setVie(false);
						_world.getFantomeRose().setPosition(new Vector2(14,16));
					}
					if(pos.x == _world.getFantomeJaune().getPosition().x ||	pos.y == _world.getFantomeJaune().getPosition().y) {
						_world.getFantomeJaune().setVie(false);
						_world.getFantomeJaune().setPosition(new Vector2(13,16));
					}
					if(pos.x == _world.getFantomeRouge().getPosition().x ||	pos.y == _world.getFantomeRouge().getPosition().y){
						_world.getFantomeRouge().setVie(false);
						_world.getFantomeRouge().setPosition(new Vector2(12,16));
					}
				}
				else {
					_world.getFeu().setPosition(new Vector2(-1,-1));
					_world.getFeu().setDirection(0);
				}
				break; 
			case 3 : 
				if(!collision(pos.x, pos.y +  _velociteFeu + _distance) && !collision(pos.x + _distance , pos.y + _distance + _velociteFeu)) {
					  Vector2 _positionUp = new Vector2(pos.x, (Math.round((pos.y + _velociteFeu)*100)/100f));
					_world.getFeu().setPosition(_positionUp);
					if(pos.x == _world.getFantomeBleu().getPosition().x || 	pos.y == _world.getFantomeBleu().getPosition().y) {
						_world.getFantomeBleu().setVie(false);
						_world.getFantomeBleu().setPosition(new Vector2(15,16));
					}
					if(pos.x == _world.getFantomeRose().getPosition().x || 	pos.y == _world.getFantomeRose().getPosition().y) {
						_world.getFantomeRose().setVie(false);
						_world.getFantomeRose().setPosition(new Vector2(14,16));
					}
					if(pos.x == _world.getFantomeJaune().getPosition().x ||	pos.y == _world.getFantomeJaune().getPosition().y) {
						_world.getFantomeJaune().setVie(false);
						_world.getFantomeJaune().setPosition(new Vector2(13,16));
					}
					if(pos.x == _world.getFantomeRouge().getPosition().x ||	pos.y == _world.getFantomeRouge().getPosition().y){
						_world.getFantomeRouge().setVie(false);
						_world.getFantomeRouge().setPosition(new Vector2(12,16));
					}
				}else {
					_world.getFeu().setPosition(new Vector2(-1,-1));
					_world.getFeu().setDirection(0);
				}
				break; 
			case 4 :
				if(!collision(pos.x, (Math.round((pos.y - _velociteFeu)*100)/100f)) && !collision(pos.x + _distance , (Math.round((pos.y - _velociteFeu)*100)/100f))) {
					 Vector2 _positionDown = new Vector2(pos.x, (Math.round((pos.y - _velociteFeu)*100)/100f));
					_world.getFeu().setPosition(_positionDown);
					if(pos.x == _world.getFantomeBleu().getPosition().x || pos.y == _world.getFantomeBleu().getPosition().y) {
						_world.getFantomeBleu().setVie(false);
						_world.getFantomeBleu().setPosition(new Vector2(15,16));
					}
					if(pos.x == _world.getFantomeRose().getPosition().x || pos.y == _world.getFantomeRose().getPosition().y) {
						_world.getFantomeRose().setVie(false);
						_world.getFantomeRose().setPosition(new Vector2(14,16));
					}
					if(pos.x == _world.getFantomeJaune().getPosition().x || pos.y == _world.getFantomeJaune().getPosition().y) {
						_world.getFantomeJaune().setVie(false);
						_world.getFantomeJaune().setPosition(new Vector2(13,16));
					}
					if(pos.x == _world.getFantomeRouge().getPosition().x || pos.y == _world.getFantomeRouge().getPosition().y){
						_world.getFantomeRouge().setVie(false);
						_world.getFantomeRouge().setPosition(new Vector2(12,16));
					}
				}else {
					_world.getFeu().setPosition(new Vector2(-1,-1));
					_world.getFeu().setDirection(0);
				}
				break;
		}
		
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && _world.getFeu().getDirection() == 0){
			_world.getFeu().setDirection(_world.getPacman().getDirection());
			switch(_world.getPacman().getDirection()) {
			case 1 : 
				Vector2 positionLeft = new Vector2(_world.getPacman().getPosition().x-1,_world.getPacman().getPosition().y);
				_world.getFeu().setPosition(positionLeft);
				break;
			case 2 : 
				Vector2 positionRight = new Vector2(_world.getPacman().getPosition().x+1,_world.getPacman().getPosition().y);
				_world.getFeu().setPosition(positionRight);	
				break;
			case 3 : 
				Vector2 positionUp = new Vector2(_world.getPacman().getPosition().x,_world.getPacman().getPosition().y+1);
				_world.getFeu().setPosition(positionUp);
				break;
			case 4 : 
				Vector2 positionDown = new Vector2(_world.getPacman().getPosition().x,_world.getPacman().getPosition().y-1);
				_world.getFeu().setPosition(positionDown);
				break;
			}
			
		}
	}

	public void tirCanon() {
		Vector2 pos = _world.getBalle().getPosition();
		Vector2 pos2 = _world.getBalle2().getPosition();
		float _velociteBalle = _world.getBalle().getVelocite();
		float _distance = 1.0f - _velociteBalle;
			if(!collision((Math.round((pos2.x - _velociteBalle)*100)/100f), pos2.y) && !collision((Math.round((pos2.x - _velociteBalle)*100)/100f) , pos2.y + _distance )) {
				 Vector2 _positionLeft = new Vector2( (Math.round((pos2.x - _velociteBalle)*100)/100f),pos2.y);
				 _world.getBalle2().setPosition(_positionLeft);
			}
			else {
				_world.getBalle2().setPosition(new Vector2(25,16));
			}
			if(!collision(pos.x + _velociteBalle + _distance, pos.y) && !collision(pos.x + _velociteBalle + _distance, pos.y + _distance)){
				Vector2 _positionRight = new Vector2( (Math.round((pos.x + _velociteBalle)*100)/100f),pos.y);
				_world.getBalle().setPosition(_positionRight);
			}else {
				_world.getBalle().setPosition(new Vector2(3,16));
			}
	}
	
	public void setPpuX(float ppuX) {
		this._ppuX = ppuX;
	}

	public void setPpuY(float ppuY) {
		this._ppuY = ppuY;
	}
	
	public float getPpuy() {
		return this._ppuY;
	}
	
	public float getPpuX() {
		return this._ppuX;
	}
}
