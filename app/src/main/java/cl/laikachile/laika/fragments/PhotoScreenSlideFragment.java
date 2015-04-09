package cl.laikachile.laika.fragments;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cl.laikachile.laika.R;
import cl.laikachile.laika.activities.PhotosFragmentActivity;
import cl.laikachile.laika.models.Photo;

public class PhotoScreenSlideFragment extends Fragment {
	
	private int mIdLayout = R.layout.photo_screen_slider_fragment;
	public Photo mPhoto;
	public PhotosFragmentActivity mActivity;

    public RelativeLayout mFullLayout;
    public LinearLayout mInformationLayout;
    public TextView mDetailTextView;
    public TextView mOwnerTextView;
    public TextView mDateTextView;
    public ImageView mMainImageView;

	public PhotoScreenSlideFragment(Photo mPhoto, PhotosFragmentActivity mActivity) {

        this.mActivity = mActivity;
		this.mPhoto = mPhoto;
	}

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        ViewGroup view = (ViewGroup) inflater.inflate(mIdLayout, container, false);

        mFullLayout = (RelativeLayout) view.findViewById(R.id.fullscreen_photo_screen_slider_relativelayout);
        mInformationLayout = (LinearLayout) view.findViewById(R.id.information_photo_screen_slider_linearlayout);
        mDetailTextView = (TextView) view.findViewById(R.id.detail_photo_screen_slider_textview);
        mOwnerTextView = (TextView) view.findViewById(R.id.owner_photo_screen_slider_textview);
        mDateTextView = (TextView) view.findViewById(R.id.date_photo_screen_slider_textview);
		mMainImageView = (ImageView) view.findViewById(R.id.main_photo_screen_slider_imageview);
        
		mDetailTextView.setText(mPhoto.mDetail);
        mOwnerTextView.setText(mPhoto.mOwnerName);
        mDateTextView.setText(mPhoto.mDate);
        mMainImageView.setImageBitmap(mPhoto.getPicture());

        if (mActivity.mIsFullScreen) {
            showFullScreen();

        } else {
            showInformationScreen();
        }

        mMainImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!mActivity.mIsFullScreen) {

                    showFullScreen();
                    mActivity.mIsFullScreen = true;

                } else {

                    showInformationScreen();
                    mActivity.mIsFullScreen = false;
                }
            }
        });

        return view;
    }

    public void showFullScreen() {

        mInformationLayout.setVisibility(View.GONE);
        mFullLayout.setBackgroundColor(Color.BLACK);

    }

    public void showInformationScreen() {

        mInformationLayout.setVisibility(View.VISIBLE);
        mFullLayout.setBackgroundColor(Color.WHITE);

    }
}
