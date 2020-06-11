package packageGame;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

import screens.GameScreen;
import screens.LaunchScreen;
import screens.EndScreen;
import screens.VictoryScreen;

public class PacmanGame extends Game {
	private int state = 0;
    private GameScreen _mainScreen;
    private EndScreen _endScreen;
    private VictoryScreen _victoireScreen;
    private LaunchScreen _launchScreen;

	@Override
	public void create () {
		_launchScreen = new LaunchScreen();
		_mainScreen = new GameScreen();
		_endScreen = new EndScreen(this);
		_victoireScreen = new VictoryScreen(this);
	}

	@Override
	public void render() {
		super.render();
		if (state == 0 ) {
			this.setScreen(_launchScreen);
		} 
		else if(state == 1) {
			this.setScreen(_mainScreen);
			if(_mainScreen.getWorld().getPacman().getVies() < -1){
				_endScreen.setScore(_mainScreen.getWorld().getScore());
				this.setScreen(_endScreen);
			}
			else if(_mainScreen.getWorld().getNbGomme() == 0){
				_victoireScreen.setScore(_mainScreen.getWorld().getScore());
				this.setScreen(_victoireScreen);
			}
		}
	}
	
	public void setState(int s) {
		this.state = s;
	}
	
	public void restart() {
		_mainScreen.init();
		setScreen(_mainScreen);
	}
}
