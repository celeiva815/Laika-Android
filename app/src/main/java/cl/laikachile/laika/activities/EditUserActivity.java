package cl.laikachile.laika.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.android.volley.Request;
import com.fourmob.datetimepicker.date.DatePickerDialog;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cl.laikachile.laika.R;
import cl.laikachile.laika.adapters.LocationsAdapter;
import cl.laikachile.laika.adapters.RegionAdapter;
import cl.laikachile.laika.listeners.ChangeRegionLocationsOnItemSelectedListener;
import cl.laikachile.laika.models.Location;
import cl.laikachile.laika.models.Owner;
import cl.laikachile.laika.models.Region;
import cl.laikachile.laika.network.RequestManager;
import cl.laikachile.laika.network.VolleyManager;
import cl.laikachile.laika.responses.EditUserResponse;
import cl.laikachile.laika.utils.Do;
import cl.laikachile.laika.utils.PrefsManager;
import cl.laikachile.laika.utils.Tag;

public class EditUserActivity extends ActionBarActivity implements DatePickerDialog.OnDateSetListener {

    public int mIdLayout = R.layout.lk_edit_owner_activity;

    private static final String TAG = EditUserActivity.class.getSimpleName();
    public static final String API_EMAIL = "email";
    public static final String API_PASSWORD = "password";
    public static final String API_PASSWORD_CONFIRMATION = "password_confirmation";
    public static final String API_FULL_NAME = "full_name";
    public static final String API_BIRTHDATE = "birth_date";

    public EditText mFullNameEditText;
    public RadioGroup mGenderRadioGroup;
    public EditText mPhoneEditText;
    public Button mBirthDateButton;
    public Spinner mRegionSpinner;
    public Spinner mCitySpinner;
    public Button mUpdateButton;
    public String mDate;
    public String mPhoneCountry;
    public Location mLocation;
    public ProgressDialog mProgressDialog;
    public Owner mOwner;
    public int mGender;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mIdLayout);
        mPhoneCountry = Do.getPhoneCountry(getApplicationContext());
        mOwner = PrefsManager.getLoggedOwner(getApplicationContext());
        mLocation = Location.getSingleLocation(mOwner.mLocationId);
        setActivityView();
        setValues();

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.color.laika_red));
        actionBar.setDisplayHomeAsUpEnabled(true);

    }

    @Override
    protected void onStart() {
        super.onStart();

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    public void setActivityView() {

        final Calendar calendar = Calendar.getInstance();
        final DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(this,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), false);

        mFullNameEditText = (EditText) findViewById(R.id.name_edit_user_edittext);
        mGenderRadioGroup = (RadioGroup) findViewById(R.id.gender_edit_user_radiogroup);
        mPhoneEditText = (EditText) findViewById(R.id.phone_edit_user_edittext);
        mBirthDateButton = (Button) findViewById(R.id.birthdate_edit_user_button);
        mRegionSpinner = (Spinner) findViewById(R.id.region_edit_user_spinner);
        mCitySpinner = (Spinner) findViewById(R.id.location_edit_user_spinner);
        mUpdateButton = (Button) findViewById(R.id.update_edit_user_button);

        RegionAdapter regionAdapter = new RegionAdapter(this.getApplicationContext(),
                R.layout.ai_simple_textview_for_adapter, R.id.simple_textview,
                getRegions(getApplicationContext()));

        mRegionSpinner.setAdapter(regionAdapter);
        mRegionSpinner.setOnItemSelectedListener(new ChangeRegionLocationsOnItemSelectedListener(mCitySpinner, mLocation));
        mCitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mLocation = (Location) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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

        mUpdateButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                requestUpdateUser();

            }
        });

    }

    private void setValues() {

        mFullNameEditText.setText(mOwner.getFullName());

        if (!Do.isNullOrEmpty(mOwner.mBirthDate)) {
            mBirthDateButton.setText(mOwner.mBirthDate);
        }

        mPhoneEditText.setText(mOwner.mPhone);

        if (mOwner.mGender == Tag.GENDER_MALE) {
            mGenderRadioGroup.check(R.id.human_male_edit_user_radiobutton);

        } else {
            mGenderRadioGroup.check(R.id.human_female_edit_user_radiobutton);

        }

        if (mLocation != null && mLocation.mLocationId > 1) {

            int regionPosition = ((RegionAdapter) mRegionSpinner.getAdapter()).
                    getPosition(mLocation.getRegion());
            LocationsAdapter locationAdapter = new LocationsAdapter(getApplicationContext(),
                    R.layout.ai_simple_textview_for_adapter, R.id.simple_textview,
                    Location.getLocationsByRegions(mLocation.mRegionId));

            mCitySpinner.setAdapter(locationAdapter);
            mRegionSpinner.setSelection(regionPosition);

        }
    }

    @Override
    protected void onPause() {
        VolleyManager.getInstance(getApplicationContext()).cancelPendingRequests(TAG);
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        if (!this.getClass().equals(MainActivity.class))
            getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {
            super.onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);

    }


    public void enableViews(boolean enable) {

        mFullNameEditText.setEnabled(enable);
        mBirthDateButton.setEnabled(enable);
        mUpdateButton.setEnabled(enable);


    }

    public void requestUpdateUser() {

        final String name = mFullNameEditText.getText().toString();

        if (TextUtils.isEmpty(name)) {
            mFullNameEditText.setError(getString(R.string.field_not_empty_error));
            return;
        }

        enableViews(false);
        mProgressDialog = ProgressDialog.show(EditUserActivity.this, "Espere un momento",
                "Estamos actualizando su perfil...");

        int ownerId = PrefsManager.getUserId(getApplicationContext());
        String ownerName = mFullNameEditText.getText().toString();
        String firstName = getFirstName(ownerName);
        String lastName = getLastName(ownerName);
        String secondLastName = getSecondLastName(ownerName);
        String rut = "";
        String birthDate = mBirthDateButton.getText().toString();
        int gender = mGender;
        String phone = mPhoneEditText.getText().toString();
        int locationId = mLocation.mLocationId;

        Owner owner = new Owner(ownerId, ownerName, firstName, lastName, secondLastName, rut, birthDate,
                gender, mOwner.mEmail, phone, locationId);

        JSONObject jsonParams = owner.getJsonObject();
        EditUserResponse response = new EditUserResponse(this);
        String token = PrefsManager.getUserToken(getApplicationContext());

        Request registerRequest = RequestManager.patchRequest(jsonParams,
                RequestManager.ADDRESS_USER, response, response, token);

        VolleyManager.getInstance(getApplicationContext())
                .addToRequestQueue(registerRequest, TAG);
    }

    private String getFirstName(String fullName) {

        String[] names = fullName.split(" ");

        if (names.length == 4) {

            return names[0] + names[1];

        } else if (names.length > 1) {

            return names[0];

        } else {

            String firstName = mFullNameEditText.getText().toString();

            if (fullName.contains(" ")) {
                firstName = fullName.substring(0, fullName.indexOf(" "));
            }

            return firstName;
        }
    }

    private String getLastName(String fullName) {

        String[] names = fullName.split(" ");

        if (names.length == 4) {

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

    public void setHumanGenderRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        this.mGender = mOwner.mGender;

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

    public List<Region> getRegions(Context context) {

        //TODO agregar el filtro por location del usuario
        int locationId = 1;
        Location location = Location.getSingleLocation(locationId);
        return Region.getRegions(location.mCountryId);
    }

    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {

        mDate = Do.getToStringDate(day, month, year);
        mBirthDateButton.setText(mDate);

    }
}
