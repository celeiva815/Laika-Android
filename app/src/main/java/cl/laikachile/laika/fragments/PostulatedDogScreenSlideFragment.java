package cl.laikachile.laika.fragments;

import android.app.Activity;
import android.view.View;

import cl.laikachile.laika.models.Dog;
import cl.laikachile.laika.models.UserAdoptDog;

/**
 * Created by Tito_Leiva on 28-05-15.
 */
public class PostulatedDogScreenSlideFragment extends AdoptDogScreenSlideFragment{

    public UserAdoptDog mUserAdoptDog;

    public PostulatedDogScreenSlideFragment(Dog mDog, UserAdoptDog mUserAdoptDog,
                                            Activity activity) {
        super(mDog, activity);
        this.mUserAdoptDog = mUserAdoptDog;
    }

    @Override
    public void setUserAdoptDog() {

        mIdTextView.setVisibility(View.VISIBLE);
        mFoundationTextView.setVisibility(View.VISIBLE);
        mStatusTextView.setVisibility(View.VISIBLE);

        mIdTextView.setText("ID:" + mUserAdoptDog.mUserAdoptDogId);
        mFoundationTextView.setText(mUserAdoptDog.mFoundationName);
        mStatusTextView.setText(mUserAdoptDog.mStatus);

    }
}
