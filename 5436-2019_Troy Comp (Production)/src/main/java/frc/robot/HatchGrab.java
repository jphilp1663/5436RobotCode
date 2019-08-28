package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

public class HatchGrab {

    

    VictorSPX hatchGrabMotor = new VictorSPX(10);
    VictorSPX solenoid = new VictorSPX(20);

    public volatile boolean latched = true;

    public void Grab(Joystick joystick) {
    if (joystick.getRawButton(7)) {
        //Going up
        hatchGrabMotor.set(ControlMode.PercentOutput, 0.95);
      }
      else if (joystick.getRawButton(8)) {
        //Going down
        hatchGrabMotor.set(ControlMode.PercentOutput, -0.95);
      }
      else {
        hatchGrabMotor.set(ControlMode.PercentOutput, 0);
      }

      if (latched && joystick.getRawButtonPressed(11)) {
        //Solenoid latching strength
        solenoid.set(ControlMode.PercentOutput, 0.75);
        latched = false;
      }
      else if (!latched && joystick.getRawButtonPressed(11)) {
        solenoid.set(ControlMode.PercentOutput, 0);
        latched = true;
      }
      
      //Add a failsafe in case button that toggles fails.  Tied to the joystick trigger which also runs reset routine in robot.java
      else if (joystick.getRawButtonPressed(1)){
        solenoid.set(ControlMode.PercentOutput, 0);
        latched = true;
      }

      SmartDashboard.putBoolean("Solenoid Latched", !latched);
    }

}