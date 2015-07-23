package cl.laikachile.laika.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import cl.laikachile.laika.R;
import cl.laikachile.laika.activities.AdoptDogsFragmentActivity;
import cl.laikachile.laika.listeners.ConfirmAdoptionDialogOnClickListener;
import cl.laikachile.laika.models.Dog;
import cl.laikachile.laika.network.RequestManager;
import cl.laikachile.laika.network.VolleyManager;
import cl.laikachile.laika.responses.ImageResponse;
import cl.laikachile.laika.utils.Do;

public class AdoptDogScreenSlideFragment extends Fragment {

    public static final String TAG = AdoptDogScreenSlideFragment.class.getSimpleName();

	private int mIdLayout = R.layout.lk_adopt_dog_screen_slide_fragment;
	public Dog mDog;
	public Activity mActivity;
    public ImageView mPictureImageView;
    public ProgressDialog mProgressDialog;
    public ProgressBar mProgressBar;
    public SlidingUpPanelLayout mSlidingUpPanelLayout;
    public TextView mMatchTextView;
    public TextView mNameTextView;
    public TextView mGenderTextView;
    public TextView mSterilizedTextView;
    public TextView mChipTextView;
    public TextView mTrainedTextView;
    public TextView mSizeTextView;
    public TextView mYearsTextView;
    public TextView mDetailsTextView;
    public TextView mFoundationTextView;
    public TextView mIdTextView;
    public TextView mStatusTextView;
    public Button mPostulateButton;

	public AdoptDogScreenSlideFragment(Dog mDog, Activity activity) {

		this.mDog = mDog;
		this.mActivity = activity;
	}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(mIdLayout, container, false);

        mSlidingUpPanelLayout  = (SlidingUpPanelLayout) view.findViewById(R.id.sliding_layout);
        mPictureImageView = (ImageView) view.findViewById(R.id.picture_adopt_dog_screen_slide_imageview);
        mProgressBar = (ProgressBar) view.findViewById(R.id.download_image_progressbar);
        mMatchTextView = (TextView) view.findViewById(R.id.match_adopt_dog_screen_slide_textview);
        mNameTextView = (TextView) view.findViewById(R.id.name_adopt_dog_screen_slide_textview);
        mGenderTextView = (TextView) view.findViewById(R.id.gender_adopt_dog_screen_slide_textview);
        mSterilizedTextView = (TextView) view.findViewById(R.id.sterilized_adopt_dog_screen_slide_textview);
        mChipTextView = (TextView) view.findViewById(R.id.chip_adopt_dog_screen_slide_textview);
        mTrainedTextView = (TextView) view.findViewById(R.id.trained_adopt_dog_screen_slide_textview);
        mSizeTextView = (TextView) view.findViewById(R.id.size_adopt_dog_screen_slide_textview);
        mYearsTextView = (TextView) view.findViewById(R.id.years_adopt_dog_screen_slide_textview);
        mDetailsTextView = (TextView) view.findViewById(R.id.detail_adopt_dog_textview);
        mFoundationTextView = (TextView) view.findViewById(R.id.foundation_adopt_dog_screen_slide_textview);
        mIdTextView = (TextView) view.findViewById(R.id.id_adopt_dog_screen_slide_textview);
        mStatusTextView = (TextView) view.findViewById(R.id.status_postulated_adopt_dog_textview);
        mPostulateButton = (Button) view.findViewById(R.id.postulate_adopt_dog_button);

        mNameTextView.setText(mDog.mName);
        mSizeTextView.setText(mDog.getSize().mName);
        mGenderTextView.setText(mDog.getGender(view.getContext()));
        mYearsTextView.setText(mDog.getAge());
        mSterilizedTextView.setText(mDog.getSterilized(view.getContext()));
        mChipTextView.setText(mDog.getChip(view.getContext()));
        mTrainedTextView.setText(mDog.getTrained(view.getContext()));
        mMatchTextView.setText(mDog.mCompatibility + "%");
        mFoundationTextView.setText(mDog.mFoundationName);

        if (!Do.isNullOrEmpty(mDog.mDetail))
            mDetailsTextView.setText(mDog.mDetail);

        ConfirmAdoptionDialogOnClickListener listener = new ConfirmAdoptionDialogOnClickListener(
                mDog, mActivity, mProgressDialog, mPictureImageView);
        mPostulateButton.setOnClickListener(listener);

        setUserAdoptDog();

        mSlidingUpPanelLayout.setPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {

            @Override
            public void onPanelSlide(View view, float v) {

                ((AdoptDogsFragmentActivity) mActivity).setIndicatorVisibiility(false);
            }

            @Override
            public void onPanelCollapsed(View view) {

                ((AdoptDogsFragmentActivity) mActivity).setIndicatorVisibiility(true);
            }

            @Override
            public void onPanelExpanded(View view) {

                ((AdoptDogsFragmentActivity) mActivity).setIndicatorVisibiility(false);

            }

            @Override
            public void onPanelAnchored(View view) {

            }

            @Override
            public void onPanelHidden(View view) {

                ((AdoptDogsFragmentActivity) mActivity).setIndicatorVisibiility(true);

            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (mDog.mDogImage == null) {
            requestDogImage(mPictureImageView);

        } else {
            mPictureImageView.setImageBitmap(mDog.mDogImage);

        }
    }

    @Override
    public void onResume() {
        super.onResume();

        mSlidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);

    }

    public void requestDogImage(ImageView imageView) {

        mProgressBar.setVisibility(View.VISIBLE);
        ImageResponse response = new ImageResponse(getActivity(), imageView, mProgressBar);
        Request imageRequest = RequestManager.imageRequest(mDog.mUrlImage, imageView, response,
                response);

        VolleyManager.getInstance(getActivity().getApplicationContext())
                .addToRequestQueue(imageRequest, TAG);

    }

    public void setUserAdoptDog() {


    }
}
