package social.laika.app.activities;

import android.view.View;

import social.laika.app.R;

public class SelectDogActivity extends BaseActivity {

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
