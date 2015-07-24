package social.laika.app.listeners;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import social.laika.app.activities.HealthDogActivity;
import social.laika.app.models.Dog;

public class ToMyDogHealthOnCLickListener implements OnClickListener {

	Dog dog;
	
	public ToMyDogHealthOnCLickListener(Dog dog) {
	
		this.dog = dog;
	}

	@Override
	public void onClick(View v) {
	
		Intent intent = new Intent(v.getContext(), HealthDogActivity.class);
		Bundle b = new Bundle();
		b.putLong("DogId", this.dog.getId()); //Your id
		intent.putExtras(b); //Put your id to your next Intent
		v.getContext().startActivity(intent);
			
	}
	
	

}
