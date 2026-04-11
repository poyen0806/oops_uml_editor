package shape;

import java.awt.Graphics2D;

public abstract class Shape {
    public abstract void draw(Graphics2D g2d);

    public Port findPortAt(int x, int y) {
        return null;
    }
}