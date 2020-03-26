package jaengine.modules.physics;

import jaengine.logic.Vector2D;

public class Hitbox extends GameAttribute {
    //this is SIZE
    protected Vector2D[] verticies;
    public Hitbox(Vector2D[] verticies) {
        super("Hitbox",true);
        this.verticies = verticies;
        
    }
    public Hitbox(String type, double[] dimensions) {
        super("Hitbox", true);
        if (type.equals("box")) { //dim0 is w, dim1 is h
            verticies = new Vector2D[4];
            verticies[0] = new Vector2D(-0.5 * dimensions[0], -0.5 * dimensions[1]);
            verticies[1] = new Vector2D(0.5 * dimensions[0], -0.5 * dimensions[1]);
            verticies[2] = new Vector2D(0.5 * dimensions[0], 0.5 * dimensions[1]);
            verticies[3] = new Vector2D(-0.5 * dimensions[0], -0.5 * dimensions[1]);
        }
    }
    // public static Vector2D computeCenter(double[] points) {

    // }

    public static Vector2D computeCenter(Vector2D[] points) {
        Vector2D COM = new Vector2D(0,0);
        double perimeter = 0;
        for (int n = 0 ; n < points.length-1; n++) {
            Vector2D toNextPoint = points[n+1].add(points[n].reverse()); //this is SUBTRACTION
            //to next point is the vector that ponits the nth point to the next point

            //first, we find the COM of hte line segment, which is the one we're on plus half of the toNext
            Vector2D COMi = points[n].add(toNextPoint.scale(.5));

            //the COM is a weighted average, but instead of weighting with mass, we use length (theres some light math magic here, but it also intuitively makes sense)
            double len = toNextPoint.magnitude();
            COMi = COMi.scale(len);
            perimeter += len;
            COM = COM.add(COMi); //average, so you sum up all of COMi from 0 to however many lines - 1
        }
        //this is the edge case: wrapping around from end to front to make a closed loop
        Vector2D _lastVector = points[0].add(points[points.length-1].reverse());
        COM = COM.add(points[points.length-1].add(_lastVector.scale(.5)).scale(_lastVector.magnitude())); //hopefully this is right
        perimeter += points[points.length-1].add(points[0].reverse()).magnitude();
        //divide by total mass
        return COM.scale(1/perimeter);
    }
}