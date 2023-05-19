package GameLogic;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JComponent;
import javax.swing.JFrame;

import main.Ventana;

public class Gameplay extends JComponent {

    public static ArrayList<Dogs> dogsInMap; // Variable para saber los perros que hay en juego
    public static ArrayList<Cats> catsInMap; // Variable para saber los gatos que hay en juego \

    private Levels nivel; // Variable en la que se instancia el nivel a jugar (Path para los gatos y los
                          // gatos a salir.)

    private Timer runLevel;


    public Gameplay() {
        dogsInMap = new ArrayList<Dogs>();
        catsInMap = new ArrayList<Cats>();
        nivel = new Levels(0);
        runLevel = new Timer("Run Level Timer");

        setPreferredSize(new Dimension(Ventana.WIDTH, Ventana.HEIGHT));
        setLayout(null);
    }

    @Override
    public void paint(Graphics g) {

        nivel.paintComponent(g);

        for (Cats cats : catsInMap) {
            cats.paintComponent(g);
        }
    }

    public void run() {

        runLevel.schedule(new TimerTask() {
            @Override
            public void run() {
                nivel.startLevel();
                for (Cats cats : catsInMap) {
                    cats.run();
                }

                catsInMap.removeIf(cats -> !cats.isVisible());
                repaint();
            }
        }, 0,10);

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
