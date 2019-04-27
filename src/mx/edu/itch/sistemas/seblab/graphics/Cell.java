package mx.edu.itch.sistemas.seblab.graphics;

import java.awt.*;

public class Cell {

    private int initialX;
    private int initialY;
    private int finalX;
    private int finalY;
    private boolean clicked;

    public Cell(int initialX, int initialY, int sepX,int sepY) {
        this.initialX = initialX;
        this.initialY = initialY;

        this.finalX=initialX+sepX;
        this.finalY=initialY+sepY;

        this.clicked=false;
    }

    public void show(Graphics g){
        if(clicked){
            g.fillRect(initialX,initialY,finalX,finalY);
        }else{
            g.drawRect(initialX,initialY,finalX,finalY);
        }
    }

    public boolean isHere(int x, int y) {
        if((initialX <= x && x < finalX) && (initialY<=y && y<finalY)){
            this.clicked=!clicked;
            return true;
        }else return false;
    }

    public boolean isClicked() {
        return clicked;
    }
}
