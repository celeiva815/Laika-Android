package cl.laikachile.laika.activities;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import java.util.List;

import cl.laikachile.laika.R;
import cl.laikachile.laika.fragments.PostulatedDogScreenSlideFragment;
import cl.laikachile.laika.models.Dog;
import cl.laikachile.laika.models.UserAdoptDog;
import cl.laikachile.laika.utils.Tag;

/**
 * Created by Tito_Leiva on 28-05-15.
 */
public class PostulatedDogsFragmentActivity extends AdoptDogsFragmentActivity {

    public List<UserAdoptDog> mUserAdoptDogs;

    @Override
    protected void setDogList() {

        mDogs = Dog.getDogs(Tag.DOG_POSTULATED);
        mUserAdoptDogs = UserAdoptDog.getUserAdoptDogs(mDogs);
        //TODO manejar el caso con el emptyView.
    }

    @Override
    protected void setViewPager() {

        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager(), this) {

            @Override
            public Fragment getItem(int position) {

                return new PostulatedDogScreenSlideFragment(mDogs.get(position),
                        mUserAdoptDogs.get(position), this.activity);
            }
        };

        mPager.setAdapter(mPagerAdapter);
    }

    public void updateDogs() {

        setDogList();
        mPagerAdapter.notifyDataSetChanged();
        
    }
}
