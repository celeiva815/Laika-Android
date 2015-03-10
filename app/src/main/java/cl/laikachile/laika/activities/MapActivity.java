package cl.laikachile.laika.activities;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;

import cl.laikachile.laika.R;

public class MapActivity extends Activity {
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
        
        
		
		LatLng pos2 = new LatLng(-33.4402333,-70.6465263);
		Marker vet2 = googleMap.addMarker(new MarkerOptions()
		.position(pos2)
		.title("Farmacia Veterinaria Dr. Schmidt Herman")
		.snippet("Monjitas 668 Santiago Regi�n Metropolitana")
		.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
		
		LatLng pos3 = new LatLng(-33.4439218,-70.6422348);
		Marker vet3 = googleMap.addMarker(new MarkerOptions()
		.position(pos3)
		.title("Cl�nica Veterinaria")
		.snippet("General Jofre 230 Santiago Centro Regi�n Metropolitana")
		.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
		
		LatLng pos4 = new LatLng(-33.4461599,-70.6379218);
		Marker vet4 = googleMap.addMarker(new MarkerOptions()
		.position(pos4)
		.title("Cl�nica Veterinaria Parque Bustamante")
		.snippet("Joaqu�n D�az Garc�s 11 Providencia Regi�n Metropolitana")
		.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

		
        
    }
}
