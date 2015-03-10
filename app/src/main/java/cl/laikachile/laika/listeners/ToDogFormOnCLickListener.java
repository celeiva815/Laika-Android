package cl.laikachile.laika.listeners;

import cl.laikachile.laika.activities.AdoptDogFormActivity;
import cl.laikachile.laika.utils.Do;

import android.view.View;
import android.view.View.OnClickListener;

public class ToDogFormOnCLickListener implements OnClickListener {

	@Override
	public void onClick(View v) {
		
		Do.changeActivity(v.getContext(), AdoptDogFormActivity.class);
		
	}
	
	

}
