package EnvironmentFinish;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;

import simbad.sim.*;

import javax.vecmath.Vector3d;
import javax.media.j3d.*;
import javax.vecmath.Matrix3d;
import javax.vecmath.Point2d;
import javax.vecmath.Point2i;
import javax.vecmath.Point3d;

import Data.ListData;

public class MyRobot extends Agent {

	final double MAX_TRANSLATIONAL = 4;
	final double MAX_ROTATIONAL = 4;
	final double ZERO = 0.01;

	Vector3d goal;
	Vector3d current_goal;
	int g; // this is what the robot is supposed to do - 1 = turn around - 2 =
			// move to next block - 3 = compute the next step
	Point3d old_position;
	char map[][];
	double world_size;
	private ArrayList<ListData> closeList;
	private double angle = Math.PI / 2; // how much the robot must to rotate
	private double rotational_velocity = Math.PI / 16; // the speed to rotate the robot
	private double steps = 0.0; // how many blocks the robot moved
	private ArrayList<ListData> path;
	private double start_angle;
	private double rotation;
	private double rotated;
	private double previous_rotation;

	public void setMap(char map[][]) {
		this.map = map;
		world_size = map.length;
	}

	public MyRobot(Vector3d position, String name, Vector3d goal) {
		super(position, name);
		this.goal = goal;
	}

	public void initBehavior() {
		
		computeNextGoalWithAStar();

		findPath();

		Collections.reverse(path);
		previous_rotation = this.getAngle();
		start_angle= this.getAngle();
		rotated = 0;
		g = 3;
	}

	public void performBehavior() {
		Vector3d lg;
		lg = getLocalCoords(goal);
		double dist = Math.sqrt(lg.x * lg.x + lg.z * lg.z);

		if (g == 1) {
			//System.out.println("Rotation: " + Math.toDegrees(rotation) + " - Rotated: " + Math.toDegrees(rotated));

			//if(rotational_velocity > 0)
			//{	
				if ((round(getAngle(), 1)) == (round(angle, 1)) )// && Math.toDegrees(getAngle()) < 90.5)
				//if(rotated >= rotation)
				{
					//rotated = 
					System.out.print("Rotational Velocity: " + rotational_velocity);
					System.out.println("No round Equal? = getAngle: " + getAngle() + ", angle: " + angle);
					System.out.println("Equal? = getAngle: " + round(getAngle(), 1) + ", angle: " + (round(angle, 1)));
					rotated = 0;
					g = 2;
					
					// this.getCoords(old_position);
					rotate(0);
				} else {
					rotate(rotational_velocity);
					//System.out.println("previous rotation: " + previous_rotation + " current angle: " +  this.getAngle());
					System.out.println("The angle every time: " + getAngle());
					//rotated = Math.abs(Math.toDegrees(this.getAngle()) - Math.toDegrees(previous_rotation));
					//System.out.println("How much it rotated: " + Math.toDegrees(rotated));
					previous_rotation = this.getAngle();
				}
			//}
//			else
//			{
//				if ((round(getAngle(), 1)) <= (round(angle, 1)))// && Math.toDegrees(getAngle()) < 90.5)
//				{
//					System.out.print("Rotational Velocity: " + rotational_velocity );
//					System.out.println(", angle: " + Math.toDegrees(getAngle()));
//					g = 2;
//					// this.getCoords(old_position);
//					rotate(0);
//				} else {
//					rotate(rotational_velocity);
//				}
//			}
		} else if (g == 2) {
			Point3d r = new Point3d();
			this.getCoords(r);
			
			if (this.getOdometer() / steps >= 1.0) {
				System.out.println("Odometer = " + this.getOdometer() + " steps = " + steps + " Division = " + this.getOdometer() / steps);

				g = 3;
				this.setTranslationalVelocity(0);
			} else {
				this.setTranslationalVelocity(0.05);
			}
		} else if (g == 3) {

			g = 1;
			computeAngle();

		}

	}

	
	
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
	
	
	public void computeAngle()
	{
		
		steps ++;

		if(steps >= path.size())
		{
			g = 4;
			//this.setTranslationalVelocity(0);
		}
		else
		{
			
			
			System.out.println("--------------------------------------");
			//testing the new angle

			Point3d r = new Point3d();
			this.getCoords(r);

			ListData ken_block = path.get((int)steps);

			Point3d k = new Point3d(ken_block.getX() - (world_size/2) + 0.5, 0, -(ken_block.getY() - (world_size/2))  - 0.5);
			
			
			//x_dif = 
			// h gwnia pou theloume = atan (y' - y / x' - x)
			angle = -Math.atan2((k.z - r.z), (k.x - r.x)); //+ Math.PI;

			if(angle < 0)
			{
				angle += 2 * Math.PI;
			}
			System.out.println("The new angle: " + Math.toDegrees(angle));
			
			double currentAngle = this.getAngle();
			
//			if(Math.abs(angle - currentAngle) > Math.PI)
//			{
//				if(angle < currentAngle)
//				{
//					angle += 2 * Math.PI;
//				}
//				else
//				{
//					currentAngle += 2 * Math.PI;
//				}
//			}
//			System.out.println("Angle for the aliex: " + angle + " and the current: " + currentAngle);
//			if(angle - currentAngle < 0)
//			{
//				rotational_velocity = -Math.abs(rotational_velocity);
//			}
//			else if(angle - currentAngle > 0)
//			{
//				rotational_velocity = Math.abs(rotational_velocity);
//			}
//			else
//			{
//				g = 2;
//			}
			System.out.println("Angle for the aliex: " + Math.toDegrees(angle) + " and the current: " + Math.toDegrees(currentAngle));
			double dif1 = angle - currentAngle;
			double dif2 = (2 * Math.PI) - Math.abs(dif1);
			rotational_velocity = Math.abs(rotational_velocity);
			if(dif1 > 0)
			{
				if(Math.abs(dif1) < Math.abs(dif2))
				{
					rotational_velocity = Math.abs(rotational_velocity);
					rotation = Math.abs(dif1);
				}
				else if(Math.abs(dif1) > Math.abs(dif2))
				{
					rotational_velocity = -Math.abs(rotational_velocity);
					rotation = Math.abs(dif2);
				}
				else
				{
					rotation = Math.PI;
					System.out.println("Yahoo!");
				}
			}
			else if(dif1 < 0)
			{
				if(Math.abs(dif1) < Math.abs(dif2))
				{
					rotational_velocity = -Math.abs(rotational_velocity);
					rotation = Math.abs(dif1);
				}
				else if(Math.abs(dif1) > Math.abs(dif2))
				{
					rotational_velocity = Math.abs(rotational_velocity);
					rotation = Math.abs(dif2);
				}
				else
				{
					rotation = Math.PI;
					System.out.println("Yahoo!");
				}
			}

			System.out.println("Real rotation : " + Math.toDegrees(rotation));
			start_angle = currentAngle;

			System.out.println("Robot coords: " + r.toString() + ", " + k.toString());
			
			// h metatropi: (x + 0.5, 0, -y - 0.5)
			
			
			
			//end test
			
			
			
//			System.out.println("Print me the steps plis: " + steps);
//			ListData next_block = path.get((int)steps);
//			ListData current_block = path.get(((int)steps) - 1);
//			
//			if(next_block.getX() < current_block.getX())
//			{
//				angle = Math.PI;
//			}
//			else if(next_block.getX() > current_block.getX())
//			{
//				angle = 0.0;
//			}
//			else if(next_block.getY() < current_block.getY())
//			{
//				angle = 3 * (Math.PI / 2);
//			}
//			else
//			{
//				angle = Math.PI / 2;
//			}
//			
//			if(angle - this.getAngle() < 0)
//			{
//				rotational_velocity = -Math.abs(rotational_velocity);
//			}
//			else if(angle - this.getAngle() > 0)
//			{
//				rotational_velocity = Math.abs(rotational_velocity);
//			}
//			else
//			{
//				g = 2;
//			}
		
		}
		//System.out.println("The angle: " + Math.toDegrees(angle));

		
	}
	
	
	

	
	
