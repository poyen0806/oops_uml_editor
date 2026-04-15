package shape.link;

import shape.Port;

import java.awt.*;

/**
 * Concrete implementation of a UML Generalization Link.
 * Provides the geometric algorithm to render a hollow triangular arrowhead at the destination port.
 */
public class GeneralizationLine extends Line {

    // Geometric constant defining the side length of the triangular arrowhead
    private static final int TRIANGLE_SIZE = 12;

    public GeneralizationLine(Port start, Port end) {
        super(start, end);
    }

    /**
     * Uses trigonometry to dynamically calculate the three vertices of the triangle
     * based on the line's current trajectory angle.
     */
    @Override
    protected void drawArrow(Graphics2D g2d, Point p1, Point p2) {
        // 1. Calculate the base trajectory angle from start to end
        double angle = Math.atan2(p2.y - p1.y, p2.x - p1.x);

        // 2. Calculate the three vertices of the triangle
        // Vertex 1: The tip of the triangle (touching the destination port)
        // Vertex 2 & 3: The base corners, calculated by projecting backward from the tip
        // using a 30-degree spread (+/- PI/6 radians)
        int[] xPoints = {
                p2.x,
                (int) (p2.x - TRIANGLE_SIZE * Math.cos(angle - Math.PI / 6)),
                (int) (p2.x - TRIANGLE_SIZE * Math.cos(angle + Math.PI / 6))
        };
        int[] yPoints = {
                p2.y,
                (int) (p2.y - TRIANGLE_SIZE * Math.sin(angle - Math.PI / 6)),
                (int) (p2.y - TRIANGLE_SIZE * Math.sin(angle + Math.PI / 6))
        };

        // 3. Render the hollow triangle
        // Fills with white first to overwrite any underlying lines, creating the standard UML "hollow" look
        g2d.setColor(Color.WHITE);
        g2d.fillPolygon(xPoints, yPoints, 3);

        // Draws the structural black outline
        g2d.setColor(Color.BLACK);
        g2d.drawPolygon(xPoints, yPoints, 3);
    }
}