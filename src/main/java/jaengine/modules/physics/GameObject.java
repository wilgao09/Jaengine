package jaengine.modules.physics;

import javafx.geometry.Point3D;
import java.util.ArrayList;

public class GameObject {
    private Point3D geometricCenter;
    private ArrayList<GameAttribute> attributes = new ArrayList<GameAttribute>();

    public GameObject(Point3D location) {
        this.geometricCenter = location;
    }
}