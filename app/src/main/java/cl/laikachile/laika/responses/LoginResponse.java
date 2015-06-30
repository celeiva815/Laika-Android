package cl.laikachile.laika.responses;

import android.app.Activity;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import cl.laikachile.laika.activities.LoginActivity;
import cl.laikachile.laika.fragments.TutorialFragment;

/**
 * Created by Tito_Leiva on 13-04-15.
 */
public class LoginResponse implements Response.ErrorListener,
        Response.Listener<JSONObject>  {

    public Activity mActivity;
    public TutorialFragment mFragment;
    public ProgressBar mLoginProgressBar;

    public LoginResponse(TutorialFragment mFragment) {
        this.mFragment = mFragment;
        this.mActivity = mFragment.getActivity();
        this.mLoginProgressBar = mFragment.mLoginProgressBar;
    }

    public LoginResponse(LoginActivity mActivity) {
        this.mActivity = mActivity;
        this.mLoginProgressBar = mActivity.mLoginProgressBar;
    }

    @Override
    public void onResponse(JSONObject response) {

        mLoginProgressBar.setVisibility(View.GONE);
        ResponseHandler.successLogin(mActivity, response);
        enableViews();
    }

    @Override
    public void onErrorResponse(VolleyError error) {

        ResponseHandler.error(error, mActivity);
        mLoginProgressBar.setVisibility(View.GONE);

        enableViews();

    }

    private void enableViews() {

        if (mFragment != null) {
            mFragment.enableViews(true);

        } else {
            ((LoginActivity) mActivity).enableViews(true);
        }
    }

}
