package javacalendar.minicalendar;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

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
    
    private final int PARENT_PANEL_HEIGHT = 120;
    private final int PARENT_PANEL_WIDTH = 170; // TODO: rethink the actual dimensions 

    private JPanel internalPanel; // internalPanelLogic; like, this is a logic class. Makes sense to one of my
                                         // braincells, I guess...?

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
            internalPanel.add(new JLabel() {
                {
                    setBackground(Color.lightGray);
                    setOpaque(true);
                }
            });
        }

        /*
         * Create JLabels with days of the month, until we run out of monthdays for the given month.
         */
        do {
            int day = gregCal.get(Calendar.DAY_OF_MONTH);
            if (day == currentDayOfMonth)
                internalPanel.add(new JLabel() {
                    {
                        setBackground(Color.decode("#e0fff9"));
                        setOpaque(true); // Make the background visible
                        setText(Integer.valueOf(day).toString());
                    }
                });
            else
                internalPanel.add(new JLabel() {
                    {
                        setBackground(Color.white);
                        setOpaque(true); // Make the background visible
                        setText(Integer.valueOf(day).toString());
                    }
                });
            gregCal.add(Calendar.DAY_OF_MONTH, 1);
        } while (gregCal.get(Calendar.MONTH) == currentMonth);
    }

    public MiniCalendarPanel() {
        //////////////////// ISSUE //////////////////
        // I think BorderLayout is terrible for such a component. Let's try to implement GridLayout later.
        // this.setLayout(new BorderLayout());
        this.setBackground(Color.lightGray);
        this.setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
        this.setPreferredSize(new Dimension(PARENT_PANEL_WIDTH, PARENT_PANEL_HEIGHT));

        internalPanel = new JPanel();
        /////////////////////// BUG TO FIX ///////////////////////
        // Row count causes issues on some months, e.g.:
        // Feb 2024 requires `rows` to be 6 instead of 7 in order to prevent the calendar from collapsing
        // Sunday into a new row, for some reason. It should be connected to what kind of month it is today,
        // also. somehow.  
        internalPanel.setLayout(new GridLayout(6,7,3,3));   
        internalPanel.setBackground(Color.lightGray);

        initializeFirstRowOfLabels(StringConstants.weekdays);

        JPanel miniCalendar = new JPanel();
        miniCalendar.add(internalPanel);
        initializeMiniCal();

        this.add(internalPanel, BorderLayout.CENTER);

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
                // Use height and the fact, that:
                // Dokładna wysokość czcionki w komponencie minicalendar.MiniCalendarBox = 9px
                // Dokładna wysokość of 1 grid and the Label inside it = 16px
                // Ratio of font pixel size to Label standard height is 9 / 16 = 0.5625
                // (9+Δy) = (16+Δx)*0.5625
                // gdzie Δx to zmiana wysokosci komponentu JLabel a Δy to zmiana wysokości czcionki
                // Mozna to zrobic tak: Δx = (...).getHeight() - 16 (Δx < 0 when smaller than standard box size)
                // I potem w if-ach albo switch-case zmieniac rozmiar czcionki poprzez setText
            }
        });

    }

    private void initializeFirstRowOfLabels(String[] labels) {
        for (int i = 0; i < 7; i++) {
            // "Variable 'i' is accessed from within inner class, needs to be final or effectively final"
            int finalI = i;
            internalPanel.add(new JLabel() {
                {
                    setBackground(Color.decode("#a9a7ed"));
                    setOpaque(true);    // Make the background visible
                    setText(labels[finalI]);
                    setHorizontalAlignment(CENTER);
                }
            });
        }
    }
}
