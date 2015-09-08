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
import social.laika.app.models.UserAdoptDog;
import social.laika.app.network.Api;
import social.laika.app.utils.Do;

/**
 * Created by Tito_Leiva on 07-05-15.
 */
public class ConfirmAdoptionResponse implements Response.Listener<JSONObject>, Response.ErrorListener {

    public Activity mActivity;
    public ProgressDialog mProgressDialog;
    public Dog mDog;
    public Context mContext;
    public Bitmap mDogBitmap;

    public ConfirmAdoptionResponse(Activity mActivity, ProgressDialog mProgressDialog, Dog mDog,
                                   Bitmap mDogBitmap) {
        this.mActivity = mActivity;
        this.mProgressDialog = mProgressDialog;
        this.mDog = mDog;
        this.mContext = mActivity.getApplicationContext();
        this.mDogBitmap = mDogBitmap;
    }

    @Override
    public void onResponse(JSONObject response) {

        UserAdoptDog userAdoptDog = UserAdoptDog.createOrUpdate(new UserAdoptDog(response));
        mProgressDialog.dismiss();
        mDog.setPostulatedDog();
        changeActivity(userAdoptDog);
    }

    @Override
    public void onErrorResponse(VolleyError error) {

        if (error.networkResponse.statusCode == 406) {

            String message = Do.isNullOrEmpty(error.getMessage()) ?
                    "¡Lo sentimos! No puedes postular a más de 3 perritos simultáneamente." :
                    error.getMessage();

            Do.showLongToast(message, mContext);

        } else {
            Api.error(error, mActivity.getApplicationContext());
        }

        mProgressDialog.dismiss();
    }

    private void changeActivity(UserAdoptDog userAdoptDog) {

        Intent intent = new Intent(mContext, AdoptDogSuccessActivity.class);
        Bundle b = new Bundle();

        b.putInt(Dog.COLUMN_DOG_ID, mDog.mDogId);
        b.putInt(UserAdoptDog.COLUMN_USER_ADOPT_DOG_ID, userAdoptDog.mUserAdoptDogId);
        intent.putExtras(b); //Put your id to your next Intent
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);

    }

}
