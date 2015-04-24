package cl.laikachile.laika.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cl.laikachile.laika.R;
import cl.laikachile.laika.adapters.EventsAdapter;
import cl.laikachile.laika.listeners.EndlessScrollListener;
import cl.laikachile.laika.models.Event;
import cl.laikachile.laika.utils.Do;

public class EventsActivity extends ActionBarActivity {

    private int mIdLayout = R.layout.lk_swipe_refresh_activity;
    public List<Event> mEvents;
    public SwipeRefreshLayout mSwipeLayout;

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

        super.onStart();
    }

    public void setActivityView() {

        mEvents = getEvents(getApplicationContext());

        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        final ListView eventsListView = (ListView) findViewById(R.id.main_listview);
        TextView emptyTextView = (TextView) findViewById(R.id.empty_view);
        EventsAdapter adapter = new EventsAdapter(getApplicationContext(), R.layout.lk_events_adapter, mEvents);

        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                Do.showToast("refresca!", getApplicationContext());
            }
        });
        mSwipeLayout.setColorScheme(R.color.light_white_font, R.color.light_laika_red,
                R.color.light_white_font, R.color.dark_laika_red);
        mSwipeLayout.setSize(SwipeRefreshLayout.LARGE);

        eventsListView.setAdapter(adapter);
        eventsListView.setItemsCanFocus(true);
        eventsListView.setOnScrollListener(new EndlessScrollListener(eventsListView, mSwipeLayout));

        emptyTextView.setText(R.string.events_no_results);
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

    private List<Event> getEvents(Context context) {

        //FIXME cambiar por la lógica de la API
        String[] events = context.getResources().getStringArray(R.array.example_tips);
        List<Event> eventList = new ArrayList<Event>(events.length);

        Event jornada = new Event("JORNADA DE ESTERILIZACIÓN", "Ilustre Municipalidad de Las Condes",
                R.drawable.event_1, "http://www.oprachile.cl/", "Parque Padre Hurtado", "05 de Mayo de 2015", "", "09:00",
                "18:00", false);

        Event perroton = new Event("PERROTÓN", "Dog Chow", R.drawable.event_2, "https://www.perroton.dogchow.cl/",
                "Parque Los Dominicos", "23 de Junio de 2015", "", "11:00", "18:00", true);

        Event expo = new Event("EXPO MASCOTAS Y ANIMALES", "Royal Canin", R.drawable.event_3, "https://www.facebook.com/events/310987522413990/",
                "Espacio Riesco", "17 de Abril de 2015", "19 de Abril de 2015", "11:00", "20:00", true);

        Event curso = new Event("CURSO DE HIGIENE", "Fundación Stuka", R.drawable.event_4, "http://www.fundacionstuka.cl/",
                "Pajaritos 8980", "26 de Junio de 2015", "", "09:00", "18:00", false);

        Event running = new Event("PERRO RUNNING", "Ilustre Municipalidad de Viña del Mar",
                R.drawable.event_5, "http://www.vinadelmarchile.cl/", "Parque Padre Hurtado", "20 de Julio de 2015", "",
                "08:30", "14:00", false);

        eventList.add(jornada);
        eventList.add(perroton);
        eventList.add(expo);
        eventList.add(curso);
        eventList.add(running);

        return eventList;
    }

}
