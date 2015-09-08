package social.laika.app.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import social.laika.app.R;
import social.laika.app.listeners.ToMapHairOnClickListener;
import social.laika.app.listeners.ToMapOnClickListener;
import social.laika.app.models.Dog;
import social.laika.app.network.Api;
import social.laika.app.responses.ImageResponse;

public class HealthDogActivity extends MainActivity {
	
	private int mIdLayout;
	private Dog mDog;
	
	 @Override
		public void onStart() {
		 	
			Bundle b = getIntent().getExtras();
			this.mDog = Dog.load(Dog.class, b.getLong("DogId"));
		 
	    	createFragmentView(mIdLayout);
			super.onStart();		
		}
	 
	 @Override
	 public void setActivityView(View view) {
		 
		 TextView nameTextView = (TextView) view.findViewById(R.id.name_my_dog_health_textview);
		 ImageView profileImageView = (ImageView) view.findViewById(R.id.profile_my_dog_health_imageview);
		 Button searchButton = (Button) view.findViewById(R.id.postulate_adopt_dog_button);
		 Button vaccineButton = (Button) view.findViewById(R.id.vaccine_my_dog_health_button);
		 Button barberButton = (Button) view.findViewById(R.id.barber_shop_my_dog_health_button);
		 Button dealsButton = (Button) view.findViewById(R.id.deals_my_dog_health_button);
		 
		 nameTextView.setText(mDog.mName);
	     ToMapOnClickListener mapListener = new ToMapOnClickListener();
	     ToMapHairOnClickListener mapHairListener = new ToMapHairOnClickListener();
	     searchButton.setOnClickListener(mapListener);
	     barberButton.setOnClickListener(mapHairListener);

		 ImageResponse response = new ImageResponse(this, profileImageView);
		 Api.imageRequest(mDog.mUrlImage, profileImageView, response, response);

	 }

}
