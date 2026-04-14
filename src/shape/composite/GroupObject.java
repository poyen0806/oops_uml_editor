package shape.composite;

import shape.basic.BasicObject;

import java.awt.*;
import java.util.List;

public class GroupObject extends BasicObject {
    private static final Color BORDER_COLOR = new Color(100, 149, 237, 120);
    private static final int INITIAL_VAL = 0;
    private static final float[] DASH_PATTERN = { 9.0f };
    private static final float DASH_PHASE = 0.0f;
    private static final float LINE_WIDTH = 1.0f;

    private final List<BasicObject> members;

    public GroupObject(List<BasicObject> members) {
        super(INITIAL_VAL, INITIAL_VAL, INITIAL_VAL, INITIAL_VAL);
        this.members = members;
        updateBounds();
    }

    private void updateBounds() {
        if (members.isEmpty()) return;

        int minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE, maxY = Integer.MIN_VALUE;

        for (BasicObject bo : members) {
            minX = Math.min(minX, bo.getX());
            minY = Math.min(minY, bo.getY());
            maxX = Math.max(maxX, bo.getX() + bo.getWidth());
            maxY = Math.max(maxY, bo.getY() + bo.getHeight());
        }

        this.x = minX;
        this.y = minY;
        this.width = maxX - minX;
        this.height = maxY - minY;
    }

    @Override
    public void draw(Graphics2D g2d) {
        for (BasicObject bo : members) {
            boolean wasSelected = bo.isSelected();
            boolean wasHovered = bo.isHovered();

            // 繪圖時暫時關閉成員 Port
            bo.setSelected(false);
            bo.setHovered(false);

            bo.draw(g2d);
            bo.setSelected(wasSelected);
            bo.setHovered(wasHovered);
        }

        if (isSelected || isHovered) {
            Stroke oldStroke = g2d.getStroke();
            g2d.setColor(BORDER_COLOR);
            g2d.setStroke(new BasicStroke(LINE_WIDTH, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, DASH_PATTERN, DASH_PHASE));
            g2d.drawRect(x, y, width, height);
            g2d.setStroke(oldStroke);
        }
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        for (BasicObject bo : members) {
            bo.setSelected(selected);
        }
    }

    @Override
    public void setHovered(boolean hovered) {
        super.setHovered(hovered);
        for (BasicObject bo : members) {
            bo.setHovered(hovered);
        }
    }

    @Override
    public void move(int dx, int dy) {
        super.move(dx, dy);
        for (BasicObject bo : members) {
            bo.move(dx, dy);
        }
    }

    @Override
    protected void createPorts() {
        // Group 不產生 Ports
    }

    public List<BasicObject> getMembers() {
        return members;
    }
}