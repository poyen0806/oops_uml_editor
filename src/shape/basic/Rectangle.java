package shape.basic;

import shape.Port;

import java.awt.*;

/**
 * Concrete implementation of a BasicObject representing a rectangular entity
 */
public class Rectangle extends BasicObject {

    // Default dimensions used when instantiated via the ToolBar's CreateMode
    private static final int DEFAULT_WIDTH = 100;
    private static final int DEFAULT_HEIGHT = 80;

    public Rectangle(int x, int y) {
        super(x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    /**
     * Defines an 8-point connection topology for Rectangles,
     * allowing links to attach at corners and edge midpoints.
     */
    @Override
    protected void createPorts() {
        // Corners
        ports.add(new Port(this, Port.Direction.NW));
        ports.add(new Port(this, Port.Direction.NE));
        ports.add(new Port(this, Port.Direction.SW));
        ports.add(new Port(this, Port.Direction.SE));

        // Edge midpoints
        ports.add(new Port(this, Port.Direction.N));
        ports.add(new Port(this, Port.Direction.S));
        ports.add(new Port(this, Port.Direction.W));
        ports.add(new Port(this, Port.Direction.E));
    }

    /**
     * The specific rendering pipeline for a Rectangle.
     */
    @Override
    public void draw(Graphics2D g2d) {
        // 1. Draw the background fill
        g2d.setColor(fillColor);
        g2d.fillRect(x, y, width, height);

        // 2. Draw the outer structural border
        g2d.setColor(Color.GRAY);
        g2d.drawRect(x, y, width, height);

        // 3. Delegate to parent to conditionally render labels and connection ports
        super.drawPorts(g2d);
    }
}