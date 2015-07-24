package social.laika.app.listeners;

import social.laika.app.R;
import social.laika.app.activities.CreateDogActivity;
import social.laika.app.adapters.BreedAdapter;
import social.laika.app.models.Breed;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

import java.util.List;

public class ChangeDogBreedsOnItemSelectedListener implements OnItemSelectedListener {

    public CreateDogActivity mActivity;
    public Spinner mBreedSpinner;

    public ChangeDogBreedsOnItemSelectedListener(CreateDogActivity mActivity) {

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
