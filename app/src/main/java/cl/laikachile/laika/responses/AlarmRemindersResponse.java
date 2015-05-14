package cl.laikachile.laika.responses;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import cl.laikachile.laika.R;
import cl.laikachile.laika.activities.AdoptDogSuccessActivity;
import cl.laikachile.laika.fragments.AlarmReminderMyDogFragment;
import cl.laikachile.laika.fragments.HistoryMyDogFragment;
import cl.laikachile.laika.models.AlarmReminder;
import cl.laikachile.laika.models.Dog;
import cl.laikachile.laika.network.utils.ResponseHandler;
import cl.laikachile.laika.utils.Do;
import cl.laikachile.laika.utils.Tag;

/**
 * Created by Tito_Leiva on 07-05-15.
 */
public class AlarmRemindersResponse implements Response.Listener<JSONObject>, Response.ErrorListener {

    public HistoryMyDogFragment mFragment;
    public Context mContext;

    public AlarmRemindersResponse(HistoryMyDogFragment mFragment) {
        this.mFragment = mFragment;
        this.mContext = mFragment.getActivity().getApplicationContext();
    }

    @Override
    public void onResponse(JSONObject response) {

        AlarmReminder.saveReminder(response, mFragment.mDog.mDogId, mContext);
        mFragment.refreshHistories();

    }

    @Override
    public void onErrorResponse(VolleyError error) {

        ResponseHandler.error(error, mContext);

    }
}
