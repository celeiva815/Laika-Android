package social.laika.app.activities;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;

import com.android.volley.Request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import social.laika.app.R;
import social.laika.app.adapters.TipsAdapter;
import social.laika.app.models.publications.Tip;
import social.laika.app.network.Api;
import social.laika.app.network.VolleyManager;
import social.laika.app.responses.TipsResponse;
import social.laika.app.utils.PrefsManager;
import social.laika.app.utils.Tag;

public class TipsActivity extends BasePublicationsActivity {

    public static final String TAG = TipsActivity.class.getSimpleName();

    public List<Tip> mTips;

    @Override
    public void requestPublications(int lastPublicationId, int limit, Context context) {

        Map<String, String> params = new HashMap<>();

        if (lastPublicationId > Tag.NONE) {
            params.put(Tip.API_LAST_TIP_ID, Integer.toString(lastPublicationId));

        }
        params.put(Tip.API_LIMIT, Integer.toString(limit));

        TipsResponse response = new TipsResponse(this);

        Request tipsRequest = Api.getRequest(params, Api.ADDRESS_TIPS,
                response, response, PrefsManager.getUserToken(context));

        VolleyManager.getInstance(context)
                .addToRequestQueue(tipsRequest, TAG);

    }

    @Override
    public ArrayAdapter getAdapter() {
        return new TipsAdapter(this, R.layout.lk_tips_adapter,
                mTips);
    }


    public void refreshList() {

        mEmptyLinearLayout.setVisibility(View.GONE);

        if (!mTips.isEmpty()) {
            mTips.clear();

        }

        mTips.addAll(mIsFavorite ? getFavoritePublications() :
                getPublications());
        mPublicationsAdapter.notifyDataSetChanged();

    }

    @Override
    public void setPublications() {

        mTips = Tip.getTips();

    }

    @Override
    public void setFavoritePublications() {

        mTips = Tip.getFavoriteTips();
    }

    @Override
    public List getPublications() {
        return Tip.getTips();
    }

    @Override
    public List getFavoritePublications() {
        return Tip.getFavoriteTips();
    }

}
