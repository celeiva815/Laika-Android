package cl.laikachile.laika.listeners;

import android.content.Context;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.AbsListView;
import android.widget.ListView;

import com.android.volley.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cl.laikachile.laika.activities.EventsActivity;
import cl.laikachile.laika.adapters.EventsAdapter;
import cl.laikachile.laika.models.Event;
import cl.laikachile.laika.network.RequestManager;
import cl.laikachile.laika.network.VolleyManager;
import cl.laikachile.laika.responses.EventsResponse;
import cl.laikachile.laika.responses.LoginResponse;
import cl.laikachile.laika.utils.PrefsManager;
import cl.laikachile.laika.utils.Tag;

/**
 * Created by Tito_Leiva on 24-04-15.
 */
public class EventsRefreshListener implements AbsListView.OnScrollListener,
        SwipeRefreshLayout.OnRefreshListener {

    public static final String TAG = EventsRefreshListener.class.getSimpleName();

    private EventsActivity mActivity;
    private SwipeRefreshLayout mSwipeLayout;
    private ListView mListView;
    private EventsAdapter mAdapter;
    private int mLastVisibleItem;
    private int mCurrentVisibleItem;
    private int mCurrentScrollState;

    public EventsRefreshListener(EventsActivity mActivity) {

        this.mActivity = mActivity;
        this.mSwipeLayout = mActivity.mSwipeLayout;
        this.mListView = mActivity.mEventsListView;
        this.mAdapter = mActivity.mEventsAdapter;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {

        int topRowVerticalPosition =
                (mListView == null || mListView.getChildCount() == 0) ?
                        0 : mListView.getChildAt(0).getTop();
        mSwipeLayout.setEnabled(firstVisibleItem == 0 && topRowVerticalPosition >= 0);

        this.mLastVisibleItem = totalItemCount;
        this.mCurrentVisibleItem = firstVisibleItem + visibleItemCount;

    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

        this.mCurrentScrollState = scrollState;
        this.isScrollCompleted(view.getContext());
    }

    private void isScrollCompleted(Context context) {
        if (this.mCurrentVisibleItem >= mLastVisibleItem &&
                this.mCurrentScrollState == SCROLL_STATE_IDLE
                && !mSwipeLayout.isRefreshing()) {

            mSwipeLayout.setRefreshing(true);

            int lastKingIndex = mAdapter.getCount() - 1;
            int lastEventId = mActivity.mEvents.get(lastKingIndex).mEventId;
            mActivity.requestEvents(lastEventId, Tag.LIMIT, mActivity);

        }
    }

    @Override
    public void onRefresh() {
        mActivity.requestEvents(Tag.NONE, Tag.LIMIT, mActivity);

    }

}
