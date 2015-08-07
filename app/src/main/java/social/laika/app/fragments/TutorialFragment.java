package social.laika.app.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import social.laika.app.R;
import social.laika.app.activities.RegisterActivity;
import social.laika.app.activities.TutorialActivity;
import social.laika.app.listeners.ToActivityOnCLickListener;
import social.laika.app.network.RequestManager;
import social.laika.app.network.VolleyManager;
import social.laika.app.responses.FacebookLoginResponse;
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
    public static final String API_TOKEN = "token";
    public static final String API_LAST_NAME = "last_name";
    public static final String API_FIRST_NAME = "first_name";
    public static final String API_FB_ID = "uid";
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

    /* Facebook Login */
    private LoginButton mFacebookLoginButton;
    private LaikaFBCallback mFacebookCallback;
    public static final List<String> FACEBOOK_PERMISSIONS =  Arrays.asList("public_profile", "email", "user_friends");
    /* Facebook Login */

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

        /* [Start] Facebook Login configuration */
        Log.d(TAG, getActivity().getClass().getName());
        mFacebookLoginButton = (LoginButton) view.findViewById(R.id.facebook_login_button);// Facebook

        /* Setting Permissions */
        mFacebookLoginButton.setReadPermissions(FACEBOOK_PERMISSIONS);

        /* Setting the CallbackManager */
        CallbackManager callbackManager = ((TutorialActivity) getActivity()).getCallbackManager();
        if (mFacebookCallback == null) {
            mFacebookCallback = new LaikaFBCallback(view.getContext(), this);
        }

        mFacebookLoginButton.registerCallback(callbackManager, mFacebookCallback);
        Log.d(TAG, "Facebook login configuration completed");
        /* [Finish] Finished Facebook Login configuration */

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

    private class LaikaFBCallback implements FacebookCallback<LoginResult> {
        /**
         * FacebookCallback implementation to manage facebook login.
         */

        Context mContext;
        TutorialFragment mFragment;
        public LaikaFBCallback(Context mContext, TutorialFragment mFragment) {
            this.mContext = mContext;
            this.mFragment = mFragment;
        }

        @Override
        public void onSuccess(LoginResult loginResult) {
            Log.d(TAG, "Facebook Login Successfull");
            final String token = loginResult.getAccessToken().getToken();
            GraphRequest request = GraphRequest.newMeRequest(
                    loginResult.getAccessToken(),
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(
                                JSONObject object,
                                GraphResponse graphResponse) {

                            try {
                                JSONObject rJson = graphResponse.getJSONObject();
                                String email = rJson.getString("email");
                                String first_name = rJson.getString("first_name");
                                String last_name = rJson.getString("last_name");
                                String id = rJson.getString("id");

                                Map<String, String> params = new HashMap<>(5);
                                params.put(API_TOKEN, token);
                                params.put(API_EMAIL, email);
                                params.put(API_FIRST_NAME, first_name);
                                params.put(API_LAST_NAME, last_name);
                                params.put(API_FB_ID, id);

                                FacebookLoginResponse response = new FacebookLoginResponse(mFragment);
                                JSONObject jsonParams = RequestManager.getJsonParams(params);
                                Request loginRequest = RequestManager.postRequest(jsonParams, RequestManager.ADDRESS_FB_LOGIN,
                                        response, response, "");

                                VolleyManager.getInstance(mContext).addToRequestQueue(loginRequest, TAG);

                                Log.d(TAG, "Token: " + token);
                                Log.d(TAG, "First Name: " + first_name);
                                Log.d(TAG, "Last Name: " + last_name);
                                Log.d(TAG, "Email: " + email);
                                Log.d(TAG, "Id: " + id);

                            } catch (JSONException e) {
                                // TODO Notify facebook error
                                Log.e(TAG, e.getMessage());
                            }
                        }
                    });
            /* Executing the request*/
            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,first_name,last_name,email");
            request.setParameters(parameters);
            request.executeAsync();

            /* Disabling views */
            mLoginProgressBar.setVisibility(View.VISIBLE);
            enableViews(false);
        }

        @Override
        public void onCancel() { Log.d(TAG, "Facebook Login Cancelled"); }

        @Override
        public void onError(FacebookException e) { Log.d(TAG, "Facebook Login Error: " + e.getMessage()); }
    }


}
