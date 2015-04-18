package EnvironmentFinish;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;

import  simbad.sim.*;

import javax.vecmath.Vector3d;
import javax.media.j3d.*;
import javax.vecmath.Matrix3d;
import javax.vecmath.Point2d;
import javax.vecmath.Point2i;
import javax.vecmath.Point3d;

import Data.ListData;

public class MyRobot extends Agent {

    final double  MAX_TRANSLATIONAL = 4;
    final double  MAX_ROTATIONAL = 4;
    final double ZERO = 0.01;


    Vector3d goal;
    Vector3d current_goal;
    int g; // this is what the robot is supposed to do - 1 = turn around - 2 = move to next block - 3 = compute the next step
    Point3d old_position;
    char map[][];
    double world_size;
    

    public void setMap(char map[][])
    {
    	this.map = map;
    	world_size = map.length;
    }
    
	public MyRobot (Vector3d position, String name, Vector3d goal) 
    {
        super(position,name);
        this.goal = goal;
    }
    
    public void initBehavior() 
    {
    	g = 3;
    }
    
    public void performBehavior() 
    {
        Vector3d lg;
        lg = getLocalCoords(goal); 
        double dist = Math.sqrt(lg.x * lg.x + lg.z * lg.z);
       if(g == 3)
       {
    	   computeNextGoalWithAStar();
    	   g = 1;
       }
        if(g == 1)
        {
	        if((getAngle()) >=  Math.PI/2)// && Math.toDegrees(getAngle()) < 90.5)
	        {
	        	System.out.println(Math.toDegrees(getAngle()));
	        	g = 2;
	        	//this.getCoords(old_position);
	        	rotate(0);
	        }
	        else
	        {
	        	rotate(Math.PI/4);
	        }
        }
        else if(g == 2)
        {
        	Point3d r = new Point3d();
        	this.getCoords(r);
        	if(this.getOdometer() >= 1.0)
        	{
        		g = 3;
        		this.setTranslationalVelocity(0);
        	}
        	else
        	{
        		this.setTranslationalVelocity(0.05);
        	}
        }
        
//        if (dist > 0.1)
//        {        
//        //    lg.x/=5;
//          //  lg.z/=5;       
//            //lg.x*=MAX_TRANSLATIONAL;
//            //lg.z*=MAX_ROTATIONAL;
//            
//            ActivateActuators(lg.x, lg.z);
//        }  
//        else
//        {
//            ActivateActuators(0,0);            
//        }
    }
    
    
    public void rotate(double a)
    {
    	this.setRotationalVelocity(a);
    }
    
    public void ActivateActuators(double x, double z)
    {
        if (x<0)
        {
            z *=2;
            if (Math.abs(z)<ZERO)                
                z=1;
            x*=-1;
        }
        this.setRotationalVelocity(z);
        this.setTranslationalVelocity(x);        
    }
    
    public Vector3d getLocalCoords(Vector3d p)
    {
        Vector3d a = new Vector3d();
        Point3d r = new Point3d();
        double th = getAngle();
        double x,y,z;
        getCoords(r);
        x = p.x - r.x;
        z = -p.z+ r.z;        
        a.x = (x*Math.cos(th) + z*Math.sin(th));
        a.z = (z*Math.cos(th) - x*Math.sin(th));
        a.y = (p.y);
        return a;
    }    
    
    public double getAngle()
    {
        double angle=0;
        double msin; 
        double mcos;              
        Transform3D m_Transform3D = new Transform3D();
        this.getRotationTransform(m_Transform3D);        
        Matrix3d m1 = new Matrix3d();
        m_Transform3D.get( m1 );                
        msin=m1.getElement( 2, 0 );
        mcos=m1.getElement( 0, 0 );        
        if (msin<0)
        {
            angle = Math.acos(mcos);
        }
        else            
        {
            if (mcos<0)
            {
                angle = 2*Math.PI-Math.acos(mcos);
            }
            else
            {            
                angle = -Math.asin(msin);
            }
        }
        while (angle<0)
            angle+=Math.PI*2;
        return angle;
    }    
    
