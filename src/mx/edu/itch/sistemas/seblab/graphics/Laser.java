package mx.edu.itch.sistemas.seblab.graphics;

import java.awt.*;

public class Laser {

    private final int THICKNESS = 2;

    private int directionX;
    private int directionY;
    private int initialX;
    private int initialY;
    private int finalX;
    private int finalY;

    private boolean stop=false;

    public Laser(int directionX, int directionY, int initialX, int initialY) {
        this.directionX = directionX;
        this.directionY = directionY;
        this.initialX = initialX;
        this.initialY = initialY;

        this.finalX= this.initialX+this.directionX;
        this.finalY= this.initialY+this.directionY;
    }

    public int getFinalX() {
        return finalX;
    }

    public int getFinalY() {
        return finalY;
    }

    public void increaseXY(){
        this.finalX+=this.directionX;
        this.finalY+=this.directionY;
    }


    public void stop(){
        stop=true;
    }

    public void show(Graphics g){
        g.drawLine(initialX,initialY,finalX,finalY);
    }

    public int getDirectionX() {
        return directionX;
    }

    public int getDirectionY() {
        return directionY;
    }

    public boolean isStop() {
        return stop;
    }
}
