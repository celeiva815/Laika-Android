package social.laika.app.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.net.Uri;
import android.util.Log;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.squareup.okhttp.Call;

import java.util.List;

import social.laika.app.R;
import social.laika.app.adapters.ShareIntentAdapter;
import social.laika.app.interfaces.Shareable;

/**
 * Created by lukas on 13-10-15.
 */
public class ShareHelper {

    private static final String TAG = ShareHelper.class.getSimpleName();

    private Activity mContext;
    private Shareable mShareable;

    public ShareHelper(Activity context, Shareable shareable) {
        mContext = context;
        mShareable = shareable;
    }

    public void share() {
        final Resources res = mContext.getResources();
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND)
                .setType("text/plain")
                .putExtra(Intent.EXTRA_TEXT, mShareable.getOtherShareText());
        List<ResolveInfo> activities = mContext.getPackageManager()
                .queryIntentActivities(shareIntent, 0);
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(res.getString(R.string
                .share_string));

        final ShareIntentAdapter adapter = new ShareIntentAdapter(mContext, activities);
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ResolveInfo ri = (ResolveInfo) adapter.getItem(which);

                if (ri.activityInfo.packageName.contains("facebook")) {
                    ShareLinkContent content = new ShareLinkContent.Builder()
                            .setContentUrl(Uri.parse(mShareable.getUrl()))
                            .setContentTitle(mShareable.getFacebookContentTitle())
                            .setContentDescription(mShareable.getFacebookContentDescription())
                            .build();

                    ShareDialog shareDialog = new ShareDialog(mContext);
                    shareDialog.show(content);

                } else {
                    Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                    intent.setClassName(ri.activityInfo.packageName, ri.activityInfo.name);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_TEXT, mShareable.getOtherShareText());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                }
            }
        });
        builder.show();
    }

}
