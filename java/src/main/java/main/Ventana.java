package main;
import GameLogic.Gameplay;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Ventana extends JPanel {

    public final static int WIDTH = 1360;
    public final static int HEIGHT = 768;
    public Menu menu = new Menu();
    public static Gameplay newGameplay = new Gameplay();
    
    public Ventana() {

        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        add(menu);
        add(newGameplay);
        setLayout(null);

    }
    public static void handleClick(){

        if (Menu.buttonStart.isClicked()){
//            remove(menu);
            newGameplay.run();
        }
    }
}
