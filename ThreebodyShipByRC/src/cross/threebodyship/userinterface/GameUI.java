package cross.threebodyship.userinterface;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import cross.threebodyship.transaction.GameController;
import cross.threeebodyship.model.Game;
import cross.threeebodyship.model.Ship;
import cross.threeebodyship.model.Star;

public class GameUI extends JFrame implements Observer{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Canvas mainCanvas;
	GameController controller;
	Game game;
	JLabel speedLabel;
	JLabel degreeLabel;
	
	public GameUI(Game game,int width,int height){
		this.game = game;
		mainCanvas = new Canvas();
		controller = new GameController(this.game);
		
		JPanel text = new JPanel();
		JLabel test = new JLabel();
		speedLabel = new JLabel();
		degreeLabel = new JLabel();
		
		text.add(speedLabel);
		text.add(degreeLabel);
		
		this.add(text,BorderLayout.NORTH);
		this.add(mainCanvas,BorderLayout.CENTER);
		this.add(test,BorderLayout.SOUTH);
		test.setText("location:"+mainCanvas.getX()+" "+mainCanvas.getY());
		this.pack();
		this.setLocation(100,100);
		this.setSize(width, height);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		//mainGamePanel.addKeyListener(controller);
		//text.addKeyListener(controller);
		this.addKeyListener(controller);
	}
	
	public void repaintMain(){
		//mainCanvas.repaint();
		Graphics g = mainCanvas.getGraphics();
		g.setColor(Color.GRAY);
		g.fillRect(0,0,mainCanvas.getWidth(),mainCanvas.getHeight());
			
		//System.out.println(1);
		speedLabel.setText("speed:"+Double.toString(game.ship.getSpeed()));
		degreeLabel.setText("degreeToEast:"+Double.toString(Math.toDegrees(game.ship.getDegreeToEast())));
		
		paintStar(g,game.star);
		paintShip(g,game.ship);
		paintWinArea(g);
	}
		
	public void paintWinArea(Graphics g){
		g.setColor(Color.yellow);
		
		int height = mainCanvas.getHeight();
		int width = mainCanvas.getWidth();
		double r = height/4;
		int locationx = width + (int)r/2 - (int)r ;
		int locationy = (int)height/2 - (int)r ;
		
		g.fillOval(locationx, locationy, (int)r*2, (int)r*2);
	}
	
	public void paintStar(Graphics g, Star star){
		
		g.setColor(Color.pink);
			
		g.fillOval((int)(star.getLocation().x-star.getGravityScope()/2), 
				(int)(star.getLocation().y-star.getGravityScope()/2),
				star.getGravityScope(),
				star.getGravityScope());
			
		g.setColor(Color.BLUE);
			
		g.fillOval((int)(star.getLocation().x-star.getSize()/2),
				(int)(star.getLocation().y-star.getSize()/2),
				star.getSize(),
				star.getSize());
	}
		
	public void paintShip(Graphics g, Ship ship){
		
		if((ship.getLocation().x<0)||
				(ship.getLocation().x+ship.getSize()>mainCanvas.getWidth())||
				(ship.getLocation().y<0)||
				(ship.getLocation().y+ship.getSize()>mainCanvas.getHeight())
				) ship.outOfBorder = true;
		
		g.setColor(Color.red);
			
		g.fillOval((int)(ship.getLocation().x-ship.getSize()/2),
				(int)(ship.getLocation().y-ship.getSize()/2),
				ship.getSize(),
				ship.getSize());
	}
	
	public void update(Observable o, Object arg0) {
		repaintMain();
	}

}
