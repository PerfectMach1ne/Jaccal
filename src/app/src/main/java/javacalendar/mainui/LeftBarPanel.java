package mainui;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import event.AddEventWindow;
import event.ChangeEventWindow;
import event.RemoveEventWindow;
import minicalendar.MiniCalendarPanel;

public class LeftBarPanel extends JPanel implements ActionListener {
    private final int PARENT_PANEL_WIDTH = 250;
    private final int PARENT_PANEL_HEIGHT = 800;

    private final Dimension TINY_RIGID_BOX_DIMENSIONS = new Dimension(PARENT_PANEL_WIDTH, 10);
    private final Dimension HUGE_RIGID_BOX_DIMENSIONS = new Dimension(PARENT_PANEL_HEIGHT, 450);

    private JLabel displayTextDate = new JLabel();

    private JButton addEventButton;
    private JButton changeEventButton;
    private JButton removeEventButton;

    public LeftBarPanel() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setAlignmentX(CENTER_ALIGNMENT);
        
        this.setBackground(Color.darkGray);
        this.setPreferredSize(new Dimension(PARENT_PANEL_WIDTH, PARENT_PANEL_HEIGHT));

        this.add(Box.createRigidArea(TINY_RIGID_BOX_DIMENSIONS));
        displayTextDate.setText(LocalDate.now().toString());
        displayTextDate.setFont(new Font("Courier New", Font.BOLD, 24));
        displayTextDate.setForeground(Color.WHITE);
        displayTextDate.setAlignmentX(CENTER_ALIGNMENT);
        this.add(displayTextDate);
        this.add(Box.createRigidArea(TINY_RIGID_BOX_DIMENSIONS));
        
        MiniCalendarPanel calendarBox = new MiniCalendarPanel();
        calendarBox.setAlignmentX(CENTER_ALIGNMENT);
        this.add(calendarBox);

        addEventButton = configButton(addEventButton, "Add event");
        changeEventButton = configButton(changeEventButton, "Change event");
        removeEventButton = configButton(removeEventButton, "Remove event");

        this.add(Box.createRigidArea(TINY_RIGID_BOX_DIMENSIONS));
        this.add(addEventButton);
        this.add(Box.createRigidArea(TINY_RIGID_BOX_DIMENSIONS));
        this.add(changeEventButton);
        this.add(Box.createRigidArea(TINY_RIGID_BOX_DIMENSIONS));
        this.add(removeEventButton);

        this.add(Box.createRigidArea(HUGE_RIGID_BOX_DIMENSIONS));
    }

    private JButton configButton(JButton butt, String buttonText) {
        butt = new JButton();

        butt.setText(buttonText);
        butt.setAlignmentX(CENTER_ALIGNMENT);
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
