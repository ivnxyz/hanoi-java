package torreshanoi;

import javax.swing.JFrame;
import elements.*;
import java.awt.Container;
import java.awt.FlowLayout;
import javax.swing.JLayeredPane;

/**
 *
 * @author ivnxyz
 */
public class TorresHanoi {
    
    // Atributos
    private static final int FRAME_HEIGHT = 600;
    private static final int FRAME_WIDTH = 800;
    private static final JFrame FRAME = new JFrame("Torres de Hanoi");

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Mostrar ventana principal
        addWindow();
        
        // Configurar y mostrar ventana
        FRAME.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        FRAME.setVisible(true);
        FRAME.setResizable(false);
        FRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    private static void addWindow() {
        // Mostrar ventana principal
        Window window = new Window();
        FRAME.add(window);
    }
    
}
