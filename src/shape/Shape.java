package shape;

import java.awt.*;
import java.awt.Rectangle;

public abstract class Shape {
    public abstract void draw(Graphics2D g2d);

    public void setSelected(boolean selected) {}
    public boolean isSelected() { return false; }

    public void setHovered(boolean hovered) {}
    public boolean isHovered() { return false; }

    public boolean isContained(int px, int py) { return false; }
    public Rectangle getBounds() { return null; }

    public void move(int dx, int dy) {}

    public Port findPortAt(int x, int y) {
        return null;
    }
}