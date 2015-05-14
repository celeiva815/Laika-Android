package cl.laikachile.laika.responses;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import cl.laikachile.laika.activities.AdoptDogFormActivity;
import cl.laikachile.laika.activities.AdoptDogFragmentActivity;
import cl.laikachile.laika.activities.AdoptDogSuccessActivity;
import cl.laikachile.laika.models.Dog;
import cl.laikachile.laika.network.utils.ResponseHandler;
import cl.laikachile.laika.utils.Do;
import cl.laikachile.laika.utils.Tag;

/**
 * Created by Tito_Leiva on 07-05-15.
 */
public class ConfirmAdoptionResponse implements Response.Listener<JSONObject>, Response.ErrorListener {

    public Activity mActivity;
    public ProgressDialog mProgressDialog;
    public Dog mDog;
    public Context mContext;

    public ConfirmAdoptionResponse(Activity mActivity, ProgressDialog mProgressDialog, Dog mDog) {
        this.mActivity = mActivity;
        this.mProgressDialog = mProgressDialog;
        this.mDog = mDog;
        this.mContext = mActivity.getApplicationContext();
    }

    @Override
    public void onResponse(JSONObject response) {

        mProgressDialog.dismiss();

        try {

            int adoptionId = response.getInt(Dog.COLUMN_USER_ADOPT_DOG_ID);

            setPostulatedDog(adoptionId);
            changeActivity();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {

        ResponseHandler.error(error, mActivity.getApplicationContext());
        mProgressDialog.dismiss();

    }

    private void setPostulatedDog(int adoptionId) {

        mDog.mStatus = Tag.DOG_POSTULATED;
        mDog.mUserAdoptDogId = adoptionId;
        mDog.save();

    }

    private void changeActivity() {

        Intent intent = new Intent(mContext, AdoptDogSuccessActivity.class);
        Bundle b = new Bundle();

        b.putInt("DogId", mDog.mDogId); //Your id
        intent.putExtras(b); //Put your id to your next Intent
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
        mActivity.finish();

    }
}
