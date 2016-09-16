/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Demos;

import java.awt.Canvas;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author BIT2
 */
public class A {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(null);
        GraphicsEnvironment ge = GraphicsEnvironment.
                getLocalGraphicsEnvironment();
        GraphicsDevice[] gs = ge.getScreenDevices();
        for (int j = 0; j < gs.length; j++) {
            GraphicsDevice gd = gs[j];
            GraphicsConfiguration[] gc
                    = gd.getConfigurations();
            for (int i = 0; i < gc.length; i++) {
                JFrame f = new JFrame(gs[j].getDefaultConfiguration());
                Canvas c = new Canvas(gc[i]);
                System.out.println(""+gc[i].getBounds());
                Rectangle gcBounds = gc[i].getBounds();
                int xoffs = gcBounds.x;
                int yoffs = gcBounds.y;
                f.getContentPane().add(c);
                f.setLocation((i * 50) + xoffs, (i * 60) + yoffs);
                f.show();

            }

        }
    }

}
