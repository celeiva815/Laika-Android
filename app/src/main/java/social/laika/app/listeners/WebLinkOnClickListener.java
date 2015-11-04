package social.laika.app.listeners;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import social.laika.app.activities.WebActivity;
import social.laika.app.utils.Do;

/**
 * Created by Tito_Leiva on 29-07-15.
 */
public class WebLinkOnClickListener implements View.OnClickListener {

    //FIXME ask for a BasePublication

    public String mUrl;

    public WebLinkOnClickListener(String mUrl) {
        this.mUrl = mUrl;
    }

    @Override
    public void onClick(View v) {

        if (!Do.isNullOrEmpty(mUrl)) {

            Context context = v.getContext();
            Intent intent = new Intent(context, WebActivity.class);
            intent.putExtra(WebActivity.URL, mUrl);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

        } else {

        }
    }
}
