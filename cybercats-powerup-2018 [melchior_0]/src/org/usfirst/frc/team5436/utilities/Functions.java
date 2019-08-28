package org.usfirst.frc.team5436.utilities;

import edu.wpi.first.wpilibj.interfaces.Accelerometer;


//TODO: Import IMU
//import org.usfirst.frc.team5436.sensors.imu;


import java.lang.*;
import java.util.concurrent.TimeUnit;

public class Functions {

	//TODO Check your math
	
	static double trapReimannSumAccel = 0;
	static double time1A = 0;
	static double accel1 = 0;
	
	static double trapReimannSumVel = 0;
	static double time1B = 0;
	static double vel1 = 0;
	
	static double dXA = 0;
	static double dYA = 0;
	static double dXB = 0;
	static double dYB = 0;
	
	static double timeOfInit;
	static double ticker = 0;
	
	static long lastCall;
	static long thisCall;
	static long timeToLastCall;
	static double a = 0;
	
	static long currentDelay = 0;
	
	public static double integralAccel(double accel) {
		
		dXA = 0.001 * System.currentTimeMillis() - time1A;
		time1A = 0.001 * System.currentTimeMillis();
		
		dYA = (accel + accel1)/2;
		accel1 = accel;
		
		trapReimannSumAccel = trapReimannSumAccel + dYA * dXA;
		
		return trapReimannSumAccel;
		
	}
	
	public static double integralVelocity(double velocity) {
		
		dXB = 0.001 * System.currentTimeMillis() - time1B;
		time1B = 0.001 * System.currentTimeMillis();
		
		dYB = (velocity + vel1)/2;
		accel1 = velocity;
		
		trapReimannSumVel = trapReimannSumVel + dYB * dXB;
		
		return trapReimannSumVel;
		
	}
	
	public static void resetInt() {
		dXA = 0;
		dYA = 0;
		
		dXB = 0;
		dYB = 0;
		
		time1A = 0;
		time1B = 0;
		
		accel1 = 0;
		vel1 = 0;
		
		trapReimannSumAccel = 0;
		trapReimannSumVel = 0;
	}
	
	public static double ticksElapsed = 0;
	
	public static double accumalator() {
		ticksElapsed = ticksElapsed + 1;
		return ticksElapsed;
	}
	
	public static void resetAccum() {
		ticksElapsed = 0;
	}
	
	public static double timeElapsed() {
		if (ticker < 1) {
			timeOfInit = System.currentTimeMillis() * 0.001;
			ticker = ticker +1;
			return 0;
		}
		return System.currentTimeMillis() * 0.001 - timeOfInit;
	}
	
	public static void resetTime() {
		ticker = 0;
		timeOfInit = 0;
	}
	
	public static long timeToLastCall() {
		if (a > 1) {
		lastCall = thisCall;
		thisCall = System.currentTimeMillis();
		timeToLastCall = thisCall - lastCall;
		return timeToLastCall;
		}
		else {
			return 0;
		}
	}

	public static void resetTimeToLastCall() {
		thisCall = 0;
		lastCall = 0;
		timeToLastCall = 0;
		a=0;
	}
	
	//TODO: Implement better compensation for long error times
	
	public static void steadyIncrement(long period) {
		currentDelay = timeToLastCall();
		if (currentDelay < period) {
			try {
			Thread.sleep(period - timeToLastCall());
			}
			catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			}
		}
	}
	
	public static void resetSteadyIncrement() {
		currentDelay = 0;
	}
	
	public static double noiseDampen(double input, double limit) {
		if (Math.abs(input) < limit) {
			return 0;
		}
		else {
			return input;
		}
	}
	
	public static double signFunction(double input) {
		if (input > 0) {
			return 1;
		}
		else if(input < 0) {
			return -1;
		}
		else {
			return 0;
		}
	}
	
	//Integration from negative infinity to positive infinity is square root of Pi
	//Convergence to this value is extremely rapid, so using a intercept value of positive or negative 2 is acceptable within error limits
	//Should define intercepts at about +/- 5, determine value for convenience
	
	public static double guassErrorFunctionApprox(double input, double domain) {
		double guassErrorFunction = 1 / Math.sqrt(domain) * 2/Math.sqrt(Math.PI) * signFunction(input) * Math.sqrt(1 - Math.pow(Math.E, -(Math.pow(Math.sqrt(domain) * input, 2)))) * (Math.sqrt(Math.PI) / 2 + 31 / 200 * 
				Math.pow(Math.E, -(Math.pow(Math.sqrt(domain) * input, 2))) - 341 / 8100 * Math.pow(Math.E, -2 * (Math.pow(Math.sqrt(domain) * input, 2))));
		return guassErrorFunction;
	}
	
	//Function of ramp curve, with intercepts at +/- 5
	//Domain will be based on this curve
	//Scalar used to make the distance traveled within correct values will be based on the Guass error function defined in the previous method  
	
	public static double rampCurve(double input, double domain) {
		double output = Math.pow(Math.E, - domain * Math.pow(input, 2)) - 1 / Math.pow(Math.E, 25);
		return output;
	}
	
	//Adaptive code for reaching a select velocity
	//Takes some continuous input and a target values, two sided adjustment, with a secondary function using a linear relation to speed up guessing
	
	//TODO: Finish
	/**
	public static double trimAndHeel(double input, double target, double scale) {
		if (input != target) {
			if (input > target) {
				if ()
			}
		}
	}
	*/
	
	//Need to define intercept values and scalar to make the integral achieve proper values
	//Intercept at x = +/- 5 achieves nominal values close to Math.sqrt(Math.PI)
	//Scalar for smaller range is inverse of the square root of the scalar a in e^(-ax^2)
	//Velocity range then is represented by the range of the resulting function with both scalars not exceeding 
	
	//TODO: Finish
	/**
	public static double ramper(double duration, double distance) {
		double domain = duration / 2;
		double velocity = distance / duration;
		
	}
	*/
	
	public static double[] vectorConvert(double inputX, double inputY) {
		double magnitude = Math.sqrt(inputX * inputX + inputY * inputY);
		double unitX = inputX/magnitude;
		double unitY = inputY/magnitude;
		double angle = Math.atan(inputY / inputX);
		double[] vector = {unitX, unitY, magnitude, angle};
		return vector;
	}
	
}