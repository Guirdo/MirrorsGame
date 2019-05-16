package mx.edu.itch.sistemas.seblab.graphics;

import mx.edu.itch.sistemas.seblab.InterfazGrafica.PaletaColores;

import java.awt.*;

public class Cell {

    private int initialX;
    private int initialY;
    private int finalX;
    private int finalY;
    private int sepX,sepY;
    private int click;
    private boolean translucent;
    private Mirror mirror;

    public Cell(int initialX, int initialY, int sepX,int sepY) {
        this.initialX = initialX;
        this.initialY = initialY;
        this.finalX=initialX+sepX;
        this.finalY=initialY+sepY;
        this.sepX=sepX;
        this.sepY=sepY;

        this.mirror=null;
        this.translucent=true;
        this.click =0;
    }

    public void show(Graphics g){
        g.setColor(PaletaColores.SILVER);

        switch (click){
            case 0:
                g.drawRect(initialX,initialY,sepX,sepY);
                break;
            case 1:
                g.drawRect(initialX,initialY,sepX,sepY);
                this.showMirror(Mirror.LESS_90,initialX,finalY,g);
                break;
            case 2:
                g.drawRect(initialX,initialY,sepX,sepY);
                this.showMirror(Mirror.MORE_90,finalX,finalY,g);
                break;
            case 3:
                g.fillRect(initialX,initialY,sepX,sepY);
                break;
            case 5:
                g.setColor(PaletaColores.EMERALD);
                g.fillRect(initialX,initialY,sepX,sepY);
                //g.setColor(PaletaColores.SILVER);
                break;
        }
    }

    private void showMirror(int face,int initialX,int initialY,Graphics g){
        this.mirror=new Mirror(face,initialX,initialY,sepY);
        mirror.show(g);
    }

    public boolean isHere(int x, int y) {
        if((initialX <= x && x < finalX) && (initialY<=y && y<finalY)){
            click+=1;

            if(click == 3) {
                this.translucent = false;
            }else if (click == 4){
                click=0;
                translucent=true;
            }

            return true;
        }else return false;
    }

    public int getClick() {
        return click;
    }

    public boolean isTranslucent() {
        return translucent;
    }

    public Mirror getMirror() {
        return mirror;
    }

    public boolean isTouched(Laser laser) {
        int xLaser = laser.getFinalX();
        int yLaser = laser.getFinalY();

        if(xLaser == initialX && initialY <= yLaser && yLaser < finalY){
            return true;
        }else if(initialX <= xLaser && xLaser < finalX && yLaser == initialY){
            return true;
        }else if(finalX == xLaser && initialY <= yLaser && yLaser < finalY){
            return true;
        }else if(initialX <= xLaser && xLaser < finalX && yLaser == finalY){
            return true;
        }else{
            return false;
        }
    }

    public boolean setGoal(int x, int y) {
        if((initialX <= x && x < finalX) && (initialY<=y && y<finalY)){
            this.click = 5;

            return true;
        }else return false;
    }
}
