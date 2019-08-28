package org.usfirst.frc.team5436.autonomous;

import org.usfirst.frc.team5436.autonomous.Routines;
import org.usfirst.frc.team5436.limbs.Drive;

public class Paths extends Routines {
	
	//TODO: Slow down turning speed, increase accuracy values to 10
	
	//TODO: Account for the size of the robot (27 wide, 32 long) (bumpers are 3 inches uniformly thick) => (33 width 38 length)
	//TODO: Add in the actual dropping of the cube	
	//TODO: Develop advanced extraneous pathing such as center to power-up station
		
	public static boolean centerToRightSwitch = false;
	public static boolean centerToLeftSwitch = false;
		
	public static boolean leftToSwitch = false;
	public static boolean leftToLeftScale = false;
	public static boolean leftToRightScale = false;
	
	public static boolean rightToSwitch = false;
	public static boolean rightToLeftScale = false;
	public static boolean rightToRightScale = false;
	
	//TODO: Input magic numbers (yes, this will be the primary mode of operation for deadReckoning, esp. given the set-up)
	//TODO: Build augmented autonomous with object tracking, etc.
	//TODO: Set-up magic numbers for exiting blocks, etc.
	
	//Time to drive the length of the robot is about 0.249 seconds
	
	//Distance from wall to switch is 151.898 inches
	//Want to drive forwards for 50 inches
	public static double decisionPointCenter = 50 - 38/2;
	public static double timeToDecisionPointCenter = 0.73;
	//Remaining distance is 101.898 inches
	public static double toSwitchCenter = 101.898 - 38/2;
	public static double timeToSwitchCenter = 1.5;
	//Each switch is 38.225 inches long
	//Distance from far edge of each scale platform to the other is 144.228 inches
	//Thus the aligning distance is 106.003 inches
	public static double alignSwitchFar = 106.003;
	public static double timeToAlignSwitchFar = 1.75;
		
	//Distance from wall to scale's middle is 323.899 inches
	//Distance from wall to switch's back is 208.543 inches
	//Length of center stick is 73.147 inches
	//Want to drive forwards 250.543 to reach point between scale and switch
	//Want to drive forwards 177.771 inches to find middle of scale
	public static double decisionPointOutside = 177.771 - 38/2;
	public static double timeToDecisionPointOutside = 2.6;
	//Want to drive forwards 57.13 inches to close to switch
	public static double toSwitchOutside = 57.13 - 38/2;
	public static double timeToSwitchOutside = 0.84;
	//Want to drive forwards 72.773 inches to go the alignment stage
	public static double passDecisionPoint = 72.773;
	public static double timeToPassDecisionPoint = 1.07;
	//Distance between the centers of both scales is 144.149 inches
	//Scale is 38.463 inches long
	//From edge to the edge of scale is 76.597 inches
	public static double toScaleOutside = 76.597;
	public static double timeToScaleOutside = 1.13;
	//TODO: Unneeded, for same side, just drive all the way forwards to the scale and turn to go in
	public static double alignScaleClose = 0;
	public static double timeToAlignScaleClose = 0;
	//Want to drive 232.714 inches to align to far side
	public static double alignScaleFar = 232.714;
	public static double timeToAlignScaleFar = 3.42;
	//Want to drive 76.739 inches to go to scale. Want to overdrive slightly to correct to small errors, etc.
	public static double toScale = 76.739 - 38/2;
	public static double timeToScale = 1.13;
	
	//Time to turn, default timeout of 3 seconds
	public static double timeToTurn = 4;
	
	public static void centerToRightSwitch() {
		centerToRightSwitch = true;
		
		if (Routines.step == 1) {
			Routines.path(false, true, 0, decisionPointCenter + toSwitchCenter, -0.5,timeToDecisionPointCenter + timeToSwitchCenter, 0.5);
			stuck = false;
		}
		
		if ((Routines.step == 2) || stuck) {
			stuck = true;
			Routines.hitSwitch();
			Routines.done = true;
		}
	}
	
	public static void centerToLeftSwitch() {
		centerToLeftSwitch = true;
		
		if (Routines.step == 1) {
			Routines.path(false, true, 0, decisionPointCenter, -0.5, timeToDecisionPointCenter, 0.5);
			stuck = false;
		}
		
		if (Routines.step == 2) {
			Routines.path(true, false, -90, 0, -0.27, timeToTurn, 5);
		}
		
		if (Routines.step == 3) {
			Routines.path(false, true, 0, alignSwitchFar, -0.5, timeToAlignSwitchFar, 0.5);
		}
		
		if (Routines.step == 4) {
			Routines.path(true, false, 90, 0, 0.27, timeToTurn, 5);
		}
		
		if (Routines.step == 5) {
			Routines.path(false, true, 0, toSwitchCenter, -0.5, timeToSwitchCenter, 0.5);
		}
	
		if ((Routines.step == 6) || stuck) {
			stuck = true;
			Routines.hitSwitch();
			Routines.done = true;
		}
		
	}
	
	//On left side
	
	public static void leftToSwitch() {
		leftToSwitch = true;
		
		if (Routines.step == 1) {
			Routines.path(false, true, 0, decisionPointOutside, -0.5, timeToDecisionPointOutside, 0.5);
			stuck = false;
		}
		
		if (Routines.step == 2) {
			Routines.path(true, false, 90, 0, 0.27, timeToTurn, 5);
		}
		
		if (Routines.step == 3) {
			Routines.path(false, true, 0, toSwitchOutside, -0.5, timeToSwitchOutside, 0.5);
		}
		
		if ((Routines.step == 4) || stuck) {
			stuck = true;
			Routines.hitSwitch();
			Routines.done = true;
		}
	}
	
