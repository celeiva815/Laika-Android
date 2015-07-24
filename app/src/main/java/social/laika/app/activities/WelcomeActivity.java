package social.laika.app.activities;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import social.laika.app.R;
import social.laika.app.fragments.NavigationDrawerFragment;
import social.laika.app.fragments.SimpleFragment;
import social.laika.app.listeners.ToLoginActivityOnCLickListener;
import social.laika.app.listeners.ToMainActivityOnCLickListener;
import social.laika.app.utils.PrefsManager;

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
