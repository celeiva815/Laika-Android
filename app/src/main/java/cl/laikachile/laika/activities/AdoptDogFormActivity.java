package cl.laikachile.laika.activities;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import cl.laikachile.laika.R;
import cl.laikachile.laika.listeners.SearchDogsToAdoptOnClickListener;

public class AdoptDogFormActivity extends BaseActivity {
	
	private int mIdLayout = R.layout.lk_adopt_dog_form_activity;
	
	 @Override
		public void onStart() {

	    	createFragmentView(mIdLayout);
			super.onStart();		
		}
	 
	 @Override
	 public void setActivityView(View view) {
		 
		 Button searchButton = (Button) view.findViewById(R.id.search_dog_form_button);
		 Spinner spaceSpinner = (Spinner) view.findViewById(R.id.space_dog_form_spinner);
		 Spinner freeTimeSpinner = (Spinner) view.findViewById(R.id.free_time_dog_form_spinner);
		 Spinner partnersSpinner = (Spinner) view.findViewById(R.id.partners_dog_form_spinner);
		 Spinner sizeSpinner = (Spinner) view.findViewById(R.id.size_dog_form_spinner);
		 Spinner personalitySpinner = (Spinner) view.findViewById(R.id.personality_dog_form_spinner);
		 
		 SearchDogsToAdoptOnClickListener listener = new SearchDogsToAdoptOnClickListener();
		 ArrayAdapter<String> spaceAdapter = new ArrayAdapter<String>(this.getApplicationContext(),R.layout.ai_simple_textview_for_adapter,this.getResources().getStringArray(R.array.home_size_adopt));
		 ArrayAdapter<String> freeTimeAdapter = new ArrayAdapter<String>(this.getApplicationContext(),R.layout.ai_simple_textview_for_adapter, this.getResources().getStringArray(R.array.free_time_adopt));
		 ArrayAdapter<String> partnersAdapter = new ArrayAdapter<String>(this.getApplicationContext(),R.layout.ai_simple_textview_for_adapter, this.getResources().getStringArray(R.array.partners_adopt));
		 ArrayAdapter<String> sizeAdapter = new ArrayAdapter<String>(this.getApplicationContext(),R.layout.ai_simple_textview_for_adapter, this.getResources().getStringArray(R.array.dog_size_adopt));
		 ArrayAdapter<String> personalityAdapter = new ArrayAdapter<String>(this.getApplicationContext(),R.layout.ai_simple_textview_for_adapter, this.getResources().getStringArray(R.array.personality_adopt));
		
		 searchButton.setOnClickListener(listener);
		 spaceSpinner.setAdapter(spaceAdapter);
		 freeTimeSpinner.setAdapter(freeTimeAdapter);
		 partnersSpinner.setAdapter(partnersAdapter);
		 sizeSpinner.setAdapter(sizeAdapter);
		 personalitySpinner.setAdapter(personalityAdapter);
		 
	 }
	 
	 


}
