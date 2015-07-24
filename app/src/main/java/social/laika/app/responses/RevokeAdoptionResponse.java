package social.laika.app.responses;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import social.laika.app.activities.PostulatedDogsFragmentActivity;
import social.laika.app.models.Dog;
import social.laika.app.models.UserAdoptDog;
import social.laika.app.utils.Do;

/**
 * Created by Tito_Leiva on 07-05-15.
 */
public class RevokeAdoptionResponse implements Response.Listener<JSONObject>, Response.ErrorListener {

    public Activity mActivity;
    public ProgressDialog mProgressDialog;
    public Dog mDog;
    public UserAdoptDog mUserAdoptDog;
    public Context mContext;

    public RevokeAdoptionResponse(Activity mActivity, ProgressDialog mProgressDialog, Dog mDog,
                                  UserAdoptDog mUserAdoptDog) {
        this.mActivity = mActivity;
        this.mProgressDialog = mProgressDialog;
        this.mDog = mDog;
        this.mContext = mActivity.getApplicationContext();
        this.mUserAdoptDog = mUserAdoptDog;
    }

    @Override
    public void onResponse(JSONObject response) {

        UserAdoptDog.deleteUserAdoptDog(mUserAdoptDog);
        Dog.deleteDog(mDog);
        mProgressDialog.dismiss();
        ((PostulatedDogsFragmentActivity) mActivity).updateDogs();
        Do.showLongToast("La postulación ha sido cancelada", mContext);
    }

    @Override
    public void onErrorResponse(VolleyError error) {

        ResponseHandler.error(error, mActivity.getApplicationContext());
        mProgressDialog.dismiss();

    }

}
