package cl.laikachile.laika.activities;

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

import java.util.ArrayList;
import java.util.List;

import cl.laikachile.laika.R;
import cl.laikachile.laika.fragments.NewsScreenSlideFragment;
import cl.laikachile.laika.models.News;

public class NewsFragmentActivity extends FragmentActivity{
    
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
    private List<News> news;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mIdLayout);
        setNewsList();

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
    }

    private void setNewsList() {

    	//FIXME ver con la API    	
    	this.news = new ArrayList<News>();
    	
    	News maipu = new News("La Municipalidad de Maip�, trabaja en conjunto con una serie de argrupaciones de animalistas con lasque se llevan a cabo pol�ticas conra el maltrato animal, adopci�n...", 
    			"Municipio de Maip�: \"La nueva brigada de perros callejeros dar� inclusi�n a todos\"", 
    			R.drawable.lk_news, 1); 
    			
    	News ptaArenas = new News("El alcalde de Punta Arenas, Emilio Boccazzi, present� en la sesi�n del Concejo Municipal una propuesta que contempla un contrato que permitir� la captura, esterilizaci�n, desparasitaci�n y vacunaci�n de perros...", 
    			"Municipalidad de Punta Arenas contrata empresa para esterilizar y vacunar perros callejeros", 
    			R.drawable.lk_news_picture_two, 2);
    	
    	news.add(maipu);
    	news.add(ptaArenas);
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
        	     	
            return new NewsScreenSlideFragment(news.get(position));
        }

        @Override
        public int getCount() {
            return news.size();	
        }
    }

}
