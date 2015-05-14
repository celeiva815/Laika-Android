package cl.laikachile.laika.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cl.laikachile.laika.R;
import cl.laikachile.laika.activities.MyDogsActivity;
import cl.laikachile.laika.adapters.HistoryMyDogAdapter;
import cl.laikachile.laika.models.AlarmReminder;
import cl.laikachile.laika.models.CalendarReminder;
import cl.laikachile.laika.models.Dog;
import cl.laikachile.laika.models.History;
import cl.laikachile.laika.network.RequestManager;
import cl.laikachile.laika.network.VolleyManager;
import cl.laikachile.laika.responses.AlarmRemindersResponse;
import cl.laikachile.laika.responses.CalendarRemindersResponse;
import cl.laikachile.laika.utils.PrefsManager;
import cl.laikachile.laika.utils.Tag;

/**
 * Created by Tito_Leiva on 09-03-15.
 */
public class HistoryMyDogFragment extends Fragment {

    public static final String TAG = HistoryMyDogFragment.class.getSimpleName();

    public String mTag;
    private int mIdLayout = R.layout.lk_history_my_dog_fragment;
    public Dog mDog;
    public List<History> mHistories;
    public HistoryMyDogAdapter mHistoryAdapter;
    public Dialog mDialog;


    public HistoryMyDogFragment(Dog mDog) {
        this.mDog = mDog;
        this.mTag = Long.toString(mDog.getId());
    }

    public HistoryMyDogFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(mIdLayout, container, false);

        ListView historyListView = (ListView) view.findViewById(R.id.history_my_dog_listview);
        mHistoryAdapter = new HistoryMyDogAdapter(view.getContext(), R.layout.lk_history_my_dog_row,
                getHistories(view.getContext()));

        historyListView.setAdapter(mHistoryAdapter);

        historyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //FIXME solo para editar el recordatorio
                editReminder(mHistories.get(position));

            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (mHistories.isEmpty()) {
            requestAlarmReminders();
        }

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

    public void requestAlarmReminders() {

        Context context = getActivity().getApplicationContext();
        Map<String,String> params = new HashMap<>();
        AlarmRemindersResponse response = new AlarmRemindersResponse(this);

        params.put(Dog.COLUMN_DOG_ID, Integer.toString(mDog.mDogId));

        Request request = RequestManager.getRequest(params,
                RequestManager.ADDRESS_ALERT_REMINDERS, response, response,
                PrefsManager.getUserToken(context));

        VolleyManager.getInstance(context)
                .addToRequestQueue(request, TAG);


    }

    public void requestCalendarReminders() {

        Context context = getActivity().getApplicationContext();
        Map<String,String> params = new HashMap<>();
        CalendarRemindersResponse response = new CalendarRemindersResponse(this);

        params.put(Dog.COLUMN_DOG_ID, Integer.toString(mDog.mDogId));

        Request request = RequestManager.getRequest(params,
                RequestManager.ADDRESS_CALENDAR_REMINDERS, response, response,
                PrefsManager.getUserToken(context));

        VolleyManager.getInstance(context)
                .addToRequestQueue(request, TAG);


    }

}
