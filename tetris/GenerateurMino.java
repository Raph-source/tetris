package tetris;

import java.util.Random;

import mino.*;

public class GenerateurMino {
    private ManageurJeuA mjA;    
    private ManageurJeuB mjB;

    public GenerateurMino(ManageurJeuA mjA, ManageurJeuB mjB){
        this.mjA = mjA;        
        this.mjB = mjB;
    }

    //generer un tetra mino
    public void genererMino(){
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

        this.mjA.fileMino.add(mino);        
        this.mjB.fileMino.add(mino);

    }
}
