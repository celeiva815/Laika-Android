package social.laika.app.network.requests;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import social.laika.app.models.AlarmReminder;
import social.laika.app.models.Dog;
import social.laika.app.models.OwnerDog;
import social.laika.app.models.Photo;
import social.laika.app.models.VetVisit;
import social.laika.app.network.Api;
import social.laika.app.responses.SimpleResponse;
import social.laika.app.utils.PrefsManager;
import social.laika.app.utils.Tag;

/**
 * Created by cnmartinez on 8/10/15.
 */
public class DogRequest extends SyncRequest {
    private static final String TAG = DogRequest.class.getSimpleName();
    int mDogId;

    public DogRequest(Context mContext, int mDogId) {
        super(mContext);
        this.mDogId = mDogId;
    }

    /** Downloads the full information for a dog, synchronizing
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws TimeoutException
     */
    public void download() throws InterruptedException, ExecutionException, TimeoutException {

        /* Building the url */
        String address = Api.ADDRESS_DOGS + Integer.toString(mDogId);
        String token = PrefsManager.getUserToken(mContext);

        /* Preparing the request */
        RequestFuture<JSONObject> future = RequestFuture.newFuture();
        SimpleResponse errorListener = new SimpleResponse();
        Request request = Api.getRequest(null, address, future, errorListener, token);

        /* Making the request */
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(request);

        /* Waiting and saving the response */
        JSONObject response = future.get(50, TimeUnit.SECONDS);
        Dog.saveDogs(response, Tag.DOG_OWNED);
        AlarmReminder.saveReminders(response, mContext);
        VetVisit.saveVetVisits(response);
        Photo.saveDogPhotos(response, mContext);
        OwnerDog.saveOwnerDogs(response, mContext);

    }

    @Override
    public void sync() throws JSONException, InterruptedException, ExecutionException, TimeoutException {
        Log.e(TAG, "sync not implemented");
    }

    @Override
    public JSONObject refresh() throws JSONException, InterruptedException, ExecutionException, TimeoutException {
        Log.e(TAG, "refresh not implemented");
        return null;
    }

    @Override
    protected JSONObject create() throws JSONException, InterruptedException, ExecutionException, TimeoutException {
        Log.e(TAG, "create not implemented");
        return null;
    }

    @Override
    protected JSONObject update() throws JSONException, InterruptedException, ExecutionException, TimeoutException {
        Log.e(TAG, "update not implemented");
        return null;
    }

    @Override
    protected JSONObject delete() throws JSONException, InterruptedException, ExecutionException, TimeoutException {
        Log.e(TAG, "delete not implemented");
        return null;
    }
}
