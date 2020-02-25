package jaengine.modules.physics;


import jaengine.math.*;
import java.util.ArrayList;

public class GameObject extends Node<GameObject>{
    protected Vector2D geometricCenter;
    protected ArrayList<GameAttribute> attributes = new ArrayList<GameAttribute>();

    public GameObject(Vector2D location) {
        this.geometricCenter = location;
    }
}