package cl.laikachile.laika.activities;

import android.app.Activity;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Calendar;

import cl.laikachile.laika.R;
import cl.laikachile.laika.models.AlarmReminder;
import cl.laikachile.laika.models.Dog;

public class StopAlarmActivity extends Activity {

    public final static String LOCAL_ID = AlarmReminder.LOCAL_ID;
    public final static String WEEKDAY = AlarmReminder.WEEKDAY;
    public final static String USER_ID = AlarmReminder.USER_ID;

    private int mIdLayout = R.layout.stop_alarm_activity;
    private AlarmReminder mAlarmReminder;
    private Dog mDog;
    private Ringtone mRingtone;
    private int mUserId;
    private int mWeekday;
    private String mTime;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(mIdLayout);
        getIntents();
        setActivityView();

    }

    @Override
    public void onStart() {
        super.onStart();

        startRing();

    }

    @Override
    protected void onStop() {
        super.onStop();

        stopRing();
    }

    public void setActivityView() {

        Button stopButton = (Button) findViewById(R.id.stop_alarm_button);

        stopButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                stopRing();

            }
        });

    }

    public void getIntents() {

        Calendar calendar = Calendar.getInstance();

        Bundle extras = getIntent().getExtras();
        long localId = extras.getLong(LOCAL_ID);
        mUserId = extras.getInt(USER_ID);
        mWeekday = extras.getInt(WEEKDAY, calendar.get(Calendar.DAY_OF_WEEK));
        mTime = extras.getString(AlarmReminder.COLUMN_TIME);
        mAlarmReminder = AlarmReminder.load(AlarmReminder.class, localId);
        mDog = Dog.getSingleDog(mAlarmReminder.mDogId);

    }

    public void startRing() {

        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alarmUri == null) {
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }

        mRingtone = RingtoneManager.getRingtone(getApplicationContext(), alarmUri);
        mRingtone.play();

    }

    public void stopRing() {

        if (mRingtone.isPlaying()) {
            mRingtone.stop();
        }

    }
}
