package cl.laikachile.laika.listeners;

import cl.laikachile.laika.R;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

public class ChangeDogBreedsOnItemSelectedListener implements OnItemSelectedListener{

	Spinner breedSpinner;
	
	public ChangeDogBreedsOnItemSelectedListener(Spinner breedSpinner) {
	
		this.breedSpinner = breedSpinner;
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		
		ArrayAdapter<String> breedAdapter = new ArrayAdapter<String>(view.getContext(),
                R.layout.ai_simple_textview_for_adapter, getBreedList(view.getContext(), position));
		breedSpinner.setAdapter(breedAdapter);
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}
	
 private String[] getBreedList(Context context, int position) {
		 
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
		 
		 return context.getResources().getStringArray(resource);
		 
	 }

}
