package jaengine.logic;

/**
 * Speed up calculation times by using approximate values for trig functinos sin and cos. This uses a 4 term Maclaurin series.
 */
public class FastTrig {
    //im tired of not being able to use trig, so heres a library that
    //should compute trig relatively fast?
    public static final double tau = 6.2831;
    public static final double pi = 3.1415;
    public static final double piHalf = 1.5707;
    public static final int[] factorial = new int[]{
        1,1,2,6,24,120,720,5040,40320,362880
    };
    // private static double[] sinTable = new double[]
    /**
     * Faster sine calcualtion
     * @param theta the angle to calcualte sine on
     * @return sin(theta)
     */
    public static double sin(double theta) {
        //if i want to do alookup, ill do a look up later as a last resort
        //first we need to make sure theta lands between -pi/2 and pi/2
        // return Math.sin(theta);
        if (theta > piHalf || theta < -piHalf) {
            theta = theta%tau;
            if (theta > piHalf) {
                theta = pi - theta;
            } else if (theta < -piHalf) {
                theta = -pi - theta;
            }
        }

        return theta - theta*theta*theta/factorial[3] + theta*theta*theta*theta*theta/factorial[5] - theta*theta*theta*theta*theta*theta*theta/factorial[7];
    }
    /**
     * Faster cios calcualtion 
     * @param theta the angle to calcualte cosine on
     * @return cos(theta)
     */
    public static double cos(double theta) {
        return sin(piHalf-theta);
    }
}