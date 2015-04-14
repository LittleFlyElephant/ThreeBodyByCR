package cross.threebodyship.transaction;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import cross.threeebodyship.model.Game;
import cross.threeebodyship.model.Ship;

public class GameController implements KeyListener{
	Game game;
	private int count = 0;
	
	public GameController(Game game) {
		this.game = game;
		// TODO Auto-generated constructor stub
	}
	@Override
	public void keyPressed(KeyEvent arg0) {
		int keycode = arg0.getKeyCode();
		
		//开始阶段用户设置相应参数
		if(game.isStarting)
			switch(keycode){
				case KeyEvent.VK_U:{
					game.ship.setDegreeToEast(
						game.ship.getDegreeToEast()+Math.toRadians(3));
					break;
				}
				case KeyEvent.VK_S:{
					game.ship.setSpeed(
						game.ship.getSpeed()+0.5);
					break;
				}
				case KeyEvent.VK_O:{
					game.isStarting = false;
					game.inGame = true;
					break;
				}
				case KeyEvent.VK_UP:{
					game.ship.setLocation(game.ship.getLocation().x, 
							game.ship.getLocation().y-3);
					break;
				}
				case KeyEvent.VK_DOWN:{
					game.ship.setLocation(game.ship.getLocation().x,
							game.ship.getLocation().y+3);
					break;
				}
		}
			
		//游戏阶段用户操作
		if(game.inGame)
			switch(keycode){
				case KeyEvent.VK_SPACE:{
					if(count == 1){
						this.game.ship.setState(true);
						count = 0;
					}
					else{
						count++;
						this.game.ship.setState(false);
					}
					break;
				}
				case KeyEvent.VK_UP:{
					Ship temp = this.game.ship;
					temp.setSpeed(this.game.speedChangeRate*temp.getSpeed());
					break;
				}
				case KeyEvent.VK_DOWN:{
					Ship temp = this.game.ship;
					temp.setSpeed(temp.getSpeed()/this.game.speedChangeRate);
					break;
				}
			}
		
		if(keycode == KeyEvent.VK_ENTER) this.game.reset();
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
