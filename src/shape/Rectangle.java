package shape;

import java.awt.*;

public class Rectangle extends BasicObject {
    private static final int DEFAULT_WIDTH = 100;
    private static final int DEFAULT_HEIGHT = 80;

    public Rectangle(int x, int y) {
        super(x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        createPorts();
    }

    @Override
    protected void createPorts() {
        int halfW = width / 2;
        int halfH = height / 2;

        int[][] portOffsets = {
                {0, 0},
                {halfW, 0},
                {width, 0},
                {width, halfH},
                {width, height},
                {halfW, height},
                {0, height},
                {0, halfH}
        };

        for (int[] offset : portOffsets) {
            ports.add(new Port(this, offset[0], offset[1]));
        }
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.WHITE);
        g2d.fillRect(x, y, width, height);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(x, y, width, height);

        super.drawPorts(g2d);
    }
}