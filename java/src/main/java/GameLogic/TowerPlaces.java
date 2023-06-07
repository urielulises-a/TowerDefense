package GameLogic;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class TowerPlaces {
    private final HashMap<Integer, ArrayList<Point> > correlationMapPointsOfTowers = new HashMap<Integer, ArrayList<Point>>() {{

        //  Al haber 3 niveles unicamente se conoce los lugares donde se puede o no instanciar una torre
        //  se tiene la posición de las coordenadas para verificar que el lugar donde se quiere instanciar una torre es posible.

        put(0 , new ArrayList<Point>(Arrays.asList(
                    new Point(385, 200),
                    new Point(190,310),
                    new Point(540,690),
                    new Point(440, 450),
                    new Point(750, 690),
                    new Point(850, 460),
                    new Point(1055, 590),
                    new Point(1010, 120)
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
        
        }};

    private final ArrayList<Point> pointsToUse;

    public TowerPlaces(int Level){
        pointsToUse = correlationMapPointsOfTowers.get(Level);
    }

    


}
