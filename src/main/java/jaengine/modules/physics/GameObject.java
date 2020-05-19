package jaengine.modules.physics;

import jaengine.logic.*;
import java.util.HashMap;


public class GameObject extends Node<GameObject>{
    protected Vector2D location= new Vector2D(0,0);
    protected Vector2D rotation= new Vector2D(0,0);
    // private Vector2D center;
    protected Environment environment;
    protected String name;

    protected double radius = 1;

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


    public boolean isIntersecting(GameObject o) {
        // double dist = (o.location.add(this.location.reverse())).magnitude();
        // double required =  o.radius + this.radius;
        // System.out.println ("a distance of " + dist + "m; hit is " + required);
        return (o.location.add(this.location.reverse())).magnitude() <= o.radius + this.radius;
    }
}