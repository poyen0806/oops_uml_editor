package ui;

import mode.Mode;
import shape.BasicObject;
import shape.Port;
import shape.Shape;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class Canvas extends JPanel {
    private final List<Shape> shapes = new ArrayList<>();
    private Mode currentMode;

    public Canvas() {
        setBackground(Color.WHITE);
        MouseAdapter adapter = new MouseAdapter() {
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

            @Override
            public void mouseMoved(MouseEvent e) {
                if (currentMode != null) currentMode.mouseMoved(e);
            }
        };
        addMouseListener(adapter);
        addMouseMotionListener(adapter);
    }

    public void setCurrentMode(Mode mode) {
        this.currentMode = mode;
    }

    public void addShape(Shape shape) {
        shapes.add(shape);
        repaint();
    }

    public BasicObject findObjectAt(int x, int y) {
        for (int i = shapes.size() - 1; i >= 0; i--) {
            Shape s = shapes.get(i);
            if (s instanceof BasicObject obj && obj.isContained(x, y)) return obj;
        }
        return null;
    }

    public void selectObjectsInArea(java.awt.Rectangle area) {
        for (Shape s : shapes) {
            if (s instanceof BasicObject obj) {
                obj.setSelected(area.contains(obj.getBounds()));
            }
        }
        repaint();
    }

    public void clearSelection() {
        for (Shape s : shapes) {
            if (s instanceof BasicObject obj) obj.setSelected(false);
        }
        repaint();
    }

    public void clearAllHover() {
        for (Shape s : shapes) {
            if (s instanceof BasicObject obj) obj.setHovered(false);
        }
    }

    public Port findPortAt(int x, int y) {
        for (int i = shapes.size() - 1; i >= 0; i--) {
            Port p = shapes.get(i).findPortAt(x, y);
            if (p != null) return p;
        }
        return null;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for (Shape s : shapes) s.draw(g2d);
    }
}