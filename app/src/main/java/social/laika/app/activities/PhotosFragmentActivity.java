package social.laika.app.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.List;

import social.laika.app.R;
import social.laika.app.fragments.PhotoScreenSlideFragment;
import social.laika.app.models.Photo;

public class PhotosFragmentActivity extends FragmentActivity{

    public static final String KEY_CURRENT_ITEM = "current_item";
    public static final String KEY_DOG_ID = "dog_id";
    private int mIdLayout = R.layout.ai_screen_slide_activity;
    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    public ViewPager mPager;
    private int mDogId;
    public List<Photo> mPhotos;
    public boolean mIsFullScreen;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    public PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int currentPosition = getIntent().getIntExtra(KEY_CURRENT_ITEM, 0);
        mDogId = getIntent().getIntExtra(KEY_DOG_ID, 0);
        mIsFullScreen = false;

        setContentView(mIdLayout);
        setPhotoList();

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setCurrentItem(currentPosition);
        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            public void onPageScrollStateChanged(int state) {
            }

            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            public void onPageSelected(int position) {

            }
        });
    }

    private void setPhotoList() {

        mPhotos = Photo.getPhotos(mDogId);
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

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
        	     	
            return new PhotoScreenSlideFragment(mPhotos.get(position), PhotosFragmentActivity.this);
        }

        @Override
        public int getCount() {
            return mPhotos.size();
        }
    }

}
