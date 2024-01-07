package tetris;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import mino.*;
import mino.joueurA.EcouteurToucheA;
import mino.joueurA.Mino;

public class ManageurJeuA {
    //Block du jouer A
    final int LARGEUR = 360;
    final int  HAUTEUR = 600;
    public static int left_x;    
    public static int right_x;
    public static int top_y;
    public static int botton_y;
    
    //ecouteur des touoches
    private EcouteurToucheA ecouteurToucheA;

    //la file de tetra mino
    public Queue<Mino> fileMino;
    
    //mino courant
    public Mino minoCourant;
    final int MINO_START_X;    
    final int MINO_START_Y;

    //mino suivant
    public Mino minoSuivant;
    final int MINOSUIVANT_X;    
    final int MINOSUIVANT_Y;
    public static ArrayList<Block> blocksMinoPrecedant = new ArrayList<>();

    public static int dropInterval = 60;

    //effect
    boolean conteurEffectOn;
    int conteurEffect;
    ArrayList<Integer> effectY = new ArrayList<>();

    //game over
    boolean gameOver;

    public ManageurJeuA(EcouteurToucheA ecouteurToucheA){
        left_x = PanneauJeu.LARGEUR - 1200;
        right_x = left_x + LARGEUR;
        top_y = 50;
        botton_y = top_y + HAUTEUR;

        MINO_START_X = left_x + (LARGEUR / 2) - Block.TAILLE;        
        MINO_START_Y = top_y + Block.TAILLE;

        MINOSUIVANT_X = right_x + 175;        
        MINOSUIVANT_Y = top_y + 500;

        //la file de tetra mino
        this.fileMino = new LinkedList<>();

        //ecouteur des touches
        this.ecouteurToucheA = ecouteurToucheA;

    }

    //premier tetra mino
    public void premierMino(){

        //le tetra mino courant     
        this.minoCourant = this.fileMino.poll();
        /*
            {
                envoyer le mino courant au jouer B
            }
        */

        this.minoCourant.setXY(MINO_START_X, MINO_START_Y);
        //le tetra mino suivant
        this.minoSuivant = this.fileMino.poll();;
        this.minoSuivant.setXY(MINOSUIVANT_X, MINOSUIVANT_Y);
    }

    public void actualiser(){
        if(this.minoCourant.active == false){
            //stocker le tetra mino courant dans le tableu
            blocksMinoPrecedant.add(this.minoCourant.block[0]);            
            blocksMinoPrecedant.add(this.minoCourant.block[1]);
            blocksMinoPrecedant.add(this.minoCourant.block[2]);
            blocksMinoPrecedant.add(this.minoCourant.block[3]);

            //si game over
            if(this.minoCourant.block[0].x == MINO_START_X && this.minoCourant.block[0].y == MINO_START_Y){
                gameOver = true;
            }
            this.minoCourant.desactivationEnCours = false;

            //changer de tetra mino courant
            this.minoCourant = this.minoSuivant;
            /*
                {
                    envoyer le mino courant au jouer B
                }
            */
            this.minoCourant.setXY(MINO_START_X, MINO_START_Y);

            //generer un autre tetra mino suivant
            this.minoSuivant = this.fileMino.poll();
            this.minoSuivant.setXY(MINOSUIVANT_X, MINOSUIVANT_Y);

            //vérifier si les lignes sont supprimables
            this.checkDelete();
        }
        else{
            this.minoCourant.actualiser();
        }
    }

    public void dessiner(Graphics2D g2){
        //dessiner la partie du joueur A
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(4f));
        g2.drawRect(left_x - 4, top_y - 4, LARGEUR + 8, HAUTEUR + 8);

        //cadre du tetra mino suivant
        int x = right_x + 100;        
        int y = botton_y - 200;
        g2.drawRect(x, y, 200, 200);
        g2.setFont(new Font("Arial", Font.PLAIN, 30));
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.drawString("SUIVANT", x + 60, y + 60);

        //dessiner le mino courant
        if(this.minoCourant != null){
            this.minoCourant.dessiner(g2);
        }

        //dessiner le mino suivant
        minoSuivant.dessiner(g2);
        
        //dessiner les blocks static
        for(int i = 0; i < blocksMinoPrecedant.size(); i++){
            blocksMinoPrecedant.get(i).dessiner(g2);
        }

        //dessiner les game over
        if(gameOver){
            g2.setColor(Color.red);
            g2.setFont(g2.getFont().deriveFont(50f));

            x = left_x + 25;
            y = top_y + 320;
            g2.drawString("GAME OVER", x, y);
        }
        //afficher pause
        else if(this.ecouteurToucheA.pause){
            g2.setColor(Color.yellow);
            g2.setFont(g2.getFont().deriveFont(50f));

            x = left_x + 70;
            y = top_y + 320;
            g2.drawString("PAUSE", x, y);
        }

        //dessiner l'effect l'hors de la suppression d'une ligne
        if(conteurEffectOn){
            conteurEffect++;

            g2.setColor(Color.white);
            for(int i = 0; i < effectY.size(); i++){
                g2.fillRect(left_x, effectY.get(i), LARGEUR, Block.TAILLE );
            }

            if(conteurEffect == 10){
                conteurEffectOn = false;
                conteurEffect = 0;
                effectY.clear();
            }
        }

    }

    private void checkDelete(){
        int x = left_x;
        int y = top_y;
        int nombreBlocks = 0;

        while(x < right_x && y < botton_y){
            //conpter les nombre des block sur la ligne
            for(int i = 0; i < blocksMinoPrecedant.size(); i++){
                if(blocksMinoPrecedant.get(i).x == x && blocksMinoPrecedant.get(i).y == y){
                    nombreBlocks++;
                }
            }
            x += Block.TAILLE;

            if(x == right_x){
                
                if(nombreBlocks == 12){
                    //effect de la suppression de la ligne
                    conteurEffectOn = true;
                    effectY.add(y);

                    //effacer la ligne
                    for(int i = blocksMinoPrecedant.size() - 1; i >= 0; i--){
                        if(blocksMinoPrecedant.get(i).y == y){
                            blocksMinoPrecedant.remove(i); 
                        }
                    }

                    //faire descendre tout les blocks
                    for(int i = 0; i < blocksMinoPrecedant.size(); i++){
                        if(blocksMinoPrecedant.get(i).y < y){
                            blocksMinoPrecedant.get(i).y += Block.TAILLE;
                        }
                    }
                }

                

                nombreBlocks = 0;
                x = left_x;
                y += Block.TAILLE;
            }
        }
    }
}
