package cl.laikachile.laika.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.android.volley.Request;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cl.laikachile.laika.R;
import cl.laikachile.laika.adapters.FreeTimeAdapter;
import cl.laikachile.laika.adapters.PersonalityAdapter;
import cl.laikachile.laika.adapters.SizeAdapter;
import cl.laikachile.laika.adapters.SpaceAdapter;
import cl.laikachile.laika.listeners.ChangeRegionLocationsOnItemSelectedListener;
import cl.laikachile.laika.listeners.SearchDogsToAdoptOnClickListener;
import cl.laikachile.laika.models.AdoptDogForm;
import cl.laikachile.laika.models.indexes.FreeTime;
import cl.laikachile.laika.models.Location;
import cl.laikachile.laika.models.Personality;
import cl.laikachile.laika.models.Size;
import cl.laikachile.laika.models.indexes.Space;
import cl.laikachile.laika.network.RequestManager;
import cl.laikachile.laika.network.VolleyManager;
import cl.laikachile.laika.responses.AdoptDogFormResponse;
import cl.laikachile.laika.responses.DogForAdoptionResponse;
import cl.laikachile.laika.utils.PrefsManager;
import cl.laikachile.laika.utils.Tag;

public class AdoptDogFormActivity extends ActionBarActivity {

    public static final String TAG = AdoptDogFormActivity.class.getSimpleName();
    public static final String API_LIMIT = "limit";

    private int mIdLayout = R.layout.lk_adopt_dog_form_activity;
    public Spinner mSizeSpinner;
    public Spinner mPersonalitySpinner;
    public Spinner mRegionSpinner;
    public Spinner mCitySpinner;
    public Spinner mHomeSpinner;
    public EditText mPartnersEditText;
    public Spinner mFreeTimeSpinner;
    public RadioGroup mKidsRadioGroup;
    public RadioGroup mElderlyRadioGroup;
    public RadioGroup mPetsRadioGroup;
    public Button mSearchButton;
    public Location mLocation;
    public int mGender;
    public boolean mKids;
    public boolean mElderly;
    public boolean mPets;
    public ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mIdLayout);
        setActivityView();

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.color.laika_red));
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        if (!this.getClass().equals(MainActivity.class))
            getMenuInflater().inflate(R.menu.activity_main, menu);

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

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    public void setActivityView() {

        //View creation
        mSizeSpinner = (Spinner) findViewById(R.id.size_dog_form_spinner);
        mPersonalitySpinner = (Spinner) findViewById(R.id.personality_dog_form_spinner);
        mRegionSpinner = (Spinner) findViewById(R.id.region_dog_form_spinner);
        mCitySpinner = (Spinner) findViewById(R.id.city_dog_form_spinner);
        mHomeSpinner = (Spinner) findViewById(R.id.space_dog_form_spinner);
        mPartnersEditText = (EditText) findViewById(R.id.partners_dog_form_edittext);
        mFreeTimeSpinner = (Spinner) findViewById(R.id.free_time_dog_form_spinner);
        mKidsRadioGroup = (RadioGroup) findViewById(R.id.kids_dog_form_radiogroup);
        mElderlyRadioGroup = (RadioGroup) findViewById(R.id.elderly_dog_form_radiogroup);
        mPetsRadioGroup = (RadioGroup) findViewById(R.id.pets_dog_form_radiogroup);
        mSearchButton = (Button) findViewById(R.id.search_dog_form_button);

        //Adapters creation
        SizeAdapter sizeAdapter = new SizeAdapter(this.getApplicationContext(),
                R.layout.ai_simple_textview_for_adapter, R.id.simple_textview,
                getSizes());

        PersonalityAdapter personalityAdapter = new PersonalityAdapter(this.getApplicationContext(),
                R.layout.ai_simple_textview_for_adapter, R.id.simple_textview,
                getPersonalities());

        SpaceAdapter spaceAdapter = new SpaceAdapter(this.getApplicationContext(),
                R.layout.ai_simple_textview_for_adapter, R.id.simple_textview,
                getSpaces(getApplicationContext()));

        FreeTimeAdapter freeTimeAdapter = new FreeTimeAdapter(this.getApplicationContext(),
                R.layout.ai_simple_textview_for_adapter, R.id.simple_textview,
                getFreeTimes(getApplicationContext()));

        //TODO el adapter para el spinner de los locations una vez que sean enviados por la API
        ArrayAdapter<String> regionAdapter = new ArrayAdapter<String>(this.getApplicationContext(),
                R.layout.ai_simple_textview_for_adapter,
                this.getResources().getStringArray(R.array.available_chilean_regions));

        SearchDogsToAdoptOnClickListener listener = new SearchDogsToAdoptOnClickListener(this);

        //Setting the adapters
        mSizeSpinner.setAdapter(sizeAdapter);
        mPersonalitySpinner.setAdapter(personalityAdapter);
        mRegionSpinner.setAdapter(regionAdapter);
        mFreeTimeSpinner.setAdapter(freeTimeAdapter);
        mHomeSpinner.setAdapter(spaceAdapter);

        //Setting the listeners
        mSearchButton.setOnClickListener(listener);
        mRegionSpinner.setOnItemSelectedListener(new ChangeRegionLocationsOnItemSelectedListener(mCitySpinner));
        mCitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mLocation = (Location) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void requestAdoptionDogForm(AdoptDogForm adoptDogForm) {

        mProgressDialog = ProgressDialog.show(AdoptDogFormActivity.this,
                "Esperanos un momento", "Estamos buscando perritos...");

        JSONObject jsonParams = adoptDogForm.getJsonObject();
        AdoptDogFormResponse response = new AdoptDogFormResponse(this);

        Request adoptDogRequest = RequestManager.postRequest(jsonParams,
                RequestManager.ADDRESS_UPLOAD_ADOPTION_FORM, response, response,
                PrefsManager.getUserToken(getApplicationContext()));

        VolleyManager.getInstance(getApplicationContext())
                .addToRequestQueue(adoptDogRequest, TAG);

    }

    public void requestDogsForAdoption() {

        Map<String,String> params = new HashMap<>();
        DogForAdoptionResponse response = new DogForAdoptionResponse(this);

        params.put(API_LIMIT, Integer.toString(10));

        Request adoptDogRequest = RequestManager.getRequest(params,
                RequestManager.ADDRESS_GET_MATCHING_DOGS, response, response,
                PrefsManager.getUserToken(getApplicationContext()));

        VolleyManager.getInstance(getApplicationContext())
                .addToRequestQueue(adoptDogRequest, TAG);

    }

    public void setGenderFormRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        this.mGender = Tag.GENDER_BOTH;

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.male_dog_form_radiobutton:
                if (checked) {
                    mGender = Tag.GENDER_MALE;
                }
                break;

            case R.id.female_dog_form_radiobutton:
                if (checked) {
                    mGender = Tag.GENDER_FEMALE;
                }
                break;

            case R.id.both_dog_form_radiobutton:
                if (checked) {
                    mGender = Tag.GENDER_BOTH;
                }
                break;
        }
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

    public List<Size> getSizes() {
        return Size.getSizes();
    }

    public List<Personality> getPersonalities() {
        return Personality.getPersonalities();
    }

    public List<Space> getSpaces(Context context) {
        return Space.getSpaces(context);
    }

    public List<FreeTime> getFreeTimes(Context context) {

        return FreeTime.getFreeTimes(context);
    }

    public void showDialog(String title, String message) {

        AlertDialog.Builder dialog = new AlertDialog.Builder(getApplicationContext());

        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.setCancelable(true);
        dialog.show();

    }

}
