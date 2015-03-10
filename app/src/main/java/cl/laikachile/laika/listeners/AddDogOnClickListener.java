package cl.laikachile.laika.listeners;

import cl.laikachile.laika.activities.MainActivity;
import cl.laikachile.laika.activities.NewDogRegisterActivity;
import cl.laikachile.laika.models.Dog;
import cl.laikachile.laika.utils.Do;

import android.view.View;
import android.view.View.OnClickListener;

public class AddDogOnClickListener implements OnClickListener {

	NewDogRegisterActivity newDogRegisterActivity;
	
	public AddDogOnClickListener(NewDogRegisterActivity newDogRegisterActivity) {
		
		this.newDogRegisterActivity = newDogRegisterActivity; 
	}

	@Override
	public void onClick(View v) {
		
		//FIXME crear los valores posibles
		
		int space = 0;
		String name = newDogRegisterActivity.nameEditText.getText().toString();		
	    String birth = Do.getToStringDate(newDogRegisterActivity.birthDatePicker.getDayOfMonth(), 
	    								  newDogRegisterActivity.birthDatePicker.getMonth(), 
	    								  newDogRegisterActivity.birthDatePicker.getYear());
		int type = 0;
		String breed = newDogRegisterActivity.breedSpinner.getSelectedItem().toString();
		int freeTime = 0;
		int partner = 0;
		String gender = newDogRegisterActivity.gender;
		String size = newDogRegisterActivity.sizeSpinner.getSelectedItem().toString();
		String personality = newDogRegisterActivity.personalitySpinner.getSelectedItem().toString();;
		int status = Dog.STATUS_OWN;
		int userId = 0;
		
		Dog newDog = new Dog(Do.randomInteger(100, 1000), space, name, birth, type, breed, freeTime, partner, gender, size, personality, status, userId, 100);//FIXME percentage
		newDog.save();
		
		Do.changeActivity(v.getContext(), MainActivity.class, newDogRegisterActivity);
		Do.showToast("Felicitaciones! haz registrado una nueva mascota: "+ name, v.getContext());
	}

}
