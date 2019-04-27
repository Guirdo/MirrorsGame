package mx.edu.itch.sistemas.seblab.graphics;

import java.awt.*;

public class Mirror {

    public static final int LESS_90 = 0;
    public static final int MORE_90 = 1;

    private int face;
    private int initialX;
    private int initialY;
    private int finalX;
    private int finalY;

    public Mirror(int face, int initialX, int initialY, int size) {
        this.face = face;
        this.initialX = initialX;
        this.initialY = initialY;

        if (face == LESS_90){
            this.finalX=this.initialX+size;
            this.finalY=this.initialY-size;
        }else{
            this.finalX=this.initialX-size;
            this.finalY=this.initialY-size;
        }
    }

    public void show(Graphics g){
        g.drawLine(initialX,initialY,finalX,finalY);
        g.drawLine(initialX+1,initialY,finalX+1,finalY);
    }

    public boolean isTouching(int finalX, int finalY) {
        int y;
        if(this.face==LESS_90){
            if(finalX>=this.initialX && finalX<=this.finalX){
                y = initialY -(finalX-initialX);

                if(y==finalY){
                    return true;
                }else{
                    return false;
                }
            }else{
                return false;
            }
        }else{
            if(finalX<=this.initialX && finalX>=this.finalX){
                y=initialY-(initialX-finalX);

                if(y==finalY){
                    return true;
                }else{
                    return false;
                }
            }else{
                return false;
            }

        }
    }

    public Laser reflect(Laser oldLaser){
        Laser newLaser=null;
        int newDirectionX=0,newDirectionY=0;

        if(face==LESS_90){
            if(oldLaser.getDirectionX()!=0){
                if(oldLaser.getDirectionX()>0){
                    newDirectionY=-1;
                }else{
                    newDirectionY=1;
                }
            }else{
                if(oldLaser.getDirectionY()>0){
                    newDirectionX=-1;
                }else{
                    newDirectionX=1;
                }
            }
        }else{
            if(oldLaser.getDirectionY()!=0){
                if(oldLaser.getDirectionY()<0){
                    newDirectionX=-1;
                }else{
                    newDirectionX=1;
                }
            }else{
                if(oldLaser.getDirectionX()<0){
                    newDirectionY=-1;
                }else{
                    newDirectionY=1;
                }
            }
        }

        newLaser=new Laser(newDirectionX,newDirectionY,oldLaser.getFinalX(),oldLaser.getFinalY());

        if(newLaser==null){
            System.out.println("sOY NULO");
        }

        return newLaser;
    }
}
