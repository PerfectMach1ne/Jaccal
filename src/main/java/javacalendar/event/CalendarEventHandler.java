package javacalendar.event;

import javax.swing.JLabel;
import java.awt.Color;

import java.util.HashMap;

import javacalendar.WeeksPanel;
import javacalendar.util.Colors;

public final class CalendarEventHandler extends WeeksPanel {
    public static HashMap<String, JLabel> eventStorage = new HashMap<>();
    public static HashMap<String, Integer> eventDays = new HashMap<>();
    public static HashMap<String, String> eventNames = new HashMap<>();
    public static HashMap<String, String> eventDescriptions = new HashMap<>();
    public static HashMap<String, Colors> eventColors = new HashMap<>();
    public static HashMap<String, String> eventStartHours = new HashMap<>();
    public static HashMap<String, String> eventEndHours = new HashMap<>();

    private static final int EVENT_POSITION_OFFSET = 14;
    private static final int HOUR_HEIGHT_UNIT = 29;

    // The event key generator function for the HashMaps. Could use a rewrite, the keys look kinda cool but they're
    // also an absolutely feral pile of arbitrary gibberish.
    public static String getEventKey(int day, String eventLabel, int eventStartHour, int eventEndHour) {
        String fixedString = eventLabel;
        if (fixedString.length() < 3) {
            while (fixedString.length() < 3) {
                fixedString = fixedString.concat("_");    // If the event has less than minimum 3 letters in the title,
            }                                             // then empty spots in the key are replaced with '_' signs.
            fixedString = eventLabel.substring(0, 3);
        }
        return Integer.valueOf(day).toString() + fixedString + Integer.valueOf(eventStartHour).toString()
                + "-" + Integer.valueOf(eventEndHour).toString();
        // 0Tes196:252
        // Meanings of individual characters:
        // 1 - day panel that the panel was assigned to
        // 2,3,4 - first 3 event characters
        // 2-4 chars - eventStartHour in raw value
        // single char inbetween - the '-' separator character
        // 2-4 chars - eventEndHour in raw value
    }

    public static void addCalendarEvent(int day, String eventLabel, String eventDescription, Color eventColor, Color textColor, String eventStartHour, String eventEndHour) {
        eventStorage.put(getEventKey(day, eventLabel, processHoursIntoEventStartValue(eventStartHour), processHoursIntoEventEndValue(eventEndHour)), new JLabel() {
            {
                setText("<html><body>" + "<div style=\"font-size:16;\">" + eventLabel +
                        "</div><div style=\"font-family:consolas; font-size:14;\">" + eventStartHour + " - " + eventEndHour +
                        "</div><div style=\"font-family:arial; font-size:13;\">" + textBreaker(eventDescription) + "</div></body></html>");
                setVerticalAlignment(JLabel.TOP);
                setForeground(textColor);
                setBackground(eventColor);
                setBounds(0, processHoursIntoEventStartValue(eventStartHour), 170, processHoursIntoEventEndValue(eventEndHour) - processHoursIntoEventStartValue(eventStartHour));
                setOpaque(true);
            }
        });
        System.out.println( "Added event with key " + getEventKey(day, eventLabel, processHoursIntoEventStartValue(eventStartHour), processHoursIntoEventEndValue(eventEndHour)) );

        weekdayPanelArray[day].add( eventStorage.get( getEventKey(day, eventLabel, processHoursIntoEventStartValue(eventStartHour), processHoursIntoEventEndValue(eventEndHour)) ) );
        eventDays.put(getEventKey(day, eventLabel, processHoursIntoEventStartValue(eventStartHour), processHoursIntoEventEndValue(eventEndHour)),
                day);
        eventNames.put(getEventKey(day, eventLabel, processHoursIntoEventStartValue(eventStartHour), processHoursIntoEventEndValue(eventEndHour)),
                eventLabel);
        eventDescriptions.put(getEventKey(day, eventLabel, processHoursIntoEventStartValue(eventStartHour), processHoursIntoEventEndValue(eventEndHour)),
                eventDescription );
        String RGBToHex = String.format("#%02x%02x%02x", eventColor.getRed(), eventColor.getGreen(), eventColor.getBlue());
        eventColors.put(getEventKey(day, eventLabel, processHoursIntoEventStartValue(eventStartHour), processHoursIntoEventEndValue(eventEndHour)),
                Colors.getColorFromHex(RGBToHex));
        eventStartHours.put(getEventKey(day, eventLabel, processHoursIntoEventStartValue(eventStartHour), processHoursIntoEventEndValue(eventEndHour)),
                eventStartHour);
        eventEndHours.put(getEventKey(day, eventLabel, processHoursIntoEventStartValue(eventStartHour), processHoursIntoEventEndValue(eventEndHour)),
                eventEndHour);
        WeeksPanel.weekdayPanelArray[day].revalidate();
        WeeksPanel.weekdayPanelArray[day].repaint();
    }

