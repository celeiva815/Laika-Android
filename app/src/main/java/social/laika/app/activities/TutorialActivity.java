package social.laika.app.activities;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import social.laika.app.R;
import social.laika.app.fragments.TutorialFragment;
import social.laika.app.utils.Do;
import social.laika.app.utils.PrefsManager;

import com.facebook.Profile;
import com.newrelic.agent.android.NewRelic;
import com.viewpagerindicator.CirclePageIndicator;

import java.security.MessageDigest;


public class TutorialActivity extends ActionBarActivity {
    private static final String TAG = TutorialActivity.class.getSimpleName();

    // the number of pages of tutorial to be shown
    public static final int NUM_PAGES = 5;

    // The pager widget, which handles animation and allows swiping horizontally to access
    // previous and next pages
    public ViewPager mPager;

    // The pager adapter, which provides the pages to the view pager widget
    private PagerAdapter mPagerAdapter;
    private CirclePageIndicator mIndicator;

    private Button mQuitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "New Relic Initialized");
        NewRelic.withApplicationToken(
                "AA602cfb22c1752b158a0b1d7465547e124ea262e4"
        ).start(this.getApplication());


        if (PrefsManager.isUserLoggedIn(getApplicationContext())) {

            Do.changeActivity(getApplicationContext(), MainActivity.class, this,
                    Intent.FLAG_ACTIVITY_NEW_TASK);
        }


        getSupportActionBar().hide(); // hide the actionbar
        setContentView(R.layout.lk_indicator_view_pager_activity);

        // Instantiate a ViewPager and a PagerAdapter
        mPager = (ViewPager) findViewById(R.id.view_pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);

        mIndicator = (CirclePageIndicator) findViewById(R.id.page_indicator);
        mIndicator.setViewPager(mPager);

        // Add code to print out the key hash
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "social.laika.app",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d(TAG, Base64.encodeToString(md.digest(), Base64.DEFAULT));
                Profile profile = Profile.getCurrentProfile();
                String first_name = profile.getFirstName();
                String last_name = profile.getLastName();
                Log.i(TAG, first_name);
                Log.i(TAG, last_name);
            }
        } catch (Exception e) {
            Log.i(TAG, e.toString());
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tutorial, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    /**
     * A simple pager adapter that represents 3 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            return TutorialFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}
