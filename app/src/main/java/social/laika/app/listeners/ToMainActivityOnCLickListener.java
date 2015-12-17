package social.laika.app.listeners;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;

import social.laika.app.activities.HomeActivity;
import social.laika.app.utils.Do;

public class ToMainActivityOnCLickListener implements OnClickListener {

    Activity activity;

    public ToMainActivityOnCLickListener(Activity activity) {
        super();
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {

        Do.changeActivity(v.getContext(), HomeActivity.class, this.activity);

    }

}
