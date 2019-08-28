package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.Encoder;
import frc.robot.Utilities;

//TODO: Pole placement algorithm needed for PID controllers

public class PID {

    private double P;
	private double I;
    private double D;
    
    private double target;
    private double position;
    public double output;

    private double pError;
    private double iError;
    private double dError;

	private boolean firstRun = true;
    private double lastpError;
    private double lastTime;

    public void configure(double[] PID) {
        this.P = PID[0];
        this.I = PID[1];
        this.D = PID[2];
    }

    public double run(double target, double feedback) {
        if (firstRun) {
            lastTime = System.currentTimeMillis();
            firstRun = false;
        }

        this.target = target;
        this.position = feedback;

        process();
        return output;
    }

    public void process() {
        //TODO: Correct units
        pError = (target - position)/(target);
        /**
        iError =+ (pError + lastpError)/2*((1/1000)*(System.currentTimeMillis()-lastTime));
        */
        iError = 0;
        /**
        try {dError = (pError - lastpError)/((1/1000)*(System.currentTimeMillis()-lastTime));} 
        catch (Exception e) {dError = 0;}
        */
        dError = 0;

        double move = P*pError + I*iError + D*dError;

        //TODO: Add additional biasing and limiting logic
    
        lastpError = pError;
        lastTime = System.currentTimeMillis();
        this.output = move;
    }

    public void autoTune(double bound) {
        //TODO: Pole placement algorithm that tunes to a very small treshold using pole placement and iteration
    }

    public void reset() {
        //TODO: Make this reset everything, including local variables
        firstRun = true;
    }

}
