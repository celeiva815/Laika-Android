package cl.laikachile.laika.activities;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.fourmob.datetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;

import cl.laikachile.laika.R;
import cl.laikachile.laika.listeners.AddDogOnClickListener;
import cl.laikachile.laika.listeners.ChangeDogBreedsOnItemSelectedListener;
import cl.laikachile.laika.models.Dog;
import cl.laikachile.laika.utils.Do;
import cl.laikachile.laika.utils.Tag;

public class EditDogRegisterActivity extends NewDogRegisterActivity {

    public Dog mDog;

    @Override
    public void onStart() {

        createFragmentView(mIdLayout);
        super.onStart();
    }

    public void completeDogForm() {

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

        //mAddButton.setEnabled(isEnable);
        //mPersonalitySpinner.setEnabled(isEnable);

    }

    public void enableViews(boolean isEnable) {

        mNameEditText.setEnabled(isEnable);
        mBirthButton.setEnabled(isEnable);
        mSizeSpinner.setEnabled(isEnable);
        mBreedSpinner.setEnabled(isEnable);
        mAddButton.setEnabled(isEnable);
        mPersonalitySpinner.setEnabled(isEnable);

    }
}
