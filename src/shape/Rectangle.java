package shape;

import java.awt.Color;
import java.awt.Graphics2D;

public class Rectangle extends Shape {
    private final int x, y;

    private static final int WIDTH = 100;
    private static final int HEIGHT = 100;

    public Rectangle(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.WHITE);
        g2d.fillRect(x, y, WIDTH, HEIGHT);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(x, y, WIDTH, HEIGHT);
    }
}