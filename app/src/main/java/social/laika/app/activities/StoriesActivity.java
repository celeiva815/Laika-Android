package social.laika.app.activities;

import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
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
import social.laika.app.utils.Do;
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
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        if (!this.getClass().equals(MainActivity.class))
            getMenuInflater().inflate(R.menu.stories_menu, menu);

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

            return true;

        } else if (id == R.id.create_story) {

            Do.changeActivity(getApplicationContext(), CreateStoryActivity.class,
                    Intent.FLAG_ACTIVITY_NEW_TASK);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public ArrayAdapter getAdapter() {
        return new StoriesAdapter(this, R.layout.lk_stories_adapter,
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
