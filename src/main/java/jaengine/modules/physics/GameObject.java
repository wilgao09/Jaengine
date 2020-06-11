package jaengine.modules.physics;

import jaengine.logic.*;
import java.util.HashMap;

/**
 * The GameObject class represents GameoBjects that can be in an envionment. It inherits from Node<GameObject>, so it benefits from being both a node and being itself. 
 */
public class GameObject extends Node<GameObject>{
    protected Vector2D rotation= new Vector2D(0,0);
    // private Vector2D center;
    protected Environment environment;
    protected String name;

    protected double radius = 1;

    protected Vector2D location= new Vector2D(0,0);
    private double mass = 1;
    private double momentOfInertia = 1;
    private EffectiveSystem sys = new EffectiveSystem();

    private HashMap<String,GameAttribute> attributes = new HashMap<String,GameAttribute>();
    /**
     * Create a new GameObject
     * @param name the name fo this GameObject
     */
    public GameObject(String name) {
        this.name = name;
        // this.location= location;
        super.setData(this);
    }

    // public GameObject(String name, Vector2D[] verticies) {
    //     this.name = name;
    //     super.setData(this);
    // }

    // public GameObject(String name, Vector2D location, Vector2D[] verticies) {
    //     this.name = name;
    //     this.location= location;
    // }

    
    /**
     * Returns a hashmap of GameAttributes 
     * @return a hasmap of attributes(key name, value attribute))
     */
    public HashMap<String,GameAttribute> attributes() {
        return attributes;
    }
    /**
     * Initialize this GameObject in an envionment
     * @param e The envionemnt to add this GameoBject to
     * @return A boolean value that tells whether or not the object was successfully added
     */
    public boolean initialize(Environment e) {
        if (environment == null) {
            environment = e;
            for (Node<GameObject> g : super.children) {
                (g.getData()).initialize(e);
            }
            //add attributes here
            return true;
        }
        return false;
    }
    /**
     * The environment this Object belongs to
     * @return
     */
    public Environment getEnvironment() {
        return environment;
    }
    /**
     * Adds a GameObject as a child of this 
     * @param g the child to add
     */
    public void addChild(GameObject g) {
        super.addChild(g);
        if (this.environment != null) {
            g.initialize(this.environment);
        } else {
            g.initialize(this.environment);
        }
    }
    /**
     * The string represnetation of this GameObject
     * @return a String representing this object
     */
    public String stringValue() {
        return this.name;
    }
    /**
     * @return a string
     */
    public String toString() {
        String toR = this.name ;
        for (int n = 0; n != children.size(); n++) {   
            toR += children.get(n).toString(1);
        }
        return toR;
    }
    /**
     * Returns a string rperesentation with special indentation and symbols to accurate represent the parent child relationship
     * @return a string representation
     */
    public String toString( int i) {
        String mod = "";
        for (int n = 0 ;n != i; n++) {
            mod += "  ";
        }
        String toR = "\n" + mod + "\\_" + this.name ;
        for (int n = 0; n != children.size(); n++) {   
            
            toR += children.get(n).toString(i+1);
        }
        return toR;
    }
    /**
     * Returns the anme fo this object
     * @return the name of this object
     */
    public String getName() {
        return this.name;
    }
    /**
     * Add an attribute to this object. It must be the only of its kind
     * @param a The attribute to add
     * @return A boolean value telling its success in adding
     */
    public boolean addAttribute(GameAttribute a) {
        if (this.attributes.containsKey(a.name)) {
            return false;
        }
        this.attributes.put(a.name,a);
        return true;
    }
    /**
     * Retrieve an attribute by string if it exists
     * @param n the name of the attribute
     * @return the attribute specified; if it does not exist, return null
     */
    public GameAttribute getAttribute(String n) {
        if (this.attributes.containsKey(n)) {
            return this.attributes.get(n);
        }
        return null;
    }
    /**
     * Determiens if this GameObject has an attribute 
     * @param n The attribute in question
     * @return A boolean telling if it has or does not have the attribute
     */
    public boolean hasAttribute(String n) {
        GameAttribute g = this.attributes.get(n);
        if (g == null || !g.active) {
            return false;
        } 
        return true;
    }
    /**
     * change this object's location by a vector amount
     * @param step the amount to displace by
     */
    public void step(Vector2D step) {
        this.location= this.location.add(step);
    }
    /**
     * Rotate this GameObject by a vector amount. Can be interpreted as a radian rotate, where the radian rotation is in the vector's y component
     * @param spin the amount to rotate by
     */
    public void spin(Vector2D spin) {
        this.rotation= this.rotation.add(spin);
    }
    /**
     * An easy way of setting this object's physics. Does nothing if this object is not a RigidBody owner
     * @param name The name of the physical variable. The only supported values are "velocity", and "angular_velocity"
     * @param params the information required to modify the physics
     */
    public void setPhysics(String name, Object[] params) {
        if (this.hasAttribute("RigidBody")) {
            RigidBody rb = (RigidBody)this.getAttribute("RigidBody");
            if (name.equals("velocity")) {
                rb.setVelocity((Vector2D)params[0]);
            } else if(name.equals("angular_velocity")) {
                rb.setAngVel((Vector2D)params[0]);
            } else {
                System.out.println("tried to set a nonexistent variable");
            }
        }
    }
    /**
     * Deactivate an attribute by name
     * @param attributeName the attribute to deactivate
     */
    public void deactivateAttribute(String attributeName) {
        if (this.hasAttribute(attributeName)) {
            this.getAttribute(attributeName).deactivate();
        }
    }   
    /**
     * Activate an attribute by name
     * @param attributeName the attribute to activate
     */
    public void activateAttribute(String attributeName) {
        if (this.hasAttribute(attributeName)) {
            this.getAttribute(attributeName).activate();
        }
    }

