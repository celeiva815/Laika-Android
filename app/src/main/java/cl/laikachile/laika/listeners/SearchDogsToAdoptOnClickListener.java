package cl.laikachile.laika.listeners;

import cl.laikachile.laika.R;
import cl.laikachile.laika.activities.AdoptDogFormActivity;
import cl.laikachile.laika.models.AdoptDogForm;
import cl.laikachile.laika.utils.PrefsManager;

import android.app.ProgressDialog;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;

public class SearchDogsToAdoptOnClickListener implements OnClickListener {

    public AdoptDogFormActivity mActivity;
    public ProgressDialog mProgressDialog;



    public SearchDogsToAdoptOnClickListener(AdoptDogFormActivity mActivity) {
        this.mActivity = mActivity;
    }

    @Override
    public void onClick(View v) {

        int ownerId = PrefsManager.getUserId(v.getContext());
        int locationId = mActivity.mLocation.mLocationId;
        int homeType = (int) mActivity.mHomeSpinner.getSelectedItemId();
        int familyCount = 0; //FIXME
        boolean hasPet = mActivity.mPets;
        boolean hasElderly = mActivity.mElderly;
        boolean hasKids = mActivity.mKids;
        int gender = mActivity.mGender;
        int dogSize = (int) mActivity.mSizeSpinner.getSelectedItemId();
        int dogPersonality = (int) mActivity.mPersonalitySpinner.getSelectedItemId();
        int freeTime = (int) mActivity.mFreeTimeSpinner.getSelectedItemId();

        AdoptDogForm adoptDogForm = new AdoptDogForm(ownerId,locationId,homeType,familyCount,hasPet,
                hasElderly,hasKids,gender,dogSize,dogPersonality, freeTime);

        mActivity.requestAdoptionDogForm(adoptDogForm);

    }

    public boolean checkField() {

        if (TextUtils.isEmpty(mActivity.mPartnersEditText.getText())) {
            mActivity.mPartnersEditText.setError(mActivity.getString(R.string.field_not_empty_error));
            return false;
        }


        return true;
    }

}
