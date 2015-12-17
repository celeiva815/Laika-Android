package social.laika.app.activities;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;

import com.android.volley.Request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import social.laika.app.R;
import social.laika.app.adapters.EventsAdapter;
import social.laika.app.models.publications.Event;
import social.laika.app.network.Api;
import social.laika.app.network.VolleyManager;
import social.laika.app.responses.EventsResponse;
import social.laika.app.utils.Flurry;
import social.laika.app.utils.PrefsManager;
import social.laika.app.utils.Tag;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class EventsActivity extends BasePublicationsActivity {

    public static final String TAG = EventsActivity.class.getSimpleName();

    public List<Event> mEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Flurry.logTimedEvent(Flurry.EVENT_TIME);
    }

    @Override
    protected void onDestroy() {

        Flurry.endTimedEvent(Flurry.EVENT_TIME);

        super.onDestroy();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        if (!this.getClass().equals(HomeActivity.class))
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

    @Override
    public void requestPublications(int lastPublicationId, int limit, Context context) {

        Map<String, String> params = new HashMap<>();

        if (lastPublicationId > Tag.NONE) {
            params.put(Event.API_LAST_EVENT_ID, Integer.toString(lastPublicationId));

        }
        params.put(Event.API_LIMIT, Integer.toString(limit));

        EventsResponse response = new EventsResponse(this);

        Request eventsRequest = Api.getRequest(params, Api.ADDRESS_EVENTS,
                response, response, PrefsManager.getUserToken(context));

        VolleyManager.getInstance(context)
                .addToRequestQueue(eventsRequest, TAG);

    }

    @Override
    public ArrayAdapter getAdapter() {
        return new EventsAdapter(getApplicationContext(),
                R.layout.lk_events_adapter, mEvents);
    }

    public void refreshList() {

        mEmptyLinearLayout.setVisibility(View.GONE);

        if (!mEvents.isEmpty()) {
            mEvents.clear();

        }

        mEvents.addAll(getPublications());
        mPublicationsAdapter.notifyDataSetChanged();

    }

    @Override
    public void setPublications() {
        mEvents = Event.getEvents();
    }

    @Override
    public void setFavoritePublications() {
        mEvents = Event.getFavoriteEvents();

    }

    @Override
    public List getPublications() {
        return Event.getEvents();
    }

    @Override
    public List getFavoritePublications() {
        return Event.getFavoriteEvents();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
