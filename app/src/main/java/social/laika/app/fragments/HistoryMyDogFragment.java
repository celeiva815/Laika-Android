package social.laika.app.fragments;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.activeandroid.Model;
import com.android.volley.Request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import social.laika.app.R;
import social.laika.app.activities.CreateReminderActivity;
import social.laika.app.adapters.HistoryMyDogAdapter;
import social.laika.app.interfaces.Refreshable;
import social.laika.app.models.AlarmReminder;
import social.laika.app.models.CalendarReminder;
import social.laika.app.models.Dog;
import social.laika.app.models.History;
import social.laika.app.network.RequestManager;
import social.laika.app.network.VolleyManager;
import social.laika.app.network.sync.SyncService;
import social.laika.app.network.sync.SyncUtils;
import social.laika.app.responses.RemindersResponse;
import social.laika.app.utils.PrefsManager;
import social.laika.app.utils.Tag;

/**
 * Created by Tito_Leiva on 09-03-15.
 */
public class HistoryMyDogFragment extends Fragment implements Refreshable {

    public static final String KEY_DOG = "dog";
    public static final String TAG = HistoryMyDogFragment.class.getSimpleName();

    public String mTag;
    private int mIdLayout = R.layout.simple_listview;
    public Dog mDog;
    public ListView mHistoryListView;
    public List<History> mHistories;
    public HistoryMyDogAdapter mHistoryAdapter;


    public HistoryMyDogFragment() {

    }

    public static final HistoryMyDogFragment newInstance(int dogId) {
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
        checkAlarmsUp(getActivity().getApplicationContext());

        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(mIdLayout, container, false);

        mHistoryListView = (ListView) view.findViewById(R.id.simple_listview);
        mHistoryAdapter = new HistoryMyDogAdapter(view.getContext(), R.layout.lk_history_my_dog_row,
                getHistories(view.getContext()));

        mHistoryListView.setAdapter(mHistoryAdapter);

        mHistoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final Context context = view.getContext();
                final int pos = position;
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);

                dialog.setTitle(R.string.choose_an_option);
                dialog.setItems(new CharSequence[]{"Editar", "Eliminar"},
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                switch (which) {

                                    case 0: // editar alarma

                                        editReminder(mHistories.get(pos));
                                        break;

                                    case 1: // eliminar alarma

                                        deleteReminder(mHistories.get(pos));

                                        break;

                                }

                            }
                        });

                dialog.show();
            }
        });

        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (mHistoryListView.getCount() == 0) {

            requestReminders();

        }

    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();

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

    private List<History> getAlarmHistories(Context context, List<AlarmReminder> alarms) {

        mHistories = new ArrayList<>();
        List<CalendarReminder> calendars = CalendarReminder.getDogReminders(mDog.mDogId);

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

    private List<History> getCalendarHistories(Context context, List<CalendarReminder> calendars) {

        mHistories = new ArrayList<>();
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

        Context context = getActivity().getApplicationContext();
        Intent intent = new Intent(context, CreateReminderActivity.class);

        switch (history.mType) {

            case Tag.TYPE_ALARM:

                intent.putExtra(CreateReminderActivity.KEY_ALARM_ID, history.mReminderId);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);


                break;

            case Tag.TYPE_CALENDAR:

                intent.putExtra(CreateReminderActivity.KEY_CALENDAR_ID, history.mReminderId);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

                break;

        }
    }

    public void deleteReminder(History history) {

        Context context = getActivity().getApplicationContext();

        switch (history.mType) {

            case Tag.TYPE_ALARM:

                AlarmReminder reminder = AlarmReminder.getSingleReminder(history.mReminderId);
                reminder.cancelAlarm(context);

                if (reminder.mAlarmReminderId > 0) {
                    Bundle data = new Bundle();

                    data.putInt(SyncUtils.CODE, SyncUtils.CODE_ALARM_DELETE);
                    data.putInt(AlarmReminder.COLUMN_ALARM_REMINDER_ID, reminder.mAlarmReminderId);

                    SyncUtils.triggerRefresh(data);
                }

                Model.delete(AlarmReminder.class, reminder.getId());

                break;

            case Tag.TYPE_CALENDAR:


                break;

        }

        refresh();
    }

    public void requestReminders() {

        Context context = getActivity().getApplicationContext();
        Map<String, String> params = new HashMap<>();
        params.put(Dog.COLUMN_DOG_ID, Integer.toString(mDog.mDogId));

        RemindersResponse response = new RemindersResponse(this, mDog, context);
        Request eventsRequest = RequestManager.getRequest(params, RequestManager.ADDRESS_ALERT_REMINDERS,
                response, response, PrefsManager.getUserToken(context));

        VolleyManager.getInstance(context).addToRequestQueue(eventsRequest, TAG);

    }

    @Override
    public void refresh() {

        if (!mHistories.isEmpty()) {
            mHistories.clear();

        }

        mHistories.addAll(getHistories(getActivity().getApplicationContext()));
        mHistoryAdapter.notifyDataSetChanged();
    }

    public void checkAlarmsUp(Context context) {

        List<AlarmReminder> alarms = AlarmReminder.getDogReminders(mDog.mDogId);

        for (AlarmReminder alarm : alarms) {

            Log.i(alarm.mTitle, "CHECKING EVERY WEEKDAY");

            alarm.checkAlarm(context);
            alarm.delete();


        }
    }

    public Uri createUri() {

        StringBuilder uri = new StringBuilder();
        uri.append("content://");
        uri.append(getActivity().getPackageName());
        uri.append("/");
        return Uri.parse(uri.toString());
    }



}
