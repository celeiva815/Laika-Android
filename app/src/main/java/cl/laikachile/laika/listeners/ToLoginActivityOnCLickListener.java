package cl.laikachile.laika.listeners;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;

import cl.laikachile.laika.activities.LoginActivity;
import cl.laikachile.laika.activities.MainActivity;
import cl.laikachile.laika.activities.RegisterActivity;
import cl.laikachile.laika.utils.Do;

public class ToLoginActivityOnCLickListener implements OnClickListener {

	Activity activity;

	public ToLoginActivityOnCLickListener(Activity activity) {
		super();
		this.activity = activity;
	}

	@Override
	public void onClick(View v) {
		
		Do.changeActivity(v.getContext(), LoginActivity.class, this.activity);
		
	}

}
