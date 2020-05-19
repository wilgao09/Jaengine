package jaengine.logic;

import java.util.ArrayList;
import java.util.HashMap;

public class Intersections {
    public static Vector2D areIntersecting(Vector2D starta, Vector2D enda, Vector2D startb, Vector2D endb) {
        double dya = enda.y()-starta.y();
        double dyb = endb.y()-startb.y();
        double dxa = enda.x()-starta.x();
        double dxb = endb.x()-startb.x();

        double specialy = starta.y()-startb.y();
        double specialx = startb.x()-starta.x();

        double denom = dxa * dyb - dxb * dya;

        double tha = ( dxb * specialy + dyb * specialx ) / denom;
        double thb = ( dya * specialx + dxa * specialy ) / denom;

        if (tha <= 1 && tha >= 0 && thb <= 1 && thb >= 0) {
            return new Vector2D(starta.x() + dxa*tha, starta.y() + dya*tha); //this is the point of intersection
        } else {
            return null;
        }
    }

    //given two closed loop shapes, do they intersect?
    //if they do, return a vector where forces should be applied
    public static Vector2D findCritPoints(Vector2D[] shape1, Vector2D[] shape2) {
        // ArrayList<Vector2D> crit1 = new ArrayList<Vector2D>();
        // ArrayList<Vector2D> crit2 = new ArrayList<Vector2D>();
        Vector2D crit = new Vector2D(0,0);
        HashMap<Vector2D,Integer> s1suspects = new HashMap<Vector2D,Integer>();
        HashMap<Vector2D,Integer> s2suspects = new HashMap<Vector2D,Integer>();
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
                if (point != null) {
                    crit.add(point);
                    additions++;
                    if (s1suspects.containsKey(starta)) {
                        s1suspects.put(starta,s1suspects.get(starta)+1);
                    }
                    if (s1suspects.containsKey(enda)) {
                        s1suspects.put(enda,s1suspects.get(enda)+1);
                    }
                    if (s2suspects.containsKey(startb)) {
                        s2suspects.put(startb,s2suspects.get(startb)+1);
                    }
                    if (s2suspects.containsKey(endb)) {
                        s2suspects.put(endb,s2suspects.get(endb)+1);
                    }
                }
            }
        }
        return crit.scale(1.0/additions); //returns average vector
    }
}