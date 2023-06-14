package main;
import GameLogic.UserMenu;
import com.ecodeup.jdbc.DataBase;

import javax.swing.*;

import java.awt.CardLayout;

public class Main extends JFrame {

    private JPanel cardPanel;
    private CardLayout cardLayout;
    private UserMenu userMenu;
    private Ventana ventana;
    private DataBase dataBase;

    public Main() {
        cardPanel = new JPanel();
        cardLayout = new CardLayout();
        cardPanel.setLayout(cardLayout);

        dataBase = new DataBase();
        userMenu = new UserMenu();
        ventana = new Ventana(this);

        cardPanel.add(userMenu, "UserMenu");
        cardPanel.add(ventana, "Ventana");

        add(cardPanel);
        setSize(Ventana.WIDTH, Ventana.HEIGHT);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        userMenu.addActionListener(e -> {
            String input = userMenu.getInputText();

            System.out.println("Dato ingresado: " + input);
            userMenu.clearInputText();  //
            cardLayout.show(cardPanel, "Ventana");
            ventana.requestFocus();
            dataBase.startData(input);
        });

    }

    public static void main(String[] args) {
        Main main = new Main();
        main.setVisible(true);
    }
}
