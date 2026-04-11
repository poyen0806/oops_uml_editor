package shape;

import java.awt.*;

public class Oval extends BasicObject {
    private static final int DEFAULT_WIDTH = 100;
    private static final int DEFAULT_HEIGHT = 80;

    public Oval(int x, int y) {
        super(x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    @Override
    protected void createPorts() {
        ports.clear();
        int hw = width / 2;
        int hh = height / 2;

        // 四邊中點
        ports.add(new Port(this, Port.Direction.N, hw, 0));
        ports.add(new Port(this, Port.Direction.S, hw, height));
        ports.add(new Port(this, Port.Direction.W, 0, hh));
        ports.add(new Port(this, Port.Direction.E, width, hh));
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.WHITE);
        g2d.fillOval(x, y, width, height);
        g2d.setColor(Color.BLACK);
        g2d.drawOval(x, y, width, height);
        super.drawPorts(g2d);
    }
}