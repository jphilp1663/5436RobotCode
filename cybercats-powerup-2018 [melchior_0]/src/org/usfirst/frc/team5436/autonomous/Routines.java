package org.usfirst.frc.team5436.autonomous;

import org.usfirst.frc.team5436.limbs.Drive;
import org.usfirst.frc.team5436.limbs.Manipulators;
import org.usfirst.frc.team5436.limbs.Motors;
import org.usfirst.frc.team5436.sensors.Encoders;
import org.usfirst.frc.team5436.sensors.IMU;
import org.usfirst.frc.team5436.utilities.DriveProcessing;
import org.usfirst.frc.team5436.utilities.Functions;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//TODO: Add in a routine to release the cube
//TODO: Depending on the base and what is needed, add in a routine to drive with respect to the computer vision pipeline
//TODO: Put the reset method into AutonInit
//TODO: Switch to encoder based distance sensing as the navxgyro's distance gauging is far too noisy to use in standard autonomous

public class Routines {
	
	public static double step = 1;
	public static double stickyStep;
	public static boolean stuck = false;
	public static boolean set = false;
	public static double time = 0;
	public static double stickyTime;
	public static boolean init = true;
	public static boolean done = false;
	
	//TODO: Test for correct values
	public static double switchHeight = 1000;
	public static double scaleHeight = 2000;
	
	public static double initPosition;
	public static double initAngle;
	public static double initElevator;
	
	public static void reset() {
		step = 1;
		stickyStep = 0;
		stuck = false;
		set = false;
		init = true;
		initPosition = 0;
		initAngle = 0;
		initElevator = 0;
		time = 0;
		stickyTime = 0;
		done = false;
	}
	
	public static void path(boolean turn, boolean align, double angle, double distance, double speed, double timeout, double accuracy) {
		
		if (init) {	
			initPosition = Encoders.averagedEncoders()[0];
			initAngle = IMU.getAngle();
			time = Functions.timeElapsed();
			init = false;
		}
		
		if (align) {
			//TODO: Check for a better way to implement
			double[] driver = {-speed, speed};
			Drive.drive(driver);
			
			//Note: this will depend heavily on direction, so make sure that is correct with the given base
			if (Math.abs((Encoders.averagedEncoders()[0] - initPosition) - distance) <= accuracy) {
				step++;	
				Encoders.reset();
				init = true;
			}
			
			else if ((Functions.timeElapsed() - time) >= timeout) {
				if (DriveProcessing.deaden(IMU.velocity()[0]) == 0) {
					System.out.println("WARNING: POSSIBLE BURNOUT");
				}
				if (DriveProcessing.deaden(Functions.vectorConvert(IMU.acceleration()[0], IMU.acceleration()[1])[3]) != 0) {
					System.out.println("WARNING: DIRECTIONAL DEVIATION");
				}
				System.out.println("WARNING: TIMEOUT");
				step++;
				Encoders.reset();
				init = true;
			} 
			
		}
		
		if (turn) {
			//TODO: Check for a better way to implement
			double[] driver = {speed, speed};
			Drive.drive(driver);
			
			//Note: this will depend slightly on direction, so make sure that is correct with the given base in order to minimize spinning time
			
			if (Math.abs((IMU.getAngle() - initAngle) - angle) <= accuracy) {
				step++;	
				Encoders.reset();
				init = true;
			}
			
			else if ((Functions.timeElapsed() - time) >= timeout) {
				if (DriveProcessing.deaden(IMU.velocity()[0]) != 0) {
					System.out.println("WARNING: POSSIBLE BURNOUT");
				}
				if (DriveProcessing.deaden(Functions.vectorConvert(IMU.acceleration()[0], IMU.acceleration()[1])[3]) != 0) {
					System.out.println("WARNING: DIRECTIONAL DEVIATION");
				}
				System.out.println("WARNING: TIMEOUT");
				step++;
				Encoders.reset();
				init = true;
			}  
			
		}
		
	}
	
