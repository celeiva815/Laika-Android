package social.laika.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import social.laika.app.R;
import social.laika.app.listeners.ToActivityOnCLickListener;
import social.laika.app.network.RequestManager;
import social.laika.app.network.VolleyManager;
import social.laika.app.responses.LoginResponse;
import social.laika.app.utils.Do;
import social.laika.app.utils.PrefsManager;

public class LoginActivity extends ActionBarActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();
    public static final String API_EMAIL = "email";
    public static final String API_PASSWORD = "password";

    // Ui views
    public EditText mEmailEditText;
    public EditText mPasswordEditText;
    public Button mLoginButton;
    public Button mRegisterButton;
    public ProgressBar mLoginProgressBar;

    // Facebook
    private LoginButton mFacebookLoginButton;
    private CallbackManager mCallbackManager = CallbackManager.Factory.create();
    private FacebookCallback<LoginResult> mFacebookLoginResult = new LaikaFBCallback();
    public static final List<String> FACEBOOK_PERMISSIONS =  Arrays.asList("public_profile", "email", "user_friends");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "OnCreate()::LoginActivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lk_login_activity);
        getSupportActionBar().hide();

        if (true || PrefsManager.isUserLoggedIn(getApplicationContext())) {

            //FIXME ver qué información sincronizar al comienzo
            Do.changeActivity(this, MainActivity.class, this, Intent.FLAG_ACTIVITY_NEW_TASK);

        }

        mEmailEditText = (EditText) findViewById(R.id.email_login_edittext);
        mPasswordEditText = (EditText) findViewById(R.id.password_login_edittext);
        mLoginButton = (Button) findViewById(R.id.login_button);
        mRegisterButton = (Button) findViewById(R.id.register_login_button);
        mLoginProgressBar = (ProgressBar) findViewById(R.id.login_progressbar);

        mRegisterButton.setOnClickListener(new ToActivityOnCLickListener(RegisterActivity.class));

        /* Facebook Login */
//        mFacebookLoginButton = (LoginButton) findViewById(R.id.facebook_login_button);
//        Log.d(TAG, FACEBOOK_PERMISSIONS.toString());
//        mFacebookLoginButton.setReadPermissions(FACEBOOK_PERMISSIONS);
//        mFacebookLoginButton.registerCallback(mCallbackManager, mFacebookLoginResult);
//        Log.d(TAG, "Facebook Login onCreate()");

    }

    @Override
    protected void onPause() {
        VolleyManager.getInstance(getApplicationContext()).cancelPendingRequests(TAG);
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }


    public void enableViews(boolean enable) {

        mEmailEditText.setEnabled(enable);
        mPasswordEditText.setEnabled(enable);
        mLoginButton.setEnabled(enable);

        if (enable) {
            mLoginButton.setVisibility(View.VISIBLE);
            mLoginProgressBar.setVisibility(View.GONE);

        } else {
            mLoginButton.setVisibility(View.GONE);
            mLoginProgressBar.setVisibility(View.VISIBLE);
        }

    }

    public void requestLogIn(View view) {

        final String email = mEmailEditText.getText().toString();
        final String password = mPasswordEditText.getText().toString();

        if (TextUtils.isEmpty(password)) {
            mPasswordEditText.setError(getString(R.string.field_not_empty_error));
            return;
        }

        mLoginProgressBar.setVisibility(View.VISIBLE);
        enableViews(false);

        Map<String, String> params = new HashMap<>(2);
        params.put(API_EMAIL, email);
        params.put(API_PASSWORD, password);

        JSONObject jsonParams = RequestManager.getJsonParams(params);
        LoginResponse response = new LoginResponse(this);

        Request loginRequest = RequestManager.postRequest(jsonParams, RequestManager.ADDRESS_LOGIN,
                response, response, PrefsManager.getUserToken(getApplicationContext()));

        VolleyManager.getInstance(getApplicationContext())
                .addToRequestQueue(loginRequest, TAG);
    }

    private class LaikaFBCallback implements FacebookCallback<LoginResult> {

        @Override
        public void onSuccess(LoginResult loginResult) {
            Log.i(TAG, loginResult.getAccessToken().getToken());
            com.facebook.Profile profile = Profile.getCurrentProfile();
            String first_name = profile.getFirstName();
            String last_name = profile.getLastName();
            Log.i(TAG, first_name);
            Log.i(TAG, last_name);

        }

        @Override
        public void onCancel() { Log.i("FacebookToken", "Cancelled"); }

        @Override
        public void onError(FacebookException e) {
            Log.i("FacebookToken",e.getMessage());
        }
    }
}
