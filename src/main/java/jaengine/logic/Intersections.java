package jaengine.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

/**
 * A general MAth class dedicated to finding the intersection between two line segments or two clsoed loop shapes composed of line segments. 
 */
public class Intersections {
    /**
     * Determine, if two line segments intersect, where they intersect. All lines must be in reference to the same point.
     * @param starta One end of first line segment 
     * @param enda Other end of first line segment
     * @param startb One end of second line segment
     * @param endb Other end of second line segment
     * @return A Vector2D that points to where they intersect, if they intersect. If they do not intersect, it returns null. The returned Vector2D uses the same reference point as the Vector2Ds that it was fed. 
     */
    public static Vector2D areIntersecting(Vector2D starta, Vector2D enda, Vector2D startb, Vector2D endb) {
        double dya = enda.y()-starta.y();
        double dyb = endb.y()-startb.y();
        double dxa = enda.x()-starta.x();
        double dxb = endb.x()-startb.x();
        // System.out.println(dya + " " + dyb + " " + dxa + " " + dxb);
        double specialy = starta.y()-startb.y();
        double specialx = startb.x()-starta.x();

        double denom = dxa * dyb - dxb * dya;

        double tha = ( dxb * specialy + dyb * specialx ) / denom;
        double thb = ( dya * specialx + dxa * specialy ) / denom;

        // System.out.println(tha + " " + thb);
        if (tha <= 1 && tha >= 0 && thb <= 1 && thb >= 0) {
            //misnomer variable names i guess
            return new Vector2D(starta.x() + dxa*thb, starta.y() + dya*thb); //this is the point of intersection
        } else {
            return null;
        }
    }

    //given two closed loop shapes, do they intersect?
    //if they do, return DATA
    /**
     * Find the "critical points" if two closed loop shapes intersect. This has no reason to be called outside of the Physics module.
     * @param shape1 An array of Vector2Ds; the lines are formed by reading one index and the index imemdiately after; the last index loops back to the first index. 
     * @param shape2 A second shape
     * @return If they intersect, return an Object array where index 0 refers to the average of the intersections, index 1 refers to a HashMap<integer,Integer> of lines involved in the intersection on shape1, and index 2 refers to a HashMap<Integer,Integer>  of liens invovled in the intersection on shape2
     */
    public static Object[] findCritPoints(Vector2D[] shape1, Vector2D[] shape2) {
        // ArrayList<Vector2D> crit1 = new ArrayList<Vector2D>();
        // ArrayList<Vector2D> crit2 = new ArrayList<Vector2D>();
        Vector2D crit = new Vector2D(0,0);
        HashMap<Integer,Integer> s1suspects = new HashMap<Integer,Integer>();
        HashMap<Integer,Integer> s2suspects = new HashMap<Integer,Integer>();
        int additions = 0;
        for (int s1 = 0; s1 != shape1.length; s1++) {
            for (int s2 = 0; s2 != shape2.length; s2++) {
                Vector2D starta = shape1[s1];
                Vector2D startb = shape2[s2];
                Vector2D enda;
                Vector2D endb;

                if (s1 == shape1.length-1) {
                    enda = shape1[0];
                } else {
                    enda = shape1[s1+1];
                }
                if (s2 == shape2.length-1) {
                    endb = shape2[0];
                } else {
                    endb = shape2[s2+1];
                }

                Vector2D point = areIntersecting(starta,enda, startb, endb);
                // System.out.println(point);
                if (point != null) {
                    crit = crit.add(point);
                    additions++;
                    if (s1suspects.containsKey(s1)) {
                        s1suspects.put(s1,s1suspects.get(s1)+1);
                    } else {
                        s1suspects.put(s1,1);
                    }
                    if (s2suspects.containsKey(s2)) {
                        s2suspects.put(s2,s2suspects.get(s2)+1);
                    } else {
                        s2suspects.put(s2,1);
                    }
                }
            }
        }
        if (additions == 0) return null;
        return new Object[]{
            crit.scale(1.0/additions),
            s1suspects,
            s2suspects
        };
    }

    public static void main(String[] args){
        Vector2D aI = areIntersecting(
            new Vector2D(670,-900), 
            new Vector2D(-888,70), 
            new Vector2D(-244,-988), 
            new Vector2D(1007,899));
        System.out.println(aI);
    }
}