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
import cl.laikachile.laika.network.RequestManager;
import cl.laikachile.laika.responses.ImageResponse;

public class HealthDogActivity extends BaseActivity {
	
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
		 RequestManager.imageRequest(mDog.mUrlImage, profileImageView, response, response);

	 }

}
