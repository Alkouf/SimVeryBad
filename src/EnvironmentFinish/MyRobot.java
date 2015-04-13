package EnvironmentFinish;

import simbad.sim.*;
import javax.vecmath.Vector3d;

public class MyRobot extends Agent {
   
	
	private Vector3d goal;
	
    public Vector3d getGoal() {
		return goal;
	}

	public void setGoal(Vector3d goal) {
		this.goal = goal;
	}

	public MyRobot(Vector3d position, String name) {
        super(position, name);
    }

}
