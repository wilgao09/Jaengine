package jaengine.physics;

import jaengine.logic.Vector2D;
import jaengine.modules.physics.*;

public class Block extends GameObject{
    // private boolean canFall = false;
    public Block(String myName, Vector2D position, double width, double height) {
        super(myName, position);
        Mesh m = new Mesh();
        m.setMesh("box",new double[]{width,height});
        RigidBody  rb = new RigidBody();
        Hitbox hb = new Hitbox("box", new double[]{width, height});
        super.addAttribute(m);
        super.addAttribute(rb);
    }
}
