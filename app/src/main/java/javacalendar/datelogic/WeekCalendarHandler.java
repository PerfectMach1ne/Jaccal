package javacalendar.datelogic;

import java.time.LocalDate;
// import java.time.LocalDateTime;

public class WeekCalendarHandler {
    WeekCalendarHandler() {
        
    }

    // Get every monthday of the current week in an array.
    public static int[] getCurrentWeekWeekdays(byte mode) {
        int[] weekdays = new int[7];
        LocalDate weekDate;

        if (mode == 0) {
            weekDate = getLD_CurrentWeekDate();

            for (int i = 0; i < 7; i++) {
                weekdays[i] = weekDate.getDayOfMonth();
                weekDate = weekDate.plusDays(1);
            }
        }

        return weekdays;
    }

    public static int[] getCurrentWeekWeekdays(byte mode, LocalDate date) {
        int[] weekdays = new int[7];
        LocalDate weekDate;
        
        if (mode == -1) {
            weekDate = getLD_PastWeekDate(date);

            for (int i = 0; i < 7; i++) {
                weekdays[i] = weekDate.getDayOfMonth();
                weekDate = weekDate.plusDays(1);
            }
        } else if (mode == 1) {
            weekDate = getLD_FutureWeekDate(date);

            for (int i = 0; i < 7; i++) {
                weekdays[i] = weekDate.getDayOfMonth();
                weekDate = weekDate.plusDays(1);
            }
        }

        return weekdays;

    }

    // Obtains (without Time part) current LocalDate set at the beginning of the week.
    public static LocalDate getLD_CurrentWeekDate() {
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
        int firstMonthWeekday = localDate.minusDays(localDate.minusDays(1).getDayOfWeek().getValue()).getDayOfMonth();
        // if (firstMonthWeekday == 32) firstMonthWeekday = 1;

        return firstMonthWeekday;
    }

    public static LocalDate getLD_PastWeekDate(LocalDate date) {
        date = date.minusWeeks(1);
        
        int currentYear = date.getYear();
        int currentMonth = date.getMonthValue();
        int weekFirstMonthday = getLD_firstMonthWeekday(date);

        // return: year, month, first day of week
        return LocalDate.of(currentYear, currentMonth, weekFirstMonthday);
    }

    public static LocalDate getLD_FutureWeekDate(LocalDate date) {
        date = date.plusWeeks(1);
        
        int currentYear = date.getYear();
        int currentMonth = date.getMonthValue();
        int weekFirstMonthday = getLD_firstMonthWeekday(date);

        // return: year, month, first day of week
        return LocalDate.of(currentYear, currentMonth, weekFirstMonthday);
    }
}