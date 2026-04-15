package shape.basic;

import shape.Port;

import java.awt.*;

/**
 * Concrete implementation of a BasicObject representing an elliptical entity
 */
public class Oval extends BasicObject {

    // Default dimensions used when instantiated via the ToolBar's CreateMode
    private static final int DEFAULT_WIDTH = 100;
    private static final int DEFAULT_HEIGHT = 80;

    public Oval(int x, int y) {
        super(x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    /**
     * Defines a 4-point connection topology for Ovals.
     */
    @Override
    protected void createPorts() {
        // Edge midpoints
        ports.add(new Port(this, Port.Direction.N));
        ports.add(new Port(this, Port.Direction.S));
        ports.add(new Port(this, Port.Direction.W));
        ports.add(new Port(this, Port.Direction.E));
    }

    /**
     * The specific rendering pipeline for an Oval.
     */
    @Override
    public void draw(Graphics2D g2d) {
        // 1. Draw the background fill
        g2d.setColor(fillColor);
        g2d.fillOval(x, y, width, height);

        // 2. Draw the outer structural border
        g2d.setColor(Color.GRAY);
        g2d.drawOval(x, y, width, height);

        // 3. Delegate to parent to conditionally render labels and connection ports
        super.drawPorts(g2d);
    }
}