package social.laika.app.wasted;

import android.view.View;

import social.laika.app.R;
import social.laika.app.activities.MainActivity;

public class FoodActivity extends MainActivity {
	
	private int mIdLayout = R.layout.ai_base_activity;
	
	 @Override
		public void onStart() {

	    	createFragmentView(mIdLayout);
			super.onStart();		
		}
	 
	 @Override
	 public void setActivityView(View view) {
		 
		 // Implementar aqu� la vista
	 }

}
