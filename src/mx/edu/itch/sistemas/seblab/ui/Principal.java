package mx.edu.itch.sistemas.seblab.ui;

import mx.edu.itch.sistemas.seblab.InterfazGrafica.Boton;
import mx.edu.itch.sistemas.seblab.InterfazGrafica.PaletaColores;
import mx.edu.itch.sistemas.seblab.InterfazGrafica.Ventana;
import mx.edu.itch.sistemas.seblab.graphics.Canvas;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class Principal extends Ventana {

    public Principal() {
        super("Mirros Game", new MigLayout("wrap 2"));

        JPanel panel = new JPanel();
        panel.setLayout(null);
        Canvas canvas = new Canvas(panel);
        panel.add(canvas);

        Boton btnShoot = new Boton("Shoot", PaletaColores.LINX_WHITE,PaletaColores.ALIZARIN);
        Boton btnNewGame = new Boton("New game",PaletaColores.LINX_WHITE,PaletaColores.EMERALD);

        btnShoot.addActionListener((ActionEvent)->{
            canvas.shoot();
        });
        btnNewGame.addActionListener((ActionEvent)->{
            canvas.newGame();
        });

        this.add(btnShoot);
        this.add(btnNewGame);
        this.add(panel,"w 505, h 505,span 2");
        this.pack();

        this.setLocationRelativeTo(null);

    }

    public static void main(String[] args) {
        new Principal().setVisible(true);
    }
}
