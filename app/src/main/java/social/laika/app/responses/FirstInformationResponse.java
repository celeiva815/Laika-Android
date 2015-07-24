package social.laika.app.responses;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import social.laika.app.activities.LoginActivity;
import social.laika.app.activities.MainActivity;
import social.laika.app.models.Breed;
import social.laika.app.models.Country;
import social.laika.app.models.Dog;
import social.laika.app.models.City;
import social.laika.app.models.Region;
import social.laika.app.utils.Do;
import social.laika.app.utils.PrefsManager;
import social.laika.app.utils.Tag;

/**
 * Created by Tito_Leiva on 07-05-15.
 */
public class FirstInformationResponse implements Response.Listener<JSONObject>, Response.ErrorListener {

    public Activity mActivity;
    public Context mContext;

    public FirstInformationResponse(Activity mActivity) {
        this.mActivity = mActivity;
        this.mContext = mActivity.getApplicationContext();
    }

    @Override
    public void onResponse(JSONObject response) {

        Dog.saveDogs(response, Tag.DOG_OWNED);
        Breed.saveBreeds(response);
        Country.saveCountries(response);
        Region.saveRegions(response);
        City.saveCities(response);

        Do.changeActivity(mContext, MainActivity.class, mActivity, Intent.FLAG_ACTIVITY_NEW_TASK);

    }

    @Override
    public void onErrorResponse(VolleyError error) {

        ResponseHandler.error(error, mContext);
        //Do.changeActivity(mContext, MainActivity.class, mActivity, Intent.FLAG_ACTIVITY_NEW_TASK);

        if (mActivity instanceof LoginActivity) {
            ((LoginActivity) mActivity).enableViews(true);
            PrefsManager.clearPrefs(mContext);
        }

    }
}
