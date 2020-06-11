package jaengine.physics;

import jaengine.logic.Vector2D;
import jaengine.modules.physics.*;

/**
 * A user-friendly, easy to produce, block
 */
public class Block extends GameObject{
    // private boolean canFall = false;
    /**
     * Create a new block
     * @param myName the name of the block
     * @param width the width of the block
     * @param height the height of the block
     */
    public Block(String myName,  double width, double height) {
        super(myName);
        Mesh m = new Mesh();
        m.setMesh("box",new double[]{width,height});
        RigidBody  rb = new RigidBody();
        Hitbox hb = new Hitbox("box", new double[]{width, height});
        hb.setUnitVectorBorder(new Vector2D[]{
            new Vector2D(0,-1),
            new Vector2D(1,0),
            new Vector2D(0,1),
            new Vector2D(-1,0)
        });
        super.addAttribute(m);
        super.addAttribute(rb);
        super.addAttribute(hb);

        super.radius=Math.sqrt(width * width + height * height)/2;
        System.out.println("RADIUS " + super.radius);

    }
    // public void applyDensity(double density) { //mass per square elngth
        
    // }
}
