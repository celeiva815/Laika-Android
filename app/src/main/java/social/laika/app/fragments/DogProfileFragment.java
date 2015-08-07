package social.laika.app.fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import social.laika.app.R;
import social.laika.app.adapters.OwnerMyDogAdapter;
import social.laika.app.adapters.OwnersAdapter;
import social.laika.app.interfaces.Refreshable;
import social.laika.app.models.Dog;
import social.laika.app.models.Owner;
import social.laika.app.models.OwnerDog;
import social.laika.app.network.RequestManager;
import social.laika.app.network.VolleyManager;
import social.laika.app.responses.ImageResponse;
import social.laika.app.responses.OwnersResponse;
import social.laika.app.utils.Do;
import social.laika.app.utils.PrefsManager;
import social.laika.app.utils.Tag;

/**
 * Created by Tito_Leiva on 09-03-15.
 */
public class DogProfileFragment extends Fragment implements Refreshable {

    public static final String KEY_DOG = "mDog";
    public static final String TAG = DogProfileFragment.class.getSimpleName();

    public String mTag;
    private int mIdLayout = R.layout.lk_dog_profile_fragment;
    public Dog mDog;
    public List<Owner> mOwners;
    public ImageView mDogImageView;
    public TextView mNameTextView;
    public TextView mBirthDateTextView;
    public TextView mSizeTextView;
    public TextView mBreedTextView;
    public TextView mPersonalityTextView;
    public TextView mSterilizedTextView;
    public TextView mChipTextView;
    public ListView mOwnersListView;
    public OwnerMyDogAdapter mOwnersAdapter;

    public ProgressBar mProgressBar;
    public Dialog mDialog;


    public DogProfileFragment(Dog mDog) {
        this.mDog = mDog;
        this.mTag = Long.toString(mDog.getId());
    }

    public DogProfileFragment() {

    }

    public static final DogProfileFragment newInstance(int dogId) {
        DogProfileFragment f = new DogProfileFragment();
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

        mDogImageView = (ImageView) view.findViewById(R.id.dog_history_imageview);
        mNameTextView = (TextView) view.findViewById(R.id.name_dog_profile_textview);
        mBirthDateTextView = (TextView) view.findViewById(R.id.birth_date_dog_profile_textview);
        mSizeTextView = (TextView) view.findViewById(R.id.size_dog_profile_textview);
        ;
        mBreedTextView = (TextView) view.findViewById(R.id.breed_dog_profile_textview);
        ;
        mPersonalityTextView = (TextView) view.findViewById(R.id.personality_dog_profile_textview);
        ;
        mSterilizedTextView = (TextView) view.findViewById(R.id.sterilized_dog_profile_textview);
        ;
        mChipTextView = (TextView) view.findViewById(R.id.chip_dog_profile_textview);
        ;
        mProgressBar = (ProgressBar) view.findViewById(R.id.download_image_progressbar);
        mOwnersListView = (ListView) view.findViewById(R.id.owners_dog_profile_listview);
        mOwnersAdapter = new OwnerMyDogAdapter(view.getContext(), R.layout.lk_owner_my_dog_row,
                getOwners(), mDog);

        mNameTextView.setText(mDog.mName);
        mBirthDateTextView.setText(mDog.mBirth);
        mSizeTextView.setText(mDog.getSize().mName);
        mBreedTextView.setText(mDog.getBreed().mName);
        mPersonalityTextView.setText(mDog.getPersonality().mName);
        mSterilizedTextView.setText(Do.getRString(view.getContext(),
                mDog.mIsSterilized ? R.string.is_sterilized : R.string.is_not_sterilized));

        if (!Do.isNullOrEmpty(mDog.mChipCode)) {
            mChipTextView.setText("Chip:" + mDog.mChipCode);

        } else {
            mChipTextView.setText(Do.getRString(view.getContext(), R.string.has_not_chip));

        }

        mOwnersListView.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        mOwnersListView.setAdapter(mOwnersAdapter);
        Do.setListViewHeightBasedOnChildren(mOwnersListView);


        return view;
    }

    @Override
    public void onStart() {

        super.onStart();

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        requestDogImage();

    }

    @Override
    public void onResume() {
        super.onResume();

        refresh();

    }

    public void requestOwners() {

        Context context = getActivity().getApplicationContext();
        Map<String, String> params = new HashMap<>();
        params.put(Dog.COLUMN_DOG_ID, Integer.toString(mDog.mDogId));

        OwnersResponse response = new OwnersResponse(this, mDog, context);
        Request eventsRequest = RequestManager.getRequest(params, RequestManager.ADDRESS_OWNER_DOGS,
                response, response, PrefsManager.getUserToken(context));

        VolleyManager.getInstance(context).addToRequestQueue(eventsRequest, TAG);

    }

    public void requestDogImage() {

        mProgressBar.setVisibility(View.VISIBLE);
        ImageResponse response = new ImageResponse(mDogImageView, mProgressBar);
        Request request = RequestManager.imageRequest(mDog.getImage(Tag.IMAGE_MEDIUM_S),
                mDogImageView, response, response);

        VolleyManager.getInstance(getActivity().getApplicationContext()).addToRequestQueue(request);

    }

    public List<Owner> getOwners() {

        List<OwnerDog> ownerDogs = OwnerDog.getOwnerDogs(mDog);
        mOwners = Owner.getOwners(ownerDogs);

        return mOwners;
    }

    @Override
    public void refresh() {

        if (!mOwnersAdapter.isEmpty()) {

            mOwnersAdapter.clear();
        }

        mOwnersAdapter.addAll(getOwners());
        mOwnersAdapter.notifyDataSetChanged();

    }


}
