package EnvironmentFinish;

import input.Input;

import javax.vecmath.Color3f;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import simbad.gui.Simbad;
import simbad.sim.Arch;
import simbad.sim.Box;
import simbad.sim.EnvironmentDescription;
import simbad.sim.Wall;

public class Simulation {

    public static void main(String[] args) {

    	Input input = new Input();
    	
    	//char[][] map = input.readFileWithGraphical();
    	char[][] map = input.createRandomFile();
    	
    	double world_size = map.length;
    	
        EnvironmentDescription env = new EnvironmentDescription();
        env.setWorldSize(map.length); // ginetai na orisoume diaforetiko megethos pleurwn?
        System.out.println(map.length);
        MyRobot robot = null;
        double goal_x = 0, goal_y = 0, robot_x = 0, robot_y = 0;
		for(int i = 0; i < map.length; i++)
		{
			for(int j = 0; j < map[i].length; j++)
			{
				
				if(map[i][j] == 'X')
				{
					double x, y;
					y = j - (world_size / 2);
					x = i - (world_size / 2);
					System.out.println(y);
					Box b1 = new Box(new Vector3d(x + 0.5, 0, -y - 0.5), new Vector3f(1, 1, 1), env);
					b1.setColor(new Color3f(0.0f, 0.0f, 1.0f));
			        env.add(b1); 
				}
				if(map[i][j] == 'R')
				{
					robot_y = j - (world_size / 2);
					robot_x = i - (world_size / 2);
				}
				if(map[i][j] == 'G')
				{
					goal_y = j - (world_size / 2);
					goal_x = i - (world_size / 2);	
					Box b1 = new Box(new Vector3d(goal_x + 0.5, 1, -goal_y - 0.5), new Vector3f(0.5f, 0.5f, 0.5f), env);
					b1.setColor(new Color3f(0.0f, 1.0f, 1.0f));
			        env.add(b1); 
				}
				if(map[i][j] == 'D')
				{
					double x, y;
					y = j - (world_size / 2);
					x = i - (world_size / 2);
			        //MyRobot r =  new MyRobot(new Vector3d(x + 0.5, 0, -y - 0.5), "Evil Rabatah", null);
			        //r.setColor(new Color3f(1.0f, 0.0f, 0.0f));
					Box b1 = new Box(new Vector3d(x + 0.5, 0, -y - 0.5), new Vector3f(1, 1, 1), env);
					b1.setColor(new Color3f(0.0f, 1.0f, 1.0f));
			        env.add(b1);
				}
			}
			System.out.println();
		}
		//robot.setGoal(new Vector3d(goal_x + 0.5, 0, -goal_y - 0.5));
		robot =  new MyRobot(new Vector3d(robot_x + 0.5, 0, -robot_y - 0.5), "Mr Rubato", new Vector3d(goal_x + 0.5, 0, -goal_y - 0.5));
		robot.setMap(map);
		env.add(robot);
    
        

        Wall w1 = new Wall(new Vector3d(0.0, 0, world_size/2 + 0.15), map.length + 0.6f, 1, env);
        w1.setColor(new Color3f(1.0f, 1.0f, 0.0f));
        env.add(w1);
        w1 = new Wall(new Vector3d(0.0, 0, -(world_size/2 + 0.15)), map.length + 0.6f, 1, env);
        w1.setColor(new Color3f(1.0f, 1.0f, 0.0f));
        env.add(w1);
        w1 = new Wall(new Vector3d(-(world_size/2 + 0.15), 0, 0), map.length, 1, env);
        w1.setColor(new Color3f(1.0f, 1.0f, 0.0f));
        w1.rotate90(1);
        env.add(w1);
        w1 = new Wall(new Vector3d(world_size/2 + 0.15, 0, 0), map.length, 1, env);
        w1.setColor(new Color3f(1.0f, 1.0f, 0.0f));
        w1.rotate90(1);
        env.add(w1);
//        
//        Arch a1 = new Arch(new Vector3d(3, 0, 2), env);
//        env.add(a1);

        Simbad simulator = new Simbad(env, false);
    }

}
