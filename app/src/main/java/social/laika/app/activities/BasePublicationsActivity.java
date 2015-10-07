package social.laika.app.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.List;

import social.laika.app.R;
import social.laika.app.listeners.PublicationsRefreshListener;
import social.laika.app.models.publications.Publication;
import social.laika.app.utils.Tag;

/**
 * Created by Tito_Leiva on 07-10-15.
 */
public abstract class BasePublicationsActivity extends ActionBarActivity {

    public static String KEY_FAVORITE = "favorite";

    public int mIdLayout;
    public SwipeRefreshLayout mSwipeLayout;
    public LinearLayout mEmptyLinearLayout;
    public ListView mPublicationsListView;
    public ArrayAdapter mPublicationsAdapter;
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
        mPublicationsAdapter = getAdapter();

        mPublicationsListView.setAdapter(mPublicationsAdapter);
        mPublicationsListView.setItemsCanFocus(true);
        mPublicationsListView.setEmptyView(mEmptyLinearLayout);

        if (!mIsFavorite) {

            mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
            PublicationsRefreshListener refreshListener = new PublicationsRefreshListener(this);

            mSwipeLayout.setEnabled(true);
            mPublicationsListView.setOnScrollListener(refreshListener);
            onCreateSwipeRefresh(mSwipeLayout, refreshListener);

        } else {

            mSwipeLayout.setEnabled(false);



        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        if (!this.getClass().equals(MainActivity.class))
            getMenuInflater().inflate(R.menu.favorite_menu, menu);

        MenuItem favorites = menu.findItem(R.id.favorite_publications);
        favorites.setTitle(mIsFavorite ? "Ver Todo" : "Ver Favoritos");

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

        } else if (id == R.id.favorite_publications) {

            mIsFavorite = !mIsFavorite;
            setActivityView();
            refreshList();
            invalidateOptionsMenu();
        }

        return super.onOptionsItemSelected(item);
    }

    protected void onCreateSwipeRefresh(SwipeRefreshLayout refreshLayout,
                                      SwipeRefreshLayout.OnRefreshListener listener) {

        refreshLayout.setOnRefreshListener(listener);
        refreshLayout.setColorScheme(
                R.color.laika_light_red, R.color.light_white_font,
                R.color.laika_dark_red, R.color.light_white_font);
        refreshLayout.setSize(SwipeRefreshLayout.LARGE);

    }

    public abstract void requestPublications(int lastPublicationId, int limit, Context context);

    public void viewSettings() {

        if (mIsFavorite) {
            mIdLayout = R.layout.lk_favorite_layout_activity;
            setFavoritePublications();

        } else {
            mIdLayout = R.layout.lk_swipe_refresh_activity;
            setPublications();

        }
    }

    public abstract ArrayAdapter getAdapter();

    public abstract void refreshList();

    public abstract void setPublications();

    public abstract void setFavoritePublications();

    public abstract List getPublications();

    public abstract List getFavoritePublications();
}
