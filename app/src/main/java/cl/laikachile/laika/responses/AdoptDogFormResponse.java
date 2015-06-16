package cl.laikachile.laika.responses;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import cl.laikachile.laika.activities.AdoptDogFormActivity;
import cl.laikachile.laika.models.AdoptDogForm;

/**
 * Created by Tito_Leiva on 13-04-15.
 */
public class AdoptDogFormResponse implements Response.ErrorListener,
        Response.Listener<JSONObject> {

    public AdoptDogFormActivity mActivity;

    public AdoptDogFormResponse(AdoptDogFormActivity mActivity) {
        this.mActivity = mActivity;
    }

    @Override
    public void onResponse(JSONObject response) {

        AdoptDogForm adoptDogForm = AdoptDogForm.saveAdoptForm(response);

        if (adoptDogForm.hasId()) {
            mActivity.requestDogsForAdoption();

        } else {

            mActivity.mProgressDialog.dismiss();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {

        mActivity.mProgressDialog.dismiss();
        ResponseHandler.error(error, mActivity);

    }

}
