package social.laika.app.responses;

import android.content.Context;
import android.content.Intent;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import social.laika.app.activities.AdoptDogFormActivity;
import social.laika.app.activities.AdoptDogUserFormActivity;
import social.laika.app.models.AdoptDogForm;
import social.laika.app.models.Owner;
import social.laika.app.utils.Do;

/**
 * Created by Tito_Leiva on 13-04-15.
 */
public class AdoptDogUserFormResponse implements Response.ErrorListener,
        Response.Listener<JSONObject> {

    public static final int NEXT_ADOPT_DOG = 1;
    public static final int NEXT_USER_PROFILE = 2;


    public AdoptDogUserFormActivity mActivity;
    public int mNext;


    public AdoptDogUserFormResponse(AdoptDogUserFormActivity mActivity, int mNext) {
        this.mActivity = mActivity;
        this.mNext = mNext;
    }

    @Override
    public void onResponse(JSONObject response) {

        Context context = mActivity.getApplicationContext();
        Owner owner = new Owner(response.optJSONObject(Owner.API_USER));
        Owner.createOrUpdate(owner);

        JSONObject jsonForm = response.optJSONObject(AdoptDogForm.TABLE_ADOPT_DOG_FORM);
        AdoptDogForm adoptDogForm = AdoptDogForm.saveAdoptForm(jsonForm);

        if (adoptDogForm.hasId()) {

            Do.showLongToast("¡Enhorabuena! Tu información de adopción fue actualizada", context);

            switch (mNext) {

                case NEXT_ADOPT_DOG:

                    Do.changeActivity(context, AdoptDogFormActivity.class, mActivity,
                            Intent.FLAG_ACTIVITY_NEW_TASK);
                    break;
                case NEXT_USER_PROFILE:

                    mActivity.onBackPressed();

                    break;

                default:
                    mActivity.onBackPressed();
                    break;

            }
        }

            mActivity.mProgressDialog.dismiss();

    }

    @Override
    public void onErrorResponse(VolleyError error) {

        mActivity.mProgressDialog.dismiss();
        ResponseHandler.error(error, mActivity);

    }

}
