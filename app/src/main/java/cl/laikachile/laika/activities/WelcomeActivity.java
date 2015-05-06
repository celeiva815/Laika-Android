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
import cl.laikachile.laika.models.AlarmReminder;
import cl.laikachile.laika.models.CalendarReminder;
import cl.laikachile.laika.models.Dog;
import cl.laikachile.laika.models.indexes.Breed;
import cl.laikachile.laika.models.indexes.Location;
import cl.laikachile.laika.models.Owner;
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

        boolean firstboot = PrefsManager.isFirstBoot(getApplicationContext());

        if (firstboot) {

            PrefsManager.setFirstBoot(getApplicationContext());
        }

        restartDataBase(firstboot);
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

    public void requestBreeds() {


    }

    public void restartDataBase(boolean firstBoot) {

        if (firstBoot) {

            Size.setSizes(getApplicationContext());
            Personality.setPersonalities(getApplicationContext());

            //FIXME hacer el metodo de la API
            for (int i = Tag.SIZE_SMALLER, id = 1; i <= Tag.SIZE_BIGGER; i++) {

                String[] breedNames;

                if (i == Tag.SIZE_SMALLER) {
                    breedNames = getResources().getStringArray(R.array.smaller_breed);

                } else if (i == Tag.SIZE_SMALL) {
                    breedNames = getResources().getStringArray(R.array.small_breed);

                } else if (i == Tag.SIZE_MIDDLE) {
                    breedNames = getResources().getStringArray(R.array.middle_breed);

                } else if (i == Tag.SIZE_BIG) {
                    breedNames = getResources().getStringArray(R.array.big_breed);

                } else {
                    breedNames = getResources().getStringArray(R.array.bigger_breed);

                }

                for (int j = 0; j < breedNames.length; j++, id++) {

                    int number = j;

                    if (breedNames[j].equals("Otro")) {
                        number = -1;
                    }

                    Breed breed = new Breed(id, number, i, breedNames[j]);
                    breed.save();

                }
            }

        }
    }
}