	//TODO: Add a sticky hit variable or another loop variable in order to make it so that the loop does not stick in a path after the step is incremented
	public static void hitSwitch() {
		if (!set) {
			stickyStep = step;
			set = true;
		}
		
		if (step == stickyStep) {
			Routines.path(false, true, 0, -1200, 0.35, 0.4, 0.5);
			System.out.println("Sticky = 1");
		}
		
		if (step == stickyStep + 1) {
			double[] stop = {0,0};
			Drive.drive(stop);
			if (init) {
				time = Functions.timeElapsed();
				init = false;
			}
			
			Motors.driveLifter(-0.85);
			System.out.println("Sticky = 2");
			if ((Functions.timeElapsed() - time) >= 1.5) {
				Motors.driveLifter(-0.25);
				step++;
				init = true;
			}
		}
		
		if (step == stickyStep + 2) {
			Routines.path(false, true, 0, 1200, -0.35, 0.625, 0.5);
			System.out.println("Sticky = 3");
		}
		
		if (step == stickyStep + 3) {
			double[] stop = {0,0};
			Drive.drive(stop);
			if (init) {
				time = Functions.timeElapsed();
				init = false;
			}
			Motors.motorGrip.set(-1);
			SmartDashboard.putString("Grip Open Requested", "True");		
			System.out.println("Gripper Open is being Requested");
			try {
				//Thread.sleep(Double.doubleToLongBits(GripMotorTime));
				Thread.sleep((long) 50);
			}
			catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}			
			Motors.driveRoller(-1);
			System.out.println("Sticky = 4");
			if ((Functions.timeElapsed() - time) >= 1.5) {
				Motors.driveRoller(0);
				step++;
				init = true;
				stuck = false;
			}
		}
		
		/**
		
		if (step == stickyStep + 4) {
			Routines.path(false, true, 0, 1200, 0.35, 0.55, 0.5);
			System.out.println("Sticky = 5");
		}
		
		if (step == stickyStep + 5) {
			double[] stop = {0,0};
			Drive.drive(stop);
			stuck = false;
		}
		
		*/
		
	}
	
	
	//TODO: Integrate smoother transition
	
	//TODO: Add a sticky hit variable or another loop variable in order to make it so that the loop does not stick in a path after the step is incremented
	public static void hitScale() {
		if (!set) {
			stickyStep = step;
			set = true;
		}
		
		if (step == stickyStep) {
			Routines.path(false, true, 0, -1200, 0.35, 0.4, 0.5);
			System.out.println("Sticky = 1");
		}
		
		if (step == stickyStep + 1) {
			double[] stop = {0,0};
			Drive.drive(stop);
			if (init) {
				time = Functions.timeElapsed();
				init = false;
			}
			
			Motors.driveLifter(-0.85);
			System.out.println("Sticky = 2");
			if ((Functions.timeElapsed() - time) >= 3) {
				Motors.driveLifter(-0.25);
				step++;
				init = true;
			}
		}
		
		if (step == stickyStep + 2) {
			Routines.path(false, true, 0, 1200, -0.35, 0.45, 0.5);
			System.out.println("Sticky = 3");
		}
		
		if (step == stickyStep + 3) {
			double[] stop = {0,0};
			Drive.drive(stop);
			if (init) {
				time = Functions.timeElapsed();
				init = false;
			}
			Motors.motorGrip.set(-1);
			SmartDashboard.putString("Grip Open Requested", "True");		
			System.out.println("Gripper Open is being Requested");
			try {
				//Thread.sleep(Double.doubleToLongBits(GripMotorTime));
				Thread.sleep((long) 50);
			}
			catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}	
			Motors.driveRoller(-1);
			System.out.println("Sticky = 4");
			if ((Functions.timeElapsed() - time) >= 1.5) {
				Motors.driveRoller(0);
				step++;
				init = true;
				//stuck = false;
			}
		}
		
		if (step == stickyStep + 4) {
			Routines.path(false, true, 0, 1200, 0.3, 0.55, 0.5);
			System.out.println("Sticky = 5");
		}
		
		if (step == stickyStep + 5) {
			Motors.driveLifter(0);
			double[] stop = {0,0};
			Drive.drive(stop);
			stuck = false;
		}
		
	}
	
	public static void state(double step) {
		if (step == 0) {
			System.out.println("State: Offline");
		}
		else {
			System.out.println("Current Step: " + step);
		}
		//TODO: Add in all possible states and all possible routes to finalize the watchdog program
		
	}
	
}
