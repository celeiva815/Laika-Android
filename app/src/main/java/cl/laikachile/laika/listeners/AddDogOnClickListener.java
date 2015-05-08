package cl.laikachile.laika.listeners;

import cl.laikachile.laika.activities.MainActivity;
import cl.laikachile.laika.activities.NewDogRegisterActivity;
import cl.laikachile.laika.models.Dog;
import cl.laikachile.laika.utils.Do;
import cl.laikachile.laika.utils.PrefsManager;
import cl.laikachile.laika.utils.Tag;

import android.view.View;
import android.view.View.OnClickListener;

public class AddDogOnClickListener implements OnClickListener {

	NewDogRegisterActivity mActivity;
	
	public AddDogOnClickListener(NewDogRegisterActivity mActivity) {
		
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
		
		Dog newDog = new Dog(Dog.ID++, name, birth, breed, gender, personality, sterilized,
                false, chipCode, status, userId);

		newDog.save();
		
		Do.changeActivity(v.getContext(), MainActivity.class, mActivity);
		Do.showToast("Felicitaciones! haz registrado una nueva mascota: "+ name, v.getContext());
	}

}
