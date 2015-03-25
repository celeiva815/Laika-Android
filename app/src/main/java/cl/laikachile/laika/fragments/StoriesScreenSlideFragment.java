package cl.laikachile.laika.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import cl.laikachile.laika.R;
import cl.laikachile.laika.models.Story;

public class StoriesScreenSlideFragment extends Fragment {
	
	private int mIdLayout = R.layout.lk_stories_screen_slide_fragment;
	Story mStory;
	
	public StoriesScreenSlideFragment(Story mStory) {
		
		this.mStory = mStory;
	}

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(mIdLayout, container, false);

        TextView titleTextView = (TextView) rootView.findViewById(R.id.title_stories_textview);
        TextView sponsorTextView = (TextView) rootView.findViewById(R.id.owner_stories_textview);
        TextView dateTextView = (TextView) rootView.findViewById(R.id.date_stories_textview);
        TextView bodyTextView = (TextView) rootView.findViewById(R.id.body_stories_textview);
        ImageView mainImageView = (ImageView) rootView.findViewById(R.id.main_stories_imageview);

        titleTextView.setText(mStory.mTitle);
        sponsorTextView.setText(mStory.getOwnerName());
        dateTextView.setText(mStory.mDate);
        bodyTextView.setText(mStory.mBody);
        mainImageView.setImageResource(mStory.mImage);

        return rootView;
    }
	
	
}
