package cl.laikachile.laika.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import cl.laikachile.laika.R;
import cl.laikachile.laika.adapters.OwnerMyDogAdapter;
import cl.laikachile.laika.models.Dog;
import cl.laikachile.laika.models.Owner;
import cl.laikachile.laika.models.OwnerDog;

/**
 * Created by Tito_Leiva on 09-03-15.
 */
public class OwnerMyDogFragment extends Fragment {

    private int mIdLayout = R.layout.lk_owner_my_dog_fragment;
    public Dog mDog;
    public List<Owner> mOwners;
    public OwnerMyDogAdapter mOwnerAdapter;

    public OwnerMyDogFragment(Dog mDog) {
        this.mDog = mDog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(mIdLayout, container, false);

        ListView ownerListView = (ListView) view.findViewById(R.id.owner_my_dog_listview);
        mOwnerAdapter = new OwnerMyDogAdapter(view.getContext(), R.layout.lk_owner_my_dog_row,
                getOwners(view.getContext()), mDog);

        ownerListView.setAdapter(mOwnerAdapter);
        ownerListView.setItemsCanFocus(true);

        return view;
    }

    private List<Owner> getOwners(Context context) {

        //FIXME cambiar por los dueños sacados de la API
        List<OwnerDog> ownerDogs = OwnerDog.getOwnerDogs(mDog);

        mOwners = Owner.getOwners(ownerDogs);

        return mOwners;
    }



}
