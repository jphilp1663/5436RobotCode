package org.usfirst.frc.team5436.limbs;

import org.usfirst.frc.team5436.utilities.Controllers;
import org.usfirst.frc.team5436.utilities.DriveProcessing;

/**
 *  
 *
 */
public class Drive extends Motors {
	
	public static void drive(double[] input) {
		//Assumes inputs are already processed
		driveL1(input[0]);
		driveL2(input[0]);
		
		driveR1(input[1]);
		driveR2(input[1]);
	}
	
	public static void run() {
		double[] driver = DriveProcessing.tank(Controllers.primary.getX(), Controllers.primary.getY());
		drive(driver);
	}
	
}