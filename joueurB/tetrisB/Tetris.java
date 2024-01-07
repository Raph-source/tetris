package tetrisB;

import java.net.Socket;

import javax.swing.JFrame;

import tetrisA.PanneauJeu;

public class Tetris {
    public static void main(String[] args) {
        JFrame fenetre  = new JFrame("Tetris");
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetre.setResizable(false);
        
        //ajout du panneau de jeu à la fenètre
        PanneauJeu pj = new PanneauJeu();
        fenetre.add(pj);
        fenetre.pack();

        //ETABLIR LA CONNEXION ENTRE LE DEUX JOUEUR
        try{
            Socket socket = new Socket("localhost", 1100);
            pj.socket = socket;
        }
        catch(Exception e){
            e.printStackTrace();
        }

        pj.lancerJeu();
        
        fenetre.setLocationRelativeTo(null);
        fenetre.setVisible(true);
        
        
    }
}