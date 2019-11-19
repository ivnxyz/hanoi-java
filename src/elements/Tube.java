package elements;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author ivnxyz
 */
public class Tube {
    
    // Atributos
    private int x;
    private int y;
    private int width;
    private int height;
   
    // Función para inicializar el tubo ancho y alto en 0
    public Tube(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    // Getters
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    
    // Dibujar el tubo
    public void drawTube(Graphics g) {
        g.setColor(Color.WHITE);
        g.drawRect(getX(), getY(), getWidth(), getHeight());
        g.fillRect(getX(), getY(), getWidth(), getHeight());
    }
    
}
