package javacalendar.minicalendar;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import javacalendar.util.StringConstants;

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
    private final int PARENT_PANEL_WIDTH = 218; // TODO: rethink the actual dimensions 

    private JPanel internalPanel;

    // Protected, bc this way it's only visible in the package (so the javacalendar.event package)
    protected void initializeMiniCal() {
        // Create a GregorianCalendar instance set at current time & date in system's timezone.
        GregorianCalendar gregCal = new GregorianCalendar();
        // If these are AFTER the calendar position is set to 1st monthday, today highlighting breaks.
        int currentMonth = gregCal.get(Calendar.MONTH);
        int currentDayOfMonth = gregCal.get(Calendar.DAY_OF_MONTH);

        // Set the calendar position at the first day of the current month (1st monthday).
        gregCal.set(Calendar.DAY_OF_MONTH, 1);
        int weekday = gregCal.get(Calendar.DAY_OF_WEEK); // Day-of-the-week of 1st monthday.
        int firstDayOfWeek = gregCal.getFirstDayOfWeek(); // Returns Monday (or Sunday if you unfortunately live in the U.S.).

        // Calculate the row indent before monthday '1'.
                          // Clone the object, so that we don't have to later write an extra code block
                          // to revert the changes this would do to gregCal's monthday position.
        GregorianCalendar indentCalCopy = (GregorianCalendar)gregCal.clone();
        int indent = 0;
        while (weekday != firstDayOfWeek) {
            indent++;
            indentCalCopy.add(Calendar.DAY_OF_MONTH, -1);
            weekday = indentCalCopy.get(Calendar.DAY_OF_WEEK);
        }

        // Add blank, indent JLabel boxes to the first mini-calendar row.
        // Note: if indent breaks, try: i = 1; i <= indent
        for (int i = 0; i < indent; i++) {
            internalPanel.add(new JLabel());
        }

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

    private void initializeFirstRowOfLabels(String[] labels) {
        for (int i = 0; i < 7; i++) {
            JLabel label = new JLabel(labels[i]);

            label.setBackground(Color.decode("#a9a7ed"));
            label.setOpaque(true);
            label.setHorizontalAlignment(SwingConstants.CENTER);

            internalPanel.add(label);
        }
    }

    public MiniCalendarPanel() {
        this.setBackground(Color.lightGray);
        this.setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
        this.setPreferredSize(new Dimension(PARENT_PANEL_WIDTH, PARENT_PANEL_HEIGHT));

        internalPanel = new JPanel();
        internalPanel.setLayout(new GridLayout(6,7,2,2));   
        internalPanel.setBackground(Color.lightGray);

        initializeFirstRowOfLabels(StringConstants.weekdays);
        initializeMiniCal();

        this.add(internalPanel, BorderLayout.CENTER);

        // Use height and the fact, that:
        // Dokładna wysokość czcionki w komponencie minicalendar.MiniCalendarBox = 9px
        // Dokładna wysokość of 1 grid and the Label inside it = 16px
        // Ratio of font pixel size to Label standard height is 9 / 16 = 0.5625
        // (9+Δy) = (16+Δx)*0.5625
        // gdzie Δx to zmiana wysokosci komponentu JLabel a Δy to zmiana wysokości czcionki
        // Mozna to zrobic tak: Δx = (...).getHeight() - 16 (Δx < 0 when smaller than standard box size)
        // I potem w if-ach albo switch-case zmieniac rozmiar czcionki poprzez setText
        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent componentEvent) {
                for (Component c : internalPanel.getComponents()) {
                    if (c.getHeight() > LABEL_HEIGHT) {
                        int scaledFontSize = (int)Math.round(c.getHeight() * FONT_TO_LABEL_HEIGHT_RATIO);
                        c.setFont(new Font("Courier New", Font.BOLD, scaledFontSize));
                    } else {
                        c.setFont(new Font("Courier New", Font.BOLD, 16));
                    }
                }
            }
        });
    }
}
