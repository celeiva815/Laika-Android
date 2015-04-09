package cl.laikachile.laika.activities;

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

public class AdoptDogFormActivity extends BaseActivity {
	
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
		public void onStart() {

	    	createFragmentView(mIdLayout);
			super.onStart();		
		}
	 
    @Override
    public void setActivityView(View view) {

        //Dog
        mSizeSpinner = (Spinner) view.findViewById(R.id.size_dog_form_spinner);
        ArrayAdapter<String> sizeAdapter = new ArrayAdapter<String>(this.getApplicationContext(),
                R.layout.ai_simple_textview_for_adapter,
                this.getResources().getStringArray(R.array.dog_size_adopt));
        mSizeSpinner.setAdapter(sizeAdapter);

        mPersonalitySpinner = (Spinner) view.findViewById(R.id.personality_dog_form_spinner);
        ArrayAdapter<String> personalityAdapter = new ArrayAdapter<String>(
                this.getApplicationContext(),R.layout.ai_simple_textview_for_adapter,
                this.getResources().getStringArray(R.array.personality_adopt));
        mPersonalitySpinner.setAdapter(personalityAdapter);

        //Owner
        mRegionSpinner = (Spinner) view.findViewById(R.id.region_dog_form_spinner);
        mCitySpinner = (Spinner) view.findViewById(R.id.city_dog_form_spinner);
        ArrayAdapter<String> regionAdapter = new ArrayAdapter<String>(this.getApplicationContext(),
                R.layout.ai_simple_textview_for_adapter,
                this.getResources().getStringArray(R.array.available_chilean_regions));
        mRegionSpinner.setAdapter(regionAdapter);
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

        mHomeSpinner = (Spinner) view.findViewById(R.id.space_dog_form_spinner);
        ArrayAdapter<String> spaceAdapter = new ArrayAdapter<String>(this.getApplicationContext(),
                R.layout.ai_simple_textview_for_adapter,
                this.getResources().getStringArray(R.array.home_size_adopt));
        mHomeSpinner.setAdapter(spaceAdapter);

        mPartnersEditText = (EditText) view.findViewById(R.id.partners_dog_form_edittext);

        mFreeTimeSpinner = (Spinner) view.findViewById(R.id.free_time_dog_form_spinner);
        ArrayAdapter<String> freeTimeAdapter = new ArrayAdapter<String>(
                this.getApplicationContext(),R.layout.ai_simple_textview_for_adapter,
                    this.getResources().getStringArray(R.array.free_time_adopt));
        mFreeTimeSpinner.setAdapter(freeTimeAdapter);

        mSearchButton = (Button) view.findViewById(R.id.search_dog_form_button);

        SearchDogsToAdoptOnClickListener listener = new SearchDogsToAdoptOnClickListener(this);
        mSearchButton.setOnClickListener(listener);


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
