package cl.laikachile.laika.network.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import cl.laikachile.laika.R;
import cl.laikachile.laika.activities.MainActivity;
import cl.laikachile.laika.network.RequestManager;
import cl.laikachile.laika.network.VolleyErrorHelper;
import cl.laikachile.laika.utils.Do;
import cl.laikachile.laika.utils.PrefsManager;


/**
 * Created by Tito_Leiva on 05-02-15.
 */
public class ResponseHandler {

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

            String fullName = "", email = "", token = "";
            int userId = 0;

            try {

                fullName = response.getString(RequestManager.EMAIL);
                email = response.getString(RequestManager.EMAIL);
                token = response.getString(RequestManager.ACCESS_TOKEN);
                userId = response.getInt(RequestManager.ID);

                PrefsManager.saveUser(context, fullName, email, token, userId);
                Do.changeActivity(activity.getApplicationContext(), MainActivity.class, activity,
                        Intent.FLAG_ACTIVITY_NEW_TASK);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else {

            String message = Do.getRString(context, R.string.auth_failure_error);
            Do.showToast(message, context);

        }
    }
}
