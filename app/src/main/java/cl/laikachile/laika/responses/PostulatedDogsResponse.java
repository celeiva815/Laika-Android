package cl.laikachile.laika.responses;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import cl.laikachile.laika.activities.AdoptDogSuccessActivity;
import cl.laikachile.laika.activities.PostulatedDogsFragmentActivity;
import cl.laikachile.laika.models.Dog;
import cl.laikachile.laika.models.UserAdoptDog;
import cl.laikachile.laika.network.utils.ResponseHandler;
import cl.laikachile.laika.utils.Do;

/**
 * Created by Tito_Leiva on 07-05-15.
 */
public class PostulatedDogsResponse implements Response.Listener<JSONObject>, Response.ErrorListener {

    public Activity mActivity;
    public ProgressDialog mProgressDialog;
    public Context mContext;
    public Bitmap mDogBitmap;

    public PostulatedDogsResponse(Activity mActivity, ProgressDialog mProgressDialog) {

        this.mActivity = mActivity;
        this.mProgressDialog = mProgressDialog;
        this.mContext = mActivity.getApplicationContext();
    }

    @Override
    public void onResponse(JSONObject response) {

        UserAdoptDog.saveUserAdoptDogs(response);
        mProgressDialog.dismiss();
        changeActivity();
    }

    @Override
    public void onErrorResponse(VolleyError error) {

        ResponseHandler.error(error, mActivity.getApplicationContext());
        mProgressDialog.dismiss();

    }

    private void changeActivity() {

        Do.changeActivity(mContext, PostulatedDogsFragmentActivity.class,
                Intent.FLAG_ACTIVITY_NEW_TASK);

    }

}
