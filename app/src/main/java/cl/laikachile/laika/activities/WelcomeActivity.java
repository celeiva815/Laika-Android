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
import cl.laikachile.laika.models.Location;
import cl.laikachile.laika.models.Owner;
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
        // TODO Auto-generated method stub

    }

    public void restartDataBase(boolean firstBoot) {

        if (firstBoot) {

            Owner tito = new Owner(1, "tito_leiva", "Cristóbal", "Eduardo", "Leiva", "Sierra",
                    "15-02-1990", 1, "celeiva@uc.cl", "+56 9 96195432", "Salvador", "2111", "1702",
                    "Ñuñoa", "Santiago", "Chile");

            Owner nacho = new Owner(2, "NachoBiker", "Ignacio", "Valentín", "Gómez", "Muñoz",
                    "15-02-1990", 1, "ignacio.gomuz@gmail.com", "+56 9 92827933", "Salvador", "2111", "1702",
                    "Ñuñoa", "Santiago", "Chile");

            Owner cami = new Owner(3, "cami_pizza", "Camila", "Andrea", "Ávalos", "Berkhoff",
                    "15-02-1990", 2, "cami.avalosb@gmail.com", "+56 9 96195432", "Salvador", "2111", "1702",
                    "Ñuñoa", "Santiago", "Chile");

            Owner chuchuca = new Owner(4, "chuchuca", "Fabian", "Esteban", "Leiva", "Sierra",
                    "15-02-1990", 1, "fls1995@gmail.com", "+56 9 96195432", "Salvador", "2111", "1702",
                    "Ñuñoa", "Santiago", "Chile");

            Owner vale = new Owner(5, "hermosita<3", "Valentina", "Andrea", "Cornejo", "Morales",
                    "15-02-1990", 2, "valecornejomorales@gmail.com", "+56 9 96195432", "Salvador", "2111", "1702",
                    "Ñuñoa", "Santiago", "Chile");

            Dog tony = new Dog(1, "Tony", "13-02-2003", "Pastor Alemán", 1, "Grande", "Sociable", false, true,
                    "", Tag.PROCESS_OWNED, 1);

            Dog cachupin = new Dog(2, "Cachupin", "29-06-2010", "Mestizo", 1, "Mediano", "Juguetón", true,
                    false, "", Tag.PROCESS_OWNED, 3);

            tony.addOwner(tito, Tag.ROLE_ADMIN);
            tony.addOwner(chuchuca, Tag.ROLE_EDITOR);
            vale.addDog(tony, Tag.ROLE_EDITOR);

            cachupin.addOwner(tito, Tag.ROLE_EDITOR);
            nacho.addDog(cachupin, Tag.ROLE_EDITOR);
            cami.addDog(cachupin, Tag.ROLE_ADMIN);


            CalendarReminder medicine = new CalendarReminder(1, Tag.TYPE_CALENDAR, Tag.CATEGORY_VACCINE,
                    "Vacuna Antirábica", "Nobivac Rabia", "12-05-15", "12:00", 1, 1);
            CalendarReminder hygiene = new CalendarReminder(2, Tag.TYPE_CALENDAR, Tag.CATEGORY_HYGIENE,
                    "Baño", "Usar Shampoo de Perros", "13-01-15", "18:15", 2, 2);
            CalendarReminder vet = new CalendarReminder(3, Tag.TYPE_CALENDAR, Tag.CATEGORY_VET,
                    "Veterinaria Laika", "Preguntar por algo", "30-03-15", "16:30", 3, 1);

            AlarmReminder food = new AlarmReminder(1, Tag.TYPE_ALARM, Tag.CATEGORY_FOOD, "Almuerzo", "2 tazas", Tag.STATUS_IN_PROGRESS,
                    true, true, true, true, true, false, false, "14:00", 4, 2);
            AlarmReminder poo = new AlarmReminder(2, Tag.TYPE_ALARM, Tag.CATEGORY_POO, "Patio de la casa", "Usar bolsa especial", Tag.STATUS_IN_PROGRESS,
                    true, false, false, true, false, true, false, "09:00", 5, 1);
            AlarmReminder walk = new AlarmReminder(3, Tag.TYPE_ALARM, Tag.CATEGORY_WALK, "Parque Bicentenario", "Llevar Disco", Tag.STATUS_IN_PROGRESS,
                    false, false, false, false, false, true, true, "18:00", 1, 2);
            AlarmReminder medicineTwo = new AlarmReminder(4, Tag.TYPE_ALARM, Tag.CATEGORY_MEDICINE, "Antipulgas", "No hay que bañarlo", Tag.STATUS_IN_PROGRESS,
                    false, false, false, false, true, false, false, "06:30", 2, 1);

            /* int[] nino = { R.drawable.nino01, R.drawable.nino02, R.drawable.nino03, R.drawable.nino04,
                    R.drawable.nino05, R.drawable.nino06 };

            int[] filipo = { R.drawable.filipo1, R.drawable.filipo2, R.drawable.filipo3,
                    R.drawable.filipo4, R.drawable.filipo5, R.drawable.filipo6, R.drawable.filipo7,
                    R.drawable.filipo8, R.drawable.filipo9, R.drawable.filipo10};

            for (int i : nino) {

                Photo mPhoto = new Photo(Photo.ID++, "Hola", cachupin.mDogId, "", Do.now(), "Prueba",
                        i);
                mPhoto.save();
            }

            for (int i : filipo) {

                Photo mPhoto = new Photo(Photo.ID++, "Hola", tony.mDogId, "", Do.now(), "Prueba", i);
                mPhoto.save();
            } */

            tito.save();
            nacho.save();
            cami.save();
            chuchuca.save();
            vale.save();

            tony.save();
            cachupin.save();

            medicine.save();
            hygiene.save();
            vet.save();
            food.save();
            poo.save();
            walk.save();
            medicineTwo.save();

            Location.generateAvailableZones(getApplicationContext());
        }
    }
}
