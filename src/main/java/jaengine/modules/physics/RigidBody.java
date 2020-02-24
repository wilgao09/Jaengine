package jaengine.modules.physics;

import javafx.geometry.Point3D;

public class RigidBody extends GameAttribute{
    private Point3D velocity;
    public RigidBody() {
        super("RigidBody",true);
    }
}