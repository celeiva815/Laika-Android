package cl.laikachile.laika.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

public class MyDogsActivity extends ActionBarActivity {

    public static final String DOG_ID = "dog_id";
    public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1;

    private int mIdLayout = R.layout.lk_my_dogs_fragment;
    public Dog mDog;
    public LinearLayout mHistoryLinearLayout;
    public LinearLayout mRemindersLinearLayout;
    public LinearLayout mHealthLinearLayout;
    public LinearLayout mOwnerLinearLayout;
    public LinearLayout mAlbumLinearLayout;

    public HistoryMyDogFragment mHistoryFragment;
    public RemindersMyDogFragment mRemindersFragment;
    public OwnersFragment mOwnerFragment;
    public AlbumMyDogFragment mAlbumFragment;
    public Fragment mCurrentFragment;

    public String mCurrentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(mIdLayout);
        setActivityView();

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.color.laika_red));
        actionBar.setDisplayHomeAsUpEnabled(true);

        int dogId = getIntent().getIntExtra(DOG_ID, 0);
        mDog = Dog.getSingleDog(dogId);
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

        if (mCurrentFragment == null) {
            setHistoryFragment(mDog);
            setTitle(mDog.mName);
        }
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

    public void setActivityView() {

        mHistoryLinearLayout = (LinearLayout) findViewById(R.id.history_my_dog_linearlayout);
        mRemindersLinearLayout = (LinearLayout) findViewById(R.id.reminders_my_dog_linearlayout);
        mHealthLinearLayout = (LinearLayout) findViewById(R.id.health_my_dog_linearlayout);
        mOwnerLinearLayout = (LinearLayout) findViewById(R.id.owner_my_dog_linearlayout);
        mAlbumLinearLayout = (LinearLayout) findViewById(R.id.album_my_dog_linearlayout);

        mHistoryLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setHistoryFragment(mDog);
            }
        });

        mOwnerLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOwnerFragment(mDog);
            }
        });

        mRemindersLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setReminderFragment(mDog);
            }
        });

        mAlbumLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAlbumFragment(mDog);
            }
        });
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

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                if (mCurrentFragment != null) {
                    transaction.detach(mCurrentFragment);
                }

                mCurrentFragment = new AlbumMyDogFragment(mDog);
                transaction.add(R.id.container_my_dog_framelayout, mCurrentFragment);
                transaction.commitAllowingStateLoss();

                int color = getResources().getColor(R.color.semi_trans_black_background);
                setBackgrounds(Color.TRANSPARENT, Color.TRANSPARENT, Color.TRANSPARENT, Color.TRANSPARENT, color);

            } else if (resultCode == RESULT_CANCELED) {

                Toast.makeText(this, "La fotografía fue cancelada", Toast.LENGTH_SHORT).show();
            } else {

                Toast.makeText(this, "No se pudo tomar la fotografía", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void setHistoryFragment(Dog mDog) {

        if (mHistoryFragment == null) {

            mHistoryFragment = new HistoryMyDogFragment(mDog);

            if (mCurrentFragment != null) {
                getSupportFragmentManager().beginTransaction().detach(mCurrentFragment).commit();
            }
            mCurrentFragment = mHistoryFragment;

            getSupportFragmentManager().beginTransaction().add(R.id.container_my_dog_framelayout,
                    mCurrentFragment).commit();

            mCurrentFragment = mHistoryFragment;

        } else {

            if (mHistoryFragment.isDetached()) {

                getSupportFragmentManager().beginTransaction().detach(mCurrentFragment).commit();
                mCurrentFragment = mHistoryFragment;
                mHistoryFragment.mHistoryAdapter.notifyDataSetChanged();
                getSupportFragmentManager().beginTransaction().attach(mCurrentFragment).commit();
            }
        }

        int color = getResources().getColor(R.color.semi_trans_black_background);
        setBackgrounds(color, Color.TRANSPARENT, Color.TRANSPARENT, Color.TRANSPARENT, Color.TRANSPARENT);

    }

    public void setReminderFragment(Dog mDog) {

        if (mRemindersFragment == null) {

            mRemindersFragment = new RemindersMyDogFragment(mDog);

            if (mCurrentFragment != null) {
                getSupportFragmentManager().beginTransaction().detach(mCurrentFragment).commit();
            }
            mCurrentFragment = mRemindersFragment;

            getSupportFragmentManager().beginTransaction().add(R.id.container_my_dog_framelayout,
                    mCurrentFragment).commit();

            mCurrentFragment = mRemindersFragment;

        } else {

            if (mRemindersFragment.isDetached()) {

                getSupportFragmentManager().beginTransaction().detach(mCurrentFragment).commit();
                mCurrentFragment = mRemindersFragment;
                getSupportFragmentManager().beginTransaction().attach(mCurrentFragment).commit();
            }
        }

        int color = getResources().getColor(R.color.semi_trans_black_background);
        setBackgrounds(Color.TRANSPARENT, color, Color.TRANSPARENT, Color.TRANSPARENT, Color.TRANSPARENT);

    }

    public void setReminderFragment(AlarmReminder alarmReminder) {

        mRemindersFragment = new RemindersMyDogFragment(mDog, alarmReminder);

        if (mCurrentFragment != null) {
            getSupportFragmentManager().beginTransaction().detach(mCurrentFragment).commit();
        }

        mCurrentFragment = mRemindersFragment;

        getSupportFragmentManager().beginTransaction().add(R.id.container_my_dog_framelayout,
                mCurrentFragment).commit();

        int color = getResources().getColor(R.color.semi_trans_black_background);
        setBackgrounds(Color.TRANSPARENT, color, Color.TRANSPARENT, Color.TRANSPARENT, Color.TRANSPARENT);

    }

    public void setReminderFragment(CalendarReminder calendarReminder) {

        mRemindersFragment = new RemindersMyDogFragment(mDog, calendarReminder);

        if (mCurrentFragment != null) {
            getSupportFragmentManager().beginTransaction().detach(mCurrentFragment).commit();
        }

        mCurrentFragment = mRemindersFragment;

        getSupportFragmentManager().beginTransaction().add(R.id.container_my_dog_framelayout,
                mCurrentFragment).commit();

        mCurrentFragment = mRemindersFragment;


        int color = getResources().getColor(R.color.semi_trans_black_background);
        setBackgrounds(Color.TRANSPARENT, color, Color.TRANSPARENT, Color.TRANSPARENT, Color.TRANSPARENT);

    }

    public void setHealthFragment() {


    }

    public void setOwnerFragment(Dog mDog) {

        if (mOwnerFragment == null) {

            mOwnerFragment = new OwnersFragment(mDog);

            if (mCurrentFragment != null) {
                getSupportFragmentManager().beginTransaction().detach(mCurrentFragment).commit();
            }

            mCurrentFragment = mOwnerFragment;

            getSupportFragmentManager().beginTransaction().add(R.id.container_my_dog_framelayout,
                    mCurrentFragment).commit();

            mCurrentFragment = mOwnerFragment;

        } else {

            if (mOwnerFragment.isDetached()) {

                getSupportFragmentManager().beginTransaction().detach(mCurrentFragment).commit();
                mCurrentFragment = mOwnerFragment;
                mOwnerFragment.mOwnerAdapter.notifyDataSetChanged();
                getSupportFragmentManager().beginTransaction().attach(mCurrentFragment).commit();
            }
        }

        int color = getResources().getColor(R.color.semi_trans_black_background);
        setBackgrounds(Color.TRANSPARENT, Color.TRANSPARENT, Color.TRANSPARENT, color, Color.TRANSPARENT);

    }

    public void setAlbumFragment(Dog mDog) {

        if (mAlbumFragment == null) {

            mAlbumFragment = new AlbumMyDogFragment(mDog);

            if (mCurrentFragment != null) {
                getSupportFragmentManager().beginTransaction().detach(mCurrentFragment).commit();
            }

            mCurrentFragment = mAlbumFragment;

            getSupportFragmentManager().beginTransaction().add(R.id.container_my_dog_framelayout,
                    mCurrentFragment).commit();

            mCurrentFragment = mAlbumFragment;

        } else {

            if (mAlbumFragment.isDetached()) {

                getSupportFragmentManager().beginTransaction().detach(mCurrentFragment).commit();
                mCurrentFragment = mAlbumFragment;
                mAlbumFragment.mAlbumAdapter.notifyDataSetChanged();
                getSupportFragmentManager().beginTransaction().attach(mCurrentFragment).commit();
            }
        }

        int color = getResources().getColor(R.color.semi_trans_black_background);
        setBackgrounds(Color.TRANSPARENT, Color.TRANSPARENT, Color.TRANSPARENT, Color.TRANSPARENT, color);
    }


    public void setBackgrounds(int history, int reminders, int health, int owner, int album) {

        mHistoryLinearLayout.setBackgroundColor(history);
        mRemindersLinearLayout.setBackgroundColor(reminders);
        mHealthLinearLayout.setBackgroundColor(health);
        mOwnerLinearLayout.setBackgroundColor(owner);
        mAlbumLinearLayout.setBackgroundColor(album);
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

}
