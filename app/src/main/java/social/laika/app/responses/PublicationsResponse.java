package social.laika.app.responses;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import social.laika.app.activities.PublicationsActivity;
import social.laika.app.models.Publication;
import social.laika.app.network.Api;

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
        Api.error(error, mActivity);

    }

    private void stopRefreshing() {

        if (mActivity.mSwipeLayout.isRefreshing()) {
            mActivity.mSwipeLayout.setRefreshing(false);
        }
    }

}
