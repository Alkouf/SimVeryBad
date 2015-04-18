package Data;

import javax.vecmath.Point2i;


public class ListData {

	private Point2i point;
	private double f;
	
	public ListData(int x, int y)
	{
		point = new Point2i(x, y);
		f = 0.0;
	}
	
	public Point2i getPoint() {
		return point;
	}
	
	public void setPoint(int x, int y) {
		//point = new Point2i(x, y);
	}
	
	public int getX()
	{
		return point.x; 
	}
	
	public int getY()
	{
		return point.y;
	}
	
	public double getF() {
		return f;
	}
	
	public void setF(double f) {
		this.f = f;
	}
	
	
}
