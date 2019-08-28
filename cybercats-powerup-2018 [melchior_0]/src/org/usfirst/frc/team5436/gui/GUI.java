package org.usfirst.frc.team5436.gui;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.*;

import org.usfirst.frc.team5436.utilities.Functions;

/**
 * CHANGELOG: 
 * Xi 2/27/18 7:22PM- Commented out gremlin code (remaining from last version)
*/

public class GUI {
	
	//TODO: UPDATE VERSION INFORMATION
	public static String version = "[Melchior_0.1]";
	public static String specifics = "General bugfixes for manipulators and GUI";
	//TODO: UPDATE VERSION INFORMATION
	
	/**
	public static String encodersEnabled = "Use Motor Encoders";
	public static String encodersDisabled = "Dont' Use Motor Encoders";
	public static String encodersSelected;
	
	public static String mecanum = "Mecanum Drive";
	public static String tank = "Tank Drive";
	public static String driveSelected;
	
	public static String gyroEnabled = "Use Gyro";
	public static String gyroDisabled = "Don't Use Gyro";
	public static String gyroSelected;
	
	public static String limitSwitchesEnabled = "Use Elevator Limit Switches";
	public static String limitSwitchesDisabled = "Don't Use Elevator Limit Switches";
	public static String limitSwitchesSelected;

	
	
	public static String autoFixedSide = "Stay at your side regardless";
	public static String autoFlexibleSide = "Go to appropriate side";		
	public static String autoCapability;
	
	public static String red = "Go Red Alliance!";
	public static String blue = "Go Blue Alliance!";		
	public static String teamColor;
		
	public static SendableChooser<String> drivechooser = new SendableChooser<>();
	public static SendableChooser<String> gyrochooser = new SendableChooser<>();
	public static SendableChooser<String> encoderchooser = new SendableChooser<>();
	public static SendableChooser<String> limitSwitchchooser = new SendableChooser<>();
	*/
	
	public static String autoSwitchDesired = "Go for Switch";
	public static String autoScaleDesired = "Go for Scale";
	public static String autoObjective;
	
	public static String autoLeft = "Left Position";
	public static String autoInsideRight = "Center Position";
	public static String autoOutsideRight = "Right Position";
	public static String autoStartLocation;
	
	public static SendableChooser<String> autoObjectivechooser = new SendableChooser<>();
	//public static SendableChooser<String> autoCapabilitychooser = new SendableChooser<>();
	public static SendableChooser<String> autoLocationchooser = new SendableChooser<>();
	//public static SendableChooser<String> teamIdentifier = new SendableChooser<>();
	
	public static String gameData;
	public static char AllianceSwitchSide;
	public static char ScaleSide;
	public static char OpponentSwitchSide;
	
	public static void init() {
	
	/**
	//Add choices for development options in SmartDashboard
	drivechooser.addDefault("Mecanum Drive", mecanum);
	drivechooser.addObject("Tank Drive", tank);
	SmartDashboard.putData("Drive Type", drivechooser);
	*/
	
	//Link to scale priority and switch priority
	autoObjectivechooser.addDefault("Go for Switch", autoSwitchDesired);
	autoObjectivechooser.addObject("Go for Scale", autoScaleDesired);
	SmartDashboard.putData("Auto Objective Selection", autoObjectivechooser);
	
	/**
	//Link to sides of the scale and switch
	autoCapabilitychooser.addDefault("Go to appropriate side",  autoFlexibleSide);
	autoCapabilitychooser.addObject("Stay on current side", autoFixedSide);
	SmartDashboard.putData("Auto Capability Selection", autoCapabilitychooser);
	*/
	
	//Link to positioning of the scale and switch
	autoLocationchooser.addDefault("Left Position", autoLeft);
	autoLocationchooser.addObject("Center Position", autoInsideRight);
	autoLocationchooser.addObject("Right Position", autoOutsideRight);
	SmartDashboard.putData("Robot Location in Autonomous", autoLocationchooser);
	
	/**
	gyrochooser.addDefault("Dont' Use Gyro", gyroDisabled);
	gyrochooser.addObject("Use Gyro", gyroEnabled);
	SmartDashboard.putData("Gyro Usage", gyrochooser);
	
	//Use as a secondary default for the pathing routine
	encoderchooser.addDefault("Don't Use Encoders", encodersDisabled); 
	encoderchooser.addObject("Use Encoders", encodersEnabled);
	SmartDashboard.putData("Encoder Usage", encoderchooser);
			
	limitSwitchchooser.addDefault("Don't Use Limit Switches", limitSwitchesDisabled);
	limitSwitchchooser.addObject("Use Limit Switches", limitSwitchesEnabled);
	SmartDashboard.putData("Limit Switch Usage", limitSwitchchooser);
	
	teamIdentifier.addDefault("Go Blue Alliance!", blue); 
	teamIdentifier.addObject("Go Red Alliance!", red);
	SmartDashboard.putData("Team Color", teamIdentifier);
	*/
	
	Functions.resetAccum();
	}
	
	public static void autonSelector() {
		
		//Retrieve the Switch and Scale Locations from the Driverstation
		gameData = DriverStation.getInstance().getGameSpecificMessage();
				
		AllianceSwitchSide = gameData.charAt(0);
		ScaleSide = gameData.charAt(1);
		OpponentSwitchSide = gameData.charAt(2);
				
		//Retrieve the Auto Choices from the Dashboard 
		autoObjective = autoObjectivechooser.getSelected();
		autoStartLocation = autoLocationchooser.getSelected();
		//autoCapability = autoCapabilitychooser.getSelected();
		
	}
	
	public static void printVersion() {
		
		//if ((Functions.accumalator() % 500) == 0) {
			System.out.println("Version: " + version);
			System.out.println("[Notes: " + specifics + " ]");
		//}
		
	}
	
}