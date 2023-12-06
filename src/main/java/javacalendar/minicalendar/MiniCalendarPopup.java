package javacalendar.minicalendar;

import javax.swing.JFrame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MiniCalendarPopup implements KeyListener {
    private JFrame popupFrame = new JFrame();

    public MiniCalendarPopup() {
        popupFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        popupFrame.setTitle("Mini calendar");
        popupFrame.setResizable(true);
        popupFrame.add(new MiniCalendarBox());
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
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}
