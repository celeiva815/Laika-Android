package social.laika.app.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.activeandroid.query.Select;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.soundcloud.android.crop.Crop;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import social.laika.app.R;
import social.laika.app.adapters.CitiesAdapter;
import social.laika.app.adapters.RegionAdapter;
import social.laika.app.interfaces.Photographable;
import social.laika.app.listeners.ChangeRegionLocationsOnItemSelectedListener;
import social.laika.app.listeners.PhotographerListener;
import social.laika.app.models.City;
import social.laika.app.models.Country;
import social.laika.app.models.Owner;
import social.laika.app.models.Photo;
import social.laika.app.models.Region;
import social.laika.app.network.Api;
import social.laika.app.network.VolleyManager;
import social.laika.app.utils.Do;
import social.laika.app.utils.Flurry;
import social.laika.app.utils.Photographer;
import social.laika.app.utils.PrefsManager;
import social.laika.app.utils.Tag;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class EditUserActivity extends ActionBarActivity
        implements DatePickerDialog.OnDateSetListener, Photographable, Response.ErrorListener,
        Response.Listener<JSONObject> {

    public int mIdLayout = R.layout.lk_edit_owner_activity;

    private static final String TAG = EditUserActivity.class.getSimpleName();
    public static final String API_EMAIL = "email";
    public static final String API_PASSWORD = "password";
    public static final String API_PASSWORD_CONFIRMATION = "password_confirmation";
    public static final String API_FULL_NAME = "full_name";
    public static final String API_BIRTHDATE = "birth_date";

    public EditText mFirstNameEditText;
    public EditText mLastNameEditText;
    public RadioGroup mGenderRadioGroup;
    public EditText mPhoneEditText;
    public Button mBirthDateButton;
    public Spinner mRegionSpinner;
    public Spinner mCitySpinner;
    public Button mUpdateButton;
    public String mDate;
    public String mPhoneCountry;
    public City mCity;
    public ProgressDialog mProgressDialog;
    public Owner mOwner;
    public int mGender;
    public boolean mCanAdopt;
    public ImageView mProfileImageView;
    public Photographer mPhotographer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mIdLayout);
        mCanAdopt = Country.existIso(Do.getCountryIso(getApplicationContext()));
        mPhoneCountry = Do.getCountryIso(getApplicationContext());
        mOwner = PrefsManager.getLoggedOwner(getApplicationContext());
        mCity = City.getSingleLocation(mOwner.mCityId);

        if (mCity == null) {
            mCity = new Select().from(City.class).executeSingle();

            Map<String, String> map = new HashMap<>();

            map.put(Owner.COLUMN_OWNER_ID, Integer.toString(mOwner.mOwnerId));
            Flurry.logEvent(Flurry.EDIT_USER_ERROR, map);

            PrefsManager.setNeedSync(getApplicationContext(), true);
        }

        setActivityView();
        setValues();

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.color.laika_red));
        actionBar.setDisplayHomeAsUpEnabled(true);

    }

    @Override
    protected void onStart() {
        super.onStart();

        Do.hideKeyboard(this);
    }

    public void setActivityView() {

        final Calendar calendar = Calendar.getInstance();
        final DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(this,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), false);

        mFirstNameEditText = (EditText) findViewById(R.id.name_edit_user_edittext);
        mLastNameEditText = (EditText) findViewById(R.id.surname_edit_user_edittext);
        mGenderRadioGroup = (RadioGroup) findViewById(R.id.gender_edit_user_radiogroup);
        mPhoneEditText = (EditText) findViewById(R.id.phone_edit_user_edittext);
        mBirthDateButton = (Button) findViewById(R.id.birthdate_edit_user_button);
        mRegionSpinner = (Spinner) findViewById(R.id.region_edit_user_spinner);
        mCitySpinner = (Spinner) findViewById(R.id.location_edit_user_spinner);
        mUpdateButton = (Button) findViewById(R.id.update_edit_user_button);
        mProfileImageView = (ImageView) findViewById(R.id.profile_edit_user_imageview);
        mPhotographer = new Photographer();

        PhotographerListener listener = new PhotographerListener(mPhotographer, this);

        mProfileImageView.setOnClickListener(listener);
        mProfileImageView.setOnLongClickListener(listener);

        if (mCanAdopt) {

            RegionAdapter regionAdapter = new RegionAdapter(this.getApplicationContext(),
                    R.layout.ai_simple_textview_for_adapter, R.id.simple_textview,
                    getRegions(getApplicationContext()));

            mRegionSpinner.setAdapter(regionAdapter);
            mRegionSpinner.setOnItemSelectedListener(new ChangeRegionLocationsOnItemSelectedListener(mCitySpinner, mCity));
            mCitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    mCity = (City) parent.getItemAtPosition(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        } else {

            mRegionSpinner.setVisibility(View.GONE);
            mCitySpinner.setVisibility(View.GONE);
            findViewById(R.id.region_edit_user_textview).setVisibility(View.GONE);
            findViewById(R.id.city_edit_user_textview).setVisibility(View.GONE);

        }

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

        mFirstNameEditText.setText(mOwner.mFirstName);
        mLastNameEditText.setText(mOwner.getLastName());

        if (!Do.isNullOrEmpty(mOwner.mBirthDate)) {
            mBirthDateButton.setText(mOwner.mBirthDate);
        }

        mPhoneEditText.setText(mOwner.mPhone);

        if (mOwner.mGender == Tag.GENDER_MALE) {
            mGender = Tag.GENDER_MALE;
            mGenderRadioGroup.check(R.id.human_male_edit_user_radiobutton);

        } else {
            mGender = Tag.GENDER_FEMALE;
            mGenderRadioGroup.check(R.id.human_female_edit_user_radiobutton);

        }

        if (mCanAdopt && mCity != null && mCity.mCityId > 1) {

            int regionPosition = ((RegionAdapter) mRegionSpinner.getAdapter()).
                    getPosition(mCity.getRegion());
            CitiesAdapter locationAdapter = new CitiesAdapter(getApplicationContext(),
                    R.layout.ai_simple_textview_for_adapter, R.id.simple_textview,
                    City.getCitiesByRegion(mCity.mRegionId));

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
        if (!this.getClass().equals(HomeActivity.class))
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent result) {

        if (requestCode == Photographer.SQUARE_CAMERA_REQUEST_CODE &&
                resultCode == RESULT_OK) {

            if (result != null) {

                cropPhoto(result.getData());

            } else if (mPhotographer.mSourceImage != null) {

                cropPhoto(mPhotographer.mSourceImage);

            } else {
                Do.showLongToast(R.string.generic_networking_error, getApplicationContext());
            }

            super.onActivityResult(requestCode, resultCode, result);

        }

        if (requestCode == Crop.REQUEST_PICK
                && resultCode == RESULT_OK) {

            cropPhoto(result.getData());

        } else if (requestCode == Crop.REQUEST_CROP) {
            mPhotographer.handleCrop(resultCode, result, this, mProfileImageView);

        }
    }

    @Override
    public void takePhoto() {

        mPhotographer.takePicture(this);

    }

    @Override
    public void pickPhoto() {

        mPhotographer.pickImage(this);
    }

    @Override
    public void cropPhoto(Uri source) {

        mPhotographer.beginCrop(source, this);
    }

    @Override
    public void uploadPhoto() {


    }

    @Override
    public void succeedUpload() {

    }

    @Override
    public void failedUpload() {

    }


    public void enableViews(boolean enable) {

        mFirstNameEditText.setEnabled(enable);
        mBirthDateButton.setEnabled(enable);
        mUpdateButton.setEnabled(enable);


    }

    public void requestUpdateUser() {

        final String firstname = mFirstNameEditText.getText().toString();
        final String lastname = mLastNameEditText.getText().toString();

        if (TextUtils.isEmpty(firstname)) {
            mFirstNameEditText.setError(getString(R.string.field_not_empty_error));
            return;
        }

        if (TextUtils.isEmpty(lastname)) {
            mLastNameEditText.setError(getString(R.string.field_not_empty_error));
            return;
        }

        enableViews(false);
        mProgressDialog = ProgressDialog.show(EditUserActivity.this, "Espere un momento",
                "Estamos actualizando su perfil...");

        int ownerId = PrefsManager.getUserId(getApplicationContext());
        String ownerName = mFirstNameEditText.getText().toString();
        String secondLastName = getSecondLastName(ownerName);
        String rut = "";
        String birthDate = mBirthDateButton.getText().toString();
        int gender = mGender;
        String phone = mPhoneEditText.getText().toString();
        int cityId = mCanAdopt ? mCity.mCityId : 0;

        Owner owner = new Owner(ownerId, ownerName, firstname, lastname, secondLastName, rut, birthDate,
                gender, mOwner.mEmail, phone, cityId);

        JSONObject jsonParams = owner.getJsonObject();

        if (mPhotographer.hasPhotoChanged()) {

            JSONObject jsonPhoto = mPhotographer.getJsonPhoto(getApplicationContext());

            try {
                jsonParams.put(Photo.API_PHOTO, jsonPhoto);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        String token = PrefsManager.getUserToken(getApplicationContext());

        Request registerRequest = Api.putRequest(jsonParams,
                Api.ADDRESS_USER, this, this, token);

        registerRequest.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleyManager.getInstance(getApplicationContext())
                .addToRequestQueue(registerRequest, TAG);
    }

    private String getSecondLastName(String fullName) {

        String[] names = fullName.split(" ");

        if (names.length >= 2) {

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

        if (mOwner.mCityId > 0) {

            City city = mOwner.getCity();
            return Region.getRegions(city.getCountry().mCountryId);

        } else {

            Country country = Country.getSingleCountry(Do.getCountryIso(context));
            return Region.getRegions(country.mCountryId);
        }
    }

    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {

        mDate = Do.getToStringDate(day, month, year);
        mBirthDateButton.setText(mDate);

    }

    @Override
    public void onResponse(JSONObject response) {

        Context context = getApplicationContext();
        Owner owner = new Owner(response);

        mOwner.update(owner);
        PrefsManager.editUser(context, mOwner);
        mProgressDialog.dismiss();
        Do.showLongToast("Su perfil ha sido actualizado", context);

        onBackPressed();
    }

    @Override
    public void onErrorResponse(VolleyError error) {

        Api.error(error, this);
        mProgressDialog.dismiss();
        enableViews(true);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
