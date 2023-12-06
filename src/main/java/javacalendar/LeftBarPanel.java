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
import javacalendar.minicalendar.MiniCalendarBox;

public class LeftBarPanel extends JPanel implements ActionListener {
    private final JButton addEventButton;
    private final JButton changeEventButton;
    private final JButton removeEventButton;

    public LeftBarPanel() {
        this.setBackground(Color.darkGray);
        this.setPreferredSize(new Dimension(210, 800));

        MiniCalendarBox calendarBox = new MiniCalendarBox();
        this.add(calendarBox);
        addEventButton = new JButton();
        changeEventButton = new JButton();
        removeEventButton = new JButton();
        addEventButton.setText("Add event");
        addEventButton.setFocusable(false);
        addEventButton.addActionListener(this);
        changeEventButton.setText("Change event");
        changeEventButton.setFocusable(false);
        changeEventButton.addActionListener(this);
        removeEventButton.setText("Delete event");
        removeEventButton.setFocusable(false);
        removeEventButton.addActionListener(this);

        this.add(addEventButton);
        this.add(changeEventButton);
        this.add(removeEventButton);
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
