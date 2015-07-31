package social.laika.app.responses;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import social.laika.app.interfaces.Photographable;
import social.laika.app.interfaces.Requestable;
import social.laika.app.models.Dog;
import social.laika.app.models.Photo;
import social.laika.app.models.Story;
import social.laika.app.network.RequestManager;
import social.laika.app.network.VolleyManager;

/**
 * Created by Tito_Leiva on 13-04-15.
 */
public class SimpleResponse implements Response.ErrorListener,
        Response.Listener<JSONObject> {

    public static final String API_URL = "url";
    public static final String API_TIME = "time";
    public static final String API_DATE = "date";

    public Requestable mRequest;

    public SimpleResponse(Requestable mRequest) {
        this.mRequest = mRequest;
    }

    @Override
    public void onResponse(JSONObject response) {

        mRequest.onSuccess();

    }

    @Override
    public void onErrorResponse(VolleyError error) {

        mRequest.onFailure();

    }
}