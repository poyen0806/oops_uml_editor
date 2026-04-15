package shape.link;

import shape.Port;

import java.awt.*;

/**
 * Concrete implementation of a UML Association Link.
 * Provides the specific geometric algorithm to render an open arrowhead at the destination port.
 */
public class AssociationLine extends Line {

    // Geometric constants for the arrowhead's visual appearance
    private static final int ARROW_SIZE = 10;

    // The sweep angle (30 degrees in radians) for the two "wings" of the open arrow
    private static final double ARROW_ANGLE = Math.PI / 6;

    public AssociationLine(Port start, Port end) {
        super(start, end);
    }

    /**
     * Uses trigonometry (polar to Cartesian coordinate conversion) to calculate and draw
     * the two angled line segments that form the open "V" shape of an Association arrow.
     */
    @Override
    protected void drawArrow(Graphics2D g2d, Point p1, Point p2) {
        // 1. Calculate the base trajectory angle of the line segment from p1 to p2
        double angle = Math.atan2(p2.y - p1.y, p2.x - p1.x);

        // 2. Calculate the endpoint of the left wing by projecting backward from p2
        int x1 = (int) (p2.x - ARROW_SIZE * Math.cos(angle - ARROW_ANGLE));
        int y1 = (int) (p2.y - ARROW_SIZE * Math.sin(angle - ARROW_ANGLE));

        // 3. Calculate the endpoint of the right wing
        int x2 = (int) (p2.x - ARROW_SIZE * Math.cos(angle + ARROW_ANGLE));
        int y2 = (int) (p2.y - ARROW_SIZE * Math.sin(angle + ARROW_ANGLE));

        // 4. Render the open arrow (just two lines intersecting at p2)
        g2d.drawLine(p2.x, p2.y, x1, y1);
        g2d.drawLine(p2.x, p2.y, x2, y2);
    }
}