package mino.joueurB;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class EcouteurToucheB implements KeyListener{
    public boolean droite, gauche, haut, bas, pause;

    @Override
    public void keyTyped(KeyEvent ev){}

    @Override
    public void keyPressed(KeyEvent ev){
        int code = ev.getKeyCode();

        if(code == 38){
            haut = true;
        }
        if(code == 37){
            gauche = true;
        }
        if(code == 40){
            bas = true;
        }
        if(code == 39){
            droite = true;
        }
        if(code == 32){
            if(pause)
                pause = false;
            else
                pause = true;
        }
    }    
    
    @Override
    public void keyReleased(KeyEvent ev){}
}
