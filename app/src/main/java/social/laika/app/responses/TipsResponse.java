package social.laika.app.responses;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import social.laika.app.activities.TipsActivity;
import social.laika.app.models.Tip;
import social.laika.app.network.Api;

/**
 * Created by Tito_Leiva on 13-04-15.
 */
public class TipsResponse implements Response.ErrorListener,
        Response.Listener<JSONObject>  {

    public TipsActivity mActivity;

    public TipsResponse(TipsActivity mActivity) {
        this.mActivity = mActivity;
    }

    @Override
    public void onResponse(JSONObject response) {

        stopRefreshing();
        Tip.saveTips(response);
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
