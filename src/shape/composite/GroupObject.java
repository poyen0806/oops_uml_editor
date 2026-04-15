package shape.composite;

import shape.basic.BasicObject;

import java.awt.*;
import java.util.List;

/**
 * Concrete implementation of the Composite Pattern.
 * A GroupObject is treated as a single BasicObject, but it acts as a container
 * that delegates operations (move, draw, select) to a collection of child BasicObjects.
 */
public class GroupObject extends BasicObject {

    // Visual styling for the dashed bounding box that indicates a grouped state
    private static final Color BORDER_COLOR = new Color(100, 149, 237, 120);
    private static final int INITIAL_VAL = 0;
    private static final float[] DASH_PATTERN = { 9.0f };
    private static final float DASH_PHASE = 0.0f;
    private static final float LINE_WIDTH = 1.0f;

    // Children of this node in the tree structure
    private final List<BasicObject> members;

    public GroupObject(List<BasicObject> members) {
        // Initializes with dummy coordinates; actual bounds are calculated immediately after
        super(INITIAL_VAL, INITIAL_VAL, INITIAL_VAL, INITIAL_VAL);
        this.members = members;
        updateBounds();
    }

    /**
     * Calculates the Minimum Bounding Rectangle (MBR) that fully encapsulates
     * all child members. Called upon instantiation to define the group's clickable area.
     */
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
        // 1. Delegate rendering to children
        for (BasicObject bo : members) {
            boolean wasSelected = bo.isSelected();
            boolean wasHovered = bo.isHovered();

            // Visual Trick: Temporarily suppress child selection states during rendering.
            // This prevents internal Ports from being drawn, maintaining the illusion
            // of a single unified object.
            bo.setSelected(false);
            bo.setHovered(false);

            bo.draw(g2d);

            // Restore actual state
            bo.setSelected(wasSelected);
            bo.setHovered(wasHovered);
        }

        // 2. Draw the Group's own dashed bounding box if it is currently targeted
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
        super.move(dx, dy); // Moves the group's bounding box
        for (BasicObject bo : members) {
            bo.move(dx, dy); // Moves every child concurrently
        }
    }

    /**
     * Domain Rule: Groups represent logical containers, not physical entities.
     * Therefore, they do not possess connection Ports of their own.
     */
    @Override
    protected void createPorts() {
        // Intentionally left blank
    }

    public List<BasicObject> getMembers() {
        return members;
    }
}