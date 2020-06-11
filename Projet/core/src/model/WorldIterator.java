package model;

import java.util.Iterator;

public class WorldIterator implements Iterator<GameElement> {
	private World _world;
	private Iterator<GameElement> _mazeIterator;
	int _i;

	public WorldIterator(World world) {
		this._world = world;
		this._mazeIterator = this._world.getLab().iterator();
		this._i = 0; 
	}

	@Override
	public boolean hasNext() {
		return (this._i < 8);
	}

	@Override
	public GameElement next() {
		if(_i == 0) {
			if (!this._mazeIterator.hasNext()) {
				_i = 1; 
			}
		}
		else {
			_i++;
		}

		if(_i == 0) {
			return this._mazeIterator.next();
		}
		else if(_i == 1){
			return this._world.getPacman();
		}
		else if(_i == 2){
			return this._world.getFantomeRouge();
		}
		else if(_i == 3) {
			return this._world.getFantomeRose();
		}
		else if(_i == 4) {
			return this._world.getFantomeBleu();
		}
		else if(_i == 5){
			return this._world.getFantomeJaune();
		}
		else if(_i == 6) {
			return this._world.getBalle();
		}
		else if(_i == 7) {
			return this._world.getBalle2();
		}
		else {
			return this._world.getFeu();
		}
	}

	@Override
	public void remove() {
	}
}
