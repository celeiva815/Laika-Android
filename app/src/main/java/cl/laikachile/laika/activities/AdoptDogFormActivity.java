package cl.laikachile.laika.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import cl.laikachile.laika.R;
import cl.laikachile.laika.listeners.ChangeRegionLocationsOnItemSelectedListener;
import cl.laikachile.laika.listeners.SearchDogsToAdoptOnClickListener;
import cl.laikachile.laika.models.Location;
import cl.laikachile.laika.utils.Tag;

public class AdoptDogFormActivity extends ActionBarActivity {
	
	private int mIdLayout = R.layout.lk_adopt_dog_form_activity;
    public Spinner mSizeSpinner;
    public Spinner mPersonalitySpinner;
    public Spinner mRegionSpinner;
    public Spinner mCitySpinner;
    public Spinner mHomeSpinner;
    public EditText mPartnersEditText;
    public Spinner mFreeTimeSpinner;
    public Button mSearchButton;
    public Location mLocation;
    public int mGender;
    public boolean mKids;
    public boolean mElderly;
    public boolean mPets;

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

    public void setActivityView() {

        //View creation
        mSizeSpinner = (Spinner) findViewById(R.id.size_dog_form_spinner);
        mPersonalitySpinner = (Spinner) findViewById(R.id.personality_dog_form_spinner);
        mRegionSpinner = (Spinner) findViewById(R.id.region_dog_form_spinner);
        mCitySpinner = (Spinner) findViewById(R.id.city_dog_form_spinner);
        mHomeSpinner = (Spinner) findViewById(R.id.space_dog_form_spinner);
        mPartnersEditText = (EditText) findViewById(R.id.partners_dog_form_edittext);
        mFreeTimeSpinner = (Spinner) findViewById(R.id.free_time_dog_form_spinner);
        mSearchButton = (Button) findViewById(R.id.search_dog_form_button);

        //Adapters creation
        ArrayAdapter<String> sizeAdapter = new ArrayAdapter<String>(this.getApplicationContext(),
                R.layout.ai_simple_textview_for_adapter,
                this.getResources().getStringArray(R.array.dog_size_adopt));
        ArrayAdapter<String> personalityAdapter = new ArrayAdapter<String>(
                this.getApplicationContext(),R.layout.ai_simple_textview_for_adapter,
                this.getResources().getStringArray(R.array.personality_adopt));
        ArrayAdapter<String> regionAdapter = new ArrayAdapter<String>(this.getApplicationContext(),
                R.layout.ai_simple_textview_for_adapter,
                this.getResources().getStringArray(R.array.available_chilean_regions));
        ArrayAdapter<String> spaceAdapter = new ArrayAdapter<String>(this.getApplicationContext(),
                R.layout.ai_simple_textview_for_adapter,
                this.getResources().getStringArray(R.array.home_size_adopt));
        ArrayAdapter<String> freeTimeAdapter = new ArrayAdapter<String>(
                this.getApplicationContext(),R.layout.ai_simple_textview_for_adapter,
                this.getResources().getStringArray(R.array.free_time_adopt));
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

    public void setGenderFormRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        this.mGender = Tag.GENDER_BOTH;

        // Check which radio button was clicked
        switch(view.getId()) {
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
        switch(view.getId()) {
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
        switch(view.getId()) {
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
        switch(view.getId()) {
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
}
