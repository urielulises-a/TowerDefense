package GameLogic;
import java.awt.Dimension;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JComponent;
import javax.swing.JFrame;

import main.Ventana;

public class Gameplay extends JComponent{

    public static ArrayList<Dogs> dogsInMap;    // Variable para saber los perros que hay en juego 
    public static ArrayList<Cats> catsInMap;    // Variable para saber los gatos que hay en juego  \

    private Levels nivel;                       // Variable en la que se instancia el nivel a jugar (Path para los gatos y los gatos a salir.)

    private Timer runLevel;

    
    
    public Gameplay() {
        dogsInMap = new ArrayList<Dogs>();
        catsInMap = new ArrayList<Cats>();
        nivel = new Levels(0);
        runLevel = new Timer("Run Level Timer");
        
        add(nivel);

        setPreferredSize(new Dimension(Ventana.WIDTH, Ventana.HEIGHT));
        setLayout(null);
    }



    public void run(){
        
        //nivel.startLevel();
        

        runLevel.schedule(new TimerTask() {
            
            @Override
            public void run() {
                Cats newCat = new Cats(0, nivel.getPath());
                catsInMap.add(newCat);
                add(newCat);
                setComponentZOrder(newCat, 0);

                for (Cats cats : catsInMap) {
                    if(cats.isVisible() == false){
                        remove(cats);
                    }
                }
                
                catsInMap.removeIf(Cats -> Cats.isVisible() == false);

                System.out.println(getComponentCount());

                
            }
            
        }, 1000,1000);

        
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("Prueba");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        
        Gameplay nuevoGameplay = new Gameplay();
        
        frame.add(nuevoGameplay);
        
        frame.pack();        
        frame.setVisible(true);
    
        nuevoGameplay.run();

        
    }





    
    
}
