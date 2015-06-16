package cl.laikachile.laika.responses;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import cl.laikachile.laika.activities.LoginActivity;
import cl.laikachile.laika.activities.MainActivity;
import cl.laikachile.laika.models.Breed;
import cl.laikachile.laika.models.Country;
import cl.laikachile.laika.models.Dog;
import cl.laikachile.laika.models.Location;
import cl.laikachile.laika.models.Region;
import cl.laikachile.laika.network.RequestManager;
import cl.laikachile.laika.utils.Do;
import cl.laikachile.laika.utils.PrefsManager;
import cl.laikachile.laika.utils.Tag;

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

        if (response.has(Dog.TABLE_DOG) && response.has(Breed.TABLE_BREED)) {

            Dog.saveDogs(response, Tag.DOG_OWNED);
            Breed.saveBreeds(response);

            ResponseHandler.requestLocations(mContext, mActivity);

        } else {

            Country.saveCountries(response);
            Region.saveRegions(response);
            Location.saveCities(response);
            Location.setLocations(response);

            Do.changeActivity(mContext, MainActivity.class, mActivity, Intent.FLAG_ACTIVITY_NEW_TASK);
        }
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
