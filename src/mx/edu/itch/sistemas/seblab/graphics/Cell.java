package mx.edu.itch.sistemas.seblab.graphics;

import java.awt.*;

public class Cell {

    private int initialX;
    private int initialY;
    private int finalX;
    private int finalY;
    private int sepX,sepY;
    private int click;

    public Cell(int initialX, int initialY, int sepX,int sepY) {
        this.initialX = initialX;
        this.initialY = initialY;
        this.finalX=initialX+sepX;
        this.finalY=initialY+sepY;

        this.sepX=sepX;
        this.sepY=sepY;

        System.out.println(initialX+", "+initialY+", "+finalX+", "+finalY);

        this.click =0;
    }

    public void show(Graphics g){

        switch (click){
            case 0:
                g.drawRect(initialX,initialY,sepX,sepY);
                break;
            case 1:
                g.drawRect(initialX,initialY,sepX,sepY);
                g.fillOval(initialX+10,initialY+10,10,10);
                break;
            case 2:
                g.drawRect(initialX,initialY,sepX,sepY);
                g.fillOval(finalX-10,finalY-10,10,10);
                break;
        }
    }

    public boolean isHere(int x, int y) {
        if((initialX <= x && x < finalX) && (initialY<=y && y<finalY)){
            click+=1;
            if (click == 3) click=0;

            return true;
        }else return false;
    }

}
