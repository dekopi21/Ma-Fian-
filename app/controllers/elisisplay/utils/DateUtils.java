package controllers.elisisplay.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Utilitaires de fonctions sur les dates.
 */
@SuppressWarnings("MagicConstant")
public class DateUtils {

    public static final char defaultDateSeparator = '-';

    public static final char defaultHourSeparator = ':';

    public static String dayStr(Calendar calendar) {
        return Utils.generateCode("", calendar.get(Calendar.DAY_OF_MONTH), 2);
    }

    public static String monthStr(Calendar calendar) {
        return Utils.generateCode("", calendar.get(Calendar.MONTH), 2);
    }

    public static String yearStr(Calendar calendar) {
        return Utils.generateCode("", calendar.get(Calendar.YEAR), 4);
    }

    public static String dateStr(Calendar calendar, char dateSeparator, char hourSeparator) {
        return yearStr(calendar) + dateSeparator + monthStr(calendar) + dateSeparator + dayStr(calendar) + " "
                + hourStr(calendar) + hourSeparator + minStr(calendar) + hourSeparator + secStr(calendar);
    }

    public static String hourStr(Calendar calendar) {
        return Utils.generateCode("", calendar.get(Calendar.HOUR_OF_DAY), 2);
    }

    public static String minStr(Calendar calendar) {
        return Utils.generateCode("", calendar.get(Calendar.MINUTE), 2);
    }

    public static String secStr(Calendar calendar) {
        return Utils.generateCode("", calendar.get(Calendar.SECOND), 2);
    }

    public static String dateStr(Calendar calendar){
        return dateStr(calendar, defaultDateSeparator, defaultHourSeparator);
    }

