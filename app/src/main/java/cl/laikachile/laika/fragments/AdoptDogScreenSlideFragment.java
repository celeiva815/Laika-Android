package cl.laikachile.laika.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import cl.laikachile.laika.R;
import cl.laikachile.laika.listeners.ConfirmAdoptionDialogOnClickListener;
import cl.laikachile.laika.listeners.ViewMoreAdoptDogOnClickListener;
import cl.laikachile.laika.models.Dog;
import cl.laikachile.laika.utils.Do;

public class AdoptDogScreenSlideFragment extends Fragment {
	
	private int mIdLayout = R.layout.lk_adopt_dog_screen_slide_fragment;
	private Dog dog;
	private Activity activity;
	
	public AdoptDogScreenSlideFragment(Dog dog, Activity activity) {
		
		this.dog = dog;
		this.activity = activity;
	}

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(mIdLayout, container, false);
        
        TextView nameTextView = (TextView) rootView.findViewById(R.id.name_dogs_screen_slide_textview);
        TextView sizeTextView = (TextView) rootView.findViewById(R.id.size_dogs_screen_slide_textview);
        TextView genderTextView = (TextView) rootView.findViewById(R.id.gender_dogs_screen_slide_textview);
        TextView yearsTextView = (TextView) rootView.findViewById(R.id.years_dogs_screen_slide_textview);
        TextView matchTextView = (TextView) rootView.findViewById(R.id.match_dogs_screen_slide_textview);
        ImageView pictureImageView = (ImageView) rootView.findViewById(R.id.picture_dogs_screen_slide_imageview);
        ImageView viewMoreImageView = (ImageView) rootView.findViewById(R.id.view_more_dogs_screen_slide_imageview);
        
        Button adoptButton = (Button) rootView.findViewById(R.id.search_vet_my_dog_health_button);
       
        nameTextView.setText(dog.mName);
        sizeTextView.setText(dog.mSize);
        genderTextView.setText(dog.getGender(rootView.getContext()));
        yearsTextView.setText(dog.mBirth);
        matchTextView.setText(Integer.toString(Do.randomInteger(50,100)) + "%");
        pictureImageView.setImageResource(dog.mImage);
        
        ViewMoreAdoptDogOnClickListener viewMoreListener = new ViewMoreAdoptDogOnClickListener(dog);
        viewMoreImageView.setOnClickListener(viewMoreListener);
        
        ConfirmAdoptionDialogOnClickListener listener = new ConfirmAdoptionDialogOnClickListener(dog, this.activity);
        adoptButton.setOnClickListener(listener);
        
        return rootView;
    }
	
	
}
