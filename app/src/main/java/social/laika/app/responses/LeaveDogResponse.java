package social.laika.app.responses;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import social.laika.app.models.Dog;
import social.laika.app.models.Owner;
import social.laika.app.network.Api;

/**
 * Created by Tito_Leiva on 07-05-15.
 */
public class LeaveDogResponse implements Response.Listener<JSONObject>, Response.ErrorListener {

    private static final String API_SUCCESS = "success";

    public Context mContext;
    public Dog mDog;
    public Owner mOwner;
    public ProgressDialog mProgressDialog;

    public LeaveDogResponse(Context context, Dog dog, Owner owner, ProgressDialog progressDialog) {
        mContext = context;
        mDog = dog;
        mOwner = owner;
        mProgressDialog = progressDialog;
    }

    @Override
    public void onResponse(JSONObject response) {

        mProgressDialog.dismiss();

        if (response.optBoolean(API_SUCCESS, false)) {

            ((ActionBarActivity) mContext).finish(); //// FIXME: 12-10-15 revisar!!
            mOwner.deleteDogInformation(mContext, mDog);
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {

        Api.error(error, mContext);
        mProgressDialog.dismiss();
    }
}