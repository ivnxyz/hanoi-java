package elements;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author ivnxyz
 */
public class Ring {
    
    // Aributo
    private int x;
    private int y;
    private int width;
    private int height;
    private Color color;
    
    public Ring(int x, int y, int width, int height, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }
    
    // Getters y setters

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
    
    // Función para dibujar el anillo
    public void drawRing(Graphics g) {
        g.setColor(color);
        g.fillRect(getX(), getY(), getWidth(), getHeight());
    }
    
}
