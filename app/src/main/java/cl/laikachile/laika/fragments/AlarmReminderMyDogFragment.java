package cl.laikachile.laika.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sleepbot.datetimepicker.time.RadialPickerLayout;
import com.sleepbot.datetimepicker.time.TimePickerDialog;

import java.util.Calendar;

import cl.laikachile.laika.R;
import cl.laikachile.laika.models.AlarmReminder;
import cl.laikachile.laika.models.Dog;
import cl.laikachile.laika.utils.Do;
import cl.laikachile.laika.utils.Tag;

/**
 * Created by Tito_Leiva on 09-03-15.
 */
public class AlarmReminderMyDogFragment extends Fragment implements TimePickerDialog.OnTimeSetListener{

    private int mIdLayout = R.layout.lk_alarm_reminder_my_dog_fragment;
    public Dog mDog;
    public int mReminderCategory;
    public Fragment mFragment;

    public EditText mTitleEditText;
    public EditText mDetailEditText;
    public Button mTimeButton;
    public Button mSaveButton;
    public Button mMondayButton;
    public Button mTuesdayButton;
    public Button mWednesdayButton;
    public Button mThursdayButton;
    public Button mFridayButton;
    public Button mSaturdayButton;
    public Button mSundayButton;

    public boolean mMonday = false;
    public boolean mTuesday = false;
    public boolean mWednesday = false;
    public boolean mThursday = false;
    public boolean mFriday = false;
    public boolean mSaturday = false;
    public boolean mSunday = false;
    public String mTime;

    public static final String TIMEPICKER_TAG = "timepicker";

    public AlarmReminderMyDogFragment(Dog mDog, int mReminderCategory) {

        this.mDog = mDog;
        this.mReminderCategory = mReminderCategory;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(mIdLayout, container, false);

        mMondayButton = (Button) view.findViewById(R.id.monday_alarm_reminder_my_dog_button);
        mTuesdayButton = (Button) view.findViewById(R.id.tuesday_alarm_reminder_my_dog_button);
        mWednesdayButton = (Button) view.findViewById(R.id.wednesday_alarm_reminder_my_dog_button);
        mThursdayButton = (Button) view.findViewById(R.id.thursday_alarm_reminder_my_dog_button);
        mFridayButton = (Button) view.findViewById(R.id.friday_alarm_reminder_my_dog_button);
        mSaturdayButton = (Button) view.findViewById(R.id.saturday_alarm_reminder_my_dog_button);
        mSundayButton = (Button) view.findViewById(R.id.sunday_alarm_reminder_my_dog_button);

        mTitleEditText = (EditText) view.findViewById(R.id.title_alarm_reminder_my_dog_edittext);
        mDetailEditText = (EditText) view.findViewById(R.id.detail_alarm_reminder_my_dog_edittext);
        mTimeButton = (Button) view.findViewById(R.id.time_alarm_remind_my_dog_button);
        mSaveButton = (Button) view.findViewById(R.id.save_alarm_remind_my_dog_button);


        final Calendar calendar = Calendar.getInstance();
        final TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(this, calendar.get(Calendar.HOUR_OF_DAY) ,calendar.get(Calendar.MINUTE), false, false);

        mTime = getTime(calendar.get(Calendar.HOUR_OF_DAY) ,calendar.get(Calendar.MINUTE));
        mTimeButton.setText(mTime);

        mTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                timePickerDialog.setCloseOnSingleTapMinute(false);
                timePickerDialog.show(getActivity().getSupportFragmentManager(), TIMEPICKER_TAG);
            }
        });

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = mTitleEditText.getText().toString();
                String detail = mDetailEditText.getText().toString();

                //FIXME agregar el owner otras variables y verificar que no sean nullOrEmpty.
                AlarmReminder reminder = new AlarmReminder(AlarmReminder.ID++, Tag.TYPE_ALARM,
                        mReminderCategory,title,detail,Tag.STATUS_IN_PROGRESS,mMonday,mTuesday,mWednesday,
                        mThursday,mFriday,mSaturday,mSunday,mTime,1,mDog.mDogId);

                reminder.save();

                String message = Do.getRString(v.getContext(), R.string.new_reminder_added);
                Do.showToast(message,v.getContext(), Toast.LENGTH_LONG);

            }
        });

        mMondayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!mMonday) {
                    mMonday = true;
                    mMondayButton.setBackgroundColor(getDarkWhiteColor());

                } else {
                    mMonday = false;
                    mMondayButton.setBackgroundColor(getSemiTransparentColor());
                }
            }
        });

        mTuesdayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!mTuesday) {
                    mTuesday = true;
                    mTuesdayButton.setBackgroundColor(getDarkWhiteColor());

                } else {
                    mTuesday = false;
                    mTuesdayButton.setBackgroundColor(getSemiTransparentColor());
                }
            }
        });

        mWednesdayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!mWednesday) {
                    mWednesday = true;
                    mWednesdayButton.setBackgroundColor(getDarkWhiteColor());

                } else {
                    mWednesday = false;
                    mWednesdayButton.setBackgroundColor(getSemiTransparentColor());
                }
            }
        });

        mThursdayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!mThursday) {
                    mThursday = true;
                    mThursdayButton.setBackgroundColor(getDarkWhiteColor());

                } else {
                    mThursday = false;
                    mThursdayButton.setBackgroundColor(getSemiTransparentColor());
                }
            }
        });

        mFridayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!mFriday) {
                    mFriday = true;
                    mFridayButton.setBackgroundColor(getDarkWhiteColor());

                } else {
                    mFriday = false;
                    mFridayButton.setBackgroundColor(getSemiTransparentColor());
                }
            }
        });

        mSaturdayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!mSaturday) {
                    mSaturday = true;
                    mSaturdayButton.setBackgroundColor(getDarkWhiteColor());

                } else {
                    mSaturday = false;
                    mSaturdayButton.setBackgroundColor(getSemiTransparentColor());
                }
            }
        });

        mSundayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!mSunday) {
                    mSunday = true;
                    mSundayButton.setBackgroundColor(getDarkWhiteColor());

                } else {
                    mSunday = false;
                    mSundayButton.setBackgroundColor(getSemiTransparentColor());
                }
            }
        });

        return view;
    }

    @Override
    public void onTimeSet(RadialPickerLayout radialPickerLayout, int hourOfDay, int minute) {

        mTime = getTime(hourOfDay, minute);
        mTimeButton.setText(mTime);

    }

    public int getSemiTransparentColor() {
        return getResources().getColor(R.color.semi_trans_background);
    }

    public int getDarkWhiteColor() {
        return getResources().getColor(R.color.dark_white_font);
    }

    private String getTime(int hourOfDay, int minute) {

        String hour = "";
        String min = "";

        if (hourOfDay < 10) {
            hour = "0" + hourOfDay;

        } else {
            hour = Integer.toString(hourOfDay);
        }

        if (minute < 10) {
            min = "0" + minute;

        } else {
            min = Integer.toString(minute);
        }

        return hour + ":" + min;
    }
}
