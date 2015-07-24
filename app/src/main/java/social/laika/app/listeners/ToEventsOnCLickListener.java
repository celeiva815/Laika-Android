package social.laika.app.listeners;

import social.laika.app.activities.EventsActivity;
import social.laika.app.utils.Do;

import android.view.View;
import android.view.View.OnClickListener;

public class ToEventsOnCLickListener implements OnClickListener {

	@Override
	public void onClick(View v) {
		
		Do.changeActivity(v.getContext(), EventsActivity.class);
		
	}
	
	

}
