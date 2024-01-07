package mino.joueurA;

import java.awt.*;

import mino.Block;
import tetrisB.ManageurJeuA;

public class Mino {
    public Block block[] = new Block[4];    
    public Block tempB[] = new Block[4];
    int autoDropCounter = 0;
    public int direction = 1;//quatre direction

    boolean collisionDroite, collisionGauche, collisionBas;
    public boolean active = true;
    public boolean desactivationEnCours;
    int conteurDesactivation = 0;

    //ecouteur des touches
    public EcouteurToucheA ecouteurToucheA;

    public Mino(EcouteurToucheA ecouteurToucheA){
        this.ecouteurToucheA = ecouteurToucheA;
    }
    public void creer(Color couleur){
        this.block[0] = new Block(couleur);        
        this.block[1] = new Block(couleur);
        this.block[2] = new Block(couleur);
        this.block[3] = new Block(couleur);

        this.tempB[0] = new Block(couleur);
        this.tempB[1] = new Block(couleur);
        this.tempB[2] = new Block(couleur);
        this.tempB[3] = new Block(couleur);

    }

    public void setXY(int x, int y){

    }
    public void actualiserXY(int direction){
        this.checkRotationCollision();

        if(collisionBas == false && collisionDroite == false && collisionGauche == false){
            this.direction = direction;

            this.block[0].x = this.tempB[0].x;
            this.block[0].y = this.tempB[0].y;
            this.block[1].x = this.tempB[1].x;
            this.block[1].y = this.tempB[1].y;
            this.block[2].x = this.tempB[2].x;
            this.block[2].y = this.tempB[2].y;
            this.block[3].x = this.tempB[3].x;
            this.block[3].y = this.tempB[3].y;
        }
    }

    public void actualiser(){
        //lancer la desactivation du tetra mino
        if(this.desactivationEnCours){
            this.desactivation();
        }
        //gestion des touches
        if(this.ecouteurToucheA.haut){
            switch (this.direction) {
                case 1: getDirection2();break;                
                case 2: getDirection3();break;
                case 3: getDirection4();break;
                case 4: getDirection1();break;

            }
            this.ecouteurToucheA.haut = false;
        }

        //teste des collisions
        this.checkMovementCollision();
        
        if(this.ecouteurToucheA.bas){
            //si la collision bas n'est pas
            if(collisionBas == false){
                this.block[0].y += Block.TAILLE;            
                this.block[1].y += Block.TAILLE;
                this.block[2].y += Block.TAILLE;
                this.block[3].y += Block.TAILLE;

                autoDropCounter = 0;
            }
            this.ecouteurToucheA.bas = false;

        }
        if(this.ecouteurToucheA.gauche){
            //si la collision gauche n'est pas
            if(collisionGauche == false){
                this.block[0].x -= Block.TAILLE;            
                this.block[1].x -= Block.TAILLE;
                this.block[2].x -= Block.TAILLE;
                this.block[3].x -= Block.TAILLE;
            }

            this.ecouteurToucheA.gauche = false;
        }
        if(this.ecouteurToucheA.droite){
            //si la collision droite n'est pas
            if(collisionDroite == false){
                this.block[0].x += Block.TAILLE;            
                this.block[1].x += Block.TAILLE;
                this.block[2].x += Block.TAILLE;
                this.block[3].x += Block.TAILLE;
            }

            this.ecouteurToucheA.droite = false;
        }

        if(collisionBas){
            //lancer a la desativation
            this.desactivationEnCours = true;
        }
        else{
            autoDropCounter++;

            if(autoDropCounter == ManageurJeuA.dropInterval){
                //le tetras mino descend
                this.block[0].y += Block.TAILLE;            
                this.block[1].y += Block.TAILLE;
                this.block[2].y += Block.TAILLE;
                this.block[3].y += Block.TAILLE;

                autoDropCounter = 0;

            }
        }
        
    }
    public void dessiner(Graphics2D g2){
        int marge = 2;

        g2.setColor(this.block[0].couleur);
        g2.fillRect(this.block[0].x + marge, this.block[0].y + marge, Block.TAILLE - (marge * 2), Block.TAILLE - (marge * 2));        
        g2.fillRect(this.block[1].x + marge, this.block[1].y + marge, Block.TAILLE - (marge * 2), Block.TAILLE - (marge * 2));
        g2.fillRect(this.block[2].x + marge, this.block[2].y + marge, Block.TAILLE - (marge * 2), Block.TAILLE - (marge * 2));
        g2.fillRect(this.block[3].x + marge, this.block[3].y + marge, Block.TAILLE - (marge * 2), Block.TAILLE - (marge * 2));

    }

    public void getDirection1(){}    
    public void getDirection2(){}
    public void getDirection3(){}
    public void getDirection4(){}

    public void checkMovementCollision(){
        collisionBas = false;        
        collisionDroite = false;
        collisionGauche = false;

        //vérifier les collision entre block
        this.checkBlocksCollision();
        //collision gauche
        for(int i = 0; i < this.block.length; i++){
            if(this.block[i].x == ManageurJeuA.left_x){
                collisionGauche = true;
            }
        }

        //collision droite
        for(int i = 0; i < this.block.length; i++){
            if(this.block[i].x + Block.TAILLE == ManageurJeuA.right_x){
                collisionDroite = true;
            }
        }

        //collision bas
        for(int i = 0; i < this.block.length; i++){
            if(this.block[i].y + Block.TAILLE == ManageurJeuA.botton_y){
                collisionBas = true;
            }
        }
    }
    public void checkRotationCollision(){
        collisionBas = false;        
        collisionDroite = false;
        collisionGauche = false;

        //vérifier les collision entre block
        this.checkBlocksCollision();

        //collision gauche
        for(int i = 0; i < this.block.length; i++){
            if(this.tempB[i].x < ManageurJeuA.left_x){
                collisionGauche = true;
            }
        }

        //collision droite
        for(int i = 0; i < this.block.length; i++){
            if(this.tempB[i].x + Block.TAILLE > ManageurJeuA.right_x){
                collisionDroite = true;
            }
        }

        //collision bas
        for(int i = 0; i < this.block.length; i++){
            if(this.tempB[i].y + Block.TAILLE > ManageurJeuA.botton_y){
                collisionBas = true;
            }
        }
    }

    private void checkBlocksCollision(){
        for(int i = 0; i < ManageurJeuA.blocksMinoPrecedant.size(); i++){
            int blockX = ManageurJeuA.blocksMinoPrecedant.get(i).x;            
            int blockY = ManageurJeuA.blocksMinoPrecedant.get(i).y;

            //verifier vers le bas
            for(int j = 0; j < this.block.length; j++){
                if(this.block[j].y + Block.TAILLE == blockY && this.block[j].x == blockX){
                    collisionBas = true;
                }
            }

            //verifier vers la gauche
            for(int j = 0; j < this.block.length; j++){
                if(this.block[j].x - Block.TAILLE == blockX && this.block[j].y == blockY){
                    collisionGauche = true;
                }
            }

            //verifier vers la droite
            for(int j = 0; j < this.block.length; j++){
                if(this.block[j].x + Block.TAILLE == blockX && this.block[j].y == blockY){
                    collisionDroite = true;
                }
            }
        }
    }

    //desactivation du tetra mino
    private void desactivation(){
        conteurDesactivation++;

        if(conteurDesactivation == 45){
            conteurDesactivation = 0;

            this.checkMovementCollision();
            if(collisionBas){
                this.active = false;
            }
        }
    }

}
