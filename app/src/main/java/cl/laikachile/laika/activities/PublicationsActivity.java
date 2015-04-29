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
import cl.laikachile.laika.adapters.PublicationsAdapter;
import cl.laikachile.laika.listeners.EndlessScrollListener;
import cl.laikachile.laika.models.Publication;
import cl.laikachile.laika.utils.Do;

public class PublicationsActivity extends ActionBarActivity {

    public static String KEY_FAVORITE = "favorite";

    public int mIdLayout;
    public List<Publication> mPublications;
    public SwipeRefreshLayout mSwipeLayout;
    public SwipeRefreshLayout mEmptySwipeLayout;
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

        super.onStart();
    }

    public void setActivityView() {

        final ListView publicationsListView = (ListView) findViewById(R.id.main_listview);
        TextView emptyTextView = (TextView) findViewById(R.id.empty_view);
        PublicationsAdapter adapter = new PublicationsAdapter(getApplicationContext(), R.layout.lk_events_adapter, mPublications);

        publicationsListView.setAdapter(adapter);
        publicationsListView.setItemsCanFocus(true);
        emptyTextView.setText(R.string.publications_no_results);

        if (!mIsFavorite) {

            mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
            mSwipeLayout.setSize(SwipeRefreshLayout.LARGE);
            mSwipeLayout.setColorScheme(R.color.light_white_font,
                                        R.color.light_laika_red,
                                        R.color.light_white_font,
                                        R.color.dark_laika_red);
            mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

                @Override
                public void onRefresh() {

                    //TODO la actualización con la API
                    Do.showToast("refresca", getApplicationContext());
                }
            });
            publicationsListView.setOnScrollListener(new EndlessScrollListener(publicationsListView, mSwipeLayout));
            publicationsListView.setEmptyView(mEmptySwipeLayout);
        }


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

    private List<Publication> getPublications(Context context) {

        //FIXME cambiar por la lógica de la API
        String[] publications = context.getResources().getStringArray(R.array.example_tips);
        /* List<Publication> publicationList = new ArrayList<Publication>(publications.length);

        Publication maipu = new Publication(
                "Municipio de Maipú: \"La nueva brigada de perros callejeros dará inclusión a " +
                        "todos\"", "Pach News", "25 de marzo de 2015, 11:02","La Municipalidad de "+
                "Maipú, trabaja en conjunto con una serie de agrupaciones de animalistas con las " +
                "que se llevan a cabo políticas contra el maltrato animal, adopción...",
                R.drawable.lk_news, 1, "http://pachnews.cl/?p=10480", false, false);

        Publication ptaArenas = new Publication("Municipalidad de Punta Arenas contrata empresa para " +
                "esterilizar y vacunar perros callejeros", "Prensa Animalista", "10 de enero de " +
                "2015, 17:32","El alcalde de Punta Arenas, Emilio Boccazzi, presentó en la " +
                "sesión del Concejo Municipal una propuesta que contempla un contrato que " +
                "permitirá la captura, esterilización, desparasitación y vacunación de perros...",
                R.drawable.lk_news_picture_two, 2, "http://www.prensanimalista.cl/web/2015/03/16/" +
                "perla-primera-pelicula-chilena-donde-un-kiltro-es-su-protagonista/",true,true);

        publicationList.add(maipu);
        publicationList.add(ptaArenas); */

        List<Publication> publicationList = new ArrayList<Publication>();
        return publicationList;
    }

    private List<Publication> getFavoritePublications() {

        return Publication.getFavorites();
    }

    public void viewSettings() {

        if (mIsFavorite) {
            mIdLayout = R.layout.lk_favorite_layout_activity;
            mPublications = getFavoritePublications();

        } else {
            mIdLayout = R.layout.lk_swipe_refresh_activity;
            mPublications = getPublications(getApplicationContext());

        }
    }

}
