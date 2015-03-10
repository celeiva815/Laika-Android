package cl.laikachile.laika.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cl.laikachile.laika.R;
import cl.laikachile.laika.models.Story;

public class StoriesScreenSlideFragment extends Fragment {
	
	private int mIdLayout = R.layout.lk_stories_screen_slide_fragment;
	Story story;
	
	public StoriesScreenSlideFragment(Story story) {
		
		this.story = story;
	}

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(mIdLayout, container, false);
        
        TextView bodyTextView = (TextView) rootView.findViewById(R.id.body_story_textview);
        
        bodyTextView.setText(story.aBody);
        
        return rootView;
    }
	
	
}
