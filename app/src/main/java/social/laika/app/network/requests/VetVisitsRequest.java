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

import social.laika.app.models.VetVisit;
import social.laika.app.models.Dog;
import social.laika.app.network.RequestManager;
import social.laika.app.responses.SimpleResponse;
import social.laika.app.utils.PrefsManager;
import social.laika.app.utils.Tag;

/**
 * Created by Tito_Leiva on 12-06-15.
 */
public class VetVisitsRequest extends SyncRequest {


    private static final String TAG = VetVisitsRequest.class.getSimpleName();

    VetVisit mVetVisit;
    int mDogId;

    public VetVisitsRequest(Context context, VetVisit alarmReminder) {
        super(context);
        mVetVisit = alarmReminder;

    }

    public VetVisitsRequest(Context mContext, int mDogId) {
        super(mContext);
        this.mDogId = mDogId;
    }

    @Override
    public void sync() throws InterruptedException, ExecutionException, TimeoutException, JSONException {

        JSONObject jsonObject;

        switch (mVetVisit.mNeedsSync) {

            case Tag.FLAG_CREATED:

                Log.i("Laika Sync Service", "FLAG_CREATED Uploading the new VetVisit to the server");

                jsonObject = create();
                mVetVisit.mVetVisitId = jsonObject.getInt(VetVisit.COLUMN_VET_VISIT_ID);
                mVetVisit.refresh();

                break;

            case Tag.FLAG_UPDATED:

                Log.i("Laika Sync Service", "FLAG_UPDATED Updating the VetVisit " +
                        mVetVisit.mVetVisitId + " to the server");

                jsonObject = update();
                VetVisit.saveVetVisit(jsonObject);

                break;

            case Tag.FLAG_DELETED:

                Log.i(TAG, "FLAG_DELETED Deleting the VetVisit " +
                        mVetVisit.mVetVisitId + " with the server");

                jsonObject = delete();

                if (jsonObject.getBoolean(SUCCESS)) {

                    mVetVisit.delete();
                }

                break;
        }
    }

    @Override
    public JSONObject refresh() throws JSONException, InterruptedException, ExecutionException,
            TimeoutException {

        Map<String, String> params = new HashMap<>();
        params.put(Dog.COLUMN_DOG_ID, Integer.toString(mDogId));

        String address = RequestManager.ADDRESS_VET_VISITS;
        String token = PrefsManager.getUserToken(mContext);

        RequestFuture<JSONObject> future = RequestFuture.newFuture();
        SimpleResponse errorListener = new SimpleResponse();
        Request request = RequestManager.getRequest(params, address, future, errorListener, token);

        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(request);

        return future.get(20, TimeUnit.SECONDS);

    }

    @Override
    protected JSONObject create() throws JSONException, InterruptedException, ExecutionException,
            TimeoutException {

        JSONObject jsonObject = mVetVisit.getJsonObject();

        String address = RequestManager.ADDRESS_VET_VISITS;
        String token = PrefsManager.getUserToken(mContext);

        RequestFuture<JSONObject> future = RequestFuture.newFuture();
        SimpleResponse errorListener = new SimpleResponse();
        Request request = RequestManager.postRequest(jsonObject, address, future, errorListener, token);

        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(request);

        return future.get(20, TimeUnit.SECONDS);
    }

    @Override
    protected JSONObject update() throws JSONException, InterruptedException, ExecutionException,
            TimeoutException {

        JSONObject jsonObject = mVetVisit.getJsonObject();

        String address = RequestManager.ADDRESS_VET_VISITS;
        String token = PrefsManager.getUserToken(mContext);

        RequestFuture<JSONObject> future = RequestFuture.newFuture();
        SimpleResponse errorListener = new SimpleResponse();
        Request request = RequestManager.putRequest(jsonObject, address, future, errorListener, token);

        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(request);

        return future.get(20, TimeUnit.SECONDS);
    }


    @Override
    protected JSONObject delete() throws JSONException, InterruptedException, ExecutionException,
            TimeoutException {

        JSONObject jsonObject = new JSONObject();

        jsonObject.put(VetVisit.COLUMN_VET_VISIT_ID, mVetVisit.mVetVisitId);

        String address = RequestManager.ADDRESS_VET_VISITS;
        String token = PrefsManager.getUserToken(mContext);

        RequestFuture<JSONObject> future = RequestFuture.newFuture();
        SimpleResponse errorListener = new SimpleResponse();
        Request request = RequestManager.deleteRequest(jsonObject, address, future, errorListener, token);

        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(request);

        return future.get(20, TimeUnit.SECONDS);

    }
}
