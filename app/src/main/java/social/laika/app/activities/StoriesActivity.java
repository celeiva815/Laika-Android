package social.laika.app.activities;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;

import com.android.volley.Request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import social.laika.app.R;
import social.laika.app.adapters.StoriesAdapter;
import social.laika.app.models.publications.Story;
import social.laika.app.network.Api;
import social.laika.app.network.VolleyManager;
import social.laika.app.responses.StoriesResponse;
import social.laika.app.utils.PrefsManager;
import social.laika.app.utils.Tag;

public class StoriesActivity extends BasePublicationsActivity {

    public static final String TAG = StoriesActivity.class.getSimpleName();
    
    public List<Story> mStories;

    @Override
    public void requestPublications(int lastPublicationId, int limit, Context context) {

        Map<String, String> params = new HashMap<>();

        if (lastPublicationId > Tag.NONE) {
            params.put(Story.API_LAST_STORY_ID, Integer.toString(lastPublicationId));

        }
        params.put(Story.API_LIMIT, Integer.toString(limit));

        StoriesResponse response = new StoriesResponse(this);

        Request storiesRequest = Api.getRequest(params, Api.ADDRESS_STORIES,
                response, response, PrefsManager.getUserToken(context));

        VolleyManager.getInstance(context)
                .addToRequestQueue(storiesRequest, TAG);

    }

    @Override
    public ArrayAdapter getAdapter() {
        return new StoriesAdapter(getApplicationContext(), R.layout.lk_stories_adapter,
                mStories);
    }

    public void refreshList() {

        mEmptyLinearLayout.setVisibility(View.GONE);

        if (!mStories.isEmpty()) {
            mStories.clear();

        }

        mStories.addAll(mIsFavorite ? getFavoritePublications() :
                getPublications());
        mPublicationsAdapter.notifyDataSetChanged();

    }

    @Override
    public void setPublications() {
        mStories = Story.getStories();

    }

    @Override
    public void setFavoritePublications() {
        mStories = Story.getFavoriteStories();

    }

    @Override
    public List getPublications() {
        return Story.getStories();
    }

    @Override
    public List getFavoritePublications() {
        return Story.getFavoriteStories();
    }

}
