package social.laika.app.interfaces;

import android.content.Context;

import social.laika.app.models.History;

/**
 * Created by Tito_Leiva on 18-08-15.
 */
public interface Alertable {

    History toHistory(Context context);
    void setAlarm(Context context);
    void cancelAlarm(Context context);
    int checkStatusAlarm(Context context);

}
