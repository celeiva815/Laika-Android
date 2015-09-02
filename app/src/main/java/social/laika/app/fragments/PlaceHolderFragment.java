package social.laika.app.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import social.laika.app.R;
import social.laika.app.activities.MainActivity;

/**
 * Created by Tito_Leiva on 10-02-15.
 */
public class PlaceHolderFragment extends Fragment {

    private int mLayout;
    private View mView;
    private MainActivity mActivity;

    public PlaceHolderFragment() {
    }

    public PlaceHolderFragment(int mLayout, MainActivity mActivity) {

        this.mLayout = mLayout;
        this.mActivity = mActivity;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        mView = inflater.inflate(mLayout > 0 ? mLayout : R.layout.laika_main_activity, container, false);
        mActivity.setActivityView(mView);

        return mView;
    }
}
