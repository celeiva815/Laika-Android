package cl.laikachile.laika.listeners;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
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

        Do.hideKeyboard(mActivity);
        mActivity.sendStory();



    }
}
