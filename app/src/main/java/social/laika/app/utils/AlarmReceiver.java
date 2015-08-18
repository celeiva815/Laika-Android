package social.laika.app.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;

import social.laika.app.activities.StopAlarmActivity;
import social.laika.app.activities.StopCalendarActivity;

/**
 * Created by Tito_Leiva on 18-05-15.
 */
public class AlarmReceiver extends BroadcastReceiver {

    public static final String ONE_TIME = "onetime";
    public static final String TYPE = "type";

    @Override
    public void onReceive(Context context, Intent intent) {

        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "YOUR TAG");
        //Acquire the lock
        wl.acquire();

        //You can do the processing here.
        Bundle extras = intent.getExtras();
        int type = extras.getInt(TYPE);

        Intent scheduledIntent = new Intent(context,
                type == Tag.TYPE_ALARM ? StopAlarmActivity.class : StopCalendarActivity.class);
        scheduledIntent.putExtras(extras);

        scheduledIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(scheduledIntent);

        //Release the lock
        wl.release();


    }

}
