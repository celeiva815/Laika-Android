package social.laika.app.responses;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import social.laika.app.R;
import social.laika.app.activities.MyDogsActivity;
import social.laika.app.fragments.AlarmReminderMyDogFragment;
import social.laika.app.models.AlarmReminder;
import social.laika.app.models.Dog;
import social.laika.app.utils.Do;

/**
 * Created by Tito_Leiva on 07-05-15.
 */
public class CreateAlarmReminderResponse implements Response.Listener<JSONObject>, Response.ErrorListener {

    public AlarmReminderMyDogFragment mFragment;
    public Context mContext;
    public Dog mDog;

    public CreateAlarmReminderResponse(AlarmReminderMyDogFragment mFragment) {
        this.mFragment = mFragment;
        this.mContext = mFragment.getActivity().getApplicationContext();
        this.mDog = mFragment.mDog;
    }

    public CreateAlarmReminderResponse(Context context, Dog dog) {

        this.mContext = context;
        this.mDog = dog;
    }

    @Override
    public void onResponse(JSONObject response) {

        AlarmReminder.saveReminder(response, mDog.mDogId, mContext);

        if (mFragment != null) {

            String message = Do.getRString(mContext, R.string.new_reminder_added);
            Do.showLongToast(message, mContext);
            ((MyDogsActivity) mFragment.getActivity()).setHistoryFragment(mDog);
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {

        ResponseHandler.error(error, mContext);

    }
}
