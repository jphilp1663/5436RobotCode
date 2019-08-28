package org.usfirst.frc.team5436.ballhandler;

import org.usfirst.frc.team5436.functions.Gamepad;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.JoystickBase;
import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.PWMSpeedController;
import edu.wpi.first.wpilibj.VictorSP;


public class ballhandler {
	
	static double oneSpeedToRuleThemAll;

	static VictorSP victorFive = new VictorSP(5);
	static VictorSP victorFour = new VictorSP(4);
	
// Intake
	
	public static void intakeBalls(double oneSpeedToRuleThemAll) {
		victorFive.set(oneSpeedToRuleThemAll);
		victorFour.set(oneSpeedToRuleThemAll);
	}
	
// Outake	
	
	public static void outakeBalls(double oneSpeedToRuleThemAll, double anotherSpeedToRuleThemAll) {
		victorFive.set(oneSpeedToRuleThemAll);
		victorFour.set(anotherSpeedToRuleThemAll);

	}

	public static void balls() {
		if (Gamepad.secondary.getLeftY() != 0) {
			ballhandler.intakeBalls(Gamepad.secondary.getLeftY());
		}
		
		else if (Gamepad.secondary.getRightY() != 0) {
			ballhandler.outakeBalls(Gamepad.secondary.getRightY(), Gamepad.secondary.getLeftY());
		}
		
		else if (Gamepad.secondary.getRightY() == 0 || Gamepad.secondary.getLeftY() == 0) {
			ballhandler.intakeBalls(0);
		}
	}
	
	public static void ballTester() {
	ballhandler.outakeBalls(Gamepad.secondary.getLeftY(), Gamepad.secondary.getRightY());	
	}
	
	}