    public void setCurrent_goal(Vector3d current_goal) {
		this.current_goal = current_goal;
	}
    
    public void moveNorth()
    {
    	
    }
    
    public void moveEast()
    {
    	
    }
    
    public void moveWest()
    {
    	
    }
    
    public void moveSouth()
    {
    	
    }
    

    public void computeNextGoalWithAStar()
    {
    	Comparator<ListData> comparator = new fValueComparator();
        PriorityQueue<ListData> openList = new PriorityQueue<ListData>(comparator);
    	ArrayList<ListData> closeList = new ArrayList<ListData>();
    	
    	
    	
    	
    	Point3d r = new Point3d();
    	this.getCoords(r);

    	r.x = (r.x - 0.5) + (world_size / 2);
    	r.z = -(r.z + 0.5) + (world_size / 2);
    	
    	openList.add(new ListData((int) Math.round(r.x), (int)Math.round(r.z)));

    	while(!openList.isEmpty())
    	{
    		System.out.println("While");
    		ListData q = openList.remove();
    		int x, y;
    		x = q.getPoint().x;
    		y = q.getPoint().y;
    		
    		if((y + 1) != map.length)
    		{
    			if(map[x][y + 1] == 'e' || map[x][y + 1] == 'R')
    			{
    				checkLists(x, y + 1, openList, closeList);	
    			}
    		}
    		if((y - 1) != - 1)
    		{
    			if(map[x][y - 1] == 'e' || map[x][y - 1] == 'R')
    			{
    				checkLists(x, y - 1, openList, closeList);	
    			}
    		}
    		if((x + 1) != map.length)
    		{
    			if(map[x + 1][y] == 'e' || map[x + 1][y] == 'R')
    			{
    				checkLists(x + 1, y, openList, closeList);	
    			}
    		}
    		if((x - 1) != -1)
    		{
    			if(map[x - 1][y] == 'e' || map[x - 1][y] == 'R')
    			{
    				checkLists(x - 1, y, openList, closeList);	
    			}
    		}
    			
    		closeList.add(q);
    	}
    	System.out.println("List size: " + closeList.size());
    	for(ListData d: closeList)
    	{
    		System.out.println(d.getX() + ", " + d.getY());
    	}
    	
    }
    
    public void checkLists(int x, int y, PriorityQueue<ListData> openList, ArrayList<ListData> closeList)    
    {
		ListData successor;
		Point2i g = new Point2i((int) Math.round((goal.x - 0.5) + (world_size / 2)),
    			(int)Math.round(-(goal.z + 0.5) + (world_size / 2)));
    	boolean valid = true;
		
		
    	successor = new ListData(x, y);
		if(x == g.x && (y) == g.y)
		{
			System.out.println("You have reached the goal. Find a way to break the execution.");
		}
		else
		{
			double f;
			f = Math.abs(x - g.x) + Math.abs((y) - g.y);
			successor.setF(f);
			
			for(ListData d: openList)
			{
				if((d.getX() == x) && (d.getY() == (y)))
				{
					if(d.getF() <= f)
					{
						valid = false;
					}
				}
			}
			
			if(valid)
			{
				for(ListData dt: closeList)
				{
					if((dt.getX() == x) && (dt.getY() == (y)))
					{
						if(dt.getF() <= f)
						{
							valid = false;
						}
					}
				}
			}
			if(valid)
			{
				openList.add(successor);
			}
			
		} // mexri edw i synartisi
    }
    
    public class fValueComparator implements Comparator<ListData>
    {
		@Override
		public int compare(ListData d0, ListData d1) {
			if(d0.getF() > d1.getF())
			{
				return 1;
			}
			else if(d0.getF() < d1.getF())
			{
				return -1;
			}
			else
			{
				return 0;
			}
		}
    }
    
    
}
