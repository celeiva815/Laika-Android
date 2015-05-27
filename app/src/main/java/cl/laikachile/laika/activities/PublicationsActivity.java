package cl.laikachile.laika.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.android.volley.Request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cl.laikachile.laika.R;
import cl.laikachile.laika.adapters.PublicationsAdapter;
import cl.laikachile.laika.listeners.PublicationsRefreshListener;
import cl.laikachile.laika.models.Publication;
import cl.laikachile.laika.network.RequestManager;
import cl.laikachile.laika.network.VolleyManager;
import cl.laikachile.laika.responses.PublicationsResponse;
import cl.laikachile.laika.utils.PrefsManager;
import cl.laikachile.laika.utils.Tag;

public class PublicationsActivity extends ActionBarActivity {

    public static final String TAG = PublicationsActivity.class.getSimpleName();
    public static String KEY_FAVORITE = "favorite";

    public int mIdLayout;
    public List<Publication> mPublications;
    public SwipeRefreshLayout mSwipeLayout;
    public LinearLayout mEmptyLinearLayout;
    public ListView mPublicationsListView;
    public PublicationsAdapter mPublicationsAdapter;
    public boolean mIsFavorite = false;

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
        mPublicationsAdapter = new PublicationsAdapter(getApplicationContext(), R.layout.lk_events_adapter, mPublications);

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
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {
            super.onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    private void onCreateSwipeRefresh(SwipeRefreshLayout refreshLayout,
                                      SwipeRefreshLayout.OnRefreshListener listener) {

        refreshLayout.setOnRefreshListener(listener);
        refreshLayout.setColorScheme(
                R.color.light_laika_red, R.color.light_white_font,
                R.color.dark_laika_red, R.color.light_white_font);
        refreshLayout.setSize(SwipeRefreshLayout.LARGE);

    }

    public void requestPublications(int lastPublicationId, int limit, Context context) {

        Map<String, String> params = new HashMap<>();

        if (lastPublicationId > Tag.NONE) {
            params.put(Publication.API_LAST_PUBLICATION_ID, Integer.toString(lastPublicationId));

        }
        params.put(Publication.API_LIMIT, Integer.toString(limit));

        PublicationsResponse response = new PublicationsResponse(this);

        Request eventsRequest = RequestManager.getRequest(params, RequestManager.ADDRESS_PUBLICATIONS,
                response, response, PrefsManager.getUserToken(context));

        VolleyManager.getInstance(context)
                .addToRequestQueue(eventsRequest, TAG);

    }

    public void viewSettings() {

        if (mIsFavorite) {
            mIdLayout = R.layout.lk_favorite_layout_activity;
            mPublications = getFavoritePublications();

        } else {
            mIdLayout = R.layout.lk_swipe_refresh_activity;
            mPublications = getPublications();

        }
    }

    public void refreshList() {

        mEmptyLinearLayout.setVisibility(View.GONE);

        if (!mPublications.isEmpty()) {
            mPublications.clear();

        }

        mPublications.addAll(getPublications());
        mPublicationsAdapter.notifyDataSetChanged();

    }

    private List<Publication> getPublications() {

        return Publication.getPublications();
    }

    private List<Publication> getFavoritePublications() {

        return Publication.getFavorites();
    }

}
