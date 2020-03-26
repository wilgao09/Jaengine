package jaengine.modules.physics;

import jaengine.logic.Vector2D;

public class RigidBody extends GameAttribute{
    private Vector2D velocity = new Vector2D(0,0);
    private Vector2D forces = new Vector2D(0,0);

    private double mass = 1; //in kg
    public RigidBody() {
        super("RigidBody",true);
    }
    public Vector2D getVelocity() {
        return this.velocity;
    }
    public void setVelocity(Vector2D v) {
        this.velocity = v;
    }
    //THERE IS NO REASON FOR THESE TO EXIST BECAUSE IT JUST
    //GETS THE OBJECT REFERENCE. 
    //PUBLIC WITH EXTRA STEPS
    public void addForce(Vector2D force) {
        this.forces = this.forces.add(force);
    }

    public Vector2D getForce() {
        return forces;
    }

    public void zeroForce() {
        this.forces = new Vector2D(0, 0);
    }

    public double getMass() {
        return mass;
    }
    public void setMass(double m) {
        this.mass = m;
    }
}