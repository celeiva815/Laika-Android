package cl.laikachile.laika.activities;

import com.activeandroid.query.Select;

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

import java.util.List;

import cl.laikachile.laika.R;
import cl.laikachile.laika.fragments.MyDogsScreenSlideFragment;
import cl.laikachile.laika.fragments.NavigationDrawerFragment;
import cl.laikachile.laika.models.Dog;

public class MyDogsFragmentActivity extends FragmentActivity{
	
	protected NavigationDrawerFragment mNavigationDrawerFragment;
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
    private List<Dog> mDogs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mIdLayout);
        setDogList();
        
        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
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
    	
    	this.mDogs = new Select().from(Dog.class).execute();
    	
    	
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
        	     	
            return new MyDogsScreenSlideFragment(mDogs.get(position));
        }

        @Override
        public int getCount() {
            return mDogs.size();
        }
    }

}
