package social.laika.app.listeners;

import social.laika.app.activities.MapActivity;
import social.laika.app.utils.Do;

import android.view.View;
import android.view.View.OnClickListener;

public class ToMapOnClickListener implements OnClickListener {

	@Override
	public void onClick(View v) {
		
		Do.changeActivity(v.getContext(), MapActivity.class);
		
	}
	
	

}
