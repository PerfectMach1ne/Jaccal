package javacalendar;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javacalendar.util.StringConstants;

public class WeeksPanel extends JPanel implements ComponentListener {
    private final int PARENT_PANEL_WIDTH = 1122;
    private final int PARENT_PANEL_HEIGHT = 730;

    private final int WEEKDAY_DISPLAY_WIDTH = 160;
    private final int WEEKDAY_DISPLAY_HEIGHT = 730;

    private final int WEEKDAY_LABEL_PANEL_WIDTH = 1122;
    private final int WEEKDAY_LABEL_PANEL_HEIGHT = 50;
    private final int WEEKDAY_LABEL_WIDTH = 154;
    private final int WEEKDAY_LABEL_HEIGHT = WEEKDAY_LABEL_PANEL_HEIGHT;

    private final int HOUR_PANEL_WIDTH = 50;
    private final int HOUR_PANEL_HEIGHT = 730;
    private final int HOUR_LABEL_WIDTH = HOUR_PANEL_WIDTH;
    private final int HOUR_LABEL_HEIGHT = 25;

    private JPanel weekdayContainer;
    private JPanel weekdayLabelPanel;
    private JPanel hourLabelPanel;
    private JLabel[] weekdayLabelArray = new JLabel[7];
    private JLabel[] hourLabelArray = new JLabel[24];
    public static final JPanel[] weekdayPanelArray = new JPanel[7];

    public WeeksPanel() {
        this.setLayout(new BorderLayout());
        this.setBackground(Color.gray);

        // Create and add panel for days of the week
        createWeekdayPanels();
        this.add(weekdayLabelPanel, BorderLayout.NORTH);

        // Create and add panel for hours of the day
        createHourPanels();
        this.add(hourLabelPanel, BorderLayout.WEST);

        // Create and add the 7-day week display for events
        createWeekdayContainer();
        this.add(weekdayContainer, BorderLayout.CENTER);

        weekdayContainer.addComponentListener(this);
    }

    private void createWeekdayContainer() {
        weekdayContainer = new JPanel();
        weekdayContainer.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        weekdayContainer.setBackground(Color.decode("#f3f6f4"));

        createWeekdayDisplay();
    }

    private void createWeekdayDisplay(){
        for (int i = 0; i < 7; i++) {
            weekdayPanelArray[i] = new JPanel();
            weekdayPanelArray[i].setLayout(null);
            weekdayPanelArray[i].setPreferredSize(new Dimension(WEEKDAY_DISPLAY_WIDTH, WEEKDAY_DISPLAY_HEIGHT));
            // Makes the panel colors alternate between lighter and darker gray
            weekdayPanelArray[i].setBackground( i % 2 == 0 ? Color.decode("#f3f6f4") : Color.decode("#e7e7e7") );
            weekdayPanelArray[i].setOpaque(true);
            weekdayContainer.add(weekdayPanelArray[i]);
        }
    }

    private void createWeekdayPanels() {
        weekdayLabelPanel = new JPanel();
        weekdayLabelPanel.setLayout(new FlowLayout());
        weekdayLabelPanel.setBackground(Color.decode("#bbbaf0"));
        weekdayLabelPanel.setPreferredSize(new Dimension(WEEKDAY_LABEL_PANEL_WIDTH, WEEKDAY_LABEL_PANEL_HEIGHT));
        // Put 50x50 px "box" in the top right corner to keep the labels from going too far to the left.
        JLabel topRightBox = new JLabel();
        topRightBox.setPreferredSize(new Dimension(HOUR_PANEL_WIDTH, WEEKDAY_LABEL_PANEL_HEIGHT));
        weekdayLabelPanel.add(topRightBox);
        // Label the days of the week
        createWeekdayLabels();
    }

    private void createWeekdayLabels() {
        for (int i = 0; i < 7; i++) {
            weekdayLabelArray[i] = new JLabel(StringConstants.weekdays[i]);
            weekdayLabelArray[i].setFont(new Font("Arial", Font.BOLD, 24));
            weekdayLabelArray[i].setPreferredSize(new Dimension(WEEKDAY_LABEL_WIDTH, WEEKDAY_LABEL_HEIGHT));
            weekdayLabelArray[i].setHorizontalTextPosition(JLabel.RIGHT);
            weekdayLabelArray[i].setVerticalTextPosition(JLabel.BOTTOM);
            weekdayLabelPanel.add(weekdayLabelArray[i]);
        }
    }

    private void createHourPanels() {
        hourLabelPanel = new JPanel();
        hourLabelPanel.setLayout(new FlowLayout());
        hourLabelPanel.setBackground(Color.decode("#a9a7ed"));
        hourLabelPanel.setPreferredSize(new Dimension(HOUR_PANEL_WIDTH, HOUR_PANEL_HEIGHT));
        // Label the hours of the day
        createHourLabels();
    }

    private void createHourLabels() {
        for (int i = 0; i < 24; i++) {
            // Ensures a leading zero is added to hours from the [0,9] interval.
            hourLabelArray[i] = new JLabel((i < 10) ?
                    ("0" + Integer.valueOf(i).toString() + ":00") :
                    Integer.valueOf(i).toString() + ":00");
            hourLabelArray[i].setFont(new Font("Consolas", Font.BOLD, 12));
            hourLabelArray[i].setPreferredSize(new Dimension(HOUR_LABEL_WIDTH, HOUR_LABEL_HEIGHT));
            hourLabelArray[i].setHorizontalTextPosition(JLabel.LEFT);
            hourLabelArray[i].setVerticalTextPosition(JLabel.BOTTOM);

            hourLabelPanel.add(hourLabelArray[i]);
        }
    }

    @Override
    public void componentResized(ComponentEvent e) {
//        System.out.println(weekdayContainer.getWidth() + " " + weekdayContainer.getHeight());
        // Calculate the proportion used for scaling components based on weekdayContainer width & height updates
        double updatedPanelWidth = weekdayContainer.getWidth() * WEEKDAY_DISPLAY_WIDTH / PARENT_PANEL_WIDTH;
        double updatedPanelHeight = weekdayContainer.getHeight() * WEEKDAY_DISPLAY_HEIGHT / PARENT_PANEL_HEIGHT;
//        System.out.println(updatedPanelWidth + " " + updatedPanelHeight);
        // Scale and resize event display panels
        for (int i = 0; i < 7; i++) {
            weekdayPanelArray[i].setPreferredSize(new Dimension(
                    (int)Math.floor(updatedPanelWidth),
                    (int)Math.floor(updatedPanelHeight))
            );
        }
        // Update the proportion for day of the wek labels. Keep the height constant.
        updatedPanelWidth = weekdayContainer.getWidth() * WEEKDAY_LABEL_PANEL_WIDTH / PARENT_PANEL_WIDTH;
        // Scale and resize weekday labels
        for (int i = 0; i < 7; i++) {
            weekdayLabelArray[i].setPreferredSize(new Dimension(
                    (int)Math.floor(updatedPanelWidth),
                    WEEKDAY_LABEL_PANEL_HEIGHT)
            );
        }
    }

    @Override
    public void componentMoved(ComponentEvent e) {}

    @Override
    public void componentShown(ComponentEvent e) {}

    @Override
    public void componentHidden(ComponentEvent e) {}
}
