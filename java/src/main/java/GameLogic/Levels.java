package GameLogic;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

import main.Ventana;

public class Levels extends JComponent{

    private static final HashMap<Integer, Point[]> correlationPathLevel = new HashMap<Integer, Point[]>() {
        {

            //  Al haber 3 niveles unicamente y con un camino ya previamente diseñado
            //  se tiene la posición de las coordenadas para autoinstanciar un camino para que los gatos puedan caminar.

            put(0 , new Point[] {   new Point(0, 415),
                                        new Point(215,415),
                                        new Point(225,200),
                                        new Point(500,200),
                                        new Point(500,490),
                                        new Point(860, 500),
                                        new Point(870,360),
                                        new Point(1316, 342)
                                    }); // Nivel 1 (Neighborhood)

            put(1 , new Point[] {   new Point(0,0),
                                        new Point(0,0),
                                        new Point(0,0),
                                        new Point(0,0)
                                    }); // Nivel 2 ()
                                    
            put(2 , new Point[] {   new Point(0,0),
                                        new Point(0,0),
                                        new Point(0,0),
                                        new Point(0,0)
                                    }); // Nivel 3 (Hell)
            
        }
    };

    private Path wayPath;
    private Image backgroundLevel;
    private int level;

    public Levels(int Level) {
        this.level = Level;
        this.wayPath = new Path(correlationPathLevel.get(Level));
        this.backgroundLevel = new ImageIcon("java/src/main/resources/Levels/PruebaNivel1.jpg").getImage();
        
        
        // setBounds(0, 0, Ventana.WIDTH, Ventana.HEIGHT);
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(backgroundLevel, 0, 0,null);
        super.paint(g);
    }

    public void startLevel() throws Exception{


        switch(level){
            case 0:
                Cats gatoNuevo = new Cats(0, wayPath);
                add(gatoNuevo);
                Gameplay.catsInMap.add(gatoNuevo);
                break;
            case 1:
                break;
            case 2:
                break;
            default:
                throw new Exception("Nivel no existente");
        }

        
    }



    

    
}
