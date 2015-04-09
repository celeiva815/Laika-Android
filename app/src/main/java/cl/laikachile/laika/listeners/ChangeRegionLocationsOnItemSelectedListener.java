package cl.laikachile.laika.listeners;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;

import cl.laikachile.laika.R;
import cl.laikachile.laika.adapters.LocationsAdapter;
import cl.laikachile.laika.models.Location;

public class ChangeRegionLocationsOnItemSelectedListener implements OnItemSelectedListener{

	Spinner mLocationSpinner;

	public ChangeRegionLocationsOnItemSelectedListener(Spinner mLocationSpinner) {
	
		this.mLocationSpinner = mLocationSpinner;
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {

        String region = (String) parent.getItemAtPosition(position);

		LocationsAdapter locationAdapter = new LocationsAdapter(view.getContext(),
                R.layout.ai_simple_textview_for_adapter, Location.getLocation(region));
		mLocationSpinner.setAdapter(locationAdapter);
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {

	}
	
}
