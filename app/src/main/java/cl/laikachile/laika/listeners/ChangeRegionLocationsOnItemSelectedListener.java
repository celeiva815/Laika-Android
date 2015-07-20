package cl.laikachile.laika.listeners;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;

import java.util.List;

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

		List<City> cities = City.getCitiesByRegion((int) id);

		CitiesAdapter citiesAdapter = new CitiesAdapter(view.getContext(),
				R.layout.ai_simple_textview_for_adapter, R.id.simple_textview,
				cities);

		mCitySpinner.setAdapter(citiesAdapter);

		if (mCity != null) {

			int cityPosition = citiesAdapter.getPosition(mCity);
			mCitySpinner.setSelection(cityPosition);

		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {

	}


	
}
