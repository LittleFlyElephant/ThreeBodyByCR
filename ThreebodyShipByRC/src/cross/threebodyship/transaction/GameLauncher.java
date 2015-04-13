package cross.threebodyship.transaction;

import cross.threebodyship.userinterface.GameUI;
import cross.threeebodyship.model.Game;

public class GameLauncher {
	public static void main(String[] args){
		Game game = new Game();
		GameUI gameUI = new GameUI(game,600,500);
		game.addObserver(gameUI);
		Thread gameThread = new Thread(game);
		gameThread.start();
	}
}
