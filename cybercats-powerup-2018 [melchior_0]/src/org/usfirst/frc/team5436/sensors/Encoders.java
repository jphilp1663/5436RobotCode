package org.usfirst.frc.team5436.sensors;

import edu.wpi.first.wpilibj.Encoder;

public class Encoders {
	
	public static Encoder encoderL = new Encoder(0,1,true);
	public static Encoder encoderR = new Encoder(2,3);
	public static Encoder encoderLift = new Encoder(4,5);
	
	public static void init() {
		encoderL.setDistancePerPulse(6*Math.PI/360);
		encoderR.setDistancePerPulse(6*Math.PI/360);
	}
	
	public static void reset() {
		encoderL.reset();
		encoderR.reset();
		encoderLift.reset();
		init();
	}
	
	public static void printEncoders() {
		init();
		System.out.println(encoderL.getDistance());
		System.out.println(encoderR.getDistance());
		System.out.println(encoderL.getRate());
		System.out.println(encoderR.getRate());
		System.out.println(encoderLift.getDistance());
	}
	
	public static double[] averagedEncoders() {
		//Currently using only one encoder in order to compensate for the dead encoder on 0,1
		init();
		double distance = (encoderL.getDistance() + encoderR.getDistance())/2;
		double rate = (encoderL.getRate() + encoderR.getRate())/2;
		double[] encoders = {distance, rate};
		
		return encoders;
	}
	
}