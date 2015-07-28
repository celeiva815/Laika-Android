package social.laika.app.responses;

import android.app.ProgressDialog;
import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import social.laika.app.activities.EditUserActivity;
import social.laika.app.models.Owner;
import social.laika.app.utils.Do;
import social.laika.app.utils.PrefsManager;

/**
 * Created by Tito_Leiva on 13-04-15.
 */
public class CreateVetVisitResponse implements Response.ErrorListener,
        Response.Listener<JSONObject>  {

    public EditUserActivity mActivity;
    public Owner mOwner;
    public ProgressDialog mProgressDialog;

    public CreateVetVisitResponse(EditUserActivity mActivity) {

        this.mActivity = mActivity;
        this.mOwner = mActivity.mOwner;
        this.mProgressDialog = mActivity.mProgressDialog;
    }

    @Override
    public void onResponse(JSONObject response) {

        Context context = mActivity.getApplicationContext();
        Owner owner = new Owner(response);

        mOwner.update(owner);
        PrefsManager.editUser(context, mOwner);
        mProgressDialog.dismiss();
        Do.showLongToast("Su perfil ha sido actualizado", context);
    }

    @Override
    public void onErrorResponse(VolleyError error) {

        ResponseHandler.error(error, mActivity);
        mProgressDialog.dismiss();
        mActivity.enableViews(true);
    }

}
