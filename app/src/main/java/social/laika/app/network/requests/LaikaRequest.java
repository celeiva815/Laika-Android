package social.laika.app.network.requests;

import android.content.Context;
import android.os.Build;
import android.telecom.TelecomManager;
import android.telephony.TelephonyManager;

import com.activeandroid.ActiveAndroid;
import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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

        // If all else fails, if the user does have lower than API 9 (lower
        // than Gingerbread), has reset their device or 'Secure.ANDROID_ID'
        // returns 'null', then simply the ID returned will be solely based
        // off their Android device information. This is where the collisions
        // can happen.
        // Thanks http://www.pocketmagic.net/?p=1662!
        // Try not to use DISPLAY, HOST or ID - these items could change.
        // If there are collisions, there will be overlapping data
        String m_szDevIDShort = "35" +
                (Build.BOARD.length() % 10) +
                (Build.BRAND.length() % 10) +
                (Build.CPU_ABI.length() % 10) +
                (Build.DEVICE.length() % 10) +
                (Build.MANUFACTURER.length() % 10) +
                (Build.MODEL.length() % 10) +
                (Build.PRODUCT.length() % 10);

        // Thanks to @Roman SL!
        // http://stackoverflow.com/a/4789483/950427
        // Only devices with API >= 9 have android.os.Build.SERIAL
        // http://developer.android.com/reference/android/os/Build.html#SERIAL
        // If a user upgrades software or roots their device, there will be a duplicate entry
        String serial = null;
        try {
            serial = android.os.Build.class.getField("SERIAL").get(null).toString();

            // Go ahead and return the serial for api => 9
            mDeviceId = new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();

        } catch (Exception exception) {
            // String needs to be initialized
            serial = "serial"; // some value
        }

        // Thanks @Joe!
        // http://stackoverflow.com/a/2853253/950427
        // Finally, combine the values we have found by using the UUID class to create a unique identifier
        mDeviceId = new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
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
