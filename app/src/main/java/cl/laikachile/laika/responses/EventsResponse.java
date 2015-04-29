package cl.laikachile.laika.responses;

import android.view.View;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import cl.laikachile.laika.activities.EventsActivity;
import cl.laikachile.laika.activities.LoginActivity;
import cl.laikachile.laika.models.Event;
import cl.laikachile.laika.network.utils.ResponseHandler;

/**
 * Created by Tito_Leiva on 13-04-15.
 */
public class EventsResponse implements Response.ErrorListener,
        Response.Listener<JSONObject>  {

    public EventsActivity mActivity;

    public EventsResponse(EventsActivity mActivity) {
        this.mActivity = mActivity;
    }

    @Override
    public void onResponse(JSONObject response) {

        stopRefreshing();
        Event.saveEvents(response);
        mActivity.refreshList();
    }

    @Override
    public void onErrorResponse(VolleyError error) {

        stopRefreshing();
        ResponseHandler.error(error, mActivity);

    }

    private void stopRefreshing() {

        if (mActivity.mSwipeLayout.isRefreshing()) {
            mActivity.mSwipeLayout.setRefreshing(false);
        }
    }

}
