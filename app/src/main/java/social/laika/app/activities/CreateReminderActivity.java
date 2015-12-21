package social.laika.app.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import social.laika.app.R;
import social.laika.app.fragments.CreateAlarmReminderFragment;
import social.laika.app.fragments.CreateCalendarReminderFragment;
import social.laika.app.models.AlarmReminder;
import social.laika.app.models.CalendarReminder;
import social.laika.app.models.Dog;
import social.laika.app.utils.Do;
import social.laika.app.utils.Tag;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class CreateReminderActivity extends ActionBarActivity {

    public static final String TAG = CreateReminderActivity.class.getSimpleName();
    public static final String KEY_DOG = "dog_id";
    public static final String KEY_ALARM_ID = "alarm_id";
    public static final String KEY_CALENDAR_ID = "calendar_id";

    private int mIdLayout = R.layout.lk_create_reminders_my_dog_fragment;
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

        long alarmReminderId = getIntent().getExtras().getLong(KEY_ALARM_ID, 0);
        long calendarReminderId = getIntent().getExtras().getLong(KEY_CALENDAR_ID, 0);

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

        foodLayout.setOnClickListener(new ReminderCategoryOnClickListener(Tag.CATEGORY_FOOD, Tag.TYPE_ALARM));

        pooLayout.setOnClickListener(new ReminderCategoryOnClickListener(Tag.CATEGORY_POO, Tag.TYPE_ALARM));

        walkLayout.setOnClickListener(new ReminderCategoryOnClickListener(Tag.CATEGORY_WALK, Tag.TYPE_ALARM));

        medicineLayout.setOnClickListener(new ReminderCategoryOnClickListener(Tag.CATEGORY_MEDICINE, Tag.TYPE_ALARM));

        hygieneLayout.setOnClickListener(new ReminderCategoryOnClickListener(Tag.CATEGORY_HYGIENE, Tag.TYPE_CALENDAR));

        vetLayout.setOnClickListener(new ReminderCategoryOnClickListener(Tag.CATEGORY_VET, Tag.TYPE_CALENDAR));

        vaccineLayout.setOnClickListener(new ReminderCategoryOnClickListener(Tag.CATEGORY_VACCINE, Tag.TYPE_CALENDAR));
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
        if (!this.getClass().equals(HomeActivity.class))
            getMenuInflater().inflate(R.menu.main_menu, menu);

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

        mFragment = new CreateAlarmReminderFragment(mDog, reminderCategory);
        getSupportFragmentManager().beginTransaction().add(R.id.container_reminder_my_dog_framelayout, mFragment).commit();

    }

    public void openAlarmReminder(AlarmReminder alarmReminder) {

        if (mFragment != null) {
            getSupportFragmentManager().beginTransaction().detach(mFragment).commit();
        }

        mFragment = new CreateAlarmReminderFragment(mDog, alarmReminder);
        getSupportFragmentManager().beginTransaction().add(R.id.container_reminder_my_dog_framelayout, mFragment).commit();

    }

    public void openCalendarReminder(int reminderCategory) {

        if (mFragment != null) {
            getSupportFragmentManager().beginTransaction().detach(mFragment).commit();
        }

        mFragment = new CreateCalendarReminderFragment(mDog, reminderCategory);
        getSupportFragmentManager().beginTransaction().add(R.id.container_reminder_my_dog_framelayout, mFragment).commit();

    }

    public void openCalendarReminder(CalendarReminder calendarReminder) {

        if (mFragment != null) {
            getSupportFragmentManager().beginTransaction().detach(mFragment).commit();
        }

        mFragment = new CreateCalendarReminderFragment(mDog, calendarReminder);
        getSupportFragmentManager().beginTransaction().add(R.id.container_reminder_my_dog_framelayout, mFragment).commit();

    }

    public void setCheckedImage(int category) {

        mFoodImageView.setImageDrawable(getResources().getDrawable(category == Tag.CATEGORY_FOOD ? R.drawable.laika_food_grey
                : R.drawable.laika_food_grey_light));
        mPooImageView.setImageDrawable(getResources().getDrawable(category == Tag.CATEGORY_POO ? R.drawable.laika_poop_grey
                : R.drawable.laika_poop_grey_light));
        mWalkImageView.setImageDrawable(getResources().getDrawable(category == Tag.CATEGORY_WALK ?
                R.drawable.laika_walk_grey : R.drawable.laika_walk_grey_light));
        mMedicineImageView.setImageDrawable(getResources().getDrawable(category == Tag.CATEGORY_MEDICINE ?
                R.drawable.laika_pill_grey : R.drawable.laika_pill_grey_light));
        mHygieneImageView.setImageDrawable(getResources().getDrawable(category == Tag.CATEGORY_HYGIENE ?
                R.drawable.laika_hygiene_grey : R.drawable.laika_hygiene_grey_light));
        mVetImageView.setImageDrawable(getResources().getDrawable(category == Tag.CATEGORY_VET ?
                R.drawable.laika_vetalarm_grey : R.drawable.laika_vetalarm_grey_light));
        mVaccineImageView.setImageDrawable(getResources().getDrawable(category == Tag.CATEGORY_VACCINE ?
                R.drawable.laika_vaccine_grey : R.drawable.laika_vaccine_grey_light));

    }

    public void getAlarmReminderFragment(AlarmReminder alarmReminder) {

        setCheckedImage(alarmReminder.mCategory);
        Do.hideView(mCalendarLayout);
        openAlarmReminder(alarmReminder);
    }

    public void getCalendarReminderFragment(CalendarReminder calendarReminder) {

        setCheckedImage(calendarReminder.mCategory);
        Do.hideView(mAlarmLayout);
        openCalendarReminder(calendarReminder);
    }

    private class ReminderCategoryOnClickListener implements View.OnClickListener {

        public int category;
        public int type;

        public ReminderCategoryOnClickListener(int category, int type) {
            this.category = category;
            this.type = type;
        }

        @Override
        public void onClick(View v) {

            if (mAlarmReminder != null) {

                mAlarmReminder.mCategory = category;
                getAlarmReminderFragment(mAlarmReminder);

            } else if (mCalendarReminder != null) {

                mCalendarReminder.mCategory = category;
                getCalendarReminderFragment(mCalendarReminder);

            } else {

                switch (type) {

                    case Tag.TYPE_ALARM:
                        openAlarmReminder(category);

                        break;

                    case Tag.TYPE_CALENDAR:
                        openCalendarReminder(category);

                        break;
                }

                setCheckedImage(category);
            }
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
