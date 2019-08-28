package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.*;
import frc.robot.Utilities;
import frc.robot.PID;

//TODO: Move away from VictorSPX and abstract to fit to the skeleton
//TODO: Make more automated off of the structure of the Profile interface

//Takes care of grouping motors and establishing basic behavior

public class Drive {
	private Utilities DriveUtilities = new Utilities();
	private PID DrivePID = new PID();

	//TODO: Move away from VictorSPXs using casting and overloading methods
	private VictorSPX[] motors;

    public boolean ramping;
	private boolean[] inversion;
	
	private double[] input;
    public double[] compensation;
	public double[] drive;
	
	private double stepCount;

    public void configure(VictorSPX[] output, boolean[] inversion) {

		this.motors = new VictorSPX[output.length];
		this.inversion = new boolean[output.length];
		this.input = new double[output.length];
		this.compensation = new double[output.length];
		this.drive = new double[output.length];

		//TODO: Add behavior depending on amount of joysticks plugged in
		for (int n = 0; n < output.length; n++) {
			motors[n] = output[n];
			if (inversion[n]) {
				this.motors[n].setInverted(inversion[n]);
			}
		}
		//TODO: Make exception cases and breakouts
	}

	public void configure(VictorSPX[] output) {
		boolean[] blocker = new boolean[output.length];
		for (int n = 0; n < output.length; n++) {
			blocker[n] = false;
		}
		configure(output, blocker);
	}
	
	public void toggleComp(boolean compensate) {
		this.ramping = compensate;
	}

	public void resetCounter() {
		this.stepCount = 0;
	}

	public void run(double[] dInput) {
		for (int n = 0; n < drive.length; n++) {
			this.drive[n] = dInput[n];
		}

		for (int n = 0; n < drive.length; n++) {
			//TODO: Consider PID intergration
			try {
				this.motors[n].set(ControlMode.PercentOutput, drive[n]);
			}
			catch (Exception e) {
				System.out.println("Motor on port " + n + " is not a VictorSPX, error code is: " + e);
			}
		}
	}

    public void run(double[] dInput, double[] eInput, boolean override) {

		for (int n = 0; n < drive.length; n++) {
			this.drive[n] = dInput[n];
		}

        if (ramping && motors.length == 2) {
            for(int n = 0; n < drive.length; n++) {
				//TODO: Add conditioncal statement for the blocking of the ramping
				dualComp(eInput, false);
                this.drive[n] = this.drive[n] * this.compensation[n];
            }
		}
		else {
			//TODO: Implement multiComp after it is finished
		}

		for (int n = 0; n < drive.length; n++) {
			//TODO: Consider PID intergration
			try {
				this.motors[n].set(ControlMode.PercentOutput, drive[n]);
			}
			catch (Exception e) {
				System.out.println("Motor on port " + n + " is not a VictorSPX, error code is: " + e);
			}
		}
    }

	//TODO: Split compensation routines into another file?

    public void dualComp(double[] rates, int[] joint, boolean override){

		//Do not change unless needed, hardcoded behavior, may automatically generate later on
		double compThreshold = 1;
		double comp = 1; //Do not go above 1.  at 1 you are splitting difference and providing max compensation 

		double difference = Math.abs(rates[0] - rates[1]);
		double average = (Math.abs(rates[0]) + Math.abs(rates[1]))/2;
		double lComp = this.compensation[joint[0]];  //Set default to no compensation
		double rComp = this.compensation[joint[1]];  //Set default to no compensation
	
		//Don't compensate for encoders when turning 
		if (override){
			lComp = 1;
			rComp = 1;
		//Only calculate compensation if outside threshold
		} else if (difference >= compThreshold){
		    //Left Side is Faster
			if (rates[0] - rates[1] > 0){
				lComp = DriveUtilities.limit(1 - comp*(0.5*difference/average), -1, 1);  
				rComp = DriveUtilities.limit(1 + comp*(0.5*difference/average), -1, 1);
			//Right Side is Faster	
			} else {
				lComp = DriveUtilities.limit(1 + comp*(0.5*difference/average), -1, 1);  
				rComp = DriveUtilities.limit(1 - comp*(0.5*difference/average), -1, 1);
			}
		//Provide no compensation as within threshold	
		} else {
			lComp = 1;
			rComp = 1;
		}
			
		this.compensation[joint[0]] = lComp;
		this.compensation[joint[1]] = rComp;
	}

	public void dualComp(double[] rates, boolean override) {
		int[] joint = {0,1};

		dualComp(rates, joint, override);
	}

	public void dualComp(double[] rates) {
		int[] joint = {0,1};

		dualComp(rates, joint, false);
	}

	/**
	public void multiComp(double[] rates, int[] set, boolean override) {
		double average = 0;
		double[] error = new double[rates.length];
		double max;

		for (int n = 0; n < rates.length; n++) {
			average =+ rates[n];
		}
		if (rates.length > 0) {
			average = average/rates.length;
		}

		for (int n = 0; n < rates.length; n++) {
			error[n] = rates[n] - average;
			error[n] = 1 + error[n]/average;
		}
		max = DriveUtilities.findMax(error);
		if (max != 0) {
			for (int n = 0; n < rates.length; n++) {
				error[n] = error[n]/max;
			}
		}

		if (!override) {
			for (int n = 0; n < set.length; n++) {
				this.compensation[set[n]] = this.compensation[set[n]] * error[n];
				if (this.compensation[set[n]] == 0) {
					this.compensation[set[n]] = 1;
				}
			}
		}
	}
	*/

