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
import cl.laikachile.laika.adapters.TipsAdapter;
import cl.laikachile.laika.listeners.TipsRefreshListener;
import cl.laikachile.laika.models.Tip;
import cl.laikachile.laika.network.RequestManager;
import cl.laikachile.laika.network.VolleyManager;
import cl.laikachile.laika.responses.TipsResponse;
import cl.laikachile.laika.utils.PrefsManager;
import cl.laikachile.laika.utils.Tag;

public class TipsActivity extends ActionBarActivity {

    public static final String TAG = TipsActivity.class.getSimpleName();
    private int mIdLayout = R.layout.lk_swipe_refresh_activity;

    public List<Tip> mTips;
    public SwipeRefreshLayout mSwipeLayout;
    public LinearLayout mEmptyLinearLayout;
    public ListView mTipsListView;
    public TipsAdapter mTipsAdapter;

    @Override
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

        if (mTipsListView.getCount() == 0) {

            mEmptyLinearLayout.setVisibility(View.VISIBLE);
            requestTips(Tag.NONE, Tag.LIMIT, getApplicationContext());
        }

        super.onStart();
    }

    public void setActivityView() {

        mTips = getTips();

        mEmptyLinearLayout = (LinearLayout) findViewById(R.id.empty_view);
        mTipsListView = (ListView) findViewById(R.id.main_listview);
        mTipsAdapter = new TipsAdapter(getApplicationContext(), R.layout.lk_tips_adapter,
                mTips);

        mTipsListView.setAdapter(mTipsAdapter);
        mTipsListView.setItemsCanFocus(true);

        mTipsListView.setEmptyView(mEmptyLinearLayout);

        //if (!isFavorite)
        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);

        TipsRefreshListener refreshListener = new TipsRefreshListener(this);

        mTipsListView.setOnScrollListener(refreshListener);
        onCreateSwipeRefresh(mSwipeLayout, refreshListener);

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

    private List<Tip> getTips() {

        return Tip.getTips();
    }

    public void requestTips(int lastTipId, int limit, Context context) {

        Map<String, String> params = new HashMap<>();

        if (lastTipId > Tag.NONE) {
            params.put(Tip.API_LAST_TIP_ID, Integer.toString(lastTipId));

        }
        params.put(Tip.API_LIMIT, Integer.toString(limit));

        TipsResponse response = new TipsResponse(this);

        Request tipsRequest = RequestManager.getRequest(params, RequestManager.ADDRESS_TIPS,
                response, response, PrefsManager.getUserToken(context));

        VolleyManager.getInstance(context)
                .addToRequestQueue(tipsRequest, TAG);

    }

    public void refreshList() {

        mEmptyLinearLayout.setVisibility(View.GONE);

        if (!mTips.isEmpty()) {
            mTips.clear();

        }

        mTips.addAll(getTips());
        mTipsAdapter.notifyDataSetChanged();

    }

}
