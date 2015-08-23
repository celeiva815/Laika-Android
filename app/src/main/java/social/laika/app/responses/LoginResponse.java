package social.laika.app.responses;

import android.app.Activity;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.facebook.login.LoginManager;

import org.json.JSONObject;

import social.laika.app.activities.LoginActivity;
import social.laika.app.fragments.TutorialFragment;

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

        ResponseHandler.successLogin(mActivity, response, mLoginProgressBar);

    }

    @Override
    public void onErrorResponse(VolleyError error) {

        ResponseHandler.error(error, mActivity);
        mLoginProgressBar.setVisibility(View.GONE);

        if (LoginManager.getInstance() != null) {
            LoginManager.getInstance().logOut();
        }

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
