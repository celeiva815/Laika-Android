package cl.laikachile.laika.activities;

import android.content.Intent;
import android.view.View;
import android.view.WindowManager;

import cl.laikachile.laika.R;
import cl.laikachile.laika.adapters.BreedAdapter;
import cl.laikachile.laika.adapters.PersonalityAdapter;
import cl.laikachile.laika.adapters.SizeAdapter;
import cl.laikachile.laika.listeners.EditDogOnClickListener;
import cl.laikachile.laika.models.Dog;
import cl.laikachile.laika.models.Breed;
import cl.laikachile.laika.models.Personality;
import cl.laikachile.laika.models.Size;
import cl.laikachile.laika.utils.Do;
import cl.laikachile.laika.utils.Tag;

public class EditDogActivity extends NewDogActivity {

    @Override
    protected void onResume() {
        super.onResume();

        completeDogForm();
        mIsSizeSelected = false;
    }

    public void completeDogForm() {

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setDog();

        mNameEditText.setText(mDog.mName);
        mBirthButton.setText(mDog.mBirth);

        Breed breed = mDog.getBreed();
        Size size = mDog.getSize();
        Personality personality = mDog.getPersonality();

        int sizePosition = ((SizeAdapter) mSizeSpinner.getAdapter()).getPosition(size);
        mSizeSpinner.setSelection(sizePosition);

        int breedPosition = ((BreedAdapter) mBreedSpinner.getAdapter()).getPosition(breed);
        mBreedSpinner.setSelection(breedPosition);

        int personalityPosition = ((PersonalityAdapter) mPersonalitySpinner.getAdapter()).
                getPosition(personality);
        mPersonalitySpinner.setSelection(personalityPosition);

        if (mDog.mGender == Tag.GENDER_MALE) {
            mGenderRadioGroup.check(R.id.male_new_dog_register_radiobutton);

        } else {
            mGenderRadioGroup.check(R.id.female_new_dog_register_radiobutton);

        }

        if (mDog.mIsSterilized) {
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

        mAddButton.setText("Guardar"); //XXX agregar el string respectivo
        mAddButton.setOnClickListener(new EditDogOnClickListener(this, mDog));

    }

    public void setDog() {

        Intent intent = getIntent();
        int dogId = intent.getIntExtra(KEY_DOG_ID, 0);

        mDog = Dog.getSingleDog(dogId);
    }
}
