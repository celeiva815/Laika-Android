package social.laika.app.network.requests;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import social.laika.app.models.Dog;
import social.laika.app.models.Photo;
import social.laika.app.network.Api;
import social.laika.app.responses.SimpleResponse;
import social.laika.app.utils.PrefsManager;
import social.laika.app.utils.Tag;

/**
 * Created by Tito_Leiva on 12-06-15.
 */
public class PhotosRequest extends SyncRequest {


    private static final String TAG = PhotosRequest.class.getSimpleName();

    Photo mPhoto;
    int mDogId;

    public PhotosRequest(Context context, Photo alarmReminder) {
        super(context);
        mPhoto = alarmReminder;

    }

    public PhotosRequest(Context mContext, int mDogId) {
        super(mContext);
        this.mDogId = mDogId;
    }

    @Override
    public void sync() throws InterruptedException, ExecutionException, TimeoutException, JSONException {

        JSONObject jsonObject;

        switch (mPhoto.mNeedsSync) {

            case Tag.FLAG_CREATED:

                Log.i("Laika Sync Service", "FLAG_CREATED Uploading the new Photo to the server");

                jsonObject = create();
                mPhoto.mPhotoId = jsonObject.getInt(Photo.COLUMN_PHOTO_ID);
                mPhoto.refresh();

                break;

            case Tag.FLAG_UPDATED:

                Log.i("Laika Sync Service", "FLAG_UPDATED Updating the Photo " +
                        mPhoto.mPhotoId + " to the server");

                jsonObject = update();
                Photo.saveDogPhoto(jsonObject, mContext);

                break;

            case Tag.FLAG_DELETED:

                Log.i(TAG, "FLAG_DELETED Deleting the Photo " +
                        mPhoto.mPhotoId + " with the server");

                jsonObject = delete();

                if (jsonObject.getBoolean(SUCCESS)) {

                    mPhoto.delete();
                }

                break;
        }
    }

    @Override
    public JSONObject refresh() throws JSONException, InterruptedException, ExecutionException,
            TimeoutException {

        Map<String, String> params = new HashMap<>();
        params.put(Dog.COLUMN_DOG_ID, Integer.toString(mDogId));

        String address = Api.ADDRESS_UPLOAD_DOG_PHOTOS;
        String token = PrefsManager.getUserToken(mContext);

        RequestFuture<JSONObject> future = RequestFuture.newFuture();
        SimpleResponse errorListener = new SimpleResponse();
        Request request = Api.getRequest(params, address, future, errorListener, token);

        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(request);

        return future.get(20, TimeUnit.SECONDS);

    }

    @Override
    protected JSONObject create() throws JSONException, InterruptedException, ExecutionException,
            TimeoutException {

        JSONObject jsonObject = mPhoto.getJsonObject();

        String address = Api.ADDRESS_UPLOAD_DOG_PHOTOS;
        String token = PrefsManager.getUserToken(mContext);

        RequestFuture<JSONObject> future = RequestFuture.newFuture();
        SimpleResponse errorListener = new SimpleResponse();
        Request request = Api.postRequest(jsonObject, address, future, errorListener, token);

        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(request);

        return future.get(20, TimeUnit.SECONDS);
    }

    @Override
    protected JSONObject update() throws JSONException, InterruptedException, ExecutionException,
            TimeoutException {

        JSONObject jsonObject = mPhoto.getJsonObject();

        String address = Api.ADDRESS_UPLOAD_DOG_PHOTOS;
        String token = PrefsManager.getUserToken(mContext);

        RequestFuture<JSONObject> future = RequestFuture.newFuture();
        SimpleResponse errorListener = new SimpleResponse();
        Request request = Api.putRequest(jsonObject, address, future, errorListener, token);

        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(request);

        return future.get(20, TimeUnit.SECONDS);
    }


    @Override
    protected JSONObject delete() throws JSONException, InterruptedException, ExecutionException,
            TimeoutException {

        JSONObject jsonObject = new JSONObject();

        jsonObject.put(Photo.COLUMN_PHOTO_ID, mPhoto.mPhotoId);

        String address = Api.ADDRESS_UPLOAD_DOG_PHOTOS;
        String token = PrefsManager.getUserToken(mContext);

        RequestFuture<JSONObject> future = RequestFuture.newFuture();
        SimpleResponse errorListener = new SimpleResponse();
        Request request = Api.deleteRequest(jsonObject, address, future, errorListener, token);

        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(request);

        return future.get(20, TimeUnit.SECONDS);

    }
}
