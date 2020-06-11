package jaengine.modules.physics;

import jaengine.logic.*;
import java.util.HashMap;


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

    

    public HashMap<String,GameAttribute> attributes() {
        return attributes;
    }
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

    public Environment getEnvironment() {
        return environment;
    }
    public void addChild(GameObject g) {
        super.addChild(g);
        if (this.environment != null) {
            g.initialize(this.environment);
        } else {
            g.initialize(this.environment);
        }
    }

    public String stringValue() {
        return this.name;
    }

    public String toString() {
        String toR = this.name ;
        for (int n = 0; n != children.size(); n++) {   
            toR += children.get(n).toString(1);
        }
        return toR;
    }

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

    public String getName() {
        return this.name;
    }

    public boolean addAttribute(GameAttribute a) {
        if (this.attributes.containsKey(a.name)) {
            return false;
        }
        this.attributes.put(a.name,a);
        return true;
    }

    public GameAttribute getAttribute(String n) {
        if (this.attributes.containsKey(n)) {
            return this.attributes.get(n);
        }
        return null;
    }

    public boolean hasAttribute(String n) {
        GameAttribute g = this.attributes.get(n);
        if (g == null || !g.active) {
            return false;
        } 
        return true;
    }

    public void step(Vector2D step) {
        this.location= this.location.add(step);
    }
    public void spin(Vector2D spin) {
        this.rotation= this.rotation.add(spin);
    }

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

    public void deactivateAttribute(String attributeName) {
        if (this.hasAttribute(attributeName)) {
            this.getAttribute(attributeName).deactivate();
        }
    }

    public void activateAttribute(String attributeName) {
        if (this.hasAttribute(attributeName)) {
            this.getAttribute(attributeName).activate();
        }
    }


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



    public double getMass() {
        return mass;
    }
    public double getRotInertia() {
        return this.momentOfInertia;
    }
    public void setMass(double mass) {
        this.mass = mass;
    }
    public void setRotInert(double rotInert) {
        this.momentOfInertia=rotInert;
    }
    public class EffectiveSystem {
        private double mass = 1;
        private double rotationalInertia = 1;
        private Vector2D COM = new Vector2D(0,0);
        private double radius = 1;

        public void step(Vector2D v) { COM = COM.add(v); }
    }
    public double getSysMass() {return sys.mass;}
    public double getSysRotInert() {return sys.rotationalInertia;}
    //dont get COM; COM is only at some instant in time and is meaningless beteen frames; just recalculate it if necessary
    public Vector2D getSysCOM() {return sys.COM;}
    public double getSysRadius() {return sys.radius;}

    public double checkSysMass() {
        double sum = this.mass;
        for (Node<GameObject> child : super.children) {
            sum += child.getData().checkSysMass();
            
        }
        this.sys.mass = sum;
        return sum;
    }

    public Vector2D checkSysCOM() {
        Vector2D loc = this.location.scale(this.mass);
        for (Node<GameObject> child: super.children) {
            loc = loc.add(child.getData().checkSysCOM()).scale(child.getData().getSysMass());
        }
        loc = loc.scale(1/this.sys.mass);
        this.sys.COM = loc;
        return loc;
    }

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

    public void checkSelf() {
        checkSysMass();
        checkSysCOM();
        checkSysRotInert();
        checkSysRadius();
    }

    public GameObject getRBAncestor() {
        if (this.hasAttribute("RigidBody")) return this;
        Node<GameObject> focus = this.getParent();
        while (!focus.getData().getName().equals("Environment")) {
            if (focus.getData().hasAttribute("RigidBody")) return focus.getData();
        }
        return null;
    }

    public Vector2D[] getPoints() {
        // System.out.println(this.location);
        Vector2D[] pointList = ((Hitbox)this.getAttribute("Hitbox")).transformPoints(this.location, this.rotation.y());
        return pointList;

    }


    /**\
     * Precondition: the object is in an environment
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