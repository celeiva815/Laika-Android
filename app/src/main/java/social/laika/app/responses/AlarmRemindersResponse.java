package social.laika.app.responses;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import social.laika.app.models.AlarmReminder;
import social.laika.app.models.Dog;
import social.laika.app.network.Api;

/**
 * Created by Tito_Leiva on 07-05-15.
 */
public class AlarmRemindersResponse implements Response.Listener<JSONObject>, Response.ErrorListener {

    public Context mContext;
    public Dog mDog;

    public AlarmRemindersResponse(Context mContext, Dog mDog) {
        this.mContext = mContext;
        this.mDog = mDog;
    }

    @Override
    public void onResponse(JSONObject response) {

        AlarmReminder.saveReminder(response, mContext);

    }

    @Override
    public void onErrorResponse(VolleyError error) {

        Api.error(error, mContext);

    }
}
