package social.laika.app.responses;

import android.content.Context;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import social.laika.app.R;
import social.laika.app.activities.MyDogsActivity;
import social.laika.app.fragments.AlarmReminderMyDogFragment;
import social.laika.app.interfaces.Requestable;
import social.laika.app.models.AlarmReminder;
import social.laika.app.models.Dog;
import social.laika.app.utils.Do;

/**
 * Created by Tito_Leiva on 07-05-15.
 */
public class CreateAlarmReminderResponse implements Response.Listener<JSONObject>, Response.ErrorListener {

    public static final String TAG = CreateAlarmReminderResponse.class.getSimpleName();

    public Context mContext;
    public Dog mDog;
    public Requestable mRequestable;

    public CreateAlarmReminderResponse(Context mContext, Requestable mRequestable) {
        this.mContext = mContext;
        this.mRequestable = mRequestable;
    }

    public CreateAlarmReminderResponse(Context context, Dog dog, Requestable mRequestable ) {

        this.mContext = context;
        this.mDog = dog;
        this.mRequestable = mRequestable;
    }

    @Override
    public void onResponse(JSONObject response) {

        AlarmReminder alarmReminder = AlarmReminder.saveReminder(response, mDog.mDogId, mContext);
        alarmReminder.setAlarm(mContext);

        mRequestable.onSuccess();
    }

    @Override
    public void onErrorResponse(VolleyError error) {

        Log.e(TAG, "Can't create the alarm");
        ResponseHandler.error(error, mContext);
        mRequestable.onFailure();

    }
}
