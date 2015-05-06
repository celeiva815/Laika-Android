package cl.laikachile.laika.responses;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import cl.laikachile.laika.activities.AdoptDogFormActivity;
import cl.laikachile.laika.activities.EventsActivity;
import cl.laikachile.laika.models.Dog;
import cl.laikachile.laika.models.Event;
import cl.laikachile.laika.network.utils.ResponseHandler;
import cl.laikachile.laika.utils.Do;

/**
 * Created by Tito_Leiva on 13-04-15.
 */
public class AdoptDogFormResponse implements Response.ErrorListener,
        Response.Listener<JSONObject>  {

    public AdoptDogFormActivity mActivity;

    public AdoptDogFormResponse(AdoptDogFormActivity mActivity) {
        this.mActivity = mActivity;
    }

    @Override
    public void onResponse(JSONObject response) {

        mActivity.mProgressDialog.dismiss();
        Dog.saveDog(response, Dog.STATUS_PUBLISH);

    }

    @Override
    public void onErrorResponse(VolleyError error) {

        mActivity.mProgressDialog.dismiss();
        ResponseHandler.error(error, mActivity);

        Do.showToast("Hubo un error", mActivity);

    }

}
