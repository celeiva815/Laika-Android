package social.laika.app.responses;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import social.laika.app.interfaces.Photographable;
import social.laika.app.models.Dog;
import social.laika.app.models.Photo;
import social.laika.app.models.Story;
import social.laika.app.network.RequestManager;
import social.laika.app.network.VolleyManager;

/**
 * Created by Tito_Leiva on 13-04-15.
 */
public class ImageUploadResponse implements Response.ErrorListener,
        Response.Listener<JSONObject> {

    public static final String API_URL = "url";
    public static final String API_IS_PROFILE = "is_profile";
    public static final String API_TIME = "time";
    public static final String API_DATE = "date";

    public Dog mDog;
    public Context mContext;
    public ImageView mImageView;
    public ProgressBar mProgressBar;
    public ProgressDialog mProgressDialog;
    public Photographable mActivity;

    public ImageUploadResponse(Dog dog, Photographable mActivity, Context mContext) {

        this.mDog = dog;
        this.mContext = mContext;
        this.mActivity = mActivity;
    }

    @Override
    public void onResponse(JSONObject response) {

        Photo photo = Photo.saveDogPhoto(response, mContext, mDog);

        if (response.has(API_IS_PROFILE) && response.optBoolean(API_IS_PROFILE)) {

            mDog.mUrlImage = photo.mUrlMedium;

        }
        mActivity.succeedUpload();
        cancelProgress();

    }

    @Override
    public void onErrorResponse(VolleyError error) {

        RequestManager.error(error, mContext);
        cancelProgress();
        mActivity.failedUpload();

    }

    public void requestImage(String imageUrl) {

        ImageResponse imageResponse = new ImageResponse(mImageView, mProgressBar);
        Request imageRequest = RequestManager.imageRequest(imageUrl, mImageView,
                imageResponse, imageResponse);

        VolleyManager.getInstance(mContext).addToRequestQueue(imageRequest);

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