	public void multiComp(double[] rates, int[] set, boolean override) {
		if (rates.length == 0) return;
		
		double average = 0.0;
		double[] error = new double[rates.length];
		double max = 0.0;

		for (int n = 0; n < rates.length; n++) {
			average += rates[n];
		}
		
		average = average / rates.length;
		
		for (int n = 0; n < rates.length; n++) {
			//error[n] = (rates[n] - average) / average + 1.0;
			if (rates[n] != 0.0) error[n] = 1.0 - (rates[n] - average) / rates[n];
			
			if (Math.abs(error[n]) > max) max = Math.abs(error[n]);
		}
		
		if (max > 0.0) {
			for (int n = 0; n < rates.length; n++) {
				error[n] = error[n]/max;
			}
		}

		if (!override) {
			for (int n = 0; n < set.length; n++) {
				this.compensation[set[n]] = this.compensation[set[n]] * error[n];
				if (Math.abs(this.compensation[set[n]]) < 0.05) {
					this.compensation[set[n]] = 1;
				}
			}
		}
	}

	public void multiComp(double[] rates) {
		int[] sets = new int[motors.length];
		for (int n = 0; n < sets.length; n++) {
			sets[n] = n;
		}

		multiComp(rates, sets, false);
	}

	public void stepComp(double[] positions, int[] set, boolean override) {
		if (positions.length == 0) return;
		double[] error = new double[positions.length];
		double max;

		if (this.stepCount % 5 == 1) {
			max = DriveUtilities.findMax(positions);

			for (int n = 0; n < error.length; n++) {
				if (positions[n] != 0) {
					error[n] = max/positions[n];
				}
				else {
					error[n] = 1;
				}
			}
		}

		if (!override) {
			for (int n = 0; n < set.length; n++) {
				this.compensation[set[n]] = this.compensation[set[n]] * error[n];
				if (Math.abs(this.compensation[set[n]]) < 0.05) {
					this.compensation[set[n]] = 1;
				}
			}
		}
	}

	public void stepComp(double[] rates, int lead) {
		int[] sets = new int[motors.length];
		for (int n = 0; n < sets.length; n++) {
			sets[n] = n;
		}

		stepComp(rates, sets, false);
	}

	public void followComp(double[] rates, int lead, int[] set, boolean override) {
		if (rates.length == 0) return;
		double[] error = new double[rates.length];

		for (int n = 0; n < rates.length; n++) {
				error[n] = rates[n]/rates[lead];
		}

		if (!override) {
			for (int n = 0; n < set.length; n++) {
				this.compensation[set[n]] = this.compensation[set[n]] * error[n];
				if (Math.abs(this.compensation[set[n]]) < 0.05) {
					this.compensation[set[n]] = 1;
				}
			}
		}
	}

	public void followComp(double[] rates, int lead) {
		int[] sets = new int[motors.length];
		for (int n = 0; n < sets.length; n++) {
			sets[n] = n;
		}

		followComp(rates, lead, sets, false);
	}

	public double subComp(double[] rates) {
		if (rates[1] == 0) {
			rates[0] = 1;
		}
		else {
			rates[0] = rates[0]/rates[1];
		}

		return rates[0];
	}

	public void crossComp(double[] rates, int[] set) {
		//Uses cross-coupling as well as motor sliding to achieve stability (see bellow for reference)
		//https://ieeexplore-ieee-org.huaryu.kl.oakland.edu/stamp/stamp.jsp?tp=&arnumber=5448469

		double[] errorComp = new double[set[set.length-1]+1];
		double[] syncComp = new double[set[set.length-1]+1];
		double[] sample = new double[2];

		//System.out.println("Error array start");
		for (int i = 0; i < set.length; i++) {
			//Sets sampling for basic error correction term
			if (i != set.length-1) {
				sample[0] = rates[i];
				sample[1] = rates[i+1];
			}
			else if (i == set.length-1) {
				sample[0] = rates[set.length-1];
				sample[1] = rates[0];
			}
			
			if (errorComp[i] == 0) {
				errorComp[i] = 1;
			}
			/**
			errorComp[i] = errorComp[i] * subComp(sample);
			System.out.println("Error compensation on motor " + i + ": " + errorComp[i]);
			*/
		}
		//System.out.println("Error array end, length: " + errorComp.length);

		//System.out.println("Sync array start");
		for (int i = 0; i < errorComp.length; i++) {
			//Sets sampling for synchronization error term
			if (i != 0) {
				sample[0] = errorComp[i];
				sample[1] = errorComp[i-1];
			}
			else if (i == 0) {
				sample[0] = errorComp[1];
				sample[1] = errorComp[set.length-1];
			}

			if (syncComp[i] == 0) {
				syncComp[i] = 1;
			}
			/**
			syncComp[i] = syncComp[i] * subComp(sample);
			System.out.println("Synchronization compensation on motor " + i + ": " + syncComp[i]);
			*/
		}
		//System.out.println("Sync array end, length: " + errorComp.length);

		//System.out.println("Compositing compensation start, length: " + set.length);
		//TODO: Figure out a better way to composite these values
		for (int n = 0; n < set.length; n++) {
			//System.out.println("Composition at position: " + n + "," + set[n]);
			this.compensation[set[n]] = errorComp[n] * syncComp[n];
		}
	}
	
	public void crossComp(double[] rates) {
		int[] sets = new int[motors.length];
		for (int n = 0; n < sets.length; n++) {
			sets[n] = n;
		}

		crossComp(rates, sets);
	}
}