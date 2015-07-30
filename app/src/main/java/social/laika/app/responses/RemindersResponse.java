package social.laika.app.responses;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import social.laika.app.fragments.VetVisitsFragment;
import social.laika.app.interfaces.Refreshable;
import social.laika.app.models.AlarmReminder;
import social.laika.app.models.Dog;
import social.laika.app.models.VetVisit;

/**
 * Created by Tito_Leiva on 07-05-15.
 */
public class RemindersResponse implements Response.Listener<JSONObject>, Response.ErrorListener {

    public Refreshable mFragment;
    public Dog mDog;
    public Context mContext;

    public RemindersResponse(Refreshable mFragment, Dog mDog, Context mContext) {
        this.mFragment = mFragment;
        this.mDog = mDog;
        this.mContext = mContext;
    }

    @Override
    public void onResponse(JSONObject response) {

        AlarmReminder.saveReminders(response, mDog.mDogId, mContext);
        mFragment.refreshList();

    }

    @Override
    public void onErrorResponse(VolleyError error) {

        ResponseHandler.error(error, mContext);

    }
}
