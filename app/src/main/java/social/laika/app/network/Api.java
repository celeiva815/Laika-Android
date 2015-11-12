package social.laika.app.network;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import social.laika.app.R;
import social.laika.app.models.Dog;
import social.laika.app.network.requests.LaikaRequest;
import social.laika.app.responses.ImageResponse;
import social.laika.app.utils.PrefsManager;

/**
 * Encapsulates the creation of Treid Api volley {@link com.android.volley.Request}
 *
 * Created by lukas on 17-11-14.
 * Improved by Tito_Leiva on 05-02-15.
 *
 */
public class Api {

    public static final String TAG = Api.class.getSimpleName();

    public static final String BASE_URL = "http://fundaciones.laika.social/";
//    public static final String BASE_URL = "http://test.laika.social/";
//    public static final String BASE_URL = "http://develop.laika.social/";
    public static final String TEST_BASE_URL = "api/";
    public static final String API_URL = BASE_URL + TEST_BASE_URL;

    //API Address
    public static final String ADDRESS_ALERT_REMINDERS = "alert_reminders/";
    public static final String ADDRESS_ADD_DOG_OWNER = "add_dog_owner/";
    public static final String ADDRESS_CALENDAR_REMINDERS = "calendar_reminders/";
    public static final String ADDRESS_DOG = "dog/";
    public static final String ADDRESS_DOGS = "dogs/";
    public static final String ADDRESS_CANCEL_POSTULATION = "cancel_postulation/";
    public static final String ADDRESS_CONFIRM_POSTULATION = "confirm_postulation/";
    public static final String ADDRESS_EVENTS = "events/";
    public static final String ADDRESS_GET_MATCHING_DOGS = "get_matching_dogs/";
    public static final String ADDRESS_LOCATIONS = "locations/";
    public static final String ADDRESS_LOGIN = "log_in/";
    public static final String ADDRESS_FB_LOGIN = "auth/facebook/log_in";
    public static final String ADDRESS_OWNER_DOGS = "owner_dogs/";
    public static final String ADDRESS_PUBLICATIONS = "publications/";
    public static final String ADDRESS_REMOVE_DOG_OWNER = "remove_dog_owner/";
    public static final String ADDRESS_SEND_RECOVER_PASS = "send_recover_pass/";
    public static final String ADDRESS_REGISTER = "sign_up/";
    public static final String ADDRESS_STORIES = "stories/";
    public static final String ADDRESS_SYNC = "sync/";
    public static final String ADDRESS_SUBSCRIBE = "subscribe/";
    public static final String ADDRESS_TIPS = "tips/";
    public static final String ADDRESS_UNSUBSCRIBE = "unsubscribe/";
    public static final String ADDRESS_UPLOAD_ADOPTION_FORM = "upload_adoption_form/";
    public static final String ADDRESS_UPLOAD_DOG_PHOTOS = "upload_dog_photos/";
    public static final String ADDRESS_USER = "user/";
    public static final String ADDRESS_USER_DOG_PHOTOS = "user_dog_photos/";
    public static final String ADDRESS_USER_POSTULATIONS = "user_postulations";
    public static final String ADDRESS_VET_VISITS = "vet_visits/";

    //Request Methods
    public static final int METHOD_DELETE = Request.Method.DELETE;
    public static final int METHOD_GET = Request.Method.GET;
    public static final int METHOD_PATCH = Request.Method.PATCH;
    public static final int METHOD_POST = Request.Method.POST;

    public static final int METHOD_PUT = Request.Method.PUT;
    public static final String URL_SPACE = "%20";
    public static final String NORMAL_SPACE = " ";

    public static final String ID = "id";
    public static final String USER_ID = "user_id";
    public static final String FULL_NAME = "full_name";
    public static final String ACCESS_TOKEN = "access_token";
    public static final String EMAIL = "email";

    public static final String API_PHOTO = "photo";
    public static final String API_CONTENT = "content";
    public static final String API_FILE_NAME = "file_name";



    public static String apiRelativeUrl;

    public Api() {

        this.apiRelativeUrl = API_URL;
    }

    public static Request getRequest(Map<String,String> params, String address,
                                     Response.Listener<JSONObject> listener,
                                     Response.ErrorListener errorListener, final String token) {

        String url = addParamsToURL(getURL(address), params);
        Log.d(Api.TAG, url);

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

    public static Request deleteRequest(JSONObject jsonParams, String address,
                                       Response.Listener<JSONObject> listener,
                                       Response.ErrorListener errorListener, final String token) {

        return defaultRequest(METHOD_DELETE, jsonParams, address, listener, errorListener, token);
    }

    public static Request defaultRequest(int method, JSONObject jsonParams, String address,
                                      Response.Listener<JSONObject> listener,
                                      Response.ErrorListener errorListener, final String token) {
        String url = getURL(address);
        Log.d(Api.TAG, address);

        return new LaikaRequest(method, url, jsonParams, listener, errorListener, token);
    }

    public static Request imageRequest(String url, ImageView imageView,
                                       Response.Listener<Bitmap> listener,
                                       Response.ErrorListener errorListener) {

        int maxWidth = imageView.getMaxWidth();
        int maxHeight = imageView.getMaxHeight();

        return new ImageRequest(url, listener, maxWidth, maxHeight, null, errorListener);
    }

    public static void getImage(String imageUrl, ProgressBar progressBar, ImageView imageView, Context context) {

        ImageResponse response = new ImageResponse(imageView, progressBar);
        Request request = imageRequest(imageUrl, imageView, response, response);

        VolleyManager.getInstance(context).addToRequestQueue(request, TAG);

        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }

    }

    public static Request postImage(int dogId, JSONObject jsonPhoto, Context context,
                                    Response.Listener<JSONObject> listener,
                                    Response.ErrorListener errorListener) {

        String address = ADDRESS_USER_DOG_PHOTOS;
        String token = PrefsManager.getUserToken(context);

        JSONObject jsonParams = new JSONObject();

        try {

            jsonParams.put(Dog.COLUMN_DOG_ID, dogId);
            jsonParams.put(API_PHOTO, jsonPhoto);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Request request = postRequest(jsonParams, address, listener, errorListener, token);

        request.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        return request;
    }

    // ############## Utils ################ //

    public static void error(VolleyError error, Context context) {

        if (error != null && error.networkResponse != null) {
            if (error.networkResponse.statusCode == 400) {
                Toast.makeText(context, R.string.parameters_error,
                        Toast.LENGTH_SHORT).show();
            } else {
                String errorStr = VolleyErrorHelper.getMessage(error,
                        context);
                Toast.makeText(context, errorStr, Toast.LENGTH_SHORT)
                        .show();
            }
        } else {
            Toast.makeText(context, R.string.no_internet_error,
                    Toast.LENGTH_SHORT).show();
        }
    }

    public static JSONObject getJsonPhoto(String encodedImage, String name) {

        Map<String,String> params = new HashMap<>();
        params.put(API_CONTENT, encodedImage);
        params.put(API_FILE_NAME, name);

        return getJsonParams(params);
    }

    public static String buildImag(String relativeUrl) {

        String url = BASE_URL + ADDRESS_USER_DOG_PHOTOS + relativeUrl;
        Log.d(TAG, url);
        return url;
    }

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
