package cl.laikachile.laika.activities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.fourmob.datetimepicker.date.DatePickerDialog;

import cl.laikachile.laika.R;
import cl.laikachile.laika.adapters.BreedAdapter;
import cl.laikachile.laika.adapters.PersonalityAdapter;
import cl.laikachile.laika.adapters.SizeAdapter;
import cl.laikachile.laika.fragments.PlaceHolderFragment;
import cl.laikachile.laika.listeners.AddDogOnClickListener;
import cl.laikachile.laika.listeners.ChangeDogBreedsOnItemSelectedListener;
import cl.laikachile.laika.models.Dog;
import cl.laikachile.laika.models.indexes.Breed;
import cl.laikachile.laika.models.indexes.Personality;
import cl.laikachile.laika.models.indexes.Size;
import cl.laikachile.laika.utils.Do;
import cl.laikachile.laika.utils.Tag;

public class NewDogRegisterActivity extends ActionBarActivity implements DatePickerDialog.OnDateSetListener {

    public static final String KEY_DOG_ID = "dog_id";
    protected int mIdLayout = R.layout.lk_new_dog_register_activity;

    public Dog mDog;
    public EditText mNameEditText;
    public Spinner mSizeSpinner;
    public Spinner mBreedSpinner;
    public BreedAdapter mBreedAdapter;
    public Button mBirthButton;
    public Spinner mPersonalitySpinner;
    public EditText mChipEditText;
    public Button mAddButton;
    public RadioGroup mGenderRadioGroup ;
    public RadioGroup mSterilizedRadioGroup;
    public RadioGroup mChipRadioGroup;
    public int mGender;
    public boolean mSterilized;
    public boolean mChip;
    public String mChipCode;
    public String mDate;

    public boolean mIsSizeSelected = true;

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

        mNameEditText = (EditText) findViewById(R.id.name_new_dog_register_edittext);
        mGenderRadioGroup =(RadioGroup) findViewById(R.id.gender_new_dog_register_radiogroup);
        mBirthButton = (Button) findViewById(R.id.birth_new_dog_register_button);
        mSizeSpinner = (Spinner) findViewById(R.id.size_new_dog_register_spinner);
        mBreedSpinner = (Spinner) findViewById(R.id.type_new_dog_register_spinner);
        mPersonalitySpinner = (Spinner) findViewById(R.id.personality_new_dog_register_spinner);
        mSterilizedRadioGroup =(RadioGroup) findViewById(R.id.sterilized_new_dog_register_radiogroup);
        mChipEditText = (EditText) findViewById(R.id.chip_code_new_dog_register_edittext);
        mChipRadioGroup =(RadioGroup) findViewById(R.id.chip_new_dog_register_radiogroup);
        mAddButton = (Button) findViewById(R.id.add_dog_new_dog_register_button);

        SizeAdapter sizeAdapter = new SizeAdapter(this.getApplicationContext(),
                R.layout.ai_simple_textview_for_adapter, R.id.simple_textview, getSizeList());
        mBreedAdapter = new BreedAdapter(this.getApplicationContext(),
                R.layout.ai_simple_textview_for_adapter, R.id.simple_textview, getBreedList(mSizeSpinner));
        PersonalityAdapter personalityAdapter = new PersonalityAdapter(this.getApplicationContext(),
                R.layout.ai_simple_textview_for_adapter, R.id.simple_textview, getPersonalityList());
        ChangeDogBreedsOnItemSelectedListener breedListener = new ChangeDogBreedsOnItemSelectedListener(this);
        AddDogOnClickListener addListener = new AddDogOnClickListener(this);

        final Calendar calendar = Calendar.getInstance();
        final DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(this,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), false);

        mBirthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                datePickerDialog.setYearRange(1990, calendar.get(Calendar.YEAR));
                datePickerDialog.setCloseOnSingleTapDay(false);
                datePickerDialog.show(getSupportFragmentManager(), Tag.DATE_PICKER);
            }
        });

        mDate = Do.getToStringDate(calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR));
        mBirthButton.setText(mDate);

        mSizeSpinner.setAdapter(sizeAdapter);
        mBreedSpinner.setAdapter(mBreedAdapter);
        mSizeSpinner.setOnItemSelectedListener(breedListener);
        mPersonalitySpinner.setAdapter(personalityAdapter);

        if (mChip) {
            mChipCode = mChipEditText.getText().toString();
        }

        mAddButton.setOnClickListener(addListener);

    }

    public List<Personality> getPersonalityList() {

        return Personality.getPersonalities();
    }


    public List<Size> getSizeList() {

        return Size.getSizes();
    }

    public List<Breed> getBreedList(Spinner sizeSpinner) {

        int sizeId = (int) sizeSpinner.getSelectedItemId();

        return Breed.getBreeds(sizeId);

    }

    public void setGenderRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        this.mGender = Tag.GENDER_MALE;

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.male_new_dog_register_radiobutton:
                if (checked)
                    mGender = Tag.GENDER_MALE;

                break;
            case R.id.female_new_dog_register_radiobutton:
                if (checked)
                    mGender = Tag.GENDER_FEMALE;

                break;
        }
    }

    public void setSterilizedRadioButton(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        this.mSterilized = false;

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.sterilized_new_dog_register_radiobutton:
                if (checked)
                    this.mSterilized = true;

                break;
            case R.id.not_sterilized_new_dog_register_radiobutton:
                if (checked)
                    this.mSterilized = false;

                break;
        }
    }

    public void setChipRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        this.mChip = false;

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.chip_new_dog_register_radiobutton:
                if (checked) {
                    mChip = true;
                    mChipEditText.setVisibility(View.VISIBLE);
                }

                break;
            case R.id.not_chip_new_dog_register_radiobutton:
                if (checked) {
                    mChip = false;
                    mChipEditText.setVisibility(View.GONE);
                    mChipEditText.setText("");
                }

                break;
        }
    }

    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {

        mDate = Do.getToStringDate(day, month, year);
        mBirthButton.setText(mDate);

    }
}
