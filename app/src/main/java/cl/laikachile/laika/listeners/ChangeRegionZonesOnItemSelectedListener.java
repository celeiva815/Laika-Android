package cl.laikachile.laika.listeners;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import cl.laikachile.laika.R;
import cl.laikachile.laika.adapters.ZonesAdapter;
import cl.laikachile.laika.models.Zone;

public class ChangeRegionZonesOnItemSelectedListener implements OnItemSelectedListener{

	Spinner mZoneSpinner;

	public ChangeRegionZonesOnItemSelectedListener(Spinner mZoneSpinner) {
	
		this.mZoneSpinner = mZoneSpinner;
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {

        String region = (String) parent.getItemAtPosition(position);

		ZonesAdapter zoneAdapter = new ZonesAdapter(view.getContext(),
                R.layout.ai_simple_textview_for_adapter, Zone.getZones(region));
		mZoneSpinner.setAdapter(zoneAdapter);
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {

	}
	
}
