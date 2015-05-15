package cl.laikachile.laika.listeners;

import android.text.TextUtils;
import android.view.View;

import java.util.Date;

import cl.laikachile.laika.R;
import cl.laikachile.laika.activities.CreateStoryActivity;
import cl.laikachile.laika.models.Story;
import cl.laikachile.laika.utils.Do;
import cl.laikachile.laika.utils.PrefsManager;

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

        String title = mActivity.mTitleEditText.getText().toString();
        String body = mActivity.mBodyEditText.getText().toString();

        if (TextUtils.isEmpty(title)) {
            mActivity.mBodyEditText.setError(mActivity.getString(R.string.field_not_empty_error));
            return;
        }

        if (TextUtils.isEmpty(body)) {
            mActivity.mBodyEditText.setError(mActivity.getString(R.string.field_not_empty_error));
            return;

        }

        String date = Do.today();
        String time = Do.now();

        Story story = new Story(title, date, time, body);
        mActivity.requestStory(story, mActivity.getApplicationContext());

    }
}
