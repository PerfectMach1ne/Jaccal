package javacalendar.event;

import javacalendar.util.WeekdayMethods;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class RemoveEventWindow implements ActionListener, KeyListener {
    JFrame removeEventFrame = new JFrame();

    private JComboBox eventComboBox;
    private JLabel chooseEventLabel = new JLabel("Choose an event: ");
    private JButton confirmButton;
    private JButton cancelButton;

    private String[] eventNames;
    private String[] eventKeys;

    private final int horizontalGap = 5;
    private final int verticalGap = 5;

    public RemoveEventWindow() {
        removeEventFrame.setBounds(0,0,100,150);
        removeEventFrame.setSize(new Dimension(300,175));
        removeEventFrame.setLocationRelativeTo(null);
        removeEventFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        removeEventFrame.setResizable(false);
        removeEventFrame.setTitle("Delete an event");

        removeEventFrame.setFocusable(true);
        removeEventFrame.addKeyListener(this);
        
        removeEventFrame.getContentPane().setLayout(new GridBagLayout());
        GridBagConstraints constraint = new GridBagConstraints();
        constraint.insets = new Insets(horizontalGap, verticalGap, horizontalGap, verticalGap);
        removeEventFrame.setVisible(true);

        eventNames = new String[CalendarEventHandler.eventNames.size()];
        eventKeys = new String[CalendarEventHandler.eventNames.size()];
        String[] parsedEventNames = new String[CalendarEventHandler.eventNames.size()];
        int i = 0;
        for (String eventName : CalendarEventHandler.eventNames.values()) {
            eventNames[i] = eventName;
            i++;
        } i = 0;
        for (String key : CalendarEventHandler.eventNames.keySet()) {
            eventKeys[i] = key;
            i++;
        } i = 0;
        // Zamienia dni tygodnia z liczb od 0 do 6 na pełne angielskie nazwy
        for (String eventName : eventNames) {
            String key = eventKeys[i];
            int day = Integer.parseInt(String.valueOf(key.charAt(0)));
            parsedEventNames[i] = eventName + " (" + WeekdayMethods.weekdayToString(day) + ")";
            i++;
        }

        // Choose an event label
        constraint.insets = new Insets(horizontalGap, 0, horizontalGap, 0);
        constraint.gridx = 0;
        constraint.gridy = 0;
        removeEventFrame.add(chooseEventLabel, constraint);

        // Choose an event ComboBox
        constraint.insets = new Insets(horizontalGap, verticalGap, horizontalGap, verticalGap);
        eventComboBox = new JComboBox(parsedEventNames);
        eventComboBox.setEditable(false);
        constraint.gridx = 0;
        constraint.gridy = 1;
        constraint.gridwidth = 3;

        removeEventFrame.add(eventComboBox, constraint);

        // Delete event button
        constraint.insets = new Insets(horizontalGap, verticalGap, horizontalGap, verticalGap);
        confirmButton = new JButton();
        confirmButton.setText("Delete event");
        confirmButton.setFocusable(false);
        confirmButton.addActionListener(this);
        constraint.gridwidth = 1;
        constraint.gridx = 0;
        constraint.gridy = 2;
        constraint.anchor = GridBagConstraints.PAGE_END;

        removeEventFrame.add(confirmButton, constraint);

        // Cancel button
        constraint.insets = new Insets(horizontalGap, verticalGap, horizontalGap, verticalGap);
        cancelButton = new JButton();
        cancelButton.setText("Cancel");
        cancelButton.setFocusable(false);
        cancelButton.addActionListener(this);
        constraint.gridwidth = 1;
        constraint.gridx = 1;
        constraint.gridy = 2;
        constraint.anchor = GridBagConstraints.PAGE_END;

        removeEventFrame.add(cancelButton, constraint);

        eventComboBox.setFocusable(true);
        eventComboBox.addKeyListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ( e.getSource() == confirmButton ) {
            String chosenEventKey = eventKeys[eventComboBox.getSelectedIndex()];
            if ( CalendarEventHandler.eventStorage.containsKey(chosenEventKey) ) {
                CalendarEventHandler.removeCalendarEventByHashKey(chosenEventKey);
            }
            removeEventFrame.dispose();
        } else if ( e.getSource() == cancelButton ) {
            removeEventFrame.dispose();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            removeEventFrame.dispose();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}
