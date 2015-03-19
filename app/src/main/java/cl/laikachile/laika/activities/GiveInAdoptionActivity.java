package cl.laikachile.laika.activities;

import java.util.ArrayList;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import cl.laikachile.laika.R;
import cl.laikachile.laika.listeners.ChangeDogBreedsOnItemSelectedListener;
import cl.laikachile.laika.utils.Do;

public class GiveInAdoptionActivity extends BaseActivity {
	
	public EditText nameEditText;
	public DatePicker birthDatePicker;
	public Spinner sizeSpinner;
	public Spinner breedSpinner;
	public Spinner personalitySpinner;
	public EditText storyEditText;
	public Button publishButton;
	public String gender;

	private int mIdLayout = R.layout.lk_give_in_adoption_activity;

	@Override
	public void onStart() {

		createFragmentView(mIdLayout);
		super.onStart();		
	}

	@Override
	public void setActivityView(View view) {

		nameEditText = (EditText) view.findViewById(R.id.name_give_in_adoption_edittext);
		birthDatePicker = (DatePicker) view.findViewById(R.id.birth_give_in_adoption_datepicker);
		sizeSpinner = (Spinner) view.findViewById(R.id.size_give_in_adoption_spinner);
		breedSpinner = (Spinner) view.findViewById(R.id.type_give_in_adoption_spinner);
		personalitySpinner = (Spinner) view.findViewById(R.id.personality_give_in_adoption_spinner);
		storyEditText = (EditText) view.findViewById(R.id.story_give_in_adoption_edittext);
		publishButton = (Button) view.findViewById(R.id.publish_give_in_adoption_button);

		ArrayAdapter<String> sizeAdapter = new ArrayAdapter<String>(this.getApplicationContext(),R.layout.ai_simple_textview_for_adapter, getSizeList());
		ArrayAdapter<String> breedAdapter = new ArrayAdapter<String>(this.getApplicationContext(),R.layout.ai_simple_textview_for_adapter, getBreedList(sizeSpinner));
		ArrayAdapter<String> personalityAdapter = new ArrayAdapter<String>(this.getApplicationContext(),R.layout.ai_simple_textview_for_adapter, this.getResources().getStringArray(R.array.personality_adopt));
		ChangeDogBreedsOnItemSelectedListener breedListener = new ChangeDogBreedsOnItemSelectedListener(breedSpinner);

		sizeSpinner.setAdapter(sizeAdapter);
		breedSpinner.setAdapter(breedAdapter);
		personalitySpinner.setAdapter(personalityAdapter);
		sizeSpinner.setOnItemSelectedListener(breedListener);

	}
	
	private ArrayList<String> getSizeList() {
		 
		 ArrayList<String> sizeList = new ArrayList<String>();
		 sizeList.add(Do.getRString(getApplicationContext(), R.string.smaller_new_dog_register));
		 sizeList.add(Do.getRString(getApplicationContext(), R.string.small_new_dog_register));
		 sizeList.add(Do.getRString(getApplicationContext(), R.string.middle_new_dog_register));
		 sizeList.add(Do.getRString(getApplicationContext(), R.string.big_new_dog_register));
		 sizeList.add(Do.getRString(getApplicationContext(), R.string.bigger_new_dog_register));
		 
		 return sizeList;		 
	 }
	
	 private String[] getBreedList(Spinner sizeSpinner) {
		 
		 int position = sizeSpinner.getSelectedItemPosition();
		 int resource;
		 
		 switch (position) {
		case 0:
			
			resource = R.array.smaller_breed;
			
			break;
		case 1:
			resource = R.array.small_breed;
					
			break;
		case 2:
			resource = R.array.middle_breed;
			
			break;
		case 3:
			
			resource = R.array.big_breed;
			break;
		case 4:
			
			resource = R.array.bigger_breed;
			break;
		default:
			resource = R.array.smaller_breed;
			break;
		}
		 
		 return getResources().getStringArray(resource);
		 
	 }
	 
	 public void onRadioButtonClicked(View view) {
		    // Is the button now checked?
		    boolean checked = ((RadioButton) view).isChecked();
		    this.gender = "";
		    
		    // Check which radio button was clicked
		    switch(view.getId()) {
		        case R.id.male_give_in_adoption_radiobutton:
		            if (checked)
		            	gender = "Macho";

		            break;
		        case R.id.female_give_in_adoption_radiobutton:
		            if (checked)
		                gender = "Hembra";
		            	
		            break;
		    }
		}

}
