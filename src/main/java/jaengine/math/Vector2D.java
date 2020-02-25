package jaengine.math;


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
        return Math.sqrt(this.x * this.x + this.y * this.y);
    }
    //please never use this
    public double angle() {
        return Math.atan(this.y / this.x);
    }
    //when do i use this?
    public double dotProduct(Vector2D v) {
        return ((this.x + v.x())*(this.y * v.y()));
    }
}
