package mx.edu.itch.sistemas.seblab.graphics;

import mx.edu.itch.sistemas.seblab.InterfazGrafica.PaletaColores;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class Canvas extends JComponent implements MouseListener {

    private JPanel panel;
    private ArrayList<Laser> laserArrayList;
    private ArrayList<Cell> cells;
    private int rows =5;
    private int cols = 5;
    private int sepRows, sepCols;
    private volatile boolean isShooting;

    public Canvas(JPanel panel) {
        this.panel = panel;
        this.setBounds(0,0,505,505);
        this.addMouseListener(this);

        isShooting=false;

        this.cells=new ArrayList<>();
        this.generateCells();

        double posLaser = sepCols * (cols-0.5);

        this.laserArrayList = new ArrayList<>();
        laserArrayList.add(new Laser(1,0,0,(int)posLaser));
    }

    private void generateCells(){
        this.sepCols =500/ cols;
        this.sepRows = 500/ rows;

        int x=0,y=0;
        for(int i = 0; i< rows; i++){
            for(int j = 0; j< cols; j++){
                cells.add(new Cell(x,y,sepCols,sepRows));
                x+=sepCols;
            }
            y+=sepRows;
            x=0;
        }
    }

    public void paint(Graphics g){

        //Cells paints
        for (Cell cell : cells) cell.show(g);

        //Lasers paints
        g.setColor(PaletaColores.ALIZARIN);
        for(Laser laser : laserArrayList) laser.show(g);

    }

    Thread thread = new Thread(()->{
        try{
            int posH=-1;
            int posI=-1;
            while(true){

                if(this.isShooting){
                    for (int i=0;i<laserArrayList.size();i++){
                        if(!laserArrayList.get(i).isStop()){
                            laserArrayList.get(i).increaseXY();

                            int h=0;
                            for(Cell cell : cells){
                                if(cell.isTranslucent()){
                                    if(cell.getClick()>0 && cell.getClick()<3){
                                        if(cell.getMirror().isTouched(laserArrayList.get(i))){
                                            laserArrayList.get(i).stop();
                                            posH=h;
                                            posI=i;
                                        }
                                    }else if(cell.getClick()==5){
                                        if(cell.isTouched(laserArrayList.get(i))){
                                            laserArrayList.get(i).stop();
                                            JOptionPane.showMessageDialog(null,"Ya ganaste");
                                        }
                                    }
                                }else{
                                    if(cell.isTouched(laserArrayList.get(i))){
                                        laserArrayList.get(i).stop();
                                    }
                                }
                                h++;
                            }

                            if(laserArrayList.get(i).getFinalX()==500 || laserArrayList.get(i).getFinalX()==0
                                    || laserArrayList.get(i).getFinalY()==0 || laserArrayList.get(i).getFinalY()==500){
                                laserArrayList.get(i).stop();
                            }
                        }
                    }

                    if( posH!=-1 && posI!=-1){
                        laserArrayList.add(cells.get(posH).getMirror().reflect(laserArrayList.get(posI)));
                        posH=-1;
                        posI=-1;
                    }

                    this.panel.repaint();
                    Thread.sleep(1);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    });

    public void shoot(){
        this.isShooting=true;
        if(!thread.isAlive()){
            System.out.println("Esta iniciando el hilo");
            this.thread.start();
        }
    }

    public void newGame(){
        isShooting=false;
        this.cells=new ArrayList<>();
        this.generateCells();

        double posLaser = sepCols * (cols-0.5);

        this.laserArrayList = new ArrayList<>();
        laserArrayList.add(new Laser(1,0,0,(int)posLaser));

        panel.repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {

        if(e.isMetaDown()){
            for(Cell cell : cells){
                if(cell.setGoal(e.getX(),e.getY())){
                    break;
                }
            }
        }else{
            for(Cell cell : cells){
                if(cell.isHere(e.getX(),e.getY())){
                    break;
                }
            }
        }

        this.panel.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
