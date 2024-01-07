package tetrisA;

import java.awt.*;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.*;

import mino.joueurA.EcouteurToucheA;
import mino.joueurB.EcouteurToucheB;

public class PanneauJeu extends JPanel implements Runnable{
    public static final int LARGEUR = 1280;    
    public static final int HAUTEUR = 720;
    final int FPS = 60; // nombre de fois par seconde
    Thread threadJeu;
    private ManageurJeuA mjA;
    private ManageurJeuB mjB;
    private GenerateurMino generateurMino;

    //ecouteur des touches
    private EcouteurToucheA ecouteurToucheA;    
    private EcouteurToucheB ecouteurToucheB;

    //le socket
    private ServerSocket serveur;
    private Socket socket;

    public PanneauJeu(){
        this.setPreferredSize(new Dimension(LARGEUR, HAUTEUR));
        this.setBackground(Color.black);
        this.setLayout(null);

        //ecouteur de touches
        this.ecouteurToucheA = new EcouteurToucheA();        
        this.ecouteurToucheB = new EcouteurToucheB();

        //l'ecouteur des touches
        this.addKeyListener(this.ecouteurToucheA);        
        this.addKeyListener(this.ecouteurToucheB);

        this.setFocusable(true);
        
        //les manageurs de jeu
        this.mjA = new ManageurJeuA(this.ecouteurToucheA);        
        this.mjB = new ManageurJeuB(this.ecouteurToucheB);

        //le générateur de tetra mino
        this.generateurMino = new GenerateurMino(this.mjA, this.mjB, this.ecouteurToucheA, this.ecouteurToucheB);

        //générer les premiers tetra mino
        this.generateurMino.genererMino();            
        this.generateurMino.genererMino();

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
        
        //ETABLIR LA CONNEXION ENTRE LE DEUX JOUEUR
        try{
            this.serveur = new ServerSocket(1100);
            this.socket = serveur.accept();
        }
        catch(Exception e){
            e.printStackTrace();
        }

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
        if(this.ecouteurToucheA.pause == false && this.mjA.gameOver == false){//il faut ajouter le teste du game du deuxieme joueur
            mjA.actualiser();
            mjB.actualiser();
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
        mjB.dessiner(g2);
    }
}
