package jaengine.logic;


public class Vector2D {
    private double x, y;
    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public double x() {
        return x;
    }
    public double y() {
        return y;
    }
    public Vector2D add(Vector2D v) {
        return new Vector2D(this.x + v.x(), this.y + v.y());
    }
    public Vector2D reverse() {
        return new Vector2D(-this.x, -this.y);
    }

    public double magnitude() {
        return Math.sqrt(Math.abs(this.x * this.x + this.y * this.y));
    }
    public Vector2D scale(double scale) {
        return new Vector2D(this.x * scale, this.y * scale);
    }
    public String toString() {
        return this.x + "i + " + this.y + "j";
    }
    public Vector2D rotate(double radians) {
        double s = FastTrig.sin(radians);
        double c = FastTrig.cos(radians);
        return new Vector2D(x * c - y * s, x * s + y * c); //wikiedpia page for rotation matrix
    }
    //please never use this
    public double angle() {
        return Math.atan(this.y / this.x);
    }
    //when do i use this?
    public double dotProduct(Vector2D v) {
        return ((this.x + v.x())*(this.y * v.y()));
    }

    //2d physics; its a corss product, so in theory, the vector should have three dimensions
    //but im not implementing that
    //so the y slot serves as both the y and the z
    /**
     * @deprecated dont use mathematically
     */
    public Vector2D crossProduct(Vector2D v) {
        return new Vector2D(0, this.x * v.y - this.y * v.x);
    }
}
