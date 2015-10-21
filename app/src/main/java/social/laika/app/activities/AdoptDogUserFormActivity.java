package social.laika.app.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
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

import com.android.volley.Request;

import org.json.JSONObject;

import java.util.List;

import social.laika.app.R;
import social.laika.app.adapters.CitiesAdapter;
import social.laika.app.adapters.FreeTimeAdapter;
import social.laika.app.adapters.RegionAdapter;
import social.laika.app.adapters.SpaceAdapter;
import social.laika.app.interfaces.Requestable;
import social.laika.app.listeners.ChangeRegionLocationsOnItemSelectedListener;
import social.laika.app.listeners.HelperDialogOnClickListener;
import social.laika.app.listeners.SubmitUserAdoptionFormOnClickListener;
import social.laika.app.models.AdoptDogForm;
import social.laika.app.models.City;
import social.laika.app.models.Country;
import social.laika.app.models.Owner;
import social.laika.app.models.Region;
import social.laika.app.models.indexes.FreeTime;
import social.laika.app.models.indexes.Space;
import social.laika.app.network.Api;
import social.laika.app.network.VolleyManager;
import social.laika.app.responses.AdoptDogUserFormResponse;
import social.laika.app.utils.Do;
import social.laika.app.utils.PrefsManager;

public class AdoptDogUserFormActivity extends ActionBarActivity implements Requestable {

    public static final String TAG = AdoptDogUserFormActivity.class.getSimpleName();
    public static final String API_LIMIT = "limit";
    public static final String KEY_NEXT_ACTIVITY = "next";

