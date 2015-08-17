package social.laika.app.network.requests;

import android.content.Context;
import android.telecom.TelecomManager;
import android.telephony.TelephonyManager;

import com.activeandroid.ActiveAndroid;
import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import social.laika.app.utils.Do;

/**
 * Created by Tito_Leiva on 08-05-15.
 */
public class LaikaRequest extends JsonObjectRequest {

    public static final String ACCESS_TOKEN = "access_token";
    public static final String DEVICE_ID = "device_id";
    private final String mToken;
    private String mDeviceId;

    public LaikaRequest(int method, String url, JSONObject jsonRequest,
                        Response.Listener<JSONObject> listener,
                        Response.ErrorListener errorListener, String mToken) {
        super(method, url, jsonRequest, listener, errorListener);

        this.mToken = mToken;

    }

    public void setDeviceId(Context mContext) {

        TelephonyManager tm = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
        mDeviceId = tm.getDeviceId();
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {

        if (!Do.isNullOrEmpty(mToken)) {

            HashMap<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "application/json");
            headers.put(ACCESS_TOKEN, mToken);

            if (mDeviceId != null) {
                headers.put(DEVICE_ID, mDeviceId);
            }

            return headers;

        } else {

            return super.getHeaders();
        }
    }

}
