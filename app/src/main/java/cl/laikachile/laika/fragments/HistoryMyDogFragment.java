package cl.laikachile.laika.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import cl.laikachile.laika.R;
import cl.laikachile.laika.activities.MyDogsActivity;
import cl.laikachile.laika.adapters.HistoryMyDogAdapter;
import cl.laikachile.laika.models.AlarmReminder;
import cl.laikachile.laika.models.CalendarReminder;
import cl.laikachile.laika.models.Dog;
import cl.laikachile.laika.models.History;

/**
 * Created by Tito_Leiva on 09-03-15.
 */
public class HistoryMyDogFragment extends Fragment {

    public String mTag;
    private int mIdLayout = R.layout.lk_history_my_dog_fragment;
    public Dog mDog;
    public List<History> mHistories;
    public HistoryMyDogAdapter mHistoryAdapter;

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


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        mHistories = getHistories(getActivity().getApplicationContext());
        mHistoryAdapter.notifyDataSetChanged();
    }

    private List<History> getHistories(Context context) {

        //FIXME cambiar por las historias sacadas de la API
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



}
