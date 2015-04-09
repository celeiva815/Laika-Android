package cl.laikachile.laika.network;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cl.laikachile.laika.utils.PrefsManager;

/**
 * Encapsulates the creation of Treid Api volley {@link com.android.volley.Request}
 *
 * Created by lukas on 17-11-14.
 * Improved by Tito_Leiva on 05-02-15.
 *
 */
public class RequestManager {

    public static final String TAG = RequestManager.class.getSimpleName();

    public static final String BASE_URL = "http://laika-test.herokuapp.com/";
    public static final String PROFILE_IMAGE_URL = ""; //TODO add the url for the images if exists
    public static final String TEST_BASE_URL = "api/";
    public static final String ADDRESS_UPLOADED_IMAGES = "/"; //TODO add the url for the images if exists
    public static final String API_URL = BASE_URL + TEST_BASE_URL;

    public static final String ADDRESS_LOGIN = "log_in/";
    public static final String ADDRESS_SIGN_UP = "sign_up/";

    public static final int METHOD_GET = Request.Method.GET;
    public static final int METHOD_POST = Request.Method.POST;

    public static final String URL_SPACE = "%20";
    public static final String NORMAL_SPACE = " ";
    public static final String ID = "id";
    public static final String USER_ID = "user_id";
    public static final String ACCESS_TOKEN = "access_token";
    public static final String EMAIL = "email";

    public static String apiRelativeUrl;

    public RequestManager() {

        this.apiRelativeUrl = API_URL;
    }

    public static String buildImageUrl(String relativeUrl) {

        String url = BASE_URL + ADDRESS_UPLOADED_IMAGES + relativeUrl;
        Log.d(TAG, url);
        return url;
    }

    public static Request defaultRequest(JSONObject jsonParams, String address, int method,
                                         Response.Listener<JSONObject> listener,
                                         Response.ErrorListener errorListener, final String token) {
        String url = getURL(address);
        Log.d(RequestManager.TAG, address);

        return new JsonObjectRequest(method, url, jsonParams, listener, errorListener) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put(ACCESS_TOKEN, token);
                return headers;

            }
        };
    }

    public static Request getRequest(List<String> params, String address,
                                     Response.Listener<JSONArray> listener,
                                     Response.ErrorListener errorListener) {

        String url = getURL(address);
        Log.d(RequestManager.TAG, url);

        return new JsonArrayRequest(url, listener, errorListener);
    }

    /**
     * This method is unique for this project, it should be standarized for new applications
     *
     * @param params
     * @param address
     * @param imageParam
     * @param listener
     * @param errorListener
     * @return
     */
    public static Request postImage(List<String> params, String address, final byte[] imageParam,
                                    Response.Listener<String> listener,
                                    Response.ErrorListener errorListener) {

        String url = getURL(address);
        Log.d(RequestManager.TAG, url);

        StringRequest request = new StringRequest(METHOD_POST, url, listener, errorListener) {

            @Override
            public byte[] getBody() throws AuthFailureError {
                return imageParam;
            }

        };

        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        return request;
    }

    // ############## Utils ################ //

    public static JSONObject getParams(Map<String, String> params) {

        JSONObject jsonParams = new JSONObject(params);

        return jsonParams;
    }

    private static String getURL(String address) {

        return API_URL + address;

    }

    private static String paramsToURL(List<String> params) {

        String url = "";

        for (int i = 0; i < params.size(); i++) {

            url += params.get(i);

            if (i < params.size() - 1) {
                url += "/";
            }
        }

        return url;
    }

    public static List<String> getTokenParams(Context context) {

        String token = PrefsManager.getUserToken(context);
        List<String> params = new ArrayList<>();
        params.add(token);

        return params;
    }

    public static Request stringRequest(List<String> params, String address, int method, JSONObject jsonParams,
                                        Response.Listener<String> listener, Response.ErrorListener errorListener) {

        final byte[] body = jsonParams.toString().getBytes();

        String url = getURL(address);
        StringRequest request = new StringRequest(METHOD_POST, url, listener, errorListener) {

            @Override
            public byte[] getBody() throws AuthFailureError {
                return body;
            }
        };

        return request;
    }

}
