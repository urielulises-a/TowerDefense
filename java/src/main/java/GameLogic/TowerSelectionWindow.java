package GameLogic;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class TowerSelectionWindow extends JFrame {

    private JButton[] listOfCharactersToPlay;
    private ArrayList<Integer> indexSelected;

        public static boolean CharactersSelected = false; // Variable de control para no empezar el nivel hasta que se hayan
                                                          // seleccionado los personajes.

    public TowerSelectionWindow(int level) {
        listOfCharactersToPlay = new JButton[14]; // 14 personajes jugables
        indexSelected = new ArrayList<Integer>();

        setTitle("Selección de Torres");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new GridLayout(3, 5));

        for (int i = 0; i < listOfCharactersToPlay.length; i++) {
            listOfCharactersToPlay[i] = createCharacterButton(i);
            add(listOfCharactersToPlay[i]);
        }

        pack();
        setSize(500, 500);
        setLocationRelativeTo(null);
        setVisible(true);

        JOptionPane.showMessageDialog(this, "Selecciona tus personajes a usar *LIMITE: 4 PERSONAJES, ESCOGE BIEN*.");
        
    }

    private JButton createCharacterButton(int characterIndex) {
        JButton button = new JButton(new ImageIcon(new ImageIcon(Dogs.getDogImagePath(characterIndex))
                .getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT)));

        button.addActionListener(new ActionListener() {
            private boolean isSelected = false;

            @Override
            public void actionPerformed(ActionEvent e) {
                isSelected = !isSelected;

                if (isSelected) {
                    indexSelected.add(characterIndex);
                    button.setBackground(Color.GREEN);
                } else {
                    indexSelected.remove(Integer.valueOf(characterIndex));
                    button.setBackground(null);
                }

                if (indexSelected.size() >= 4) {
                    JOptionPane.showMessageDialog(getOwner(), "Has seleccionado los personajes requeridos.");
                    CharactersSelected = true;
                }
            }
        });

        return button;
    }

    public ArrayList<Integer> getSelectedCharacters() {
        return indexSelected;
    }
}
