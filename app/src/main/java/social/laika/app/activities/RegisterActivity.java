package social.laika.app.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
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

import social.laika.app.R;
import social.laika.app.models.Owner;
import social.laika.app.network.Api;
import social.laika.app.network.VolleyManager;
import social.laika.app.responses.RegisterResponse;
import social.laika.app.utils.Do;
import social.laika.app.utils.Tag;

public class RegisterActivity extends ActionBarActivity implements DatePickerDialog.OnDateSetListener {
    private static final String TAG = RegisterActivity.class.getSimpleName();
    private static final int MIN_CHARACTERS_PASSWORD = 8;

    public static final String API_EMAIL = "email";
    public static final String API_PASSWORD = "password";
    public static final String API_PASSWORD_CONFIRMATION = "password_confirmation";
    public static final String API_FULL_NAME = "full_name";
    public static final String API_BIRTHDATE = "birth_date";

    // Ui views
    public EditText mFirstNameEditText;
    public EditText mLastNameEditText;
    public EditText mEmailEditText;
    public EditText mPasswordEditText;
    public EditText mRepeatPasswordEditText;
    public EditText mPhoneEditText;
    public Button mBirthDateButton;
    public Button mRegisterButton;
    public ProgressBar mRegisterProgressBar;
    public int mGender;
    public String mDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lk_register_activity);
        getSupportActionBar().hide();

        final Calendar calendar = Calendar.getInstance();
        final DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(this,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), false);

        mFirstNameEditText = (EditText) findViewById(R.id.name_register_edittext);
        mLastNameEditText = (EditText) findViewById(R.id.surname_register_edittext);
        mEmailEditText = (EditText) findViewById(R.id.email_register_edittext);
        mPasswordEditText = (EditText) findViewById(R.id.password_register_edittext);
        mRepeatPasswordEditText = (EditText) findViewById(R.id.repeat_password_register_edittext);
        mBirthDateButton = (Button) findViewById(R.id.birthdate_register_button);
        mPhoneEditText = (EditText) findViewById(R.id.phone_register_edittext);
        mRegisterButton = (Button) findViewById(R.id.submit_register_button);
        mRegisterProgressBar = (ProgressBar) findViewById(R.id.register_progressbar);

        mGender = Tag.GENDER_FEMALE;
        mDate = Do.getToStringDate(calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR));
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

    public void enableViews(boolean enable) {

        mFirstNameEditText.setEnabled(enable);
        mLastNameEditText.setEnabled(enable);
        mEmailEditText.setEnabled(enable);
        mPasswordEditText.setEnabled(enable);
        mRepeatPasswordEditText.setEnabled(enable);
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

        final String name = mFirstNameEditText.getText().toString();
        final String lastName = mLastNameEditText.getText().toString();
        final String email = mEmailEditText.getText().toString();
        final String password = mPasswordEditText.getText().toString();
        final String repeatPassword = mRepeatPasswordEditText.getText().toString();
        final String phone = mPhoneEditText.getText().toString();


        if (TextUtils.isEmpty(name)) {
            mFirstNameEditText.setError(getString(R.string.field_not_empty_error));
            return;
        }

        if (TextUtils.isEmpty(name)) {
            mLastNameEditText.setError(getString(R.string.field_not_empty_error));
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

        if (password.length() < 8) {
            mPasswordEditText.setError(getString(R.string.password_min_length));
            return;
        }

        if (TextUtils.isEmpty(repeatPassword)) {
            mPasswordEditText.setError(getString(R.string.field_not_empty_error));
            return;
        }

        if (!password.equals(repeatPassword)) {
            mPasswordEditText.setError(getString(R.string.equal_password_field));
            mRepeatPasswordEditText.setError(getString(R.string.equal_password_field));
            return;
        }

        mRegisterProgressBar.setVisibility(View.VISIBLE);
        enableViews(false);

        JSONObject jsonParams = new JSONObject();

        try {

            jsonParams.putOpt(Owner.COLUMN_FIRST_NAME, name);
            jsonParams.putOpt(Owner.COLUMN_LAST_NAME, lastName);
            jsonParams.putOpt(Owner.COLUMN_SECOND_LAST_NAME, getSecondLastName(lastName));
            jsonParams.putOpt(Owner.COLUMN_BIRTH_DATE, mDate);
            jsonParams.putOpt(Owner.COLUMN_GENDER, mGender);
            jsonParams.putOpt(Owner.COLUMN_PHONE, phone);
            jsonParams.putOpt(API_EMAIL, email);
            jsonParams.putOpt(API_PASSWORD, password);
            jsonParams.putOpt(API_PASSWORD_CONFIRMATION, repeatPassword);

            RegisterResponse response = new RegisterResponse(this);

            Request registerRequest = Api.postRequest(jsonParams, Api.ADDRESS_REGISTER, response, response, null);

            VolleyManager.getInstance(getApplicationContext()).addToRequestQueue(registerRequest, TAG);

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

    private String getSecondLastName(String lastName) {

        if (!lastName.contains(" ")) {
            return "";
        }

        String[] names = lastName.split(" ");
        if (names.length >= 2) {
            return names[names.length - 1];
        } else {
            return "";
        }
    }
}
