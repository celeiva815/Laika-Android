package social.laika.app.activities;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;

import java.util.Calendar;

import social.laika.app.R;
import social.laika.app.models.AlarmReminder;
import social.laika.app.models.Dog;
import social.laika.app.network.Api;
import social.laika.app.network.VolleyManager;
import social.laika.app.responses.ImageResponse;
import social.laika.app.utils.Tag;

public class StopAlarmActivity extends Activity {

    public static final String TAG = StopAlarmActivity.class.getSimpleName();

    public final static String LOCAL_ID = AlarmReminder.LOCAL_ID;
    public final static String WEEKDAY = AlarmReminder.WEEKDAY;
    public final static String USER_ID = AlarmReminder.USER_ID;

    private int mIdLayout = R.layout.stop_alarm_activity;
    private AlarmReminder mAlarmReminder;
    private Dog mDog;
    private MediaPlayer mMediaPlayer;
    private int mUserId;
    private int mWeekday;
    private String mTime;

    private Button mAcceptButton;
    private Button mCancelButton;
    private TextView mAlarmTextView;
    private TextView mDetailTextView;
    private ImageView mDogImageView;
    private ImageView mCategoryImageView;
    private ProgressBar mProgressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(mIdLayout);
        getIntents();
        setActivityView();
        requestDogImage();

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

    @Override
    public void onBackPressed() {

        Log.d("CDA", "onBackPressed Called");
        Intent setIntent = new Intent(Intent.ACTION_MAIN);
        setIntent.addCategory(Intent.CATEGORY_HOME);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(setIntent);
        finish();
    }

    public void setActivityView() {

        mAcceptButton = (Button) findViewById(R.id.accept_stop_alarm_button);
        mCancelButton = (Button) findViewById(R.id.cancel_stop_alarm_button);
        mAlarmTextView = (TextView) findViewById(R.id.alarm_title_stop_alarm_textview);
        mDetailTextView = (TextView) findViewById(R.id.alarm_detail_stop_alarm_textview);
        mDogImageView = (ImageView) findViewById(R.id.dog_stop_alarm_imageview);
        mCategoryImageView = (ImageView) findViewById(R.id.category_stop_alarm_imageview);
        mProgressBar = (ProgressBar) findViewById(R.id.image_stop_alarm_progressbar);

        mAlarmTextView.setText(mAlarmReminder.mTime + " - " + mAlarmReminder.mTitle);
        mDetailTextView.setText(mAlarmReminder.mDetail);
        setCategoryImage();

        mAcceptButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                stopRing();
                finish();

            }
        });

        mCancelButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                stopRing();
                finish();

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

        mMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.ladrido);
        mMediaPlayer.setVolume(1,1);
        mMediaPlayer.setLooping(true);
        mMediaPlayer.start();

    }

    public void stopRing() {

        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
        }

    }

    public void requestDogImage() {

        ImageResponse response = new ImageResponse(this, mDogImageView, mProgressBar);
        Request imageRequest = Api.imageRequest(mDog.getImage(Tag.IMAGE_MEDIUM), mDogImageView, response, response);
        VolleyManager.getInstance(getApplicationContext()).addToRequestQueue(imageRequest, TAG);

    }

    public void setCategoryImage(){

        int imageId = mAlarmReminder.mCategory;

        switch (imageId) {

            case Tag.CATEGORY_FOOD:

                mCategoryImageView.setImageResource(R.drawable.dog51_white);

                break;

            case Tag.CATEGORY_POO:

                mCategoryImageView.setImageResource(R.drawable.poo_white);

                break;

            case Tag.CATEGORY_WALK:

                mCategoryImageView.setImageResource(R.drawable.walk_white);
                break;

            case Tag.CATEGORY_MEDICINE:
                mCategoryImageView.setImageResource(R.drawable.pill_white);
                break;

            case Tag.CATEGORY_VACCINE:
                mCategoryImageView.setImageResource(R.drawable.medicine_white);
                break;

            case Tag.CATEGORY_HYGIENE:
                mCategoryImageView.setImageResource(R.drawable.hygiene_white);
                break;

            case Tag.CATEGORY_VET:
                mCategoryImageView.setImageResource(R.drawable.cross_white);
                break;
        }
    }
}
