package main;
import GameLogic.Gameplay;

import javax.swing.*;
import java.awt.*;

public class Ventana extends JPanel {

    public final static int WIDTH = 1360;
    public final static int HEIGHT = 768;
    public Menu menu = new Menu();
    private static JFrame frame;

    public Ventana(JFrame frame) {

        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        Ventana.frame = frame;

        add(menu);
        //add(newGameplay);
        setLayout(null);

    }
    public static void handleClick(){

        if (Menu.buttonStart.isClicked()){
            newScene();
        }

    }
    public static void newScene(){
        
        
        JFrame jf = new JFrame("");
        Gameplay newGameplay = new Gameplay();
        jf.add(newGameplay);
        jf.pack();
        jf.setVisible(true);
        jf.setResizable(false);
        jf.setSize(WIDTH, HEIGHT);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setLayout(null);
        newGameplay.run();


        frame.dispose();
    }
}
