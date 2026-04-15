package shape.link;

import shape.Port;
import shape.Shape;

import java.awt.*;

/**
 * Abstract base class for all UML connection lines.
 */
public abstract class Line extends Shape {

    // Structural Dependencies: A Line strictly requires two valid endpoint Ports.
    // Storing the Port objects (instead of Point coordinates) allows dynamic spatial querying.
    protected final Port startPort;
    protected final Port endPort;

    public Line(Port start, Port end) {
        this.startPort = start;
        this.endPort = end;
    }

    /**
     * The core rendering pipeline for all lines.
     * Implements a "Just-In-Time" coordinate resolution strategy.
     */
    @Override
    public void draw(Graphics2D g2d) {
        // 1. Dynamically fetch the latest absolute coordinates from the attached Ports
        Point p1 = startPort.getAbsoluteLocation();
        Point p2 = endPort.getAbsoluteLocation();

        // 2. Draw the primary connecting segment
        g2d.setColor(Color.BLACK);
        g2d.drawLine(p1.x, p1.y, p2.x, p2.y);

        // 3. Delegate the arrowhead drawing to the concrete subclass
        drawArrow(g2d, p1, p2);
    }

    /**
     * Template Method Hook.
     * Defers the geometric calculation and rendering of the specific UML arrowhead
     * (e.g., solid diamond, hollow triangle, open arrow) to the concrete subclasses.
     */
    protected abstract void drawArrow(Graphics2D g2d, Point p1, Point p2);
}