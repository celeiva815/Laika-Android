package cl.laikachile.laika.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cl.laikachile.laika.activities.BaseActivity;

/**
 * Created by Tito_Leiva on 10-02-15.
 */
public class PlaceHolderFragment extends Fragment {

    private int mLayout;
    private View mView;
    private BaseActivity mActivity;

    public PlaceHolderFragment() {
    }

    public PlaceHolderFragment(int mLayout, BaseActivity mActivity) {

        this.mLayout = mLayout;
        this.mActivity = mActivity;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(mLayout,container, false);
        mActivity.setActivityView(mView);

        return mView;
    }
}
