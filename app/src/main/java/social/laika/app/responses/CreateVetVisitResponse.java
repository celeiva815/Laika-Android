package social.laika.app.responses;

import android.app.ProgressDialog;
import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import social.laika.app.activities.CreateVetVisitActivity;
import social.laika.app.models.Dog;
import social.laika.app.models.VetVisit;
import social.laika.app.network.Api;
import social.laika.app.utils.Do;

/**
 * Created by Tito_Leiva on 13-04-15.
 */
public class CreateVetVisitResponse implements Response.ErrorListener,
        Response.Listener<JSONObject>  {

    public CreateVetVisitActivity mActivity;
    public Dog mDog;
    public ProgressDialog mProgressDialog;

    public CreateVetVisitResponse(CreateVetVisitActivity mActivity) {

        this.mActivity = mActivity;
        this.mDog = mActivity.mDog;
        this.mProgressDialog = mActivity.mProgressDialog;
    }

    @Override
    public void onResponse(JSONObject response) {

        Context context = mActivity.getApplicationContext();
        VetVisit.saveVetVisit(response);
        mActivity.onBackPressed();

        mProgressDialog.dismiss();
        Do.showLongToast("La visita médica ha sido creada", context);
    }

    @Override
    public void onErrorResponse(VolleyError error) {

        Api.error(error, mActivity);
        mProgressDialog.dismiss();
        mActivity.enableViews(true);
    }

}
