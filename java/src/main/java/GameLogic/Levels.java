package GameLogic;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.util.HashMap;


import javax.swing.ImageIcon;
import javax.swing.JComponent;

import main.Ventana;


public class Levels extends JComponent{

    private final HashMap<Integer, Point[]> correlationPathLevel = new HashMap<Integer, Point[]>() {
        {

            //  Al haber 3 niveles unicamente y con un camino ya previamente diseñado
            //  se tiene la posición de las coordenadas para autoinstanciar un camino para que los gatos puedan caminar.

            put(0 , new Point[] {   new Point(0, 240),
                                        new Point(250,250),
                                        new Point(270,460),
                                        new Point(360,550),
                                        new Point(900,550),
                                        new Point(930, 515),
                                        new Point(955,300),
                                        new Point(1230, 0)
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

    private final Path wayPath;
    private Image backgroundLevel;
    private int level;
    private int countOfCycles;

    public Levels(int Level) {
        this.countOfCycles = 0;
        this.level = Level;
        this.wayPath = new Path(correlationPathLevel.get(Level));
        this.backgroundLevel = new ImageIcon("java/src/main/resources/Levels/Nivel1.png").getImage();
        
        setBounds(0, 0, Ventana.WIDTH, Ventana.HEIGHT);
        setVisible(true);
        setEnabled(true);
        setLayout(null);
        
        
    }

   @Override
   protected void paintComponent(Graphics g) {
       g.drawImage(backgroundLevel, 0, 0,null);
   }

    public void startLevel(){

        if(countOfCycles == 0 || countOfCycles == 500){

            Cats newCat = new Cats(0, wayPath);
            countOfCycles = 1;
        }else{
            countOfCycles++;
        }

            
    }



    

    
}
