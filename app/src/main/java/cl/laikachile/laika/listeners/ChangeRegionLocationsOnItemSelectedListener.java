package cl.laikachile.laika.listeners;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;

import cl.laikachile.laika.R;
import cl.laikachile.laika.adapters.CitiesAdapter;
import cl.laikachile.laika.models.City;

public class ChangeRegionLocationsOnItemSelectedListener implements OnItemSelectedListener{

	City mCity;
	Spinner mCitySpinner;

	public ChangeRegionLocationsOnItemSelectedListener(Spinner citySpinner, City city) {

		this.mCitySpinner = citySpinner;
		this.mCity = city;
	}

	public ChangeRegionLocationsOnItemSelectedListener(Spinner mCitySpinner) {
	
		this.mCitySpinner = mCitySpinner;
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {

		CitiesAdapter locationAdapter = new CitiesAdapter(view.getContext(),
				R.layout.ai_simple_textview_for_adapter, R.id.simple_textview,
				City.getCitiesByRegion((int) id));

		mCitySpinner.setAdapter(locationAdapter);

		if (mCity != null) {

			int locationPosition = locationAdapter.getPosition(mCity);
			mCitySpinner.setSelection(locationPosition);

		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {

	}


	
}
