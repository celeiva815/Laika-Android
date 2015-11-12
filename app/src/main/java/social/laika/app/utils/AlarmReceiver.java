package social.laika.app.utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;

import java.util.Calendar;
import java.util.Date;

import social.laika.app.R;
import social.laika.app.activities.MainActivity;
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
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK |
                PowerManager.ACQUIRE_CAUSES_WAKEUP |
                PowerManager.ON_AFTER_RELEASE, "YOUR TAG");
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

                isMomentToWakeUp = weekday == alarmDay && hour == alarmHour;

                if (isMomentToWakeUp) {

                    long localId = extras.getLong(StopAlarmActivity.LOCAL_ID);
                    AlarmReminder reminder = AlarmReminder.load(AlarmReminder.class, localId);

                    Intent alarmIntent = new Intent(context, StopAlarmActivity.class);

                    alarmIntent.putExtras(extras);
                    alarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    sendNotification(context, alarmIntent, reminder.mTitle, reminder.mDetail);

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

                isMomentToWakeUp = alarmDay == actualDay && alarmHour == actualHour;

                if (isMomentToWakeUp) {

                    long localId = extras.getLong(StopCalendarActivity.LOCAL_ID);
                    CalendarReminder reminder = CalendarReminder.load(CalendarReminder.class, localId);

                    Intent calendarIntent = new Intent(context, StopCalendarActivity.class);
                    calendarIntent.putExtras(extras);
                    calendarIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    sendNotification(context, calendarIntent, reminder.mTitle, reminder.mDetail);
                }

                break;
            }
        }

        //Release the lock
        wl.release();

    }

    private void sendNotification(Context context, Intent intent, String message, String title) {

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri soundUri = Uri.parse("android.resource://" + context.getPackageName() + "/" +
                R.raw.ladrido);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.laika_k)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(soundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}
