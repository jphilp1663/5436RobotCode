package org.usfirst.frc.team5436.limbs;

import org.usfirst.frc.team5436.utilities.DriveProcessing;

import edu.wpi.first.wpilibj.VictorSP;

public class Motors extends DriveProcessing {
	
	public static VictorSP motorL1 = new VictorSP(0);
	public static VictorSP motorL2 = new VictorSP(1);
	
	public static VictorSP motorR1 = new VictorSP(2);
	public static VictorSP motorR2 = new VictorSP(3);
	
	public static VictorSP motorLift = new VictorSP(4);
	
	public static VictorSP motorRoller = new VictorSP(5);
	
	public static VictorSP motorGrip = new VictorSP(6);
	
	public static VictorSP motorClimb = new VictorSP(7);
	
	public static void driveL1(double speed) {
		motorL1.set(deaden(bound(speed)));
	}
	
	public static void driveL2(double speed) {
		motorL2.set(deaden(bound(speed)));
	}
	
	public static void driveR1(double speed) {
		motorR1.set(deaden(bound(speed)));
	}

	public static void driveR2(double speed) {
		motorR2.set(deaden(bound(speed)));
	}

	public static void driveLifter(double speed) {
		motorLift.set(deaden(bound(speed)));
	}

	public static void driveRoller(double speed) {
		motorRoller.set(deaden(bound(speed)));
	}

	public static void driveGripper(double speed) {
		motorGrip.set(deaden(bound(speed)));
	}

	public static void driveClimber(double speed) {
		motorClimb.set(deaden(bound(speed)));
	}
	
}