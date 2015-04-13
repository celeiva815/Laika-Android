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
import com.fourmob.datetimepicker.date.DatePickerDialog;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import cl.laikachile.laika.R;
import cl.laikachile.laika.network.RequestManager;
import cl.laikachile.laika.network.VolleyManager;
import cl.laikachile.laika.responses.RegisterResponse;
import cl.laikachile.laika.utils.Do;
import cl.laikachile.laika.utils.PrefsManager;
import cl.laikachile.laika.utils.Tag;

public class RegisterActivity extends ActionBarActivity implements DatePickerDialog.OnDateSetListener {

    private static final String TAG = RegisterActivity.class.getSimpleName();
    public static final String API_EMAIL = "email";
    public static final String API_PASSWORD = "password";
    public static final String API_PASSWORD_CONFIRMATION = "password_confirmation";
    public static final String API_FULL_NAME = "full_name";
    public static final String API_BIRTHDATE = "birth_date";

    // Ui views
    public EditText mFullNameEditText;
    public EditText mEmailEditText;
    public EditText mPasswordEditText;
    public Button mBirthDateButton;
    public Button mRegisterButton;
    public ProgressBar mRegisterProgressBar;
    public String mDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        getSupportActionBar().hide();

        if (PrefsManager.isUserLoggedIn(getApplicationContext())) {

            //FIXME ver qué información sincronizar al comienzo
            Do.changeActivity(this, MainActivity.class, this, Intent.FLAG_ACTIVITY_NEW_TASK);

        }

        final Calendar calendar = Calendar.getInstance();
        final DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(this,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), false);

        mFullNameEditText = (EditText) findViewById(R.id.name_register_edittext);
        mEmailEditText = (EditText) findViewById(R.id.email_register_edittext);
        mPasswordEditText = (EditText) findViewById(R.id.password_register_edittext);
        mBirthDateButton = (Button) findViewById(R.id.birthdate_register_button);
        mRegisterButton = (Button) findViewById(R.id.submit_register_button);
        mRegisterProgressBar = (ProgressBar) findViewById(R.id.register_progressbar);

        mDate = Do.getToStringDate(calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR));
        mBirthDateButton.setText(mDate);
        mBirthDateButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                datePickerDialog.setYearRange(calendar.get(Calendar.YEAR) - 100,
                        calendar.get(Calendar.YEAR));
                datePickerDialog.setCloseOnSingleTapDay(false);
                datePickerDialog.show(getSupportFragmentManager(), Tag.DATE_PICKER);

            }
        });
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

        mFullNameEditText.setEnabled(enable);
        mEmailEditText.setEnabled(enable);
        mPasswordEditText.setEnabled(enable);
        mBirthDateButton.setEnabled(enable);
        mRegisterButton.setEnabled(enable);

        if (enable) {
            mRegisterButton.setVisibility(View.VISIBLE);
            mRegisterProgressBar.setVisibility(View.GONE);

        } else {
            mRegisterButton.setVisibility(View.GONE);
            mRegisterProgressBar.setVisibility(View.VISIBLE);
        }

    }

    public void register(View view) {

        final String name = mFullNameEditText.getText().toString();
        final String email = mEmailEditText.getText().toString();
        final String password = mPasswordEditText.getText().toString();
        final String birth = mDate;

        if (TextUtils.isEmpty(name)) {
            mFullNameEditText.setError(getString(R.string.field_not_empty_error));
            return;
        }

        if (TextUtils.isEmpty(email)) {
            mEmailEditText.setError(getString(R.string.field_not_empty_error));
            return;
        }

        if (TextUtils.isEmpty(password)) {
            mPasswordEditText.setError(getString(R.string.field_not_empty_error));
            return;
        }

        mRegisterProgressBar.setVisibility(View.VISIBLE);
        enableViews(false);

        Map<String, String> params = new HashMap<String, String>(2);
        params.put(API_FULL_NAME, name);
        params.put(API_EMAIL, email);
        params.put(API_PASSWORD, password);
        params.put(API_PASSWORD_CONFIRMATION, password);

        JSONObject jsonParams = RequestManager.getParams(params);
        RegisterResponse response = new RegisterResponse(this);

        Request registerRequest = RequestManager.defaultRequest(jsonParams, RequestManager.ADDRESS_REGISTER,
                RequestManager.METHOD_POST, response, response,
                PrefsManager.getUserToken(getApplicationContext()));

        VolleyManager.getInstance(getApplicationContext())
                .addToRequestQueue(registerRequest, TAG);
    }

    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {

        mDate = Do.getToStringDate(day, month, year);
        mBirthDateButton.setText(mDate);

    }
}