    /**
     * Determiens if this object is near another object by comapring radii
     * @param o The other object to check
     * @return a boolean value telling if the objects are near each other
     */
    public boolean isOverlapping(GameObject o) {
        // double dist = (o.location.add(this.location.reverse())).magnitude();
        // double required =  o.radius + this.radius;
        // System.out.println ("a distance of " + dist + "m; hit is " + required);
        double num1 = (this.getSysCOM().add(o.getSysCOM().reverse())).magnitude();
        double num2 = o.getSysRadius() + this.getSysRadius();
        // System.out.println("measured distance: " +num1);
        // System.out.println("required dist:" + num2);
        return num1 <= num2;
    }


    /**
     * Retiurns this object's mass
     * @return this mass
     */
    public double getMass() {
        return mass;
    }
    /**
     * Returns this object's rotation inertia
     * @return this rotational inertia
     */
    public double getRotInertia() {
        return this.momentOfInertia;
    }
    /**
     * Set this object's mass
     * @param mass The mass of this object
     */
    public void setMass(double mass) {
        this.mass = mass;
    }
    /**
     * Set this object's rotational inertia
     * @param mass The rotationla inertia of this object
     */
    public void setRotInert(double rotInert) {
        this.momentOfInertia=rotInert;
    }
    /**
     * Innerclass to store the system information. The system information incldues information about this objecta nd all fo its descendents. 
     */
    public class EffectiveSystem {
        private double mass = 1;
        private double rotationalInertia = 1;
        private Vector2D COM = new Vector2D(0,0);
        private double radius = 1;
        /**
         * Dispalce this system's COM. Note that this does not mean anything other than keep track of where it is
         * @param v the amount to displace by
         */
        public void step(Vector2D v) { COM = COM.add(v); }
    }
    /**
     * Return this system's last computed total mass
     * @return the total mass
     */
    public double getSysMass() {return sys.mass;}
    /**
     * Return this system's last computed rotation inertia
     * @return the rotational inertia
     */
    public double getSysRotInert() {return sys.rotationalInertia;}
    //dont get COM; COM is only at some instant in time and is meaningless beteen frames; just recalculate it if necessary
    /**
     * Return the last computed COM; this must be recomputed each frame
     * @return the location of the COM
     */
    public Vector2D getSysCOM() {return sys.COM;}
    /**
     * Return the last computed radius
     * @return the total radius
     */
    public double getSysRadius() {return sys.radius;}
    /**
     * Computes the total mass of the system
     * @return the total mass of it and all descendemtns
     */
    public double checkSysMass() {
        double sum = this.mass;
        for (Node<GameObject> child : super.children) {
            sum += child.getData().checkSysMass();
            
        }
        this.sys.mass = sum;
        return sum;
    }
    /**
     * Computes the center of mass 
     * @return the center of mass
     */
    public Vector2D checkSysCOM() {
        Vector2D loc = this.location.scale(this.mass);
        for (Node<GameObject> child: super.children) {
            loc = loc.add(child.getData().checkSysCOM()).scale(child.getData().getSysMass());
        }
        loc = loc.scale(1/this.sys.mass);
        this.sys.COM = loc;
        return loc;
    }
    /**
     * Computes the rotational inertia of the system; does so by repeated parallel axis theorem
     * @return the rotational inertia
     */
    public double checkSysRotInert() {
        Vector2D effCOM = this.sys.COM;
        double nInert = this.momentOfInertia + this.mass * this.location.add(effCOM.reverse()).dotProduct(this.location.add(effCOM.reverse()));

        for (Node<GameObject> child : super.children) {
            GameObject.EffectiveSystem ec = child.getData().sys;
            Vector2D D = ec.COM.add(effCOM.reverse());
            nInert += ec.rotationalInertia + ec.mass * D.dotProduct(D);
        }
        this.sys.rotationalInertia = nInert;
        return nInert;
    }
    /**
     * Computes the radius of the system; the radius of the system is the AABB of a circle
     * @return the radius
     */
    public double checkSysRadius() {
        //find the object w the furthest location from COM and the largest radius
        //score by sum
        double largest = this.radius;
        for (Node<GameObject> child : super.children){
            GameObject g = child.getData();
            double dist = g.getSysCOM().add(this.getSysCOM().reverse()).magnitude() + g.checkSysRadius();
            // System.out.println( g.getName() + " COM " + g.getSysCOM() + ", " + this.getName() + " COM " + this.getSysCOM());
            // System.out.println("dist is " + (dist-g.getSysRadius()));
            if (dist > largest) largest = dist;
        }
        this.sys.radius = largest;
        System.out.println("radius found to be " + largest);
        return largest;
    }
    /**
     * Runs all the computations on the system
     */
    public void checkSelf() {
        checkSysMass();
        checkSysCOM();
        checkSysRotInert();
        checkSysRadius();
    }
    /**
     * Retrieve the nearest RigidBodied parent
     * @return returns the most recent ancestor that has a rigidbody. If it does not find a rigidbody before reaching environment , return null. if this is a rigidbody, return this
     */
    public GameObject getRBAncestor() {
        if (this.hasAttribute("RigidBody")) return this;
        Node<GameObject> focus = this.getParent();
        while (!focus.getData().getName().equals("Environment")) {
            if (focus.getData().hasAttribute("RigidBody")) return focus.getData();
        }
        return null;
    }
    /**
     * Return the points as seen on screen
     * @return the points after taking location and rotation into account
     */
    public Vector2D[] getPoints() {
        // System.out.println(this.location);
        Vector2D[] pointList = ((Hitbox)this.getAttribute("Hitbox")).transformPoints(this.location, this.rotation.y());
        return pointList;

    }


