package social.laika.app.responses;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import social.laika.app.models.publications.BasePublication;

/**
 * Created by Tito_Leiva on 13-04-15.
 */
public class PublicationImageResponse extends ImageResponse {

    public Context mContext;
    public Activity mActivity;
    public ImageView mImageView;
    public BasePublication mPublication;
    public String mType;

    public PublicationImageResponse(Context context, ImageView mImageView, BasePublication mPublication,
                                    String mType) {
        super(context, mImageView);

        this.mImageView = mImageView;
        this.mPublication = mPublication;
        this.mContext = context;
        this.mType = mType;
    }

    @Override
    public void onResponse(Bitmap response) {

        super.onResponse(response);

        if (mPublication != null) {
            mPublication.setUriLocal(response, mContext, mType);
        }
    }
}