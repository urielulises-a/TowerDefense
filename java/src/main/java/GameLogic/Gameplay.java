package GameLogic;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Gameplay extends JPanel{

    public static ArrayList<Dogs> dogsInMap;    // Variable para saber los perros que hay en juego 
    public static ArrayList<Cats> catsInMap;    // Variable para saber los gatos que hay en juego  

    
    
    public Gameplay() {
        dogsInMap = new ArrayList<Dogs>();
        catsInMap = new ArrayList<Cats>();
        setLayout(null);
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("Prueba");
        
    }





    
    
}
