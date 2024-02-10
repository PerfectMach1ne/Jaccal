package javacalendar;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Main implements KeyListener {
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
        mainWindow.addKeyListener(this);

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

    @Override
    public void keyTyped(KeyEvent e) {
      throw new UnsupportedOperationException("Unimplemented method 'keyTyped'");
    }

    @Override
    public void keyPressed(KeyEvent e) {
      if ((e.getKeyCode() == KeyEvent.VK_W) && (e.getModifiersEx() & KeyEvent.CTRL_DOWN_MASK) != 0) {
        mainWindow.dispose();
      }
    }

    @Override
    public void keyReleased(KeyEvent e) {
      throw new UnsupportedOperationException("Unimplemented method 'keyReleased'");
    }

}
