package social.laika.app.activities;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.viewpagerindicator.CirclePageIndicator;

import java.util.List;

import social.laika.app.R;
import social.laika.app.fragments.PostulatedDogScreenSlideFragment;
import social.laika.app.models.Dog;
import social.laika.app.models.UserAdoptDog;

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

        if (mPagerAdapter.getCount() == 0) {

            setContentView(R.layout.lk_adopt_dog_empty_layout);
            TextView emptyTextView = (TextView) findViewById(R.id.empty_view);
            emptyTextView.setText("Aún no has realizado postulaciones de adopción.\n" +
                    "Si quieres encontrar a tu perro ideal ingresa a \"Adóptame\"");

        } else {

            mPager.setAdapter(mPagerAdapter);

            mIndicator = (CirclePageIndicator) findViewById(R.id.page_indicator);
            mIndicator.setViewPager(mPager);
            mIndicator.setVisibility(View.GONE);
        }
    }

    public void updateDogs() {

        setDogList();
        mPagerAdapter.notifyDataSetChanged();

    }


}
