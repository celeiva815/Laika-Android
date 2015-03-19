package cl.laikachile.laika.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.fourmob.datetimepicker.date.DatePickerDialog.OnDateSetListener;
import com.sleepbot.datetimepicker.time.RadialPickerLayout;
import com.sleepbot.datetimepicker.time.TimePickerDialog;

import java.util.Calendar;

import cl.laikachile.laika.R;
import cl.laikachile.laika.models.CalendarReminder;
import cl.laikachile.laika.models.Dog;
import cl.laikachile.laika.utils.Do;
import cl.laikachile.laika.utils.Tag;

/**
 * Created by Tito_Leiva on 09-03-15.
 */
public class CalendarReminderMyDogFragment extends Fragment implements OnDateSetListener, TimePickerDialog.OnTimeSetListener{

    private int mIdLayout = R.layout.lk_calendar_reminder_my_dog_fragment;
    public Dog mDog;
    public int mReminderCategory;

    public EditText mTitleEditText;
    public EditText mDetailEditText;
    public Button mDateButton;
    public Button mTimeButton;
    public Button mSaveButton;
    public String mDate;
    public String mTime;

    public CalendarReminderMyDogFragment(Dog mDog, int mReminderCategory) {

        this.mDog = mDog;
        this.mReminderCategory = mReminderCategory;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(mIdLayout, container, false);
        mDateButton = (Button) view.findViewById(R.id.date_alarm_remind_my_dog_button);
        mTimeButton = (Button) view.findViewById(R.id.time_alarm_remind_my_dog_button);
        mSaveButton = (Button) view.findViewById(R.id.save_calendar_remind_my_dog_button);

        mTitleEditText = (EditText) view.findViewById(R.id.title_calendar_reminder_my_dog_edittext);
        mDetailEditText = (EditText) view.findViewById(R.id.detail_calendar_reminder_my_dog_edittext);

        final Calendar calendar = Calendar.getInstance();
        final DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), false);
        final TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(this, calendar.get(Calendar.HOUR_OF_DAY) ,calendar.get(Calendar.MINUTE), false, false);

        mTime = getTime(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
        mDate = Do.getToStringDate(calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.MONTH),calendar.get(Calendar.YEAR)-2000);

        mTimeButton.setText(mTime);
        mDateButton.setText(mDate);

        mTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                timePickerDialog.setCloseOnSingleTapMinute(false);
                timePickerDialog.show(getActivity().getSupportFragmentManager(), Tag.TIME_PICKER);
            }
        });

        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int year = calendar.get(Calendar.YEAR);
                datePickerDialog.setYearRange(year, 2028);
                datePickerDialog.setCloseOnSingleTapDay(false);
                datePickerDialog.show(getActivity().getSupportFragmentManager(), Tag.DATE_PICKER);
            }
        });

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = mTitleEditText.getText().toString();
                String detail = mDetailEditText.getText().toString();

                //FIXME agregar el owner otras variables y verificar que no sean nullOrEmpty.
                CalendarReminder reminder = new CalendarReminder(CalendarReminder.ID++,Tag.TYPE_CALENDAR,
                        mReminderCategory, title, detail, mDate, mTime, 1, mDog.mDogId);

                reminder.save();

                String message = Do.getRString(v.getContext(), R.string.new_reminder_added);
                Do.showToast(message,v.getContext(), Toast.LENGTH_LONG);


            }
        });

        return view;
    }

    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {

        mDate = Do.getToStringDate(day,month,year-2000);
        mDateButton.setText(mDate);

    }

    @Override
    public void onTimeSet(RadialPickerLayout radialPickerLayout, int hourOfDay, int minute) {

        mTime = getTime(hourOfDay, minute);
        mTimeButton.setText(mTime);

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

        String time = hour + ":" + min;

        return time;
    }

}
