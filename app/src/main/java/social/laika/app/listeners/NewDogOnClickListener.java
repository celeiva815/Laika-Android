package social.laika.app.listeners;

import social.laika.app.activities.CreateDogActivity;

import android.view.View;
import android.view.View.OnClickListener;

public class NewDogOnClickListener implements OnClickListener {

    CreateDogActivity mActivity;

    public NewDogOnClickListener(CreateDogActivity mActivity) {

        this.mActivity = mActivity;
    }

    @Override
    public void onClick(View v) {

        mActivity.request();

    }
}
