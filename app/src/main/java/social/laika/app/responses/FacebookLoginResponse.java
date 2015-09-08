package social.laika.app.responses;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import social.laika.app.activities.LoginActivity;
import social.laika.app.fragments.TutorialFragment;
import social.laika.app.network.Api;

/**
 * Created by cnmartinez on 8/4/15.
 */
public class FacebookLoginResponse implements Response.ErrorListener,
        Response.Listener<JSONObject>  {

    public Activity mActivity;
    public TutorialFragment mFragment;
    public ProgressBar mLoginProgressBar;

    public FacebookLoginResponse(TutorialFragment fragment) {
        this.mFragment = fragment;
        this.mActivity = mFragment.getActivity();
        this.mLoginProgressBar = mFragment.mLoginProgressBar;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.d("Tutorial", "Error :c " + error.getMessage());

        Api.error(error, mActivity);
        mLoginProgressBar.setVisibility(View.GONE);

        enableViews();
    }

    @Override
    public void onResponse(JSONObject response) {
        Log.d("Tutorial", "Success :D " + response.toString());
        LoginHandler.successLogin(mActivity, response, mLoginProgressBar);
    }

    private void enableViews() {

        if (mFragment != null) {
            mFragment.enableViews(true);

        } else {
            ((LoginActivity) mActivity).enableViews(true);
        }
    }
}
