package ui;

import ui.component.Canvas;
import ui.component.ToolBar;
import ui.dialog.LabelDialog;
import shape.basic.BasicObject;
import shape.Shape;

import javax.swing.*;
import java.awt.BorderLayout;
import java.util.List;

/**
 * The primary View container for the application.
 * Orchestrates the main layout, delegates user commands to the Canvas,
 * and manages the top-level MenuBar interactions.
 */
public class MainWindow extends JFrame {

    // Window configuration
    private static final String APP_TITLE = "Oops UML Editor";
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    // Menu labels
    private static final String MENU_FILE = "File";
    private static final String MENU_EDIT = "Edit";
    private static final String ITEM_GROUP = "Group";
    private static final String ITEM_UNGROUP = "Ungroup";
    private static final String ITEM_LABEL = "Label";

    // Validation messages and rules
    private static final String ERROR_MSG_SINGLE_SELECT = "Select exactly one Basic Object.";
    private static final int REQUIRED_SELECTION_COUNT = 1;

    public MainWindow() {
        setTitle(APP_TITLE);
        setSize(WIDTH, HEIGHT);
        // Exit application when the window is closed
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Employs BorderLayout to strictly separate the tool palette and drawing area
        setLayout(new BorderLayout());

        // Initializes core components
        Canvas canvas = new Canvas();

        // Dependency Injection: Passing the shared Canvas instance to the ToolBar
        // so button clicks can update the Canvas's active Mode
        ToolBar toolBar = new ToolBar(canvas);

        setJMenuBar(createMenuBar(canvas));
        add(toolBar, BorderLayout.WEST);
        add(canvas, BorderLayout.CENTER);
        setLocationRelativeTo(null);
    }

    /**
     * Constructs the application's menu bar and binds command actions.
     */
    private JMenuBar createMenuBar(Canvas canvas) {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu(MENU_FILE);
        JMenu editMenu = new JMenu(MENU_EDIT);

        JMenuItem labelItem = new JMenuItem(ITEM_LABEL);

        // Event Listener for the Label Edit command
        labelItem.addActionListener(e -> {

            // Filters the active selection
            List<Shape> selected = canvas.getShapes().stream()
                    .filter(Shape::isSelected)
                    .toList();

            // Ensures exactly one BasicObject is selected before permitting label modification.
            if (selected.size() == REQUIRED_SELECTION_COUNT && selected.getFirst() instanceof BasicObject obj) {

                // Triggers a dialog to capture user input
                LabelDialog dialog = new LabelDialog(this, obj.getLabelName(), obj.getFillColor());
                dialog.setVisible(true);

                // Applies the data update and triggers a View repaint if the user confirmed
                if (dialog.isConfirmed()) {
                    obj.updateLabel(dialog.getLabelName(), dialog.getLabelColor());
                    canvas.repaint();
                }
            } else {
                JOptionPane.showMessageDialog(this, ERROR_MSG_SINGLE_SELECT);
            }
        });

        // Forwards Group/Ungroup requests directly to the Canvas
        editMenu.add(new JMenuItem(ITEM_GROUP)).addActionListener(ev -> canvas.groupObjects());
        editMenu.add(new JMenuItem(ITEM_UNGROUP)).addActionListener(ev -> canvas.ungroupObjects());
        editMenu.addSeparator();
        editMenu.add(labelItem);

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        return menuBar;
    }
}