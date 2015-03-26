package cl.laikachile.laika.activities;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import cl.laikachile.laika.R;
import cl.laikachile.laika.fragments.AlbumMyDogFragment;
import cl.laikachile.laika.fragments.HistoryMyDogFragment;
import cl.laikachile.laika.fragments.OwnerMyDogFragment;
import cl.laikachile.laika.fragments.RemindersMyDogFragment;
import cl.laikachile.laika.models.AlarmReminder;
import cl.laikachile.laika.models.CalendarReminder;
import cl.laikachile.laika.models.Dog;

public class MyDogsActivity extends ActionBarActivity {

    public static final String DOG_ID = "dog_id";

    private int mIdLayout = R.layout.lk_my_dogs_fragment;
    public Dog mDog;
    public LinearLayout mHistoryLinearLayout;
    public LinearLayout mRemindersLinearLayout;
    public LinearLayout mHealthLinearLayout;
    public LinearLayout mOwnerLinearLayout;
    public LinearLayout mAlbumLinearLayout;

    public Fragment mChildFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(mIdLayout);
        setActivityView();

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.color.laika_red));

        int dogId = getIntent().getIntExtra(DOG_ID, 0);
        mDog = Dog.getSingleDog(dogId);

        setTitle(mDog.mName);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        if (!this.getClass().equals(MainActivity.class))
            getMenuInflater().inflate(R.menu.activity_main, menu);

        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        setHistoryFragment(mDog);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.home_menu_button) {

            Intent homeIntent = new Intent(this, MainActivity.class);
            homeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(homeIntent);

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

    public void setHistoryFragment(Dog mDog) {

        if (mChildFragment != null) {
            getSupportFragmentManager().beginTransaction().detach(mChildFragment).commit();
        }

        mChildFragment = new HistoryMyDogFragment(mDog);
        getSupportFragmentManager().beginTransaction().add(R.id.container_my_dog_framelayout, mChildFragment).commit();
        int color = getResources().getColor(R.color.semi_trans_black_background);
        setBackgrounds(color, Color.TRANSPARENT, Color.TRANSPARENT, Color.TRANSPARENT ,Color.TRANSPARENT);

    }

    public void setReminderFragment(Dog mDog) {

        if (mChildFragment != null) {
            getSupportFragmentManager().beginTransaction().detach(mChildFragment).commit();
        }

        mChildFragment = new RemindersMyDogFragment(mDog);
        getSupportFragmentManager().beginTransaction().add(R.id.container_my_dog_framelayout, mChildFragment).commit();
        int color = getResources().getColor(R.color.semi_trans_black_background);
        setBackgrounds(Color.TRANSPARENT, color, Color.TRANSPARENT, Color.TRANSPARENT ,Color.TRANSPARENT);

    }

    public void setReminderFragment(AlarmReminder alarmReminder) {

        if (mChildFragment != null) {
            getSupportFragmentManager().beginTransaction().detach(mChildFragment).commit();
        }

        mChildFragment = new RemindersMyDogFragment(mDog, alarmReminder);
        getSupportFragmentManager().beginTransaction().add(R.id.container_my_dog_framelayout, mChildFragment).commit();
        int color = getResources().getColor(R.color.semi_trans_black_background);
        setBackgrounds(Color.TRANSPARENT, color, Color.TRANSPARENT, Color.TRANSPARENT ,Color.TRANSPARENT);

    }

    public void setReminderFragment(CalendarReminder calendarReminder) {

        if (mChildFragment != null) {
            getSupportFragmentManager().beginTransaction().detach(mChildFragment).commit();
        }

        mChildFragment = new RemindersMyDogFragment(mDog, calendarReminder);
        getSupportFragmentManager().beginTransaction().add(R.id.container_my_dog_framelayout, mChildFragment).commit();
        int color = getResources().getColor(R.color.semi_trans_black_background);
        setBackgrounds(Color.TRANSPARENT, color, Color.TRANSPARENT, Color.TRANSPARENT ,Color.TRANSPARENT);

    }

    public void setHealthFragment() {


    }

    public void setOwnerFragment(Dog mDog) {

        if (mChildFragment != null) {
            getSupportFragmentManager().beginTransaction().detach(mChildFragment).commit();
        }

        mChildFragment = new OwnerMyDogFragment(mDog);
        getSupportFragmentManager().beginTransaction().add(R.id.container_my_dog_framelayout, mChildFragment).commit();
        int color = getResources().getColor(R.color.semi_trans_black_background);
        setBackgrounds(Color.TRANSPARENT, Color.TRANSPARENT, Color.TRANSPARENT, color ,Color.TRANSPARENT);

    }

    public void setAlbumFragment(Dog mDog) {

        if (mChildFragment != null) {
            getSupportFragmentManager().beginTransaction().detach(mChildFragment).commit();
        }

        mChildFragment = new AlbumMyDogFragment(mDog);
        getSupportFragmentManager().beginTransaction().add(R.id.container_my_dog_framelayout, mChildFragment).commit();
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


}
