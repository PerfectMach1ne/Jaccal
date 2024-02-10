package javacalendar;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;

public class Main {
    private final JFrame mainWindow;

    public static final int WINDOW_WIDTH = 1400;
    public static final int WINDOW_HEIGHT = 850;

    public Main() {
        try {
          Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
          e.printStackTrace();
        }

        mainWindow = new JFrame("JavaCalendar");
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        mainWindow.setResizable(false);

        mainWindow.setLayout(new BorderLayout());
        addMainComponents();

        mainWindow.setLocationRelativeTo(null);
    }

    private void addMainComponents() {
        mainWindow.add(new WeeksPanel(), BorderLayout.CENTER);
        mainWindow.add(new LeftBarPanel(), BorderLayout.WEST);
        mainWindow.setJMenuBar(new MenuBar());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> { // Invoking a runnable asynchronously (the lambda is the "Runnable" class; or the run() method)
            Main window = new Main();
            window.mainWindow.setVisible(true);
        });
    }

}
