package cl.laikachile.laika.responses;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import cl.laikachile.laika.models.Dog;
import cl.laikachile.laika.models.Photo;
import cl.laikachile.laika.models.Story;
import cl.laikachile.laika.network.RequestManager;
import cl.laikachile.laika.network.VolleyManager;
import cl.laikachile.laika.utils.Do;

/**
 * Created by Tito_Leiva on 13-04-15.
 */
public class ImageUploadResponse implements Response.ErrorListener,
        Response.Listener<JSONObject> {

    public static final int TYPE_ALBUM = 1;
    public static final int TYPE_PROFILE = 2;
    public static final int TYPE_STORY = 3;
    public static final String API_URL = "url";
    public static final String API_TIME = "time";
    public static final String API_DATE = "date";

    public Dog mDog;
    public Story mStory;
    public Context mContext;
    public ImageView mImageView;
    public ProgressBar mProgressBar;
    public ProgressDialog mProgressDialog;
    public int mType;

    public ImageUploadResponse(Dog dog, Context mContext, ImageView mImageView,
                               ProgressBar mProgressBar, int mType) {

        this.mDog = dog;
        this.mContext = mContext;
        this.mImageView = mImageView;
        this.mProgressBar = mProgressBar;
        this.mType = mType;
    }

    public ImageUploadResponse(Story story, ProgressDialog mProgressDialog, Context context) {

        this.mContext = context;
        this.mStory = story;
        this.mProgressDialog = mProgressDialog;
        this.mType = TYPE_STORY;

    }

    @Override
    public void onResponse(JSONObject response) {

        switch (mType) {

            case TYPE_ALBUM:

                break;

            case TYPE_PROFILE:

                break;

            case TYPE_STORY:

                storyPhoto(response);

                break;
        }

    }

    @Override
    public void onErrorResponse(VolleyError error) {

        ResponseHandler.error(error, mContext);
        cancelProgress();


    }

    public void requestImage(String imageUrl) {

        ImageResponse imageResponse = new ImageResponse(mImageView, mProgressBar);
        Request imageRequest = RequestManager.imageRequest(imageUrl, mImageView,
                imageResponse, imageResponse);

        VolleyManager.getInstance(mContext).addToRequestQueue(imageRequest);

    }

    public void albumPhoto(JSONObject response) {

        Photo photo = Photo.saveDogPhoto(response, mContext, mDog);


    }

    public void profilePhoto(JSONObject response) {

    }

    public void storyPhoto(JSONObject response) {

        Photo photo = Photo.getPhoto(response, mContext);
        String url = photo.mUrlImage;

        if (!Do.isNullOrEmpty(url)) {
            mStory.mUrlImage = url;

        }

        cancelProgress();

    }

    public void cancelProgress() {

        if (mProgressBar != null) {

            mProgressBar.setVisibility(View.GONE);

        }

        if (mProgressDialog != null) {

            mProgressDialog.dismiss();

        }

    }


}