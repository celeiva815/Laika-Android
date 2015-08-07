package social.laika.app.responses;

import android.app.Activity;
import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import social.laika.app.fragments.AlbumMyDogFragment;
import social.laika.app.models.Dog;
import social.laika.app.models.Photo;

/**
 * Created by Tito_Leiva on 24-07-15.
 */
public class DogPhotosResponse implements Response.ErrorListener,
        Response.Listener<JSONObject>  {

    public Dog mDog;
    public Context mContext;
    public Activity mActivity;
    public AlbumMyDogFragment mFragment;

    public DogPhotosResponse(Dog mDog, AlbumMyDogFragment mFragment) {
        this.mDog = mDog;
        this.mFragment = mFragment;
        this.mActivity = mFragment.getActivity();
        this.mContext = mActivity.getApplicationContext();
    }

    @Override
    public void onResponse(JSONObject response) {

        Photo.saveDogPhotos(response, mContext, mDog);
        mFragment.onResume();

    }


    @Override
    public void onErrorResponse(VolleyError error) {

    }


}
