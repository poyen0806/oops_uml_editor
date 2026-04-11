package ui;

import javax.swing.JFrame;
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

        add(toolBar, BorderLayout.WEST);
        add(canvas, BorderLayout.CENTER);
    }
}