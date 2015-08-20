package social.laika.app.responses;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import social.laika.app.activities.PostulatedDogsFragmentActivity;
import social.laika.app.interfaces.Requestable;
import social.laika.app.models.UserAdoptDog;
import social.laika.app.utils.Do;

/**
 * Created by Tito_Leiva on 07-05-15.
 */
public class PostulatedDogsResponse implements Response.Listener<JSONObject>, Response.ErrorListener {

    public Activity mActivity;
    public ProgressDialog mProgressDialog;
    public Context mContext;
    public Bitmap mDogBitmap;
    public Requestable mRequest;

    public PostulatedDogsResponse(Activity mActivity, ProgressDialog mProgressDialog) {

        this.mActivity = mActivity;
        this.mProgressDialog = mProgressDialog;
        this.mContext = mActivity.getApplicationContext();
    }

    public PostulatedDogsResponse(Requestable request) {

        this.mRequest = request;
    }

    @Override
    public void onResponse(JSONObject response) {

        UserAdoptDog.saveUserAdoptDogs(response);

        if (mRequest != null) {
            mRequest.onSuccess();
        }

        if (mProgressDialog != null) {
            mProgressDialog.dismiss();

        }

        if (mActivity != null) {
            Do.changeActivity(mContext, PostulatedDogsFragmentActivity.class,
                    Intent.FLAG_ACTIVITY_NEW_TASK);
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {

        mRequest.onFailure();

    }

}
