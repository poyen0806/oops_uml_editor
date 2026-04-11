package shape;

import java.awt.*;
import java.util.List;

public class GroupObject extends BasicObject {
    private static final Color GROUP_BORDER_COLOR = new Color(100, 149, 237, 120);
    private static final int INITIAL_PLACEHOLDER = 0;

    private static final float[] DASH_PATTERN = { 9.0f };
    private static final float DASH_PHASE = 0.0f;
    private static final float LINE_WIDTH = 1.0f;

    private final List<BasicObject> members;

    public GroupObject(List<BasicObject> members) {
        super(INITIAL_PLACEHOLDER, INITIAL_PLACEHOLDER, INITIAL_PLACEHOLDER, INITIAL_PLACEHOLDER);
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
            boolean originalSelected = bo.isSelected();

            bo.setSelected(false);

            bo.draw(g2d);

            bo.setSelected(originalSelected);
        }

        if (isSelected) {
            g2d.setColor(GROUP_BORDER_COLOR);
            Stroke dashed = new BasicStroke(LINE_WIDTH, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, DASH_PATTERN, DASH_PHASE);
            g2d.setStroke(dashed);
            g2d.drawRect(x, y, width, height);
            g2d.setStroke(new BasicStroke(LINE_WIDTH));
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
    protected void createPorts() {
        // Group 不產生 Ports
    }

    public List<BasicObject> getMembers() {
        return members;
    }
}