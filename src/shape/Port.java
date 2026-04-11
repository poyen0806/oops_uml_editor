package shape;

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
        return new Point(parent.getX() + offsetX, parent.getY() + offsetY);
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