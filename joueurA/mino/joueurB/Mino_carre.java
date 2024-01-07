package mino.joueurB;

import java.awt.Color;

import mino.Block;

public class Mino_carre extends Mino{
    public Mino_carre(EcouteurToucheB ecouteurToucheB){
        super(ecouteurToucheB);
        this.creer(Color.yellow);
    }

    public void setXY(int x, int y){
        this.block[0].x = x;        
        this.block[0].y = y;
        this.block[1].x = this.block[0].x;
        this.block[1].y = this.block[0].y + Block.TAILLE;
        this.block[2].x = this.block[0].x + Block.TAILLE;
        this.block[2].y = this.block[0].y;
        this.block[3].x = this.block[0].x + Block.TAILLE;
        this.block[3].y = this.block[0].y + Block.TAILLE;

    }

    public void getDirection1(){}    
    public void getDirection2(){}
    public void getDirection3(){}
    public void getDirection4(){}
}
