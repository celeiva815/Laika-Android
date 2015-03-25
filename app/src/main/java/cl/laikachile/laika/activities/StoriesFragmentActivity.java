package cl.laikachile.laika.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import cl.laikachile.laika.R;
import cl.laikachile.laika.fragments.StoriesScreenSlideFragment;
import cl.laikachile.laika.models.Story;

public class StoriesFragmentActivity extends ActionBarActivity{
    
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
    private List<Story> stories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mIdLayout);
        setStoriesList();

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.color.laika_red));

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
    }

    private void setStoriesList() {

    	//FIXME ver con la API    	
    	this.stories = new ArrayList<Story>();
    	
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

    	stories.add(cholito);
        stories.add(gaspar);
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
       

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
        	     	
            return new StoriesScreenSlideFragment(stories.get(position));
        }

        @Override
        public int getCount() {
            return stories.size();	
        }
    }

}
