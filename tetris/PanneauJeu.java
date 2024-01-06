package tetris;

import java.awt.*;
import javax.swing.*;

import mino.EcouterTouche;

public class PanneauJeu extends JPanel implements Runnable{
    public static final int LARGEUR = 1280;    
    public static final int HAUTEUR = 720;
    final int FPS = 60; // nombre de fois par seconde
    Thread threadJeu;
    ManageurJeuA mjA;
    ManageurJeuB mjB;
    GenerateurMino generateurMino;
    public PanneauJeu(){
        this.setPreferredSize(new Dimension(LARGEUR, HAUTEUR));
        this.setBackground(Color.black);
        this.setLayout(null);

        //l'ecouteur des touches
        this.addKeyListener(new EcouterTouche());
        this.setFocusable(true);
        
        //les manageurs de jeu
        this.mjA = new ManageurJeuA();        
        this.mjB = new ManageurJeuB();

        //le générateur de tetra mino
        generateurMino = new GenerateurMino(this.mjA, this.mjB);

        //générer les premiers tetra mino
        generateurMino.genererMino();            
        generateurMino.genererMino();

        this.mjA.premierMino();        
        this.mjB.premierMino();

    }

    public void lancerJeu(){
        this.threadJeu = new Thread(this);
        this.threadJeu.start();
    }

    @Override
    public void run() {
        //game loop
        double drawInterval = 1000000000 / this.FPS;
        double deltat = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        //tester la connexion entre les deux machines
        /*
            {
                A FAIRE
            }
        */
        
        //lancer le game loop
        while(this.threadJeu != null){
            currentTime = System.nanoTime();
            deltat += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if(deltat > 1){
                this.actualiser();
                repaint();
                deltat--;
            }
        }
    }

    public void actualiser(){
        if(EcouterTouche.pause == false && mjA.gameOver == false){//il faut ajouter le teste du game du deuxieme joueur
            mjA.actualiser();
        }

        if(mjA.minoCourant.active == false){
            generateurMino.genererMino();            
            generateurMino.genererMino();
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;
        mjA.dessiner(g2);
    }
}
