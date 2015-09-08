package social.laika.app.responses;

import android.app.Activity;
import android.content.Context;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import social.laika.app.R;
import social.laika.app.interfaces.Requestable;
import social.laika.app.models.AdoptDogForm;
import social.laika.app.models.Owner;
import social.laika.app.models.Personality;
import social.laika.app.models.Size;
import social.laika.app.network.Api;
import social.laika.app.network.VolleyErrorHelper;
import social.laika.app.network.VolleyManager;
import social.laika.app.utils.Do;
import social.laika.app.utils.PrefsManager;


/**
 * Created by Tito_Leiva on 05-02-15.
 */
public class LoginHandler {

    public static final String TAG = LoginHandler.class.getSimpleName();
    public final static String LAST_SYNC = "last_sync";
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

    public static void successLogin(Activity activity, JSONObject response, ProgressBar progressBar) {

        Context context = activity.getApplicationContext();

        if (!PrefsManager.isUserLoggedIn(context)) {

            String token;

            try {

                JSONObject jsonUser = response.getJSONObject(Owner.API_USER);
                Owner owner = new Owner(jsonUser);
                Owner.createOrUpdate(owner);

                if (response.has(AdoptDogForm.TABLE_ADOPT_DOG_FORM)) {

                    JSONObject jsonForm = response.optJSONObject(AdoptDogForm.TABLE_ADOPT_DOG_FORM);

                    if (jsonForm != null) {
                        AdoptDogForm.saveAdoptForm(jsonForm);
                    }
                }

                token = jsonUser.getString(Api.ACCESS_TOKEN);
                PrefsManager.saveUser(context, token, owner);

                refreshDataBase(context, activity, progressBar);

            } catch (JSONException e) {
                e.printStackTrace();

                Do.showLongToast("Hubo un problema con su inicio de sesión. Inténtelo nuevamente.",
                        context);
            }

        } else {

            String message = Do.getRString(context, R.string.auth_failure_error);
            Do.showShortToast(message, context);

        }
    }

    public static void requestFirstInformation(Context context, Requestable requestable) {

        String token = PrefsManager.getUserToken(context);
        FirstInformationResponse response = new FirstInformationResponse(requestable, context);
        Request firstRequest = Api.getRequest(null, Api.ADDRESS_SYNC,
                response, response, token);

        VolleyManager.getInstance(context).addToRequestQueue(firstRequest, TAG);

    }

    public static void requestFirstInformation(Context context, Activity activity, ProgressBar progressBar) {

        String token = PrefsManager.getUserToken(context);
        FirstInformationResponse response = new FirstInformationResponse(activity, progressBar);

        Request firstRequest = Api.getRequest(null, Api.ADDRESS_SYNC,
                response, response, token);

        VolleyManager.getInstance(context).addToRequestQueue(firstRequest, TAG);

    }


    public static void refreshDataBase(Context context, Activity activity, ProgressBar progressBar) {

        Size.setSizes(context);
        Personality.setPersonalities(context);

        requestFirstInformation(context, activity, progressBar);

    }
}
