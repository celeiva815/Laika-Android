package social.laika.app.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.soundcloud.android.crop.Crop;

import org.json.JSONException;
import org.json.JSONObject;

import social.laika.app.R;
import social.laika.app.fragments.AlarmReminderMyDogFragment;
import social.laika.app.fragments.CalendarReminderMyDogFragment;
import social.laika.app.interfaces.Photographable;
import social.laika.app.interfaces.Requestable;
import social.laika.app.listeners.CreateStoryOnClickListener;
import social.laika.app.listeners.PhotographerListener;
import social.laika.app.models.AlarmReminder;
import social.laika.app.models.CalendarReminder;
import social.laika.app.models.Dog;
import social.laika.app.models.Photo;
import social.laika.app.models.Story;
import social.laika.app.network.RequestManager;
import social.laika.app.network.VolleyManager;
import social.laika.app.responses.CreateStoryResponse;
import social.laika.app.utils.Do;
import social.laika.app.utils.Photographer;
import social.laika.app.utils.PrefsManager;
import social.laika.app.utils.Tag;

public class CreateReminderActivity extends ActionBarActivity {

    public static final String TAG = CreateReminderActivity.class.getSimpleName();
    public static final String KEY_DOG = "dog_id";
    public static final String KEY_ALARM_ID = "alarm_id";
    public static final String KEY_CALENDAR_ID = "calendar_id";

