package cl.laikachile.laika.activities;

import java.util.List;

import android.app.Activity;
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

import cl.laikachile.laika.R;
import cl.laikachile.laika.fragments.AdoptDogScreenSlideFragment;
import cl.laikachile.laika.models.Dog;
import cl.laikachile.laika.utils.Tag;

public class AdoptDogsFragmentActivity extends ActionBarActivity{
	
	protected int mIdLayout = R.layout.ai_screen_slide_activity;
    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    protected ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    protected PagerAdapter mPagerAdapter;
    protected List<Dog> mDogs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mIdLayout);
        setDogList();

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.color.laika_red));
        actionBar.setDisplayHomeAsUpEnabled(true);

        setViewPager();

    }
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		if (!this.getClass().equals(MainActivity.class))
			getMenuInflater().inflate(R.menu.main_menu, menu);
		
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
    
    protected void setDogList() {
    	
    	mDogs = Dog.getDogs(Tag.DOG_FOUNDATION);

    }

    protected void setViewPager() {

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager(), this);
        mPager.setAdapter(mPagerAdapter);

    }


    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    protected class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
    	
    	Activity activity;
        public ScreenSlidePagerAdapter(FragmentManager fm, Activity activity) {
        	
        	super(fm);
        	this.activity = activity;            
        }

        @Override
        public Fragment getItem(int position) {
        	     	
            return new AdoptDogScreenSlideFragment(mDogs.get(position), this.activity);
        }

        @Override
        public int getCount() {
            return mDogs.size();
        }
    }

}
