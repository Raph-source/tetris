package mino.joueurA;

import java.awt.Color;

import mino.Block;

public class Mino_t extends Mino{
    public Mino_t(EcouteurToucheA ecouteurToucheA){
        super(ecouteurToucheA);
        this.creer(Color.magenta);
    }

    public void setXY(int x, int y){
        this.block[0].x = x;        
        this.block[0].y = y;
        this.block[1].x = this.block[0].x;
        this.block[1].y = this.block[0].y - Block.TAILLE;
        this.block[2].x = this.block[0].x - Block.TAILLE;
        this.block[2].y = this.block[0].y;
        this.block[3].x = this.block[0].x + Block.TAILLE;
        this.block[3].y = this.block[0].y;

    }

    public void getDirection1(){
        this.tempB[0].x = this.block[0].x;        
        this.tempB[0].y = this.block[0].y;
        this.tempB[1].x = this.block[0].x;
        this.tempB[1].y = this.block[0].y - Block.TAILLE;
        this.tempB[2].x = this.block[0].x - Block.TAILLE;
        this.tempB[2].y = this.block[0].y;
        this.tempB[3].x = this.block[0].x + Block.TAILLE;
        this.tempB[3].y = this.block[0].y;

        this.actualiserXY(1);

    }    
    public void getDirection2(){
        this.tempB[0].x = this.block[0].x;        
        this.tempB[0].y = this.block[0].y;
        this.tempB[1].x = this.block[0].x + Block.TAILLE;
        this.tempB[1].y = this.block[0].y;
        this.tempB[2].x = this.block[0].x;
        this.tempB[2].y = this.block[0].y - Block.TAILLE;
        this.tempB[3].x = this.block[0].x;
        this.tempB[3].y = this.block[0].y + Block.TAILLE;

        this.actualiserXY(2);
    }
    public void getDirection3(){
        this.tempB[0].x = this.block[0].x;        
        this.tempB[0].y = this.block[0].y;
        this.tempB[1].x = this.block[0].x;
        this.tempB[1].y = this.block[0].y + Block.TAILLE;
        this.tempB[2].x = this.block[0].x + Block.TAILLE;
        this.tempB[2].y = this.block[0].y;
        this.tempB[3].x = this.block[0].x - Block.TAILLE;
        this.tempB[3].y = this.block[0].y;

        this.actualiserXY(3);
    }
    public void getDirection4(){
        this.tempB[0].x = this.block[0].x;        
        this.tempB[0].y = this.block[0].y;
        this.tempB[1].x = this.block[0].x - Block.TAILLE;
        this.tempB[1].y = this.block[0].y;
        this.tempB[2].x = this.block[0].x;
        this.tempB[2].y = this.block[0].y + Block.TAILLE;
        this.tempB[3].x = this.block[0].x;
        this.tempB[3].y = this.block[0].y - Block.TAILLE;

        this.actualiserXY(4);
    }
}
