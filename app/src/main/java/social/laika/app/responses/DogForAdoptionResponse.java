package social.laika.app.responses;

import android.content.Context;
import android.content.Intent;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import social.laika.app.activities.AdoptDogFormActivity;
import social.laika.app.activities.AdoptDogsFragmentActivity;
import social.laika.app.models.Dog;
import social.laika.app.utils.Do;
import social.laika.app.utils.Tag;

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

        Dog.deleteDogs(Tag.DOG_FOUNDATION);
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
