package main.java.mainui;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

// import main.java.event.ui.AddEventWindow;
// import main.java.event.ui.ChangeEventWindow;
// import main.java.event.ui.RemoveEventWindow;
import main.java.minicalendar.MiniCalendarPanel;

public class LeftBarView extends JPanel implements ActionListener {
    private final int PARENT_PANEL_WIDTH = 250;
    private final int PARENT_PANEL_HEIGHT = 800;

    private final Dimension TINY_RIGID_BOX_DIMENSIONS = new Dimension(PARENT_PANEL_WIDTH, 5);
    private final Dimension HUGE_RIGID_BOX_DIMENSIONS = new Dimension(PARENT_PANEL_HEIGHT, 450);

    private JLabel displayTextDate = new JLabel();

    private JButton addEventButton;
    private JButton changeEventButton;
    private JButton removeEventButton;

    public LeftBarView() {
        this.setLayout(new FlowLayout());
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

    /*
     * Make buttons open the corresponding windows.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if ( e.getSource() == addEventButton ) {
            // new AddEventWindow();
        } else if ( e.getSource() == changeEventButton ) {
            // new ChangeEventWindow();
        } else if ( e.getSource() == removeEventButton ) {
            // new RemoveEventWindow();
        }
    }
}
