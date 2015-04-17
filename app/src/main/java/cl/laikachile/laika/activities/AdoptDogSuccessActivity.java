package cl.laikachile.laika.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import cl.laikachile.laika.R;
import cl.laikachile.laika.models.Dog;
import cl.laikachile.laika.utils.Do;
import cl.laikachile.laika.utils.PrefsManager;

public class AdoptDogSuccessActivity extends BaseActivity {
	
	private int mIdLayout = R.layout.lk_adopt_dog_success_activity;
	private Dog dog;
	
	 @Override
		public void onStart() {

            //FIXME aqui hay que enviar la información del match.
		 	Bundle b = getIntent().getExtras();
            int dogId = b.getInt("DogId");
			this.dog = Dog.getSingleDog(dogId);

	    	createFragmentView(mIdLayout);
			super.onStart();
		}
	 
	 @Override
	 public void setActivityView(View view) {
		 
		 LinearLayout containerLinearLayout = (LinearLayout) view.findViewById(R.id.container_adopt_dog_success_linearlayout);
		 TextView congratsTextView = (TextView) view.findViewById(R.id.congrats_adopt_dog_success_textview);
		 TextView happyTextView = (TextView) view.findViewById(R.id.happy_news_adopt_dog_success_textview);
         TextView contactTextView = (TextView) view.findViewById(R.id.contact_news_adopt_dog_success_textview);
         TextView matchTextView = (TextView) view.findViewById(R.id.match_adopt_dog_success_textview);
         ImageView imageTextView = (ImageView) view.findViewById(R.id.picture_adopt_dog_success_imageview);
		 
		 congratsTextView.setText(getCongratsMessage());
		 happyTextView.setText(getHappyNewsMessage());
         contactTextView.setText(getContactMessage());
         matchTextView.setText(Integer.toString(Do.randomInteger(50,100)) + "%"); //FIXME que sea el que viene desde el servidor.
         imageTextView.setImageResource(this.dog.mImage);
	 }

    private String getContactMessage() {

        String first = Do.getRString(getApplicationContext(), R.string.contact_news_adopt_dog_success_first);
        String foundation = "Fundación Stuka"; //FIXME agregar la fundación
        String second = Do.getRString(getApplicationContext(), R.string.contact_news_adopt_dog_success_second);

        return first + " " +  foundation + " " + second;
    }

    private String getCongratsMessage() {
		 
		 String name = PrefsManager.getUserName(getApplicationContext());
		 String congrats = Do.getRString(getApplicationContext(), R.string.congrats_adopt_dog_success);
		 
		 return congrats + " " + name + "!";
	 }
	 
	 private String getHappyNewsMessage() {
		 
		 String name = dog.mName;
		 String happy = getResources().getString(R.string.happy_news_adopt_dog_success);
		 
		 return name + " " + happy;
	 }
	 

}
