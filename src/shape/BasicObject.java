package shape;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class BasicObject extends Shape {
    private static final int PORT_VISUAL_SIZE = 6;
    private static final int PORT_OFFSET = PORT_VISUAL_SIZE / 2;

    protected int x, y, width, height;
    protected List<Port> ports = new ArrayList<>();
    protected boolean isSelected = false;

    public BasicObject(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    protected abstract void createPorts();

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setSelected(boolean selected) {
        this.isSelected = selected;
    }

    @Override
    public Port findPortAt(int mx, int my) {
        for (Port p : ports) {
            if (p.isHit(mx, my)) return p;
        }
        return null;
    }

    protected void drawPorts(Graphics2D g2d) {
        if (!isSelected) return;
        g2d.setColor(Color.BLACK);
        for (Port p : ports) {
            Point loc = p.getAbsoluteLocation();
            g2d.fillRect(loc.x - PORT_OFFSET, loc.y - PORT_OFFSET, PORT_VISUAL_SIZE, PORT_VISUAL_SIZE);
        }
    }
}