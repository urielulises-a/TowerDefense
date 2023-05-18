import javax.swing.*;


public class Main extends JFrame {

    Ventana ventana = new Ventana();

    public Main(){

        add(ventana);
        pack();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        ventana.menu.opciones();

    }

    public static void main(String[] args) {

        Main main = new Main();
    }
}