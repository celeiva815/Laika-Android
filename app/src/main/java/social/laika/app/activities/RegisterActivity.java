package social.laika.app.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;

import com.android.volley.Request;
import com.fourmob.datetimepicker.date.DatePickerDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import social.laika.app.R;
import social.laika.app.models.Owner;
import social.laika.app.network.RequestManager;
import social.laika.app.network.VolleyManager;
import social.laika.app.responses.RegisterResponse;
import social.laika.app.utils.Do;
import social.laika.app.utils.Tag;

public class RegisterActivity extends ActionBarActivity implements DatePickerDialog.OnDateSetListener {

    public int mIdLayout = R.layout.lk_register_activity;

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
    public EditText mPhoneEditText;
    public Button mBirthDateButton;
    public Button mRegisterButton;
    public ProgressBar mRegisterProgressBar;
    public int mGender;
    public String mDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mIdLayout);
        getSupportActionBar().hide();

        final Calendar calendar = Calendar.getInstance();
        final DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(this,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), false);

        mFullNameEditText = (EditText) findViewById(R.id.name_register_edittext);
        mEmailEditText = (EditText) findViewById(R.id.email_register_edittext);
        mPasswordEditText = (EditText) findViewById(R.id.password_register_edittext);
        mBirthDateButton = (Button) findViewById(R.id.birthdate_register_button);
        mPhoneEditText = (EditText) findViewById(R.id.phone_register_edittext);
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
        final String phone = mPhoneEditText.getText().toString();


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

        JSONObject jsonParams = new JSONObject();

        try {

            jsonParams.putOpt(Owner.COLUMN_FIRST_NAME, getFirstName(name));
            jsonParams.putOpt(Owner.COLUMN_LAST_NAME, getLastName(name));
            jsonParams.putOpt(Owner.COLUMN_SECOND_LAST_NAME, getSecondLastName(name));
            jsonParams.putOpt(Owner.COLUMN_BIRTH_DATE, mDate);
            jsonParams.putOpt(Owner.COLUMN_GENDER, mGender);
            jsonParams.putOpt(Owner.COLUMN_PHONE, phone);
            jsonParams.putOpt(API_EMAIL, email);
            jsonParams.putOpt(API_PASSWORD, password);
            jsonParams.putOpt(API_PASSWORD_CONFIRMATION, password);

            RegisterResponse response = new RegisterResponse(this);

            Request registerRequest = RequestManager.postRequest(jsonParams,
                    RequestManager.ADDRESS_REGISTER, response, response, null);

            VolleyManager.getInstance(getApplicationContext())
                    .addToRequestQueue(registerRequest, TAG);

        } catch (JSONException e) {
            e.printStackTrace();
            Do.showShortToast("Hubo un error en el registro", this);
        }
    }

    public void setHumanGenderRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        this.mGender = Tag.GENDER_FEMALE;

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.human_male_edit_user_radiobutton:
                if (checked)
                    mGender = Tag.GENDER_MALE;

                break;
            case R.id.human_female_edit_user_radiobutton:
                if (checked)
                    mGender = Tag.GENDER_FEMALE;

                break;
        }
    }

    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {

        mDate = Do.getToStringDate(day, month, year);
        mBirthDateButton.setText(mDate);

    }

    private String getFirstName(String fullName) {

        String[] names = fullName.split(" ");

        if (names.length >= 4) {

            return names[0] + " " + names[1];

        } else if (names.length > 1) {

            return names[0];

        } else {

            String firstName = fullName;

            if (fullName.contains(" ")) {
                firstName = fullName.substring(0, fullName.indexOf(" "));
            }

            return firstName;
        }
    }

    private String getLastName(String fullName) {

        String[] names = fullName.split(" ");

        if (names.length >= 4) {

            return names[2];

        } else if (names.length > 1) {

            return names[1];

        } else {

            return "";
        }
    }

    private String getSecondLastName(String fullName) {

        String[] names = fullName.split(" ");

        if (names.length >= 3) {

            return names[names.length - 1];

        } else {

            return "";
        }
    }
}
