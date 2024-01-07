package tetrisA;

import javax.swing.JFrame;

import tetrisB.PanneauJeu;

public class Tetris {
    public static void main(String[] args) {
        JFrame fenetre  = new JFrame("Tetris");
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetre.setResizable(false);
        
        //ajout du panneau de jeu à la fenètre
        PanneauJeu pj = new PanneauJeu();
        fenetre.add(pj);
        fenetre.pack();

        pj.lancerJeu();
        
        fenetre.setLocationRelativeTo(null);
        fenetre.setVisible(true);
        
        
    }
}