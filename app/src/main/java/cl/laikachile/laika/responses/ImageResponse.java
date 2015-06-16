package cl.laikachile.laika.responses;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import cl.laikachile.laika.R;

/**
 * Created by Tito_Leiva on 13-04-15.
 */
public class ImageResponse implements Response.ErrorListener,
        Response.Listener<Bitmap>  {

    public Activity mActivity;
    public ImageView mImageView;
    public ProgressBar mProgressBar;

    public ImageResponse(Activity mActivity, ImageView mImageView) {
        this.mActivity = mActivity;
        this.mImageView = mImageView;
    }

    public ImageResponse(Activity mActivity, ImageView mImageView, ProgressBar mProgressBar) {
        this.mActivity = mActivity;
        this.mImageView = mImageView;
        this.mProgressBar = mProgressBar;
    }

    @Override
    public void onResponse(Bitmap response) {

        mImageView.setImageBitmap(response);

        if (mProgressBar != null) {
            mProgressBar.setVisibility(View.GONE);

        }

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        mImageView.setImageResource(R.drawable.lk_blanquito_picture);
    }
}