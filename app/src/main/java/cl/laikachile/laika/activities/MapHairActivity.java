package cl.laikachile.laika.activities;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import cl.laikachile.laika.R;

public class MapHairActivity extends Activity {
	private GoogleMap googleMap;
	private LatLng myPosition;
	private Context ctxt;
	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lk_map);
        ctxt = this;
        googleMap = ((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();
        googleMap.setMyLocationEnabled(true);
        LatLng loc = new LatLng(-33.439248, -70.63978);
       // googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 16.0f));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 16.0f));
		
        LatLng pos = new LatLng(-33.4396245,-70.6421919);
		Marker vet1 = googleMap.addMarker(new MarkerOptions()
		.position(pos)
		.title("Peluqueria Canina & Pet shop")
		.snippet("Avda. Portugal 87 Remodelacion San Borja Torre 8 Santigo 8320000 Regi�n Metropolitana")
		.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

		
        
    }
}
