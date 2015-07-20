package cl.laikachile.laika.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import cl.laikachile.laika.R;
import cl.laikachile.laika.adapters.DogsAdapter;
import cl.laikachile.laika.models.Dog;
import cl.laikachile.laika.models.City;
import cl.laikachile.laika.models.Owner;
import cl.laikachile.laika.network.VolleyManager;
import cl.laikachile.laika.utils.Do;
import cl.laikachile.laika.utils.PrefsManager;
import cl.laikachile.laika.utils.Tag;

public class UserProfileActivity extends ActionBarActivity {

    private static final String TAG = UserProfileActivity.class.getSimpleName();
    public static final String KEY_OWNER_ID = "owner_id";

    public int mIdLayout = R.layout.lk_user_profile_activity;
    public ImageView mProfileImageView;
    public TextView mNameTextView;
    public TextView mGenderTextView;
    public TextView mEmailTextView;
    public TextView mPhoneTextView;
    public TextView mBirthDateTextView;
    public TextView mCountryTextView;
    public TextView mRegionTextView;
    public TextView mLocationTextView;
    public ListView mDogsListView;
    public DogsAdapter mDogsAdapter;
    public City mCity;
    public int mOwnerId;
    public Owner mOwner;
    public List<Dog> mDogs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mOwnerId = getIntent().getExtras().getInt(KEY_OWNER_ID,
                PrefsManager.getUserId(getApplicationContext()));

        setInformation();
        setContentView(mIdLayout);
        setActivityView();

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.color.laika_red));
        actionBar.setDisplayHomeAsUpEnabled(true);

    }

    @Override
    protected void onStart() {
        super.onStart();

        setOwnerInformation();
        setValues();

    }

    public void setActivityView() {

        mProfileImageView = (ImageView) findViewById(R.id.user_profile_imageview);
        mNameTextView = (TextView) findViewById(R.id.name_user_profile_textview);
        mGenderTextView = (TextView) findViewById(R.id.gender_user_profile_textview);
        mEmailTextView = (TextView) findViewById(R.id.email_user_profile_textview);
        mPhoneTextView = (TextView) findViewById(R.id.phone_user_profile_textview);
        mBirthDateTextView = (TextView) findViewById(R.id.birth_date_user_profile_textview);
        mCountryTextView = (TextView) findViewById(R.id.country_user_profile_textview);
        mRegionTextView = (TextView) findViewById(R.id.region_user_profile_textview);
        mLocationTextView = (TextView) findViewById(R.id.location_user_profile_textview);
        mDogsListView = (ListView) findViewById(R.id.dogs_user_profile_listview);

        mDogsListView.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        mProfileImageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });

    }

    public void setValues() {

        Context context = getApplicationContext();
        mNameTextView.setText(mOwner.getFullName());
        mGenderTextView.setText(mOwner.getGender(context));
        mEmailTextView.setText(mOwner.mEmail);
        mPhoneTextView.setText(mOwner.mPhone);
        mBirthDateTextView.setText(mOwner.mBirthDate);
        mCountryTextView.setText(mCity.getCountry().mName);
        mRegionTextView.setText(mCity.getRegion().mName);
        mLocationTextView.setText(mCity.mCityName);

        mDogsAdapter = new DogsAdapter(context, R.layout.lk_dog_user_profile_row, mDogs);
        mDogsListView.setAdapter(mDogsAdapter);
        Do.setListViewHeightBasedOnChildren(mDogsListView);

        if (PrefsManager.isLoggedOwner(context, mOwner)) {
            mDogsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent intent = new Intent(getApplicationContext(), MyDogsActivity.class);
                    intent.putExtra(MyDogsActivity.DOG_ID, mDogs.get(position).mDogId);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();

                }
            });
        }

        setTitle("Perfil de " + mOwner.mFirstName);
    }

    @Override
    protected void onPause() {
        VolleyManager.getInstance(getApplicationContext()).cancelPendingRequests(TAG);
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        if (!this.getClass().equals(MainActivity.class))
            getMenuInflater().inflate(R.menu.user_profile_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {

            case android.R.id.home:

                super.onBackPressed();
                return true;

            case R.id.edit_profile:

                Do.changeActivity(getApplicationContext(), EditUserActivity.class,
                        Intent.FLAG_ACTIVITY_NEW_TASK);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setInformation() {
        setOwnerInformation();
        setDogs();

    }

    public void setOwnerInformation() {

        mOwner = Owner.getSingleOwner(mOwnerId);
        mCity = City.getSingleLocation(mOwner.mCityId);
    }

    public void setDogs() {

        mDogs = Dog.getDogs(Tag.DOG_OWNED);

    }

}
