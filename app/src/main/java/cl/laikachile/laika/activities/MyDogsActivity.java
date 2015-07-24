package cl.laikachile.laika.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Request;
import com.soundcloud.android.crop.Crop;

import org.json.JSONObject;

import cl.laikachile.laika.R;
import cl.laikachile.laika.fragments.AlbumMyDogFragment;
import cl.laikachile.laika.fragments.HistoryMyDogFragment;
import cl.laikachile.laika.fragments.OwnersFragment;
import cl.laikachile.laika.fragments.RemindersMyDogFragment;
import cl.laikachile.laika.interfaces.Photographable;
import cl.laikachile.laika.models.AlarmReminder;
import cl.laikachile.laika.models.CalendarReminder;
import cl.laikachile.laika.models.Dog;
import cl.laikachile.laika.network.RequestManager;
import cl.laikachile.laika.network.VolleyManager;
import cl.laikachile.laika.responses.ImageUploadResponse;
import cl.laikachile.laika.utils.Do;
import cl.laikachile.laika.utils.Photographer;
import cl.laikachile.laika.utils.views.CustomPagerSlidingTabStrip;

public class MyDogsActivity extends ActionBarActivity implements Photographable {

    public static final String DOG_ID = "dog_id";
    public static final String HISTORY = "Historial";
    public static final String REMINDERS = "Recordatorios";
    public static final String OWNERS = "Dueños";
    public static final String ALBUM = "Album";
    public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1;
    public static final String[] CONTENT = new String[]{HISTORY, REMINDERS, OWNERS, ALBUM};
    public static final int[] ICONS = new int[]{
            R.drawable.laika_history_selector,
            R.drawable.laika_reminder_selector,
            R.drawable.laika_owner_selector,
            R.drawable.laika_album_selector
    };

