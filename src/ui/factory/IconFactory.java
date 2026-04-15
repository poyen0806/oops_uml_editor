package ui.factory;

import javax.swing.Icon;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;

/**
 * A Factory utility class responsible for procedurally generating all UI icons.
 * Replaces the need for external image files (e.g., PNGs), ensuring the UI
 * remains lightweight, resolution-independent, and perfectly themed.
 */
public class IconFactory {

    // --- Global Metric & Style Constants ---
    private static final int ICON_WIDTH = 40;
    private static final int ICON_HEIGHT = 40;
    private static final Color FILL_COLOR = Color.GRAY;
    private static final Color BORDER_COLOR = Color.DARK_GRAY;
    private static final int STROKE_WIDTH = 2;

    // --- Basic Shapes Geometry (Rect, Oval) ---
    private static final int SHAPE_SIZE = 20;
    private static final int SHAPE_PADDING = 10;
    private static final int OVAL_WIDTH = 28;
    private static final int OVAL_PADDING_X = 6;

    // --- Select Cursor Geometry ---
    // Represents the 7 vertices of a traditional mouse pointer arrow
    private static final int[] SELECT_X = { 12, 12, 16, 20, 24, 19, 27 };
    private static final int[] SELECT_Y = { 10, 28, 23, 31, 29, 21, 21 };

    // --- Association Line Geometry ---
    private static final int ASSOC_LINE_START_X = 30;
    private static final int ASSOC_LINE_END_X = 10;
    private static final int ASSOC_LINE_Y = 20;
    private static final int ASSOC_ARROW_TOP_X = 18;
    private static final int ASSOC_ARROW_TOP_Y = 12;
    private static final int ASSOC_ARROW_BOTTOM_Y = 28;

    // --- Generalization Line Geometry ---
    private static final int[] GEN_ARROW_X = { 20, 20, 10 };
    private static final int[] GEN_ARROW_Y = { 12, 28, 20 };
    private static final int GEN_LINE_START_X = 30;
    private static final int GEN_LINE_END_X = 20;
    private static final int GEN_LINE_Y = 20;

    // --- Composition Line Geometry ---
    private static final int[] COMP_DIAMOND_X = { 6, 12, 18, 12 };
    private static final int[] COMP_DIAMOND_Y = { 20, 14, 20, 26 };
    private static final int COMP_LINE_START_X = 18;
    private static final int COMP_LINE_END_X = 30;
    private static final int COMP_LINE_Y = 20;

    /**
     * Abstract base class providing common rendering context and geometry helpers.
     * Prevents code duplication across the concrete Icon implementations.
     */
    private static abstract class BaseIcon implements Icon {
        @Override
        public int getIconWidth() { return ICON_WIDTH; }

        @Override
        public int getIconHeight() { return ICON_HEIGHT; }

        /**
         * Standardizes the graphics context, enabling antialiasing for smooth edges.
         */
        protected void setupGraphics(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setStroke(new BasicStroke(STROKE_WIDTH));
        }

        /**
         * Helper method to translate base geometric coordinates into the actual
         * rendering space of the icon bounding box.
         */
        protected Polygon createShiftedPolygon(int[] baseX, int[] baseY, int shiftX, int shiftY) {
            int length = baseX.length;
            int[] px = new int[length];
            int[] py = new int[length];
            for (int i = 0; i < length; i++) {
                px[i] = baseX[i] + shiftX;
                py[i] = baseY[i] + shiftY;
            }
            return new Polygon(px, py, length);
        }
    }

    // --- Factory Methods for Generating Specific Icons via Anonymous Inner Classes ---

    public static Icon createSelectIcon() {
        return new BaseIcon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                setupGraphics(g);
                Polygon p = createShiftedPolygon(SELECT_X, SELECT_Y, x, y);
                g.setColor(FILL_COLOR);
                g.fillPolygon(p);
                g.setColor(BORDER_COLOR);
                g.drawPolygon(p);
            }
        };
    }

    public static Icon createAssociationIcon() {
        return new BaseIcon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                setupGraphics(g);
                g.setColor(BORDER_COLOR);
                g.drawLine(x + ASSOC_LINE_START_X, y + ASSOC_LINE_Y, x + ASSOC_LINE_END_X, y + ASSOC_LINE_Y);
                g.drawLine(x + ASSOC_ARROW_TOP_X, y + ASSOC_ARROW_TOP_Y, x + ASSOC_LINE_END_X, y + ASSOC_LINE_Y);
                g.drawLine(x + ASSOC_ARROW_TOP_X, y + ASSOC_ARROW_BOTTOM_Y, x + ASSOC_LINE_END_X, y + ASSOC_LINE_Y);
            }
        };
    }

    public static Icon createGeneralizationIcon() {
        return new BaseIcon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                setupGraphics(g);
                Polygon p = createShiftedPolygon(GEN_ARROW_X, GEN_ARROW_Y, x, y);
                g.setColor(FILL_COLOR);
                g.fillPolygon(p);
                g.setColor(BORDER_COLOR);
                g.drawPolygon(p);
                g.drawLine(x + GEN_LINE_START_X, y + GEN_LINE_Y, x + GEN_LINE_END_X, y + GEN_LINE_Y);
            }
        };
    }

    public static Icon createCompositionIcon() {
        return new BaseIcon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                setupGraphics(g);
                Polygon p = createShiftedPolygon(COMP_DIAMOND_X, COMP_DIAMOND_Y, x, y);
                g.setColor(FILL_COLOR);
                g.fillPolygon(p);
                g.setColor(BORDER_COLOR);
                g.drawPolygon(p);
                g.drawLine(x + COMP_LINE_START_X, y + COMP_LINE_Y, x + COMP_LINE_END_X, y + COMP_LINE_Y);
            }
        };
    }

    public static Icon createRectIcon() {
        return new BaseIcon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                setupGraphics(g);
                g.setColor(FILL_COLOR);
                g.fillRect(x + SHAPE_PADDING, y + SHAPE_PADDING, SHAPE_SIZE, SHAPE_SIZE);
                g.setColor(BORDER_COLOR);
                g.drawRect(x + SHAPE_PADDING, y + SHAPE_PADDING, SHAPE_SIZE, SHAPE_SIZE);
            }
        };
    }

    public static Icon createOvalIcon() {
        return new BaseIcon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                setupGraphics(g);
                g.setColor(FILL_COLOR);
                g.fillOval(x + OVAL_PADDING_X, y + SHAPE_PADDING, OVAL_WIDTH, SHAPE_SIZE);
                g.setColor(BORDER_COLOR);
                g.drawOval(x + OVAL_PADDING_X, y + SHAPE_PADDING, OVAL_WIDTH, SHAPE_SIZE);
            }
        };
    }
}