package main;
import com.ecodeup.jdbc.DataBase;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class Main extends JFrame {

    private JPanel cardPanel;
    private CardLayout cardLayout;
    private UserMenu userMenu;
    private Ventana ventana;
    private static DataBase dataBase;
    private AudioInputStream AIS;
    public static Clip mainMenuMusic;

    public Main() {

        try {
            AIS = AudioSystem.getAudioInputStream(new File("java/src/main/resources/Sounds/mainMenuMusic.wav"));
            mainMenuMusic =  AudioSystem.getClip();
            mainMenuMusic.open(AIS);

            // Establece el volumen del Clip (de 0.0 a 1.0)
            float volumen = 0.01f; // Volumen al 01%
            FloatControl control = (FloatControl) mainMenuMusic.getControl(FloatControl.Type.MASTER_GAIN);
            float dB = (float) (Math.log(volumen) / Math.log(10.0) * 20.0);
            control.setValue(dB);

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }

        mainMenuMusic.start();
        cardPanel = new JPanel();
        cardLayout = new CardLayout();
        cardPanel.setLayout(cardLayout);

        dataBase = new DataBase();
        userMenu = new UserMenu();
        ventana = new Ventana(this);

        cardPanel.add(userMenu, "UserMenu");
        cardPanel.add(ventana, "Ventana");
        
        add(cardPanel);
        setTitle("MithPets");
        setIconImage(new ImageIcon("java/src/main/resources/BackgroundMenu/Logo.png").getImage());
        setSize(Ventana.WIDTH, Ventana.HEIGHT);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        userMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = userMenu.getInputText();
                System.out.println(input);
                userMenu.clearInputText();
                cardLayout.show(cardPanel, "Ventana");
                ventana.requestFocus();
                dataBase.startData(input);
            }
        });

    }
    public static void main(String[] args) {
        Main main = new Main();
        main.setVisible(true);
    }
}

