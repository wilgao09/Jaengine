package jaengine.modules.graphics;

import jaengine.logic.Vector2D;

import java.util.HashMap;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

public class Map {
    private HashMap<Object, Node> nodes = new HashMap<Object, Node>();
    private Color[] colors = new Color[] {
        Color.RED,
        Color.CYAN,
        Color.GREEN,
        Color.ORANGE,
        Color.YELLOW,
        Color.GRAY,
        Color.LIME,
        Color.DODGERBLUE
    };
    Graphics graphics;
    public Map(Graphics g) { graphics = g;}

    //precondition the object is in the hashmap
    public void apply(Object obj, Vector2D translate, Vector2D rotate) {
        if (nodes.containsKey(obj)) {
            Node n = nodes.get(obj);
            n.setTranslateX(n.getTranslateX() + translate.x());
            n.setTranslateY(n.getTranslateY() + translate.y());
            //makeshift debug on screen
            //mark(n.getTranslateX() + translate.x(), n.getTranslateY() + translate.y(), 3);
            n.setRotate(n.getRotate() + rotate.y() * 180 / 3.141592);
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

    public void mark(double x,double y, int color) {
        Rectangle r = new Rectangle();
        r.setWidth(2);
        r.setHeight(2);
        r.setFill(colors[color]);

        r.setX(x);
        r.setY(y);

        graphics.group.getChildren().add(r);


    }

}