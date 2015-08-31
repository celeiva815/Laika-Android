package social.laika.app.responses;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import social.laika.app.activities.CreateDogActivity;
import social.laika.app.models.Dog;
import social.laika.app.models.Owner;
import social.laika.app.network.RequestManager;
import social.laika.app.utils.Do;
import social.laika.app.utils.PrefsManager;
import social.laika.app.utils.Tag;

/**
 * Created by Tito_Leiva on 13-04-15.
 */
public class CreateDogResponse implements Response.ErrorListener,
        Response.Listener<JSONObject> {

    public CreateDogActivity mActivity;
    public String mMessage;

    public CreateDogResponse(CreateDogActivity mActivity, String mMessage) {
        this.mActivity = mActivity;
        this.mMessage = mMessage;
    }

    @Override
    public void onResponse(JSONObject response) {

        Context context = mActivity.getApplicationContext();
        mActivity.mDog = Dog.saveDog(response, Tag.DOG_OWNED);
        Owner owner = PrefsManager.getLoggedOwner(context);
        owner.createOwnerDog(context, mActivity.mDog);
        Do.showShortToast(mMessage + mActivity.mDog.mName, context);
        mActivity.onSuccess();
    }

    @Override
    public void onErrorResponse(VolleyError error) {

        mActivity.enableViews(true);
        RequestManager.error(error, mActivity);
        mActivity.onFailure();
    }
}
