package social.laika.app.responses;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import social.laika.app.fragments.HistoryMyDogFragment;
import social.laika.app.network.RequestManager;

/**
 * Created by Tito_Leiva on 07-05-15.
 */
public class CalendarRemindersResponse implements Response.Listener<JSONObject>, Response.ErrorListener {

    public HistoryMyDogFragment mFragment;
    public Context mContext;

    public CalendarRemindersResponse(Context mContext) {
        this.mContext = mContext;
    }

    public CalendarRemindersResponse(HistoryMyDogFragment mFragment) {
        this.mFragment = mFragment;
        this.mContext = mFragment.getActivity().getApplicationContext();


    }

    @Override
    public void onResponse(JSONObject response) {



    }

    @Override
    public void onErrorResponse(VolleyError error) {

        RequestManager.error(error, mContext);

    }
}
