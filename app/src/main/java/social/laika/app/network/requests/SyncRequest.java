package social.laika.app.network.requests;

import android.content.Context;

import com.android.volley.Request;

import java.util.HashMap;
import java.util.Map;

import social.laika.app.interfaces.Requestable;
import social.laika.app.network.RequestManager;
import social.laika.app.network.VolleyManager;
import social.laika.app.responses.FirstInformationResponse;
import social.laika.app.utils.Do;
import social.laika.app.utils.PrefsManager;

/**
 * Created by Tito_Leiva on 31-07-15.
 */
public class SyncRequest extends BaseRequest {

    public static final String TAG = SyncRequest.class.getSimpleName();
    public static final String LAST_SYNC = "last_sync";

    public Context mContext;
    public String mLastSync = "";

    public SyncRequest(Context mContext) {
        super(mContext);
    }

    public SyncRequest(Context mContext, Context mContext1, String mLastSync) {
        super(mContext);
        mContext = mContext1;
        this.mLastSync = mLastSync;
    }

    @Override
    public void request() {

        Map<String,String> params = new HashMap<>();
        params.put(LAST_SYNC, mLastSync);

        String token = PrefsManager.getUserToken(mContext);
        FirstInformationResponse response = new FirstInformationResponse(this);

        Request firstRequest = RequestManager.getRequest(params, RequestManager.ADDRESS_SYNC,
                response, response, token);

        VolleyManager.getInstance(mContext).addToRequestQueue(firstRequest, TAG);
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onFailure() {

    }
}
