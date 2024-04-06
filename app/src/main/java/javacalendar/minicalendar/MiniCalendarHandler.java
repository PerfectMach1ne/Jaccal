package javacalendar.minicalendar;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;

public class MiniCalendarHandler {
    private final JPanel internalPanelL; // internalPanelLogic; like, this is a logic class. Makes sense to one of my
                                         // braincells, I guess...?

    protected MiniCalendarHandler(JPanel internalPanel) {
        this.internalPanelL = internalPanel;
    }

    // Protected, bc this way it's only visible in the package (so the
    // javacalendar.event package)
    protected void initializeMiniCal() {
        // Create a GregorianCalendar instance set at current time & date in system's timezone.
        GregorianCalendar gregCal = new GregorianCalendar();
        int currentDayOfMonth = gregCal.get(Calendar.DAY_OF_MONTH);
        int currentMonth = gregCal.get(Calendar.MONTH);

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

        for (int i = 1; i <= indent; i++) {
            internalPanelL.add(new JLabel() {
                {
                    setBackground(Color.white);
                    setOpaque(true); // Make the background visible
                    setText("");
                }
            });
        }

        gregCal.set(Calendar.DAY_OF_MONTH, 1);

        do {
            int day = gregCal.get(Calendar.DAY_OF_MONTH);
            if (day == currentDayOfMonth)
                internalPanelL.add(new JLabel() {
                    {
                        setBackground(Color.decode("#e0fff9"));
                        setOpaque(true); // Make the background visible
                        setText(Integer.valueOf(day).toString());
                    }
                });
            else
                internalPanelL.add(new JLabel() {
                    {
                        setBackground(Color.white);
                        setOpaque(true); // Make the background visible
                        setText(Integer.valueOf(day).toString());
                    }
                });
            gregCal.add(Calendar.DAY_OF_MONTH, 1);

        } while (gregCal.get(Calendar.MONTH) == currentMonth);
    }
}
