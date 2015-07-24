package social.laika.app.listeners;

import social.laika.app.activities.PublicationsFragmentActivity;
import social.laika.app.utils.Do;

import android.view.View;
import android.view.View.OnClickListener;

public class ToNewsOnCLickListener implements OnClickListener {

	@Override
	public void onClick(View v) {
		
		Do.changeActivity(v.getContext(), PublicationsFragmentActivity.class);
		
	}
	
	

}
