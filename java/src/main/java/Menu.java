import javax.swing.*;

public class Menu extends JComponent {
    private JUButton botonPlay;
    public Menu(){
        this.botonPlay = new JUButton("", 100, 100);
        this.setBounds(0,0,Ventana.WIDTH, Ventana.HEIGHT);
        // Instanciamiento boton play
        add(botonPlay);

        opciones();
    }

    private void opciones(){
        if(botonPlay.clicked){
            JOptionPane.showMessageDialog(null, "¡Cuidado! Esto es una advertencia.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }



}
