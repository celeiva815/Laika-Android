package cl.laikachile.laika.responses;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import cl.laikachile.laika.R;
import cl.laikachile.laika.models.Dog;
import cl.laikachile.laika.models.Photo;
import cl.laikachile.laika.network.RequestManager;
import cl.laikachile.laika.network.VolleyManager;

/**
 * Created by Tito_Leiva on 13-04-15.
 */
public class PostImageResponse implements Response.ErrorListener,
        Response.Listener<JSONObject> {

    public static final int TYPE_PROFILE = 1;
    public static final int TYPE_ALBUM = 2;
    public static final String API_URL = "url";

    public Dog mDog;
    public Context mContext;
    public ImageView mImageView;
    public ProgressBar mProgressBar;
    public int mType;

    public PostImageResponse(Dog mDog, Context mContext, ImageView mImageView,
                             ProgressBar mProgressBar, int mType) {
        this.mDog = mDog;
        this.mContext = mContext;
        this.mImageView = mImageView;
        this.mProgressBar = mProgressBar;
        this.mType = mType;
    }

    @Override
    public void onResponse(JSONObject response) {

        int dogId = response.optInt(Dog.COLUMN_DOG_ID, 0);
        String imageUrl = "";
        Photo photo = new Photo();

        if (mDog.mDogId == dogId) {

            imageUrl = response.optString(API_URL);
            photo = Photo.savePhoto(response, mContext, mDog);

            switch (mType) {

                case TYPE_PROFILE:
                    mDog.mUrlImage = imageUrl;
                    break;

            }

            requestImage(imageUrl);
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {

        ResponseHandler.error(error, mContext);
    }

    public void requestImage(String imageUrl) {

        ImageResponse imageResponse = new ImageResponse(mImageView, mProgressBar);
        Request imageRequest = RequestManager.imageRequest(imageUrl, mImageView,
                imageResponse, imageResponse);

        VolleyManager.getInstance(mContext).addToRequestQueue(imageRequest);

    }
}