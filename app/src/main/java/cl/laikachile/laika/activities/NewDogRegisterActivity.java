package cl.laikachile.laika.activities;

import java.util.ArrayList;
import java.util.Calendar;

import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.fourmob.datetimepicker.date.DatePickerDialog;

import cl.laikachile.laika.R;
import cl.laikachile.laika.listeners.AddDogOnClickListener;
import cl.laikachile.laika.listeners.ChangeDogBreedsOnItemSelectedListener;
import cl.laikachile.laika.utils.Do;
import cl.laikachile.laika.utils.Tag;

public class NewDogRegisterActivity extends BaseActivity implements DatePickerDialog.OnDateSetListener {

    protected int mIdLayout = R.layout.lk_new_dog_register_activity;

    public EditText mNameEditText;
    public Spinner mSizeSpinner;
    public Spinner mBreedSpinner;
    public Button mBirthButton;
    public Spinner mPersonalitySpinner;
    public EditText mChipEditText;
    public Button mAddButton;
    public int mGender;
    public boolean mSterilized;
    public boolean mChip;
    public String mChipCode;
    public String mDate;

    @Override
    public void onStart() {

        createFragmentView(mIdLayout);
        super.onStart();
    }

    @Override
    public void setActivityView(View view) {

        mNameEditText = (EditText) view.findViewById(R.id.name_new_dog_register_edittext);
        mBirthButton = (Button) view.findViewById(R.id.birth_new_dog_register_button);
        mSizeSpinner = (Spinner) view.findViewById(R.id.size_new_dog_register_spinner);
        mBreedSpinner = (Spinner) view.findViewById(R.id.type_new_dog_register_spinner);
        mAddButton = (Button) view.findViewById(R.id.add_dog_new_dog_register_button);
        mPersonalitySpinner = (Spinner) view.findViewById(R.id.personality_new_dog_register_spinner);

        ArrayAdapter<String> sizeAdapter = new ArrayAdapter<String>(this.getApplicationContext(), R.layout.ai_simple_textview_for_adapter, getSizeList());
        ArrayAdapter<String> breedAdapter = new ArrayAdapter<String>(this.getApplicationContext(), R.layout.ai_simple_textview_for_adapter, getBreedList(mSizeSpinner));
        ArrayAdapter<String> personalityAdapter = new ArrayAdapter<String>(this.getApplicationContext(), R.layout.ai_simple_textview_for_adapter, getPersonalityList());
        ChangeDogBreedsOnItemSelectedListener breedListener = new ChangeDogBreedsOnItemSelectedListener(mBreedSpinner);
        AddDogOnClickListener addListener = new AddDogOnClickListener(this);

        final Calendar calendar = Calendar.getInstance();
        final DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(this,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), false);

        mBirthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                datePickerDialog.setYearRange(1990, 2028);
                datePickerDialog.setCloseOnSingleTapDay(false);
                datePickerDialog.show(getSupportFragmentManager(), Tag.DATE_PICKER);
            }
        });

        mDate = Do.getToStringDate(calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR));
        mBirthButton.setText(mDate);

        mSizeSpinner.setAdapter(sizeAdapter);
        mBreedSpinner.setAdapter(breedAdapter);
        mSizeSpinner.setOnItemSelectedListener(breedListener);
        mPersonalitySpinner.setAdapter(personalityAdapter);

        if (mChip) {
            mChipCode = mChipEditText.getText().toString();
        }

        mAddButton.setOnClickListener(addListener);

    }

    protected String[] getPersonalityList() {

        return this.getResources().getStringArray(R.array.personality_adopt);
    }


    protected ArrayList<String> getSizeList() {

        ArrayList<String> sizeList = new ArrayList<String>();
        sizeList.add(Do.getRString(getApplicationContext(), R.string.smaller_new_dog_register));
        sizeList.add(Do.getRString(getApplicationContext(), R.string.small_new_dog_register));
        sizeList.add(Do.getRString(getApplicationContext(), R.string.middle_new_dog_register));
        sizeList.add(Do.getRString(getApplicationContext(), R.string.big_new_dog_register));
        sizeList.add(Do.getRString(getApplicationContext(), R.string.bigger_new_dog_register));

        return sizeList;
    }

    protected String[] getBreedList(Spinner sizeSpinner) {

        int position = sizeSpinner.getSelectedItemPosition();
        int resource;

        switch (position) {
            case 0:

                resource = R.array.smaller_breed;

                break;
            case 1:
                resource = R.array.small_breed;

                break;
            case 2:
                resource = R.array.middle_breed;

                break;
            case 3:

                resource = R.array.big_breed;
                break;
            case 4:

                resource = R.array.bigger_breed;
                break;
            default:
                resource = R.array.smaller_breed;
                break;
        }

        return getResources().getStringArray(resource);

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
            case R.id.male_new_dog_register_radiobutton:
                if (checked) {
                    mChip = true;
                    mChipEditText.setVisibility(View.VISIBLE);
                }

                break;
            case R.id.female_new_dog_register_radiobutton:
                if (checked) {
                    mChip = false;
                    mChipEditText.setVisibility(View.GONE);
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
