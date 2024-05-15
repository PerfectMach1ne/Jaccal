package main.java.minicalendar;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import main.java.util.StringConstants;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class MiniCalendarPanel extends JPanel {
    private final int LABEL_HEIGHT = 16;
    private final double FONT_TO_LABEL_HEIGHT_RATIO = 0.5625;
    
    private final int PARENT_PANEL_HEIGHT = 144;
    private final int PARENT_PANEL_WIDTH = 244;

    private JPanel internalPanel;

    protected void initializeMiniCal() {
        internalPanel = new JPanel();
        internalPanel.setBackground(Color.lightGray);

        /*
         * Create a GregorianCalendar instance set at current time & date in system's timezone. 
         */
        GregorianCalendar gregCal = new GregorianCalendar();

        // gregCal.add(Calendar.MONTH, -38); // Cannot be after setCorrectLayout()
        
        // If these are AFTER the calendar position is set to 1st monthday, highlighting of today's day breaks.
        int currentMonth = gregCal.get(Calendar.MONTH);
        int currentDayOfMonth = gregCal.get(Calendar.DAY_OF_MONTH);

        /*
         * Set the calendar position at the first day of the current month (1st monthday). 
         */
        gregCal.set(Calendar.DAY_OF_MONTH, 1);

        setCorrectLayout(gregCal); 
        
        initializeWeekdayLabels(StringConstants.weekdays);
        
        /* 
         * Clone the object, so that we don't have to later write an extra code block 
         * to revert the changes this would do to gregCal's monthday position.
         */                  
        GregorianCalendar indentCalCopy = (GregorianCalendar)gregCal.clone();
        initalizeIndentLabels(indentCalCopy); 

        /*
         * Create JLabels with days of the month, until we run out of monthdays for the given month.
         */
        do {
            int day = gregCal.get(Calendar.DAY_OF_MONTH);
            JLabel label = new JLabel(Integer.valueOf(day).toString());

            label.setBackground( day == currentDayOfMonth ? Color.decode("#e0fff9") : Color.white );
            label.setOpaque(true);
            internalPanel.add(label);
            
            gregCal.add(Calendar.DAY_OF_MONTH, 1);
        } while (gregCal.get(Calendar.MONTH) == currentMonth);
    }

    private void initializeWeekdayLabels(String[] labels) {
        for (int i = 0; i < 7; i++) {
            JLabel label = new JLabel(labels[i]);

            label.setBackground(Color.decode("#a9a7ed"));
            label.setOpaque(true);
            label.setHorizontalAlignment(SwingConstants.CENTER);

            internalPanel.add(label);
        }
    }

    private void initalizeIndentLabels(GregorianCalendar gregCalClone) {
        int weekday = gregCalClone.get(Calendar.DAY_OF_WEEK); // Day-of-the-week of 1st monthday.
        gregCalClone.setFirstDayOfWeek(Calendar.MONDAY); // Fix bug related to disgusting US date standards.
        int firstDayOfWeek = gregCalClone.getFirstDayOfWeek(); // Returns Monday (or Sunday if you unfortunately live in the U.S.).
        
        /*
         * Calculate the row indent before monthday '1'.
         */
        int indent = 0;
        while (weekday != firstDayOfWeek) {
            indent++;
            gregCalClone.add(Calendar.DAY_OF_MONTH, -1);
            weekday = gregCalClone.get(Calendar.DAY_OF_WEEK);
        }

        /*
         * Add blank, indent JLabel boxes to the first mini-calendar row.
         * ((Note: if indent breaks, try: i = 1; i <= indent))
         */
        for (int i = 0; i < indent; i++) {
            internalPanel.add(new JLabel());
        }
    }

    /*
     * The default row x col count for MiniCalendar's GridLayout is 6x7, including 1 row of weekday
     * labels and 5 rows of monthdays.
     * However, it breaks on certain months that have 6 or 4 rows of monthdays. This function aims
     * to fix that. 
     */
    private void setCorrectLayout(GregorianCalendar gregCalRef) {
        if (gregCalRef.get(Calendar.MONTH) == Calendar.FEBRUARY && gregCalRef.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
            System.out.println("[debug] MiniCalendarPanel: 4 week rows");
            internalPanel.setLayout(new GridLayout(5,7,2,2));
        } else if (gregCalRef.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY ||
                   (gregCalRef.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY &&
                    gregCalRef.getActualMaximum(Calendar.DAY_OF_MONTH) > 30)) {
            System.out.println("[debug] MiniCalendarPanel: 6 week rows");
            internalPanel.setLayout(new GridLayout(7,7,2,2));
        } else {
            System.out.println("[debug] MiniCalendarPanel: 5 week rows");
            internalPanel.setLayout(new GridLayout(6,7,2,2));
        }  
    }

    public MiniCalendarPanel() {
        this.setLayout(new BorderLayout());
        this.setBackground(Color.lightGray);
        this.setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
        this.setPreferredSize(new Dimension(PARENT_PANEL_WIDTH, PARENT_PANEL_HEIGHT));

        initializeMiniCal();

        this.add(internalPanel, BorderLayout.CENTER);
        // this.add(internalPanel/*, BorderLayout.CENTER*/);

        /*
         ***** Old comment rough translation:
         * Use height and the fact, that the precise height of the font in each JLabel component is 9px,
         * and the precise height of 1 grid is about 16px. Thus, the ratio of the font's size in px to
         * the JLabel preferred/default height is 9/16 = 0.5625. Now, consider: 
         * 
         * (9+Δy) = (16+Δx)*0.5625
         * ...where Δx is the change in JLabel height and
         * Δy is the change of font height.
         */
        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent componentEvent) {
                for (Component c : internalPanel.getComponents()) {
                    c.setFont(new Font("Courier New", Font.BOLD, 16));
                }
            }
        });
    }
}
