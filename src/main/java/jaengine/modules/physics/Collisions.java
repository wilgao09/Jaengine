package jaengine.modules.physics;

import java.util.HashMap;

import jaengine.logic.Vector2D;

public class Collisions {
    //this class is just static methods for insane math
    public static double findImpulse(double massA, Vector2D velA, double massB, Vector2D velB, Vector2D bNorm, double res) {

        //try number four
        //what i failed to know was that the coefficient of restitution 
        // was from the colliding FRAME OF REFERENCE
        
        // double trueV0 = velA.add(velB.reverse()).magnitude();
        // return (Math.sqrt(Math.abs(1+res*res)-1)) * trueV0;
        Vector2D relV = velB.add(velA.reverse());
        double velOnNorm = relV.dotProduct(bNorm.reverse());
        if (velOnNorm > 0) return 0;

        double impulse = -(1+res) * velOnNorm; 
        impulse /= 1/massA + 1/massB;

        return impulse;

        // double vA = velA.magnitude();
        // double vB = velB.magnitude();
        // Vector2D p0 = velA.scale(massA).add(velB.scale(massB));
        // double p0scalar = p0.magnitude(); //if error, check here first
        // double p02 = p0.dotProduct(p0);
        // double massTerm = -1 * (massB*massB + massB*massA);
        // //this si not the real k0, you need to divide this by two
        // double k0 = velA.dotProduct(velA) *massA + velB.dotProduct(velB) * massB;
        
        // //this can be all done in one line, but im breaking it so i dont get an aneurysm
        // double term1 = (-1/8.0) * (massA+massB) / p0scalar;
        // double term2 = p02 / massTerm;
        // double term3 = res*res*k0*massA/massTerm;
        // double term4 = p0scalar/massTerm;

        // double vbf = Math.sqrt(Math.abs(term1 + term2 + term3) )- term4;
        // System.out.println("inputs" + massA + "," + velA + " ," + massB + " ," + velB);
        // System.out.println("terms " + term1 + "," + term2 + " ," + term3 + " ," + term4);
        // return massB*(vbf-vA); // this is very likeyl wrong
        // massA = 1/massA;
        // massB = 1/massB;
        // double scale = -(1+res)/(massA + massB);
        // return velB.add(velA.reverse()).scale(scale);
    }

    public static Vector2D findDirection(HashMap<Integer,Integer> lines, Hitbox hb) {
        Vector2D largest = new Vector2D(0,0); //notactually largest, more like the mode
        int freq = -1;
        for (int key : lines.keySet()) {
            if (lines.get(key) > freq){
                freq = lines.get(key);
                largest = hb.getNthBorder(key);
            }
        }
        return largest;
        
    }
}