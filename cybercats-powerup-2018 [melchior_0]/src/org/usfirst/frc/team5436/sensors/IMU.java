package org.usfirst.frc.team5436.sensors;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;
import java.math.*;

public class IMU {
	
	public static AHRS navxgyro = new AHRS(SPI.Port.kMXP);
	
	public static double getAngle() {
		return navxgyro.getAngle();
	}
	
	public static void resetGyro() {
		navxgyro.reset();
	}
	
	//TODO: Get distance accumulators
	//TODO: Get accelerations, etc. possibly make a process to detect collision and other motion based occurances
	
	//Note: Distance measurement is too noisy to utilize
	public static double[] position() {
		double x = navxgyro.getDisplacementX();
		double y = navxgyro.getDisplacementY();
		double z = navxgyro.getDisplacementZ();
		
		double[] position = {x,y,z};
		return position;
	}
	
	public static double[] velocity() {
		double x = navxgyro.getVelocityX();
		double y = navxgyro.getVelocityY();
		double z = navxgyro.getVelocityZ();
		
		double[] velocity = {x,y,z};
		return velocity;
	}
	
	public static double[] acceleration() {
		double x = navxgyro.getRawAccelX();
		double y = navxgyro.getRawAccelY();
		double z = navxgyro.getRawAccelZ();
		
		double[] acceleration = {x,y,z};
		return acceleration;
	}
	
	public static void printIMU() {
		
		System.out.println("Angle: " + getAngle());
		
	}
	
}