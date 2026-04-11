package shape;

import java.awt.Rectangle;
import java.awt.Graphics2D;

public abstract class Shape {
    public abstract void draw(Graphics2D g2d);

    public void setSelected(boolean selected) {}
    public boolean isSelected() { return false; }

    public void setHovered(boolean hovered) {}
    public boolean isHovered() { return false; }

    public boolean isContained(int px, int py) { return false; }
    public Rectangle getBounds() { return null; }

    public Port findPortAt(int x, int y) {
        return null;
    }

}