	public void rotate(double a) {
		this.setRotationalVelocity(a);
	}

	public void ActivateActuators(double x, double z) {
		if (x < 0) {
			z *= 2;
			if (Math.abs(z) < ZERO)
				z = 1;
			x *= -1;
		}
		this.setRotationalVelocity(z);
		this.setTranslationalVelocity(x);
	}

	public Vector3d getLocalCoords(Vector3d p) {
		Vector3d a = new Vector3d();
		Point3d r = new Point3d();
		double th = getAngle();
		double x, y, z;
		getCoords(r);
		x = p.x - r.x;
		z = -p.z + r.z;
		a.x = (x * Math.cos(th) + z * Math.sin(th));
		a.z = (z * Math.cos(th) - x * Math.sin(th));
		a.y = (p.y);
		return a;
	}

	public double getAngle() {
		double angle = 0;
		double msin;
		double mcos;
		Transform3D m_Transform3D = new Transform3D();
		this.getRotationTransform(m_Transform3D);
		Matrix3d m1 = new Matrix3d();
		m_Transform3D.get(m1);
		msin = m1.getElement(2, 0);
		mcos = m1.getElement(0, 0);
		if (msin < 0) {
			angle = Math.acos(mcos);
		} else {
			if (mcos < 0) {
				angle = 2 * Math.PI - Math.acos(mcos);
			} else {
				angle = -Math.asin(msin);
			}
		}
		while (angle < 0)
			angle += Math.PI * 2;
		return angle;
	}

	public void setCurrent_goal(Vector3d current_goal) {
		this.current_goal = current_goal;
	}


