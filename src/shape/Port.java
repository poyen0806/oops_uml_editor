package shape;

import shape.basic.BasicObject;

import java.awt.*;

public class Port {
    public enum Direction {
        N, S, E, W,
        NW, NE, SW, SE
    }

    private static final int SENSITIVITY_RADIUS = 5;
    private final BasicObject parent;
    private final Direction direction;
    private final int offsetX;
    private final int offsetY;

    public Port(BasicObject parent, Direction direction, int offsetX, int offsetY) {
        this.parent = parent;
        this.direction = direction;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

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

    public boolean isHit(int px, int py) {
        Point loc = getAbsoluteLocation();
        return Math.pow(px - loc.x, 2) + Math.pow(py - loc.y, 2) <= Math.pow(SENSITIVITY_RADIUS, 2);
    }

    public BasicObject getParent() {
        return parent;
    }

    public Direction getDirection() { return direction; }
}