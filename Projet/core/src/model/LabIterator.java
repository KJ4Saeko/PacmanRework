package model;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LabIterator implements Iterator<GameElement> {

	private Lab _lab;
	int _i, _j;

	public LabIterator(Lab maze) {
		this._lab = maze;
		_i = _j = 0;
	}

	@Override
	public boolean hasNext() {
		return (_i < this._lab.getHeight()) && (_j < this._lab.getWidth());
	}

	@Override
	public GameElement next() {
		if (!this.hasNext())
			throw new NoSuchElementException("No more game elements");
		GameElement gameElement;
		do {
			gameElement = this._lab.getGE(_i, _j);
			_j = (_j + 1) % this._lab.getWidth();
			if (_j == 0) {
				_i++;
			}
		} while (gameElement == null && this.hasNext());
		return gameElement;
	}

	@Override
	public void remove() {
	}
}