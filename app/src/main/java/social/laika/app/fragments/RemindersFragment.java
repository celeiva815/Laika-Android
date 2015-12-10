package social.laika.app.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import social.laika.app.R;
import social.laika.app.activities.CreateReminderActivity;
import social.laika.app.adapters.RemindersAdapter;
import social.laika.app.interfaces.Refreshable;
import social.laika.app.models.AlarmReminder;
import social.laika.app.models.CalendarReminder;
import social.laika.app.models.Dog;
import social.laika.app.models.Reminder;
import social.laika.app.utils.Flurry;
import social.laika.app.utils.Tag;

/**
 * Created by Tito_Leiva on 09-03-15.
 */
public class RemindersFragment extends Fragment implements Refreshable {

    public static final String KEY_DOG = "mDog";
    public static final String TAG = RemindersFragment.class.getSimpleName();

    public String mTag;
    private int mIdLayout = R.layout.simple_listview;
    public Dog mDog;
    public ListView mRemindersListView;
    public TextView mEmptyTextView;
    private List<Reminder> mReminders;
    public RemindersAdapter mRemindersAdapter;


    public RemindersFragment() {

    }

    public static final RemindersFragment newInstance(int dogId) {
        RemindersFragment f = new RemindersFragment();
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

        mRemindersListView = (ListView) view.findViewById(R.id.simple_listview);
        mEmptyTextView = (TextView) view.findViewById(R.id.empty_view);
        mRemindersAdapter = new RemindersAdapter(view.getContext(), R.layout.lk_history_my_dog_row,
                getHistories(view.getContext()));

        mEmptyTextView.setText("Agrega todos los recordatorios para el cuidado de " + mDog.mName);
        mRemindersListView.setAdapter(mRemindersAdapter);
        mRemindersListView.setEmptyView(mEmptyTextView);

        mRemindersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final Context context = view.getContext();
                final Reminder reminder = mReminders.get(position);
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);

                dialog.setTitle(R.string.choose_an_option);
                dialog.setItems(getItems(),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                switch (which) {
                                    case 0: // editar alarma
                                        editReminder(reminder);
                                        break;
                                    case 1: // eliminar alarma
                                        deleteReminder(reminder);
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

        if (mRemindersListView.getCount() == 0) {

            // requestReminders();

        }

    }

    @Override
    public void onStart() {
        super.onStart();

        Flurry.logTimedEvent(Flurry.DOG_REMINDERS_TIME);
    }

    @Override
    public void onStop() {

        Flurry.endTimedEvent(Flurry.DOG_REMINDERS_TIME);
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();

    }

    private List<Reminder> getHistories(Context context) {

        if (mReminders == null) {
            mReminders = new ArrayList<>();
        } else {
            mReminders.clear();
        }

        List<CalendarReminder> calendars = CalendarReminder.getDogReminders(mDog.mDogId);
        List<AlarmReminder> alarms = AlarmReminder.getDogReminders(mDog.mDogId);

        for (CalendarReminder c : calendars) {
            mReminders.add(c.toHistory(context));
        }
        for (AlarmReminder a : alarms) {
            mReminders.add(a.toHistory(context));
        }

        Collections.sort(mReminders, Collections.reverseOrder());

        return mReminders;

    }

    public void editReminder(Reminder reminder) {

        Context context = getActivity().getApplicationContext();
        Intent intent = new Intent(context, CreateReminderActivity.class);

        switch (reminder.mType) {

            case Tag.TYPE_ALARM:

                intent.putExtra(CreateReminderActivity.KEY_ALARM_ID, reminder.mReminderId);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);


                break;

            case Tag.TYPE_CALENDAR:

                intent.putExtra(CreateReminderActivity.KEY_CALENDAR_ID, reminder.mReminderId);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

                break;

        }
    }

    public void deleteReminder(final Reminder history) {

        final Context context = getActivity().getApplicationContext();

        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());

        dialog.setMessage("¿Estás seguro de eliminar este recordatorio?");
        dialog.setPositiveButton(R.string.accept_dialog, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch (history.mType) {

                    case Tag.TYPE_ALARM:

                        AlarmReminder reminder = AlarmReminder.getSingleReminder(history.mReminderId);

                        reminder.cancelAlarm(context);
                        reminder.remove();
                        break;

                    case Tag.TYPE_CALENDAR:

                        CalendarReminder calendarReminder = CalendarReminder.getSingleReminder(history.mReminderId);

                        calendarReminder.cancelAlarm(context);
                        calendarReminder.remove();
                        break;

                }

                mRemindersAdapter.remove(history);
                refresh();

            }
        });

        dialog.setNegativeButton(R.string.cancel_dialog, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });

        dialog.show();
    }

    /*public void requestReminders() {

        Context context = getActivity().getApplicationContext();
        Map<String, String> params = new HashMap<>();
        params.put(Dog.COLUMN_DOG_ID, Integer.toString(mDog.mDogId));

        RemindersResponse response = new RemindersResponse(this, mDog, context);
        Request eventsRequest = Api.getRequest(params, Api.ADDRESS_ALERT_REMINDERS,
                response, response, PrefsManager.getUserToken(context));

        VolleyManager.getInstance(context).addToRequestQueue(eventsRequest, TAG);
    }*/

    @Override
    public void refresh() {

        getHistories(getActivity().getApplicationContext());
        mRemindersAdapter.notifyDataSetChanged();
    }

    public void checkAlarmsUp(Context context) {

        List<AlarmReminder> alarms = AlarmReminder.getDogReminders(mDog.mDogId);

        for (AlarmReminder alarm : alarms) {

            Log.i(alarm.mTitle, "CHECKING EVERY WEEKDAY");
            alarm.checkStatusAlarm(context);

        }
    }

    public Uri createUri() {

        StringBuilder uri = new StringBuilder();
        uri.append("content://");
        uri.append(getActivity().getPackageName());
        uri.append("/");
        return Uri.parse(uri.toString());
    }

    public CharSequence[] getItems() {
        return new CharSequence[]{"Editar", "Eliminar"};
    }

}
