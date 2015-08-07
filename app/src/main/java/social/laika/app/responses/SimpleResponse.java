package social.laika.app.responses;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import social.laika.app.interfaces.Requestable;

/**
 * Created by Tito_Leiva on 13-04-15.
 */
public class SimpleResponse implements Response.ErrorListener,
        Response.Listener<JSONObject> {

    public static final String API_URL = "url";
    public static final String API_TIME = "time";
    public static final String API_DATE = "date";

    public Requestable mRequest;

    public SimpleResponse(){  }

    public SimpleResponse(Requestable mRequest) {
        this.mRequest = mRequest;
    }

    @Override
    public void onResponse(JSONObject response) {

        if (mRequest != null)
        mRequest.onSuccess();

    }

    @Override
    public void onErrorResponse(VolleyError error) {

        if (mRequest != null)
            mRequest.onFailure();

    }
}