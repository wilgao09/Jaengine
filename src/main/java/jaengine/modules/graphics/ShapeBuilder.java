package jaengine.modules.graphics;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class ShapeBuilder {
    public static Rectangle createBox(double w, double h) { //generic white box
        Rectangle r = new Rectangle(w, h);
        r.setFill(Color.WHITE);
        r.setX(-w/2);
        r.setY(-h/2);
        return r;
    }

    public static void setIntoWorld(Shape n) {
        n.setFill(Color.WHITE);
    }
}