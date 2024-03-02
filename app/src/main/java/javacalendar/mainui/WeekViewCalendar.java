package javacalendar.mainui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDate;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.ScrollPaneLayout;

import javacalendar.datelogic.WeekCalendarHandler;
import javacalendar.util.StringConstants;

public class WeekViewCalendar extends JScrollPane implements MouseListener {
    private final int PARENT_PANEL_WIDTH = 1122;
    private final int PARENT_PANEL_HEIGHT = 780;

    private final int WEEKDAY_SLICE_WIDTH = 160;
    private final int WEEKDAY_SLICE_HEIGHT = 780;

    private final int WEEKDAY_LABEL_PANEL_WIDTH = 1122;
    private final int WEEKDAY_LABEL_PANEL_HEIGHT = 50;
    private final int WEEKDAY_LABEL_WIDTH = 154;
    private final int WEEKDAY_LABEL_HEIGHT = 50;

    private final int MONTHDAY_LABEL_PANEL_WIDTH = 1122;
    private final int MONTHDAY_LABEL_PANEL_HEIGHT = 50;
    private final int MONTHDAY_LABEL_WIDTH = 154;
    private final int MONTHDAY_LABEL_HEIGHT = 50;

    private final int HOUR_LABEL_PANEL_WIDTH = 50;
    private final int HOUR_LABEL_PANEL_HEIGHT = 730;
    private final int HOUR_LABEL_WIDTH = 50;
    private final int HOUR_LABEL_HEIGHT = 25;

    private JPanel weekViewport;
    private JPanel wrapperPanel;
    private JPanel weekdayLabelViewport;
    private JPanel monthdayLabelViewport;
    private JPanel hourLabelViewport;
    private JPanel weekSwitchCorner;
    // This one's a static array, as it needs to be accessible by WeekCalendarHandler, so that it 
    // can add events to it.
    public static final JPanel[] weekdayPanelArray = new JPanel[7];
    private final JLabel[] weekdayLabelArray = new JLabel[7];
    private final JLabel[] monthdayLabelArray = new JLabel[7];
    private final JLabel[] hourLabelArray = new JLabel[24];
    private final JButton[] weekSwitchButtons = new JButton[2];

    private LocalDate selectedDate = WeekCalendarHandler.getCurrentWeekDate();

    public WeekViewCalendar() {
        this.setLayout(new ScrollPaneLayout());
        this.setBackground(Color.gray);

        // Create and add the 7-day week display for events
        this.setViewportView(configWeekViewport(weekViewport));
        // Create and add panel for days of the week and days of the month
        // Has to be contained within a new panel, due to column-header viewport of the ScrollPaneLayou t
        // only being able to hold one component at a time:
        wrapperPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        wrapperPanel.setPreferredSize(new Dimension(WEEKDAY_LABEL_PANEL_WIDTH, 2 * MONTHDAY_LABEL_PANEL_HEIGHT));
        wrapperPanel.add(configWeekdayLabelViewport(weekdayLabelViewport));
        wrapperPanel.add(configMonthdayLabelViewport(monthdayLabelViewport, (byte)0));
        wrapperPanel.addMouseListener(this);
        this.setColumnHeaderView(wrapperPanel);
        // Create and add panel for hours of the day
        this.setRowHeaderView(configHourLabelViewport(hourLabelViewport));

        this.createHorizontalScrollBar();
        this.createVerticalScrollBar();

        // ScrollPaneLayout corner setup
        weekSwitchCorner = new JPanel();
        weekSwitchCorner.setLayout(new BoxLayout(weekSwitchCorner, BoxLayout.Y_AXIS));
        weekSwitchCorner.setBackground(Color.decode("#6a699a"));
        for (int i = 0; i < 2; i++) {
            weekSwitchCorner.add(Box.createRigidArea(
                new Dimension(weekSwitchCorner.getWidth(), 12)
            ));
            weekSwitchCorner.add(setupWeekSwitchButtons(i));
        }
        this.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, weekSwitchCorner);

