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

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent().hasExtra(KEY_FAVORITE)) {
            mIsFavorite = getIntent().getExtras().getBoolean(KEY_FAVORITE, false);
        }

        viewSettings();
        setContentView(mIdLayout);
        setActivityView();

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.color.laika_red));
        actionBar.setDisplayHomeAsUpEnabled(true);
    }


        @Override
    public void onStart() {

            if (mPublicationsListView.getCount() == 0) {

                mEmptyLinearLayout.setVisibility(View.VISIBLE);
                requestPublications(Tag.NONE, Tag.LIMIT, getApplicationContext());
            }

            super.onStart();
    }

    public void setActivityView() {

        mEmptyLinearLayout = (LinearLayout) findViewById(R.id.empty_view);
        mPublicationsListView = (ListView) findViewById(R.id.main_listview);
        mPublicationsAdapter = new PublicationsAdapter(this, R.layout.lk_events_adapter,
                mPublications);

        mPublicationsListView.setAdapter(mPublicationsAdapter);
        mPublicationsListView.setItemsCanFocus(true);
        mPublicationsListView.setEmptyView(mEmptyLinearLayout);

        if (!mIsFavorite) {

            mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);

            PublicationsRefreshListener refreshListener = new PublicationsRefreshListener(this);

            mPublicationsListView.setOnScrollListener(refreshListener);
            onCreateSwipeRefresh(mSwipeLayout, refreshListener);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        if (!this.getClass().equals(MainActivity.class))
            getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }

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
