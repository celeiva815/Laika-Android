package cl.laikachile.laika.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import cl.laikachile.laika.R;
import cl.laikachile.laika.models.Dog;

/**
 * Created by Tito_Leiva on 09-03-15.
 */
public class MyDogsScreenSlideFragment extends android.support.v4.app.Fragment {

    private int mIdLayout = R.layout.lk_my_dogs_screen_slider_fragment;
    Dog mDog;

    LinearLayout mHistoryLinearLayout;
    LinearLayout mRemindersLinearLayout;
    LinearLayout mHealthLinearLayout;
    LinearLayout mOwnerLinearLayout;
    LinearLayout mAlbumLinearLayout;

    public android.support.v4.app.Fragment mFragment;

    public MyDogsScreenSlideFragment(Dog mDog) {

        this.mDog = mDog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(mIdLayout, container, false);

        mHistoryLinearLayout = (LinearLayout) view.findViewById(R.id.history_my_dog_linearlayout);
        mRemindersLinearLayout = (LinearLayout) view.findViewById(R.id.reminders_my_dog_linearlayout);
        mHealthLinearLayout = (LinearLayout) view.findViewById(R.id.health_my_dog_linearlayout);
        mOwnerLinearLayout = (LinearLayout) view.findViewById(R.id.owner_my_dog_linearlayout);
        mAlbumLinearLayout = (LinearLayout) view.findViewById(R.id.album_my_dog_linearlayout);

        mHistoryLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setHistoryFragment();
            }
        });

        mOwnerLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOwnerFragment();
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        setHistoryFragment();
    }

    private void setListViewHeightBasedOnChildren(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;

        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    public void setHistoryFragment() {

        if (mFragment != null) {
            getChildFragmentManager().beginTransaction().detach(mFragment).commit();
        }

        mFragment = new HistoryMyDogFragment(mDog);
        getChildFragmentManager().beginTransaction().add(R.id.container_my_dog_framelayout, mFragment).commit();
        int color = getResources().getColor(R.color.red_background);
        setBackgrounds(color, Color.TRANSPARENT, Color.TRANSPARENT, Color.TRANSPARENT ,Color.TRANSPARENT);

    }

    public void setReminderFragment() {

        getChildFragmentManager().beginTransaction().add(R.id.container_my_dog_framelayout, mFragment).commit();
    }

    public void setHealthFragment() {


    }

    public void setOwnerFragment() {

        if (mFragment != null) {
            getChildFragmentManager().beginTransaction().detach(mFragment).commit();
        }

        mFragment = new OwnerMyDogFragment(mDog);
        getChildFragmentManager().beginTransaction().add(R.id.container_my_dog_framelayout, mFragment).commit();
        int color = getResources().getColor(R.color.red_background);
        setBackgrounds(Color.TRANSPARENT, Color.TRANSPARENT, Color.TRANSPARENT, color ,Color.TRANSPARENT);

    }

    public void setAlbumFragment() {

        getChildFragmentManager().beginTransaction().add(R.id.container_my_dog_framelayout, mFragment).commit();
    }

    public void setBackgrounds(int history, int reminders, int health, int owner, int album) {

        mHistoryLinearLayout.setBackgroundColor(history);
        mRemindersLinearLayout.setBackgroundColor(reminders);
        mHealthLinearLayout.setBackgroundColor(health);
        mOwnerLinearLayout.setBackgroundColor(owner);
        mAlbumLinearLayout.setBackgroundColor(album);
    }
}

