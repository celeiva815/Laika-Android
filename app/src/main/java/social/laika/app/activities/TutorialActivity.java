package social.laika.app.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import social.laika.app.R;
import social.laika.app.fragments.TutorialFragment;
import social.laika.app.network.RequestManager;
import social.laika.app.network.VolleyManager;
import social.laika.app.responses.FacebookLoginResponse;
import social.laika.app.utils.Do;
import social.laika.app.utils.PrefsManager;

import com.android.volley.Request;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.newrelic.agent.android.NewRelic;
import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


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

    /* Facebook Login */
    private CallbackManager mCallbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Initialize the defaults */
        initializer();

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
     * Facebook CallbackManager Single Instance ?
     * @return CallbackManager
     */
    public CallbackManager getCallbackManager() {
        if(mCallbackManager == null)
            mCallbackManager = CallbackManager.Factory.create();
        return mCallbackManager;
    }

    /**
     * Calling the CallbackManager :D
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "On Activity Result");
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initializer() {
        NewRelic.withApplicationToken(
                "AA602cfb22c1752b158a0b1d7465547e124ea262e4"
        ).start(this.getApplication());
        Log.d(TAG, "Started New Relic");
        FacebookSdk.sdkInitialize(getApplicationContext());
        Log.d(TAG, "Started Facebook Service");
//        mGoogleApiClient = new GoogleApiClient.Builder(this)
//                .addConnectionCallbacks(this)
//                .addOnConnectionFailedListener(this)
//                .addApi(Plus.API)
//                .addScope(Plus.SCOPE_PLUS_LOGIN)
//                .addScope(Plus.SCOPE_PLUS_PROFILE)//Scopes.PLUS_ME)
//                .build();
//        Log.d(TAG, "Started Google Service");

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
