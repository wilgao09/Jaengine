package jaengine.modules.physics;

import jaengine.logic.Vector2D;

public class Hitbox extends GameAttribute {
    //this is SIZE
    protected Vector2D[] verticies;
    /**
     * UnitBorder describes the direction of the normal. UnitBorder[0] corresponds to the normal direction for the line formed by verticies[0] and verticies[1]
     */
    private Vector2D[] unitBorder;
    /**
     * Creates a new Hitbox
     * @param verticies the verties of the HixBox. This assuems that the center of mass is about 0,0
     */
    public Hitbox(Vector2D[] verticies) {
        super("Hitbox",false);
        this.verticies = verticies;
        this.unitBorder = new Vector2D[verticies.length];
    }
    /**
     * Creates a new Hitbox
     * @param type The shape of the hitbox; currently only accepts "box"
     * @param dimensions specifies arguemtns for the creation of the shape
     */
    public Hitbox(String type, double[] dimensions) {
        super("Hitbox", false);
        if (type.equals("box")) { //dim0 is w, dim1 is h
            verticies = new Vector2D[4];
            verticies[0] = new Vector2D(-0.5 * dimensions[0], -0.5 * dimensions[1]);
            verticies[1] = new Vector2D(0.5 * dimensions[0], -0.5 * dimensions[1]);
            verticies[2] = new Vector2D(0.5 * dimensions[0], 0.5 * dimensions[1]);
            verticies[3] = new Vector2D(-0.5 * dimensions[0], -0.5 * dimensions[1]);
        }
    }
    /**
     * Creates a set of points that reflect the points shown on screen
     * @param position the amount to move the points by
     * @return the moved points; this does not affect the points stored in this attribute
     */
    public Vector2D[] transformedPoints(Vector2D position) {
        Vector2D[] nPoints = new Vector2D[verticies.length];
        for (int n = 0 ;n != verticies.length; n++) {
            nPoints[n] = verticies[n].add(position);
        }
        return nPoints;
    }
    /**
     * Creates a set of points that reflect the points shown on screen
     * @param radians the amount to rotate the points by in radians
     * @return the rotated points; this does not affect the points stored in this attribute
     */
    public Vector2D[] transformPoints(double radians) {
        Vector2D[] nPoints = new Vector2D[verticies.length];
        // double[] matrix = new double[]{Math.cos(radians), Math.sin(radians)};
        for (int n = 0 ;n != verticies.length; n++) {
            nPoints[n] = verticies[n].rotate(radians);
        }
        return nPoints;
    }
    /**
     * Creates a set of points that reflect the points shown on screen
     * @param position the amount to move the points by
     * @param radians the amount to rotate the points by 
     * @return the rotated -then-translated points; this does not affect the points stored in this attribute
     */
    public Vector2D[] transformPoints(Vector2D position, double radians) {
        Vector2D[] nPoints = new Vector2D[verticies.length];
        for (int n = 0 ;n != verticies.length; n++) {
            nPoints[n] = verticies[n].rotate(radians).add(position);
        }
        return nPoints;
    }
    /**
     * Required function. Sets the normal force vectors. 
     * @param newBorder the border to set
     */
    public void setUnitVectorBorder(Vector2D[] newBorder) {
        this.unitBorder = newBorder;
        super.activate();
    }
    /**
     * Returns unitBorder[n]
     * @param n the nth border
     * @return the direction of the normal on that line
     */
    public Vector2D getNthBorder(int n) {
        return this.unitBorder[n];
    }
    // public static Vector2D computeCenter(double[] points) {

    // }
    /**
     * Finds the center of mass by line segment. THIS SHOULD NOT BE USED.
     * @param points The points of a closed loop shape 
     * @return the COM
     */
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