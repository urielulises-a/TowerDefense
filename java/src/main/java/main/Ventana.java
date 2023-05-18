package main;
import javax.swing.*;
import java.awt.*;

public class Ventana extends JPanel {

    public final static int WIDTH = 1360;
    public final static int HEIGHT = 768;
    public Menu menu = new Menu();
    
    public Ventana() {

        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        add(menu);

        setLayout(null);

    }
}
