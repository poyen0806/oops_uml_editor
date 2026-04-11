package ui;

import javax.swing.*;
import java.awt.BorderLayout;

public class MainWindow extends JFrame {
    private static final String WINDOW_TITLE = "Oops UML Editor";
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 600;

    public MainWindow() {
        setTitle(WINDOW_TITLE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        Canvas canvas = new Canvas();
        ToolBar toolBar = new ToolBar(canvas);

        setJMenuBar(createMenuBar(canvas));
        add(toolBar, BorderLayout.WEST);
        add(canvas, BorderLayout.CENTER);
    }

    private JMenuBar createMenuBar(Canvas canvas) {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);

        JMenu editMenu = new JMenu("Edit");

        JMenuItem groupItem = new JMenuItem("Group");
        groupItem.addActionListener(e -> {
            canvas.groupObjects();
        });

        JMenuItem ungroupItem = new JMenuItem("Ungroup");
        ungroupItem.addActionListener(e -> {
            canvas.ungroupObjects();
        });


        editMenu.add(groupItem);
        editMenu.add(ungroupItem);

        menuBar.add(editMenu);

        return menuBar;
    }
}