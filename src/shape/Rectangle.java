package shape;

import java.awt.*;

public class Rectangle extends BasicObject {
    private static final int DEFAULT_WIDTH = 100;
    private static final int DEFAULT_HEIGHT = 80;

    public Rectangle(int x, int y) {
        super(x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    @Override
    protected void createPorts() {
        int hw = width / 2;
        int hh = height / 2;

        // 角落
        ports.add(new Port(this, Port.Direction.NW, 0, 0));
        ports.add(new Port(this, Port.Direction.NE, width, 0));
        ports.add(new Port(this, Port.Direction.SW, 0, height));
        ports.add(new Port(this, Port.Direction.SE, width, height));

        // 四邊中點
        ports.add(new Port(this, Port.Direction.N, hw, 0));
        ports.add(new Port(this, Port.Direction.S, hw, height));
        ports.add(new Port(this, Port.Direction.W, 0, hh));
        ports.add(new Port(this, Port.Direction.E, width, hh));
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(fillColor);
        g2d.fillRect(x, y, width, height);
        g2d.setColor(Color.GRAY);
        g2d.drawRect(x, y, width, height);
        super.drawPorts(g2d);
    }
}