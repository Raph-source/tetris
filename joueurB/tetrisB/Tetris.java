package tetrisB;

import java.net.Socket;

import javax.swing.JFrame;

public class Tetris {
    public static void main(String[] args) {
        Socket socket = null;
        JFrame fenetre = new JFrame("Tetris B");
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetre.setResizable(false);

        //ETABLIR LA CONNEXION ENTRE LE DEUX JOUEUR
        try{
            socket = new Socket("localhost", 1100);
        }
        catch(Exception e){
            e.printStackTrace();
        }

        //ajout du panneau de jeu à la fenètre
        PanneauJeu pj = new PanneauJeu(socket);
        fenetre.add(pj);
        fenetre.pack();
        
        fenetre.setLocationRelativeTo(null);
        fenetre.setVisible(true);
        
        //demarer avec le premier mino
        // pj.getMessage();        
        // pj.getMessage();
        // pj.startPremierMino();        
        // pj.startPremierMino();
        
        pj.lancerJeu();

        // try{
        //     Thread.sleep(2000);
        // }
        // catch(Exception e){
        //     e.printStackTrace();
        // }
        
        pj.actualiser();

        fenetre.setLocationRelativeTo(null);
        fenetre.setVisible(true);
    }
}