package social.laika.app.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import social.laika.app.R;
import social.laika.app.activities.EventsActivity;
import social.laika.app.activities.MainActivity;
import social.laika.app.activities.PublicationsActivity;
import social.laika.app.activities.StoriesActivity;
import social.laika.app.activities.TipsActivity;
import social.laika.app.listeners.ToActivityOnCLickListener;
import social.laika.app.listeners.ToAdoptActivityOnClickListener;
import social.laika.app.listeners.ToMyDogOnCLickListener;

/**
 * Created by Tito_Leiva on 10-02-15.
 */
public class PlaceHolderFragment extends Fragment {

    private int mLayout;

    public PlaceHolderFragment() {
    }

    public PlaceHolderFragment(int mLayout) {

        this.mLayout = mLayout;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(mLayout > 0 ? mLayout : R.layout.laika_main_activity, container, false);

        ImageView adoptImageView = (ImageView) view.findViewById(R.id.gender_new_dog_register_textview);
        ImageView myDogImageView = (ImageView) view.findViewById(R.id.my_dog_main_imageview);
        ImageView newsImageView = (ImageView) view.findViewById(R.id.news_main_imageview);
        ImageView tipsImageView = (ImageView) view.findViewById(R.id.tips_main_imageview);
        ImageView eventsImageView = (ImageView) view.findViewById(R.id.events_main_imageview);
        ImageView storiesImageView = (ImageView) view.findViewById(R.id.stories_main_imageview);

        myDogImageView.setOnClickListener(new ToMyDogOnCLickListener());
        adoptImageView.setOnClickListener(new ToAdoptActivityOnClickListener());
        newsImageView.setOnClickListener(new ToActivityOnCLickListener(PublicationsActivity.class));
        tipsImageView.setOnClickListener(new ToActivityOnCLickListener(TipsActivity.class));
        eventsImageView.setOnClickListener(new ToActivityOnCLickListener(EventsActivity.class));
        storiesImageView.setOnClickListener(new ToActivityOnCLickListener(StoriesActivity.class));

        return view;
    }
}
