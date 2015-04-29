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
import cl.laikachile.laika.fragments.PublicationScreenSlideFragment;
import cl.laikachile.laika.models.Publication;

public class PublicationsFragmentActivity extends ActionBarActivity{
    
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
    private List<Publication> aNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mIdLayout);
        setNewsList();

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.color.laika_red));
        actionBar.setDisplayHomeAsUpEnabled(true);

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
    }

    private void setNewsList() {

    	//FIXME ver con la API    	
    	this.aNews = new ArrayList<Publication>();
    	
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
                "perla-primera-pelicula-chilena-donde-un-kiltro-es-su-protagonista/", true, true);
    	
    	aNews.add(maipu);
    	aNews.add(ptaArenas);
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
        	     	
            return new PublicationScreenSlideFragment(aNews.get(position));
        }

        @Override
        public int getCount() {
            return aNews.size();
        }
    }

}
