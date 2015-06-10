package cl.laikachile.laika.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
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
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import cl.laikachile.laika.R;
import cl.laikachile.laika.fragments.AlbumMyDogFragment;
import cl.laikachile.laika.fragments.HistoryMyDogFragment;
import cl.laikachile.laika.fragments.OwnersFragment;
import cl.laikachile.laika.fragments.RemindersMyDogFragment;
import cl.laikachile.laika.models.AlarmReminder;
import cl.laikachile.laika.models.CalendarReminder;
import cl.laikachile.laika.models.Dog;
import cl.laikachile.laika.models.Photo;
import cl.laikachile.laika.utils.Do;
import cl.laikachile.laika.utils.PrefsManager;
import cl.laikachile.laika.utils.views.CustomPagerSlidingTabStrip;

public class MyDogsActivity extends ActionBarActivity {

    public static final String DOG_ID = "dog_id";
    public static final String HISTORY = "Historial";
    public static final String REMINDERS = "Recordatorios";
    public static final String OWNERS = "Dueños";
    public static final String ALBUM = "Album";
    public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1;
    public static final String[] CONTENT = new String[] {HISTORY, REMINDERS, OWNERS, ALBUM};
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(mIdLayout);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.color.laika_red));
        actionBar.setDisplayHomeAsUpEnabled(true);

        int dogId = getIntent().getIntExtra(DOG_ID, 0);
        mDog = Dog.getSingleDog(dogId);

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
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // Ensure that there's a camera activity to handle the intent
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    // Create the File where the mPhoto should go
                    File photoFile = null;
                    try {

                        photoFile = createImageFile();

                    } catch (IOException ex) {
                        // Error occurred while creating the File
                        Do.showShortToast("Problem creating the picture", getApplicationContext());
                    }
                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                                Uri.fromFile(photoFile));
                        startActivityForResult(takePictureIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
                    }
                }

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {

            if (resultCode == RESULT_OK) {

                //Bitmap imageBitmap = setPicture();
                //String imageName = getImageName(mDog.mDogId);
                //FIXME agregar el nombre correcto del usuario
                String ownerName = PrefsManager.getUserName(getApplicationContext());
                int ownerId = PrefsManager.getUserId(getApplicationContext());
                Photo photo = new Photo(Photo.ID++, ownerId, ownerName,
                        mDog.mDogId, mCurrentPhotoPath, Do.now(), "foto de prueba",
                        R.drawable.filipo1);

                photo.save();

                mPagerAdapter.notifyDataSetChanged();


            } else if (resultCode == RESULT_CANCELED) {

                Toast.makeText(this, "La fotografía fue cancelada", Toast.LENGTH_SHORT).show();
            } else {

                Toast.makeText(this, "No se pudo tomar la fotografía", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void setHistoryFragment(Dog mDog) {

        if (mHistoryFragment != null) {
            mHistoryFragment.refreshList(getApplicationContext());

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


    public File createImageFile() throws IOException {
        // Create an image file name

        mCurrentPhotoPath = "";

        String imageFileName = getImageName(mDog.mDogId) + ".jpg";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = new File(storageDir, imageFileName);

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();

        return image;
    }

    private Bitmap setPicture() {

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW / 640, photoH / 480);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;
        bmOptions.inSampleSize = scaleFactor;
        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);

        return bitmap;
    }

    public String getImageName(int dogId) {

        int[] dateArray = Do.nowDateInArray();
        int[] timeArray = Do.timeInArray();

        String date = "";

        for (int i : dateArray) {

            date += Integer.toString(i);
        }

        for (int i : timeArray) {

            date += Integer.toString(i);
        }

        date += Integer.toString(dogId);

        return date;

    }

    public int getPagerPosition(String tab) {

        for (int i = 0 ; i < CONTENT.length ; i++) {

            if (CONTENT[i].equals(tab)) {
                return i;
            }
        }

        return 0;
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