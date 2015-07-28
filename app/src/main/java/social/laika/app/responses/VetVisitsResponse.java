package social.laika.app.responses;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import social.laika.app.fragments.OwnersFragment;
import social.laika.app.fragments.VetVisitsFragment;
import social.laika.app.models.Owner;
import social.laika.app.models.VetVisit;

/**
 * Created by Tito_Leiva on 07-05-15.
 */
public class VetVisitsResponse implements Response.Listener<JSONObject>, Response.ErrorListener {

    public VetVisitsFragment mFragment;
    public Context mContext;

    public VetVisitsResponse(VetVisitsFragment mFragment) {
        this.mFragment = mFragment;
        this.mContext = mFragment.getActivity().getApplicationContext();
    }

    @Override
    public void onResponse(JSONObject response) {

        VetVisit.saveVetVisits(response);
        mFragment.refreshVetVisits();

    }

    @Override
    public void onErrorResponse(VolleyError error) {

        ResponseHandler.error(error, mFragment.getActivity());

    }
}
