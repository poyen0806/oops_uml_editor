package shape.basic;

import shape.Port;
import shape.Shape;

import java.awt.*;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract base class for all fundamental 2D entities (e.g., Rectangles, Ovals).
 * Manages core spatial geometry, state data (selection/hover), custom labels,
 * and the lifecycle of connection Ports.
 */
public abstract class BasicObject extends Shape {

    // UI/UX Constraints and Styling Constants
    private static final int MIN_SIZE = 20;
    private static final int PORT_VISUAL_SIZE = 6;
    private static final int PORT_DRAW_OFFSET = PORT_VISUAL_SIZE / 2;
    private static final Color DEFAULT_FILL_COLOR = new Color(255, 255, 255);
    private static final Color TEXT_COLOR = Color.BLACK;

    // Spatial Geometry
    protected int x, y, width, height;

    // A BasicObject manages a collection of connection anchors (Ports)
    protected List<Port> ports = new ArrayList<>();

    // Interaction States
    protected boolean isSelected = false;
    protected boolean isHovered = false;

    // Customization Attributes
    protected String labelName = "";
    protected Color fillColor = DEFAULT_FILL_COLOR;

    public BasicObject(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        createPorts(); // Initializes ports immediately upon instantiation
    }

    /**
     * Defers the specific Port generation strategy to concrete subclasses,
     * as different shapes require different port layouts.
     */
    protected abstract void createPorts();

    public void updateLabel(String name, Color color) {
        this.labelName = name;
        this.fillColor = color;
    }

    /**
     * Renders text dynamically centered within the shape's bounding box
     * using real-time FontMetrics calculations.
     */
    protected void drawLabel(Graphics2D g2d) {
        if (labelName == null || labelName.isEmpty()) return;

        g2d.setColor(TEXT_COLOR);
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(labelName);
        int textHeight = fm.getAscent();

        // Calculates perfect center alignment
        int lx = x + (width - textWidth) / 2;
        int ly = y + (height + textHeight) / 2 - fm.getDescent();

        g2d.drawString(labelName, lx, ly);
    }

    /**
     * Core Resizing Engine.
     * Recalculates the bounding box based on the dragged Port's direction.
     * Enforces a minimum size constraint and handles inverted dragging
     */
    public void resize(Port.Direction dir, int mouseX, int mouseY, Point anchor) {
        String d = dir.name();

        // Determine new boundaries based on which corner/edge is being pulled
        int nL = d.contains("W") ? mouseX : (d.contains("E") ? anchor.x : x);
        int nR = d.contains("E") ? mouseX : (d.contains("W") ? anchor.x : x + width);
        int nT = d.contains("N") ? mouseY : (d.contains("S") ? anchor.y : y);
        int nB = d.contains("S") ? mouseY : (d.contains("N") ? anchor.y : y + height);

        // Domain Rule: Prevent the object from collapsing into a singularity or flipping
        if (Math.abs(nR - nL) < MIN_SIZE) nL = (nL < nR) ? nR - MIN_SIZE : nR + MIN_SIZE;
        if (Math.abs(nB - nT) < MIN_SIZE) nT = (nT < nB) ? nB - MIN_SIZE : nB + MIN_SIZE;

        // Apply normalized coordinates (ensuring width/height are always positive)
        this.x = Math.min(nL, nR);
        this.y = Math.min(nT, nB);
        this.width = Math.abs(nR - nL);
        this.height = Math.abs(nB - nT);

        // Triggers the subclass to verify/re-initialize port structures if necessary
        createPorts();
    }

    @Override
    public void move(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }

    @Override
    public Port findPortAt(int mx, int my) {
        // Delegates hit-testing down to the individual Port components
        for (Port p : ports) if (p.isHit(mx, my)) return p;
        return null;
    }

    /**
     * Conditionally renders connection anchors to provide visual affordance
     * only when the user is actively interacting with or selecting the object.
     */
    protected void drawPorts(Graphics2D g2d) {
        drawLabel(g2d);
        if (!isSelected && !isHovered) return;
        g2d.setColor(Color.BLACK);
        for (Port p : ports) {
            Point loc = p.getAbsoluteLocation();
            g2d.fillRect(loc.x - PORT_DRAW_OFFSET, loc.y - PORT_DRAW_OFFSET,
                    PORT_VISUAL_SIZE, PORT_VISUAL_SIZE);
        }
    }

    public String getLabelName() {
        return labelName;
    }

    public Color getFillColor() {
        return fillColor;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public void setSelected(boolean s) {
        this.isSelected = s;
    }

    @Override
    public boolean isSelected() {
        return isSelected;
    }

    @Override
    public void setHovered(boolean h) {
        this.isHovered = h;
    }

    @Override
    public boolean isHovered() {
        return isHovered;
    }

    @Override
    public boolean isContained(int px, int py) {
        return px >= x && px <= x + width && py >= y && py <= y + height;
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}