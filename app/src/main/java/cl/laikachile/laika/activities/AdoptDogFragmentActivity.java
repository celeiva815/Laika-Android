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
import android.view.Menu;
import android.view.MenuItem;

import cl.laikachile.laika.R;
import cl.laikachile.laika.fragments.AdoptDogScreenSlideFragment;
import cl.laikachile.laika.models.Dog;

public class AdoptDogFragmentActivity extends FragmentActivity{
	
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
    	Dog blanquito = new Dog(100, 10, "Blanquito", "02-05-2011", R.drawable.lk_blanquito_picture, "Mestizo", 1, 5, "Macho", "Mediano", "Sociable", Dog.STATUS_ADOPTED, 1, 98);
    	Dog pulguita = new Dog(101, 10, "Pulguita", "17-10-2013", R.drawable.lk_miko_picture, "Mestizo", 1, 5, "Hembra", "Mediano", "Sociable", Dog.STATUS_ADOPTED, 1,83);
    	Dog filipa = new Dog(102, 10, "Filipa", "01-08-2014", R.drawable.lk_milo_picture, "Mestizo", 1, 5, "Hembra", "Mediano", "Sociable", 1, Dog.STATUS_ADOPTED, 79);
    	Dog alba = new Dog(103, 10, "Alba", "05-05-2014", R.drawable.lk_lolo_picture, "Mestizo", 1, 5, "Hembra", "Mediano", "Sociable", 1, Dog.STATUS_ADOPTED, 65);
    	
    	blanquito.setaStory("Es un perrito de 3 a�os, rescatado de la calle. Es un perro tama�o grande, buen car�cter, excelente para guardi�n. Necesita un hogar cari�oso que lo reciba como uno m�s de la familia.");
    	filipa.setaStory("Es una perrita tama�o medio, muy cari�osa y juguetona. Ella fue rescatada muy enferma, hoy 100% recuperada pero debe comer comida renal, por lo que quien la adopte debe tener eso en consideraci�n. Esterilizada.");
    	pulguita.setaStory("Es una perrita de 4 meses, juguetona y cari�osa rescatada de la calle a punto de ser atropellada. Est� vacunada y desparasitada y se entrega con compromiso de esterilizaci�n para cuando cumpla 6 meses.");
    	alba.setaStory("Es una perrita mestiza tama�o mediano que necesita un hogar para vivir. Le gusta mucho que le den cari�o y se lleva muy bien con otros perros y gatos."); //TODO hacer una historia para alba
    	
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
