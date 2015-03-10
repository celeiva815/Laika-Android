package cl.laikachile.laika.listeners;

import cl.laikachile.laika.activities.MainActivity;
import cl.laikachile.laika.utils.Do;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;

public class ToMainActivityOnCLickListener implements OnClickListener {

	Activity activity;
	
	public ToMainActivityOnCLickListener(Activity activity) {
		super();
		this.activity = activity;
	}

	@Override
	public void onClick(View v) {
		
		Do.changeActivity(v.getContext(), MainActivity.class, this.activity);
		
	}

}
