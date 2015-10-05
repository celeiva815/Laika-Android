package social.laika.app.responses;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import social.laika.app.interfaces.Picturable;
import social.laika.app.models.publications.BasePublication;

/**
 * Created by Tito_Leiva on 13-04-15.
 */
public class LocalImageSaverResponse extends ImageResponse {

    public Context mContext;
    public Activity mActivity;
    public ImageView mImageView;
    public Picturable mPicturable;
    public String mType;

    public LocalImageSaverResponse(Context context, ImageView mImageView, Picturable mPicturable,
                                   String mType) {
        super(context, mImageView);

        this.mImageView = mImageView;
        this.mPicturable= mPicturable;
        this.mContext = context;
        this.mType = mType;
    }

    @Override
    public void onResponse(Bitmap response) {

        super.onResponse(response);

        if (mPicturable != null) {
            mPicturable.setUriLocal(response, mContext, mType);
        }
    }
}