package javacalendar.minicalendar;

import javax.swing.JFrame;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MiniCalendarPopup implements KeyListener {
    private JFrame popupFrame = new JFrame();

    private final int PARENT_PANEL_HEIGHT = 120;
    private final int PARENT_PANEL_WIDTH = 170; 

    public MiniCalendarPopup() {
        popupFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        popupFrame.setTitle("Mini calendar");
        popupFrame.setResizable(true);
        popupFrame.setPreferredSize(new Dimension(PARENT_PANEL_WIDTH, PARENT_PANEL_HEIGHT));

        popupFrame.add(new MiniCalendarPanel());
        popupFrame.pack();

        popupFrame.addKeyListener(this);

        popupFrame.setVisible(true);
        popupFrame.setLocationRelativeTo(null);
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            popupFrame.dispose();
        } else if ((e.getKeyCode() == KeyEvent.VK_W) && (e.getModifiersEx() & KeyEvent.CTRL_DOWN_MASK) != 0) {
            popupFrame.dispose();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}
