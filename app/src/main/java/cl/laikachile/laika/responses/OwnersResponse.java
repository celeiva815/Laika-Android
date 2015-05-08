package cl.laikachile.laika.responses;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import cl.laikachile.laika.fragments.OwnersFragment;
import cl.laikachile.laika.models.Owner;
import cl.laikachile.laika.network.utils.ResponseHandler;

/**
 * Created by Tito_Leiva on 07-05-15.
 */
public class OwnersResponse implements Response.Listener<JSONObject>, Response.ErrorListener {

    public OwnersFragment mFragment;
    public Context mContext;

    public OwnersResponse(OwnersFragment mFragment) {
        this.mFragment = mFragment;
        this.mContext = mFragment.getActivity().getApplicationContext();
    }

    @Override
    public void onResponse(JSONObject response) {

        Owner.saveOwners(response, mContext, mFragment.mDog);
        mFragment.refreshList();

    }

    @Override
    public void onErrorResponse(VolleyError error) {

        ResponseHandler.error(error, mFragment.getActivity());

    }
}
