package shape;

import java.awt.*;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public abstract class BasicObject extends Shape {
    private static final int PORT_VISUAL_SIZE = 6;
    private static final int PORT_OFFSET = PORT_VISUAL_SIZE / 2;

    protected int x, y, width, height;
    protected List<Port> ports = new ArrayList<>();
    protected boolean isSelected = false;
    protected boolean isHovered = false;

    public BasicObject(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    protected abstract void createPorts();

    @Override
    public void setSelected(boolean selected) { this.isSelected = selected; }
    @Override
    public boolean isSelected() { return isSelected; }

    @Override
    public void setHovered(boolean hovered) { this.isHovered = hovered; }
    @Override
    public boolean isHovered() { return isHovered; }

    @Override
    public boolean isContained(int px, int py) {
        return px >= x && px <= x + width && py >= y && py <= y + height;
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    @Override
    public Port findPortAt(int mx, int my) {
        for (Port p : ports) {
            if (p.isHit(mx, my)) return p;
        }
        return null;
    }

    @Override
    public void move(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }

    protected void drawPorts(Graphics2D g2d) {
        if (!isSelected && !isHovered) return;
        g2d.setColor(Color.BLACK);
        for (Port p : ports) {
            Point loc = p.getAbsoluteLocation();
            g2d.fillRect(loc.x - PORT_OFFSET, loc.y - PORT_OFFSET, PORT_VISUAL_SIZE, PORT_VISUAL_SIZE);
        }
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
}