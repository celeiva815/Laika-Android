package social.laika.app.responses;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import social.laika.app.interfaces.Refreshable;
import social.laika.app.models.VetVisit;
import social.laika.app.network.RequestManager;

/**
 * Created by Tito_Leiva on 07-05-15.
 */
public class VetVisitsResponse implements Response.Listener<JSONObject>, Response.ErrorListener {

    public Refreshable mFragment;
    public Context mContext;

    public VetVisitsResponse(Refreshable mFragment, Context context) {
        this.mFragment = mFragment;
        this.mContext = context;
    }

    @Override
    public void onResponse(JSONObject response) {

        VetVisit.saveVetVisits(response);
        mFragment.refresh();

    }

    @Override
    public void onErrorResponse(VolleyError error) {

        RequestManager.error(error, mContext);

    }
}
