package shape;

import shape.basic.BasicObject;

import java.awt.*;

/**
 * Represents a connection anchor on a BasicObject.
 * Facilitates shape linking and acts as the interaction handle for resizing operations.
 */
public class Port {

    // Defines the 8 cardinal and ordinal anchor positions
    public enum Direction {
        N, S, E, W,
        NW, NE, SW, SE
    }

    // Hit-box tolerance for mouse interactions
    private static final int SENSITIVITY_RADIUS = 5;

    // Structural relationship: A Port belongs strictly to one BasicObject
    private final BasicObject parent;
    private final Direction direction;

    public Port(BasicObject parent, Direction direction) {
        this.parent = parent;
        this.direction = direction;
    }

    /**
     * Dynamically calculates the Port's current screen coordinates.
     * By querying the parent's spatial data in real-time, this ensures
     * attached Links automatically sync their endpoints when the parent is resized.
     */
    public Point getAbsoluteLocation() {
        int x = parent.getX();
        int y = parent.getY();
        int w = parent.getWidth();
        int h = parent.getHeight();

        return switch (direction) {
            case NW -> new Point(x, y);
            case NE -> new Point(x + w, y);
            case SW -> new Point(x, y + h);
            case SE -> new Point(x + w, y + h);
            case N  -> new Point(x + w / 2, y);
            case S  -> new Point(x + w / 2, y + h);
            case W  -> new Point(x, y + h / 2);
            case E  -> new Point(x + w, y + h / 2);
        };
    }

    /**
     * Hit-testing logic using the circular distance formula.
     * Determines if a mouse coordinate falls within the Port's interactive radius.
     */
    public boolean isHit(int px, int py) {
        Point loc = getAbsoluteLocation();
        return Math.pow(px - loc.x, 2) + Math.pow(py - loc.y, 2) <= Math.pow(SENSITIVITY_RADIUS, 2);
    }

    public BasicObject getParent() {
        return parent;
    }

    public Direction getDirection() { return direction; }
}