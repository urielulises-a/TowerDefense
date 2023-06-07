package GameLogic;


import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JComponent;
import javax.swing.JOptionPane;



import main.Ventana;

public class Gameplay extends JComponent{

    public static ArrayList<Dogs> dogsInMap;                // Variable para saber los perros que hay en juego
    public static ArrayList<Cats> catsInMap;                // Variable para saber los gatos que hay en juego \

    private Levels nivel;                                   // Variable en la que se instancia el nivel a jugar (Path para los gatos y los
                                                            // gatos a salir.)
    private Timer runLevel;                                 // Timer para renovar el gamplay a 60 FPS
    private TowerSelectionWindow TSW;                       // Seleccion de perros de inicio

    public static boolean CharactersSelected = false;       // Variable de control para no empezar el nivel hasta que se hayan seleccionado los personajes.
    private boolean panelCreated = false;
    private ArrayList<Integer> IndexSelectedCharacters;     // Array que contiene los personajes a usar por nivel.
    private PanelPlayableCharacters PPC;                    // Panel para creacion de nuevas torres dentro del gamplay.

    public Gameplay() {

        dogsInMap = new ArrayList<>();
        catsInMap = new ArrayList<>();
        nivel = new Levels(0);
        runLevel = new Timer("Run Level Timer");

        setPreferredSize(new Dimension(Ventana.WIDTH, Ventana.HEIGHT));
        setLayout(null);

        TSW = new TowerSelectionWindow(0);
        
    
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        nivel.paintComponent(g);

        for (Cats cats : catsInMap) {
            cats.paintComponent(g);
        }
    }

    public void run() {
        

        runLevel.schedule(new TimerTask() {
            @Override
            public void run() {
                if (CharactersSelected) {
                    if(!panelCreated){
                        IndexSelectedCharacters = TSW.getSelectedCharacters();
                        PPC = new PanelPlayableCharacters(IndexSelectedCharacters);
                        
                        TSW.dispose();
                        add(PPC);
                        panelCreated = true;
                    }
                    
                    nivel.startLevel();
                    for (Cats cats : catsInMap) {
                        cats.run();
                    }

                    catsInMap.removeIf(cats -> !cats.isVisible());
                    repaint();
                }
            }
        }, 0, 60);

        JOptionPane.showMessageDialog(null, "Selecciona tus personajes a usar *LIMITE: 4 PERSONAJES, ESCOGE BIEN*.");
        TSW.toFront();
        
    }

}
