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

import com.android.volley.Request;

import cl.laikachile.laika.R;
import cl.laikachile.laika.listeners.ConfirmAdoptionDialogOnClickListener;
import cl.laikachile.laika.models.Dog;
import cl.laikachile.laika.network.RequestManager;
import cl.laikachile.laika.network.VolleyManager;
import cl.laikachile.laika.responses.ImageResponse;
import cl.laikachile.laika.utils.Do;

public class AdoptDogScreenSlideFragment extends Fragment {

    public static final String TAG = AdoptDogScreenSlideFragment.class.getSimpleName();

	private int mIdLayout = R.layout.lk_adopt_dog_screen_slide_fragment;
	private Dog mDog;
	private Activity activity;
    public ImageView mPictureImageView;
	
	public AdoptDogScreenSlideFragment(Dog mDog, Activity activity) {
		
		this.mDog = mDog;
		this.activity = activity;
	}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(mIdLayout, container, false);

        mPictureImageView = (ImageView) view.findViewById(R.id.picture_dogs_screen_slide_imageview);
        TextView matchTextView = (TextView) view.findViewById(R.id.match_dogs_screen_slide_textview);
        TextView nameTextView = (TextView) view.findViewById(R.id.name_dogs_screen_slide_textview);
        TextView genderTextView = (TextView) view.findViewById(R.id.gender_dogs_screen_slide_textview);
        TextView sterilizedTextView = (TextView) view.findViewById(R.id.sterilized_dogs_screen_slide_textview);
        TextView chipTextView = (TextView) view.findViewById(R.id.chip_dogs_screen_slide_textview);
        TextView trainedTextView = (TextView) view.findViewById(R.id.trained_dogs_screen_slide_textview);
        TextView sizeTextView = (TextView) view.findViewById(R.id.size_dogs_screen_slide_textview);
        TextView yearsTextView = (TextView) view.findViewById(R.id.years_dogs_screen_slide_textview);
        TextView detailsTextView = (TextView) view.findViewById(R.id.detail_adopt_dog_textview);
        Button postulateButton = (Button) view.findViewById(R.id.postulate_adopt_dog_button);

        nameTextView.setText(mDog.mName);
        sizeTextView.setText(mDog.getSize().mName);
        genderTextView.setText(mDog.getGender(view.getContext()));
        yearsTextView.setText(mDog.mBirth);
        sterilizedTextView.setText(mDog.getSterilized(view.getContext()));
        chipTextView.setText(mDog.getChip(view.getContext()));
        trainedTextView.setText(mDog.getTrained(view.getContext()));
        matchTextView.setText(Integer.toString(Do.randomInteger(50,100)) + "%"); //FIXME
        detailsTextView.setText(mDog.mDetail);

        ConfirmAdoptionDialogOnClickListener listener = new ConfirmAdoptionDialogOnClickListener(
                mDog, this.activity);
        postulateButton.setOnClickListener(listener);
        
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        requestDogImage(mPictureImageView);
    }

    public void requestDogImage(ImageView imageView) {

        ImageResponse response = new ImageResponse(getActivity(), imageView);
        Request imageRequest = RequestManager.imageRequest(mDog.mUrlImage, imageView, response,
                response);

        VolleyManager.getInstance(getActivity().getApplicationContext())
                .addToRequestQueue(imageRequest, TAG);

    }
	
	
}
