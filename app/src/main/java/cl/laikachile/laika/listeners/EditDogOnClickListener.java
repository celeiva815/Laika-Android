package cl.laikachile.laika.listeners;

import android.view.View;
import android.view.View.OnClickListener;

import cl.laikachile.laika.activities.EditDogActivity;
import cl.laikachile.laika.models.Dog;
import cl.laikachile.laika.network.RequestManager;
import cl.laikachile.laika.utils.Tag;

public class EditDogOnClickListener implements OnClickListener {

    Dog mDog;
	EditDogActivity mActivity;

	public EditDogOnClickListener(EditDogActivity mActivity, Dog mDog) {
		
		this.mActivity = mActivity;
        this.mDog = mDog;
	}

	@Override
	public void onClick(View v) {
		
		mDog.mName = mActivity.mNameEditText.getText().toString();
        mDog.mBirth = mActivity.mBirthButton.getText().toString();
        mDog.mBreedId = (int) mActivity.mBreedSpinner.getSelectedItemId();
        mDog.mPersonality = (int) mActivity.mPersonalitySpinner.getSelectedItemId();
        mDog.mIsSterilized = mActivity.mSterilized;
        mDog.mChipCode = mActivity.mChipEditText.getText().toString();
        mDog.mGender = mActivity.mGender;
        mDog.mStatus = Tag.DOG_OWNED;

		mDog.save();

		mActivity.requestCreateOrUpdateDog(mDog, "Haz editado la información de " + mDog.mName,
				RequestManager.METHOD_PUT);
	}

}
