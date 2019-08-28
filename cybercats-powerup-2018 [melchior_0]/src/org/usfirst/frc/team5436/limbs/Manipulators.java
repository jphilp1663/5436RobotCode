package org.usfirst.frc.team5436.limbs;

import org.usfirst.frc.team5436.sensors.Encoders;
import org.usfirst.frc.team5436.utilities.Controllers;
import org.usfirst.frc.team5436.utilities.DriveProcessing;
import org.usfirst.frc.team5436.utilities.Functions;

import edu.wpi.first.wpilibj.smartdashboard.*;

/**
 * CHANGELOG:
 * Xi 2/27/18 7:26 PM- Changed calls for climbing mechanism, fixing button call conflicts
 * Xi 2/27/18 8:09 PM- Reversed the direction of the climber
 */

public class Manipulators extends Motors {
	
	public static void feeder() {
		if (deaden(Controllers.secondary.getRawAxis(3)) != 0){
			motorRoller.set(1);
		}
		if (Controllers.secondary.getRawButton(1)){
			motorRoller.set(-1);	
		}
		if (!(deaden(Controllers.secondary.getRawAxis(3)) != 0) && !(Controllers.secondary.getRawButton(1))){
			motorRoller.set(0);
		}
	}
	
	public static boolean done = false;
	
	public static void elevator(double input) {
		//DOWN AXIS
		if (Controllers.secondary.getRawAxis(1) > 0) {
			motorLift.set(0.5*Controllers.secondary.getRawAxis(1));
		}
				
		else {
			motorLift.set(Controllers.secondary.getRawAxis(1));		
		}
	}
	
	public static void moveTo(double level, double speed, double accuracy) {
		
		if (Math.abs(Encoders.encoderLift.getDistance() - level) > accuracy) {
			elevator(Functions.signFunction(Encoders.encoderLift.getDistance() - level) * speed);
			done = false;
		}
		else {
			Motors.driveLifter(0);
			done = true;
		}
		
	}
	
	public static void climber() {
		if (Controllers.secondary.getRawButton(2)) {
			motorClimb.set(-1);
		}
		else {
			motorClimb.set(0);
		}
	}
	
	public static double gripTime = 430;
	public static boolean lastX = false;
	public static boolean lastY = false;
	public static boolean open = false;
	public static boolean closed = true;
	
	public static void gripper() {
		//Tie button "Y" = button 4 = closing the gripper	
		if (Controllers.secondary.getRawButton(4) && !lastY) {
			motorGrip.set(1);
			SmartDashboard.putString("Grip Close Requested", "True");
			System.out.println("Gripper Closed is being Requested");
			try {
				//Thread.sleep(Double.doubleToLongBits(GripMotorTime));
				Thread.sleep((long) gripTime);	
			}
			catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			motorGrip.set(0);
			closed = true;
			open = false;
			SmartDashboard.putString("Grip Close Requested", "False");
		}
		lastY = Controllers.secondary.getRawButton(4);		
				
		//Tie button "X" = button 3 to grip open
		if (Controllers.secondary.getRawButton(3) && !lastX) {
			motorGrip.set(-1);
			SmartDashboard.putString("Grip Open Requested", "True");		
			System.out.println("Gripper Open is being Requested");
			try {
				//Thread.sleep(Double.doubleToLongBits(GripMotorTime));
				Thread.sleep((long) gripTime);
			}
			catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			motorGrip.set(0);
			open = true;
			closed = false;
			SmartDashboard.putString("Grip Open Requested", "False");
		}
		lastX = Controllers.secondary.getRawButton(3);
	}
	
}