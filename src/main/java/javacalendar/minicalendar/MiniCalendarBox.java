package javacalendar.minicalendar;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javacalendar.util.StringConstants;

public class MiniCalendarBox extends JPanel {
    private final JPanel internalPanel;
    private final int LABEL_HEIGHT = 16;
    private final double FONT_TO_LABEL_HEIGHT_RATIO = 0.5625;

    public MiniCalendarBox() {
        this.setLayout(new BorderLayout());
        this.setBackground(Color.lightGray);
        this.setBorder(BorderFactory.createLineBorder(Color.lightGray, 3));

        internalPanel = new JPanel();
        internalPanel.setLayout(new GridLayout(7,7,2,2));   // 7x7, na górze dni tygodnia
        internalPanel.setBackground(Color.lightGray);

        calendarInitializer();

        this.add(internalPanel, BorderLayout.CENTER);

        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent componentEvent) {
                for (Component c : internalPanel.getComponents()) {
                    if (c.getHeight() > LABEL_HEIGHT) {
                        int scaledFontSize = (int)Math.round(c.getHeight() * FONT_TO_LABEL_HEIGHT_RATIO);
                        c.setFont(new Font("Arial", Font.BOLD, scaledFontSize));
                    } else {
                        c.setFont(new Font("Arial", Font.BOLD, 12));
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
                }
            });
        }
    }

    private void calendarInitializer() {
        // crucial calendar code below
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
        // crucial calendar code above

        initializeFirstRowOfLabels(StringConstants.weekdays);

        for (int i = 1; i <= indent; i++) {
            internalPanel.add(new JLabel() {
                {
                    setBackground(Color.white);
                    setOpaque(true);    // Make the background visible
                    setText("");
                }
            });
        }

        gregCal.set(Calendar.DAY_OF_MONTH, 1);

        do {
            int day = gregCal.get(Calendar.DAY_OF_MONTH);
            if (day == today)
                internalPanel.add(new JLabel() {
                    {
                        setBackground(Color.decode("#e0fff9"));
                        setOpaque(true);    // Make the background visible
                        setText(Integer.valueOf(day).toString());
                    }
                });
            else
                internalPanel.add(new JLabel() {
                    {
                        setBackground(Color.white);
                        setOpaque(true);    // Make the background visible
                        setText(Integer.valueOf(day).toString());
                    }
                });
            gregCal.add(Calendar.DAY_OF_MONTH, 1);

        } while (gregCal.get(Calendar.MONTH) == month);
    }
}
