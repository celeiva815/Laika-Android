package cl.laikachile.laika.listeners;

import cl.laikachile.laika.activities.AdoptDogFormActivity;
import cl.laikachile.laika.activities.AdoptDogFragmentActivity;
import cl.laikachile.laika.models.AdoptDogForm;
import cl.laikachile.laika.utils.Do;
import cl.laikachile.laika.utils.PrefsManager;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;

public class SearchDogsToAdoptOnClickListener implements OnClickListener {

    public AdoptDogFormActivity mActivity;

    public SearchDogsToAdoptOnClickListener(AdoptDogFormActivity mActivity) {
        this.mActivity = mActivity;
    }

    @Override
	public void onClick(View v) {

        /*FIXME hacer el check de que toda la información este ingresada antes de postular.
        int ownerId = PrefsManager.getUserId(v.getContext());
        String region = mActivity.mLocation.mRegion;
        String city = mActivity.mLocation.mCity;
        String homeType = (String) mActivity.mHomeSpinner.getSelectedItem();

        int familyCount = 0;
        if (!Do.isNullOrEmpty(mActivity.mPartnersEditText.getText().toString())) {
            familyCount = Integer.parseInt(mActivity.mPartnersEditText.getText().toString());
        }

        boolean hasPet = mActivity.mPets;
        boolean hasElderly = mActivity.mElderly;
        boolean hasKids = mActivity.mKids;
        int gender = mActivity.mGender;
        String size = (String) mActivity.mSizeSpinner.getSelectedItem();
        String personality = (String) mActivity.mPersonalitySpinner.getSelectedItem();

        //TODO enviar este form por la API.
        AdoptDogForm adoptDogForm = new AdoptDogForm(ownerId, region, city, homeType, familyCount,
                hasPet, hasElderly, hasKids, gender, size, personality);

        adoptDogForm.save(); */

		final Context context = v.getContext();
		final ProgressDialog progressDialog = ProgressDialog.show(v.getContext(),
                "Espera un momento", "Estamos buscando mascotas que se adecúen a tu perfil");
		
		new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                
            	progressDialog.dismiss();
            	Do.changeActivity(context, AdoptDogFragmentActivity.class);
            	
            }
        }, 1000);
		
	}

}
