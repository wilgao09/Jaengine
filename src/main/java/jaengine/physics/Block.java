package jaengine.physics;

import jaengine.math.Vector2D;
import jaengine.modules.physics.*;

public class Block extends GameObject{

    public Block(String myName, Vector2D position, double width, double height) {
        super(myName, position);
        Mesh m = new Mesh();
        m.setMesh("box",new double[]{width,height});
    }
}
