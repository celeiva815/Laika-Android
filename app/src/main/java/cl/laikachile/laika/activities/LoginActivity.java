package cl.laikachile.laika.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cl.laikachile.laika.R;
import cl.laikachile.laika.network.RequestManager;
import cl.laikachile.laika.network.VolleyManager;
import cl.laikachile.laika.network.utils.ResponseHandler;
import cl.laikachile.laika.utils.Do;
import cl.laikachile.laika.utils.PrefsManager;

public class LoginActivity extends ActionBarActivity
                           implements Response.ErrorListener,
                                      Response.Listener<JSONObject> {

    private static final String TAG = LoginActivity.class.getSimpleName();
    public static final String API_EMAIL = "email";
    public static final String API_PASSWORD = "password";

    // Ui views
    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private Button mLoginButton;
    private ProgressBar mLoginProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        getSupportActionBar().hide();

        if (PrefsManager.isUserLoggedIn(getApplicationContext())) {

            //FIXME ver qué información sincronizar al comienzo
            Do.changeActivity(this, MainActivity.class, this, Intent.FLAG_ACTIVITY_NEW_TASK);

        }

        mEmailEditText = (EditText) findViewById(R.id.email_login_edittext);
        mPasswordEditText = (EditText) findViewById(R.id.password_login_edittext);
        mLoginButton = (Button) findViewById(R.id.login_button);
        mLoginProgressBar = (ProgressBar) findViewById(R.id.login_progressbar);

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


    private void enableViews(boolean enable) {
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

    public void login(View view) {

        final String email = mEmailEditText.getText().toString();
        final String password = mPasswordEditText.getText().toString();

        if (TextUtils.isEmpty(password)) {
            mPasswordEditText.setError(getString(R.string.field_not_empty_error));
            return;
        }

        mLoginProgressBar.setVisibility(View.VISIBLE);
        enableViews(false);

        Map<String, String> params = new HashMap<String, String>(2);
        params.put(API_EMAIL, email);
        params.put(API_PASSWORD, password);

        JSONObject jsonParams = RequestManager.getParams(params);

        Request loginRequest = RequestManager.defaultRequest(jsonParams, RequestManager.ADDRESS_LOGIN,
                RequestManager.METHOD_POST, this, this,
                PrefsManager.getUserToken(getApplicationContext()));

        VolleyManager.getInstance(getApplicationContext())
                .addToRequestQueue(loginRequest, TAG);
    }

    @Override
    public void onResponse(JSONObject response) {

        mLoginProgressBar.setVisibility(View.GONE);
        ResponseHandler.successLogin(this, response, getApplicationContext());
    }

    @Override
    public void onErrorResponse(VolleyError error) {

        ResponseHandler.error(error, getApplicationContext());
        mLoginProgressBar.setVisibility(View.GONE);
        enableViews(true);
    }
}
