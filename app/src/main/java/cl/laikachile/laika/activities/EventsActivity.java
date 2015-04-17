package cl.laikachile.laika.activities;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import cl.laikachile.laika.R;
import cl.laikachile.laika.adapters.EventsAdapter;
import cl.laikachile.laika.models.Event;

public class EventsActivity extends ActionBarActivity {

    private int mIdLayout = R.layout.lk_events_activity;
    public List<Event> mEvents;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mIdLayout);
        setActivityView();

        final android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.color.laika_red));
        actionBar.setDisplayHomeAsUpEnabled(true);
    }


        @Override
    public void onStart() {

        super.onStart();
    }

    public void setActivityView() {

        mEvents = getEvents(getApplicationContext());
        ListView eventsListView = (ListView) findViewById(R.id.events_listview);
        EventsAdapter adapter = new EventsAdapter(getApplicationContext(), R.layout.lk_events_adapter, mEvents);
        eventsListView.setAdapter(adapter);
        eventsListView.setItemsCanFocus(true);
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
