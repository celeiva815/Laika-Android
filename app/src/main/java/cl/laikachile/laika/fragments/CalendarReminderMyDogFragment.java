package cl.laikachile.laika.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.fourmob.datetimepicker.date.DatePickerDialog.OnDateSetListener;
import com.sleepbot.datetimepicker.time.RadialPickerLayout;
import com.sleepbot.datetimepicker.time.TimePickerDialog;

import java.util.Calendar;

import cl.laikachile.laika.R;
import cl.laikachile.laika.models.Dog;
import cl.laikachile.laika.utils.Do;

/**
 * Created by Tito_Leiva on 09-03-15.
 */
public class CalendarReminderMyDogFragment extends Fragment implements OnDateSetListener, TimePickerDialog.OnTimeSetListener{

    private int mIdLayout = R.layout.lk_calendar_reminder_my_dog_fragment;
    public Dog mDog;
    public int mReminderCategory;
    public Button mDateButton;
    public Button mTimeButton;

    public static final String DATEPICKER_TAG = "datepicker";
    public static final String TIMEPICKER_TAG = "timepicker";

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

        final Calendar calendar = Calendar.getInstance();
        final DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), false);
        final TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(this, calendar.get(Calendar.HOUR_OF_DAY) ,calendar.get(Calendar.MINUTE), false, false);

        mTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                timePickerDialog.setCloseOnSingleTapMinute(false);
                timePickerDialog.show(getActivity().getSupportFragmentManager(), TIMEPICKER_TAG);
            }
        });

        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int year = calendar.get(Calendar.YEAR);
                datePickerDialog.setYearRange(year, 2028);
                datePickerDialog.setCloseOnSingleTapDay(false);
                datePickerDialog.show(getActivity().getSupportFragmentManager(), DATEPICKER_TAG);
            }
        });

        return view;
    }

    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {

        mDateButton.setText(Do.getToStringDate(day,month,year-2000));

    }

    @Override
    public void onTimeSet(RadialPickerLayout radialPickerLayout, int hourOfDay, int minute) {

        mTimeButton.setText(getTime(hourOfDay, minute));

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
