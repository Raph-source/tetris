package tetrisB;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;

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

    //ecouteur des touches
    private EcouteurToucheA ecouteurToucheA;    
    private EcouteurToucheB ecouteurToucheB;

    //le socket
    private Socket socket;

    public PanneauJeu(Socket socket){
        this.socket = socket;

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
        
        //permet de lancer les premier mino
        int i = 0;
        //lancer le game loop
        while(this.threadJeu != null){
            currentTime = System.nanoTime();
            deltat += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;
            if(deltat > 1){
                this.getMessage();                
                this.getMessage();

                if(i == 0){
                    this.mjA.premierMino();        
                    this.mjB.premierMino();
                }
                i++;
                repaint();
                deltat--;
            }
        }
    }

    public void actualiser(){
        //game loop
        double drawInterval = 1000000000 / this.FPS;
        double deltat = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        
        //lancer le game loop
        while(true){
            currentTime = System.nanoTime();
            deltat += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;
            if(deltat > 1){
                if(this.ecouteurToucheA.pause == false && this.mjA.gameOver == false){//il faut ajouter le teste du game du deuxieme joueur
                    this.mjA.actualiser();
                    this.mjB.actualiser();
                }
                deltat--;
            }
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;
        mjA.dessiner(g2);
        mjB.dessiner(g2);
    } 

    //generer un tetra mino
    public void getMessage(){

        mino.joueurA.Mino minoJoueurA = null;        
        mino.joueurB.Mino minoJoueurB = null;
        char x;

        try{
            BufferedReader getMessage = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            x = getMessage.readLine().charAt(0);

            if(x == '0' || x == '1' || x == '2' || x == '3' || x == '4' || x == '5' || x == '6'){
                switch (x) {
                    case '0': {
                        minoJoueurA = new mino.joueurA.Mino_l1(this.ecouteurToucheA);                
                        minoJoueurB = new mino.joueurB.Mino_l1(this.ecouteurToucheB);
                        System.out.println("teste");
                        break;
                    }            
                    case '1': {
                        minoJoueurA = new mino.joueurA.Mino_l2(this.ecouteurToucheA);                
                        minoJoueurB = new mino.joueurB.Mino_l2(this.ecouteurToucheB);
                        System.out.println("teste");
                        break;
                    }
                    case '2': {
                        minoJoueurA = new mino.joueurA.Mino_t(this.ecouteurToucheA);                
                        minoJoueurB = new mino.joueurB.Mino_t(this.ecouteurToucheB);
                        System.out.println("teste");
                        break;
                    }
                    case '3': {
                        minoJoueurA = new mino.joueurA.Mino_z1(this.ecouteurToucheA);                
                        minoJoueurB = new mino.joueurB.Mino_z1(this.ecouteurToucheB);
                        System.out.println("teste");
                        break;
                    }
                    case '4': {
                        minoJoueurA = new mino.joueurA.Mino_z2(this.ecouteurToucheA);                
                        minoJoueurB = new mino.joueurB.Mino_z2(this.ecouteurToucheB);
                        System.out.println("teste");
                        break;
                    }
                    case '5': {
                        minoJoueurA = new mino.joueurA.Mino_carre(this.ecouteurToucheA);                
                        minoJoueurB = new mino.joueurB.Mino_carre(this.ecouteurToucheB);
                        System.out.println("teste");
                        break;
                    }
                    case '6': {
                        minoJoueurA = new mino.joueurA.Mino_bar(this.ecouteurToucheA);                
                        minoJoueurB = new mino.joueurB.Mino_bar(this.ecouteurToucheB);
                        System.out.println("teste");
                        break;
                    }
                }

                this.mjA.fileMino.add(minoJoueurA);        
                this.mjB.fileMino.add(minoJoueurB);
            }
            
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }
}
