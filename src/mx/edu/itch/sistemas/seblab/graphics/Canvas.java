package mx.edu.itch.sistemas.seblab.graphics;

import mx.edu.itch.sistemas.seblab.InterfazGrafica.PaletaColores;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Canvas extends JComponent {

    private JPanel panel;
    private ArrayList<Laser> laserArrayList;
    private ArrayList<Mirror> mirrors;

    public Canvas(JPanel panel) {
        this.panel = panel;

        this.setBounds(0,0,500,500);

        this.mirrors=new ArrayList<>();
        this.mirrors.add(new Mirror(Mirror.LESS_90,275,275,50));
        this.mirrors.add(new Mirror(Mirror.MORE_90,325,150,50));
        this.mirrors.add(new Mirror(Mirror.LESS_90,175,150,50));
        this.mirrors.add(new Mirror(Mirror.LESS_90,175,350,50));
        this.mirrors.add(new Mirror(Mirror.MORE_90,105,350,50));
        this.mirrors.add(new Mirror(Mirror.LESS_90,55,100,50));
        this.mirrors.add(new Mirror(Mirror.MORE_90,450,100,50));
        this.mirrors.add(new Mirror(Mirror.MORE_90,450,400,50));

        this.laserArrayList = new ArrayList<>();
        laserArrayList.add(new Laser(1,0,0,250));
    }

    public void paint(Graphics g){
        g.setColor(PaletaColores.IRISH_BLUE);

        for (int i=0;i<mirrors.size();i++){
            g.drawLine(mirrors.get(i).getInitialX(),mirrors.get(i).getInitialY(),
                    mirrors.get(i).getFinalX(),mirrors.get(i).getFinalY());
            g.drawLine(mirrors.get(i).getInitialX()+1,mirrors.get(i).getInitialY(),
                    mirrors.get(i).getFinalX()+1,mirrors.get(i).getFinalY());
        }

        g.setColor(PaletaColores.ALIZARIN);

        for (int i=0;i<laserArrayList.size();i++){

            g.drawLine(laserArrayList.get(i).getInitialX(),laserArrayList.get(i).getInitialY(),
                    laserArrayList.get(i).getFinalX(),laserArrayList.get(i).getFinalY());

        }

    }

    Thread thread = new Thread(()->{
        try{
            System.out.println("Started thread");
            int posH=-1;
            int posI=-1;
            while(true){

                for (int i=0;i<laserArrayList.size();i++){
                    if(!laserArrayList.get(i).isStop()){
                        laserArrayList.get(i).increaseX();
                        laserArrayList.get(i).increaseY();

                        for(int h=0;h<mirrors.size();h++){
                            if(mirrors.get(h).isTouching(laserArrayList.get(i).getFinalX(),laserArrayList.get(i).getFinalY())){
                                laserArrayList.get(i).stop();
                                posH=h;
                                posI=i;
                            }
                        }

                        if(laserArrayList.get(i).getFinalX()==500 || laserArrayList.get(i).getFinalX()==0
                            || laserArrayList.get(i).getFinalY()==0 || laserArrayList.get(i).getFinalY()==500){
                            laserArrayList.get(i).stop();
                        }
                    }
                }

                if( posH!=-1 && posI!=-1){
                    laserArrayList.add(mirrors.get(posH).getLaser(laserArrayList.get(posI)));
                    posH=-1;
                    posI=-1;
                }

                this.panel.repaint();
                Thread.sleep(1);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    });

    public void shot(){
        this.thread.start();
    }
}
