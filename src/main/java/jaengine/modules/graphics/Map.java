package jaengine.modules.graphics;

import jaengine.logic.Vector2D;

import java.util.HashMap;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Map {
    private HashMap<Object, Node> nodes = new HashMap<Object, Node>();
    Graphics graphics;
    public Map(Graphics g) { graphics = g;}

    //precondition the object is in the hashmap
    public void apply(Object obj, Vector2D translate, Vector2D rotate) {
        if (nodes.containsKey(obj)) {
            Node n = nodes.get(obj);
            n.setTranslateX(n.getTranslateX() + translate.x());
            n.setTranslateY(n.getTranslateY() + translate.y());
        }
    }
    // public void addBox(Object obj, double w, double h) {
    //     Rectangle r = ShapeBuilder.createBox(w, h); 
    //     graphics.group.getChildren().add(r);
    //     nodes.put(obj, r);
    // }

    public void addObject(Object obj, double[] points) {
        Polygon p = new Polygon(points);
        p.setFill(Color.WHITE);

        graphics.group.getChildren().add(p);
        nodes.put(obj, p);

    }

}