package social.laika.app.listeners;

import social.laika.app.wasted.GPlusSignInActivity;
import social.laika.app.utils.Do;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;

public class GPlusSignInOnClickListener implements OnClickListener{

	Activity activity; 
	
	public GPlusSignInOnClickListener(Activity activity) {
		super();
		this.activity = activity;
	}

	@Override
	public void onClick(View v) {
		
		Do.changeActivity(v.getContext(), GPlusSignInActivity.class, activity);

	}

}
