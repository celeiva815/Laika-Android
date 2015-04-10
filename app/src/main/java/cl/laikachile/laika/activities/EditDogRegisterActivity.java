package cl.laikachile.laika.activities;

import android.content.Intent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.fourmob.datetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;

import cl.laikachile.laika.R;
import cl.laikachile.laika.fragments.PlaceHolderFragment;
import cl.laikachile.laika.listeners.AddDogOnClickListener;
import cl.laikachile.laika.listeners.ChangeDogBreedsOnItemSelectedListener;
import cl.laikachile.laika.listeners.EditDogOnClickListener;
import cl.laikachile.laika.models.Dog;
import cl.laikachile.laika.utils.Do;
import cl.laikachile.laika.utils.Tag;

public class EditDogRegisterActivity extends NewDogRegisterActivity {

    public Dog mDog;

    @Override
    protected void onResume() {
        super.onResume();

        completeDogForm();
    }

    public void completeDogForm() {

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setDog();

        mNameEditText.setText(mDog.mName);
        mBirthButton.setText(mDog.mBirth);

        ArrayList<String> sizeList = getSizeList();

        for (int i = 0; i < sizeList.size(); i++) {

            if (sizeList.get(i).equals(mDog.mSize)) {

                mSizeSpinner.setSelection(i);
                break;
            }
        }

        String[] breedList = getBreedList(mSizeSpinner);

        for (int i = 0; i < breedList.length; i++) {

            if (breedList[i].equals(mDog.mSize)) {

                mBreedSpinner.setSelection(i);
                break;
            }
        }

        String[] personalityList = getPersonalityList();

        for (int i = 0; i < personalityList.length; i++) {

            if (personalityList[i].equals(mDog.mSize)) {

                mPersonalitySpinner.setSelection(i);
                break;
            }
        }

        if (mDog.mGender == Tag.GENDER_MALE) {
            mGenderRadioGroup.check(R.id.male_new_dog_register_radiobutton);

        } else {
            mGenderRadioGroup.check(R.id.female_new_dog_register_radiobutton);

        }

        if (mDog.mSterilized) {
            mSterilizedRadioGroup.check(R.id.sterilized_new_dog_register_radiobutton);

        } else {
            mSterilizedRadioGroup.check(R.id.not_sterilized_new_dog_register_radiobutton);
        }

        if (Do.isNullOrEmpty(mDog.mChipCode)) {
            mChipRadioGroup.check(R.id.not_chip_new_dog_register_radiobutton);

        } else {
            mChipRadioGroup.check(R.id.chip_new_dog_register_radiobutton);
            mChipEditText.setVisibility(View.VISIBLE);
            mChipEditText.setText(mDog.mChipCode);
        }

        mAddButton.setText("Guardar");
        mAddButton.setOnClickListener(new EditDogOnClickListener(this, mDog));

    }

    public void setDog() {

        Intent intent = getIntent();
        int dogId = intent.getIntExtra(KEY_DOG_ID, 0);

        mDog = Dog.getSingleDog(dogId);
    }
}
