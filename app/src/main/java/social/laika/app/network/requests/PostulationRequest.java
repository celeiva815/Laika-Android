package social.laika.app.network.requests;

import android.content.Context;
import android.os.Bundle;

import com.android.volley.Request;

import social.laika.app.activities.PostulatedDogsFragmentActivity;
import social.laika.app.interfaces.Requestable;
import social.laika.app.network.Api;
import social.laika.app.network.VolleyManager;
import social.laika.app.network.gcm.LaikaGcmListenerService;
import social.laika.app.responses.PostulatedDogsResponse;
import social.laika.app.utils.Do;
import social.laika.app.utils.PrefsManager;

/**
 * Created by Tito_Leiva on 30-07-15.
 */
public class PostulationRequest implements Requestable {

    public static final String TAG = PostulationRequest.class.getSimpleName();

    public Context mContext;
    public String mNotificationMessage;

    public PostulationRequest(Context mContext) {
        this.mContext = mContext;
    }

    public PostulationRequest(Context mContext, String mNotificationMessage) {
        this.mContext = mContext;
        this.mNotificationMessage = mNotificationMessage;
    }

    @Override
    public void request() {

        PostulatedDogsResponse response = new PostulatedDogsResponse(this);
        Request adoptDogRequest = Api.getRequest(null,
                Api.ADDRESS_USER_POSTULATIONS, response, response,
                PrefsManager.getUserToken(mContext));

        VolleyManager.getInstance(mContext)
                .addToRequestQueue(adoptDogRequest, TAG);

    }

    @Override
    public void onSuccess() {

        Bundle data = new Bundle();

        if (!Do.isNullOrEmpty(mNotificationMessage)) {

            LaikaGcmListenerService.sendNotification(mNotificationMessage, PostulatedDogsFragmentActivity.class,
                    data, mContext);
        }
    }

    @Override
    public void onFailure() {

    }
}
