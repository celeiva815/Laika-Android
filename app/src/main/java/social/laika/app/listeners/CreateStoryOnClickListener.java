package social.laika.app.listeners;

import android.view.View;

import social.laika.app.activities.CreateStoryActivity;
import social.laika.app.utils.Do;

/**
 * Created by Tito_Leiva on 15-05-15.
 */
public class CreateStoryOnClickListener implements View.OnClickListener {

    public CreateStoryActivity mActivity;

    public CreateStoryOnClickListener(CreateStoryActivity mActivity) {
        this.mActivity = mActivity;
    }

    @Override
    public void onClick(View v) {

        Do.hideKeyboard(mActivity);
        mActivity.sendStory();



    }
}
