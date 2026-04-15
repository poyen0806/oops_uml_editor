package shape.link;

import shape.Port;

import java.awt.*;

/**
 * Concrete implementation of a UML Composition Link.
 * Provides the geometric algorithm to render a diamond-shaped arrowhead at the destination port.
 */
public class CompositionLine extends Line {

    // Geometric constants for the diamond arrowhead
    private static final int DIAMOND_SIZE = 12;
    private static final int VERTEX_COUNT = 4;

    public CompositionLine(Port start, Port end) {
        super(start, end);
    }

    /**
     * Uses trigonometry (polar coordinate projection) to dynamically calculate
     * the four vertices of the diamond polygon based on the line's current angle.
     */
    @Override
    protected void drawArrow(Graphics2D g2d, Point p1, Point p2) {
        // 1. Calculate the base trajectory angle from start to end
        double angle = Math.atan2(p2.y - p1.y, p2.x - p1.x);

        // Vertex 1: The tip of the diamond (touching the destination port)
        int x2 = p2.x;
        int y2 = p2.y;

        // Calculate the center point of the diamond
        int xMid = (int) (p2.x - DIAMOND_SIZE * Math.cos(angle));
        int yMid = (int) (p2.y - DIAMOND_SIZE * Math.sin(angle));

        // Vertex 2 & 3: The left and right corners of the diamond
        // Achieved by shifting 90 degrees (+/- PI/2) from the line's trajectory
        int xLeft = (int) (xMid - (DIAMOND_SIZE / 2.0) * Math.cos(angle - Math.PI / 2));
        int yLeft = (int) (yMid - (DIAMOND_SIZE / 2.0) * Math.sin(angle - Math.PI / 2));

        int xRight = (int) (xMid - (DIAMOND_SIZE / 2.0) * Math.cos(angle + Math.PI / 2));
        int yRight = (int) (yMid - (DIAMOND_SIZE / 2.0) * Math.sin(angle + Math.PI / 2));

        // Vertex 4: The tail of the diamond (connecting back to the main line)
        int xTail = (int) (p2.x - 2 * DIAMOND_SIZE * Math.cos(angle));
        int yTail = (int) (p2.y - 2 * DIAMOND_SIZE * Math.sin(angle));

        int[] xPoints = {x2, xLeft, xTail, xRight};
        int[] yPoints = {y2, yLeft, yTail, yRight};

        // 2. Render the polygon: Fill the background first, then draw the border outline
        g2d.setColor(Color.WHITE);
        g2d.fillPolygon(xPoints, yPoints, VERTEX_COUNT);
        g2d.setColor(Color.BLACK);
        g2d.drawPolygon(xPoints, yPoints, VERTEX_COUNT);
    }
}