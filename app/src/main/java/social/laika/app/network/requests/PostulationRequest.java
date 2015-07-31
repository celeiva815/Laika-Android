package social.laika.app.network.requests;

import android.content.Context;

import com.android.volley.Request;

import social.laika.app.interfaces.Requestable;
import social.laika.app.network.RequestManager;
import social.laika.app.network.VolleyManager;
import social.laika.app.responses.PostulatedDogsResponse;
import social.laika.app.utils.PrefsManager;

/**
 * Created by Tito_Leiva on 30-07-15.
 */
public class PostulationRequest implements Requestable {

    public static final String TAG = PostulationRequest.class.getSimpleName();

    public Context mContext;

    public PostulationRequest(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void request() {

        PostulatedDogsResponse response = new PostulatedDogsResponse(this);
        Request adoptDogRequest = RequestManager.getRequest(null,
                RequestManager.ADDRESS_USER_POSTULATIONS, response, response,
                PrefsManager.getUserToken(mContext));

        VolleyManager.getInstance(mContext)
                .addToRequestQueue(adoptDogRequest, TAG);

    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onFailure() {

    }
}
