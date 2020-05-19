package jaengine.modules.physics;

import jaengine.logic.Vector2D;

public class RigidBody extends GameAttribute{
    private Vector2D velocity = new Vector2D(0,0);
    private Vector2D forces = new Vector2D(0,0);
    private Vector2D angVel = new Vector2D(0,0);
    private Vector2D torques = new Vector2D(0,0);

    private double mass = 1; //in kg
    private double momentOfIntertia = 1;
    public RigidBody() {
        super("RigidBody",true);
    }
    public Vector2D getVelocity() {
        return this.velocity;
    }
    public void setVelocity(Vector2D v) {
        this.velocity = v;
    }
    public void addCOMForce(Vector2D force) {
        this.forces = this.forces.add(force);
    }
    public Vector2D getNetForce() {
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

    public Vector2D getAngVel() {
        return this.angVel;
    }
    public void setAngVel(Vector2D v) {
        this.angVel = v;
    }
    public void addTorque(Vector2D t) {
        this.torques = this.torques.add(t);
    }
    public Vector2D getNetTorque() {
        return this.torques;
    }
    public void zeroTorque() {
        this.torques = new Vector2D(0,0);
    }
    public void setRotIntertia(double I) {
        this.momentOfIntertia = I;
    }
    public double getRotInetria() {
        return this.momentOfIntertia;
    }
}