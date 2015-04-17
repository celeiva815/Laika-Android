package cl.laikachile.laika.listeners;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;

import cl.laikachile.laika.activities.AdoptDogFormActivity;
import cl.laikachile.laika.activities.BaseActivity;
import cl.laikachile.laika.utils.Do;

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
