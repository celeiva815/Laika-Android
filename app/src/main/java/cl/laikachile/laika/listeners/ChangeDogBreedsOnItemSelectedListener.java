package cl.laikachile.laika.listeners;

import cl.laikachile.laika.R;
import cl.laikachile.laika.activities.NewDogRegisterActivity;
import cl.laikachile.laika.adapters.BreedAdapter;
import cl.laikachile.laika.models.indexes.Breed;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

import java.util.List;

public class ChangeDogBreedsOnItemSelectedListener implements OnItemSelectedListener {

    public NewDogRegisterActivity mActivity;
    public Spinner mBreedSpinner;

    public ChangeDogBreedsOnItemSelectedListener(NewDogRegisterActivity mActivity) {

        this.mActivity = mActivity;
        this.mBreedSpinner = mActivity.mBreedSpinner;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {

        if (mActivity.mIsSizeSelected) {

            List<Breed> breeds = getBreedList((int) id);
            mActivity.mBreedAdapter = new BreedAdapter(view.getContext(),
                    R.layout.ai_simple_textview_for_adapter, R.id.simple_textview, breeds);
            mBreedSpinner.setAdapter(mActivity.mBreedAdapter);
        }

        mActivity.mIsSizeSelected = true;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub

    }

    private List<Breed> getBreedList(int sizeId) {

        return Breed.getBreeds(sizeId);


    }

}
