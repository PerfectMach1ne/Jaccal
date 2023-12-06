package javacalendar.util;

// A few methods to assist add, change and remove event windows in handling week days.
public class WeekdayMethods {
    public static int stringToWeekday(String weekday) {
        switch (weekday) {
            case "Mon":
                return 0;
            case "Tue":
                return 1;
            case "Wed":
                return 2;
            case "Thu":
                return 3;
            case "Fri":
                return 4;
            case "Sat":
                return 5;
            case "Sun":
                return 6;
        }
        return -1;
    }

    public static String weekdayToString(int weekday) {
        switch (weekday) {
            case 0:
                return "Monday";
            case 1:
                return "Tuesday";
            case 2:
                return "Wednesday";
            case 3:
                return "Thursday";
            case 4:
                return "Friday";
            case 5:
                return "Saturday";
            case 6:
                return "Sunday";
        }
        return "undefined";
    }
}
