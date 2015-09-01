package social.laika.app.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Benjamin on 06-11-2014.
 */
public class DateFormatter {

    private static final SimpleDateFormat API_DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy HH:mm");
    private static final SimpleDateFormat LOCAL_DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy HH:mm");

    public static String shortDateFormat(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.setTimeZone(TimeZone.getTimeZone("America/Santiago"));
        Integer weekDay = calendar.get(Calendar.DAY_OF_WEEK);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(" dd/MM HH:mm");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("America/Santiago"));

        Character weekDayCharacter = 'L';

        switch (weekDay) {

            case Calendar.SUNDAY:
                weekDayCharacter = 'D';
                break;
            case Calendar.MONDAY:
                weekDayCharacter = 'L';
                break;
            case Calendar.TUESDAY:
                weekDayCharacter = 'M';
                break;
            case Calendar.WEDNESDAY:
                weekDayCharacter = 'X';
                break;
            case Calendar.THURSDAY:
                weekDayCharacter = 'J';
                break;
            case Calendar.FRIDAY:
                weekDayCharacter = 'V';
                break;
            case Calendar.SATURDAY:
                weekDayCharacter = 'S';
                break;

            default:
                break;
        }

        return weekDayCharacter + simpleDateFormat.format(date);
    }


    public static String apiStringFromDate(Date date) {
        return API_DATE_FORMAT.format(date);
    }

    public static Date dateFromString(String stringDate) throws ParseException {
        return API_DATE_FORMAT.parse(stringDate);
    }

    public static String humanReadableDateFormat(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd' a las 'HH:mm");
        return simpleDateFormat.format(date);
    }

    public static int[] parseTimeFromString(String time) {

        try {

            int hour = Integer.parseInt(time.substring(0, 2));
            int minute = Integer.parseInt(time.substring(3, 5));

            return new int[]{hour, minute};

        } catch(StringIndexOutOfBoundsException e) {
            e.printStackTrace();
            return new int[]{0,0};
        }

    }

}
