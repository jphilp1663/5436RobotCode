package frc.robot;

public class Utilities {

    public double deaden(double input, double deadband) {
        deadband = Math.min(1, deadband);
        deadband = Math.max(0, deadband);
        
        if (Math.abs(input) - deadband < 0) {
          return 0;
        }
        else {
          return Math.signum(input) * ((Math.abs(input) - deadband) / (1 - deadband));
        }
    }

    public double limit(double input, double lower, double higher) {
        input = Math.max(higher, input);
        input = Math.min(lower, input);
        
        return input;
    }

    public double[] fgsquircule(double x, double y) {
		  double u = x * Math.sqrt(x*x + y*y - x*x*y*x) / Math.sqrt(x*x + y*y);
		  double v = y * Math.sqrt(x*x + y*y - x*x*y*x) / Math.sqrt(x*x + y*y);
		  double[] squircule = {u,v};
		
		  return squircule;
    }

    //Finds value with maximum absolute value
    public double findMax(double[] values) {
        double max = values[0];

        for (int n = 0; n < values.length; n++) {
            if (Math.abs(values[n]) > max) {
                max = values[n];
            }
        }

        return max;
    }

    public double[] order(double[] input) {
        Mergesort thisSort = new Mergesort();
        
        return thisSort.mergesort(input);
    }
    
    public class Mergesort {

        private double[] merge(double[] a, double[] b) {
            double[] c = new double[a.length + b.length];
            int i = 0, j = 0;
            for (int k = 0; k < c.length; k++) {
                if      (i >= a.length) c[k] = b[j++];
                else if (j >= b.length) c[k] = a[i++];
                else if (a[i] <= b[j])  c[k] = a[i++];
                else                    c[k] = b[j++];
            }
            return c;
        }

        public double[] mergesort(double[] input) {
            int N = input.length;
            if (N <= 1) return input;
            double[] a = new double[N/2];
            double[] b = new double[N - N/2];
            for (int i = 0; i < a.length; i++)
                a[i] = input[i];
            for (int i = 0; i < b.length; i++)
                b[i] = input[i + N/2];
            return merge(mergesort(a), mergesort(b));
        }

        /***************************************************************************
        *  Check if array is sorted - useful for debugging.
        ***************************************************************************/
        private boolean isSorted(double[] a) {
            for (int i = 1; i < a.length; i++)
                if (a[i] < a[i-1]) return false;
            return true;
        }

    }

    public double simPID(double k, double a, double current, double Target, boolean use, double bypassSpeed){
		/**
		 * 
		 * Math Ramping down to target based on encoder count
		 * Function is k*SquareRoot(a + (Target - current)/Target)
		 *  
		 */
		
		// Adding in a boolean we can tie to global variable in case encoders fail
		if (use){
			return k*Math.sqrt(a + (Target - current)/Target);
		}else{
			return bypassSpeed;
		}
    }







}