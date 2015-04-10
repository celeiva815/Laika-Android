package cl.laikachile.laika.listeners;

import android.view.View;
import android.view.View.OnClickListener;

import cl.laikachile.laika.activities.EditDogRegisterActivity;
import cl.laikachile.laika.activities.MainActivity;
import cl.laikachile.laika.activities.NewDogRegisterActivity;
import cl.laikachile.laika.models.Dog;
import cl.laikachile.laika.utils.Do;
import cl.laikachile.laika.utils.PrefsManager;
import cl.laikachile.laika.utils.Tag;

public class EditDogOnClickListener implements OnClickListener {

    Dog mDog;
	EditDogRegisterActivity mActivity;

	public EditDogOnClickListener(EditDogRegisterActivity mActivity, Dog mDog) {
		
		this.mActivity = mActivity;
        this.mDog = mDog;
	}

	@Override
	public void onClick(View v) {
		
		mDog.mName = mActivity.mNameEditText.getText().toString();
        mDog.mBirth = mActivity.mBirthButton.getText().toString();
        mDog.mBreed = mActivity.mBreedSpinner.getSelectedItem().toString();
        mDog.mSize = mActivity.mSizeSpinner.getSelectedItem().toString();
        mDog.mPersonality = mActivity.mPersonalitySpinner.getSelectedItem().toString();
        mDog.mSterilized = mActivity.mSterilized;
        mDog.mChipCode = mActivity.mChipEditText.getText().toString();
        mDog.mGender = mActivity.mGender;
        mDog.mStatus = Tag.PROCESS_OWNED;

		mDog.save();
		
		mActivity.finish();
		Do.showToast("Haz editado la información de "+ mDog.mName, v.getContext());
	}

}
