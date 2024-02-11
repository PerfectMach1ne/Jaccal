package javacalendar;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javacalendar.event.AddEventWindow;
import javacalendar.event.ChangeEventWindow;
import javacalendar.event.RemoveEventWindow;
import javacalendar.minicalendar.MiniCalBox;

public class LeftBarPanel extends JPanel implements ActionListener {
    private final int PARENT_PANEL_WIDTH = 210;
    private final int PARENT_PANEL_HEIGHT = 800;

    private JButton addEventButton;
    private JButton changeEventButton;
    private JButton removeEventButton;

    public LeftBarPanel() {
        this.setLayout(getLayout());
        this.setBackground(Color.darkGray);
        this.setPreferredSize(new Dimension(PARENT_PANEL_WIDTH, PARENT_PANEL_HEIGHT));

        MiniCalBox calendarBox = new MiniCalBox();
        this.add(calendarBox);

        addEventButton = configButton(addEventButton, "Add event");
        changeEventButton = configButton(changeEventButton, "Change event");
        removeEventButton = configButton(removeEventButton, "Remove event");

        this.add(addEventButton);
        this.add(changeEventButton);
        this.add(removeEventButton);
    }

    private JButton configButton(JButton butt, String buttonText) {
        butt = new JButton();

        butt.setText(buttonText);
        butt.setFocusable(false);
        butt.addActionListener(this);

        return butt;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ( e.getSource() == addEventButton ) {
            new AddEventWindow();
        } else if ( e.getSource() == changeEventButton ) {
            new ChangeEventWindow();
        } else if ( e.getSource() == removeEventButton ) {
            new RemoveEventWindow();
        }
    }
}