	public static void leftToLeftScale() {
		leftToLeftScale = true;
		
		if (Routines.step == 1) {
			Routines.path(false, true, 0, decisionPointOutside + passDecisionPoint + toScaleOutside, -0.5, timeToDecisionPointOutside + timeToPassDecisionPoint + timeToScaleOutside, 0.5);
			stuck = false;
		}
		
		if (Routines.step == 2) {
			Routines.path(true, false, 90, 0, 0.27, timeToTurn, 5);
		}
		
		if (Routines.step == 3) {
			Routines.path(false, true, 0, toScale, -0.5, timeToScale, 0.5);
		}
		
		if ((Routines.step == 4) || stuck) {
			stuck = true;
			Routines.hitScale();
			Routines.done = true;
		}
	}
	
	public static void leftToRightScale() {
		leftToRightScale = true;
		
		if (Routines.step == 1) {
			Routines.path(false, true, 0, decisionPointOutside + passDecisionPoint, -0.5, timeToDecisionPointOutside + timeToPassDecisionPoint, 0.5);
			stuck = false;
		}
		else {
			double[] stop = {0,0};
			Drive.drive(stop);
		}
		
		if (Routines.step == 2) {
			Routines.path(true, false, 90, 0, 0.27, timeToTurn, 5);
		}
		
		if (Routines.step == 3) {
			Routines.path(false, true, 0, alignScaleFar, -0.5, timeToAlignScaleFar, 0.5);
		}
		
		if (Routines.step == 4) {
			Routines.path(true, false, -90, 0, -0.27, timeToTurn, 5);
		}
		
		if (Routines.step == 5) {
			Routines.path(false, true, 0, toScaleOutside, -0.5, timeToScaleOutside, 0.5);
		}
	
		if (Routines.step == 6) {
			Routines.path(true, false, -90, 0, 0.27, timeToTurn, 5);
		}
		
		if (Routines.step == 7) {
			Routines.path(false, true, 0, toScale, -0.5, timeToScale, 0.5);
		}
		
		if ((Routines.step == 8) || stuck) {
			stuck = true;
			Routines.hitScale();
			Routines.done = true;
		}
		
	}
	
	//On right side
	
	public static void rightToSwitch() {
		rightToSwitch = true;
		
		if (Routines.step == 1) {
			Routines.path(false, true, 0, decisionPointOutside, -0.5, timeToDecisionPointOutside, 0.5);
			stuck = false;
		}
		
		if (Routines.step == 2) {
			Routines.path(true, false, -90, 0, -0.27, timeToTurn, 5);
		}
		
		if (Routines.step == 3) {
			Routines.path(false, true, 0, toSwitchOutside, -0.5, timeToSwitchOutside, 0.5);
		}
		
		if ((Routines.step == 4) || stuck) {
			stuck = true;
			Routines.hitSwitch();
			Routines.done = true;
		}
	}
	
	public static void rightToRightScale() {
		rightToRightScale = true;
		
		if (Routines.step == 1) {
			Routines.path(false, true, 0, decisionPointOutside + passDecisionPoint + toScaleOutside, -0.5, timeToDecisionPointOutside + timeToPassDecisionPoint + timeToScaleOutside, 0.5);
			stuck = false;
		}
		
		if (Routines.step == 2) {
			Routines.path(true, false, -90, 0, 0.27, timeToTurn, 5);
		}
		
		if (Routines.step == 3) {
			Routines.path(false, true, 0, toScale, -0.5, timeToScale, 0.5);
		}
		
		if ((Routines.step == 4) || stuck) {
			stuck = true;
			Routines.hitScale();
			Routines.done = true;
		}
	}
	
	public static void rightToLeftScale() {
		rightToLeftScale = true;
		
		if (Routines.step == 1) {
			Routines.path(false, true, 0, decisionPointOutside + passDecisionPoint, -0.5, timeToDecisionPointOutside + timeToPassDecisionPoint, 0.5);
			stuck = false;
		}
		else {
			double[] stop = {0,0};
			Drive.drive(stop);
		}
		
		if (Routines.step == 2) {
			Routines.path(true, false, -90, 0, -0.27, timeToTurn, 5);
		}
		
		if (Routines.step == 3) {
			Routines.path(false, true, 0, alignScaleFar, -0.5, timeToAlignScaleFar, 0.5);
		}
		
		if (Routines.step == 4) {
			Routines.path(true, false, 90, 0, 0.27, timeToTurn, 5);
		}
		
		if (Routines.step == 5) {
			Routines.path(false, true, 0, toScaleOutside, -0.5, timeToScaleOutside, 0.5);
		}
	
		if (Routines.step == 6) {
			Routines.path(true, false, 90, 0, 0.27, timeToTurn, 5);
		}
		
		if (Routines.step == 7) {
			Routines.path(false, true, 0, toScale, -0.5, timeToScale, 0.5);
		}
		
		if ((Routines.step == 8) || stuck) {
			stuck = true;
			Routines.hitScale();
			Routines.done = true;
		}
		
	}
		
}
	