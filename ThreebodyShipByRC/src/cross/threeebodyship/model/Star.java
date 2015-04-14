package cross.threeebodyship.model;

public class Star {
	public static double G = 0.0000006672;
	private double mass = 100000000;
	private int size;
	private Point centerPoint;
	private int gravityScope;
	
	public Star(){
		centerPoint = new Point();
	}
	
	public double getMass(){
		return this.mass;
	}
	
	public void setMass(double mass){
		this.mass = mass;
	}
	
	public int getSize(){
		return this.size;
	}
	
	public void setSize(int size){
		this.size = size;
	}
	
	public Point getLocation(){
		return this.centerPoint;
	}
	
	public void setLocation(double x,double y){
		this.centerPoint.setPoint(x, y);
	}
	
	public void setGravityScope(int scope){
		this.gravityScope = scope;
	}
	
	public int getGravityScope(){
		return this.gravityScope;
	}
}
