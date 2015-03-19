package cl.laikachile.laika.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import cl.laikachile.laika.R;
import cl.laikachile.laika.models.Dog;
import cl.laikachile.laika.utils.Do;

public class AdoptDogSuccessActivity extends BaseActivity {
	
	private int mIdLayout = R.layout.lk_adopt_dog_success_activity;
	private Dog dog;
	
	 @Override
		public void onStart() {
		 
		 	Bundle b = getIntent().getExtras();
			this.dog = Dog.load(Dog.class, b.getLong("DogId"));

	    	createFragmentView(mIdLayout);
	    	Do.showToast(getMailMessage(), getApplicationContext(), Toast.LENGTH_LONG);
			super.onStart();		
		}
	 
	 @Override
	 public void setActivityView(View view) {
		 
		 LinearLayout containerLinearLayout = (LinearLayout) view.findViewById(R.id.container_adopt_dog_success_linearlayout);
		 TextView congratsTextView = (TextView) view.findViewById(R.id.congrats_adopt_dog_success_textview);
		 TextView happyTextView = (TextView) view.findViewById(R.id.happy_news_adopt_dog_success_textview);
		 
		 containerLinearLayout.setBackgroundResource(this.dog.mImage);
		 congratsTextView.setText(getCongratsMessage());
		 happyTextView.setText(getHappyNewsMessage());
	 }
	 
	 private String getMailMessage() {
		 
		 return getResources().getString(R.string.mail_adopt_dog_success);
		 
	 }
	 
	 private String getCongratsMessage() {
		 
		 //TODO agregar el usuario despu�s con SharedPreferences
		 
		 String name = " Ignacio"; //FIXME que sea el nombre del usuario loggeado
		 String congrats = getResources().getString(R.string.congrats_adopt_dog_success);
		 
		 return congrats + name +"!";
	 }
	 
	 private String getHappyNewsMessage() {
		 
		 String name = dog.mName;
		 String happy = getResources().getString(R.string.happy_news_adopt_dog_success);
		 
		 return name + " " + happy;
	 }
	 

}
