package tetris;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.Random;

import mino.*;

public class ManageurJeu {
    //Block du jouer A
    final int LARGEUR = 360;
    final int  HAUTEUR = 600;
    public static int left_x;    
    public static int right_x;
    public static int top_y;
    public static int botton_y;

    //mino courant
    Mino minoCourant;
    final int MINO_START_X;    
    final int MINO_START_Y;
    //mino suivant
    Mino minoSuivant;
    final int MINOSUIVANT_X;    
    final int MINOSUIVANT_Y;
    public static ArrayList<Block> blocksMinoPrecedant = new ArrayList<>();

    public static int dropInterval = 60;

    public ManageurJeu(){
        left_x = (PanneauJeu.LARGEUR / 2) - (LARGEUR / 2);
        right_x = left_x + LARGEUR;
        top_y = 50;
        botton_y = top_y + HAUTEUR;

        MINO_START_X = left_x + (LARGEUR / 2) - Block.TAILLE;        
        MINO_START_Y = top_y + Block.TAILLE;

        MINOSUIVANT_X = right_x + 175;        
        MINOSUIVANT_Y = top_y + 500;

        //le tetra mino courant
        minoCourant = this.genererMino();
        minoCourant.setXY(MINO_START_X, MINO_START_Y);
        //le tetra mino suivant
        minoSuivant = this.genererMino();
        minoSuivant.setXY(MINOSUIVANT_X, MINOSUIVANT_Y);

    }

    public void actualiser(){
        if(this.minoCourant.active == false){
            //stocker le tetra mino courant dans le tableu
            blocksMinoPrecedant.add(this.minoCourant.block[0]);            
            blocksMinoPrecedant.add(this.minoCourant.block[1]);
            blocksMinoPrecedant.add(this.minoCourant.block[2]);
            blocksMinoPrecedant.add(this.minoCourant.block[3]);

            this.minoCourant.desactivationEnCours = false;

            //changer de tetra mino courant
            this.minoCourant = this.minoSuivant;
            this.minoCourant.setXY(MINO_START_X, MINO_START_Y);

            //generer un autre tetra mino suivant
            this.minoSuivant = this.genererMino();
            this.minoSuivant.setXY(MINOSUIVANT_X, MINOSUIVANT_Y);

            //vérifier si les lignes sont supprimables
            this.checkDelete();
        }
        else{
            minoCourant.actualiser();
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
        if(minoCourant != null){
            minoCourant.dessiner(g2);
        }

        //dessiner le mino suivant
        minoSuivant.dessiner(g2);
        
        //dessiner les blocks static
        for(int i = 0; i < blocksMinoPrecedant.size(); i++){
            blocksMinoPrecedant.get(i).dessiner(g2);
        }

        //afficher pause
        g2.setColor(Color.yellow);
        g2.setFont(g2.getFont().deriveFont(50f));
        if(EcouterTouche.pause){
            x = left_x + 70;
            y = top_y + 320;
            g2.drawString("PAUSE", x, y);
        }

    }

    //generer un tetra mino
    private Mino genererMino(){
        Mino mino = null;

        int num = new Random().nextInt(7);

        switch (num) {
            case 0: mino = new Mino_l1();break;            
            case 1: mino = new Mino_l2();break;
            case 2: mino = new Mino_t();break;
            case 3: mino = new Mino_z1();break;
            case 4: mino = new Mino_z2();break;
            case 5: mino = new Mino_carre();break;
            case 6: mino = new Mino_bar();break;
        }

        return mino;
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
