package cl.laikachile.laika.responses;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import cl.laikachile.laika.activities.TipsActivity;
import cl.laikachile.laika.models.Tip;

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
        ResponseHandler.error(error, mActivity);

    }

    private void stopRefreshing() {

        if (mActivity.mSwipeLayout.isRefreshing()) {
            mActivity.mSwipeLayout.setRefreshing(false);
        }
    }

}
