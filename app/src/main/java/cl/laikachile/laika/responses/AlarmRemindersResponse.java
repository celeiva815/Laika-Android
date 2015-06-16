package cl.laikachile.laika.responses;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import cl.laikachile.laika.models.AlarmReminder;
import cl.laikachile.laika.models.Dog;

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

        AlarmReminder.saveReminder(response, mDog.mDogId, mContext);

    }

    @Override
    public void onErrorResponse(VolleyError error) {

        ResponseHandler.error(error, mContext);

    }
}
