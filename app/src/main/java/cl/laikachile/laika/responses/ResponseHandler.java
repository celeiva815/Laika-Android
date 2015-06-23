package cl.laikachile.laika.responses;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import cl.laikachile.laika.R;
import cl.laikachile.laika.models.Owner;
import cl.laikachile.laika.models.Breed;
import cl.laikachile.laika.models.Personality;
import cl.laikachile.laika.models.Size;
import cl.laikachile.laika.network.RequestManager;
import cl.laikachile.laika.network.VolleyErrorHelper;
import cl.laikachile.laika.network.VolleyManager;
import cl.laikachile.laika.utils.Do;
import cl.laikachile.laika.utils.PrefsManager;
import cl.laikachile.laika.utils.Tag;


/**
 * Created by Tito_Leiva on 05-02-15.
 */
public class ResponseHandler {

    public static final String TAG = ResponseHandler.class.getSimpleName();
    public final static int TYPE_SYNC = 1;
    public final static int TYPE_ASYNC = 2;

    public static void error(VolleyError error, Context context) {

        if (error != null && error.networkResponse != null) {
            if (error.networkResponse.statusCode == 400) {
                Toast.makeText(context, R.string.parameters_error,
                        Toast.LENGTH_SHORT).show();
            } else {
                String errorStr = VolleyErrorHelper.getMessage(error,
                        context);
                Toast.makeText(context, errorStr, Toast.LENGTH_SHORT)
                        .show();
            }
        } else {
            Toast.makeText(context, R.string.no_internet_error,
                    Toast.LENGTH_SHORT).show();
        }
    }

    public static void successLogin(Activity activity, JSONObject response) {

        Context context = activity.getApplicationContext();

        if (!PrefsManager.isUserLoggedIn(context)) {

            String token;

            try {

                Owner owner = new Owner(response);
                Owner.createOrUpdate(owner);

                token = response.getString(RequestManager.ACCESS_TOKEN);
                PrefsManager.saveUser(context, token, owner);

                refreshDataBase(context, activity);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else {

            String message = Do.getRString(context, R.string.auth_failure_error);
            Do.showShortToast(message, context);

        }
    }

    public static void requestFirstInformation(Context context, Activity activity) {

        String token = PrefsManager.getUserToken(context);
        FirstInformationResponse response = new FirstInformationResponse(activity);

        Request firstRequest = RequestManager.getRequest(null, RequestManager.ADDRESS_SYNC,
                response, response, token);

        VolleyManager.getInstance(context).addToRequestQueue(firstRequest, TAG);

    }

    public static void requestLocations(Context context, Activity activity) {

        String token = PrefsManager.getUserToken(context);
        FirstInformationResponse response = new FirstInformationResponse(activity);

        Request locationsRequest = RequestManager.getRequest(null, RequestManager.ADDRESS_LOCATIONS,
                response, response, token);

        VolleyManager.getInstance(context).addToRequestQueue(locationsRequest, TAG);

    }

    public static void refreshDataBase(Context context, Activity activity) {

        Size.setSizes(context);
        Personality.setPersonalities(context);

        requestFirstInformation(context, activity);

    }
}
