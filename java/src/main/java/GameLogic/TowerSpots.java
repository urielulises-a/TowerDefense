package GameLogic;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TowerSpots {

    public static Map<Integer, ArrayList<HashMap<Point, Boolean>>> correlationTowersSpotAvailable = new HashMap<Integer, ArrayList<HashMap<Point, Boolean>>>() {
        {
            put(0, new ArrayList<HashMap<Point, Boolean>>() {   // Spots para torres del nivel 1 (Neighborhood)
                {
                    add(new HashMap<Point, Boolean>() {{    put(new Point(385, 200), true);     }});
                    add(new HashMap<Point, Boolean>() {{    put(new Point(190, 310), true);     }});
                    add(new HashMap<Point, Boolean>() {{    put(new Point(540, 690), true);     }});
                    add(new HashMap<Point, Boolean>() {{    put(new Point(440, 450), true);     }});
                    add(new HashMap<Point, Boolean>() {{    put(new Point(750, 690), true);     }});
                    add(new HashMap<Point, Boolean>() {{    put(new Point(850, 460), true);     }});
                    add(new HashMap<Point, Boolean>() {{    put(new Point(1055, 590), true);    }});
                    add(new HashMap<Point, Boolean>() {{    put(new Point(1010, 120), true);    }});
                }
            });
            put(1, new ArrayList<HashMap<Point, Boolean>>() {   //  Spots para torres del nivel 2 ()
                {
                    add(new HashMap<Point, Boolean>() {{    put(new Point(0, 0), true); }});
                    add(new HashMap<Point, Boolean>() {{    put(new Point(0, 0), true); }});
                    add(new HashMap<Point, Boolean>() {{    put(new Point(0, 0), true); }});
                    add(new HashMap<Point, Boolean>() {{    put(new Point(0, 0), true); }});
                    add(new HashMap<Point, Boolean>() {{    put(new Point(0, 0), true); }});
                    add(new HashMap<Point, Boolean>() {{    put(new Point(0, 0), true); }});
                    add(new HashMap<Point, Boolean>() {{    put(new Point(0, 0), true); }});
                    add(new HashMap<Point, Boolean>() {{    put(new Point(0, 0), true); }});
                }
            });
            put(2, new ArrayList<HashMap<Point, Boolean>>() {   // Spots para torres del nivel 3 
                {
                    add(new HashMap<Point, Boolean>() {{    put(new Point(0, 0), true); }});
                    add(new HashMap<Point, Boolean>() {{    put(new Point(0, 0), true); }});
                    add(new HashMap<Point, Boolean>() {{    put(new Point(0, 0), true); }});
                    add(new HashMap<Point, Boolean>() {{    put(new Point(0, 0), true); }});
                    add(new HashMap<Point, Boolean>() {{    put(new Point(0, 0), true); }});
                    add(new HashMap<Point, Boolean>() {{    put(new Point(0, 0), true); }});
                    add(new HashMap<Point, Boolean>() {{    put(new Point(0, 0), true); }});
                    add(new HashMap<Point, Boolean>() {{    put(new Point(0, 0), true); }});
                }
            });
        }
    };

    public static boolean isSpotAvailable(int level, Point point) {
        ArrayList<HashMap<Point, Boolean>> spots = correlationTowersSpotAvailable.get(level);
        for (HashMap<Point, Boolean> spot : spots) {
            Boolean isAvailable = spot.get(point);
            if (isAvailable != null && isAvailable) {
                return true; // El punto está disponible para instanciar una torre
            }
        }
        return false; // El punto no está disponible
    }

    public static void changeSpotAvailability(int level, Point point, boolean isAvailable) {
        ArrayList<HashMap<Point, Boolean>> spots = correlationTowersSpotAvailable.get(level);

        Point closesPoint = getPointInRange(level, point);

        for (HashMap<Point, Boolean> spot : spots) {
            if (isInSpotRange(level, point)) {
                spot.replace(closesPoint, spot.get(closesPoint), isAvailable);
            }
        }
    }

    // La funcion al estar relacionada con un circulo de radio 90, se usara esa
    // medida para indicar si esta en rango de algun punto o no
    public static boolean isInSpotRange(int level, Point pointToCompare) {
        if(pointToCompare == null){
            return false;
        }
        ArrayList<HashMap<Point, Boolean>> spots = TowerSpots.correlationTowersSpotAvailable.get(level);
        for (HashMap<Point, Boolean> spot : spots) {
            for (Point point : spot.keySet()) {
                if (pointToCompare.getX() >= point.getX() - 45 && pointToCompare.getX() <= point.getX() + 45
                 && pointToCompare.getY() >= point.getY() - 45 && pointToCompare.getY() <= point.getY() + 45) {
                    return true;
                }
            }
        }
        return false;
    }

    public static Point getPointInRange(int level, Point pointToCompare){
        ArrayList<HashMap<Point, Boolean>> spots = TowerSpots.correlationTowersSpotAvailable.get(level);
        for (HashMap<Point, Boolean> spot : spots) {
            for (Point point : spot.keySet()) {

                if (pointToCompare.getX() >= point.getX() - 45 && pointToCompare.getX() <= point.getX() + 45
                 && pointToCompare.getY() >= point.getY() - 45 && pointToCompare.getY() <= point.getY() + 45) {
                    return point;
                }

            }
        }
        return null;
    }
}
