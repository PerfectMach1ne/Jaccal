package javacalendar.minicalendar;

import javax.swing.JFrame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MiniCalPopup implements KeyListener {
    private JFrame popupFrame = new JFrame();

    public MiniCalPopup() {
        popupFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        popupFrame.setTitle("Mini calendar");
        popupFrame.setResizable(true);
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
