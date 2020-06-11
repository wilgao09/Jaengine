package jaengine.modules.physics;

import jaengine.logic.Vector2D;

/**
 * The RigidBody class outlines how a physics-applicable object is to move. It contains all physics and keeps track of velocity, forces, and torques
 */
public class RigidBody extends GameAttribute{
    private Vector2D velocity = new Vector2D(0,0);
    private Vector2D forces = new Vector2D(0,0);
    private Vector2D angVel = new Vector2D(0,0);
    private Vector2D torques = new Vector2D(0,0);

    // private double mass = 1; //in kg
    // private double momentOfIntertia = 1;
    /**
     * Create a RigidBody attribute
     */
    public RigidBody() {
        super("RigidBody",true);
    }
    /**
     * return the current velocity
     * @return the velocity
     */
    public Vector2D getVelocity() {
        return this.velocity;
    }
    /**
     * Set the velocity
     * @param v the velocity to set
     */
    public void setVelocity(Vector2D v) {
        this.velocity = v;
    }
    /**
     * Add a fore directly into the center of mass; creates no torque
     * @param force the force to ass to the NetForce
     */
    public void addCOMForce(Vector2D force) {
        if (force.x() == 0 && force.y() == 0) return;
        //System.out.println("addimh " + this.forces + " n " + force);
        Vector2D newForce = this.forces.add(force);

        this.forces = newForce;

    }
    /**
     * Return the current NetForce
     * @return the net force
     */
    public Vector2D getNetForce() {
        return this.forces;
    }
    /**
     * Set the force to zero
     */
    public void zeroForce() {
        this.forces = new Vector2D(0, 0);
    }
    // public double getMass() {
    //     return mass;
    // }
    // public void setMass(double m) {
    //     this.mass = m;
    // }
    /**
     * Retrieve the angular velocity
     * @return the angualr velocity
     */
    public Vector2D getAngVel() {
        return this.angVel;
    }
    /**
     * Set the angualr velocity 
     * @param v the angular velocity in the form of a vector; it should be in the z component, but the value is stored in the y component
     */
    public void setAngVel(Vector2D v) {
        this.angVel = v;
    }
    /**
     * Add a torque
     * @param t the torque to add
     */
    public void addTorque(Vector2D t) {
        this.torques = this.torques.add(t);
    }
    /**
     * Retrieve the net torque
     * @return the net torque
     */
    public Vector2D getNetTorque() {
        return this.torques;
    }
    /**
     * Set the net torque to zero
     */
    public void zeroTorque() {
        this.torques = new Vector2D(0,0);
    }
    // public void setRotIntertia(double I) {
    //     this.momentOfIntertia = I;
    // }
    // public double getRotInetria() {
    //     return this.momentOfIntertia;
    // }
}