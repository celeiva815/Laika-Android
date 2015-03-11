package cl.laikachile.laika.activities;

import com.activeandroid.query.Delete;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;

import cl.laikachile.laika.R;
import cl.laikachile.laika.fragments.NavigationDrawerFragment;
import cl.laikachile.laika.fragments.SimpleFragment;
import cl.laikachile.laika.listeners.GPlusSignInOnClickListener;
import cl.laikachile.laika.listeners.ToMainActivityOnCLickListener;
import cl.laikachile.laika.models.AlarmReminder;
import cl.laikachile.laika.models.CalendarReminder;
import cl.laikachile.laika.models.Dog;
import cl.laikachile.laika.models.Owner;
import cl.laikachile.laika.models.OwnerDog;
import cl.laikachile.laika.utils.Tag;

public class WelcomeActivity extends Activity 
					implements NavigationDrawerFragment.NavigationDrawerCallbacks{
	
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
		
		boolean firstboot = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getBoolean("firstboot", true);
		
		if (!firstboot) {
	        
	    	PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putBoolean("firstboot", true).commit();
	    }
		
		restartDataBase();
		createFragmentView(mIdLayout);
		super.onStart();
		
	}
	
	public void createFragmentView(int layoutId) {
		
		//FIXME agregar la clase hija
		mFragment = new SimpleFragment(layoutId, this);
		getFragmentManager().beginTransaction().add(R.id.container, mFragment).commit();
	}

	public void setActivityView(View view) {
		
		ImageView gPlusImageView = (ImageView) view.findViewById(R.id.gplus_sign_in_imageview);
		ImageView mainImageView = (ImageView) view.findViewById(R.id.main_logo_welcome_imageview);
		
		GPlusSignInOnClickListener gPlusListener = new GPlusSignInOnClickListener(this);
		gPlusImageView.setOnClickListener(gPlusListener);
		
		ToMainActivityOnCLickListener listener = new ToMainActivityOnCLickListener(this);
		mainImageView.setOnClickListener(listener);
		
	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		// TODO Auto-generated method stub
		
	}
	
	public void restartDataBase() {
		
		new Delete().from(Dog.class).execute();
        new Delete().from(Owner.class).execute();
        new Delete().from(OwnerDog.class).execute();
        new Delete().from(CalendarReminder.class).execute();
        new Delete().from(AlarmReminder.class).execute();

        /*
        int mOwnerId, String mOwnerName, String mFirstName, String mSecondName,
                 String mFirstSurname, String mSecondSurname, String mBirthday, int mGender,
                 String mEmail, String mPhone, String mAddress, String mAddressNumber,
                 String mApartmentNumber, String mTown, String mCity, String mCountry)
         */
		
		Owner tito = new Owner(1, "tito_leiva", "Cristóbal", "Eduardo", "Leiva", "Sierra",
                "15-02-1990", 1, "celeiva@uc.cl", "+56 9 96195432", "Salvador", "2111", "1702",
                "Ñuñoa", "Santiago", "Chile");

		Owner nacho = new Owner(2, "NachoBiker", "Ignacio", "Valentín", "Gómez", "Muñoz",
                "15-02-1990", 1, "ignacio.gomuz@gmail.com", "+56 9 96195432", "Salvador", "2111", "1702",
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

		Dog tony = new Dog(1, 10, "Tony", "13-02-2003", R.drawable.lk_tony_profile, "Pastor Alemán", 1, 5, "Macho", "Grande", "Sociable",Dog.STATUS_OWN, 1, 98);
    	Dog cachupin = new Dog(2, 10, "Cachupin", "01-12-2013", R.drawable.lk_cachupin_profile, "Mestizo", 1, 5, "Macho", "Mediano", "Juguetón", Dog.STATUS_OWN, 1,83);


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
        CalendarReminder vet = new CalendarReminder(3, Tag.TYPE_CALENDAR,Tag.CATEGORY_VET,
                "Veterinaria Laika", "Preguntar por algo", "30-03-15", "16:30", 3, 1);

        AlarmReminder food = new AlarmReminder(1, Tag.TYPE_ALARM, Tag.CATEGORY_FOOD, "Almuerzo", "2 tazas", Tag.STATUS_IN_PROGRESS,
                true, true,true, true, true, false, false, "14:00", 4, 2);
        AlarmReminder poo = new AlarmReminder(2, Tag.TYPE_ALARM,Tag.CATEGORY_POO, "Patio de la casa", "Usar bolsa especial", Tag.STATUS_IN_PROGRESS,
                true, false,false, true, false, true, false, "09:00", 5, 1);
        AlarmReminder walk = new AlarmReminder(3, Tag.TYPE_ALARM,Tag.CATEGORY_WALK, "Parque Bicentenario", "Llevar Disco", Tag.STATUS_IN_PROGRESS,
                false, false,false, false, false, true, true, "18:00", 1, 2);
        AlarmReminder medicinetwo = new AlarmReminder(4, Tag.TYPE_ALARM,Tag.CATEGORY_MEDICINE, "Antipulgas", "No hay que bañarlo", Tag.STATUS_IN_PROGRESS,
                false, false,false, false, true, false, false, "06:30", 2, 1);

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
        medicinetwo.save();
    }
}
