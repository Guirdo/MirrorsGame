package mx.edu.itch.sistemas.seblab.ui;

import mx.edu.itch.sistemas.seblab.InterfazGrafica.Ventana;
import mx.edu.itch.sistemas.seblab.graphics.Canvas;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public class Principal extends Ventana {

    public Principal() {
        super("Mirros Game", new MigLayout());

        JPanel panel = new JPanel();
        panel.setLayout(null);
        Canvas canvas = new Canvas(panel);
        panel.add(canvas);

        this.add(panel,"w 500, h 500");
        this.pack();

        this.setLocationRelativeTo(null);
        canvas.shot();
    }

    public static void main(String[] args) {
        new Principal().setVisible(true);
    }
}
