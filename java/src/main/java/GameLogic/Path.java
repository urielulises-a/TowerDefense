package GameLogic;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


public final class Path {

    private final HashMap<Integer, ArrayList<Point> > correlationPathLevel = new HashMap<Integer, ArrayList<Point>>() {{

        //  Al haber 3 niveles unicamente y con un camino ya previamente diseñado
        //  se tiene la posición de las coordenadas para autoinstanciar un camino para que los gatos puedan caminar.

        put(0 , new ArrayList<Point>(Arrays.asList(
                    
                    new Point(0, 165),
                    new Point(245,160),
                    new Point(260,430),
                    new Point(410,520),
                    new Point(850,530),
                    new Point(935, 465),
                    new Point(960,300),
                    new Point(1230, 0)
        )));// )); // Nivel 1 (Neighborhood)

        put(1 , new ArrayList<Point>(Arrays.asList(   
                                    new Point(0,0),
                                    new Point(0,0),
                                    new Point(0,0),
                                    new Point(0,0)
                                ))); // Nivel 2 ()
                                
        put(2 , new ArrayList<Point>(Arrays.asList(  
                                    new Point(0,0),
                                    new Point(0,0),
                                    new Point(0,0),
                                    new Point(0,0)
                                ))); // Nivel 3 (Hell)
        
    }
};

    private final ArrayList<Point> points;

    public Path(int Level){
        points = correlationPathLevel.get(Level) ;
    }

    @Override
    public String toString() {

        String toString = new String();
        for (Point point : points) {
            toString = toString + point + '\n';
        }
        return toString;
    }

    public Point getPosition(int index) {
        if (index >= 0 && index < points.size()) {
            return points.get(index);
        }
        return null;
    }

    public Point getFirst(){
        return points.get(0);
    }

    public int getLength(){
        return points.size();
    }


}

