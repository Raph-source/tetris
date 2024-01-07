package mino;

import java.awt.*;

public class Block extends Rectangle{
    public int x, y;
    public static final int TAILLE = 30;
    public Color couleur;

    public Block(Color couleur){
        this.couleur = couleur;
    }

    //dessiner un block
     public void dessiner(Graphics2D g2){
        int marge = 2;
        g2.setColor(this.couleur);
        g2.fillRect(x + 2, y + 2, TAILLE - (marge * 2), TAILLE - (marge * 2));
     }
}
