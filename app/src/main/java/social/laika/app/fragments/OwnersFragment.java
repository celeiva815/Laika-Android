package social.laika.app.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import social.laika.app.R;
import social.laika.app.adapters.OwnerMyDogAdapter;
import social.laika.app.models.Dog;
import social.laika.app.models.Owner;
import social.laika.app.models.OwnerDog;
import social.laika.app.network.RequestManager;
import social.laika.app.network.VolleyManager;
import social.laika.app.responses.OwnersResponse;
import social.laika.app.utils.PrefsManager;

/**
 * Created by Tito_Leiva on 09-03-15.
 */
public class OwnersFragment extends Fragment {

    public static final String TAG = OwnersFragment.class.getSimpleName();
    public static final String KEY_DOG = "dog";

    public int mIdLayout = R.layout.lk_owner_my_dog_fragment;
    public Dog mDog;
    public List<Owner> mOwners;
    public OwnerMyDogAdapter mOwnerAdapter;
    public ListView mOwnerListView;
    public LinearLayout mEmptyLinearLayout;
    public TextView mEmptyTextView;


    public OwnersFragment() {
    }

    public OwnersFragment(Dog mDog) {
        this.mDog = mDog;
    }

    public static final OwnersFragment newInstance(int dogId)
    {
        OwnersFragment f = new OwnersFragment();
        Bundle bdl = new Bundle(1);
        bdl.putInt(KEY_DOG, dogId);
        f.setArguments(bdl);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        int dogId = getArguments().getInt(KEY_DOG);
        mDog = Dog.getSingleDog(dogId);

        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(mIdLayout, container, false);

        mOwnerListView = (ListView) view.findViewById(R.id.owner_my_dog_listview);
        mEmptyLinearLayout = (LinearLayout) view.findViewById(R.id.empty_view);
        mEmptyTextView = (TextView) view.findViewById(R.id.simple_textview);
        mOwnerAdapter = new OwnerMyDogAdapter(view.getContext(), R.layout.lk_owner_my_dog_row,
                getOwners(), mDog);

        mOwnerListView.setAdapter(mOwnerAdapter);
        mOwnerListView.setItemsCanFocus(true);
        mOwnerListView.setEmptyView(mEmptyLinearLayout);


        return view;
    }

    @Override
    public void onStart() {

        if (mOwnerListView.getCount() == 0) {

            mEmptyLinearLayout.setVisibility(View.VISIBLE);
            requestOwners();

        }
        super.onStart();

    }

    public List<Owner> getOwners() {

        List<OwnerDog> ownerDogs = OwnerDog.getOwnerDogs(mDog);
        mOwners = Owner.getOwners(ownerDogs);

        return mOwners;
    }


    public void requestOwners() {

        Context context = getActivity().getApplicationContext();
        Map<String, String> params = new HashMap<>();
        params.put(Dog.COLUMN_DOG_ID, Integer.toString(mDog.mDogId));

        OwnersResponse response = new OwnersResponse(this);
        Request eventsRequest = RequestManager.getRequest(params, RequestManager.ADDRESS_OWNER_DOGS,
                response, response, PrefsManager.getUserToken(context));

        VolleyManager.getInstance(context).addToRequestQueue(eventsRequest, TAG);

    }

    public void refreshList() {

        mEmptyLinearLayout.setVisibility(View.GONE);

        if (!mOwnerAdapter.isEmpty()) {
            mOwnerAdapter.clear();

        }

        mOwnerAdapter.addAll(getOwners());
        mOwnerAdapter.notifyDataSetChanged();

    }
}
