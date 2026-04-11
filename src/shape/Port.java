package shape;

import java.awt.*;

public class Port {
    private static final int SENSITIVITY_RADIUS = 15;
    private final BasicObject parent;
    private final int offsetX;
    private final int offsetY;

    public Port(BasicObject parent, int offsetX, int offsetY) {
        this.parent = parent;
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
}