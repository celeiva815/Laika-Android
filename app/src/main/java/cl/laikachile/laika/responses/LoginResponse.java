package cl.laikachile.laika.responses;

import android.content.Context;
import android.view.View;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import cl.laikachile.laika.activities.LoginActivity;
import cl.laikachile.laika.network.utils.ResponseHandler;

/**
 * Created by Tito_Leiva on 13-04-15.
 */
public class LoginResponse implements Response.ErrorListener,
        Response.Listener<JSONObject>  {

    public LoginActivity mActivity;

    public LoginResponse(LoginActivity mActivity) {
        this.mActivity = mActivity;
    }

    @Override
    public void onResponse(JSONObject response) {

        mActivity.mLoginProgressBar.setVisibility(View.GONE);
        ResponseHandler.successLogin(mActivity, response);
    }

    @Override
    public void onErrorResponse(VolleyError error) {

        ResponseHandler.error(error, mActivity);
        mActivity.mLoginProgressBar.setVisibility(View.GONE);
        mActivity.enableViews(true);
    }

}
