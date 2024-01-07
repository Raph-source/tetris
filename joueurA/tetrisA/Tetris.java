package tetrisA;

import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;

public class Tetris {
    public static void main(String[] args) {
        JFrame fenetre  = new JFrame("Tetris");
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetre.setResizable(false);
        
        //ajout du panneau de jeu à la fenètre
        PanneauJeu pj = new PanneauJeu();
        fenetre.add(pj);
        fenetre.pack();

        System.out.println("entente du joueur B");
        try{
            ServerSocket serveur = new ServerSocket(1100);
            Socket socket = serveur.accept();
            pj.socket = socket; 
            System.out.println("joueur B connecté");
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
        pj.lancerJeu();
        
        fenetre.setLocationRelativeTo(null);
        fenetre.setVisible(true);
        
        
    }
}