    public static void removeCalendarEventByHashKey(String key) {
        int day = Integer.parseInt(String.valueOf(key.charAt(0)));
        if ( CalendarEventHandler.eventStorage.containsKey(key) ) {
            WeeksPanel.weekdayPanelArray[day].remove(eventStorage.get(key));
            eventStorage.remove(key);
            eventDays.remove(key);
            eventNames.remove(key);
            eventDescriptions.remove(key);
            eventColors.remove(key);
            eventStartHours.remove(key);
            eventEndHours.remove(key);
            WeeksPanel.weekdayPanelArray[day].revalidate();
            WeeksPanel.weekdayPanelArray[day].repaint();
            System.out.println( "Deleted event with key " + key);
        }
    }

    // Transforms hours given as String and in hh:mm format into event box starer position
    public static int processHoursIntoEventStartValue(String stringHours) {
        // odległość od napisu "00:00" od krańca poziomego JPanelu wynosi 14px
        // 29px to średnia długość JLabeli w panelu godzin
        return getEVENT_POSITION_OFFSET() + (int)Math.round( getHOUR_HEIGHT_UNIT() * ( Double.parseDouble(stringHours.substring(0,2))
                + Double.parseDouble(stringHours.substring(3)) / 60 ) ); // HOURS + MINUTES / 60
    }

    // Transforms hours given as String and in hh:mm format into a parameter required for getting the event box height.
    public static int processHoursIntoEventEndValue(String stringHours) {
        /* Quoting the original comment:
        * "Adding 1 in the return statement fixes the alignment of labels when the method is used as an argument
        * in eventStart"
        * Why would you ever want to do that?! This function's called "EndValue" and not "StartValue" for a REASON!
        * I have to fix rewrite all of this, this is janky like a car built from scrap.
        * Part 2: "Without this, the box of an event ending at the hour 23:00 will reach no higher than the left panel's
        * 22:00 hour label."
        * Maybe it's because your code was shit, past me. Can't blame you, though (I had less than 1 week to get this
        * from start to finish (...then the time was extended for everyone on the due date)). */
        return getEVENT_POSITION_OFFSET() + (int)Math.round( getHOUR_HEIGHT_UNIT() * ( 1 + Double.parseDouble(stringHours.substring(0,2))
                + Double.parseDouble(stringHours.substring(3)) / 60 ) ); // 1 + HOURS + MINUTES / 60
    }

    // This Ta metoda naprawia "młotkiem" problem zbyt długich słow nie mieszczacych sie w eventDescription
    public static String textBreaker(String text) {
        /* Panel length can show about 23 digits, but because character lengths in pixels may differ, text is "cut"
         * and a HTML line break is inserted at the "slicing point" after just 21 characters for safety.
        */
        if (text.length() <= 21) {
            return text;
        }
        return text.substring(0,21) + "<br>" + textBreaker( text.substring(21) );
    }

    public static int getEVENT_POSITION_OFFSET() {
        return EVENT_POSITION_OFFSET;
    }

    public static int getHOUR_HEIGHT_UNIT() {
        return HOUR_HEIGHT_UNIT;
    }
}
