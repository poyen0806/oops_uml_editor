package ui;

import mode.Mode;
import shape.BasicObject;
import shape.GroupObject;
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
            @Override public void mousePressed(MouseEvent e) { if (currentMode != null) currentMode.mousePressed(e); }
            @Override public void mouseReleased(MouseEvent e) { if (currentMode != null) currentMode.mouseReleased(e); }
            @Override public void mouseDragged(MouseEvent e) { if (currentMode != null) currentMode.mouseDragged(e); }
            @Override public void mouseMoved(MouseEvent e) { if (currentMode != null) currentMode.mouseMoved(e); }
        };
        addMouseListener(adapter);
        addMouseMotionListener(adapter);
    }

    public void setCurrentMode(Mode mode) { this.currentMode = mode; }

    public List<Shape> getShapes() {
        return shapes;
    }

    public void addShape(Shape shape) {
        shapes.add(shape);
        repaint();
    }

    public Shape findObjectAt(int x, int y) {
        for (Shape s : shapes.reversed()) {
            if (s.isContained(x, y)) return s;
        }
        return null;
    }

    public Port findPortAt(int x, int y) {
        for (Shape s : shapes.reversed()) {
            Port p = s.findPortAt(x, y);
            if (p != null) return p;
        }
        return null;
    }

    public void selectObjectsInArea(java.awt.Rectangle area) {
        for (Shape s : shapes) {
            Rectangle bounds = s.getBounds();
            if (bounds != null) {
                s.setSelected(area.contains(bounds));
            }
        }
        repaint();
    }

    public void clearSelection() {
        for (Shape s : shapes) s.setSelected(false);
        repaint();
    }

    public void clearAllHover() {
        for (Shape s : shapes) s.setHovered(false);
    }

    public void groupObjects() {
        List<BasicObject> toGroup = new ArrayList<>();
        for (Shape s : shapes) {
            if (s.isSelected()) toGroup.add((BasicObject) s);
        }

        if (toGroup.size() >= 2) {
            shapes.removeAll(toGroup);
            GroupObject group = new GroupObject(toGroup);
            group.setSelected(true);
            shapes.add(group);
            repaint();
        }
    }

    public void ungroupObjects() {
        Shape target = null;
        int count = 0;

        for (Shape s : shapes) {
            if (s.isSelected()) {
                target = s;
                count++;
            }
        }

        if (count == 1 && target instanceof GroupObject group) {
            shapes.remove(group);
            List<BasicObject> members = group.getMembers();
            shapes.addAll(members);
            for (BasicObject bo : members) bo.setSelected(true);
            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for (Shape s : shapes) s.draw(g2d);
    }
}