    /**\
     * Precondition: the object is in an environment
     * Applies a force on an object at a point in the world; this creates both a net COM force and a net torque
     * @param f the force to apply
     * @param worldPoint the point to apply the force at
     */
    public void applyForce(Vector2D f, Vector2D worldPoint) {
        if (f.dotProduct(f) == 0) return;
        if (!this.hasAttribute("RigidBody")) {
            GameObject a = this.getRBAncestor();
            if (a != null) this.getRBAncestor().applyForce(f, worldPoint);
        }
        RigidBody rb = ((RigidBody)this.getAttribute("RigidBody"));
        //at this point, COM should be computed and the object should not yet have moved
        Vector2D radius = this.getSysCOM();
        //System.out.println(radius);
        double dott = radius.dotProduct(f);
        double intoCOMPercent = Math.abs(radius.dotProduct(f)/(radius.magnitude()*f.magnitude()));
        //System.out.println("this percent into com: " + intoCOMPercent);
        Vector2D intoCOM = f.scale(intoCOMPercent);
        rb.addCOMForce(intoCOM);
        rb.addTorque(f.add(intoCOM.reverse()));
        //added forces
        //System.out.println(rb.getNetForce());
    }

    //disp is based on the system COM
    //this should work on the minumum required topmost object
    // public void moveSystem(Vector2D disp, Vector2D angDisp){
    //     //assume THIS has not MOVED yet
    //     this.step(disp);
    //     this.spin(angDisp);

    // }
}