package input;
import java.awt.Color;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Random;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;



public class Input {

	
	private char[][] map;
	
	public char[][] readFileWithGraphical()
	{
//		JFileChooser chooser = new JFileChooser();
		String path = "";
//		// to choose with file chooser
//		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
//		int option = chooser.showOpenDialog(chooser); // parentComponent must a component like JFrame, JDialog...
//		if (option == JFileChooser.APPROVE_OPTION) 
//		{
//		   File selectedFile = chooser.getSelectedFile();
//		   path = selectedFile.getAbsolutePath();
//		   System.out.println(path);
//		}else {
//			System.exit(1);
//		}
		
		path = "C:/Users/Andreas Sitaras/Desktop/robot_input.txt";
		FileInputStream fstream = null;
		try {
			fstream = new FileInputStream(path);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		@SuppressWarnings("resource")
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
		
		String line;
		
		
		int[] dimensions = new int[2];
		//String[][] map;
		try
		{
			
			line = br.readLine();
			String[] temp = line.split(" ");
			
			for(int i = 0; i < 2; i++)
			{
				dimensions[i] = Integer.parseInt(temp[i]);
			}
			//map = new String[dimensions[0]][dimensions[1]];
			map = new char[dimensions[0]][dimensions[1]];
			
			for(int i = 0; i < dimensions[0]; i++)
			{
				line = br.readLine();
				
				temp = line.split("");
				for(int j = 0; j < dimensions[1]; j++)
				{
					//map[i][j] = temp[j + 1];
					map[i][j] = line.charAt(j);
				}
			}
			
			for(int i = 0; i < dimensions[0]; i++)
			{
				for(int j = 0; j < dimensions[1]; j++)
				{
					System.out.print(map[i][j]);
				}
				System.out.println();
			}
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return map;
		
	}
	
	
	
	private int[] input;
    private JSpinner[] inputField;
	
	private JPanel getPanel()
    {
        JPanel basePanel = new JPanel();
        //basePanel.setLayout(new BorderLayout(5, 5));
        basePanel.setOpaque(true);
        basePanel.setBackground(Color.BLUE.darker());

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(3, 2, 5, 5));
        centerPanel.setBorder(
            BorderFactory.createEmptyBorder(5, 5, 5, 5));
        centerPanel.setOpaque(true);
        centerPanel.setBackground(Color.WHITE);

        JLabel mLabel1 = new JLabel("Enter X size : ");
        JLabel mLabel2 = new JLabel("Enter Y size : ");
        JLabel mLabel3 = new JLabel("Enter Space Propability : ");


        input = new int[3];
        inputField = new JSpinner[3];
        inputField[0] = new JSpinner();
        inputField[1] = new JSpinner();
        inputField[2] = new JSpinner();
        for(int i = 0; i < 3; i++)
        {
            inputField[i].setModel(new SpinnerNumberModel(10, 0, 100, 1));
        }
        inputField[2].setValue(50);
        centerPanel.add(mLabel1);
        centerPanel.add(inputField[0]);
        centerPanel.add(mLabel2);
        centerPanel.add(inputField[1]);
        centerPanel.add(mLabel3);
        centerPanel.add(inputField[2]);

        basePanel.add(centerPanel);

        return basePanel;
    }
	
	
	
	public char[][] createRandomFile()
	{
		try {
			
			int selection = JOptionPane.showConfirmDialog(
	                null, getPanel(), "Input Form : "
	                                , JOptionPane.OK_CANCEL_OPTION
	                                , JOptionPane.PLAIN_MESSAGE);

	        if (selection == JOptionPane.OK_OPTION) 
	        {
	            for ( int i = 0; i < 3; i++)
	            {
	               input[i] = (int)inputField[i].getValue();
	               System.out.println(input[i]);           
	            }
	            
	        }
	        else
	        {
	            System.exit(1);
	        }

			
			//System.out.println(int1 + ", " + int2 + ", " + int3);
			PrintWriter writer = new PrintWriter("C:/Users/Andreas "
					+ "Sitaras/Documents/ranbot_input.txt", "UTF-8");
			
			Scanner scan = new Scanner(System.in);
			int[] dimensions = new int[2];
			
			/*
			for(int i = 0; i < 2; i++)
			{
				System.out.println("Give value for dimension " + (i + 1) + ": ");
				dimensions[i] = scan.nextInt();
			}
			*/
			
			dimensions[0] = input[0];
			dimensions[1] = input[1];
			
			map = new char[dimensions[0]][dimensions[1]];
			
			Random rand = new Random();
			String charSet = "eXee";
			for(int i = 0; i < dimensions[0]; i++)
			{
				for(int j = 0; j < dimensions[1]; j++)
				{
					int randomChar = rand.nextInt(charSet.length());
					map[i][j] = charSet.charAt(randomChar);
				}
			}
			
			charSet = "DGR";
			for(int i = 0; i < 3; i++)
			{
				int x = rand.nextInt(dimensions[0]);
				int y = rand.nextInt(dimensions[1]);
				
				System.out.println(x + ", " + y);
				map[x][y] = charSet.charAt(i);
			}

			
			
			scan.close();
			writer.close();
			
			for(int i = 0; i < dimensions[0]; i++)
			{
				for(int j = 0; j < dimensions[1]; j++)
				{
					System.out.print(map[i][j]);
				}
				System.out.println();
			}
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
			return map;
		
	}
	
	
	
}
