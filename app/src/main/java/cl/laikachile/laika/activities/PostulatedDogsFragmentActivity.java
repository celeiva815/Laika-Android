package cl.laikachile.laika.activities;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.viewpagerindicator.CirclePageIndicator;

import java.util.Collections;
import java.util.List;

import cl.laikachile.laika.R;
import cl.laikachile.laika.fragments.PostulatedDogScreenSlideFragment;
import cl.laikachile.laika.models.Dog;
import cl.laikachile.laika.models.UserAdoptDog;
import cl.laikachile.laika.utils.DogSort;

/**
 * Created by Tito_Leiva on 28-05-15.
 */
public class PostulatedDogsFragmentActivity extends AdoptDogsFragmentActivity {

    public List<UserAdoptDog> mUserAdoptDogs;

    @Override
    protected void setDogList() {

        mUserAdoptDogs = UserAdoptDog.getUserAdoptDogs();
        mDogs = Dog.getDogs(mUserAdoptDogs);
    }

    @Override
    protected void setViewPager() {

        mPager = (ViewPager) findViewById(R.id.view_pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager(), this) {

            @Override
            public Fragment getItem(int position) {

                return new PostulatedDogScreenSlideFragment(mDogs.get(position),
                        mUserAdoptDogs.get(position), this.activity);
            }
        };

        mPager.setAdapter(mPagerAdapter);

        mIndicator = (CirclePageIndicator) findViewById(R.id.page_indicator);
        mIndicator.setViewPager(mPager);
    }

    public void updateDogs() {

        setDogList();
        mPagerAdapter.notifyDataSetChanged();
        
    }


}
