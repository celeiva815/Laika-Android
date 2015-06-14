package cl.laikachile.laika.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import cl.laikachile.laika.R;
import cl.laikachile.laika.models.AlarmReminder;
import cl.laikachile.laika.models.CalendarReminder;
import cl.laikachile.laika.models.Dog;
import cl.laikachile.laika.utils.Do;
import cl.laikachile.laika.utils.Tag;

/**
 * Created by Tito_Leiva on 09-03-15.
 */
public class RemindersMyDogFragment extends Fragment {

    public static final String KEY_DOG = "dog";
    public static final String KEY_ALARM = "alarm";
    public static final String KEY_CALENDAR = "calendar";

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

    public RemindersMyDogFragment() {
    }

    public RemindersMyDogFragment(Dog mDog) {
        this.mDog = mDog;
    }

    public RemindersMyDogFragment(Dog mDog, AlarmReminder alarmReminder) {

        this.mDog = mDog;
        this.mAlarmReminder = alarmReminder;

    }

    public RemindersMyDogFragment(Dog mDog, CalendarReminder calendarReminder) {

        this.mDog = mDog;
        this.mCalendarReminder = calendarReminder;
    }

    public static final RemindersMyDogFragment newInstance(int dogId)
    {
        RemindersMyDogFragment f = new RemindersMyDogFragment();
        Bundle bdl = new Bundle(1);
        bdl.putInt(KEY_DOG, dogId);
        f.setArguments(bdl);
        return f;
    }

    public static final RemindersMyDogFragment newInstance(int dogId, int reminderId, String key)
    {
        RemindersMyDogFragment f = new RemindersMyDogFragment();
        Bundle bundle = new Bundle(1);
        bundle.putInt(KEY_DOG, dogId);
        bundle.putInt(key, reminderId);

        f.setArguments(bundle);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        int dogId = getArguments().getInt(KEY_DOG);
        mDog = Dog.getSingleDog(dogId);

        int alarmReminderId = getArguments().getInt(KEY_ALARM, 0);
        int calendarReminderId = getArguments().getInt(KEY_CALENDAR, 0);

        if (alarmReminderId > 0) {

            mAlarmReminder = AlarmReminder.getSingleReminder(alarmReminderId);

        } else if (calendarReminderId > 0) {

            mCalendarReminder = CalendarReminder.getSingleReminder(calendarReminderId);
        }

        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(mIdLayout, container, false);

        RelativeLayout foodLayout = (RelativeLayout) view.findViewById(R.id.food_reminders_my_dog_layout);
        RelativeLayout pooLayout = (RelativeLayout) view.findViewById(R.id.poo_reminders_my_dog_layout);
        RelativeLayout walkLayout = (RelativeLayout) view.findViewById(R.id.walk_reminders_my_dog_layout);
        RelativeLayout medicineLayout = (RelativeLayout) view.findViewById(R.id.medicine_reminders_my_dog_layout);
        RelativeLayout hygieneLayout = (RelativeLayout) view.findViewById(R.id.hygiene_reminders_my_dog_layout);
        RelativeLayout vetLayout = (RelativeLayout) view.findViewById(R.id.vet_reminders_my_dog_layout);
        RelativeLayout vaccineLayout = (RelativeLayout) view.findViewById(R.id.vaccine_reminders_my_dog_layout);

        mAlarmLayout = (LinearLayout) view.findViewById(R.id.alarms_reminders_layout);
        mCalendarLayout = (LinearLayout) view.findViewById(R.id.calendar_reminders_layout);
        mFoodImageView = (ImageView) view.findViewById(R.id.food_reminders_my_dog_imageview);
        mPooImageView = (ImageView) view.findViewById(R.id.poo_reminders_my_dog_imageview);
        mWalkImageView = (ImageView) view.findViewById(R.id.walk_reminders_my_dog_imageview);
        mMedicineImageView = (ImageView) view.findViewById(R.id.medicine_reminders_my_dog_imageview);
        mHygieneImageView = (ImageView) view.findViewById(R.id.hygiene_reminders_my_dog_imageview);
        mVetImageView = (ImageView) view.findViewById(R.id.vet_reminders_my_dog_imageview);
        mVaccineImageView = (ImageView) view.findViewById(R.id.vaccine_reminders_my_dog_imageview);

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

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

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

    public void openAlarmReminder(int reminderCategory) {

        if (mFragment != null) {
            getActivity().getSupportFragmentManager().beginTransaction().detach(mFragment).commit();
        }

        mFragment = new AlarmReminderMyDogFragment(mDog, reminderCategory);
        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.container_reminder_my_dog_framelayout, mFragment).commit();

    }

    public void openAlarmReminder(AlarmReminder alarmReminder) {

        if (mFragment != null) {
            getActivity().getSupportFragmentManager().beginTransaction().detach(mFragment).commit();
        }

        mFragment = new AlarmReminderMyDogFragment(mDog, alarmReminder);
        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.container_reminder_my_dog_framelayout, mFragment).commit();

    }

    public void openCalendarReminder(int reminderCategory) {

        if (mFragment != null) {
            getActivity().getSupportFragmentManager().beginTransaction().detach(mFragment).commit();
        }

        mFragment = new CalendarReminderMyDogFragment(mDog, reminderCategory);
        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.container_reminder_my_dog_framelayout, mFragment).commit();

    }

    public void openCalendarReminder(CalendarReminder calendarReminder) {

        if (mFragment != null) {
            getActivity().getSupportFragmentManager().beginTransaction().detach(mFragment).commit();
        }

        mFragment = new CalendarReminderMyDogFragment(mDog, calendarReminder);
        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.container_reminder_my_dog_framelayout, mFragment).commit();

    }

    public void setCheckedImage(boolean food, boolean poo, boolean walk, boolean medicine,
                                boolean hygiene, boolean vet, boolean vaccine) {

        mFoodImageView.setImageDrawable(getResources().getDrawable(food? R.drawable.laika_food_grey
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
