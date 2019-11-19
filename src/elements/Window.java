package elements;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.SwingUtilities;

/**
 *
 * @author ivnxyz
 */
public class Window extends javax.swing.JPanel {
    
    // Atributos
    private Tube tube1;
    private Tube tube2;
    private Tube tube3;
    private ArrayList<Color> colors = new ArrayList<>();
    
    private ArrayList<Ring> tubeA = new ArrayList<>();; // Los anillos del tubo A
    private ArrayList<Ring> tubeB = new ArrayList<>(); // Los anillos del tubo B
    private ArrayList<Ring> tubeC = new ArrayList<>(); // Los anillos del tubo C
    
    private final int TUBE_WIDTH = 20;
    private final int RING_WIDTH = TUBE_WIDTH * 10;
    private final int RING_HEIGHT = 30;
    
    private int w = 300;
    private boolean shouldGenerateRings = true;

    public Window() {
        initComponents();
        
        // Inicializar lista de colores
        colors.add(new Color(120, 80, 203));
        colors.add(new Color(236, 25, 24));
        colors.add(new Color(255, 150, 50));
        colors.add(new Color(231, 230, 0));
        colors.add(new Color(79, 161, 58));
        colors.add(new Color(100, 160, 230));
        colors.add(new Color(200, 200, 200));
        colors.add(new Color(255, 255, 255));
        
        // Agregar listener al combo
        jComboBox2.addActionListener((ActionEvent e) -> {
            // Volver a renderizar pantalla y generar los anillos nuevamente
            shouldGenerateRings = true;
            repaint();
        });
        
        jButton1.addActionListener((ActionEvent e) -> {
            // Se comienzar a mover los anillos en un nuevo hilo para evitar problemas con el hilo que usa JPanel para dibujar los anillos
            Thread appThread = new Thread() {
                public void run() {
                    // Deshabilitar elementos de UI
                    jButton1.setEnabled(false);
                    jComboBox2.setEnabled(false);
                    
                    int numberOfRings = jComboBox2.getSelectedIndex() + 1;
                    
                    if (tubeC.size() == jComboBox2.getSelectedIndex() + 1) {
                        moveDisks("C", "B", "A", numberOfRings);
                    } else {
                        // Comenzar algoritmo para mover los anillos
                        moveDisks("A", "B", "C", numberOfRings);
                    }
                    
                    // Volver a habilitar elementos de UI
                    jButton1.setEnabled(true);
                    jComboBox2.setEnabled(true);
                }
            };
            
            appThread.start();
        });
    }
    
    // Generar los aros que irán en los tubos
    private void generateRings() {
        // Limpiar listas de anillos
        tubeA = new ArrayList<>();
        tubeB = new ArrayList<>();
        tubeC = new ArrayList<>();
        
        // Obtener propiedades de la ventana
        final int windowHeight = getHeight();
        
        // Obtener el número de anillos que se van a dibujar
        int numberOfRings = jComboBox2.getSelectedIndex() + 1;
        
        // Genear anillos
        for (int i = 0; i < numberOfRings; i++) {
            // Por defecto, los nuevos anillos estarán en el tubo 1
            final int yPosition = windowHeight - RING_HEIGHT - (i * RING_HEIGHT);
            final int ringWidth = RING_WIDTH - i * 20;
            final int xPosition = tube1.getX() + (tube1.getWidth()/2) - (ringWidth/2);
            
            // Instanciar un nuevo anillo y agregarlo a la lista de anillos
            final Ring ring = new Ring(xPosition, yPosition, ringWidth, RING_HEIGHT, colors.get(i));
            tubeA.add(ring);
        }
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        
        // Obtener propiedades de la ventana
        final int windowHeight = getHeight();
        final int windowWidth = getWidth();
        
        // Calcular alto de los tubos
        final int tubeHeight = (int) (windowHeight - windowHeight * 0.4);
        
        // Inicializar los tubos
        tube1 = new Tube(windowWidth/6 - TUBE_WIDTH, windowHeight - tubeHeight, TUBE_WIDTH, tubeHeight);
        tube2 = new Tube(windowWidth/2 - TUBE_WIDTH/2, windowHeight - tubeHeight, TUBE_WIDTH, tubeHeight);
        tube3 = new Tube(windowWidth - windowWidth/6 - TUBE_WIDTH, windowHeight - tubeHeight, TUBE_WIDTH, tubeHeight);
        
        // Dibujar los tubos
        tube1.drawTube(g);
        tube2.drawTube(g);
        tube3.drawTube(g);
        
        // Generar anillos si es necesario
        if (shouldGenerateRings) {
            generateRings();
            shouldGenerateRings = false;
        }
        
        drawRings(g);
    }
    
