package cl.laikachile.laika.network;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cl.laikachile.laika.network.requests.LaikaRequest;
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

    //API Address
    public static final String ADDRESS_DOG = "dog/";
    public static final String ADDRESS_DOGS = "dogs/";
    public static final String ADDRESS_CANCEL_POSTULATION = "cancel_postulation/";
    public static final String ADDRESS_CONFIRM_POSTULATION = "confirm_postulation/";
    public static final String ADDRESS_EVENTS = "events/";
    public static final String ADDRESS_GET_MATCHING_DOGS = "get_matching_dogs/";
    public static final String ADDRESS_LOGIN = "log_in/";
    public static final String ADDRESS_OWNER_DOGS = "owner_dogs/";
    public static final String ADDRESS_PUBLICATIONS = "publications/";
    public static final String ADDRESS_REGISTER = "sign_up/";
    public static final String ADDRESS_STORIES = "stories/";
    public static final String ADDRESS_TIPS = "tips/";
    public static final String ADDRESS_UPLOAD_ADOPTION_FORM = "upload_adoption_form/";
    public static final String ADDRESS_USER_POSTULATIONS = "user_postulations";

    //Request Methods
    public static final int METHOD_GET = Request.Method.GET;
    public static final int METHOD_POST = Request.Method.POST;
    public static final int METHOD_PUT = Request.Method.PUT;

    public static final String URL_SPACE = "%20";
    public static final String NORMAL_SPACE = " ";
    public static final String ID = "id";

    public static final String USER_ID = "user_id";
    public static final String FULL_NAME = "full_name";
    public static final String ACCESS_TOKEN = "access_token";
    public static final String EMAIL = "email";

    public static String apiRelativeUrl;

    public RequestManager() {

        this.apiRelativeUrl = API_URL;
    }

    public static Request getRequest(Map<String,String> params, String address,
                                     Response.Listener<JSONObject> listener,
                                     Response.ErrorListener errorListener, final String token) {

        String url = addParamsToURL(getURL(address), params);
        Log.d(RequestManager.TAG, url);

        return new LaikaRequest(METHOD_GET, url, null, listener, errorListener, token);
    }

    public static Request postRequest(JSONObject jsonParams, String address,
                                      Response.Listener<JSONObject> listener,
                                      Response.ErrorListener errorListener, final String token) {

        return defaultRequest(METHOD_POST,jsonParams,address,listener,errorListener,token);
    }

    public static Request putRequest(JSONObject jsonParams, String address,
                                      Response.Listener<JSONObject> listener,
                                      Response.ErrorListener errorListener, final String token) {

        return defaultRequest(METHOD_PUT, jsonParams, address, listener, errorListener, token);
    }

    public static Request defaultRequest(int method, JSONObject jsonParams, String address,
                                      Response.Listener<JSONObject> listener,
                                      Response.ErrorListener errorListener, final String token) {
        String url = getURL(address);
        Log.d(RequestManager.TAG, address);

        return new LaikaRequest(method, url, jsonParams, listener, errorListener, token);
    }

    public static Request imageRequest(String url, ImageView imageView,
                                       Response.Listener<Bitmap> listener,
                                       Response.ErrorListener errorListener) {

        int maxWidth = imageView.getMaxWidth();
        int maxHeight = imageView.getMaxHeight();

        return new ImageRequest(url, listener, maxWidth, maxHeight, null, errorListener);
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

    public static String buildImageUrl(String relativeUrl) {

        String url = BASE_URL + ADDRESS_UPLOADED_IMAGES + relativeUrl;
        Log.d(TAG, url);
        return url;
    }

    // ############## Utils ################ //

    public static JSONObject getJsonParams(Map<String, String> params) {

        JSONObject jsonParams = new JSONObject(params);

        return jsonParams;
    }

    private static String getURL(String address) {

        return API_URL + address;

    }

    private static String addParamsToURL(String url, Map<String, String> params) {

        if (params != null) {
            if (!url.endsWith("?"))
                url += "?";

            for (Map.Entry<String, String> entry : params.entrySet()) {

                String key = entry.getKey();
                String value = entry.getValue();

                url += key + "=" + value + "&";
            }

            if (url.charAt(url.length() - 1) == '&') {

                url = url.substring(0, url.length() - 1);
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
