package social.laika.app.listeners;

import android.view.View;
import android.view.View.OnClickListener;

import social.laika.app.utils.Do;

public class ToActivityOnCLickListener implements OnClickListener {

    Class mClass;

    public ToActivityOnCLickListener(Class mClass) {
        this.mClass = mClass;
    }

    @Override
	public void onClick(View v) {
		
		Do.changeActivity(v.getContext(), mClass);
		
	}
	
	

}
