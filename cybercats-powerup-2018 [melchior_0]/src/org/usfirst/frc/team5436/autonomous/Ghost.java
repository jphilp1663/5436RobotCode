package org.usfirst.frc.team5436.autonomous;

import org.usfirst.frc.team5436.utilities.Functions;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import org.usfirst.frc.team5436.limbs.Drive;
import org.usfirst.frc.team5436.utilities.DriveProcessing;
import org.usfirst.frc.team5436.utilities.Controllers;

//TODO: Rework for mecanum and swerve drives
//TODO: Clean in respect to motor arrangement

public class Ghost {
	public static double[] leftDrive = {};
	public static double[] rightDrive = {};
	public static double[] lastLeftDrive = {};
	public static double[] lastRightDrive = {};
	
	public static double[] leftDriveLeft = {};
	public static double[] rightDriveLeft = {};
	public static double[] leftDriveCenter = {};
	public static double[] rightDriveCenter = {};
	public static double[] leftDriveRight = {};
	public static double[] rightDriveRight = {};
	
	public static String leftDriveLeftFile;
	public static String rightDriveLeftFile;
	public static String leftDriveCenterFile;
	public static String rightDriveCenterFile;
	public static String leftDriveRightFile;
	public static String rightDriveRightFile;
	
	public static double interval = 0;
	
	public static int num = 0;
	
	public static void record(double[] leftDrive, double[] rightDrive) {
		lastLeftDrive = leftDrive;
		lastRightDrive = rightDrive;
		
		Functions.steadyIncrement(30);
		
		leftDrive = new double[leftDrive.length + 1];
		rightDrive = new double[leftDrive.length + 1];
		
		for (int n = 0; n < lastLeftDrive.length + 1 && n < lastRightDrive.length; n++) {
			if (n < lastLeftDrive.length + 1 && n < lastRightDrive.length) {
			leftDrive[n] = lastLeftDrive[n]; 
			rightDrive[n] = lastRightDrive[n];
			}
			else if (n == lastLeftDrive.length + 1 && n == lastRightDrive.length) {
			leftDrive[lastLeftDrive.length + 1] = Controllers.primary.getY()+0.9*DriveProcessing.deaden(Controllers.primary.getX()); 
			rightDrive[lastRightDrive.length + 1] = Controllers.primary.getY()-0.9*DriveProcessing.deaden(Controllers.primary.getX());
			}
		}
	}
	
	public static void runMacro( double[] leftDrive, double[] rightDrive) {
		Functions.steadyIncrement(30);
		
		interval = Functions.timeElapsed()/0.03 - Functions.timeElapsed()%0.03;
		int intervalInt = (int) interval;
		
		double driver[] = {leftDrive[intervalInt], rightDrive[intervalInt]};
		
		Drive.drive(driver);
	}
	
	public static void write (String filename, double[] x) throws IOException{
		  BufferedWriter outputWriter = null;
		  outputWriter = new BufferedWriter(new FileWriter(filename));
		  for (int i = 0; i < x.length; i++) {
		    // outputWriter.write(x[i]+"");
		    outputWriter.write(Double.toString(x[i]));
		    outputWriter.newLine();
		  }
		  outputWriter.flush();  
		  outputWriter.close();  
	}
	
	public static void read(String filename, double[] x) throws IOException {
		int i= 0; 
	    Scanner sc = new Scanner(new File(filename));
	    while (sc.hasNextDouble()) {
	    	  x[i] = sc.nextDouble();
	          i++;
	    }
	    sc.close();
	    num = i;
	}
	
	public static void writeToFile(String fileLeft, String fileRight, double[] macroLeft, double[] macroRight) {
		try
		{write (fileLeft, macroLeft);}
		catch (IOException e) {
		};
		try
		{write (fileRight, macroRight);}
		catch (IOException e) {
		}
		
	}
	
	public static void readFromFile(String fileLeft, String fileRight, double[] macroLeft, double[] macroRight) {
		try
		{read (fileLeft, macroLeft);}
		catch (IOException e) {
		};
		try
		{read (fileRight, macroRight);}
		catch (IOException e) {
		}
	}
	
	public static void recordMacro(double[] macroLeft, double[] macroRight) {
		record(macroLeft, macroRight);
	}
	
	public static void runFromArray (double[] macroLeft, double[] macroRight) {
		runMacro(macroLeft, macroRight);
	}
	
}