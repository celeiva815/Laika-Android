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
        calendar.setTime(new Date());

        switch (type) {

            case Tag.TYPE_ALARM:

                String time = extras.getString(AlarmReminder.COLUMN_TIME);
                int weekday = extras.getInt(WEEKDAY);
                int alarmDay = calendar.get(Calendar.DAY_OF_WEEK);
                int alarmHour = calendar.get(Calendar.HOUR_OF_DAY);
                calendar.add(Calendar.HOUR_OF_DAY, -1);
                int prevHour = calendar.get(Calendar.HOUR_OF_DAY);
                int hour = DateFormatter.parseTimeFromString(time)[0];

                boolean isMomentToWakeUp = weekday == alarmDay && (hour == alarmHour || hour == prevHour);

                if (isMomentToWakeUp) {

                    Intent alarmIntent = new Intent(context, StopAlarmActivity.class);

                    alarmIntent.putExtras(extras);
                    alarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(alarmIntent);

                }

                break;

            case Tag.TYPE_CALENDAR:

                //TODO implementar lo mismo que se hizo en alarmas

                Intent alarmIntent = new Intent(context,
                        type == Tag.TYPE_ALARM ? StopAlarmActivity.class : StopCalendarActivity.class);
                alarmIntent.putExtras(extras);

                alarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(alarmIntent);

                break;
        }

        //Release the lock
        wl.release();

    }
}
