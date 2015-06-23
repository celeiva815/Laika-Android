package cl.laikachile.laika.responses;

import android.view.View;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import cl.laikachile.laika.activities.EditUserActivity;
import cl.laikachile.laika.activities.RegisterActivity;
import cl.laikachile.laika.models.Owner;
import cl.laikachile.laika.utils.Do;

/**
 * Created by Tito_Leiva on 13-04-15.
 */
public class EditUserResponse implements Response.ErrorListener,
        Response.Listener<JSONObject>  {

    public EditUserActivity mActivity;

    public EditUserResponse(EditUserActivity mActivity) {
        this.mActivity = mActivity;
    }

    @Override
    public void onResponse(JSONObject response) {

        Owner owner = new Owner(response);
        mActivity.mOwner.update(owner);
        mActivity.mProgressDialog.dismiss();
        Do.showLongToast("Su perfil ha sido actualizado", mActivity.getApplicationContext());
    }

    @Override
    public void onErrorResponse(VolleyError error) {

        ResponseHandler.error(error, mActivity);
        mActivity.mProgressDialog.dismiss();
        mActivity.enableViews(true);
    }

}
