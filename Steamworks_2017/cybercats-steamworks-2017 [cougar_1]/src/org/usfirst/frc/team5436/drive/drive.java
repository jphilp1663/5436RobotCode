package org.usfirst.frc.team5436.drive;

import org.usfirst.frc.team5436.functions.Gamepad;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.JoystickBase;
import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.PWMSpeedController;
import edu.wpi.first.wpilibj.VictorSP;


public class drive {

	static VictorSP victorZero = new VictorSP(0);
	static VictorSP victorOne = new VictorSP(1);
	static VictorSP victorTwo = new VictorSP(2);
	static VictorSP victorThree = new VictorSP(3);
	
//Tank Control Format (Deadband of +/- 0.15 as function Deaden)
	
	public static double deaden(double u) {
		return Math.abs(u) < .15 ? 0 : u;
	}

//Left Wheel Units
	
	public static void driveLeft(double speedLeft) {
		double realValueLeft = Math.max(-1, speedLeft);
		realValueLeft = Math.min(1, speedLeft);
		
		victorZero.set(-realValueLeft);
		victorOne.set(-realValueLeft);
	}
	
//Right Wheel Units
	
	public static void driveRight(double speedRight) {
		double realValueRight = Math.max(-1, speedRight);
		realValueRight = Math.min(1, speedRight);
		
		victorTwo.set(realValueRight);
		victorThree.set(realValueRight);
	}
	
	public static void driveTank()
	
	{
	
	if (Gamepad.primary.getTriggerLeft() != 0 && Gamepad.primary.getTriggerRight() == 0){
		drive.driveLeft(-Gamepad.primary.getTriggerLeft());
		drive.driveRight(-Gamepad.primary.getTriggerLeft());
	}
	
	else if (Gamepad.primary.getTriggerLeft() == 0 && Gamepad.primary.getTriggerRight() != 0){
		drive.driveLeft(Gamepad.primary.getTriggerRight());
		drive.driveRight(Gamepad.primary.getTriggerRight());
	}
	
	else if (Gamepad.primary.getTriggerLeft() == 0 && Gamepad.primary.getTriggerRight() == 0) {
		drive.driveLeft(Gamepad.primary.getRightY());
		drive.driveRight(Gamepad.primary.getLeftY());
	}
	
	else if ((Gamepad.primary.getTriggerLeft() == 0 && Gamepad.primary.getTriggerRight() == 0) && (Gamepad.primary.getLeftY() == 0 && Gamepad.primary.getRightY() == 0)) {
		drive.driveLeft(0);
		drive.driveRight(0);
	}
	
	}
	
	public static void driveArcade() {
		
		// Y axis denotes speed, X axis denotes rotation
/**		
		if (Gamepad.primary.getTriggerLeft() == 0 && Gamepad.primary.getTriggerRight() == 0){
			drive.driveLeft(Gamepad.primary.getRightY()+0.9*Gamepad.primary.getRightX());
			drive.driveRight(Gamepad.primary.getRightY()-0.9*Gamepad.primary.getRightX());
		}
*/		
		if (Gamepad.primary.getTriggerLeft() != 0 && Gamepad.primary.getTriggerRight() == 0){
			drive.driveLeft(-Gamepad.primary.getTriggerLeft()+0.9*Gamepad.primary.getLeftX());
			drive.driveRight(-Gamepad.primary.getTriggerLeft()-0.9*Gamepad.primary.getLeftX());
		}
		
		else if (Gamepad.primary.getTriggerLeft() == 0 && Gamepad.primary.getTriggerRight() != 0){
			drive.driveLeft(Gamepad.primary.getTriggerRight()-0.9*Gamepad.primary.getLeftX());
			drive.driveRight(Gamepad.primary.getTriggerRight()+0.9*Gamepad.primary.getLeftX());
		}
		
		else if ((Gamepad.primary.getTriggerLeft() == 0 && Gamepad.primary.getTriggerRight() == 0)){
			drive.driveLeft(-Gamepad.primary.getRightX());
			drive.driveRight(Gamepad.primary.getRightX());
		}
	
		else if ((Gamepad.primary.getTriggerLeft() == 0 && Gamepad.primary.getTriggerRight() == 0) && (Gamepad.primary.getLeftY() == 0 && Gamepad.primary.getRightY() == 0)) {
			drive.driveLeft(0);
			drive.driveRight(0);
			
		}
	}
}
		// Depreciated code
	/**	Old code (tank drive that have both sides at the same speed)
	 * 
	 * 		if (Gamepad.primary.getLeftY() > 0 && Gamepad.primary.getRightY() > 0) {
			drive.driveLeft(Gamepad.primary.getLeftY());
			drive.driveRight(-Gamepad.primary.getLeftY());
			}
			else if (Gamepad.primary.getLeftY() > 0 && Gamepad.primary.getRightY() < 0) {
				drive.driveLeft(Gamepad.primary.getLeftY());
				drive.driveRight(Gamepad.primary.getLeftY());
			}
			else if (Gamepad.primary.getLeftY() < 0 && Gamepad.primary.getRightY() > 0) {
				drive.driveLeft(Gamepad.primary.getLeftY());
				drive.driveRight(Gamepad.primary.getLeftY());
			}
			else if (Gamepad.primary.getLeftY() < 0 && Gamepad.primary.getRightY() < 0) {
				drive.driveLeft(Gamepad.primary.getLeftY());
				drive.driveRight(-Gamepad.primary.getLeftY());
			}
			else if (Gamepad.primary.getLeftY() != 0 && Gamepad.primary.getRightY() == 0) {
				drive.driveLeft(Gamepad.primary.getLeftY());
				drive.driveRight(0);
			}
			else if (Gamepad.primary.getLeftY() == 0 && Gamepad.primary.getRightY() != 0) {
				drive.driveLeft(0);
				drive.driveRight(-Gamepad.primary.getRightY());
			}
			else if (Gamepad.primary.getLeftY() == 0 || Gamepad.primary.getRightY() == 0) {
				drive.driveLeft(0);
				drive.driveRight(0);		}
	*/		
	
	
	