package social.laika.app.responses;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import social.laika.app.interfaces.Refreshable;
import social.laika.app.models.Dog;
import social.laika.app.models.Owner;
import social.laika.app.network.RequestManager;

/**
 * Created by Tito_Leiva on 07-05-15.
 */
public class OwnersResponse implements Response.Listener<JSONObject>, Response.ErrorListener {

    public Refreshable mFragment;
    public Dog mDog;
    public Context mContext;

    public OwnersResponse(Refreshable mFragment, Dog mDog, Context mContext) {
        this.mFragment = mFragment;
        this.mDog = mDog;
        this.mContext = mContext;
    }

    @Override
    public void onResponse(JSONObject response) {

        Owner.saveOwners(response, mContext, mDog);
        mFragment.refresh();

    }

    @Override
    public void onErrorResponse(VolleyError error) {

        RequestManager.error(error, mContext);

    }
}
