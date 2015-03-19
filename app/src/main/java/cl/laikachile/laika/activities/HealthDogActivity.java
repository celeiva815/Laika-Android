package cl.laikachile.laika.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import cl.laikachile.laika.R;
import cl.laikachile.laika.listeners.ToMapHairOnClickListener;
import cl.laikachile.laika.listeners.ToMapOnClickListener;
import cl.laikachile.laika.models.Dog;

public class HealthDogActivity extends BaseActivity {
	
	private int mIdLayout = R.layout.lk_my_dog_health_activity;
	private Dog dog;
	
	 @Override
		public void onStart() {
		 	
			Bundle b = getIntent().getExtras();
			this.dog = Dog.load(Dog.class, b.getLong("DogId"));
		 
	    	createFragmentView(mIdLayout);
			super.onStart();		
		}
	 
	 @Override
	 public void setActivityView(View view) {
		 
		 TextView nameTextView = (TextView) view.findViewById(R.id.name_my_dog_health_textview);
		 ImageView profileImageView = (ImageView) view.findViewById(R.id.profile_my_dog_health_imageview);
		 Button searchButton = (Button) view.findViewById(R.id.search_vet_my_dog_health_button);
		 Button vaccineButton = (Button) view.findViewById(R.id.vaccine_my_dog_health_button);
		 Button barberButton = (Button) view.findViewById(R.id.barber_shop_my_dog_health_button);
		 Button dealsButton = (Button) view.findViewById(R.id.deals_my_dog_health_button);
		 
		 nameTextView.setText(dog.mName);
	     profileImageView.setImageResource(dog.mImage);
	     ToMapOnClickListener mapListener = new ToMapOnClickListener();
	     ToMapHairOnClickListener mapHairListener = new ToMapHairOnClickListener();
	     searchButton.setOnClickListener(mapListener);
	     barberButton.setOnClickListener(mapHairListener);
		 
	 }

}
