package ui.component;

import mode.Mode;
import shape.basic.BasicObject;
import shape.composite.GroupObject;
import shape.Port;
import shape.Shape;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * The core drawing engine and entity manager.
 * Acts as the 'Context' in the Strategy Pattern (delegating mouse events to the current Mode)
 * and manages the lifecycle/rendering of all Shape objects on the screen.
 */
public class Canvas extends JPanel {

    // The main entity list. Rendering order defines the Z-index (depth).
    // Elements later in the list are drawn on top.
    private final List<Shape> shapes = new ArrayList<>();

    // Holds the currently active behavior (Select, Create, Link)
    private Mode currentMode;

    public Canvas() {
        setBackground(Color.WHITE);

        // Event Delegation: The Canvas doesn't process interactions itself.
        MouseAdapter adapter = new MouseAdapter() {
            @Override public void mousePressed(MouseEvent e) { if (currentMode != null) currentMode.mousePressed(e); }
            @Override public void mouseReleased(MouseEvent e) { if (currentMode != null) currentMode.mouseReleased(e); }
            @Override public void mouseDragged(MouseEvent e) { if (currentMode != null) currentMode.mouseDragged(e); }
            @Override public void mouseMoved(MouseEvent e) { if (currentMode != null) currentMode.mouseMoved(e); }
        };
        addMouseListener(adapter);
        addMouseMotionListener(adapter);
    }

    /** Injects a new interactive Mode into the Canvas. */
    public void setCurrentMode(Mode mode) { this.currentMode = mode; }

    public List<Shape> getShapes() {
        return shapes;
    }

    public void addShape(Shape shape) {
        shapes.add(shape);
        repaint();
    }

    /**
     * Hit-testing for Objects.
     * Iterates in reverse to respect Z-index depth: top-most objects are selected first.
     */
    public Shape findObjectAt(int x, int y) {
        for (Shape s : shapes.reversed()) {
            if (s.isContained(x, y)) return s;
        }
        return null;
    }

    /**
     * Hit-testing specifically for connection Ports.
     */
    public Port findPortAt(int x, int y) {
        for (Shape s : shapes.reversed()) {
            Port p = s.findPortAt(x, y);
            if (p != null) return p;
        }
        return null;
    }

    /** Area selection. */
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

    /**
     * Wraps multiple BasicObjects into a single GroupObject.
     * Replaces the selected individual objects with the new Group wrapper in the render list.
     */
    public void groupObjects() {
        List<BasicObject> toGroup = new ArrayList<>();
        for (Shape s : shapes) {
            if (s.isSelected()) toGroup.add((BasicObject) s);
        }

        if (toGroup.size() >= 2) {
            shapes.removeAll(toGroup); // Remove individuals
            GroupObject group = new GroupObject(toGroup); // Wrap
            group.setSelected(true);
            shapes.add(group); // Add wrapper
            repaint();
        }
    }

    /**
     * Unwraps a single GroupObject back into its constituent BasicObjects.
     */
    public void ungroupObjects() {
        Shape target = null;
        int count = 0;

        for (Shape s : shapes) {
            if (s.isSelected()) {
                target = s;
                count++;
            }
        }

        // Domain Rule Validation: Only one GroupObject can be ungrouped at a time
        if (count == 1 && target instanceof GroupObject group) {
            shapes.remove(group); // Remove wrapper
            List<BasicObject> members = group.getMembers(); // Extract
            shapes.addAll(members); // Restore individuals
            for (BasicObject bo : members) bo.setSelected(true);
            repaint();
        }
    }

    /**
     * The core rendering loop.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Enables antialiasing for smooth lines and shapes
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Render all permanent entities (Shapes, Links)
        for (Shape s : shapes) {
            s.draw(g2d);
        }

        // Render ephemeral UI elements from the active Mode (e.g., selection box, drag lines)
        if (currentMode != null) {
            currentMode.drawOverlay(g2d);
        }
    }
}