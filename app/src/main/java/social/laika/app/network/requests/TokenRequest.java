package social.laika.app.network.requests;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.android.volley.Request;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import social.laika.app.interfaces.Requestable;
import social.laika.app.network.RequestManager;
import social.laika.app.network.VolleyManager;
import social.laika.app.responses.SimpleResponse;
import social.laika.app.utils.PrefsManager;

/**
 * Created by Tito_Leiva on 31-07-15.
 */
public class TokenRequest implements Requestable {

    public static final String TAG = TokenRequest.class.getSimpleName();
    public static final String KEY_TOKEN = "token";
    public String mToken;
    public Context mContext;

    public TokenRequest(String mToken, Context mContext) {
        this.mToken = mToken;
        this.mContext = mContext;
    }

    @Override
    public void request() {

        SimpleResponse response = new SimpleResponse(this);
        Map<String,String> params = new HashMap<>();

        params.put(KEY_TOKEN, mToken);

        JSONObject jsonParams = new JSONObject(params);
        String token = PrefsManager.getUserToken(mContext);
        Request request = RequestManager.postRequest(jsonParams, RequestManager.ADDRESS_SUBSCRIBE,
                response, response, token);

        VolleyManager.getInstance(mContext).addToRequestQueue(request, TAG);
    }

    @Override
    public void onSuccess() {
        Log.d(TAG, "GCM token registered.");
    }

    @Override
    public void onFailure() {
        Log.e(TAG, "Can't register the token");
    }
}
