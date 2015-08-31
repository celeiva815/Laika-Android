package social.laika.app.responses;

import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import social.laika.app.activities.RegisterActivity;
import social.laika.app.network.RequestManager;

/**
 * Created by Tito_Leiva on 13-04-15.
 */
public class RegisterResponse implements Response.ErrorListener,
        Response.Listener<JSONObject>  {

    public RegisterActivity mActivity;
    public ProgressBar mProgressBar;

    public RegisterResponse(RegisterActivity mActivity) {

        this.mActivity = mActivity;
        this.mProgressBar = mActivity.mRegisterProgressBar;
    }

    @Override
    public void onResponse(JSONObject response) {

        LoginHandler.successLogin(mActivity, response, mProgressBar);
    }

    @Override
    public void onErrorResponse(VolleyError error) {

        RequestManager.error(error, mActivity);
        mProgressBar.setVisibility(View.GONE);
        mActivity.enableViews(true);
    }

}