    private int mIdLayout = R.layout.lk_reminders_my_dog_fragment;
    public Dog mDog;
    public Fragment mFragment;
    public AlarmReminder mAlarmReminder;
    public CalendarReminder mCalendarReminder;
    public ImageView mFoodImageView;
    public ImageView mPooImageView;
    public ImageView mWalkImageView;
    public ImageView mMedicineImageView;
    public ImageView mHygieneImageView;
    public ImageView mVetImageView;
    public ImageView mVaccineImageView;
    public LinearLayout mAlarmLayout;
    public LinearLayout mCalendarLayout;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mIdLayout);
        setActivityView();

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.color.laika_red));
        actionBar.setDisplayHomeAsUpEnabled(true);

        int dogId = getIntent().getExtras().getInt(KEY_DOG);
        mDog = Dog.getSingleDog(dogId);

        int alarmReminderId = getIntent().getExtras().getInt(KEY_ALARM_ID, 0);
        int calendarReminderId = getIntent().getExtras().getInt(KEY_CALENDAR_ID, 0);

        if (alarmReminderId > 0) {

            mAlarmReminder = AlarmReminder.getSingleReminder(alarmReminderId);

        } else if (calendarReminderId > 0) {

            mCalendarReminder = CalendarReminder.getSingleReminder(calendarReminderId);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();

        Do.hideKeyboard(this);
    }

    public void setActivityView() {

        RelativeLayout foodLayout = (RelativeLayout) findViewById(R.id.food_reminders_my_dog_layout);
        RelativeLayout pooLayout = (RelativeLayout) findViewById(R.id.poo_reminders_my_dog_layout);
        RelativeLayout walkLayout = (RelativeLayout) findViewById(R.id.walk_reminders_my_dog_layout);
        RelativeLayout medicineLayout = (RelativeLayout) findViewById(R.id.medicine_reminders_my_dog_layout);
        RelativeLayout hygieneLayout = (RelativeLayout) findViewById(R.id.hygiene_reminders_my_dog_layout);
        RelativeLayout vetLayout = (RelativeLayout) findViewById(R.id.vet_reminders_my_dog_layout);
        RelativeLayout vaccineLayout = (RelativeLayout) findViewById(R.id.vaccine_reminders_my_dog_layout);

        mAlarmLayout = (LinearLayout) findViewById(R.id.alarms_reminders_layout);
        mCalendarLayout = (LinearLayout) findViewById(R.id.calendar_reminders_layout);
        mFoodImageView = (ImageView) findViewById(R.id.food_reminders_my_dog_imageview);
        mPooImageView = (ImageView) findViewById(R.id.poo_reminders_my_dog_imageview);
        mWalkImageView = (ImageView) findViewById(R.id.walk_reminders_my_dog_imageview);
        mMedicineImageView = (ImageView) findViewById(R.id.medicine_reminders_my_dog_imageview);
        mHygieneImageView = (ImageView) findViewById(R.id.hygiene_reminders_my_dog_imageview);
        mVetImageView = (ImageView) findViewById(R.id.vet_reminders_my_dog_imageview);
        mVaccineImageView = (ImageView) findViewById(R.id.vaccine_reminders_my_dog_imageview);

        foodLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAlarmReminder(Tag.CATEGORY_FOOD);
                setCheckedImage(true, false, false, false, false, false, false);
            }
        });

        pooLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAlarmReminder(Tag.CATEGORY_POO);
                setCheckedImage(false, true, false, false, false, false, false);
            }
        });

        walkLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAlarmReminder(Tag.CATEGORY_WALK);
                setCheckedImage(false, false, true, false, false, false, false);
            }
        });

        medicineLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAlarmReminder(Tag.CATEGORY_MEDICINE);
                setCheckedImage(false, false, false, true, false, false, false);
            }
        });

        hygieneLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCalendarReminder(Tag.CATEGORY_HYGIENE);
                setCheckedImage(false, false, false, false, true, false, false);
            }
        });

        vetLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCalendarReminder(Tag.CATEGORY_VET);
                setCheckedImage(false, false, false, false, false, true, false);
            }
        });

        vaccineLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCalendarReminder(Tag.CATEGORY_VACCINE);
                setCheckedImage(false, false, false, false, false, false, true);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();

        if (mAlarmReminder != null) {

            getAlarmReminderFragment(mAlarmReminder);
        }

        if (mCalendarReminder != null) {

            getCalendarReminderFragment(mCalendarReminder);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        if (!this.getClass().equals(MainActivity.class))
            getMenuInflater().inflate(R.menu.create_story_menu, menu);

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

        }

        return super.onOptionsItemSelected(item);
    }

    public void openAlarmReminder(int reminderCategory) {

        if (mFragment != null) {
            getSupportFragmentManager().beginTransaction().detach(mFragment).commit();
        }

        mFragment = new AlarmReminderMyDogFragment(mDog, reminderCategory);
        getSupportFragmentManager().beginTransaction().add(R.id.container_reminder_my_dog_framelayout, mFragment).commit();

    }

    public void openAlarmReminder(AlarmReminder alarmReminder) {

        if (mFragment != null) {
            getSupportFragmentManager().beginTransaction().detach(mFragment).commit();
        }

        mFragment = new AlarmReminderMyDogFragment(mDog, alarmReminder);
        getSupportFragmentManager().beginTransaction().add(R.id.container_reminder_my_dog_framelayout, mFragment).commit();

    }

    public void openCalendarReminder(int reminderCategory) {

        if (mFragment != null) {
            getSupportFragmentManager().beginTransaction().detach(mFragment).commit();
        }

        mFragment = new CalendarReminderMyDogFragment(mDog, reminderCategory);
        getSupportFragmentManager().beginTransaction().add(R.id.container_reminder_my_dog_framelayout, mFragment).commit();

    }

    public void openCalendarReminder(CalendarReminder calendarReminder) {

        if (mFragment != null) {
            getSupportFragmentManager().beginTransaction().detach(mFragment).commit();
        }

        mFragment = new CalendarReminderMyDogFragment(mDog, calendarReminder);
        getSupportFragmentManager().beginTransaction().add(R.id.container_reminder_my_dog_framelayout, mFragment).commit();

    }

    public void setCheckedImage(boolean food, boolean poo, boolean walk, boolean medicine,
                                boolean hygiene, boolean vet, boolean vaccine) {

        mFoodImageView.setImageDrawable(getResources().getDrawable(food ? R.drawable.laika_food_grey
                : R.drawable.laika_food_grey_light));
        mPooImageView.setImageDrawable(getResources().getDrawable(poo? R.drawable.laika_poop_grey
                : R.drawable.laika_poop_grey_light));
        mWalkImageView.setImageDrawable(getResources().getDrawable(walk?
                R.drawable.laika_walk_grey : R.drawable.laika_walk_grey_light));
        mMedicineImageView.setImageDrawable(getResources().getDrawable(medicine?
                R.drawable.laika_pill_grey : R.drawable.laika_pill_grey_light ));
        mHygieneImageView.setImageDrawable(getResources().getDrawable(hygiene?
                R.drawable.laika_hygiene_grey : R.drawable.laika_hygiene_grey_light ));
        mVetImageView.setImageDrawable(getResources().getDrawable(vet?
                R.drawable.laika_vetalarm_grey : R.drawable.laika_vetalarm_grey_light));
        mVaccineImageView.setImageDrawable(getResources().getDrawable(vaccine?
                R.drawable.laika_vaccine_grey : R.drawable.laika_vaccine_grey_light));

    }

    public void getAlarmReminderFragment(AlarmReminder alarmReminder) {

        switch (alarmReminder.mCategory) {

            case Tag.CATEGORY_FOOD:
                setCheckedImage(true, false, false, false, false, false, false);
                break;

            case Tag.CATEGORY_POO:
                setCheckedImage(false, true, false, false, false, false, false);
                break;

            case Tag.CATEGORY_WALK:
                setCheckedImage(false, false, true, false, false, false, false);
                break;

            case Tag.CATEGORY_MEDICINE:
                setCheckedImage(false, false, false, true, false, false, false);
                break;
        }

        Do.hideView(mCalendarLayout);
        openAlarmReminder(alarmReminder);
    }

    public void getCalendarReminderFragment(CalendarReminder calendarReminder) {

        switch (calendarReminder.mCategory) {

            case Tag.CATEGORY_VET:
                setCheckedImage(false, false, false, false, false, true, false);

            case Tag.CATEGORY_HYGIENE:
                setCheckedImage(false, false, false, false, true, false, false);

            case Tag.CATEGORY_VACCINE:
                setCheckedImage(false, false, false, false, false, false, true);
        }

        Do.hideView(mAlarmLayout);
        openCalendarReminder(calendarReminder);
    }
}
