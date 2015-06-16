package cl.laikachile.laika.responses;

import android.content.Context;
import android.content.Intent;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import cl.laikachile.laika.activities.AdoptDogFormActivity;
import cl.laikachile.laika.activities.AdoptDogsFragmentActivity;
import cl.laikachile.laika.models.Dog;
import cl.laikachile.laika.utils.Do;
import cl.laikachile.laika.utils.Tag;

/**
 * Created by Tito_Leiva on 07-05-15.
 */
public class DogForAdoptionResponse implements Response.Listener<JSONObject>, Response.ErrorListener {

    public AdoptDogFormActivity mActivity;
    public Context mContext;

    public DogForAdoptionResponse(AdoptDogFormActivity mActivity) {
        this.mActivity = mActivity;
        this.mContext = mActivity.getApplicationContext();
    }

    @Override
    public void onResponse(JSONObject response) {

        Dog.saveDogs(response, Tag.DOG_FOUNDATION);
        mActivity.mProgressDialog.dismiss();
        Do.changeActivity(mContext, AdoptDogsFragmentActivity.class, Intent.FLAG_ACTIVITY_NEW_TASK);


    }

    @Override
    public void onErrorResponse(VolleyError error) {

        ResponseHandler.error(error, mContext);
        mActivity.mProgressDialog.dismiss();

    }
}