    public static int day(Calendar calendar) {
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static int year(Calendar calendar) {
        return calendar.get(Calendar.YEAR);
    }

    public static int month(Calendar calendar) {
        return calendar.get(Calendar.MONTH);
    }

    public static final int firstDayOfWeek = Calendar.SUNDAY;

    public static Date convertToDate(Calendar calendar) {
        if (calendar == null)
            return new Date();

        return new Date(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE),
                calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND));
    }

    public static Calendar convertToCalendar(Date date){
        return new GregorianCalendar(date.getYear(), date.getMonth(), date.getDate(),
                date.getHours(), date.getMinutes(), date.getSeconds());
    }

    public static String formatFr() {
        return formatFr(new GregorianCalendar());
    }

    public static String formatFrDataTable(Calendar calendar) {
        if (calendar == null)
            return "";

        return dateStr(calendar) + " (" + formatFr(calendar) + ") ";
    }

    public static String formatFr(Calendar calendar) {
        if (calendar == null)
            return "";

        return dayofWeekStr(calendar.get(Calendar.DAY_OF_WEEK)) + " " + Utils.generateCode("", calendar.get(Calendar.DAY_OF_MONTH), 2) + " "
                + monthStrFr(calendar.get(Calendar.MONTH)) + " " + Utils.generateCode("", calendar.get(Calendar.YEAR), 4) + " " +
                calendar.get(Calendar.HOUR_OF_DAY) + ":" + Utils.generateCode("", calendar.get(Calendar.MINUTE), 2) + ":" +
                Utils.generateCode("", calendar.get(Calendar.SECOND), 2);
    }

    public static String formatFrDate(Calendar calendar) {
        if (calendar == null)
            return "";

        return dayofWeekStr(calendar.get(Calendar.DAY_OF_WEEK)) + " " + Utils.generateCode("", calendar.get(Calendar.DAY_OF_MONTH), 2) + " "
                + monthStrFr(calendar.get(Calendar.MONTH)) + " " + Utils.generateCode("", calendar.get(Calendar.YEAR), 4);
    }

    public static String dayofWeekStr(int dayOfWeek) {
        switch (dayOfWeek) {
            case 1:
                return "Dimanche";

            case 2:
                return "Lundi";

            case 3:
                return "Mardi";

            case 4:
                return "Mercredi";

            case 5:
                return "Jeudi";

            case 6:
                return "Vendredi";

            case 7:
                return "Samedi";

            default:
                return dayofWeekStr(firstDayOfWeek);
        }
    }

    public static String monthStrFr(Calendar calendar) {
        return monthStrFr(month(calendar));
    }

    public static String monthStrFr(int month) {
        switch (month) {
            case 0:
                return "Janvier";

            case 1:
                return "Février";

            case 2:
                return "Mars";

            case 3:
                return "Avril";

            case 4:
                return "Mai";

            case 5:
                return "Juin";

            case 6:
                return "juillet";

            case 7:
                return "Août";

            case 8:
                return "Septembre";

            case 9:
                return "Octobre";

            case 10:
                return "Novembre";

            case 11:
                return "Décembre";

            default:
                return monthStrFr(Calendar.JANUARY);
        }
    }

    public static int age(Calendar dateNaissance) {
        return new GregorianCalendar().get(Calendar.YEAR) - dateNaissance.get(Calendar.YEAR);
    }

    public static String dayfr(byte day) {
        return dayofWeekStr(day).substring(0, 3);
    }

    public static boolean sameDay(GregorianCalendar toCompare, GregorianCalendar reference) {
        return lastNDay(toCompare, reference, 0);
    }

    public static boolean sameDay(GregorianCalendar toCompare) {
        return lastNDay(toCompare, 0);
    }

    public static boolean yesterday(GregorianCalendar toCompare, GregorianCalendar reference) {
        return lastNDay(toCompare, reference, 1);
    }

    public static boolean yesterday(GregorianCalendar toCompare) {
        return lastNDay(toCompare, 1);
    }

    public static boolean tomorrow(GregorianCalendar toCompare, GregorianCalendar reference) {
        return nextNDay(toCompare, reference, 1);
    }

    public static boolean tomorrow(GregorianCalendar toCompare) {
        return nextNDay(toCompare, 1);
    }

    public static boolean thisMonth(GregorianCalendar toCompare, GregorianCalendar reference) {
        return lastNMonth(toCompare, reference, 0);
    }

    public static boolean thisMonth(GregorianCalendar toCompare) {
        return lastNMonth(toCompare, 0);
    }

    public static boolean nextMonth(GregorianCalendar toCompare, GregorianCalendar reference) {
        return nextNMonth(toCompare, reference, 1);
    }

    public static boolean nextMonth(GregorianCalendar toCompare) {
        return nextNMonth(toCompare, 1);
    }

    public static boolean lastMonth(GregorianCalendar toCompare, GregorianCalendar reference) {
        return lastNMonth(toCompare, reference, 1);
    }

    public static boolean lastMonth(GregorianCalendar toCompare) {
        return lastNMonth(toCompare, 1);
    }

    public static boolean thisWeek(GregorianCalendar toCompare, GregorianCalendar reference) {
        return lastNWeek(toCompare, reference, 0);
    }

    public static boolean thisWeek(GregorianCalendar toCompare) {
        return lastNWeek(toCompare, 0);
    }

    public static boolean nextWeek(GregorianCalendar toCompare, GregorianCalendar reference) {
        return nextNWeek(toCompare, reference, 1);
    }

    public static boolean nextWeek(GregorianCalendar toCompare) {
        return nextNWeek(toCompare, 1);
    }

    public static boolean lastWeek(GregorianCalendar toCompare, GregorianCalendar reference) {
        return lastNWeek(toCompare, reference, 1);
    }

    public static boolean lastWeek(GregorianCalendar toCompare) {
        return lastNWeek(toCompare, 1);
    }

    public static boolean thisYear(GregorianCalendar toCompare, GregorianCalendar reference) {
        return lastNWeek(toCompare, reference, 0);
    }

    public static boolean thisYear(GregorianCalendar toCompare) {
        return lastNWeek(toCompare, 0);
    }

    public static boolean nextYear(GregorianCalendar toCompare, GregorianCalendar reference) {
        return nextNYear(toCompare, reference, 1);
    }

    public static boolean nextYear(GregorianCalendar toCompare) {
        return nextNYear(toCompare, 1);
    }

    public static boolean lastYear(GregorianCalendar toCompare, GregorianCalendar reference) {
        return lastNYear(toCompare, reference, 1);
    }

    public static boolean lastYear(GregorianCalendar toCompare) {
        return lastNYear(toCompare, 1);
    }

    public static boolean lastNDays(GregorianCalendar toCompare, GregorianCalendar reference, int maxDifference) {
        return (toCompare.get(Calendar.YEAR) == reference.get(Calendar.YEAR) &&
                toCompare.get(Calendar.DAY_OF_YEAR) >= reference.get(Calendar.DAY_OF_YEAR) - maxDifference);
    }

    public static boolean nextNDays(GregorianCalendar toCompare, GregorianCalendar reference, int maxDifference) {
        return (toCompare.get(Calendar.YEAR) == reference.get(Calendar.YEAR) &&
                toCompare.get(Calendar.DAY_OF_YEAR) <= reference.get(Calendar.DAY_OF_YEAR) + maxDifference);
    }

    public static boolean lastNDays(GregorianCalendar toCompare, int maxDifference) {
        return lastNDays(toCompare, new GregorianCalendar(), maxDifference);
    }

    public static boolean nextNDays(GregorianCalendar toCompare, int maxDifference) {
        return nextNDays(toCompare, new GregorianCalendar(), maxDifference);
    }

    public static boolean lastNDay(GregorianCalendar toCompare, GregorianCalendar reference, int maxDifference) {
        return ((toCompare.get(Calendar.YEAR) == reference.get(Calendar.YEAR)) &&
                (toCompare.get(Calendar.DAY_OF_YEAR) == (reference.get(Calendar.DAY_OF_YEAR) - maxDifference)));
    }

    public static boolean nextNDay(GregorianCalendar toCompare, GregorianCalendar reference, int maxDifference) {
        return (toCompare.get(Calendar.YEAR) == reference.get(Calendar.YEAR) &&
                toCompare.get(Calendar.DAY_OF_YEAR) == reference.get(Calendar.DAY_OF_YEAR) + maxDifference);
    }

    public static boolean lastNDay(GregorianCalendar toCompare, int maxDifference) {
        return lastNDay(toCompare, new GregorianCalendar(), maxDifference);
    }

    public static boolean nextNDay(GregorianCalendar toCompare, int maxDifference) {
        return nextNDay(toCompare, new GregorianCalendar(), maxDifference);
    }

    public static boolean lastNYears(GregorianCalendar toCompare, GregorianCalendar reference, int maxDifference) {
        return (toCompare.get(Calendar.YEAR) >= reference.get(Calendar.YEAR) - maxDifference);
    }

    public static boolean nextNYears(GregorianCalendar toCompare, GregorianCalendar reference, int maxDifference) {
        return (toCompare.get(Calendar.YEAR) <= reference.get(Calendar.YEAR) + maxDifference);
    }

    public static boolean lastNYears(GregorianCalendar toCompare, int maxDifference) {
        return lastNYears(toCompare, new GregorianCalendar(), maxDifference);
    }

    public static boolean nextNYears(GregorianCalendar toCompare, int maxDifference) {
        return nextNYears(toCompare, new GregorianCalendar(), maxDifference);
    }

    public static boolean lastNYear(GregorianCalendar toCompare, GregorianCalendar reference, int maxDifference) {
        return (toCompare.get(Calendar.YEAR) == reference.get(Calendar.YEAR) - maxDifference);
    }

    public static boolean lastNYear(GregorianCalendar toCompare, int maxDifference) {
        return lastNYear(toCompare, new GregorianCalendar(), maxDifference);
    }

    public static boolean nextNYear(GregorianCalendar toCompare, GregorianCalendar reference, int maxDifference) {
        return (toCompare.get(Calendar.YEAR) == reference.get(Calendar.YEAR) + maxDifference);
    }

    public static boolean nextNYear(GregorianCalendar toCompare, int maxDifference) {
        return nextNYear(toCompare, new GregorianCalendar(), maxDifference);
    }

    public static boolean lastNMonths(GregorianCalendar toCompare, GregorianCalendar reference, int maxDifference) {
        return (toCompare.get(Calendar.YEAR) == reference.get(Calendar.YEAR) &&
                toCompare.get(Calendar.MONTH) >= reference.get(Calendar.MONTH) - maxDifference);
    }

    public static boolean lastNMonths(GregorianCalendar toCompare, int maxDifference) {
        return lastNMonths(toCompare, new GregorianCalendar(), maxDifference);
    }

    public static boolean nextNMonths(GregorianCalendar toCompare, GregorianCalendar reference, int maxDifference) {
        return (toCompare.get(Calendar.YEAR) == reference.get(Calendar.YEAR) &&
                toCompare.get(Calendar.MONTH) <= reference.get(Calendar.MONTH) + maxDifference);
    }

    public static boolean nextNMonths(GregorianCalendar toCompare, int maxDifference) {
        return nextNMonths(toCompare, new GregorianCalendar(), maxDifference);
    }

    public static boolean lastNMonth(GregorianCalendar toCompare, GregorianCalendar reference, int maxDifference) {
        return (toCompare.get(Calendar.YEAR) == reference.get(Calendar.YEAR) &&
                toCompare.get(Calendar.MONTH) == reference.get(Calendar.MONTH) - maxDifference);
    }

    public static boolean lastNMonth(GregorianCalendar toCompare, int maxDifference) {
        return lastNMonth(toCompare, new GregorianCalendar(), maxDifference);
    }

    public static boolean nextNMonth(GregorianCalendar toCompare, GregorianCalendar reference, int maxDifference) {
        return (toCompare.get(Calendar.YEAR) == reference.get(Calendar.YEAR) &&
                toCompare.get(Calendar.MONTH) == reference.get(Calendar.MONTH) + maxDifference);
    }

    public static boolean nextNMonth(GregorianCalendar toCompare, int maxDifference) {
        return nextNMonth(toCompare, new GregorianCalendar(), maxDifference);
    }

    public static boolean lastNWeeks(GregorianCalendar toCompare, GregorianCalendar reference, int maxDifference) {
        return (toCompare.get(Calendar.YEAR) == reference.get(Calendar.YEAR) &&
                toCompare.get(Calendar.WEEK_OF_YEAR) >= reference.get(Calendar.WEEK_OF_YEAR) - maxDifference);
    }

    public static boolean lastNWeeks(GregorianCalendar toCompare, int maxDifference) {
        return lastNWeeks(toCompare, new GregorianCalendar(), maxDifference);
    }

    public static boolean nextNWeeks(GregorianCalendar toCompare, GregorianCalendar reference, int maxDifference) {
        return (toCompare.get(Calendar.YEAR) == reference.get(Calendar.YEAR) &&
                toCompare.get(Calendar.WEEK_OF_YEAR) <= reference.get(Calendar.WEEK_OF_YEAR) + maxDifference);
    }

    public static boolean nextNWeeks(GregorianCalendar toCompare, int maxDifference) {
        return nextNWeeks(toCompare, new GregorianCalendar(), maxDifference);
    }

    public static boolean lastNWeek(GregorianCalendar toCompare, GregorianCalendar reference, int maxDifference) {
        return (toCompare.get(Calendar.YEAR) == reference.get(Calendar.YEAR) &&
                toCompare.get(Calendar.WEEK_OF_YEAR) == reference.get(Calendar.WEEK_OF_YEAR) - maxDifference);
    }

    public static boolean lastNWeek(GregorianCalendar toCompare, int maxDifference) {
        return lastNWeek(toCompare, new GregorianCalendar(), maxDifference);
    }

    public static boolean nextNWeek(GregorianCalendar toCompare, GregorianCalendar reference, int maxDifference) {
        return (toCompare.get(Calendar.YEAR) == reference.get(Calendar.YEAR) &&
                toCompare.get(Calendar.WEEK_OF_YEAR) == reference.get(Calendar.WEEK_OF_YEAR) + maxDifference);
    }

    public static boolean nextNWeek(GregorianCalendar toCompare, int maxDifference) {
        return nextNWeek(toCompare, new GregorianCalendar(), maxDifference);
    }
}
