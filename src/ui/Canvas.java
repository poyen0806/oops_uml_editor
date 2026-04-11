package ui;

import mode.Mode;
import shape.Shape;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class Canvas extends JPanel {
    private static final Color CANVAS_BACKGROUND = Color.WHITE;

    private final List<Shape> shapes;

    private Mode currentMode;

    public Canvas() {
        this.shapes = new ArrayList<>();
        setBackground(CANVAS_BACKGROUND);

        setupMouseListeners();
    }

    private void setupMouseListeners() {
        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (currentMode != null) currentMode.mousePressed(e);
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                if (currentMode != null) currentMode.mouseReleased(e);
            }
            @Override
            public void mouseDragged(MouseEvent e) {
                if (currentMode != null) currentMode.mouseDragged(e);
            }
        };

        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);
    }

    public void setCurrentMode(Mode mode) {
        this.currentMode = mode;
    }

    public void addShape(Shape shape) {
        shapes.add(shape);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (Shape shape : shapes) {
            shape.draw(g2d);
        }
    }

    public List<Shape> getShapes() {
        return shapes;
    }
}