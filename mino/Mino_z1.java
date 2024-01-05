package mino;

import java.awt.Color;

public class Mino_z1 extends Mino{
    public Mino_z1(){
        this.creer(Color.red);
    }

    public void setXY(int x, int y){
        this.block[0].x = x;        
        this.block[0].y = y;
        this.block[1].x = this.block[0].x;
        this.block[1].y = this.block[0].y - Block.TAILLE;
        this.block[2].x = this.block[0].x - Block.TAILLE;
        this.block[2].y = this.block[0].y;
        this.block[3].x = this.block[0].x - Block.TAILLE;
        this.block[3].y = this.block[0].y + Block.TAILLE;

    }

    public void getDirection1(){
        this.tempB[0].x = this.block[0].x;        
        this.tempB[0].y = this.block[0].y;
        this.tempB[1].x = this.block[0].x;
        this.tempB[1].y = this.block[0].y - Block.TAILLE;
        this.tempB[2].x = this.block[0].x - Block.TAILLE;
        this.tempB[2].y = this.block[0].y;
        this.tempB[3].x = this.block[0].x - Block.TAILLE;
        this.tempB[3].y = this.block[0].y + Block.TAILLE;

        this.actualiserXY(1);

    }    
    public void getDirection2(){
        this.tempB[0].x = this.block[0].x;        
        this.tempB[0].y = this.block[0].y;
        this.tempB[1].x = this.block[0].x + Block.TAILLE;
        this.tempB[1].y = this.block[0].y;
        this.tempB[2].x = this.block[0].x;
        this.tempB[2].y = this.block[0].y - Block.TAILLE;
        this.tempB[3].x = this.block[0].x - Block.TAILLE;
        this.tempB[3].y = this.block[0].y - Block.TAILLE;

        this.actualiserXY(2);
    }
    public void getDirection3(){
        this.getDirection1();
    }
    public void getDirection4(){
        this.getDirection2();
    }
}
