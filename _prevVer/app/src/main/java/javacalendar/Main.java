package javacalendar;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import javacalendar.mainui.*;

public class Main implements KeyListener {
    private final JFrame mainWindow;

    public static final int WINDOW_WIDTH = 1450;
    public static final int WINDOW_HEIGHT = 800; // Originally 850

    public Main() {
      // MySQL JDBC driver connection (does that work? we'll see!).
      try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        mainWindow = new JFrame("JavaCalendar");
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        mainWindow.setResizable(true);
        mainWindow.setLayout(new BorderLayout());  
        // A "nooby", albeit functional way to center the window, I suppose.
        mainWindow.setLocationRelativeTo(null);

        mainWindow.addKeyListener(this);

        addMainComponents();
    }

    private void addMainComponents() {
        mainWindow.add(new WeekViewCalendar(), BorderLayout.CENTER);
        mainWindow.add(new LeftBarPanel(), BorderLayout.WEST);
        mainWindow.setJMenuBar(new MenuBar());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Invoking a runnable asynchronously (the lambda is the "Runnable" class; or the run() method).
            Main window = new Main();
            window.mainWindow.setVisible(true);
        });
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    /*
     * Ctrl + W closes the main window.
     */
    public void keyPressed(KeyEvent e) {
        if ((e.getKeyCode() == KeyEvent.VK_W) && (e.getModifiersEx() & KeyEvent.CTRL_DOWN_MASK) != 0) {
            mainWindow.dispose();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}
