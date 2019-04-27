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
    private ArrayList<Mirror> mirrors;
    private ArrayList<Cell> cells;
    private int rows =3;
    private int cols = 3;
    private int sepRows, sepCols;

    //Variables para pruebas
    private Color[] paleta = {PaletaColores.ALIZARIN, PaletaColores.LIGHT_GREEN, PaletaColores.TURQUOISE,PaletaColores.LINX_WHITE};

    public Canvas(JPanel panel) {
        this.panel = panel;
        this.setBounds(0,0,505,505);
        this.addMouseListener(this);

        this.cells=new ArrayList<>();
        this.generateCells();

        this.mirrors=new ArrayList<>();
        this.laserArrayList = new ArrayList<>();
        laserArrayList.add(new Laser(1,0,0,437));
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
        g.setColor(PaletaColores.SIENNA_4);
        for (Cell cell : cells) cell.show(g);
        //Mirrors paint
        g.setColor(PaletaColores.IRISH_BLUE);
        for(Mirror mirror : mirrors) mirror.show(g);

        //Lasers paints
        g.setColor(PaletaColores.ALIZARIN);
        for(Laser laser : laserArrayList) laser.show(g);

    }

    Thread thread = new Thread(()->{
        try{
            int posH=-1;
            int posI=-1;
            while(true){

                for (int i=0;i<laserArrayList.size();i++){
                    if(!laserArrayList.get(i).isStop()){
                        laserArrayList.get(i).increaseXY();

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
                    laserArrayList.add(mirrors.get(posH).reflect(laserArrayList.get(posI)));
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

    public void shoot(){
        this.thread.start();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        for(Cell cell : cells){
            if(cell.isHere(e.getX(),e.getY())){
                break;
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
