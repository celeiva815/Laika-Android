package social.laika.app.responses;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import social.laika.app.activities.AdoptDogSuccessActivity;
import social.laika.app.models.Dog;
import social.laika.app.models.Owner;
import social.laika.app.models.UserAdoptDog;
import social.laika.app.network.Api;
import social.laika.app.utils.Do;

/**
 * Created by Tito_Leiva on 07-05-15.
 */
public class TransferOwnershipResponse implements Response.Listener<JSONObject>, Response.ErrorListener {

    private static final String API_SUCCESS = "success";

    public Context mContext;
    public Dog mDog;
    public Owner mOwner;
    public ProgressDialog mProgressDialog;

    public TransferOwnershipResponse(Context context, Dog dog, Owner owner, ProgressDialog progressDialog) {
        mContext = context;
        mDog = dog;
        mOwner = owner;
        mProgressDialog = progressDialog;
    }

    @Override
    public void onResponse(JSONObject response) {

        if (response.optBoolean(API_SUCCESS, false)) {

            mDog.mOwnerId = mOwner.mOwnerId;
            mDog.save();
        }

        mProgressDialog.dismiss();
    }

    @Override
    public void onErrorResponse(VolleyError error) {

        Api.error(error, mContext);
        mProgressDialog.dismiss();
    }
}
