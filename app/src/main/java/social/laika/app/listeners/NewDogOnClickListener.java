package social.laika.app.listeners;

import social.laika.app.R;
import social.laika.app.activities.CreateDogActivity;
import social.laika.app.models.Dog;
import social.laika.app.models.Owner;
import social.laika.app.network.RequestManager;
import social.laika.app.utils.Do;
import social.laika.app.utils.PrefsManager;
import social.laika.app.utils.Tag;

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
