package org.usfirst.frc.team5436.utilities;

import org.usfirst.frc.team5436.utilities.Functions;

public class DriveProcessing {
	
	//TODO: Write in motion profiles
	//TODO: Port over mecanum drive set-ups for redundancy
	//TODO: Generalize into a customizable interface and class setup for flexibility
	
	public static double deaden(double input) {
		if (Math.abs(input) < 0.1) {
			return 0;
		}
		else {
			return Functions.signFunction(input) * (Math.abs(input) - 0.1) * 1/0.9;
		}
	}
	
	public static double bound(double input) {
		if (Math.abs(input) > 1) {
			return Functions.signFunction(input) * 1;
		}
		else {
			return input;
		}
	}
	
	public static double[] fgsquircule(double x, double y) {
		double u = x * Math.sqrt(x*x + y*y - x*x*y*x) / Math.sqrt(x*x + y*y);
		double v = y * Math.sqrt(x*x + y*y - x*x*y*x) / Math.sqrt(x*x + y*y);
		double[] squircule = {u,v};
		
		return squircule;
	}
	
	public static double[] tank(double x, double y) {
		double u = bound(-deaden(y) - deaden(-0.75*x));
		double v = bound(-deaden(-y) - deaden(-0.75*x));
		double driver[] = {u,v};
		
		return driver;
	}
	
	//TODO: Rewrite another ramping set-up generalized for any given motor, one is piecewise, the other is logistic (not needed, but will be necessary for motor profiling)
	
}