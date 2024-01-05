package tetris;

import java.awt.*;
import javax.swing.*;

import mino.EcouterTouche;

public class PanneauJeu extends JPanel implements Runnable{
    public static final int LARGEUR = 1280;    
    public static final int HAUTEUR = 720;
    final int FPS = 60; // nombre de fois par seconde
    Thread threadJeu;
    ManageurJeu mj;

    public PanneauJeu(){
        this.setPreferredSize(new Dimension(LARGEUR, HAUTEUR));
        this.setBackground(Color.black);
        this.setLayout(null);

        //l'ecouteur des touches
        this.addKeyListener(new EcouterTouche());
        this.setFocusable(true);

        mj = new ManageurJeu();
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
        if(EcouterTouche.pause == false && mj.gameOver == false){
            mj.actualiser();
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;
        mj.dessiner(g2);
    }
}
