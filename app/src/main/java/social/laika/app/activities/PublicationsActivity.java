package social.laika.app.activities;

import android.content.Context;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;

import com.android.volley.Request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import social.laika.app.R;
import social.laika.app.adapters.PublicationsAdapter;
import social.laika.app.models.publications.Publication;
import social.laika.app.network.Api;
import social.laika.app.network.VolleyManager;
import social.laika.app.responses.PublicationsResponse;
import social.laika.app.utils.PrefsManager;
import social.laika.app.utils.Tag;

public class PublicationsActivity extends BasePublicationsActivity {

    public static final String TAG = PublicationsActivity.class.getSimpleName();

    public List<Publication> mPublications;


    @Override
    public void requestPublications(int lastPublicationId, int limit, Context context) {

        Map<String, String> params = new HashMap<>();

        if (lastPublicationId > Tag.NONE) {
            params.put(Publication.API_LAST_PUBLICATION_ID, Integer.toString(lastPublicationId));

        }
        params.put(Publication.API_LIMIT, Integer.toString(limit));

        PublicationsResponse response = new PublicationsResponse(this);

        Request eventsRequest = Api.getRequest(params, Api.ADDRESS_PUBLICATIONS,
                response, response, PrefsManager.getUserToken(context));

        VolleyManager.getInstance(context)
                .addToRequestQueue(eventsRequest, TAG);

    }

    @Override
    public ArrayAdapter getAdapter() {

        return new PublicationsAdapter(getApplicationContext(), R.layout.lk_events_adapter,
                mPublications);
    }

    @Override
    public void refreshList() {

        mEmptyLinearLayout.setVisibility(View.GONE);

        if (!mPublications.isEmpty()) {
            mPublications.clear();

        }

        mPublications.addAll(mIsFavorite ? getFavoritePublications() :
                getPublications());
        mPublicationsAdapter.notifyDataSetChanged();

    }

    @Override
    public void setPublications() {
        mPublications = Publication.getPublications();
    }

    @Override
    public void setFavoritePublications() {
        mPublications = Publication.getFavorites();

    }

    @Override
    public List getPublications() {
        return Publication.getPublications();
    }

    @Override
    public List getFavoritePublications() {
        return Publication.getFavorites();
    }
}