        JPanel cornerFillerBox = new JPanel();
        // TODO: Fill that panel with 2 labels/panels who have height of 25 each and colors of the two monthday+weekday bars.
        cornerFillerBox.setBackground(Color.decode("#bbbaf0"));
        cornerFillerBox.setPreferredSize(new Dimension(4, 15));
        this.setCorner(ScrollPaneConstants.UPPER_RIGHT_CORNER, cornerFillerBox);

        // TODO: Put here bottom left corner
        // TODO: Put here bottom right corner

        this.addMouseListener(this);
    }

    private JPanel configWeekViewport(JPanel panel) {
        panel = new JPanel();

        panel.setPreferredSize(new Dimension(PARENT_PANEL_WIDTH, PARENT_PANEL_HEIGHT));
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

        // Add the 7 weekdaySlices
        for (int i = 0; i < 7; i++) {
          panel.add(setupWeekdaySlice(weekdayPanelArray[i], i));
        }

        return panel;
    }

    private JPanel configWeekdayLabelViewport(JPanel panel) {
        panel = new JPanel();
        
        panel.setLayout(new FlowLayout());
        panel.setBackground(Color.decode("#bbbaf0"));
        panel.setPreferredSize(new Dimension(WEEKDAY_LABEL_PANEL_WIDTH, WEEKDAY_LABEL_PANEL_HEIGHT));

        // Add the 7 weekdayLabels
        for (int i = 0; i < 7; i++) {
          panel.add(setupWeekdayLabels(weekdayLabelArray[i], i));
        }

        return panel;
    }

    private JPanel configMonthdayLabelViewport(JPanel panel, byte mode) {
        panel = new JPanel();
        
        panel.setLayout(new FlowLayout());
        panel.setBackground(Color.decode("#7574b3"));
        panel.setPreferredSize(new Dimension(MONTHDAY_LABEL_PANEL_WIDTH, MONTHDAY_LABEL_PANEL_HEIGHT));

        int[] monthdays = new int[7];
        // Add the 7 weekdayLabels
        if (mode == (byte)0) {
            selectedDate = WeekCalendarHandler.getCurrentWeekDate();
            monthdays = WeekCalendarHandler.getWeekWeekdays(selectedDate);
        } else if (mode == (byte)-1) {
            selectedDate = WeekCalendarHandler.getPastWeekDate(selectedDate);
            monthdays = WeekCalendarHandler.getWeekWeekdays(selectedDate);
        } else if (mode == (byte)1) {
            selectedDate = WeekCalendarHandler.getFutureWeekDate(selectedDate);
            monthdays = WeekCalendarHandler.getWeekWeekdays(selectedDate);
        }
        
        for (int i = 0; i < 7; i++) {
            System.out.println(monthdays[i]);
            panel.add(setupMonthdayLabels(monthdayLabelArray[i], monthdays[i], i));
        }

        return panel;
    }

    private JPanel configHourLabelViewport(JPanel panel) {
        panel = new JPanel();

        panel.setLayout(new FlowLayout());
        panel.setBackground(Color.decode("#a9a7ed"));
        panel.setPreferredSize(new Dimension(HOUR_LABEL_PANEL_WIDTH, HOUR_LABEL_PANEL_HEIGHT));

        for (int i = 0; i < 24; i++) {
          panel.add(setupHourLabels(hourLabelArray[i], i));
        }

        return panel;
    }

    private JPanel setupWeekdaySlice(JPanel weekdaySlice, int iterator) {
        weekdaySlice = new JPanel();

        weekdaySlice.setLayout(null);
        weekdaySlice.setPreferredSize(new Dimension(WEEKDAY_SLICE_WIDTH, WEEKDAY_SLICE_HEIGHT));
        weekdaySlice.setBackground( iterator % 2 == 0 ? Color.decode("#f3f6f4") : Color.decode("#e7e7e7") );
        weekdaySlice.setOpaque(true);

        return weekdaySlice;
    }

    private JLabel setupWeekdayLabels(JLabel weekdayLabel, int iterator) {
        weekdayLabel = new JLabel(StringConstants.weekdays[iterator]);

        weekdayLabel.setFont(new Font("Arial", Font.BOLD, 24));
        weekdayLabel.setPreferredSize(new Dimension(WEEKDAY_LABEL_WIDTH, WEEKDAY_LABEL_HEIGHT));
        weekdayLabel.setHorizontalTextPosition(JLabel.RIGHT);
        weekdayLabel.setVerticalTextPosition(JLabel.BOTTOM);

        return weekdayLabel;
    }

    private JLabel setupMonthdayLabels(JLabel monthdayLabel, int monthday, int iterator) {
        monthdayLabel = new JLabel(Integer.toString(monthday));

        monthdayLabel.setFont(new Font("Arial", Font.BOLD, 24));
        monthdayLabel.setPreferredSize(new Dimension(MONTHDAY_LABEL_WIDTH, MONTHDAY_LABEL_HEIGHT));
        monthdayLabel.setHorizontalTextPosition(JLabel.RIGHT);
        monthdayLabel.setVerticalTextPosition(JLabel.BOTTOM);

        return monthdayLabel;
    }

    private JLabel setupHourLabels(JLabel hourLabel, int iterator) {
        hourLabel = new JLabel(
          (iterator < 10) ?
          ("0" + Integer.valueOf(iterator).toString() + ":00") :
          Integer.valueOf(iterator).toString() + ":00");

        hourLabel.setFont(new Font("Consolas", Font.BOLD, 12));
        hourLabel.setPreferredSize(new Dimension(HOUR_LABEL_WIDTH, HOUR_LABEL_HEIGHT));
        hourLabel.setHorizontalTextPosition(JLabel.LEFT);
        hourLabel.setVerticalTextPosition(JLabel.BOTTOM);

        return hourLabel;
    }

    private JButton setupWeekSwitchButtons(int iterator) {
        JButton switchButton = weekSwitchButtons[iterator];
        switchButton = new JButton();

        switchButton.setText(iterator == 0 ? "<" : ">");
        switchButton.setFont(new Font("Courier New", Font.BOLD, 18));
        switchButton.setFocusable(false);
        switchButton.setAlignmentX(CENTER_ALIGNMENT);
        switchButton.setAlignmentY(CENTER_ALIGNMENT);
        switchButton.addMouseListener(this);
        
        return switchButton;
    }

    @Override
    public void mouseClicked(MouseEvent e) {};

    @Override
    public void mousePressed(MouseEvent e) {};

    @Override
    public void mouseReleased(MouseEvent e) {
        if ( e.getSource() == weekSwitchCorner.getComponent(1) ) {
            System.out.println(selectedDate);
            wrapperPanel.remove(1); // wrapperPanel contains only 2 elements, and the first is always the static weekday panel.
            wrapperPanel.add(configMonthdayLabelViewport(monthdayLabelViewport, (byte)-1));
            wrapperPanel.addMouseListener(this);
            wrapperPanel.revalidate();
            wrapperPanel.repaint();
            this.revalidate();
            this.repaint();
        } else if ( e.getSource() == weekSwitchCorner.getComponent(3) /*weekSwitchButtons[1].getComponent(0)*/ ) {
            System.out.println(selectedDate);
            wrapperPanel.remove(1); // wrapperPanel contains only 2 elements, and the first is always the static weekday panel.
            wrapperPanel.add(configMonthdayLabelViewport(monthdayLabelViewport, (byte)1));
            wrapperPanel.addMouseListener(this);
            wrapperPanel.revalidate();
            wrapperPanel.repaint();
            this.revalidate();
            this.repaint();
        }
    };

    @Override
    public void mouseEntered(MouseEvent e) {};

    @Override
    public void mouseExited(MouseEvent e) {};
}
