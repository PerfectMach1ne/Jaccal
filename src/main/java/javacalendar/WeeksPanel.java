package javacalendar;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneLayout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javacalendar.util.StringConstants;

public class WeeksPanel extends JScrollPane {
  private final int PARENT_PANEL_WIDTH = 1122;
  private final int PARENT_PANEL_HEIGHT = 730;

  private final int WEEKDAY_SLICE_WIDTH = 160;
  private final int WEEKDAY_SLICE_HEIGHT = 730;

  private final int WEEKDAY_LABEL_PANEL_WIDTH = 1122;
  private final int WEEKDAY_LABEL_PANEL_HEIGHT = 50;
  private final int WEEKDAY_LABEL_WIDTH = 154;
  private final int WEEKDAY_LABEL_HEIGHT = 50;

  private final int HOUR_LABEL_PANEL_WIDTH = 50;
  private final int HOUR_LABEL_PANEL_HEIGHT = 730;
  private final int HOUR_LABEL_WIDTH = 50;
  private final int HOUR_LABEL_HEIGHT = 25;

  private JPanel weekViewport;
  private JPanel weekdayLabelViewport;
  private JPanel hourLabelViewport;
  public static final JPanel[] weekdayPanelArray = new JPanel[7];
  private final JLabel[] weekdayLabelArray = new JLabel[7];
  private final JLabel[] hourLabelArray = new JLabel[24];

  public WeeksPanel() {
    this.setLayout(new ScrollPaneLayout());
    this.setBackground(Color.gray);

    // Create and add the 7-day week display for events
    this.setViewportView(configWeekViewport(weekViewport));
    // Create and add panel for days of the week
    this.setColumnHeaderView(configWeekdayLabelViewport(weekdayLabelViewport));
    // Create and add panel for hours of the day
    this.setRowHeaderView(configHourLabelViewport(hourLabelViewport));
    this.createHorizontalScrollBar();
    this.createVerticalScrollBar();
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
}
