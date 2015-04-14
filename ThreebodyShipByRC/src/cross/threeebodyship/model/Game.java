package cross.threeebodyship.model;

import java.awt.BorderLayout;
import java.util.Observable;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Game extends Observable implements Runnable{
	private boolean isRunning = false;
	public boolean isStarting = false;
	public boolean inGame = false;
	
	private long refreshInterval = 100;
	public Ship ship;
	public Star star;
	public double speedChangeRate = 1.1;
	public Point border;
	public double distance;
	
	public Game(int width,int height){
		border = new Point();
		this.border.x = width;
		this.border.y = height;
		reset();
	}
	
	//飞行轨迹计算
	public void update(){
		double nowX = ship.getLocation().x;
		double nowY = ship.getLocation().y;
		double vx = ship.getSpeed()*Math.cos(ship.getDegreeToEast());
		double vy = ship.getSpeed()*Math.sin(ship.getDegreeToEast());
		double t = this.refreshInterval/100;
		
		distance = Math.sqrt((ship.getLocation().x-star.getLocation().x)*
				(ship.getLocation().x-star.getLocation().x)+
				(ship.getLocation().y-star.getLocation().y)*
				(ship.getLocation().y-star.getLocation().y)
				);
		//System.out.println("distance:"+distance);
		//如果进入引力区
		if(distance<star.getGravityScope()/2){
			double a = Star.G*star.getMass()/
					(star.getSize()*star.getSize()/4);
			
			//System.out.println("a:"+a);
			/*bug 代码
			double theta1 = Math.atan((double)(star.getLocation().y-
					ship.getLocation().y)/(star.getLocation().x-
					ship.getLocation().x));
			if((ship.getLocation().x<star.getLocation().x)&&
					ship.getLocation().y>star.getLocation().y) theta1 += 2*Math.PI;
			else{
				if(ship.getLocation().x>star.getLocation().x)
					theta1 += Math.PI;
			}
			
			double theta3 = theta1 - ship.getDegreeToEast();
			if(theta3<0) theta3 = -theta3;
			else if(theta3>Math.PI) theta3 = Math.PI*2 - theta3;
			//System.out.println("stary:"+star.getLocation().x);
			//System.out.println("shipy:"+ship.getLocation().x);
			ship.setDegreeToStar(theta3);
			
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
			*/
			
			//计算星球飞船连线与正东的夹角
			double theta1 = Math.atan((double)(star.getLocation().y-
					ship.getLocation().y)/(star.getLocation().x-
					ship.getLocation().x));
			if((ship.getLocation().x<star.getLocation().x)&&
					ship.getLocation().y>star.getLocation().y) theta1 += 2*Math.PI;
			else{
				if(ship.getLocation().x>star.getLocation().x)
					theta1 += Math.PI;
			}
			
			//计算速度，加速度，位移
			double ax = a*Math.cos(theta1);
			double ay = a*Math.sin(theta1);
			vx += ax*t;
			vy += ay*t;
			//System.out.println("ax:"+ax);
			//System.out.println("ay:"+ay);
			double sx = vx*t + (ax*t*t)/2;
			double sy = vy*t + (ay*t*t)/2;
			
			nowX += sx;
			nowY += sy;
			
			double vNew = Math.sqrt(vx*vx+vy*vy);
			
			ship.setSpeed(vNew);
			
			double theta2 = Math.atan(vy/vx);
			
			//修改theta2
			if(ship.getDegreeToEast()<Math.PI/2){
				if(theta2<0) theta2+=Math.PI*2;
				ship.setDegreeToEast(theta2);
			}
			else if(ship.getDegreeToEast()<Math.PI){
				if(vx<0) theta2+=Math.PI;
				ship.setDegreeToEast(theta2);
			}
			else if(ship.getDegreeToEast()<Math.PI*3/2){
				if(vx>0) theta2+=Math.PI*2;
				else theta2+=Math.PI;
				ship.setDegreeToEast(theta2);
			}
			else {
				if(theta2>0){
					if(vx<0) theta2+=Math.PI; 
				}
				else theta2+=Math.PI*2;
				ship.setDegreeToEast(theta2);
			}
			
			//System.out.println("theta1:"+Math.toDegrees(theta1));
			//System.out.println("theta2:"+Math.toDegrees(theta2));
			
		}else{
			nowX += vx*t;
			nowY += vy*t;
		}
		ship.setLocation(nowX, nowY);
		//System.out.println(ship.getLocation().x);
		
	}
	
	//重置参数
	public void reset(){
		this.isRunning = true;
		this.isStarting = true;
		this.inGame = false;
		
		//初始化飞船
		ship = new Ship();
		ship.setDegreeToEast(0);
		ship.setDegreeToStar(0);
		ship.setLocation(20, 245);
		ship.setMass(1000);
		ship.setSize(10);
		ship.setSpeed(3);
		ship.setState(true);
		ship.outOfBorder = false;
		
		//初始化星球
		star = new Star();
		star.setLocation(300, 250);
		star.setMass(40000000);
		star.setSize(50);
		star.setGravityScope(160);
	}
	
	//结束判定
	private void check(){
		//判断是否出界
		JFrame result = new JFrame();
		JLabel state = new JLabel();
		Boolean gameEnd = false;
		
		if((ship.getLocation().x<0)||
				(ship.getLocation().x+ship.getSize()>border.x)||
				(ship.getLocation().y<0)||
				(ship.getLocation().y+ship.getSize()>border.y)
				) ship.outOfBorder = true;
		
		if(ship.outOfBorder){
			ship.setState(false);
			state.setText("you lose!");
			gameEnd = true;
		}	
		
		if(checkWin()) {
			ship.setState(false);
			state.setText("you win!");
			gameEnd = true;
		}
		
		if(distance<star.getSize()/2){
			ship.setState(false);
			state.setText("you lose!");
			gameEnd = true;
		}
		
		//过关或出界或被星球吸引，则游戏结束
		if(gameEnd){
			result.add(state,BorderLayout.CENTER);
			result.setVisible(true);
			result.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			result.setLocationRelativeTo(null);
			result.pack();
		}
		
	}
	
	//获胜判定
	public boolean checkWin(){
		double winX = border.x + border.y/8;
		double winY = border.y/2;
		double winR = border.y/4-10;
		
		double dis = Math.sqrt((ship.getLocation().x-winX)*(ship.getLocation().x-winX)
				+(ship.getLocation().y-winY)*(ship.getLocation().y-winY));
		System.out.println("dis:"+dis);
		if(dis<winR) return true;
		else return false;
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
	
	//主要过程
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
