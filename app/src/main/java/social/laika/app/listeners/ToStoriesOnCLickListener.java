package social.laika.app.listeners;

import social.laika.app.activities.StoriesFragmentActivity;
import social.laika.app.utils.Do;

import android.view.View;
import android.view.View.OnClickListener;

public class ToStoriesOnCLickListener implements OnClickListener {

	@Override
	public void onClick(View v) {
		
		Do.changeActivity(v.getContext(), StoriesFragmentActivity.class);
		
	}
	
	

}
