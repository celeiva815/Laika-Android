package social.laika.app.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import social.laika.app.R;
import social.laika.app.adapters.VetVisitAdapter;
import social.laika.app.models.Dog;
import social.laika.app.models.VetVisit;
import social.laika.app.network.RequestManager;
import social.laika.app.network.VolleyManager;
import social.laika.app.responses.VetVisitsResponse;
import social.laika.app.utils.PrefsManager;

/**
 * Created by Tito_Leiva on 09-03-15.
 */
public class VetVisitsFragment extends Fragment {

    public static final String KEY_DOG = "dog";
    public static final String TAG = VetVisitsFragment.class.getSimpleName();

    public String mTag;
    private int mIdLayout = R.layout.simple_listview;
    public Dog mDog;
    public ProgressBar mProgressBar;
    public ListView mVetVisitListView;
    public List<VetVisit> mVetVisits;
    public VetVisitAdapter mVetVisitAdapter;


    public VetVisitsFragment(Dog mDog) {
        this.mDog = mDog;
        this.mTag = Long.toString(mDog.getId());
    }

    public VetVisitsFragment() {  }

    public static final VetVisitsFragment newInstance(int dogId)
    {
        VetVisitsFragment f = new VetVisitsFragment();
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

        mVetVisitListView = (ListView) view.findViewById(R.id.simple_listview);
        mVetVisitAdapter = new VetVisitAdapter(view.getContext(), R.layout.lk_vet_visit_adapter,
                getVetVisit(), mDog);

        mVetVisitListView.setAdapter(mVetVisitAdapter);
        mVetVisitListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (mVetVisitListView.getCount() == 0) {

            requestVetVisits();

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshVetVisits();

    }

    private List<VetVisit> getVetVisit() {

        return VetVisit.getVetVisits(mDog.mDogId);

    }

    public void refreshVetVisits() {

        if (!mVetVisitAdapter.isEmpty()) {
            mVetVisitAdapter.clear();

        }

        mVetVisitAdapter.addAll(getVetVisit());
        mVetVisitAdapter.notifyDataSetChanged();
    }

    public void requestVetVisits() {

        Context context = getActivity().getApplicationContext();
        Map<String, String> params = new HashMap<>();
        params.put(Dog.COLUMN_DOG_ID, Integer.toString(mDog.mDogId));

        VetVisitsResponse response = new VetVisitsResponse(this);
        Request eventsRequest = RequestManager.getRequest(params, RequestManager.ADDRESS_VET_VISITS,
                response, response, PrefsManager.getUserToken(context));

        VolleyManager.getInstance(context).addToRequestQueue(eventsRequest, TAG);

    }

}
