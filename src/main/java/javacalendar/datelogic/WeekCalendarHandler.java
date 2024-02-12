package javacalendar.datelogic;

import java.time.LocalDate;
// import java.time.LocalDateTime;

public class WeekCalendarHandler {
    // a

    WeekCalendarHandler() {
        
    }

    // Get every monthday of the current week in an array.
    public static int[] getCurrentWeekWeekdays() {
        int[] weekdays = new int[7];

        LocalDate weekDate = getCurrentWeekDate();
        for (int i = 0; i < 7; i++) {
            weekdays[i] = weekDate.getDayOfMonth();
            weekDate = weekDate.plusDays(1);
        }

        return weekdays;
    }

    // Obtains (without Time part) current LocalDate set at the beginning of the week.
    public static LocalDate getCurrentWeekDate() {
        LocalDate currentDate = LocalDate.now();

        int currentYear = currentDate.getYear();
        int currentMonth = currentDate.getMonthValue();
        int weekFirstMonthday = getLD_firstMonthWeekday(currentDate);

        // return: year, month, first day of week
        return LocalDate.of(currentYear, currentMonth, weekFirstMonthday);
    }

    // Get the monthday of the first day of the week (so, of a Monday of the current week!).
    public static int getLD_firstMonthWeekday(LocalDate localDate) {
        // As opposed to JavaScript, Java uses an Enum class DayOfWeek, wherein the weekday
        // numberings are contained in the interval [1,7].
        //
        //  int                 LocalDate long                DayOfWeek      int         int
        int firstMonthWeekday = localDate.minusDays(localDate.getDayOfWeek().getValue()).getDayOfMonth() + 1;

        return firstMonthWeekday;
    }

    // a
}
