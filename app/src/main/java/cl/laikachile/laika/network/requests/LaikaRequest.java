package cl.laikachile.laika.network.requests;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cl.laikachile.laika.utils.Do;

/**
 * Created by Tito_Leiva on 08-05-15.
 */
public class LaikaRequest extends JsonObjectRequest {

    public static final String ACCESS_TOKEN = "access_token";
    private final String mToken;

    public LaikaRequest(int method, String url, JSONObject jsonRequest,
                        Response.Listener<JSONObject> listener,
                        Response.ErrorListener errorListener, String mToken) {
        super(method, url, jsonRequest, listener, errorListener);

        this.mToken = mToken;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {

        if (!Do.isNullOrEmpty(mToken)) {

            HashMap<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "application/json");
            headers.put(ACCESS_TOKEN, mToken);
            return headers;

        } else {

            return super.getHeaders();
        }
    }

}