    public int mIdLayout = R.layout.activity_my_dog;
    public Dog mDog;
    public HistoryMyDogFragment mHistoryFragment;
    public RemindersMyDogFragment mRemindersFragment;
    public OwnersFragment mOwnerFragment;
    public AlbumMyDogFragment mAlbumFragment;
    public String mCurrentPhotoPath;
    public ViewPager mPager;
    public PagerAdapter mPagerAdapter;
    public CustomPagerSlidingTabStrip mIndicator;
    public Photographer mPhotographer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(mIdLayout);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.color.laika_red));
        actionBar.setDisplayHomeAsUpEnabled(true);

        int dogId = getIntent().getIntExtra(DOG_ID, 0);
        mDog = Dog.getSingleDog(dogId);
        mPhotographer = new Photographer();

        // Instantiate a ViewPager and a PagerAdapter
        mPager = (ViewPager) findViewById(R.id.my_dog_pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);

        mIndicator = (CustomPagerSlidingTabStrip) findViewById(R.id.my_dog_indicator);
        mIndicator.setViewPager(mPager);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        if (!this.getClass().equals(MainActivity.class))
            getMenuInflater().inflate(R.menu.activity_my_dog, menu);

        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();


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

            case R.id.camera_menu_button:

                takePhoto();
                return true;

            case R.id.dog_settings:

                Intent intent = new Intent(getApplicationContext(), EditDogActivity.class);
                intent.putExtra(EditDogActivity.KEY_DOG_ID, mDog.mDogId);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setHistoryFragment(Dog mDog) {

        if (mHistoryFragment != null) {
            mHistoryFragment.refreshHistories();

        } else {
            mHistoryFragment = HistoryMyDogFragment.newInstance(mDog.mDogId);
        }

        mPager.setCurrentItem(getPagerPosition(HISTORY), true);

    }

    public void setReminderFragment(AlarmReminder alarmReminder) {

        mRemindersFragment = RemindersMyDogFragment.newInstance(mDog.mDogId,
                alarmReminder.mAlarmReminderId, RemindersMyDogFragment.KEY_ALARM);

        mPager.setCurrentItem(getPagerPosition(REMINDERS), true);
    }

    public void setReminderFragment(CalendarReminder calendarReminder) {

        mRemindersFragment = RemindersMyDogFragment.newInstance(mDog.mDogId,
                calendarReminder.mCalendarReminderId, RemindersMyDogFragment.KEY_CALENDAR);

        mPager.setCurrentItem(getPagerPosition(REMINDERS), true);
    }

    public void setHealthFragment() {


    }

    public void setOwnerFragment(Dog mDog) {


    }

    public void setAlbumFragment(Dog mDog) {


    }

    public int getPagerPosition(String tab) {

        for (int i = 0; i < CONTENT.length; i++) {

            if (CONTENT[i].equals(tab)) {
                return i;
            }
        }

        return 0;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent result) {

        if (requestCode == Photographer.SQUARE_CAMERA_REQUEST_CODE &&
                resultCode == RESULT_OK) {

            if (result != null) {

                cropPhoto(result.getData());

            } else if (mPhotographer.mSourceImage != null) {

                cropPhoto(mPhotographer.mSourceImage);

            } else {
                Do.showLongToast(R.string.generic_networking_error, getApplicationContext());
            }

            super.onActivityResult(requestCode, resultCode, result);

        }

        if (requestCode == Crop.REQUEST_PICK
                && resultCode == RESULT_OK) {

            cropPhoto(result.getData());

        } else if (requestCode == Crop.REQUEST_CROP) {

            uploadPhoto();

        }
    }

    @Override
    public void takePhoto() {

        mPhotographer.takePicture(this);

    }

    @Override
    public void pickPhoto() {

        mPhotographer.pickImage(this);
    }

    @Override
    public void cropPhoto(Uri source) {

        mPhotographer.beginCrop(source, this);
    }

    @Override
    public void uploadPhoto() {

        if (Do.isNetworkAvailable(getApplicationContext())) {

            Context context = getApplicationContext();

            JSONObject jsonPhoto = mPhotographer.getJsonPhoto(context);
            ImageUploadResponse response = new ImageUploadResponse(mDog, this, context);
            response.mProgressDialog = ProgressDialog.show(MyDogsActivity.this, "Espera un momento",
                    "Estamos subiendo la foto");

            Request request = RequestManager.postImage(mDog.mDogId, jsonPhoto, context,
                    response, response);

            VolleyManager.getInstance(context).addToRequestQueue(request);

        } else {

            Do.showLongToast("Debes estar conectado para subir fotos", getApplicationContext());
        }

    }

    @Override
    public void succeedUpload() {

        Do.showShortToast("la foto ha sido subida correctamente", getApplicationContext());
        mAlbumFragment.refreshPhotos();

    }

    @Override
    public void failedUpload() {

        Do.showShortToast("La foto no se subió, pero quedó guardada en tu dispositivo.",
                getApplicationContext());

    }

    private class ScreenSlidePagerAdapter extends FragmentPagerAdapter implements CustomPagerSlidingTabStrip.IconTabProvider {

        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            int dogId = mDog.mDogId;

            switch (position) {

                case 0:

                    if (mHistoryFragment == null) {
                        mHistoryFragment = HistoryMyDogFragment.newInstance(dogId);
                    }

                    return mHistoryFragment;

                case 1:

                    if (mRemindersFragment == null) {
                        mRemindersFragment = RemindersMyDogFragment.newInstance(dogId);

                    }

                    return mRemindersFragment;

                case 2:

                    if (mOwnerFragment == null) {
                        mOwnerFragment = OwnersFragment.newInstance(dogId);

                    }

                    return mOwnerFragment;

                case 3:

                    if (mAlbumFragment == null) {
                        mAlbumFragment = AlbumMyDogFragment.newInstance(dogId);
                        mAlbumFragment.setPhotographer(mPhotographer);

                    }

                    return mAlbumFragment;

                default:
                    if (mHistoryFragment == null) {
                        mHistoryFragment = HistoryMyDogFragment.newInstance(dogId);
                    }

                    return mHistoryFragment;

            }
        }

        @Override
        public int getCount() {
            return ICONS.length;
        }

        @Override
        public int getPageIconResId(int position) {
            return ICONS[position];
        }
    }

}