    private void drawRings(Graphics g) {
        // Mostrar los anillos del tubo A en la pantalla
        for (int i = 0; i < tubeA.size(); i++) {
            final Ring ring = tubeA.get(i);
            ring.drawRing(g);
        }
        
        // Mostrar los anillos del tubo B en la pantalla
        for (int i = 0; i < tubeB.size(); i++) {
            final Ring ring = tubeB.get(i);
            ring.drawRing(g);
        }
        
        // Mostrar los anillos del tubo C en la pantalla
        for (int i = 0; i < tubeC.size(); i++) {
            final Ring ring = tubeC.get(i);
            ring.drawRing(g);
        }
    }
    
    /**
    * Este es un algoritmo recursivo
    * 
    * @param from
    *            tubo inicial
    * @param via
    *            tubo intermedio
    * @param to
    *            tubo de destino
    * @param n
    *            número de aros
    */
    private void moveDisks(String from, String via, String to, int n) {
        if (n == 1) {
            if (from.equals("A") && to.equals("C")) {
                tubeC.add(tubeA.get(tubeA.size() - 1));
                move(tubeA.get(tubeA.size() - 1), tube3, tubeC);
                tubeA.remove(tubeA.size() - 1);
                timeout(w);
            } else if (from.equals("A") && to.equals("B")) {
                tubeB.add(tubeA.get(tubeA.size() - 1));
                move(tubeA.get(tubeA.size() - 1), tube2, tubeB);
                tubeA.remove(tubeA.size() - 1);
                timeout(w);
            } else if (from.equals("B") && to.equals("C")) {
                tubeC.add(tubeB.get(tubeB.size() - 1));
                move(tubeB.get(tubeB.size() - 1), tube3, tubeC);
                tubeB.remove(tubeB.size() - 1);
                timeout(w);
            } else if (from.equals("B") && to.equals("A")) {
                tubeA.add(tubeB.get(tubeB.size() - 1));
                move(tubeB.get(tubeB.size() - 1), tube1, tubeA);
                tubeB.remove(tubeB.size() - 1);
                timeout(w);
            } else if (from.equals("C") && to.equals("A")) {
                tubeA.add(tubeC.get(tubeC.size() - 1));
                move(tubeC.get(tubeC.size() - 1), tube1, tubeA);
                tubeC.remove(tubeC.size() - 1);
                timeout(w);
            } else if (from.equals("C") && to.equals("B")) {
                tubeB.add(tubeC.get(tubeC.size() - 1));
                move(tubeC.get(tubeC.size() - 1), tube2, tubeB);
                tubeC.remove(tubeC.size() - 1);
                timeout(w);
            }
        } else {
            moveDisks(from, to, via, n - 1); // A -> C -> B
            moveDisks(from, "", to, 1); // A -> C
            moveDisks(via, from, to, n - 1); // B -> A -> C
        }
    }
    
    /**
    * Método para realizar pequeños retrasos para las animaciones
    * 
    * @param wait
    */
    private void timeout(int time) {
        try {
            Thread.sleep(time);
        }  catch (Exception e) {
            // ignoring exception at the moment
            System.out.println("Exception: " + e);
        }
    }
    
    /**
     * Método para mover un anillo de un tubo a otro
     * 
     * @param ring
     * @param to 
     * @param tubeList
     */
    private void move(Ring ring, Tube to, ArrayList<Ring> tubeList) {
        Window window = this;
        
        // Obtener propiedades de la pantalla
        final int windowHeight = getHeight();
        
        // Obtener la nueva posición del anillo
        int ringIndex = tubeList.indexOf(ring);
        int xPosition = to.getX() + (to.getWidth()/2) - (ring.getWidth()/2);
        int yPosition = windowHeight - RING_HEIGHT - (ringIndex * RING_HEIGHT);
        
        ring.setX(xPosition);
        ring.setY(yPosition);
        
        // Mostrar el anillo
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                    window.repaint();
                }
            });
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jComboBox1 = new javax.swing.JComboBox<>();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        setBackground(new java.awt.Color(40, 40, 40));

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8" }));
        jComboBox2.setSelectedIndex(3);
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Elige el número de aros");

        jButton1.setText("Comenzar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(165, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton1))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap(232, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
