package cl.laikachile.laika.responses;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import cl.laikachile.laika.activities.PublicationsActivity;
import cl.laikachile.laika.models.Publication;

/**
 * Created by Tito_Leiva on 13-04-15.
 */
public class PublicationsResponse implements Response.ErrorListener,
        Response.Listener<JSONObject>  {

    public PublicationsActivity mActivity;

    public PublicationsResponse(PublicationsActivity mActivity) {
        this.mActivity = mActivity;
    }

    @Override
    public void onResponse(JSONObject response) {

        stopRefreshing();
        Publication.savePublications(response);
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
