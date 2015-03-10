package cl.laikachile.laika.listeners;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import cl.laikachile.laika.activities.MyDogRemindersActivity;
import cl.laikachile.laika.models.Dog;

public class ToMyDogRemaindersOnCLickListener implements OnClickListener {

	Dog dog;
	
	public ToMyDogRemaindersOnCLickListener(Dog dog) {
	
		this.dog = dog;
	}

	@Override
	public void onClick(View v) {
	
		Intent intent = new Intent(v.getContext(), MyDogRemindersActivity.class);
		Bundle b = new Bundle();
		b.putLong("DogId", this.dog.getId()); //Your id
		intent.putExtras(b); //Put your id to your next Intent
		v.getContext().startActivity(intent);
			
	}
	
	

}
