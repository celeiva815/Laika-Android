package cl.laikachile.laika.responses;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import cl.laikachile.laika.R;
import cl.laikachile.laika.activities.LoginActivity;
import cl.laikachile.laika.network.utils.ResponseHandler;

/**
 * Created by Tito_Leiva on 13-04-15.
 */
public class ImageResponse implements Response.ErrorListener,
        Response.Listener<Bitmap>  {

    public Activity mActivity;
    public ImageView mImageView;

    public ImageResponse(Activity mActivity, ImageView mImageView) {
        this.mActivity = mActivity;
        this.mImageView = mImageView;
    }

    @Override
    public void onResponse(Bitmap response) {
        mImageView.setImageBitmap(response);

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        mImageView.setImageResource(R.drawable.lk_blanquito_picture);
    }
}