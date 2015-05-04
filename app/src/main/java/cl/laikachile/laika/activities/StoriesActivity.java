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
import cl.laikachile.laika.adapters.StoriesAdapter;
import cl.laikachile.laika.listeners.StoriesRefreshListener;
import cl.laikachile.laika.models.Story;
import cl.laikachile.laika.network.RequestManager;
import cl.laikachile.laika.network.VolleyManager;
import cl.laikachile.laika.responses.StoriesResponse;
import cl.laikachile.laika.utils.PrefsManager;
import cl.laikachile.laika.utils.Tag;

public class StoriesActivity extends ActionBarActivity {

    public static final String TAG = StoriesActivity.class.getSimpleName();
    
    private int mIdLayout = R.layout.lk_swipe_refresh_activity;
    public List<Story> mStories;
    public SwipeRefreshLayout mSwipeLayout;
    public LinearLayout mEmptyLinearLayout;
    public ListView mStoriesListView;
    public StoriesAdapter mStoriesAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mIdLayout);
        setActivityView();

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.color.laika_red));
        actionBar.setDisplayHomeAsUpEnabled(true);
    }


        @Override
    public void onStart() {

            if (mStoriesListView.getCount() == 0) {

                mEmptyLinearLayout.setVisibility(View.VISIBLE);
                requestStories(Tag.NONE, Tag.LIMIT, getApplicationContext());
            }

            super.onStart();
    }

    public void setActivityView() {

        mStories = getStories();


        mEmptyLinearLayout = (LinearLayout) findViewById(R.id.empty_view);
        mStoriesListView = (ListView) findViewById(R.id.main_listview);
        mStoriesAdapter = new StoriesAdapter(getApplicationContext(), R.layout.lk_stories_adapter, 
                mStories);

        mStoriesListView.setAdapter(mStoriesAdapter);
        mStoriesListView.setItemsCanFocus(true);

        mStoriesListView.setEmptyView(mEmptyLinearLayout);

        //if (!mIsFavorite)
        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        StoriesRefreshListener refreshListener = new StoriesRefreshListener(this);

        mStoriesListView.setOnScrollListener(refreshListener);
        onCreateSwipeRefresh(mSwipeLayout, refreshListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        if (!this.getClass().equals(MainActivity.class))
            getMenuInflater().inflate(R.menu.activity_main, menu);

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

    private List<Story> getStories() {

        return Story.getStories();
    }

    public void requestStories(int lastStoryId, int limit, Context context) {

        Map<String, String> params = new HashMap<>();

        if (lastStoryId > Tag.NONE) {
            params.put(Story.API_LAST_STORY_ID, Integer.toString(lastStoryId));

        }
        params.put(Story.API_LIMIT, Integer.toString(limit));

        StoriesResponse response = new StoriesResponse(this);

        Request storiesRequest = RequestManager.getRequest(params, RequestManager.ADDRESS_STORIES,
                response, response, PrefsManager.getUserToken(context));

        VolleyManager.getInstance(context)
                .addToRequestQueue(storiesRequest, TAG);

    }

    public void refreshList() {

        mEmptyLinearLayout.setVisibility(View.GONE);

        if (!mStories.isEmpty()) {
            mStories.clear();

        }

        mStories.addAll(getStories());
        mStoriesAdapter.notifyDataSetChanged();

    }

}
