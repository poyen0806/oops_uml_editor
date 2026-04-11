package ui;

import shape.BasicObject;
import shape.Shape;

import javax.swing.*;
import java.awt.BorderLayout;
import java.util.List;

public class MainWindow extends JFrame {
    private static final String APP_TITLE = "Oops UML Editor";
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    private static final String MENU_FILE = "File";
    private static final String MENU_EDIT = "Edit";
    private static final String ITEM_GROUP = "Group";
    private static final String ITEM_UNGROUP = "Ungroup";
    private static final String ITEM_LABEL = "Label";

    private static final String ERROR_MSG_SINGLE_SELECT = "Select exactly one Basic Object.";
    private static final int REQUIRED_SELECTION_COUNT = 1;

    public MainWindow() {
        setTitle(APP_TITLE);
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        Canvas canvas = new Canvas();
        ToolBar toolBar = new ToolBar(canvas);

        setJMenuBar(createMenuBar(canvas));
        add(toolBar, BorderLayout.WEST);
        add(canvas, BorderLayout.CENTER);
        setLocationRelativeTo(null);
    }

    private JMenuBar createMenuBar(Canvas canvas) {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu(MENU_FILE);
        JMenu editMenu = new JMenu(MENU_EDIT);

        JMenuItem labelItem = new JMenuItem(ITEM_LABEL);
        labelItem.addActionListener(e -> {
            List<Shape> selected = canvas.getShapes().stream()
                    .filter(Shape::isSelected)
                    .toList();

            if (selected.size() == REQUIRED_SELECTION_COUNT && selected.getFirst() instanceof BasicObject obj) {
                LabelDialog dialog = new LabelDialog(this, obj.getLabelName(), obj.getFillColor());
                dialog.setVisible(true);

                if (dialog.isConfirmed()) {
                    obj.updateLabel(dialog.getLabelName(), dialog.getLabelColor());
                    canvas.repaint();
                }
            } else {
                JOptionPane.showMessageDialog(this, ERROR_MSG_SINGLE_SELECT);
            }
        });

        editMenu.add(new JMenuItem(ITEM_GROUP)).addActionListener(ev -> canvas.groupObjects());
        editMenu.add(new JMenuItem(ITEM_UNGROUP)).addActionListener(ev -> canvas.ungroupObjects());
        editMenu.addSeparator();
        editMenu.add(labelItem);

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        return menuBar;
    }
}