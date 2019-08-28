package frc.robot;

import frc.robot.PID;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

public class Elevator {
    PID elevatorPID = new PID();

    VictorSPX elevatorMotor = new VictorSPX(8);

    public static Encoder eElevator = new Encoder (8, 9);

    double[] elevatePID = {5, 0, 0};

    public void elevator(Joystick xbox) {
        if (!xbox.getRawButton(5) && !xbox.getRawButton(6)) {
          if (xbox.getRawButton(1)) {
            //elevatorMotor.set(ControlMode.PercentOutput, 0.80);
            if (xbox.getRawButton(7)) {
              //208*(12+0) is the encoder count of the baseline position
              elevatorPID.run(208*(12+0), -eElevator.getDistance());
            }
            else {
              elevatorPID.run(208*(1+0), -eElevator.getDistance());
            }
            elevatorMotor.set(ControlMode.PercentOutput, elevatorPID.output);
          }
          else if (xbox.getRawButton(3)) {
            if (xbox.getRawButton(7)) {
              //See comment in previous
              elevatorPID.run(208*(12+28+1), -eElevator.getDistance());
            }
            else {
              elevatorPID.run(208*(0+28+1), -eElevator.getDistance());
            }
            elevatorMotor.set(ControlMode.PercentOutput, elevatorPID.output);
          }
          else if (xbox.getRawButton(4)) {
            if (xbox.getRawButton(7)) {
              elevatorPID.run(208*(12+56-2), -eElevator.getDistance());
            }
            else {
              elevatorPID.run(208*(0+56-2), -eElevator.getDistance());
            }
            elevatorMotor.set(ControlMode.PercentOutput, elevatorPID.output);
          }
          else {
            elevatorMotor.set(ControlMode.PercentOutput, 0);
          }
        }
        else {
          //Manual override here
          if (xbox.getRawButton(5)) {
           elevatorMotor.set(ControlMode.PercentOutput, 0.80);
          }
          else if (xbox.getRawButton(6)) {
            elevatorMotor.set(ControlMode.PercentOutput, -0.80);
          }
          else {
            elevatorMotor.set(ControlMode.PercentOutput, 0);
          }
        }
      }

      public void init() {
        elevatorMotor.setNeutralMode(NeutralMode.Brake);
        elevatorPID.configure(elevatePID);
        elevatorPID.reset();
    
        //Set all the distance per pulse values for the encoders to use the GetDistance() method
        eElevator.setDistancePerPulse(1);  //based on last years bot to start
      }

      public void diog() {
        System.out.println("Elevator Encoder: " + eElevator.getDistance());
      }

      public void reset() {
        eElevator.reset();
      }
}