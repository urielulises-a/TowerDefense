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
                    
                    new Point(0, 210),
                    new Point(290,210),
                    new Point(290,430),
                    new Point(340, 505),
                    new Point(400,560),
                    new Point(635, 560),
                    new Point(880,560),
                    new Point(980, 460),
                    new Point(1000,300),
                    new Point(1100, 190),
                    new Point(1190, 90),
                    new Point(1265, 0)
        )));// )); // Nivel 1 (Neighborhood)

        put(1 , new ArrayList<Point>(Arrays.asList(   
                    new Point(100,768),
                    new Point(165, 680),
                    new Point(270, 600),
                    new Point(400, 470),
                    new Point(480, 390),
                    new Point(540,330),
                    new Point(665,325),
                    new Point(745,375),
                    new Point(790, 600),
                    new Point(920, 660),
                    new Point(1050, 635),
                    new Point(1120,475),
                    new Point(1125, 335),
                    new Point(1060, 200),
                    new Point(1085, 75),
                    new Point(1220, 60),
                    new Point(1360, 65)
        ))); // Nivel 2 (Cemetery)
                                
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

