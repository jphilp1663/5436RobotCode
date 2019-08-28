package org.usfirst.frc.team5436.lifter;

import org.usfirst.frc.team5436.functions.Gamepad;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.JoystickBase;
import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.PWMSpeedController;
import edu.wpi.first.wpilibj.VictorSP;


public class lifter {
	
	static VictorSP victorSix = new VictorSP(6);
/**
	public static void lifter() {
		victorFour.set(1);
	}
	
 public static void stopLifter() {
	 	victorFour.set(0);
 	}
*/	
public static void lifting() { 
	if (Gamepad.secondary.getX()) {
		victorSix.set(1);
	}

	else {
		victorSix.set(0);
	}
 
}
	
	}