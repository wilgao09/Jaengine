package jaengine.modules.physics;

import java.util.ArrayList;
import jaengine.math.*;
import java.util.ArrayList;


public class GameObject extends Node<GameObject>{
    protected Vector2D geometricCenter;
    protected Environment environment;
    protected String name;
    protected ArrayList<GameAttribute> attributes = new ArrayList<GameAttribute>();

    public GameObject(Vector2D location) {
        this.geometricCenter = location;
    }

    public ArrayList<GameAttribute> attributes() {
        return attributes;
    }
    public boolean initialize(Environment e) {
        if (environment == null) {
            environment = e;
            for (Node<GameObject> g : super.children) {
                ((GameObject)g).initialize(e);
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
}