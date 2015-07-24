package social.laika.app.listeners;

import social.laika.app.activities.CreateDogActivity;
import social.laika.app.models.Dog;
import social.laika.app.network.RequestManager;
import social.laika.app.utils.PrefsManager;
import social.laika.app.utils.Tag;

import android.view.View;
import android.view.View.OnClickListener;

public class NewDogOnClickListener implements OnClickListener {

	CreateDogActivity mActivity;
	
	public NewDogOnClickListener(CreateDogActivity mActivity) {
		
		this.mActivity = mActivity;
	}

	@Override
	public void onClick(View v) {
		
		//FIXME crear los valores posibles, Esperar por la API
		String name = mActivity.mNameEditText.getText().toString();
	    String birth = mActivity.mBirthButton.getText().toString();
		int breed = (int) mActivity.mBreedSpinner.getSelectedItemId();
        int personality = (int) mActivity.mPersonalitySpinner.getSelectedItemId();
        boolean sterilized = mActivity.mSterilized;
        String chipCode = mActivity.mChipEditText.getText().toString();
        int gender = mActivity.mGender;
        int status = Tag.DOG_OWNED;
        int userId = PrefsManager.getUserId(v.getContext());
		
		Dog newDog = new Dog(name, birth, breed, gender, personality, sterilized,
                false, chipCode, status, userId);

		mActivity.requestCreateOrUpdateDog(newDog, "¡Felicitaciones! Haz agregado una nueva mascota: ",
				RequestManager.METHOD_POST);

	}
}