    private int mIdLayout = R.layout.lk_adopt_dog_user_form_activity;
    public int mNext;
    public Spinner mRegionSpinner;
    public Spinner mCitySpinner;
    public Spinner mHomeSpinner;
    public EditText mPhoneEditText;
    public Spinner mFreeTimeSpinner;
    public RadioGroup mKidsRadioGroup;
    public RadioGroup mElderlyRadioGroup;
    public RadioGroup mPetsRadioGroup;
    public Button mSearchButton;
    public City mCity;
    public boolean mKids;
    public boolean mElderly;
    public boolean mPets;
    public ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mIdLayout);

        Owner owner = PrefsManager.getLoggedOwner(getApplicationContext());
        mCity = City.getSingleLocation(owner.mCityId);
        mNext = getIntent().getExtras().getInt(KEY_NEXT_ACTIVITY, AdoptDogUserFormResponse.NEXT_ADOPT_DOG);

        setActivityView();
        setValues();

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.color.laika_red));
        actionBar.setDisplayHomeAsUpEnabled(true);
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

    @Override
    protected void onStart() {
        super.onStart();

        setValues();
        Do.hideKeyboard(this);
    }

    public void setActivityView() {

        //View creation
        mRegionSpinner = (Spinner) findViewById(R.id.region_dog_form_spinner);
        mCitySpinner = (Spinner) findViewById(R.id.city_dog_form_spinner);
        mHomeSpinner = (Spinner) findViewById(R.id.space_dog_form_spinner);
        mFreeTimeSpinner = (Spinner) findViewById(R.id.free_time_dog_form_spinner);
        mPhoneEditText = (EditText) findViewById(R.id.phone_adopt_dog_user_edittext);
        mKidsRadioGroup = (RadioGroup) findViewById(R.id.kids_dog_form_radiogroup);
        mElderlyRadioGroup = (RadioGroup) findViewById(R.id.elderly_dog_form_radiogroup);
        mPetsRadioGroup = (RadioGroup) findViewById(R.id.pets_dog_form_radiogroup);
        mSearchButton = (Button) findViewById(R.id.search_dog_form_button);
        ImageView spaceHelper = (ImageView) findViewById(R.id.space_helper);
        ImageView phoneHelper = (ImageView) findViewById(R.id.phone_helper);
        ImageView kidsHelper = (ImageView) findViewById(R.id.kids_helper);
        ImageView elderlyHelper = (ImageView) findViewById(R.id.elderly_helper);
        ImageView petsHelper = (ImageView) findViewById(R.id.pets_helper);
        ImageView freeTimeHelper = (ImageView) findViewById(R.id.free_time_helper);

        //Adapters creation
        SpaceAdapter spaceAdapter = new SpaceAdapter(this.getApplicationContext(),
                R.layout.ai_simple_textview_for_adapter, R.id.simple_textview,
                getSpaces(getApplicationContext()));

        FreeTimeAdapter freeTimeAdapter = new FreeTimeAdapter(this.getApplicationContext(),
                R.layout.ai_simple_textview_for_adapter, R.id.simple_textview,
                getFreeTimes(getApplicationContext()));

        RegionAdapter regionAdapter = new RegionAdapter(this.getApplicationContext(),
                R.layout.ai_simple_textview_for_adapter, R.id.simple_textview,
                getRegions(getApplicationContext()));

        SubmitUserAdoptionFormOnClickListener listener = new SubmitUserAdoptionFormOnClickListener(this);

        spaceHelper.setOnClickListener(new HelperDialogOnClickListener(R.string.home_type_helper, AdoptDogUserFormActivity.this));
        phoneHelper.setOnClickListener(new HelperDialogOnClickListener(R.string.phone_helper, AdoptDogUserFormActivity.this));
        kidsHelper.setOnClickListener(new HelperDialogOnClickListener(R.string.kids_helper, AdoptDogUserFormActivity.this));
        elderlyHelper.setOnClickListener(new HelperDialogOnClickListener(R.string.elderly_helper, AdoptDogUserFormActivity.this));
        petsHelper.setOnClickListener(new HelperDialogOnClickListener(R.string.pets_helper, AdoptDogUserFormActivity.this));
        freeTimeHelper.setOnClickListener(new HelperDialogOnClickListener(R.string.time_helper, AdoptDogUserFormActivity.this));

        //Setting the adapters
        mRegionSpinner.setAdapter(regionAdapter);
        mFreeTimeSpinner.setAdapter(freeTimeAdapter);
        mHomeSpinner.setAdapter(spaceAdapter);

        //Setting the listeners
        mSearchButton.setOnClickListener(listener);
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
    }

    private void setValues() {

        Owner owner = PrefsManager.getLoggedOwner(getApplicationContext());
        AdoptDogForm form = AdoptDogForm.getUserAdoptDogForm(owner.mOwnerId);

        mPhoneEditText.setText(owner.mPhone);

        if (mCity != null && mCity.mCityId > 0) {

            int regionPosition = ((RegionAdapter) mRegionSpinner.getAdapter()).
                    getPosition(mCity.getRegion());
            CitiesAdapter locationAdapter = new CitiesAdapter(getApplicationContext(),
                    R.layout.ai_simple_textview_for_adapter, R.id.simple_textview,
                    City.getCitiesByRegion(mCity.mRegionId));

            mCitySpinner.setAdapter(locationAdapter);
            mRegionSpinner.setSelection(regionPosition);

        }

        if (form != null) {

            if (form.mHomeType > 0) {

                int homePosition = ((SpaceAdapter) mHomeSpinner.getAdapter()).getPosition(form.mHomeType);
                mHomeSpinner.setSelection(homePosition);
            }

            if (form.mFreeTime > 0) {

                int position = ((FreeTimeAdapter) mFreeTimeSpinner.getAdapter()).getPosition(form.mFreeTime);
                mFreeTimeSpinner.setSelection(position);
            }

            if (!Do.isNullOrEmpty(form.mPhone)) {

                mPhoneEditText.setText(form.mPhone);
            }

            if (form.mHasKids) {

                ((RadioButton) findViewById(R.id.yes_kid_dog_form_radiobutton)).setChecked(true);
                mKids = true;

            } else {

                ((RadioButton) findViewById(R.id.no_kid_dog_form_radiobutton)).setChecked(true);
                mKids = false;
            }

            if (form.mHasElderly) {

                ((RadioButton) findViewById(R.id.yes_elderly_dog_form_radiobutton)).setChecked(true);
                mElderly = true;

            } else {

                ((RadioButton) findViewById(R.id.no_elderly_dog_form_radiobutton)).setChecked(true);
                mElderly = false;

            }

            if (form.mHasPet) {

                ((RadioButton) findViewById(R.id.yes_pet_dog_form_radiobutton)).setChecked(true);
                mPets = true;

            } else {

                ((RadioButton) findViewById(R.id.no_pet_dog_form_radiobutton)).setChecked(true);
                mPets = false;
            }
        }
    }

    public void requestAdoptionDogForm(AdoptDogForm adoptDogForm) {

        mProgressDialog = ProgressDialog.show(AdoptDogUserFormActivity.this, "Espere un momento...",
                "Enviando formulario de adopción");

        JSONObject jsonParams = adoptDogForm.getJsonObject();
        AdoptDogUserFormResponse response = new AdoptDogUserFormResponse(this, mNext);

        Request adoptDogRequest = Api.postRequest(jsonParams,
                Api.ADDRESS_UPLOAD_ADOPTION_FORM, response, response,
                PrefsManager.getUserToken(getApplicationContext()));

        VolleyManager.getInstance(getApplicationContext())
                .addToRequestQueue(adoptDogRequest, TAG);

    }

    public void setKidsRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        this.mKids = false;

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.yes_kid_dog_form_radiobutton:
                if (checked) {
                    mKids = true;
                }
                break;

            case R.id.no_kid_dog_form_radiobutton:
                if (checked) {
                    mKids = false;
                }

                break;
        }
    }

    public void setElderlyRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        this.mElderly = false;

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.yes_elderly_dog_form_radiobutton:
                if (checked) {
                    mElderly = true;
                }
                break;

            case R.id.no_elderly_dog_form_radiobutton:
                if (checked) {
                    mElderly = false;
                }
                break;
        }
    }

    public void setPetsRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        this.mPets = false;

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.yes_pet_dog_form_radiobutton:
                if (checked) {
                    mPets = true;

                }
                break;

            case R.id.no_pet_dog_form_radiobutton:
                if (checked) {
                    mPets = false;
                }
                break;
        }
    }

    public List<Space> getSpaces(Context context) {
        return Space.getSpaces(context);
    }

    public List<FreeTime> getFreeTimes(Context context) { return FreeTime.getFreeTimes(context);  }

    public List<Region> getRegions(Context context) {

        String iso = Do.getCountryIso(context);
        Country country = Country.getSingleCountry(iso);
        return Region.getRegions(country.mCountryId);
    }

    public void showDialog(String title, String message) {

        AlertDialog.Builder dialog = new AlertDialog.Builder(getApplicationContext());

        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.setCancelable(true);
        dialog.show();

    }

    @Override
    public void request() {

    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onFailure() {

    }
}
