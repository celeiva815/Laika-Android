package social.laika.app.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.sleepbot.datetimepicker.time.RadialPickerLayout;
import com.sleepbot.datetimepicker.time.TimePickerDialog;

import org.json.JSONObject;

import java.util.Calendar;

import social.laika.app.R;
import social.laika.app.interfaces.Requestable;
import social.laika.app.models.AlarmReminder;
import social.laika.app.models.Dog;
import social.laika.app.network.RequestManager;
import social.laika.app.network.VolleyManager;
import social.laika.app.responses.CreateAlarmReminderResponse;
import social.laika.app.utils.Do;
import social.laika.app.utils.PrefsManager;
import social.laika.app.utils.Tag;

/**
 * Created by Tito_Leiva on 09-03-15.
 */
public class AlarmReminderMyDogFragment extends Fragment implements
        TimePickerDialog.OnTimeSetListener, Requestable{

    public static final String TAG = AlarmReminderMyDogFragment.class.getSimpleName();
    public static final String TIMEPICKER_TAG = "timepicker";

    private int mIdLayout = R.layout.lk_alarm_reminder_my_dog_fragment;
    public Dog mDog;
    public int mReminderCategory;
    public Fragment mFragment;

    public AlarmReminder mAlarmReminder;
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
    public int mRequestMethod;

    public String mTime;

    //TODO cambiarlo a la forma comun de instanciar fragments
    public AlarmReminderMyDogFragment(Dog mDog, int mReminderCategory) {

        this.mDog = mDog;
        this.mReminderCategory = mReminderCategory;
        this.mRequestMethod = RequestManager.METHOD_POST;
    }

    public AlarmReminderMyDogFragment(Dog mDog, AlarmReminder alarmReminder) {

        this.mDog = mDog;
        this.mAlarmReminder = alarmReminder;
        this.mRequestMethod = RequestManager.METHOD_PATCH;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(mIdLayout, container, false);

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

                Context context = v.getContext();

                String title = mTitleEditText.getText().toString();
                String detail = mDetailEditText.getText().toString();

                //TODO agregar el owner otras variables y verificar que no sean nullOrEmpty.

                if (mAlarmReminder != null) {

                    //FIXME este es el método para actualizar en la API

                    mAlarmReminder.mTitle = title;
                    mAlarmReminder.mDetail = detail;
                    mAlarmReminder.mHasMonday = mMonday;
                    mAlarmReminder.mHasTuesday = mTuesday;
                    mAlarmReminder.mHasWednesday = mWednesday;
                    mAlarmReminder.mHasThursday = mThursday;
                    mAlarmReminder.mHasFriday = mFriday;
                    mAlarmReminder.mHasSaturday = mSaturday;
                    mAlarmReminder.mHasSunday = mSunday;
                    mAlarmReminder.mTime = mTime;
                    mAlarmReminder.mOwnerId = PrefsManager.getUserId(context);

                    mAlarmReminder.save();

                    String message = Do.getRString(context, R.string.edit_reminder_added);
                    Do.showLongToast(message, context);

                } else {

                    mAlarmReminder = new AlarmReminder(Tag.TYPE_ALARM,
                            mReminderCategory, title, detail, Tag.STATUS_IN_PROGRESS, mMonday,
                            mTuesday, mWednesday, mThursday, mFriday, mSaturday, mSunday, mTime,
                            PrefsManager.getUserId(context), mDog.mDogId);

                    if (Do.isNetworkAvailable(context)) {
                        request();

                    }  else {

                        //TODO cachar bien como hacer el sync de las alarms no subidas
                        mAlarmReminder.save();
                        mAlarmReminder.setAlarm(context);

                        onSuccess();

                    }

                }
            }
        });

        mMondayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setMonday(!mMonday);

            }
        });

        mTuesdayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setTuesday(!mTuesday);
            }
        });

        mWednesdayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setWednesday(!mWednesday);
            }
        });

        mThursdayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setThursday(!mThursday);
            }
        });

        mFridayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setFriday(!mFriday);
            }
        });

        mSaturdayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setSaturday(!mSaturday);
            }
        });

        mSundayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setSunday(!mSunday);
            }
        });

        if (mAlarmReminder != null) {

            mTitleEditText.setText(mAlarmReminder.mTitle);
            mDetailEditText.setText(mAlarmReminder.mDetail);

            mTime = mAlarmReminder.mTime;
            mTimeButton.setText(mTime);

            setMonday(mAlarmReminder.mHasMonday);
            setTuesday(mAlarmReminder.mHasTuesday);
            setWednesday(mAlarmReminder.mHasWednesday);
            setThursday(mAlarmReminder.mHasThursday);
            setFriday(mAlarmReminder.mHasFriday);
            setSaturday(mAlarmReminder.mHasSaturday);
            setSunday(mAlarmReminder.mHasSunday);
        }

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

    public int getLightRedColor() {
        return getResources().getColor(R.color.laika_light_red);
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

    public void setMonday(boolean day) {

        if (day) {
            mMonday = true;
            mMondayButton.setBackgroundColor(getLightRedColor());

        } else {
            mMonday = false;
            mMondayButton.setBackgroundColor(getSemiTransparentColor());
        }

    }

    public void setTuesday(boolean day) {

        if (day) {
            mTuesday = true;
            mTuesdayButton.setBackgroundColor(getLightRedColor());

        } else {
            mTuesday = false;
            mTuesdayButton.setBackgroundColor(getSemiTransparentColor());
        }
    }
    public void setWednesday(boolean day) {

        if (day) {
            mWednesday = true;
            mWednesdayButton.setBackgroundColor(getLightRedColor());

        } else {
            mWednesday = false;
            mWednesdayButton.setBackgroundColor(getSemiTransparentColor());
        }
    }

    public void setThursday(boolean day) {

        if (day) {
            mThursday = true;
            mThursdayButton.setBackgroundColor(getLightRedColor());

        } else {
            mThursday = false;
            mThursdayButton.setBackgroundColor(getSemiTransparentColor());
        }

    }
    public void setFriday(boolean day) {

        if (day) {
            mFriday = true;
            mFridayButton.setBackgroundColor(getLightRedColor());

        } else {
            mFriday = false;
            mFridayButton.setBackgroundColor(getSemiTransparentColor());
        }

    }
    public void setSaturday(boolean day) {

        if (day) {
            mSaturday = true;
            mSaturdayButton.setBackgroundColor(getLightRedColor());

        } else {
            mSaturday = false;
            mSaturdayButton.setBackgroundColor(getSemiTransparentColor());
        }

    }
    public void setSunday(boolean day) {

        if (day) {
            mSunday = true;
            mSundayButton.setBackgroundColor(getLightRedColor());

        } else {
            mSunday = false;
            mSundayButton.setBackgroundColor(getSemiTransparentColor());
        }
    }

    @Override
    public void request() {

        Context context = getActivity().getApplicationContext();
        JSONObject jsonParams = mAlarmReminder.getJsonObject();
        CreateAlarmReminderResponse response = new CreateAlarmReminderResponse(context, mDog,this);

        Request createRequest = RequestManager.defaultRequest(mRequestMethod, jsonParams,
                RequestManager.ADDRESS_ALERT_REMINDERS, response, response,
                PrefsManager.getUserToken(context));

        VolleyManager.getInstance(context).addToRequestQueue(createRequest, TAG);

    }

    @Override
    public void onSuccess() {

        Context context = getActivity().getApplicationContext();
        String message = Do.getRString(context, R.string.new_reminder_added);

        Do.showLongToast(message, context);
        getActivity().onBackPressed();

    }

    @Override
    public void onFailure() {



    }

    //Data Base



}
