package shape;

import java.awt.*;

public class Oval extends BasicObject {
    private static final int DEFAULT_WIDTH = 100;
    private static final int DEFAULT_HEIGHT = 80;

    public Oval(int x, int y) {
        super(x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        createPorts();
    }

    @Override
    protected void createPorts() {
        int halfW = width / 2;
        int halfH = height / 2;
        ports.add(new Port(this, halfW, 0));
        ports.add(new Port(this, halfW, height));
        ports.add(new Port(this, 0, halfH));
        ports.add(new Port(this, width, halfH));
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.WHITE);
        g2d.fillOval(x, y, width, height);
        g2d.setColor(Color.BLACK);
        g2d.drawOval(x, y, width, height);
    }
}