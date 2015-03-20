package cl.laikachile.laika.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import cl.laikachile.laika.R;
import cl.laikachile.laika.fragments.AdoptDogScreenSlideFragment;
import cl.laikachile.laika.models.Dog;
import cl.laikachile.laika.utils.Tag;

public class AdoptDogFragmentActivity extends ActionBarActivity{
	
	private int mIdLayout = R.layout.ai_screen_slide_activity;
    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;
    private List<Dog> dogs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mIdLayout);
        setDogList();

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.color.red_background));
        
        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager(), this);
        mPager.setAdapter(mPagerAdapter);
        
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
		if (id == R.id.home_menu_button) {
			
			Intent homeIntent = new Intent(this, MainActivity.class);
			homeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(homeIntent);
		
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

    

    @Override
    public void onBackPressed() {
        /*if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.*/
            super.onBackPressed();
        /*} else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
         }*/ 
    }
    
    private void setDogList() {
    	
    	//FIXME aqu� deber�a tener la l�gica de la API, etc.
    	
    	this.dogs = new ArrayList<Dog>();
    	
    	//XXX hardcoded dogs
        Dog blanquito = new Dog(Dog.ID++,"Blanquito","13-02-2013","Mestizo",1,"Grande","Juguetón",true, false,"", Tag.PROCESS_ADOPTED,1000);
    	blanquito.mImage = R.drawable.lk_blanquito_picture;
        blanquito.save();

    	Dog pulguita = new Dog(Dog.ID++,"Pulguita","17-10-2013","Mestizo",2,"Mediano","Sociable",true, true,"", Tag.PROCESS_ADOPTED,1001);
        pulguita.mImage = R.drawable.lk_miko_picture;
        pulguita.save();

    	Dog filipa =new Dog(Dog.ID++,"Filipa","01-08-2014","Mestizo",2,"Mediano","Sociable",true, false,"", Tag.PROCESS_ADOPTED,1002);
        filipa.mImage = R.drawable.lk_milo_picture;
        filipa.save();

    	Dog alba = new Dog(Dog.ID++,"Alba","05-05-2014","Mestizo",2,"Mediano","Sociable",true, true,"", Tag.PROCESS_ADOPTED,1003);
        alba.mImage = R.drawable.lk_lolo_picture;
        alba.save();

    	blanquito.setDetail("Es un perrito de 3 años, rescatado de la calle. Es un perro tamaño grande, buen carácter, excelente para guardián. Necesita un hogar cariñoso que lo reciba como uno más de la familia.");
    	filipa.setDetail("Es una perrita tamaño medio, muy cariñosa y juguetona. Ella fue rescatada muy enferma, hoy 100% recuperada pero debe comer comida renal, por lo que quien la adopte debe tener eso en consideración. Esterilizada.");
    	pulguita.setDetail("Es una perrita de 4 meses, juguetona y cariñosa rescatada de la calle a punto de ser atropellada. Está vacunada y desparasitada y se entrega con compromiso de esterilización para cuando cumpla 6 meses.");
    	alba.setDetail("Es una perrita mestiza tamaño mediano que necesita un hogar para vivir. Le gusta mucho que le den cariño y se lleva muy bien con otros perros y gatos.");
    	
    	dogs.add(blanquito);
    	dogs.add(pulguita);
    	dogs.add(filipa);
    	dogs.add(alba);

    }
    

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
    	
    	Activity activity;
        public ScreenSlidePagerAdapter(FragmentManager fm, Activity activity) {
        	
        	super(fm);
        	this.activity = activity;            
        }

        @Override
        public Fragment getItem(int position) {
        	     	
            return new AdoptDogScreenSlideFragment(dogs.get(position), this.activity);
        }

        @Override
        public int getCount() {
            return dogs.size();
        }
    }

}
