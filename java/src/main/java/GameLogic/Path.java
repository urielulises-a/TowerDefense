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
                                
        put(2, new ArrayList<Point>(Arrays.asList( 
                    new Point(145,0),
                    new Point(160, 240),
                    new Point(305,  265),
                    new Point(300,425),
                    new Point(100, 425),
                    new Point(80, 600),
                    new Point(465, 620),
                    new Point(495, 320),
                    new Point(650, 315),
                    new Point(650, 520),
                    new Point(590, 535),
                    new Point(580, 710),
                    new Point(815, 715),
                    new Point(810,535),
                    new Point(755, 525),
                    new Point(745, 315),
                    new Point(900, 320),
                    new Point(930,615),
                    new Point(1120, 630), 
                    new Point(1320, 600),
                    new Point(1325, 510),
                    new Point(1310, 430),
                    new Point(1095, 425),
                    new Point(1090,  265), 
                    new Point(1235, 240),
                    new Point(1255, 130),
                    new Point(1255, 0)
)));

//Nivel 3 (Hell)
        
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

