package jaengine.modules.physics;

import jaengine.logic.*;
import java.util.HashMap;


public class GameObject extends Node<GameObject>{
    protected Vector2D location= new Vector2D(0,0);
    private Vector2D center;
    protected Environment environment;
    protected String name;

    private HashMap<String,GameAttribute> attributes = new HashMap<String,GameAttribute>();

    public GameObject(String name, Vector2D location) {
        this.name = name;
        this.location= location;
        super.setData(this);
    }

    public GameObject(String name, Vector2D[] verticies) {
        this.name = name;
        super.setData(this);
    }

    public GameObject(String name, Vector2D location, Vector2D[] verticies) {
        this.name = name;
        this.location= location;
    }

    

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
        super.children.add(g);
        if (this.environment != null) {
            g.initialize(this.environment);
        }
    }

    public String stringValue() {
        return this.name;
    }

    public String toString() {
        String toR = this.name ;
        for (int n = 0; 
            n != children.size(); 
            n++) {   
            toR += "\n \\_" + children.get(n).toString();
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
        return this.attributes.containsKey(n);
    }

    public void step(Vector2D step) {
        this.location= this.location.add(step);
    }

    public void setVelocity(Vector2D v) {
        if (this.hasAttribute("RigidBody")) {
            ((RigidBody)(this.getAttribute("RigidBody"))).setVelocity(v);
        }
    }
}