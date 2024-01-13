package tetrisA;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

import mino.joueurA.EcouteurToucheA;
import mino.joueurB.EcouteurToucheB;

public class GenerateurMino {
    private ManageurJeuA mjA;    
    private ManageurJeuB mjB;
    private EcouteurToucheA ecouteurToucheA;    
    private EcouteurToucheB ecouteurToucheB;
    private Socket socket;
    PrintWriter senderMino;

    public GenerateurMino(ManageurJeuA mjA, ManageurJeuB mjB, EcouteurToucheA ecouteurToucheA, EcouteurToucheB ecouteurToucheB, Socket socket){
        this.mjA = mjA;        
        this.mjB = mjB;
        this.ecouteurToucheA = ecouteurToucheA;
        this.ecouteurToucheB = ecouteurToucheB;
        this.socket = socket;
    }

    //generer un tetra mino
    public void genererMino(){
        mino.joueurA.Mino minoJoueurA = null;        
        mino.joueurB.Mino minoJoueurB = null;
        
        //l'objet qui envoi les tetras mino
        try{
            senderMino = new PrintWriter(this.socket.getOutputStream(), true);
        }
        catch(Exception e){
            e.printStackTrace();
        }

        int num = new Random().nextInt(7);

        switch (num) {
            case 0: {
                //envoi de l'object
                senderMino.println(0 + "");

                minoJoueurA = new mino.joueurA.Mino_l1(this.ecouteurToucheA);                
                minoJoueurB = new mino.joueurB.Mino_l1(this.ecouteurToucheB);
                break;
            }            
            case 1: {
                //envoi de l'object
                senderMino.println(1 + "");

                minoJoueurA = new mino.joueurA.Mino_l2(this.ecouteurToucheA);                
                minoJoueurB = new mino.joueurB.Mino_l2(this.ecouteurToucheB);
                break;
            }
            case 2: {
                //envoi de l'object
                senderMino.println(2 + "");

                minoJoueurA = new mino.joueurA.Mino_t(this.ecouteurToucheA);                
                minoJoueurB = new mino.joueurB.Mino_t(this.ecouteurToucheB);
                break;
            }
            case 3: {
                //envoi de l'object
                senderMino.println(3 + "");

                minoJoueurA = new mino.joueurA.Mino_z1(this.ecouteurToucheA);                
                minoJoueurB = new mino.joueurB.Mino_z1(this.ecouteurToucheB);
                break;
            }
            case 4: {
                //envoi de l'object
                senderMino.println(4 + "");

                minoJoueurA = new mino.joueurA.Mino_z2(this.ecouteurToucheA);                
                minoJoueurB = new mino.joueurB.Mino_z2(this.ecouteurToucheB);
                break;
            }
            case 5: {
                //envoi de l'object
                senderMino.println(5 + "");

                minoJoueurA = new mino.joueurA.Mino_carre(this.ecouteurToucheA);                
                minoJoueurB = new mino.joueurB.Mino_carre(this.ecouteurToucheB);
                break;
            }
            case 6: {
                //envoi de l'object
                senderMino.println(6 + "");

                minoJoueurA = new mino.joueurA.Mino_bar(this.ecouteurToucheA);                
                minoJoueurB = new mino.joueurB.Mino_bar(this.ecouteurToucheB);
                break;
            }
        }

        this.mjA.fileMino.add(minoJoueurA);        
        this.mjB.fileMino.add(minoJoueurB);

    }
}
