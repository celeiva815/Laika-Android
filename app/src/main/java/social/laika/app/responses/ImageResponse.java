package social.laika.app.responses;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import social.laika.app.models.Photo;

/**
 * Created by Tito_Leiva on 13-04-15.
 */
public class ImageResponse implements Response.ErrorListener,
        Response.Listener<Bitmap>  {

    public Context mContext;
    public Activity mActivity;
    public ImageView mImageView;
    public ProgressBar mProgressBar;
    public Photo mPhoto;

    public ImageResponse(Context context, ImageView mImageView) {
        this.mImageView = mImageView;
        this.mContext = context;
    }

    public ImageResponse(Activity mActivity, ImageView mImageView) {
        this.mActivity = mActivity;
        this.mImageView = mImageView;
    }

    public ImageResponse(Activity mActivity, ImageView mImageView, ProgressBar mProgressBar) {
        this.mActivity = mActivity;
        this.mImageView = mImageView;
        this.mProgressBar = mProgressBar;
    }

    public ImageResponse(ImageView mImageView, ProgressBar mProgressBar) {
        this.mImageView = mImageView;
        this.mProgressBar = mProgressBar;
    }

    public ImageResponse(ImageView mImageView, Photo mPhoto, Context mContext) {
        this.mImageView = mImageView;
        this.mPhoto = mPhoto;
        this.mContext = mContext;
    }

    @Override
    public void onResponse(Bitmap response) {

        mImageView.setImageBitmap(response);

        if (mProgressBar != null) {
            mProgressBar.setVisibility(View.GONE);

        }

        if (mPhoto != null) {
            mPhoto.setUri(response, mContext);
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        // mImageView.setImageResource(R.drawable.lk_blanquito_picture);

        if (mProgressBar != null) {
            mProgressBar.setVisibility(View.GONE);
        }
    }

    public void setProgressBar(ProgressBar progressBar) {
        mProgressBar = progressBar;
    }
}