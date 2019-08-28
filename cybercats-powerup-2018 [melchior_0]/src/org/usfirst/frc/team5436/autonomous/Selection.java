package org.usfirst.frc.team5436.autonomous;

import org.usfirst.frc.team5436.gui.GUI;

import edu.wpi.first.wpilibj.smartdashboard.*;

/**
 * CHANGELOG:
 * Xi 2/1X/18 XX:XX PM- Fixed calls for character reading from dashboard 
 * Xi 2/27/18 7:34 PM- Cleaning of calls from GUI
*/
public class Selection extends Paths {
	
	public static boolean scalePriority = false;
	public static boolean switchPriority = false;
	
	public static boolean left = false;
	public static boolean right = false;
	public static boolean center = false;
	
	public static boolean switchLeft = false;
	public static boolean switchRight = false;
	public static boolean scaleLeft = false;
	public static boolean scaleRight = false;

	
	//TODO: read() reads data from driver and field input to change variables in order for choose() to work
	public static void read() {
		
		GUI.autonSelector();
		
		switch(GUI.autoObjective) {
		
		case("Go for Switch"):
			switchPriority = true;
			scalePriority = false;
			break;
		case("Go for Scale"):
			switchPriority = false;
			scalePriority = true;
			break;
		
		}
		
		if (GUI.AllianceSwitchSide == 'L') {
			switchLeft = true;
			switchRight = false;
		}
		if (GUI.AllianceSwitchSide == 'R') {
			switchLeft = false;
			switchRight = true;
		}
		
		if (GUI.ScaleSide == 'L') {
			scaleLeft = true;
			scaleRight = false;
		}
		if (GUI.ScaleSide == 'R') {
			scaleLeft = false;
			scaleRight = true;
		}
		
		switch(GUI.autoStartLocation) {
		
		case("Left Position"):
			left = true;
			center = false;
			right = false;
			break;
		case("Center Position"):
			left = false;
			center = true;
			right = false;
			break;
		case("Right Position"):
			left = false;
			center = false;
			right = true;
			break;
		}
		
	}
	
	public static void choose() {
		
		if (left) {
			if (switchPriority) {
				if (switchLeft) {
					Paths.leftToSwitch();
				}
				else {
					switchPriority = false;
					scalePriority = true;
				}
			}
			
			else if (scalePriority) {
				if (scaleLeft) {
					Paths.leftToLeftScale();
				}
				else if (scaleRight) {
					Paths.leftToRightScale();
				}
			}
		}
		
		if (center) {
			if (switchRight) {
				Paths.centerToRightSwitch();
			}
			else if (switchLeft) {
				Paths.centerToLeftSwitch();
			}
		}
		
		if (right) {
			if (switchPriority) {
				if (switchRight) {
					Paths.rightToSwitch();
				}
				else {
					switchPriority = false;
					scalePriority = true;
				}
			}
			
			else if (scalePriority) {
				if (scaleLeft) {
					Paths.rightToLeftScale();
				}
				else if (scaleRight) {
					Paths.rightToRightScale();
				}
			}
		}	
	}
	
	public static void print() {
		
		SmartDashboard.putBoolean("Scale Priority", scalePriority);
		SmartDashboard.putBoolean("Switch Priority", switchPriority);
		SmartDashboard.putBoolean("Left", left);
		SmartDashboard.putBoolean("Center", center);
		SmartDashboard.putBoolean("Right", right);
		SmartDashboard.putBoolean("Switch Left", switchLeft);
		SmartDashboard.putBoolean("Switch Right", switchRight);
		SmartDashboard.putBoolean("Scale Left", scaleLeft);
		SmartDashboard.putBoolean("Scale Right", scaleRight);
		
		System.out.println(String.valueOf(GUI.AllianceSwitchSide));
		System.out.println(String.valueOf(GUI.ScaleSide));
	}
	
	public static void manual(String path) {
		switch(path) {
		case "centerToLeftSwitch":
			Paths.centerToLeftSwitch();
			break;
		case "centerToRightSwitch":
			Paths.centerToRightSwitch();
			break;
		
		case "leftToSwitch":
			Paths.leftToSwitch();
			break;
		case "leftToLeftScale":
			Paths.leftToLeftScale();
			break;
		case "leftToRightScale":
			Paths.leftToRightScale();
			break;
			
		case "rightToSwitch":
			Paths.rightToSwitch();
			break;
		case "rightToLeftScale":
			Paths.rightToLeftScale();
			break;
		case "rightToRightScale":
			Paths.rightToRightScale();
			break;
		}
	}
	
}