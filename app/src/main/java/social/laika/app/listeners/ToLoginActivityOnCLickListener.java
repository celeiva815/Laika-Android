package social.laika.app.listeners;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;

import social.laika.app.activities.LoginActivity;
import social.laika.app.utils.Do;

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
