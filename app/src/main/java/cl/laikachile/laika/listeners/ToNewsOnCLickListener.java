package cl.laikachile.laika.listeners;

import cl.laikachile.laika.activities.PublicationsFragmentActivity;
import cl.laikachile.laika.utils.Do;

import android.view.View;
import android.view.View.OnClickListener;

public class ToNewsOnCLickListener implements OnClickListener {

	@Override
	public void onClick(View v) {
		
		Do.changeActivity(v.getContext(), PublicationsFragmentActivity.class);
		
	}
	
	

}
