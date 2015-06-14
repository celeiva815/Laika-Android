package cl.laikachile.laika.responses;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import cl.laikachile.laika.R;
import cl.laikachile.laika.activities.MyDogsActivity;
import cl.laikachile.laika.fragments.AlarmReminderMyDogFragment;
import cl.laikachile.laika.models.AlarmReminder;
import cl.laikachile.laika.network.utils.ResponseHandler;
import cl.laikachile.laika.utils.Do;

/**
 * Created by Tito_Leiva on 07-05-15.
 */
public class CreateAlarmReminderResponse implements Response.Listener<JSONObject>, Response.ErrorListener {

    public AlarmReminderMyDogFragment mFragment;
    public Context mContext;

    public CreateAlarmReminderResponse(AlarmReminderMyDogFragment mFragment) {
        this.mFragment = mFragment;
        this.mContext = mFragment.getActivity().getApplicationContext();
    }

    public CreateAlarmReminderResponse(Context context) {
        this.mContext = context;
    }

    @Override
    public void onResponse(JSONObject response) {

        AlarmReminder.saveReminder(response, mFragment.mDog.mDogId, mContext);

        if (mFragment != null) {

            String message = Do.getRString(mContext, R.string.new_reminder_added);
            Do.showLongToast(message, mContext);
            ((MyDogsActivity) mFragment.getActivity()).setHistoryFragment(mFragment.mDog);
        }

    }

    @Override
    public void onErrorResponse(VolleyError error) {

        ResponseHandler.error(error, mContext);

    }
}
