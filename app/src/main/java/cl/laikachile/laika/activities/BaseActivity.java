package cl.laikachile.laika.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import cl.laikachile.laika.R;
import cl.laikachile.laika.fragments.NavigationDrawerFragment;
import cl.laikachile.laika.fragments.PlaceHolderFragment;
import cl.laikachile.laika.models.Publication;
import cl.laikachile.laika.utils.Do;
import cl.laikachile.laika.utils.PrefsManager;

/**
 * Created by Tito_Leiva on 10-02-15.
 */
public class BaseActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    protected NavigationDrawerFragment mNavigationDrawerFragment;
    protected CharSequence mTitle;
    protected PlaceHolderFragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ai_base_activity);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.color.laika_red));


        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

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

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onNavigationDrawerItemSelected(int position) {

        // update the main content by replacing fragments

        Intent intent;

        if (Do.isLoggedIn(this)) {
            //Logged in menu
            switch (position) {
                case 0: // Añadir nueva mascota
                    Do.changeActivity(this.getApplicationContext(), NewDogRegisterActivity.class,
                            Intent.FLAG_ACTIVITY_NEW_TASK);
                    break;

                case 1: // Mis postulaciones
                    Do.showToast("Por implementar", this.getApplicationContext());
                    break;

                case 2: // Favoritos

                    intent = new Intent(this.getApplicationContext(), PublicationsActivity.class);
                    intent.putExtra(PublicationsActivity.KEY_FAVORITE, true);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    break;

                case 3: // Mi perfil
                    Do.showToast("Por implementar", this.getApplicationContext());
                    break;

                case 4: // Cerrar Sesión
                    PrefsManager.clearPrefs(getApplicationContext());
                    Do.changeActivity(this.getApplicationContext(), LoginActivity.class,
                            Intent.FLAG_ACTIVITY_NEW_TASK);
                    break;

                default:
                    intent = new Intent(this.getApplicationContext(), MainActivity.class);
                    startActivity(intent);

                    break;
            }

        }
    }
    public void setActivityView(View view) {

    }

    public void createFragmentView(int layoutId) {

        //FIXME agregar la clase hija
        mFragment = new PlaceHolderFragment(layoutId, this);
        getFragmentManager().beginTransaction().add(R.id.container, mFragment).commit();
    }
}
