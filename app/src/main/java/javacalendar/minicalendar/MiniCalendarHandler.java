package main.java.javacalendar.minicalendar;

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
        GregorianCalendar gregCal = new GregorianCalendar();
        int today = gregCal.get(Calendar.DAY_OF_MONTH);
        int month = gregCal.get(Calendar.MONTH);

        gregCal.set(Calendar.DAY_OF_MONTH, 1);
        int weekday = gregCal.get(Calendar.DAY_OF_WEEK);
        int firstDayOfWeek = gregCal.getFirstDayOfWeek();

        int indent = 0;
        while (weekday != firstDayOfWeek) {
            indent++;
            gregCal.add(Calendar.DAY_OF_MONTH, -1);
            weekday = gregCal.get(Calendar.DAY_OF_WEEK);
        }

        do {
            gregCal.add(Calendar.DAY_OF_MONTH, 1);
            weekday = gregCal.get(Calendar.DAY_OF_WEEK);
        } while (weekday != firstDayOfWeek);

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
            if (day == today)
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

        } while (gregCal.get(Calendar.MONTH) == month);
    }
}
