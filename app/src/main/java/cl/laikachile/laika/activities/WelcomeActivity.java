package cl.laikachile.laika.activities;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import cl.laikachile.laika.R;
import cl.laikachile.laika.fragments.NavigationDrawerFragment;
import cl.laikachile.laika.fragments.SimpleFragment;
import cl.laikachile.laika.listeners.ToLoginActivityOnCLickListener;
import cl.laikachile.laika.listeners.ToMainActivityOnCLickListener;
import cl.laikachile.laika.models.indexes.Breed;
import cl.laikachile.laika.models.indexes.Personality;
import cl.laikachile.laika.models.indexes.Size;
import cl.laikachile.laika.utils.PrefsManager;
import cl.laikachile.laika.utils.Tag;

public class WelcomeActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    protected NavigationDrawerFragment mNavigationDrawerFragment;
    Fragment mFragment;
    private int mIdLayout = R.layout.lk_welcome_activity;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.ai_base_activity);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
    }

    @Override
    public void onStart() {

        createFragmentView(mIdLayout);
        super.onStart();

    }

    public void createFragmentView(int layoutId) {

        //FIXME agregar la clase hija
        mFragment = new SimpleFragment(layoutId, this);
        getFragmentManager().beginTransaction().add(R.id.container, mFragment).commit();
    }

    public void setActivityView(View view) {

        ImageView mainImageView = (ImageView) view.findViewById(R.id.main_logo_welcome_imageview);

        if (!PrefsManager.isUserLoggedIn(getApplicationContext())) {

            ToLoginActivityOnCLickListener listener = new ToLoginActivityOnCLickListener(this);
            mainImageView.setOnClickListener(listener);

        } else {

            ToMainActivityOnCLickListener listener = new ToMainActivityOnCLickListener(this);
            mainImageView.setOnClickListener(listener);
        }
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {

    }

}
