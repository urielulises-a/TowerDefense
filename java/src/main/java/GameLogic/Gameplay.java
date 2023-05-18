package GameLogic;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import main.Ventana;

public class Gameplay extends JComponent{

    public static ArrayList<Dogs> dogsInMap;    // Variable para saber los perros que hay en juego 
    public static ArrayList<Cats> catsInMap;    // Variable para saber los gatos que hay en juego  \

    private Levels nivel;                       // Variable en la que se instancia el nivel a jugar (Path para los gatos y los gatos a salir.)

    
    
    public Gameplay() {
        dogsInMap = new ArrayList<Dogs>();
        catsInMap = new ArrayList<Cats>();
        nivel = new Levels(0);
        
        add(nivel);

        setPreferredSize(new Dimension(Ventana.WIDTH, Ventana.HEIGHT));
        setLayout(null);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        nivel.paintComponent(g);

        for (Cats cats : catsInMap) {
            cats.paintComponent(g);
        }
        for (Dogs dogs : dogsInMap) {
            dogs.paintComponent(g);
        }

    }

    public void run() throws Exception{
        while(true){
            
            nivel.startLevel();
            
            Thread.sleep(100);
            for (Cats cats : catsInMap) {
                cats.update();
            }
            for (Dogs dogs : dogsInMap) {
                dogs.update();
            }

            repaint();
        }
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("Prueba");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        
        Gameplay nuevoGameplay = new Gameplay();
        
        frame.add(nuevoGameplay);
        
        frame.pack();        
        frame.setVisible(true);

        try {
            nuevoGameplay.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }





    
    
}
