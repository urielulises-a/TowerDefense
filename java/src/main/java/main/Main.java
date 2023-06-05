package main;

import com.ecodeup.jdbc.DataBase;

import javax.swing.*;


public class Main extends JFrame {

    Ventana ventana = new Ventana(this);
    public DataBase dataBase = new DataBase();

    public Main(){

        add(ventana);
        pack();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        dataBase.startData();

    }

    public static void main(String[] args) {

        Main main = new Main();
    }
}