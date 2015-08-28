package social.laika.app.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;

import java.util.Calendar;
import java.util.Date;

import social.laika.app.activities.StopAlarmActivity;
import social.laika.app.activities.StopCalendarActivity;
import social.laika.app.models.AlarmReminder;
import social.laika.app.models.CalendarReminder;

/**
 * Created by Tito_Leiva on 18-05-15.
 */
public class AlarmReceiver extends BroadcastReceiver {

    public static final String ONE_TIME = "onetime";
    public static final String TYPE = "type";
    public final static String WEEKDAY = AlarmReminder.WEEKDAY;

    @Override
    public void onReceive(Context context, Intent intent) {

        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "YOUR TAG");
        //Acquire the lock
        wl.acquire();

        //You can do the processing here.
        Bundle extras = intent.getExtras();
        int type = extras.getInt(TYPE);
        Calendar calendar = Calendar.getInstance();
        boolean isMomentToWakeUp = false;
        calendar.setTime(new Date());

        switch (type) {

            case Tag.TYPE_ALARM: {

                String time = extras.getString(AlarmReminder.COLUMN_TIME);
                int weekday = extras.getInt(WEEKDAY);
                int alarmDay = calendar.get(Calendar.DAY_OF_WEEK);
                int alarmHour = calendar.get(Calendar.HOUR_OF_DAY);
                calendar.add(Calendar.HOUR_OF_DAY, -1);
                int prevHour = calendar.get(Calendar.HOUR_OF_DAY);
                int hour = DateFormatter.parseTimeFromString(time)[0];

                isMomentToWakeUp = weekday == alarmDay && (hour == alarmHour || hour == prevHour);

                if (isMomentToWakeUp) {

                    Intent alarmIntent = new Intent(context, StopAlarmActivity.class);

                    alarmIntent.putExtras(extras);
                    alarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(alarmIntent);

                }

                break;
            }
            case Tag.TYPE_CALENDAR: {

                String date = extras.getString(CalendarReminder.COLUMN_DATE);
                String time = extras.getString(CalendarReminder.COLUMN_TIME);
                int actualDay = calendar.get(Calendar.DAY_OF_YEAR);
                int actualHour = calendar.get(Calendar.HOUR_OF_DAY);
                calendar.add(Calendar.HOUR_OF_DAY, -1);
                int prevHour = calendar.get(Calendar.HOUR_OF_DAY);
                int alarmHour = DateFormatter.parseTimeFromString(time)[0];
                calendar.setTime(Do.stringToDate(date, Do.DAY_FIRST));
                int alarmDay = calendar.get(Calendar.DAY_OF_YEAR);

                isMomentToWakeUp = alarmDay == actualDay && (alarmHour == actualHour || alarmHour == prevHour);

                if (isMomentToWakeUp) {

                    Intent alarmIntent = new Intent(context, StopCalendarActivity.class);
                    alarmIntent.putExtras(extras);
                    alarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(alarmIntent);
                }

                break;
            }
        }

        //Release the lock
        wl.release();

    }
}
