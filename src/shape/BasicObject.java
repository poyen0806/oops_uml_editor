package shape;

import java.awt.*;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public abstract class BasicObject extends Shape {
    private static final int MIN_SIZE = 20;
    private static final int PORT_VISUAL_SIZE = 6;
    private static final int PORT_DRAW_OFFSET = PORT_VISUAL_SIZE / 2;
    private static final Color PORT_COLOR = Color.BLACK;
    private static final Color OBJECT_FILL_COLOR = Color.WHITE;
    private static final Color OBJECT_LINE_COLOR = Color.BLACK;

    protected int x, y, width, height;
    protected List<Port> ports = new ArrayList<>();
    protected boolean isSelected = false;
    protected boolean isHovered = false;

    public BasicObject(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        createPorts();
    }

    protected abstract void createPorts();


    public void resize(Port.Direction dir, int mouseX, int mouseY, Point anchor) {
        String dirName = dir.name();

        int newL = dirName.contains("W") ? mouseX : (dirName.contains("E") ? anchor.x : this.x);
        int newR = dirName.contains("E") ? mouseX : (dirName.contains("W") ? anchor.x : this.x + this.width);
        int newT = dirName.contains("N") ? mouseY : (dirName.contains("S") ? anchor.y : this.y);
        int newB = dirName.contains("S") ? mouseY : (dirName.contains("N") ? anchor.y : this.y + this.height);

        if (Math.abs(newR - newL) < MIN_SIZE) {
            newL = (newL < newR) ? newR - MIN_SIZE : newR + MIN_SIZE;
        }
        if (Math.abs(newB - newT) < MIN_SIZE) {
            newT = (newT < newB) ? newB - MIN_SIZE : newB + MIN_SIZE;
        }

        this.x = Math.min(newL, newR);
        this.y = Math.min(newT, newB);
        this.width = Math.abs(newR - newL);
        this.height = Math.abs(newB - newT);

        createPorts();
    }

    @Override
    public void move(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }

    @Override
    public Port findPortAt(int mx, int my) {
        for (Port p : ports) if (p.isHit(mx, my)) return p;
        return null;
    }

    protected void drawPorts(Graphics2D g2d) {
        if (!isSelected && !isHovered) return;
        g2d.setColor(PORT_COLOR);
        for (Port p : ports) {
            Point loc = p.getAbsoluteLocation();
            g2d.fillRect(loc.x - PORT_DRAW_OFFSET, loc.y - PORT_DRAW_OFFSET,
                    PORT_VISUAL_SIZE, PORT_VISUAL_SIZE);
        }
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
}