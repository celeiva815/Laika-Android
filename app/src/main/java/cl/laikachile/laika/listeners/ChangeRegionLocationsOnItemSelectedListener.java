package cl.laikachile.laika.listeners;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;

import cl.laikachile.laika.R;
import cl.laikachile.laika.adapters.LocationsAdapter;
import cl.laikachile.laika.models.Location;

public class ChangeRegionLocationsOnItemSelectedListener implements OnItemSelectedListener{

	Location mLocation;
	Spinner mCitySpinner;

	public ChangeRegionLocationsOnItemSelectedListener(Spinner citySpinner, Location location) {

		this.mCitySpinner = citySpinner;
		this.mLocation = location;
	}

	public ChangeRegionLocationsOnItemSelectedListener(Spinner mCitySpinner) {
	
		this.mCitySpinner = mCitySpinner;
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {

		LocationsAdapter locationAdapter = new LocationsAdapter(view.getContext(),
				R.layout.ai_simple_textview_for_adapter, R.id.simple_textview,
				Location.getLocationsByRegions((int) id));

		mCitySpinner.setAdapter(locationAdapter);

		if (mLocation != null) {

			int locationPosition = locationAdapter.getPosition(mLocation);
			mCitySpinner.setSelection(locationPosition);

		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {

	}


	
}
