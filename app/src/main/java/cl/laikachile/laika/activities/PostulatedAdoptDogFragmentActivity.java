package cl.laikachile.laika.activities;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import cl.laikachile.laika.fragments.AdoptDogScreenSlideFragment;
import cl.laikachile.laika.models.Dog;
import cl.laikachile.laika.utils.Tag;

/**
 * Created by Tito_Leiva on 17-04-15.
 */
public class PostulatedAdoptDogFragmentActivity extends AdoptDogFragmentActivity{

    @Override
    protected void setDogList() {

        mDogs = Dog.getDogs(Tag.DOG_POSTULATED);

    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

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
