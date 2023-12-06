package javacalendar;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javacalendar.event.AddEventWindow;
import javacalendar.event.ChangeEventWindow;
import javacalendar.event.RemoveEventWindow;
import javacalendar.minicalendar.MiniCalendarPopup;

public class MenuBar extends JMenuBar implements ActionListener {
    private JMenuItem calMenuItem1, calMenuItem2, calSubMenuItem1, calSubMenuItem2, calSubMenuItem3;
    private JMenu calSubMenu1;
    private JMenuItem helpMenuItem1;
    private JMenuItem aboutMenuItem1;

    public MenuBar() {
        this.setBackground(Color.LIGHT_GRAY);

        JMenu calendarMenu = new JMenu("Calendar");
        JMenu helpMenu = new JMenu("Help");
        JMenu aboutMenu = new JMenu("About");

        // calendarMenu items and submenus
        calMenuItem1 = new JMenuItem("View mini calendar");
        calSubMenu1 = new JMenu("Edit events");
        calMenuItem2 = new JMenuItem("Exit");
        calSubMenuItem1 = new JMenuItem("Add a new event");
        calSubMenuItem2 = new JMenuItem("Change an event");
        calSubMenuItem3 = new JMenuItem("Delete an event");
        calSubMenu1.add(calSubMenuItem1); calSubMenu1.add(calSubMenuItem2); calSubMenu1.add(calSubMenuItem3);
        // helpMenu items and submenus
        helpMenuItem1 = new JMenuItem("How to use");
        // aboutMenu items and submenus
        aboutMenuItem1 = new JMenuItem("About");

        Font font = new Font("Arial", Font.BOLD, 14);
        calendarMenu.setFont(font);
        helpMenu.setFont(font);
        aboutMenu.setFont(font);

        calendarMenu.add(calMenuItem1); calendarMenu.add(calSubMenu1); calendarMenu.add(calMenuItem2);
        helpMenu.add(helpMenuItem1);
        aboutMenu.add(aboutMenuItem1);

        // Add menu action listeners
        calMenuItem1.addActionListener(this);
        calMenuItem2.addActionListener(this);
        calSubMenuItem1.addActionListener(this);
        calSubMenuItem2.addActionListener(this);
        calSubMenuItem3.addActionListener(this);
        helpMenuItem1.addActionListener(this);
        aboutMenuItem1.addActionListener(this);

        this.add(calendarMenu);
        this.add(helpMenu);
        this.add(aboutMenu);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == calMenuItem1) {
            new MiniCalendarPopup();
        } else if (e.getSource() == calMenuItem2) {
            System.exit(0);
        } else if (e.getSource() == calSubMenuItem1) {
            new AddEventWindow();
        } else if (e.getSource() == calSubMenuItem2) {
            new ChangeEventWindow();
        } else if (e.getSource() == calSubMenuItem3) {
            new RemoveEventWindow();
        } else if (e.getSource() == helpMenuItem1) {
            // new TutorialWindow
        } else if (e.getSource() == aboutMenuItem1) {
            JOptionPane.showMessageDialog(null, "JavaCalendar something point o point o 2022","About Java Calendar", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
