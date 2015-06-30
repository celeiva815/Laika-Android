package cl.laikachile.laika.fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;

import java.util.ArrayList;
import java.util.List;

import cl.laikachile.laika.R;
import cl.laikachile.laika.activities.MyDogsActivity;
import cl.laikachile.laika.adapters.HistoryMyDogAdapter;
import cl.laikachile.laika.models.AlarmReminder;
import cl.laikachile.laika.models.CalendarReminder;
import cl.laikachile.laika.models.Dog;
import cl.laikachile.laika.models.History;
import cl.laikachile.laika.network.RequestManager;
import cl.laikachile.laika.network.VolleyManager;
import cl.laikachile.laika.responses.ImageResponse;
import cl.laikachile.laika.utils.Do;
import cl.laikachile.laika.utils.Tag;

/**
 * Created by Tito_Leiva on 09-03-15.
 */
public class HistoryMyDogFragment extends Fragment {

    public static final String KEY_DOG = "dog";
    public static final String TAG = HistoryMyDogFragment.class.getSimpleName();

    public String mTag;
    private int mIdLayout = R.layout.lk_history_my_dog_fragment;
    public Dog mDog;
    public ImageView mDogImageView;
    public TextView mNameTextView;
    public ProgressBar mProgressBar;
    public ListView mHistoryListView;
    public List<History> mHistories;
    public HistoryMyDogAdapter mHistoryAdapter;
    public Dialog mDialog;


    public HistoryMyDogFragment(Dog mDog) {
        this.mDog = mDog;
        this.mTag = Long.toString(mDog.getId());
    }

    public HistoryMyDogFragment() {

    }

    public static final HistoryMyDogFragment newInstance(int dogId)
    {
        HistoryMyDogFragment f = new HistoryMyDogFragment();
        Bundle bdl = new Bundle(1);
        bdl.putInt(KEY_DOG, dogId);
        f.setArguments(bdl);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        int dogId = getArguments().getInt(KEY_DOG);
        mDog = Dog.getSingleDog(dogId);

        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(mIdLayout, container, false);

        mDogImageView = (ImageView) view.findViewById(R.id.dog_history_imageview);
        mNameTextView = (TextView) view.findViewById(R.id.dog_name_history_textview);
        mHistoryListView = (ListView) view.findViewById(R.id.history_my_dog_listview);
        mProgressBar = (ProgressBar) view.findViewById(R.id.download_image_progressbar);
        mHistoryAdapter = new HistoryMyDogAdapter(view.getContext(), R.layout.lk_history_my_dog_row,
                getHistories(view.getContext()));

        mHistoryListView.setAdapter(mHistoryAdapter);
        Do.setListViewHeightBasedOnChildren(mHistoryListView);

        mHistoryListView.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        mHistoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //FIXME solo para editar el recordatorio, falta eliminarlo
                editReminder(mHistories.get(position));

            }
        });

        mNameTextView.setText(mDog.mName);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        requestDogImage();

    }

    @Override
    public void onResume() {
        super.onResume();
        refreshHistories();

    }

    private List<History> getHistories(Context context) {

        mHistories = new ArrayList<>();
        List<CalendarReminder> calendars = CalendarReminder.getDogReminders(mDog.mDogId);
        List<AlarmReminder> alarms = AlarmReminder.getDogReminders(mDog.mDogId);

        if (calendars.size() > 0) {

            for (CalendarReminder c : calendars) {

                mHistories.add(c.toHistory(context));
            }
        }

        if (alarms.size() > 0) {

            for (AlarmReminder a : alarms) {

                mHistories.add(a.toHistory(context));
            }
        }

        return mHistories;

    }

    public void editReminder(History history) {

        MyDogsActivity activity = (MyDogsActivity) getActivity();

        switch (history.mType) {

            case Tag.TYPE_ALARM:

                AlarmReminder alarmReminder = AlarmReminder.getSingleReminder(history.mReminderId);
                activity.setReminderFragment(alarmReminder);

                break;

            case Tag.TYPE_CALENDAR:

                CalendarReminder calendarReminder = CalendarReminder.getSingleReminder(history.mReminderId);
                activity.setReminderFragment(calendarReminder);

                break;

        }
    }

    public void refreshHistories() {

        if (!mHistories.isEmpty()) {
            mHistories.clear();

        }

        mHistories.addAll(getHistories(getActivity().getApplicationContext()));
        mHistoryAdapter.notifyDataSetChanged();
    }

    public void requestDogImage() {

        mProgressBar.setVisibility(View.VISIBLE);
        ImageResponse response = new ImageResponse(mDogImageView, mProgressBar);
        Request request = RequestManager.imageRequest(mDog.getImage(Tag.IMAGE_MEDIUM_S),
                mDogImageView, response, response);

        VolleyManager.getInstance(getActivity().getApplicationContext()).addToRequestQueue(request);


    }

}
