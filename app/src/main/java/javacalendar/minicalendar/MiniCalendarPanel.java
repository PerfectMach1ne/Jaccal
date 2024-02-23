package main.java.javacalendar.minicalendar;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.java.javacalendar.util.StringConstants;

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
    private final int PARENT_PANEL_WIDTH = 170; 

    private final JPanel internalPanel;

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

        MiniCalendarHandler miniCalendar = new MiniCalendarHandler(internalPanel);
        miniCalendar.initializeMiniCal();

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
