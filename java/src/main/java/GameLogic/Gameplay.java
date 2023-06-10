package GameLogic;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JComponent;
import javax.swing.JOptionPane;

import main.Ventana;

public class Gameplay extends JComponent {

    public static ArrayList<Dogs> dogsInMap;            // Variable para saber los perros que hay en juego
    public static ArrayList<Cats> catsInMap;            // Variable para saber los gatos que hay en juego
    public static ArrayList<Bullet> bulletsInMap;       // Variable para saber las municiones que hay en juego
    private Levels nivel;                               // Variable en la que se instancia el nivel a jugar (Path para los gatos y los
                                                        // gatos a salir.)
    private Timer runLevel;                             // Timer para renovar el gamplay a 60 FPS
    private TowerSelectionWindow TSW;                   // Seleccion de perros de inicio
    private boolean panelCreated = false;               // Variable de control 


    public Gameplay() {

        dogsInMap       = new ArrayList<>();
        catsInMap       = new ArrayList<>();
        bulletsInMap    = new ArrayList<>();
        nivel = new Levels(0);
        runLevel = new Timer("Run Level Timer");

        setPreferredSize(new Dimension(Ventana.WIDTH, Ventana.HEIGHT));
        setLayout(null);
        
        TSW = new TowerSelectionWindow(0);

        add(nivel);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        for (Cats cats : catsInMap) {
            cats.paintComponent(g);
        }
        for (Dogs dogs : dogsInMap) {
            dogs.paintComponent(g);
        }
        for (Bullet bullets : bulletsInMap) {
            bullets.paintComponent(g);
        }
    }

    public void run() {
        nivel.startLevel();

        runLevel.schedule(new TimerTask() {
            @Override
            public void run() {
                if (Levels.CharactersSelected) {

                    if(!panelCreated){
                        nivel.setIndexSelectedCharacters(TSW.getSelectedCharacters());
                        TSW.dispose();
                        panelCreated = true;
                    }

                    for (Cats cats : catsInMap) {
                        cats.run();
                    }
                    for (Dogs dogs : dogsInMap) {
                        dogs.update();
                    }
                    for (Bullet bullets : bulletsInMap) {
                        bullets.update();
                    }

                    catsInMap.removeIf(cats -> !cats.isVisible());
                    repaint();
                }
                TSW.toFront();
                
            }
        }, 0, 60);

        JOptionPane.showMessageDialog(null, "Selecciona tus personajes a usar *LIMITE: 4 PERSONAJES, ESCOGE BIEN*.");
        
    }

    

}
