package social.laika.app.interfaces;

import android.content.Context;

import java.util.Date;

import social.laika.app.models.Reminder;

/**
 * Created by Tito_Leiva on 18-08-15.
 */
public interface Alertable {

    Reminder toHistory(Context context);
    void setAlarm(Context context);
    void cancelAlarm(Context context);
    int checkStatusAlarm(Context context);
    Date createdAt();
}
