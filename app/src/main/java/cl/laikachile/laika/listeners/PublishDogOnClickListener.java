package cl.laikachile.laika.listeners;

import cl.laikachile.laika.activities.GiveInAdoptionActivity;
import cl.laikachile.laika.activities.MainActivity;
import cl.laikachile.laika.models.Dog;
import cl.laikachile.laika.utils.Do;

import android.view.View;
import android.view.View.OnClickListener;

public class PublishDogOnClickListener implements OnClickListener {

	GiveInAdoptionActivity giveInAdoptionActivity;
	
	public PublishDogOnClickListener(GiveInAdoptionActivity giveInAdoptionActivity) {
		
		this.giveInAdoptionActivity = giveInAdoptionActivity; 
	}

	@Override
	public void onClick(View v) {
		
		//FIXME crear los valores posibles
		
		int space = 0;
		String name = giveInAdoptionActivity.nameEditText.getText().toString();		
	    String birth = Do.getToStringDate(giveInAdoptionActivity.birthDatePicker.getDayOfMonth(), 
	    								  giveInAdoptionActivity.birthDatePicker.getMonth(), 
	    								  giveInAdoptionActivity.birthDatePicker.getYear());
		int type = 0;
		String breed = giveInAdoptionActivity.breedSpinner.getSelectedItem().toString();
		int freeTime = 0;
		int partner = 0;
		String gender = giveInAdoptionActivity.gender;
		String size = giveInAdoptionActivity.sizeSpinner.getSelectedItem().toString();
		String personality = giveInAdoptionActivity.personalitySpinner.getSelectedItem().toString();
		int status = Dog.STATUS_PUBLISH;
		int userId = 0;
		
		Dog newDog = new Dog(Do.randomInteger(100, 1000), space, name, birth, type, breed, freeTime, partner, gender, size, personality, status, userId, 100);//FIXME percentage
		newDog.save();
		
		Do.changeActivity(v.getContext(), MainActivity.class, giveInAdoptionActivity);
		Do.showToast("Haz publicado una nueva mascota para adopci�n: "+ name, v.getContext());
	}

}
