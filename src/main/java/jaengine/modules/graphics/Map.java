package jaengine.modules.graphics;

import jaengine.logic.Vector2D;

import java.util.HashMap;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

/**
 * Contains all the functions required to keep track of important onscreen elements
 */
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
    /**
     * Create a new Map to keep track of elements
     * @param g A reference to the graphics module that created this. 
     */
    public Map(Graphics g) { graphics = g;}

    //precondition the object is in the hashmap
    /**
     * Precondition: the object has already been added to the hashmap of objects
     * Translate and roate an object based on a translation and rotation vector. 
     * @param obj The object to be displaced
     * @param translate A Vector2D that determines how much the element should be dispalced
     * @param rotate A Vector2D that determiens how much the object should rotate; The rotation amount is in radians and stored in the y component
     */
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
    /**
     * Add an object to this class's lsit of important objects.
     * @param obj Object representation of the object to keep track of
     * @param points The points that represent the object; the points must form a closed loop polygon.
     */
    public void addObject(Object obj, double[] points) {
        Polygon p = new Polygon(points);
        p.setFill(Color.WHITE);

        graphics.group.getChildren().add(p);
        nodes.put(obj, p);

    }
    /**
     * Make a mark on the screen. The mark is not added to the hashmap of important elements
     * @param x x coordinate for the mark
     * @param y y coordinate for the mark
     * @param color an integer corresponding to an index in the color array defined in this class. Mst be between 0 and 8 inclusive. 
     */
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