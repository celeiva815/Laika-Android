package social.laika.app.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import social.laika.app.activities.WelcomeActivity;

public class SimpleFragment extends Fragment {

	private int mLayout;
	private View mView;
	private WelcomeActivity mActivity;
	
	public SimpleFragment() {
	}
	
	public SimpleFragment(int mLayout, WelcomeActivity mActivity) {
		
		this.mLayout = mLayout;
		this.mActivity = mActivity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
			mView = inflater.inflate(mLayout,container, false);
			mActivity.setActivityView(mView);
			
		return mView;
	}
}
