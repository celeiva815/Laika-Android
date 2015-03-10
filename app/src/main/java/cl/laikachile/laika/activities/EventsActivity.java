package cl.laikachile.laika.activities;

import android.content.Context;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import cl.laikachile.laika.R;
import cl.laikachile.laika.adapters.EventsAdapter;
import cl.laikachile.laika.models.Event;

public class EventsActivity extends BaseActivity {
	
	private int mIdLayout = R.layout.lk_events_activity;
	
	 @Override
		public void onStart() {

	    	createFragmentView(mIdLayout);
			super.onStart();		
		}
	 
	 @Override
	 public void setActivityView(View view) {
		 
		 ListView eventsListView = (ListView) view.findViewById(R.id.events_listview);
		 EventsAdapter adapter = new EventsAdapter(getApplicationContext(), R.layout.lk_events_adapter, getEvents(getApplicationContext()));
		 
		 eventsListView.setAdapter(adapter);
		 
	 }
	 
	 private List<Event> getEvents(Context context) {
		 
		 String[] events = context.getResources().getStringArray(R.array.example_tips);
		 List<Event> eventList = new ArrayList<Event>(events.length);
		 
		Event vaccine = new Event("Vacunaci�n","11/03/2015 08:00","12/03/2015 20:00",R.drawable.lk_event_municipalidad_de_stgo_logo);
		Event dogRunning = new Event("Corrida Dog","11/03/2015 08:00","12/03/2015 20:00",R.drawable.lk_event_dog_chow_logo);
		Event adoptions = new Event("Adopciones","11/03/2015 08:00","12/03/2015 20:00",R.drawable.lk_event_pedigree_logo);
		Event winterClothes = new Event("Venta Ropa Invierno","11/03/2015 08:00","12/03/2015 20:00",R.drawable.lk_event_dr_pet_logo);
		
		eventList.add(vaccine);
		eventList.add(dogRunning);
		eventList.add(adoptions);
		eventList.add(winterClothes);
		 
		 return eventList;
	 }

}
