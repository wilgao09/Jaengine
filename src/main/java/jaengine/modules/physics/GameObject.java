package jaengine.modules.physics;

import java.util.ArrayList;
import jaengine.math.*;
import java.util.HashMap;


public class GameObject extends Node<GameObject>{
    protected Vector2D geometricCenter;
    protected Environment environment;
    protected String name;
    // protected ArrayList<GameAttribute> attributes = new ArrayList<GameAttribute>();
    private HashMap<String,GameAttribute> attributes = new HashMap<String,GameAttribute>();

    public GameObject(String name, Vector2D location) {
        this.geometricCenter = location;
        super.setData(this);
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
        for (int n = 0; n < super.children.size(); n++) {   
            toR += "\n \\_" + super.children.get(n).toString();
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
}