package social.laika.app.network.requests;

import android.content.Context;

import com.android.volley.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import social.laika.app.network.RequestManager;
import social.laika.app.network.VolleyManager;
import social.laika.app.responses.FirstInformationResponse;
import social.laika.app.utils.PrefsManager;

/**
 * Created by Tito_Leiva on 31-07-15.
 */
public class GeneralRequest extends SyncRequest {

    public static final String TAG = GeneralRequest.class.getSimpleName();
    public static final String LAST_SYNC = "last_sync";

    public Context mContext;
    public String mLastSync = "";

    public GeneralRequest(Context mContext) {
        super(mContext);
    }

    public GeneralRequest(Context mContext, Context mContext1, String mLastSync) {
        super(mContext);
        mContext = mContext1;
        this.mLastSync = mLastSync;
    }

    @Override
    public void sync() throws JSONException, InterruptedException, ExecutionException, TimeoutException {

    }

    @Override
    public JSONObject refresh() throws JSONException, InterruptedException, ExecutionException, TimeoutException {
        return null;
    }

    @Override
    protected JSONObject create() throws JSONException, InterruptedException, ExecutionException, TimeoutException {
        return null;
    }

    @Override
    protected JSONObject update() throws JSONException, InterruptedException, ExecutionException, TimeoutException {
        return null;
    }

    @Override
    protected JSONObject delete() throws JSONException, InterruptedException, ExecutionException, TimeoutException {
        return null;
    }

    public void request() {

        Map<String, String> params = new HashMap<>();
        params.put(LAST_SYNC, mLastSync);

        String token = PrefsManager.getUserToken(mContext);
        FirstInformationResponse response = new FirstInformationResponse(null);

        Request firstRequest = RequestManager.getRequest(params, RequestManager.ADDRESS_SYNC,
                response, response, token);

        VolleyManager.getInstance(mContext).addToRequestQueue(firstRequest, TAG);
    }

}
