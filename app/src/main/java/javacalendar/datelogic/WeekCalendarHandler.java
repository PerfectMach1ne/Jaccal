package javacalendar.datelogic;

import java.time.LocalDate;

public class WeekCalendarHandler {
    /**
     * Offset the LocalDate instance (passed as a parameter) by the amount of days required to set it
     * at the first day of the week (so, tl;dr: offsets a LocalDate instance to Monday).
     * @param localDate - a LocalDate instance.
     * @return firstMonthWeekday - month.
     */
    public static LocalDate getOffsetDate(LocalDate localDate) {
        // Subtract an x in the interval [0,6] from the localDate:
        return localDate.minusDays(localDate.getDayOfWeek().getValue() - 1);
    }

    /**
     * Returns an instance of LocalDate.now() offset to a Monday (so the first day of the week).
     * It has to be a LocalDate instance specifically, so that time isn't included in this method.
     * @return LocalDate
     */
    public static LocalDate getCurrentWeekDate() {
        LocalDate currentDate = LocalDate.now();
        
        LocalDate offsetDate = getOffsetDate(currentDate);
        int mondayMonthday = offsetDate.getDayOfMonth();

        return LocalDate.of(offsetDate.getYear(), offsetDate.getMonthValue(), mondayMonthday);
    }
    
    /**
     * Returns an instance of LocalDate, first offset a week backwards, then offset to a Monday
     * (so the first day of the week).
     * It has to be a LocalDate instance specifically, so that time isn't included in this method.
     * @return LocalDate
     */
    public static LocalDate getPastWeekDate(LocalDate date) {
        date = date.minusWeeks(1);
        
        LocalDate offsetDate = getOffsetDate(date);
        int mondayMonthday = offsetDate.getDayOfMonth();

        return LocalDate.of(offsetDate.getYear(), offsetDate.getMonth(), mondayMonthday);
    }

    /**
     * Returns an instance of LocalDate first offset a week forward, then offset to a Monday
     * (so the first day of the week).
     * It has to be a LocalDate instance specifically, so that time isn't included in this method.
     * @return LocalDate
     */
    public static LocalDate getFutureWeekDate(LocalDate date) {
        date = date.plusWeeks(1);
        
        LocalDate offsetDate = getOffsetDate(date);
        int mondayMonthday = offsetDate.getDayOfMonth();

        return LocalDate.of(offsetDate.getYear(), offsetDate.getMonth(), mondayMonthday);
    }

    /**
     * Return an array of seven monthdays corresponding to the week of a LocalDate instance
     * (assumes that the LocalDate instance passed as a parameter is offset to Monday).
     * 
     * @param date
     * @return int[] - a 7-element array of integer monthdays.
     */
    // Get every monthday of the current week in an array.
    public static int[] getWeekWeekdays(LocalDate date) {
        int[] weekdays = new int[7];

        for (int i = 0; i < 7; i++) {
            weekdays[i] = date.getDayOfMonth();
            date = date.plusDays(1);
        }

        return weekdays;
    }
}
