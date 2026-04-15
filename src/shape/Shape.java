package shape;

import java.awt.*;
import java.awt.Rectangle;

/**
 * The foundational abstract base class for all graphical entities in the editor.
 * Acts as the 'Component' interface in the Composite Pattern, establishing a
 * uniform contract for rendering, hit-testing, and state management across
 * basic objects, lines, and groups.
 */
public abstract class Shape {

    /**
     * The core rendering contract.
     * Every concrete shape must define how it draws itself onto the graphics context.
     */
    public abstract void draw(Graphics2D g2d);

    public void setSelected(boolean selected) {}
    public boolean isSelected() { return false; }

    public void setHovered(boolean hovered) {}
    public boolean isHovered() { return false; }

    /** * Point-in-polygon hit-testing.
     * Used primarily by the Canvas and SelectMode to detect direct mouse clicks on the object body.
     */
    public boolean isContained(int px, int py) { return false; }

    /** * Returns the bounding box of the shape.
     * Essential for calculating intersections during lasso area selection.
     */
    public Rectangle getBounds() { return null; }

    /** * Translates the shape spatially by a given delta (dx, dy).
     */
    public void move(int dx, int dy) {}

    /**
     * Connection resolution. Checks if a coordinate hits a connection Port.
     * Declared at the base level to allow polymorphic iteration over all shapes
     * in the Canvas, even though it's primarily implemented by BasicObject.
     */
    public Port findPortAt(int x, int y) {
        return null;
    }
}