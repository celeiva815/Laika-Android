package social.laika.app.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import social.laika.app.R;
import social.laika.app.activities.RegisterActivity;
import social.laika.app.activities.TutorialActivity;
import social.laika.app.listeners.ToActivityOnCLickListener;
import social.laika.app.network.RequestManager;
import social.laika.app.network.VolleyManager;
import social.laika.app.responses.LoginResponse;
import social.laika.app.utils.Do;
import social.laika.app.utils.PrefsManager;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TutorialFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class TutorialFragment extends Fragment {

    public static final String TAG = TutorialFragment.class.getSimpleName();
    public static final String API_EMAIL = "email";
    public static final String API_PASSWORD = "password";
    public static final int TUTORIAL_ADOPTION = 0;
    public static final int TUTORIAL_OWNERSHIP = 1;
    public static final int TUTORIAL_INFORMATION = 2;
    public static final int TUTORIAL_FOUNDATIONS = 3;
    public static final int TUTORIAL_LOG_IN = 4;

    public EditText mEmailEditText;
    public EditText mPasswordEditText;
    public Button mLoginButton;
    public Button mRegisterButton;
    public ProgressBar mLoginProgressBar;

    // the fragment initialization parameters
    private static final String ARG_PAGE_NUMB = "cl.laikachile.laika.fragments" +
            ".TutorialFragment.argPageNumb";

    // The fragment number
    private int mPageNumb;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param pageNumber The number that corresponds to the fragment page.
     * @return A new instance of fragment TutorialFragment.
     */
    public static TutorialFragment newInstance(int pageNumber) {
        TutorialFragment fragment = new TutorialFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE_NUMB, pageNumber);
        fragment.setArguments(args);
        return fragment;
    }
    public TutorialFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPageNumb = getArguments().getInt(ARG_PAGE_NUMB);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment. The layout depends on the corresponding number
        // of the tutorial page
        int layoutId = R.layout.lk_tutorial_fragment;
        View view = inflater.inflate(layoutId, container, false);
        Context context = view.getContext();
        String title = "";
        String detail = "";
        int backgroundId = 0;

        switch (mPageNumb) {

            case TUTORIAL_ADOPTION:
                title = Do.getRString(context, R.string.title_adoption_tutorial);
                detail = Do.getRString(context, R.string.detail_adoption_tutorial);
                backgroundId = R.drawable.lk_adoption_background;
                break;

            case TUTORIAL_OWNERSHIP:
                title = Do.getRString(context, R.string.title_ownership_tutorial);
                detail = Do.getRString(context, R.string.detail_ownership_tutorial);
                backgroundId = R.drawable.lk_events_background;
                break;

            case TUTORIAL_INFORMATION:
                title = Do.getRString(context, R.string.title_information_tutorial);
                detail = Do.getRString(context, R.string.detail_information_tutorial);
                backgroundId = R.drawable.lk_give_in_adoption_background;
                break;

            case TUTORIAL_FOUNDATIONS:
                title = Do.getRString(context, R.string.title_foundation_tutorial);
                detail = Do.getRString(context, R.string.detail_foundation_tutorial);
                backgroundId = R.drawable.lk_reminders_background;
                break;

            case TUTORIAL_LOG_IN:
                layoutId = R.layout.lk_login_activity;
                view = inflater.inflate(layoutId, container, false);

                return setLoginLayout(view);
        }

        return setTutorialLayout(view, title, detail, backgroundId);
    }

    public View setTutorialLayout(View view, String title, String detail, int backgroundId) {

        final TutorialActivity activity = (TutorialActivity) getActivity();
        TextView titleTextView = (TextView) view.findViewById(R.id.title_tutorial_textview);
        TextView detailTextView = (TextView) view.findViewById(R.id.detail_tutorial_textview);
        RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.tutorial_layout);
        Button skipButton = (Button) view.findViewById(R.id.skip_tutorial_button);

        titleTextView.setText(title);
        detailTextView.setText(detail);
        relativeLayout.setBackgroundResource(backgroundId);

        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                activity.mPager.setCurrentItem(TutorialActivity.NUM_PAGES - 1);

            }
        });

        return view;
    }

    public View setLoginLayout(View view) {

        mEmailEditText = (EditText) view.findViewById(R.id.email_login_edittext);
        mPasswordEditText = (EditText) view.findViewById(R.id.password_login_edittext);
        mLoginButton = (Button) view.findViewById(R.id.login_button);
        mRegisterButton = (Button) view.findViewById(R.id.register_login_button);
        mLoginProgressBar = (ProgressBar) view.findViewById(R.id.login_progressbar);

        mLoginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                requestLogIn(view);

            }
        });

        mRegisterButton.setOnClickListener(new ToActivityOnCLickListener(RegisterActivity.class));

        return view;
    }

    public void enableViews(boolean enable) {

        mEmailEditText.setEnabled(enable);
        mPasswordEditText.setEnabled(enable);
        mLoginButton.setEnabled(enable);

        if (enable) {
            mLoginButton.setVisibility(View.VISIBLE);
            mLoginProgressBar.setVisibility(View.GONE);

        } else {
            mLoginButton.setVisibility(View.GONE);
            mLoginProgressBar.setVisibility(View.VISIBLE);
        }

    }

    public void requestLogIn(View view) {

        final String email = mEmailEditText.getText().toString();
        final String password = mPasswordEditText.getText().toString();

        if (TextUtils.isEmpty(password)) {
            mPasswordEditText.setError(getString(R.string.field_not_empty_error));
            return;
        }

        mLoginProgressBar.setVisibility(View.VISIBLE);
        enableViews(false);

        Map<String, String> params = new HashMap<>(2);
        params.put(API_EMAIL, email);
        params.put(API_PASSWORD, password);

        JSONObject jsonParams = RequestManager.getJsonParams(params);
        LoginResponse response = new LoginResponse(this);

        Request loginRequest = RequestManager.postRequest(jsonParams, RequestManager.ADDRESS_LOGIN,
                response, response, PrefsManager.getUserToken(view.getContext()));

        VolleyManager.getInstance(view.getContext())
                .addToRequestQueue(loginRequest, TAG);
    }


}
