package cl.laikachile.laika.network;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Encapsulates the creation of Treid Api volley {@link com.android.volley.Request}
 *
 * Created by lukas on 17-11-14.
 * Improved by Tito_Leiva on 05-02-15.
 *
 */
public class RequestManager {

    public static final String TAG = RequestManager.class.getSimpleName();

    private static final String BASE_URL = "http://rsolver1.azurewebsites.net/WebServices/SCMSservices.svc/";
    public static final String PROFILE_IMAGE_URL = ""; //TODO add the url for the images if exists
    public static final String API_VERSION_URL = "";
    public static final String API_URL = BASE_URL + API_VERSION_URL;

    public static final String ADDRESS_LOGIN = "login/";
    public static final String ADDRESS_PO_BY_ID = "poById/";
    public static final String ADDRESS_PO_BY_CUSTOMER_ID = "poByCustomerId/";
    public static final String ADDRESS_ALL_CUSTOMERS = "allCustomers/";
    public static final String ADDRESS_ALL_PORTS = "allPorts/";
    public static final String ADDRESS_ALL_DCS = "allDcs/";

    public static final int METHOD_GET = Request.Method.GET;
    public static final int METHOD_POST = Request.Method.POST;

    public static final String URL_SPACE = "%20";
    public static final String NORMAL_SPACE = " ";

    public static String apiRelativeUrl;

    public RequestManager() {

        this.apiRelativeUrl = API_URL;
    }

    public static String buildImageUrl(String relativeUrl) {
        String url = PROFILE_IMAGE_URL + relativeUrl;
        Log.d(TAG, url);
        return url;
    }

    public static Request getRequest(List<String> params, String address, int method,
                                     Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {

        JSONObject jsonParams = null;
        String url = getURL(address, params);
        Log.d(RequestManager.TAG, url);

        return new JsonObjectRequest(method, url, jsonParams, listener, errorListener);
    }

    public static Request getRequest(List<String> params, String address,
                                     Response.Listener<JSONArray> listener,
                                     Response.ErrorListener errorListener) {

        String url = getURL(address, params);
        Log.d(RequestManager.TAG, url);

        return new JsonArrayRequest(url, listener, errorListener);
    }

    public static List<String> getParams(String... params) {

        List<String> list = new ArrayList<>(params.length);

        for (String p : params) {

            list.add(p);
        }

        return list;
    }

    private static String getURL(String address, List<String> params) {

        String paramsURL = RequestManager.paramsToURL(params);

        return API_URL + address + paramsURL;

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

}
