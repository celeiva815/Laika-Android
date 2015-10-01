package social.laika.app.listeners;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.AbsListView;
import android.widget.ListView;

import social.laika.app.activities.StoriesActivity;
import social.laika.app.adapters.StoriesAdapter;
import social.laika.app.utils.Tag;

/**
 * Created by Tito_Leiva on 24-04-15.
 */
public class StoriesRefreshListener implements AbsListView.OnScrollListener,
        SwipeRefreshLayout.OnRefreshListener {

    public static final String TAG = StoriesRefreshListener.class.getSimpleName();

    private StoriesActivity mActivity;
    private SwipeRefreshLayout mSwipeLayout;
    private ListView mListView;
    private StoriesAdapter mAdapter;
    private int mLastVisibleItem;
    private int mCurrentVisibleItem;
    private int mCurrentScrollState;

    public StoriesRefreshListener(StoriesActivity mActivity) {

        this.mActivity = mActivity;
        this.mSwipeLayout = mActivity.mSwipeLayout;
        this.mListView = mActivity.mStoriesListView;
        this.mAdapter = mActivity.mStoriesAdapter;
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

            int size = mActivity.mStories.size();
            int lastStoryId = mActivity.mStories.get(size - 1).mServerId;
            mActivity.requestStories(lastStoryId, Tag.LIMIT, mActivity);

        }
    }

    @Override
    public void onRefresh() {
        mActivity.requestStories(Tag.NONE, Tag.LIMIT, mActivity);

    }

}
