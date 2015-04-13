package cross.threeebodyship.model;

public class Ship {
	private Point centerPoint;
	private int size;
	private double speed;
	private double degreeToEast;
	private double degreeToStar;
	private double mass = 6000;
	private boolean isAlive = false;
	public boolean outOfBorder = false;
	
	public Ship(){
		centerPoint = new Point();
	}
	
	public Point getLocation(){
		return this.centerPoint;
	}
	
	public void setLocation(double x,double y){
		this.centerPoint.setPoint(x, y);
	}
	
	public int getSize(){
		return this.size;
	}
	
	public void setSize(int size){
		this.size = size;
	}
	
	public double getSpeed(){
		return this.speed;
	}
	
	public void setSpeed(double speed){
		this.speed = speed;
	}
	
	public void changeSpeed(double rate){
		this.speed *= rate;
	}
	
	public double getDegreeToEast(){
		return this.degreeToEast;
	}
	
	public void setDegreeToEast(double degree){
		this.degreeToEast = degree;
	}
	
	public double getDegreeToStar(){
		return this.degreeToStar;
	}
	
	public void setDegreeToStar(double degree){
		this.degreeToStar = degree;
	}
	
	public double getMass(){
		return this.mass;
	}
	
	public void setMass(double mass){
		this.mass = mass;
	}
	
	public boolean getState(){
		return this.isAlive;
	}
	
	public void setState(boolean state){
		this.isAlive = state;
	}
}
