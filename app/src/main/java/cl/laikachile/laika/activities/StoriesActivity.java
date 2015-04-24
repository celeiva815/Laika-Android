package cl.laikachile.laika.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cl.laikachile.laika.R;
import cl.laikachile.laika.adapters.StoriesAdapter;
import cl.laikachile.laika.listeners.EndlessScrollListener;
import cl.laikachile.laika.models.Story;
import cl.laikachile.laika.utils.Do;

public class StoriesActivity extends ActionBarActivity {

    private int mIdLayout = R.layout.lk_swipe_refresh_activity;
    public List<Story> mStories;
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

        mStories = getStories(getApplicationContext());

        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        final ListView storiesListView = (ListView) findViewById(R.id.main_listview);
        TextView emptyTextView = (TextView) findViewById(R.id.empty_view);
        StoriesAdapter adapter = new StoriesAdapter(getApplicationContext(), R.layout.lk_events_adapter, mStories);

        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                Do.showToast("refresca!", getApplicationContext());
            }
        });
        mSwipeLayout.setColorScheme(R.color.light_white_font, R.color.light_laika_red,
                R.color.light_white_font, R.color.dark_laika_red);
        mSwipeLayout.setSize(SwipeRefreshLayout.LARGE);
        
        storiesListView.setAdapter(adapter);
        storiesListView.setItemsCanFocus(true);
        storiesListView.setOnScrollListener(new EndlessScrollListener(storiesListView, mSwipeLayout));

        emptyTextView.setText(R.string.stories_no_results);
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

    private List<Story> getStories(Context context) {

        //FIXME cambiar por la lógica de la API
        String[] stories = context.getResources().getStringArray(R.array.example_tips);
        List<Story> storiesList = new ArrayList<Story>(stories.length);

        Story cholito = new Story("Pinky y los Abuelitos", "Valentina Cornejo", "25 de Marzo de 2015, 12:59",
                "Después del fallecimiento de \"Tati\", la perrita de mis abuelos, me propuso " +
                        "buscarles otra para animarlos nuevamente. Así supe de Laika y me puse a " +
                        "investigar de qué se trataba. Hoy puedo decir que Pinky, como le pusieron ellos," +
                        " era la Perrita ideal para acompañarse. Una perra de 6 años, super cariñosa y " +
                        "que te acompaña a todos lados. Ellos están felices y ella con nueva casita",
                R.drawable.abuelo, Story.ID++);

        Story gaspar = new Story("Mi Mamá y Gaspar", "Fabiola Muñoz", "29 de Febrero de 2018, 12:59",
                "Esta foto se la tomé a mi mamá con su querido Gaspar. Ella ese día tuvo complicaciones médicas" +
                        "debido a su edad, nosotros no sabíamos como animarla hasta que cuando Laika" +
                        " me mandó una notificación en que Gaspar tenía que almorzar, se me ocurrió llevarlo al " +
                        " hospital. Los encargados me ayudaron a que eso pasara y aquí el resultado." +
                        " Hoy mi mamá está en su casa junto a Gaspar.", R.drawable.abuela, Story.ID++);

        storiesList.add(cholito);
        storiesList.add(gaspar);

        return storiesList;
    }

}