	public void computeNextGoalWithAStar() {
		Comparator<ListData> comparator = new fValueComparator();
		PriorityQueue<ListData> openList = new PriorityQueue<ListData>(
				comparator);
		closeList = new ArrayList<ListData>();

		Point3d r = new Point3d();
		this.getCoords(r); 

		r.x = (r.x - 0.5) + (world_size / 2);
		r.z = -(r.z + 0.5) + (world_size / 2);

		openList.add(new ListData((int) Math.round(r.x), (int) Math.round(r.z),
				null));
		int counter = 1;
		while (!openList.isEmpty()) {
			System.out.println("Run " + counter + ": ");
			counter++;
			ListData q = openList.remove();
			int x, y;
			x = q.getPoint().x;
			y = q.getPoint().y;

			if ((y + 1) != map.length) {
				if (map[x][y + 1] == 'e' || map[x][y + 1] == 'R'
						|| map[x][y + 1] == 'G') {
					if (checkLists(x, y + 1, openList, q, (int)r.x, (int)r.z)) {
						closeList.add(q);
						closeList.add(new ListData(x, y + 1, q));
						break;
					}
				}
			}
			if ((y - 1) != -1) {
				if (map[x][y - 1] == 'e' || map[x][y - 1] == 'R'
						|| map[x][y - 1] == 'G') {
					if (checkLists(x, y - 1, openList, q, (int)r.x, (int)r.z)) {
						closeList.add(q);
						closeList.add(new ListData(x, y - 1, q));
						break;
					}
				}
			}
			if ((x + 1) != map.length) {
				if (map[x + 1][y] == 'e' || map[x + 1][y] == 'R'
						|| map[x + 1][y] == 'G') {
					if (checkLists(x + 1, y, openList, q, (int)r.x, (int)r.z)) {
						closeList.add(q);
						closeList.add(new ListData(x + 1, y, q));
						break;
					}
				}
			}
			if ((x - 1) != -1) {
				if (map[x - 1][y] == 'e' || map[x - 1][y] == 'R'
						|| map[x - 1][y] == 'G') {
					if (checkLists(x - 1, y, openList, q, (int)r.x, (int)r.z)) {
						closeList.add(q);
						closeList.add(new ListData(x - 1, y, q));
						break;
					}
				}
			}
			closeList.add(q);
			System.out.println("Close List: ");
			for (ListData d : closeList) {
				System.out.println(d.getX() + ", " + d.getY());
			}
			System.out.println("Open List: ");
			for (ListData d : openList) {
				System.out.println(d.getX() + ", " + d.getY());
			}
		}

	}

	public boolean checkLists(int x, int y, PriorityQueue<ListData> openList, ListData parent, int start_x, int start_y) {

		ListData successor;
		Point2i g = new Point2i((int) Math.round((goal.x - 0.5)
				+ (world_size / 2)), (int) Math.round(-(goal.z + 0.5)
				+ (world_size / 2)));
		boolean valid = true;

		successor = new ListData(x, y, parent);
		if (x == g.x && (y) == g.y) {
			System.out
					.println("You have reached the goal. Find a way to break the execution.");
			return true;
		} else {
			double f;
			f = Math.abs(x - g.x) + Math.abs((y) - g.y);
			
			//find the number of parents - distance that the robot did
			ListData it = successor;
			int count = 0;
			while(it.getParent() != null)
			{
				count++;
				it = it.getParent();
			}
			
			f += count;
			successor.setF(f);

			for (ListData d : openList) {
				if ((d.getX() == x) && (d.getY() == (y))) {
					if (d.getF() <= f) {
						valid = false;
					}
				}
			}

			if (valid) {
				for (ListData dt : closeList) {
					if ((dt.getX() == x) && (dt.getY() == (y))) {
						if (dt.getF() <= f) {
							valid = false;
						}
					}
				}
			}
			if (valid) {
				openList.add(successor);
			}

		} // mexri edw i synartisi
		return false;
	}

	public void findPath() {
		path = new ArrayList<ListData>();
		ListData prev;
		path.add(closeList.get(closeList.size() - 1));

		prev = closeList.get(closeList.size() - 1);

		for (int i = closeList.size() - 2; i >= 0; i--) {
			System.out.println(i);
			ListData dt = closeList.get(i);
			if (dt.compareData(prev)) {
				path.add(dt);
				prev = closeList.get(i);
			}

		}
		for (int i = path.size() - 1; i >= 0; i--) {
			System.out.println(path.get(i).getX() + ", " + path.get(i).getY());
		}
	}

	public class fValueComparator implements Comparator<ListData> {
		@Override
		public int compare(ListData d0, ListData d1) {
			if (d0.getF() > d1.getF()) {
				return 1;
			} else if (d0.getF() < d1.getF()) {
				return -1;
			} else {
				return 0;
			}
		}
	}

}
