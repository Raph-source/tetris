package tetrisA;

import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;

public class Tetris {
    public static void main(String[] args) {
        Socket socket = null;

        JFrame fenetre  = new JFrame("Tetris A");
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetre.setResizable(false);

        System.out.println("entente du joueur B");
        try{
            ServerSocket serveur = new ServerSocket(1100);
            socket = serveur.accept();
            System.out.println("joueur B connecté");
        }
        catch(Exception e){
            e.printStackTrace();
        }
        //ajout du panneau de jeu à la fenètre
        PanneauJeu pj = new PanneauJeu(socket);
        fenetre.add(pj);
        fenetre.pack();

        pj.lancerJeu();
        
        fenetre.setLocationRelativeTo(null);
        fenetre.setVisible(true);
        
        
    }
}