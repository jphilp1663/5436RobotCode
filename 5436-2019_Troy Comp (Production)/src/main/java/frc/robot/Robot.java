/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.AnalogTrigger;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.MjpegServer;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoMode.PixelFormat;
//import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.cameraserver.CameraServer;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.sensors.PigeonIMU;
import com.ctre.phoenix.sensors.PigeonIMU.CalibrationMode;

import frc.robot.Utilities;
import frc.robot.Drive;
import frc.robot.Elevator;
import frc.robot.HatchGrab;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.CANSparkMax;
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  Utilities MyUtilities = new Utilities();


  Elevator Elevator = new Elevator();

  HatchGrab HatchGrab = new HatchGrab();

  //TODO: Fix numbering, and wiring
  VictorSPX cargoGrabMotor = new VictorSPX(9);
  public static CANSparkMax L1 = new CANSparkMax(0,CANSparkMax.MotorType.kBrushless);
  public static CANSparkMax L2 = new CANSparkMax(1,CANSparkMax.MotorType.kBrushless);
  public static CANSparkMax R1 = new CANSparkMax(2,CANSparkMax.MotorType.kBrushless);
  public static CANSparkMax R2 = new CANSparkMax(3,CANSparkMax.MotorType.kBrushless);


  //This is new!!! 

  VictorSPX solenoid = new VictorSPX(20);


  //declare both input joysticks
  //TO DO - individual control box declarations
  public Joystick joystick = new Joystick(0);
  public Joystick xbox = new Joystick(1);
  public Joystick blocker = new Joystick(2);

  //Declare two new Camera Objects
  //Declare two new Camera Objects
  UsbCamera HatchCamera;
  //MjpegServer hatchServer = new MjpegServer("Hatch Server", 1181);
  //CvSource hatchSource = new CvSource("Hatch Source", PixelFormat.kMJPEG, 240, 180, 25);
  UsbCamera CargoCamera;
  //MjpegServer cargoServer = new MjpegServer("Cargo Server", 1182);
  //CvSource cargoSource = new CvSource("Cargo Source", PixelFormat.kMJPEG, 240, 180, 15);

  boolean running = false;

  double input = 0;

  double currentMax = 0;

  double elevatorRamp;

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    /**
    hatchServer.setSource(HatchCamera);
    hatchServer.setCompression(80);
    hatchSource = CameraServer.getInstance().putVideo("Hatch Server", 240, 180);
    */
    HatchCamera = CameraServer.getInstance().startAutomaticCapture();

    /**
    cargoServer.setSource(CargoCamera);
    cargoServer.setCompression(25);
    cargoSource = CameraServer.getInstance().putVideo("Cargo Server", 240, 180);
    */
    CargoCamera = CameraServer.getInstance().startAutomaticCapture();

    Elevator.init();
  }


  @Override
  public void robotPeriodic() {
  }


  @Override
  public void autonomousInit() {

    //This is new!!!
    solenoid.set(ControlMode.PercentOutput, 0.75);
    

  }


  @Override
  public void autonomousPeriodic() {

    //TODO: Set up drive base
    double x = joystick.getRawAxis(0);
    double y = joystick.getRawAxis(1);

    if (joystick.getRawButton(5)) {
      y = -y;
    }

    double L = (-MyUtilities.deaden(y, 0.03) - MyUtilities.deaden(-x, 0.04));
    double R = (-MyUtilities.deaden(-y, 0.03) - MyUtilities.deaden(-x, 0.04));

    L1.set(L);
    L2.set(L);
    R1.set(R);
    R2.set(R);
    //Ends drive
    Elevator.elevator(xbox);

    diog();

    if (MyUtilities.deaden(xbox.getRawAxis(2), 0.05) != 0 || MyUtilities.deaden(xbox.getRawAxis(3), 0.05) != 0) {
      cargoGrabMotor.set(ControlMode.PercentOutput, -MyUtilities.deaden(xbox.getRawAxis(3), 0.05) + MyUtilities.deaden(xbox.getRawAxis(2), 0.05));
    }
    else {
      cargoGrabMotor.set(ControlMode.PercentOutput, 0);
    }

  HatchGrab.Grab(joystick);

  }

  @Override
  public void teleopPeriodic() {

    //TODO: Set up drive base
    double x = joystick.getRawAxis(0);
    double y = joystick.getRawAxis(1);

    if (joystick.getRawButton(5)) {
      y = -y;
    }

    double L = (-MyUtilities.deaden(y, 0.03) - MyUtilities.deaden(-x, 0.04));
    double R = (-MyUtilities.deaden(-y, 0.03) - MyUtilities.deaden(-x, 0.04));

    L1.set(L);
    L2.set(L);
    R1.set(R);
    R2.set(R);
    //Ends drive

    Elevator.elevator(xbox);

    diog();

    if (MyUtilities.deaden(xbox.getRawAxis(2), 0.05) != 0 || MyUtilities.deaden(xbox.getRawAxis(3), 0.05) != 0) {
      cargoGrabMotor.set(ControlMode.PercentOutput, -MyUtilities.deaden(xbox.getRawAxis(3), 0.05) + MyUtilities.deaden(xbox.getRawAxis(2), 0.05));
    }
    else {
      cargoGrabMotor.set(ControlMode.PercentOutput, 0);
    }

    HatchGrab.Grab(joystick);

  }

  @Override
  public void testPeriodic() {
  }

  public void diog() {
        if (joystick.getRawButton(1)) {
          Elevator.reset();
        }

        Elevator.diog();
  }
}
