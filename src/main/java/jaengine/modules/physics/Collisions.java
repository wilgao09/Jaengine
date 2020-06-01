package jaengine.modules.physics;

import java.util.HashMap;

import jaengine.logic.Vector2D;

public class Collisions {
    //this class is just static methods for insane math
    public static double findImpulseMag(double massA, Vector2D velA, double massB, Vector2D velB, double res) {
        double vA = velA.magnitude();
        double vB = velB.magnitude();
        Vector2D p0 = velA.scale(massA).add(velB.scale(massB));
        double p0scalar = p0.magnitude(); //if error, check here first
        double p02 = p0.dotProduct(p0);
        double massTerm = -1 * (massB*massB + massB*massA);
        //this si not the real k0, you need to divide this by two
        double k0 = velA.dotProduct(velA) *massA + velB.dotProduct(velB) * massB;
        
        //this can be all done in one line, but im breaking it so i dont get an aneurysm
        double term1 = (-1/8.0) * (massA+massB) / p0scalar;
        double term2 = p02 / massTerm;
        double term3 = res*res*k0*massA/massTerm;
        double term4 = p0scalar/massTerm;

        double vbf = Math.sqrt(Math.abs(term1 + term2 + term3) )- term4;
        System.out.println("inputs" + massA + "," + velA + " ," + massB + " ," + velB);
        System.out.println("terms " + term1 + "," + term2 + " ," + term3 + " ," + term4);
        return massB*(vbf-vA); // this is very likeyl wrong
    }

    public static Vector2D findDirection(HashMap<Integer,Integer> lines, Hitbox hb) {
        Vector2D sum = new Vector2D(0,0);
        for (int key : lines.keySet()) {
            sum = sum.add(hb.getNthBorder(key));
        }
        return sum.scale(1.0/lines.keySet().size());
    }
}