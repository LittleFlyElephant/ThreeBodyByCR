package cross.threeebodyship.model;

import java.util.Observable;

import javax.swing.JFrame;

public class Game extends Observable implements Runnable{
	private boolean isRunning = false;
	public boolean isStarting = false;
	public boolean inGame = false;
	private long refreshInterval = 100;
	public Ship ship;
	public Star star;
	public double speedChangeRate = 1.1;
	
	public Game(){
		reset();
	}
	
	public void update(){
		
		double distance = Math.sqrt((ship.getLocation().x-star.getLocation().x)*
				(ship.getLocation().x-star.getLocation().x)+
				(ship.getLocation().y-star.getLocation().y)*
				(ship.getLocation().y-star.getLocation().y)
				);
		//System.out.println("distance:"+distance);
		//如果进入引力区
		if(distance<star.getGravityScope()/2){
			double a = Star.G*star.getMass()/
					(star.getSize()*star.getSize()/4);
			
			double theta1 = Math.atan((double)(star.getLocation().y-
					ship.getLocation().y)/(star.getLocation().x-
					ship.getLocation().x));
			if(theta1<0) theta1 += Math.PI;
			//System.out.println("stary:"+star.getLocation().x);
			//System.out.println("shipy:"+ship.getLocation().x);
			//System.out.println("theta1:"+Math.toDegrees(theta1));
			ship.setDegreeToStar(theta1 - ship.getDegreeToEast());
			
			System.out.println("degreeToStar:"+Math.toDegrees(ship.getDegreeToStar()));
			
			double v1 = ship.getSpeed()*Math.cos(ship.getDegreeToStar());
			double v2 = ship.getSpeed()*Math.sin(ship.getDegreeToStar());
			
			v1 = v1 + a*this.getRI();
			
			double vNew = Math.sqrt(v1*v1+v2*v2);
			
			ship.setSpeed(vNew);
			
			double theta2 = Math.atan(v2/v1);
			if(theta2<0) theta2 += Math.PI;
			System.out.println("theta2:"+Math.toDegrees(theta2));
			double thetaNew = ship.getDegreeToStar() - theta2;
			
			ship.setDegreeToEast(thetaNew);
		}
		
		double nowX = ship.getLocation().x + (ship.getSpeed()*Math.cos(ship.getDegreeToEast()));
		double nowY = ship.getLocation().y + (ship.getSpeed()*Math.sin(ship.getDegreeToEast()));
		ship.setLocation(nowX, nowY);
		//System.out.println(ship.getLocation().x);
		
	}
	
	public void reset(){
		this.isRunning = true;
		this.isStarting = true;
		this.inGame = false;
		
		//初始化飞船
		ship = new Ship();
		ship.setDegreeToEast(0);
		ship.setDegreeToStar(0);
		ship.setLocation(120, 130);
		ship.setMass(1000);
		ship.setSize(5);
		ship.setSpeed(3);
		ship.setState(true);
		
		//初始化星球
		star = new Star();
		star.setLocation(200, 150);
		star.setMass(1000000);
		star.setSize(20);
		star.setGravityScope(100);
	}
	
	private void check(){
		//判断是否出界
		if(ship.outOfBorder){
			ship.setState(false);
			JFrame loser = new JFrame();
			loser.setVisible(true);
		}
		
		//判断是否到达胜利区域
		
		
	}
	
	public void setState(boolean isRunning){
		this.isRunning = isRunning;
	}
	
	public boolean getState(){
		return this.isRunning;
	}
	
	public void setRI(long interval){
		this.refreshInterval = interval;
	}
	
	public long getRI(){
		return this.refreshInterval;
	}
	
	@Override
	public void run() {
		//reset();
		
		while(this.getState()){
			try{
				Thread.sleep(this.getRI());
			}catch(Exception e){}
			
			if(this.isStarting){
				this.setChanged();
				this.notifyObservers();
			}
			
			if((this.inGame)&&(ship.getState())){
				update();
				check();
				this.setChanged();
				this.notifyObservers();
			}
		}
		
		this.setState(false);
	}

}
