package jaengine.logic;

/**
 * Generic mathematical Vector class. Only supports two dimensiona
 */
public class Vector2D {
    private double x, y;
    /**
     * Initialize a vector with an x component and a y component
     * @param x the x component
     * @param y the y component 
     */
    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }
    /**
     * return this x component
     * @return a scalar representation of this vector's x component
     */
    public double x() {
        return x;
    }
    /**
     * return this y component
     * @return a scalar representation of this vector's y component
     */
    public double y() {
        return y;
    }
    /**
     * Add a vector to this vector. Does not modify either vector
     * @param v a vector to add this to.
     * @return a new vector that replresents the sum of the two vectors. 
     */
    public Vector2D add(Vector2D v) {
        return new Vector2D(this.x + v.x(), this.y + v.y());
    }
    /**
     * Negate a vector by negating each of its components
     * @return a new vector with negated components
     */
    public Vector2D reverse() {
        return new Vector2D(-this.x, -this.y);
    }
    /**
     * Return the magnitude fo this vector
     * @return the magnitude of this vector
     */
    public double magnitude() {
        return Math.sqrt(Math.abs(this.x * this.x + this.y * this.y));
    }
    /**
     * Mutliply a vector by a scalar amount
     * @param scale the scalar amonut to multiply by
     * @return a new vector that is scaled according to the scale parameter
     */
    public Vector2D scale(double scale) {
        return new Vector2D(this.x * scale, this.y * scale);
    }
    /**
     * A string representation of this vector
     * @return this vector in the form xi + yj
     */
    public String toString() {
        return this.x + "i + " + this.y + "j";
    }
    /**
     * Rotate this vector by a radiam amount about (0,0). Utilizes the rotation matrix
     * @param radians the amonut to rotate by
     * @return a new vector representing the rotated vector.
     */
    public Vector2D rotate(double radians) {
        double s = FastTrig.sin(radians);
        double c = FastTrig.cos(radians);
        return new Vector2D(x * c - y * s, x * s + y * c); //wikiedpia page for rotation matrix
    }
    //please never use this
    /**
     * Not meant to be used; return the angle below this vector
     * @return the angle in radians
     */
    public double angle() {
        return Math.atan(this.y / this.x);
    }
    //when do i use this? (LOL NOW BUDDY)
    /**
     * Return the dotproduct betwen this vector and another vector
     * @param v the other vector to dot
     * @return the dot product
     */
    public double dotProduct(Vector2D v) {
        return ((this.x * v.x())+(this.y * v.y()));
    }

    //2d physics; its a corss product, so in theory, the vector should have three dimensions
    //but im not implementing that
    //so the y slot serves as both the y and the z
    /**
     * Compute the cross product. Because these vectors only support two dimensions, the result is stored in the y component.
     * @param v the other vector to cross
     * @return "the cross product"
     */
    public Vector2D crossProduct(Vector2D v) {
        return new Vector2D(0, this.x * v.y - this.y * v.x);
    }
}
